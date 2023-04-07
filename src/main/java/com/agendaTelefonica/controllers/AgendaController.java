package com.agendaTelefonica.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.agendaTelefonica.models.Contato;
import com.agendaTelefonica.repository.ContatoRepository;

import jakarta.validation.Valid;

@Controller
public class AgendaController {
	
	@Autowired
	private ContatoRepository cr;
	

	
	@RequestMapping(value="/minhaAgenda", method=RequestMethod.GET)
	public ModelAndView minhaAgenda() {
		ModelAndView mv = new ModelAndView("agenda/agendaTelefonica");
		Iterable<Contato> contatos = cr.findAll();
		mv.addObject("contatos", contatos);
		return mv;
	}
	
	@RequestMapping(value="/minhaAgenda", method=RequestMethod.POST)
	public String salvarContato(@Valid Contato contato, BindingResult result) {
		if(!result.hasErrors() && !contato.getNumero().contains("_")) {
			cr.save(contato);
			return "redirect:/minhaAgenda";
		}
		else {
			return "redirect:/minhaAgenda";
		}
		
	}
	
	@RequestMapping("/{codigo}")
	public String apagarContato(Contato contato) {
		cr.delete(contato);
		return "redirect:/minhaAgenda";
	}
	

}
