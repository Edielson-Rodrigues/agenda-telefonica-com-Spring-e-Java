package com.agendaTelefonica.repository;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.agendaTelefonica.models.Contato;
import com.agendaTelefonica.models.Numero;

public interface NumeroRepository extends CrudRepository<Numero, String>{
	
	Numero findByCodigoNumero(long codigoNumero);
	ArrayList<Numero> findByContato(Contato contato); //Retorna um ArrayList com todos números que o contato possui
}
