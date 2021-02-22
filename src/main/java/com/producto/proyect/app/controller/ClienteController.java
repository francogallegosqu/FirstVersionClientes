package com.producto.proyect.app.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.producto.proyect.app.models.entity.Cliente;
import com.producto.proyect.app.models.service.IClienteService;

@Controller
@SessionAttributes("cliente")
public class ClienteController {
	
	@Autowired
	//@Qualifier("clienteDaoJPA")
	private IClienteService clienteService;

	
	
	
	@RequestMapping(value = "/listar",method=RequestMethod.GET)
	public String listar(Model model) {
		model.addAttribute("titulo", "Listado de clientes");
		model.addAttribute("clientes", clienteService.findAll());
		return "listar";
	}
	
	@RequestMapping(value = "/form")
	//Aca se le puede pasar un model o tambien Map
	public String crear(Map<String, Object> model) {
		Cliente cliente =new Cliente();
		model.put("cliente", cliente);
		model.put("titulo", "Formulario de Cliente");
		return "form";
	}

	//Tenmos que anotar con Valid para terminar la validacion
	@RequestMapping(value = "/form",method=RequestMethod.POST)
	public String guardar(@Valid Cliente cliente, BindingResult result,Model model,SessionStatus status) {
		if(result.hasErrors()) {
			//El cliente lo pasa de forma automatica porque tiene el mismo nombre
			//"cliente" pero si no es asi debes escribir
			//@ModelAttribute("cliente") al costado de Cliente cliente
			model.addAttribute("titulo", "Formulario de Cliente");
			return "form";
		}
		clienteService.save(cliente);;
		status.setComplete();
		return "redirect:listar";
	}
	
	
	@RequestMapping(value = "/form/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model) {
		Cliente cliente=null;
		if(id>0) {
			cliente=clienteService.findOne(id);
		}else {
			return "redirect:/listar";
		}
		model.put("cliente", cliente);
		return "form";
	}
	
	
	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id) {
		
		if(id>0) {
			clienteService.delete(id);
		}
		return "redirect:/listar";
	}
	
}
