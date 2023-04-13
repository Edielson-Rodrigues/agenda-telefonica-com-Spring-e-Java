package com.agendaTelefonica.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Numero {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long codigoNumero;
	
	@NotEmpty
	private String digitos;
	
	@ManyToOne
	private Contato contato;

	public String getDigitos() {
		return digitos;
	}

	public void setDigitos(String digitos) {
		this.digitos = digitos;
	}

	public Contato getContato() {
		return contato;
	}

	public void setContato(Contato contato) {
		this.contato = contato;
	}

	public long getCodigoNumero() {
		return codigoNumero;
	}

	public void setCodigoNumero(long codigoNumero) {
		this.codigoNumero = codigoNumero;
	}
}
