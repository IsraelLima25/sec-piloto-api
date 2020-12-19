package com.sec.api.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.sec.api.business.CalculoMensalidade;

@Service
public class CalculadorMensalidade {
	
	public BigDecimal calcular(CalculoMensalidade calculo) {
		return calculo.calcularValor();
	}
}
