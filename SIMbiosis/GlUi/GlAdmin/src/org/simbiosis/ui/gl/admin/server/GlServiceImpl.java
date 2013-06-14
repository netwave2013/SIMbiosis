package org.simbiosis.ui.gl.admin.server;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;

import org.simbiosis.bp.gl.IGlBp;
import org.simbiosis.bp.system.ISystemBp;
import org.simbiosis.gl.CoaDto;
import org.simbiosis.system.BranchDto;
import org.simbiosis.ui.gl.admin.client.rpc.GlService;
import org.simbiosis.ui.gl.admin.shared.BranchDv;
import org.simbiosis.ui.gl.admin.shared.CoaDv;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class GlServiceImpl extends RemoteServiceServlet implements GlService {

	@EJB(lookup = "java:global/GlBpEar/GlBpEjb/GlBp")
	IGlBp iGlBp;
	@EJB(lookup = "java:global/SystemBpEar/SystemBpEjb/SystemBp")
	ISystemBp iSystemBp;

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
		coaDv.setRefId(coaDto.getRefId());
		coaDv.setCode(coaDto.getCode());
		coaDv.setPrefix(coaDto.getPrefix());
		coaDv.setDescription(coaDto.getDescription());
		coaDv.setCodeDescription(coaDto.getCode() + " - "
				+ coaDto.getDescription());
		coaDv.setParent(coaDto.getParent());
		coaDv.setParentCodeDescription(coaDto.getParentCode() + " - "
				+ coaDto.getParentDescription());
		coaDv.setLevel(coaDto.getLevel());
		coaDv.setHasChildren(coaDto.isHasChildren());
		coaDv.setGrandParent(coaDto.getGrandParent());
		coaDv.setBranch(coaDto.getBranch());
		coaDv.setExcBranch(coaDto.getExcBranch());
		return coaDv;
	}

	@Override
	public List<CoaDv> listCoaByParent(String key, Long parent) {
		//
		List<BranchDv> listAllBranch = listBranch(key);
		Map<Long, String> map = new HashMap<Long, String>();
		for (BranchDv branchDv : listAllBranch) {
			map.put(branchDv.getId(),
					branchDv.getCode() + " - " + branchDv.getName());
		}
		//
		List<CoaDv> returnList = new ArrayList<CoaDv>();
		List<CoaDto> listCoaDto = iGlBp.listCoaByParent(key, parent);
		for (CoaDto coaDto : listCoaDto) {
			CoaDv coaDv = createCoaDv(coaDto);
			coaDv.setStrBranch(map.get(coaDv.getBranch()));
			coaDv.setStrExcBranch(map.get(coaDv.getExcBranch()));
			returnList.add(coaDv);
		}
		return returnList;
	}

	@Override
	public List<CoaDv> listCoaByType(String key, Integer type) {
		//
		List<BranchDv> listAllBranch = listBranch(key);
		Map<Long, String> map = new HashMap<Long, String>();
		for (BranchDv branchDv : listAllBranch) {
			map.put(branchDv.getId(),
					branchDv.getCode() + " - " + branchDv.getName());
		}
		//
		List<CoaDv> returnList = new ArrayList<CoaDv>();
		List<CoaDto> listCoaDto = iGlBp.listCoaByType(key, type);
		for (CoaDto coaDto : listCoaDto) {
			CoaDv coaDv = createCoaDv(coaDto);
			coaDv.setStrBranch(map.get(coaDv.getBranch()));
			coaDv.setStrExcBranch(map.get(coaDv.getExcBranch()));
			returnList.add(coaDv);
		}
		return returnList;
	}

	@Override
	public CoaDv saveCoa(String key, CoaDv coaDv) {
		CoaDto coaDto = new CoaDto();
		coaDto.setId(coaDv.getId());
		coaDto.setRefId(coaDv.getRefId());
		coaDto.setCode(coaDv.getCode());
		coaDto.setPrefix(coaDv.getPrefix());
		coaDto.setDescription(coaDv.getDescription());
		coaDto.setParent(coaDv.getParent());
		coaDto.setBranch(coaDv.getBranch());
		coaDto.setExcBranch(coaDv.getExcBranch());
		long id = iGlBp.saveCoa(key, coaDto);
		coaDto = iGlBp.getCoa(id);
		//
		List<BranchDv> listAllBranch = listBranch(key);
		Map<Long, String> map = new HashMap<Long, String>();
		for (BranchDv branchDv : listAllBranch) {
			map.put(branchDv.getId(),
					branchDv.getCode() + " - " + branchDv.getName());
		}
		CoaDv resultCoaDv = createCoaDv(coaDto);
		resultCoaDv.setStrBranch(map.get(resultCoaDv.getBranch()));
		resultCoaDv.setStrExcBranch(map.get(resultCoaDv.getExcBranch()));
		//
		return resultCoaDv;
	}

	@Override
	public void removeCoa(long id) {
		iGlBp.removeCoa(id);
	}

	@Override
	public List<BranchDv> listBranch(String key) {
		List<BranchDv> listBranch = new ArrayList<BranchDv>();
		//
		BranchDv branchDv = new BranchDv();
		branchDv.setId(0);
		branchDv.setCode("*");
		branchDv.setName("SEMUA CABANG");
		listBranch.add(branchDv);
		//
		List<BranchDto> listBranchDto = iSystemBp.listBranch(key);
		for (BranchDto branchDto : listBranchDto) {
			branchDv = new BranchDv();
			branchDv.setId(branchDto.getId());
			branchDv.setCode(branchDto.getCode());
			branchDv.setName(branchDto.getName());
			listBranch.add(branchDv);
		}
		return listBranch;
	}

}
