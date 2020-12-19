package com.sec.api.service.util;

public class GeradorMatricula {

	public static String gerarMatricula() {

		String matricula = "";

		for (int i = 0; i < 5; i++) {
			String numero = String.valueOf((int) (Math.random() * 7) + 1);
			matricula = matricula.concat(numero);
		}
		return matricula;
	}

}
