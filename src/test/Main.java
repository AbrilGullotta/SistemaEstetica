package test;

import modelo.Cliente;
import repository.ClienteRepository;

import util.Encriptador;public class Main {

	public static void main(String[] args) {

	    Cliente cliente = new Cliente();

	    cliente.setNombre("lucia");
	    cliente.setApellido("Fernandez");
	    cliente.setDni("87654321");
	    cliente.setEmail("lucia@gmail.com");
	    cliente.setTelefono("1199988877");
	    cliente.setContrasenia("abcd");
	    cliente.setFechaNacimiento("10/10/1999");

	    ClienteRepository repository = new ClienteRepository();

	    repository.guardarCliente(cliente);

	    String hash = Encriptador.hash("1234");

	    System.out.println(hash);

	    System.out.println(
	        Encriptador.verificar("1234", hash)
	    );
	}}