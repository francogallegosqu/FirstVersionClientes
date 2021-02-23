package com.producto.proyect.app.models.dao;



//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.producto.proyect.app.models.entity.Cliente;

//Esto para hacer una paginacion
public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long> {
	

}
