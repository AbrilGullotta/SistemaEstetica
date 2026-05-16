package test;

import modelo.Cliente;
import repository.ClienteRepository;

public class Main {

	public static void main(String[] args) {

		Cliente cliente = new Cliente();

		cliente.setNombre("Lucia");
		cliente.setApellido("Fernandez");
		cliente.setDni(87654321);
		cliente.setMail("lucia@gmail.com");
		cliente.setTelefono("1199988877");
		cliente.setUsuario("luciaf");
		cliente.setContrasenia("abcd");
		cliente.setFechaNacimiento("10/10/1999");

		ClienteRepository repository = new ClienteRepository();

		repository.guardarCliente(cliente);

	}
}