package com.sec.api.business;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class IdadeMaiorIgualDoze implements CalculoMensalidade {
	
	public BigDecimal calcularValor() {
		return new BigDecimal(700);
	}

}
