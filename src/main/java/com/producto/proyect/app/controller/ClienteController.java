package com.producto.proyect.app.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.producto.proyect.app.models.entity.Cliente;
import com.producto.proyect.app.models.service.IClienteService;

@Controller
@SessionAttributes("cliente")
public class ClienteController {
	
	@Autowired
	private IClienteService clienteService;

	
	
	
	@RequestMapping(value = "/listar",method=RequestMethod.GET)
	//Estamos haciendo una modificaion en donde agregamos paginacion con  el "page"
	public String listar(@RequestParam(name="page",defaultValue = "0") int page, Model model) {
		//ese 4 es la cantidad de paginas que quiero mostrar
		Pageable pageRequest= PageRequest.of(page, 4);
		
		Page<Cliente> clientes = clienteService.findAll(pageRequest);
		model.addAttribute("titulo", "Listado de clientes");
		model.addAttribute("clientes", clienteService.findAll());
		///model.addAttribute("clientes", clientes);
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
	@RequestMapping(value = "/form",method=RequestMethod.POST)   //ese flash es para ayudar a salir mensaje en pantalla
	public String guardar(@Valid Cliente cliente, BindingResult result,Model model,RedirectAttributes flash ,SessionStatus status) {
		if(result.hasErrors()) {
			//El cliente lo pasa de forma automatica porque tiene el mismo nombre
			//"cliente" pero si no es asi debes escribir
			//@ModelAttribute("cliente") al costado de Cliente cliente
			model.addAttribute("titulo", "Formulario de Cliente");
			return "form";
		}
		String mensajeFlash= (cliente.getId()!=null)? "Cliente editado con éxito":"Cliente creado con exito";
		clienteService.save(cliente);;
		status.setComplete();
		flash.addFlashAttribute("success",mensajeFlash);
		return "redirect:listar";
	}
	
	
	@RequestMapping(value = "/form/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model,RedirectAttributes flash) {
		Cliente cliente=null;
		if(id>0) {
			cliente=clienteService.findOne(id);
			if(cliente==null) {
				flash.addFlashAttribute("error","El ID del cliente no existe en la base de datos");
				return "redirect:/listar";
			}
		}else {
			flash.addFlashAttribute("error","El ID del cliente no puede ser 0");
			return "redirect:/listar";
		}
		model.put("cliente", cliente);
		return "form";
	}
	
	
	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id ,RedirectAttributes flash) {
		
		if(id>0) {
			clienteService.delete(id);
			flash.addFlashAttribute("success","Cliente eliminado con éxito");
		}
		return "redirect:/listar";
	}
	
}
