package com.producto.proyect.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.producto.proyect.app.models.entity.Cliente;

public interface IClienteService {
	
	//Estamos agregando la funcion de paginador
	public Page<Cliente> findAll(Pageable pageable);
	
    public List<Cliente> findAll();
	
	public void save(Cliente cliente);
	
	public Cliente findOne(Long id);
	
	public void delete(Long id);

}
