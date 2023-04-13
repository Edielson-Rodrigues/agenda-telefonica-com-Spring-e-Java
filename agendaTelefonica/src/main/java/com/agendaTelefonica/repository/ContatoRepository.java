package com.agendaTelefonica.repository;

import org.springframework.data.repository.CrudRepository;

import com.agendaTelefonica.models.Contato;

public interface ContatoRepository extends CrudRepository<Contato, String>{

	Contato findByCodigoContato(long condigoContato);
	
}
