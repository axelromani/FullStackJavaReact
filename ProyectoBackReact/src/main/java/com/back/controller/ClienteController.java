package com.back.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.back.exception.ResourceNotFoundException;
import com.back.model.Cliente;
import com.back.repository.ClienteRepository;


/* @CrossOrigin
Hace que el SERIVOR/BACKEND reciba PETICIONES DEL FRONT en este caso REACT para accede a la info de los datos
de la BD y poder desde el FRONT hacer todas las acciones como LISTAR, INSERTAR, ETC */
@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/v1")
public class ClienteController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	
	@GetMapping("/clientes")
	public List<Cliente> listarCliente(){
		
		return clienteRepository.findAll();
	}
	
	
	@GetMapping("/clientes/{id}")
	public ResponseEntity<Cliente> listarClienteId(@PathVariable Long id){
		
		Cliente cliente = clienteRepository.findById(id).
				orElseThrow(() -> new ResourceNotFoundException("El cliente con es ID no existe: " + id));
		
		return ResponseEntity.ok(cliente);
	}
	
	
	@PostMapping("/clientes")
	public Cliente guardarCliente(@RequestBody Cliente cliente) {
		
		return clienteRepository.save(cliente);
	}
	
	
	
	@PutMapping("/clientes/{id}")
	public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id, @RequestBody Cliente clienteRequest){
		
		Cliente cliente = clienteRepository.findById(id).
				orElseThrow(() -> new ResourceNotFoundException("El cliente con es ID no existe: " + id));
		
		cliente.setNombre(clienteRequest.getNombre());
		cliente.setApellido(clienteRequest.getApellido());
		cliente.setEmail(clienteRequest.getEmail());
		
		Cliente clienteUpdate = clienteRepository.save(cliente);
		
		return ResponseEntity.ok(clienteUpdate);
	}
	
	
	@DeleteMapping("/clientes/{id}")
	public ResponseEntity<Map<String, Boolean>> eliminarCliente(@PathVariable Long id){
		
		Cliente cliente = clienteRepository.findById(id).
				orElseThrow(() -> new ResourceNotFoundException("El cliente con es ID no existe: " + id));
		
		
		clienteRepository.delete(cliente);
		
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		
		return ResponseEntity.ok(response);
	}
	

}
