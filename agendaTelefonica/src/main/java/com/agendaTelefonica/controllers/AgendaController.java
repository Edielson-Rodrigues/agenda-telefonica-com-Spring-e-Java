package com.agendaTelefonica.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.agendaTelefonica.models.Contato;
import com.agendaTelefonica.models.Numero;
import com.agendaTelefonica.repository.ContatoRepository;
import com.agendaTelefonica.repository.NumeroRepository;

import jakarta.validation.Valid;


@Controller
public class AgendaController {
	
	@Autowired
	private ContatoRepository cr;
	
	@Autowired
	private NumeroRepository nr;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView minhaAgenda() {
		ModelAndView mv = new ModelAndView("agenda/agendaTelefonica");
		Iterable<Contato> contatos = cr.findAll();
		Iterable<Numero> numeros = nr.findAll();
		mv.addObject("contatos", contatos);
		mv.addObject("numeros", numeros);
		return mv;
	}
	
	@PostMapping("/salvarContato")
	public String salvarContato(@Valid Contato contato, BindingResult resultContato, @Valid Numero numero, BindingResult resultNumero) {
		if(resultContato.hasErrors() || resultNumero.hasErrors()) {
			return "redirect:/";
		}else {
			cr.save(contato);
			numero.setContato(contato);
			nr.save(numero);
			return "redirect:/";
		}
	}
	
	@GetMapping("/deletarNumero/{codigoNumero}")
	public String deletarContato(@PathVariable("codigoNumero") long codigoNumero) {
		Numero numero = nr.findByCodigoNumero(codigoNumero);
		Contato contato = numero.getContato();
		nr.delete(numero);		
		
		if(nr.findByContato(contato).size() == 0) {
			cr.delete(contato);
		}
		
		return "redirect:/";
	}
	
	@GetMapping("/deletarContato/{codigoContato}")
	public String apagarContato(@PathVariable("codigoContato") long codigoContato) {
		Contato contato = cr.findByCodigoContato(codigoContato);
		ArrayList<Numero> numeros = nr.findByContato(contato);
		
		for(Numero numero : numeros) {
			nr.delete(numero);
		}
		cr.delete(contato);
		
		return "redirect:/";
	}
	
	@GetMapping("/editarContato={codigoContato}")
	public ModelAndView editarContato(@PathVariable("codigoContato") long codigoContato) {
		ModelAndView mv = new ModelAndView("agenda/adicionarNovoNumero");	
		Contato contato = cr.findByCodigoContato(codigoContato);
		Iterable<Numero> numeros = nr.findByContato(contato);
		
		mv.addObject("contato", contato);
		mv.addObject("numeros", numeros);
		
		return mv;
	}
	
	@PostMapping("/salvarNovoNumero/{codigoContato}")
	public String salvarNovoNumero(@Valid Contato contato, BindingResult resultContato, @Valid Numero numero, BindingResult resultNumero) {
		if(resultContato.hasErrors() && resultNumero.hasErrors()) {
			return "redirect:/editarContato="+contato.getCodigoContato();
		}else if(!resultContato.hasErrors() && resultNumero.hasErrors()) {
			cr.save(contato);
			return "redirect:/editarContato="+contato.getCodigoContato();
		}else {
			cr.save(contato);
			numero.setContato(contato);
			nr.save(numero);
			return "redirect:/editarContato="+contato.getCodigoContato();
		}
	}
	
}
