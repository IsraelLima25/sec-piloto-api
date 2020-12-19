package com.sec.api.business;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class IdadeMenorDoze implements CalculoMensalidade {

	@Override
	public BigDecimal calcularValor() {
		return new BigDecimal(150);
	}

}
