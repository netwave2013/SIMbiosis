package org.simbiosis.ui.gl.input.server;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;

import org.simbiosis.bp.gl.IGlBp;
import org.simbiosis.gl.CoaDto;
import org.simbiosis.gl.GLTransDto;
import org.simbiosis.gl.GLTransItemDto;
import org.simbiosis.ui.gl.input.client.rpc.GlService;
import org.simbiosis.ui.gl.input.shared.CoaDv;
import org.simbiosis.ui.gl.input.shared.GLTransDv;
import org.simbiosis.ui.gl.input.shared.GLTransItemDv;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class GlServiceImpl extends RemoteServiceServlet implements GlService {

	@EJB(lookup = "java:global/GlBpEar/GlBpEjb/GlBp")
	IGlBp glBp;

	public static Date strToDate(String strDate) {
		DateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
		Date date = null;
		if (!strDate.equalsIgnoreCase("null")) {
			try {
				date = (Date) formater.parse(strDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return date;

		}
		return null;
	}

	public static String dateToStr(Date date) {
		DateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
		if (date == null) {
			return "";
		}
		return formater.format(date);
	}

	private CoaDv createCoaDv(CoaDto coaDto) {
		CoaDv coaDv = new CoaDv();
		coaDv.setId(coaDto.getId());
		coaDv.setCode(coaDto.getCode());
		coaDv.setDescription(coaDto.getDescription());
		return coaDv;
	}

	@Override
	public List<CoaDv> listCoaForTransaction(String key) {
		List<CoaDv> returnList = new ArrayList<CoaDv>();
		List<CoaDto> listCoaDto = glBp.listCoaForTransaction(key);
		Iterator<CoaDto> iterCoaDto = listCoaDto.iterator();
		while (iterCoaDto.hasNext()) {
			CoaDto coaDto = iterCoaDto.next();
			returnList.add(createCoaDv(coaDto));
		}
		return returnList;
	}

	private GLTransDv createGLTransDv(GLTransDto transDto) {
		int i = 1;
		GLTransDv transDv = new GLTransDv();
		transDv.setId(transDto.getId());
		transDv.setCode(transDto.getCode());
		transDv.setDescription(transDto.getDescription());
		transDv.setStrDate(dateToStr(transDto.getDate()));
		transDv.setDate(strToDate(transDv.getStrDate()));
		transDv.setCurrencyId(transDto.getCurrencyId());
		// FIXME:
		transDv.setStrCurrency("Rupiah");
		transDv.setRate(transDto.getRate());
		transDv.setStrRate("" + transDto.getRate());
		for (GLTransItemDto itemDto : transDto.getItems()) {
			GLTransItemDv itemDv = new GLTransItemDv();
			itemDv.setNr(i++);
			itemDv.setId(itemDto.getId());
			itemDv.setCoa(itemDto.getCoa());
			itemDv.setDescription(itemDto.getDescription());
			CoaDto coaDto = glBp.getCoa(itemDto.getCoa());
			itemDv.setCoaStr(coaDto.getCode() + " - " + coaDto.getDescription());
			if (itemDto.getDirection() == 1) {
				itemDv.setDebit(itemDto.getValue());
				itemDv.setCredit(0.00);
			} else {
				itemDv.setDebit(0.00);
				itemDv.setCredit(itemDto.getValue());
			}
			transDv.getItems().add(itemDv);
		}
		return transDv;
	}

	@Override
	public List<GLTransDv> listGLTransByDate(String key, Date begin, Date end,
			Integer status) {
		List<GLTransDv> searchResultDv = new ArrayList<GLTransDv>();
		List<GLTransDto> transDtoList = glBp.listGLTransByDate(key, begin, end,
				status);
		int i = 1;
		for (GLTransDto transDto : transDtoList) {
			GLTransDv transDv = new GLTransDv();
			transDv.setId(transDto.getId());
			transDv.setChecked(false);
			transDv.setNr(i++);
			transDv.setCode(transDto.getCode());
			transDv.setStrDate(dateToStr(transDto.getDate()));
			transDv.setDescription(transDto.getDescription());
			transDv.setValue(transDto.getValue());
			searchResultDv.add(transDv);
		}
		return searchResultDv;
	}

	@Override
	public GLTransDv getGLTrans(Long id) {
		GLTransDto transDto = glBp.getGLTrans(id);
		return createGLTransDv(transDto);
	}

	@Override
	public Long saveGLTrans(String key, GLTransDv transDv) {
		GLTransDto transDto = new GLTransDto();
		transDto.setId(transDv.getId());
		transDto.setCode(transDv.getCode());
		transDto.setDescription(transDv.getDescription());
		transDto.setDate(transDv.getDate());
		transDto.setCurrencyId(transDv.getCurrencyId());
		transDto.setRate(Double.parseDouble(transDv.getStrRate()));
		for (GLTransItemDv itemDv : transDv.getItems()) {
			GLTransItemDto itemDto = new GLTransItemDto();
			itemDto.setId(itemDv.getId());
			itemDto.setCoa(itemDv.getCoa());
			itemDto.setDescription(itemDv.getDescription());
			if (itemDv.getDebit() != 0) {
				itemDto.setValue(itemDv.getDebit());
				itemDto.setDirection(1);
			} else {
				itemDto.setValue(itemDv.getCredit());
				itemDto.setDirection(2);
			}
			transDto.getItems().add(itemDto);
		}
		return glBp.saveGLTrans(key, transDto);
	}

	@Override
	public void removeGLTrans(Long id) {
		glBp.removeGLTrans(id);
	}

	@Override
	public void releaseGLTrans(String key, List<Long> idList, Boolean released) {
		glBp.releaseGLTrans(key, idList, released ? 1 : 0);
	}

	@Override
	public void postGLTrans(String key, List<Long> idList, Boolean posted) {
		glBp.postGLTrans(key, idList, posted ? 1 : 0);
	}

	@Override
	public List<GLTransItemDv> listGLTransCoa(String key, Date begin, Date end,
			Long coa) {
		List<GLTransItemDv> result = new ArrayList<GLTransItemDv>();
		List<GLTransItemDto> items = glBp
				.listGLTransByCoa(key, begin, end, coa);
		int i = 1;
		double saldo = 0;
		for (GLTransItemDto item : items) {
			GLTransItemDv dv = new GLTransItemDv();
			dv.setNr(i++);
			dv.setDate(item.getDate());
			dv.setCode(item.getCode());
			dv.setDescription(item.getDescription());
			if (item.getDirection() == 1) {
				dv.setDebit(item.getValue());
				dv.setCredit(0D);
				saldo += item.getValue();
			} else {
				dv.setDebit(0D);
				dv.setCredit(item.getValue());
				saldo -= item.getValue();
			}
			dv.setSaldo(saldo*item.getFactor());
			result.add(dv);
		}
		return result;
	}

}
