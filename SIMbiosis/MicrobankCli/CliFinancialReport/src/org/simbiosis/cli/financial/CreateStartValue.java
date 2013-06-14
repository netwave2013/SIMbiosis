package org.simbiosis.cli.financial;

import org.simbiosis.cli.financial.lib.StartValue;

public class CreateStartValue {

	String host = "localhost";
	int port = 8080;
	int year = 2013;

	public static void main(String[] args) {
		CreateStartValue app = new CreateStartValue();
		app.execute();
	}

	public void execute() {
		StartValue neraca = new StartValue(host, port, year);
		neraca.execute();
	}

}
