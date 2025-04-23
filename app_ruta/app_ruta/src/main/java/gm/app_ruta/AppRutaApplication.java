package gm.app_ruta;

import gm.app_ruta.modelo.Articulo;
import gm.app_ruta.modelo.Cliente;
import gm.app_ruta.modelo.Venta;
import gm.app_ruta.servicio.IArticuloServicio;
import gm.app_ruta.servicio.IClienteServicio;
import gm.app_ruta.servicio.IVentaServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.time.LocalDate;
import java.util.Scanner;

@SpringBootApplication
public class AppRutaApplication implements CommandLineRunner {
	@Autowired
	private IArticuloServicio articuloServicio;
	@Autowired
	private IClienteServicio clienteServicio;
	@Autowired
	private IVentaServicio ventaServicio;

	private static final Logger logger= LoggerFactory.getLogger(AppRutaApplication.class);
	private String menuArticulo= """
				1. Inventario de Articulos
				2. Buscar Articulo por Id
				3. Agregar Articulo
				4. Modificar Articulo
				5. Eliminar Articulo
				Seleccione la opcion a realizar:\s""",
	menuInicial= """ 
				*** Kim Lopez Sweet Home ***
				1. Articulos
				2. Clientes
				3. Ventas
				4. Salir
				Seleccione la opcion a realizar:\s""",
	menuClientes= """
				1. Listado de Clientes
				2. Buscar Cliente por ID
				3. Actualizar información del Cliente
				4. Eliminar Cliente
				Seleccione la opcion a realizar:\s""",
	menuVentas= """
				1.Listado de ventas
				2. Realizar una venta
				3. Registrar pago de un cliente
				Seleccione la opcion a realizar:\s""";

	public static void main(String[] args) {
		SpringApplication.run(AppRutaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		appRuta();
	}

	public void appRuta(){
		var salir= false;
		var consola = new Scanner(System.in);
		while (!salir){
			try {
				var opcion = mostrarMenu(menuInicial,consola);
				salir= ejecutarOpcionesIniciales(opcion);

			}catch (Exception e){
                logger.info("Ocurrio un error en la aplicacion: {}", e.getMessage());
			}
			finally {
				logger.info("\n");
			}
		}
	}

	private int mostrarMenu(String mensaje,Scanner consola){
		logger.info(mensaje);
		return Integer.parseInt(consola.nextLine());
	}
	private boolean ejecutarOpcionesIniciales(int opcion){
		var salir = false;
		switch (opcion){
			case 1 -> ejecutarOpcionesArticulos();
			case 2-> ejecutarOpcionesClientes();
			case 3-> ejecutarOpcionesVentas();
			case 4 ->{
				logger.info("Gracias por usar nuestra APP. Hasta pronto!!!");
				salir = true;
			}
			default -> logger.info("Opcion invalida.");
		}
		return salir;
	}

	private void ejecutarOpcionesArticulos(){
		var consola= new Scanner(System.in);
		var opcion = mostrarMenu(menuArticulo, consola);
		switch (opcion){
			case 1 ->{
				logger.info("--- Inventario de Artículos ---");
				var articulos= articuloServicio.listarArticulos();
				articulos.forEach(articulo -> logger.info(articulo.toString()));
			}
			case 2 ->{
				logger.info("--- Buscar articulo por ID ---");
				logger.info("Ingrese el ID del articulo: ");
				var idArticulo= Integer.parseInt(consola.nextLine());
				var articulo= articuloServicio.buscarArticuloPorId(idArticulo);
				logger.info(articulo.toString());
			}
			case 3 -> {
				logger.info("--- Agregar Articulo al Inventario ---");
				logger.info("Nombre del articulo: ");
				var nombre= consola.nextLine();
				logger.info("Marca del articulo: ");
				var marca= consola.nextLine();
				logger.info("Modelo del articulo: ");
				var modelo= consola.nextLine();
				logger.info("Precio de compra del articulo: ");
				var precioCompra= Double.parseDouble(consola.nextLine());
				logger.info("Precio del articulo al contado: ");
				var precioAlcontado= Double.parseDouble(consola.nextLine());
				logger.info("Precio del articulo a credito: ");
				var precioCredito= Double.parseDouble(consola.nextLine());
				//Creacion del objeto
				var articulo = new Articulo();
				articulo.setNombre(nombre);
				articulo.setMarca(marca);
				articulo.setModelo(modelo);
				articulo.setPrecioCompra(precioCompra);
				articulo.setPrecioAlContado(precioAlcontado);
				articulo.setPrecioCredito(precioCredito);
				//Metodo para agregar el objeto creado a la BD
				articuloServicio.agregarArticulo(articulo);
				logger.info("El articulo ha sido creado. "+ articulo);
			}
			case 4 ->{
				logger.info("--- Modificar los prrecios del articulo en inventario ---");
				logger.info("Ingrese el id del articulo: ");
				var idArticulo= Integer.parseInt(consola.nextLine());
				var articulo = articuloServicio.buscarArticuloPorId(idArticulo);
				if(articulo != null){
					logger.info("Nuevo precio de compra del articulo: ");
					var precioCompra = Double.parseDouble(consola.nextLine());
					articulo.setPrecioCompra(precioCompra);
					logger.info("Nuevo precio de venta cash del articulo: ");
					var precioAlContado = Double.parseDouble(consola.nextLine());
					articulo.setPrecioCompra(precioCompra);
					logger.info("Nuevo precio de venta a credito del articulo: ");
					var precioCredito = Double.parseDouble(consola.nextLine());
					articulo.setPrecioCompra(precioCompra);
					articulo.setPrecioAlContado(precioAlContado);
					articulo.setPrecioCredito(precioCredito);
					articuloServicio.agregarArticulo(articulo);
					logger.info("Precios actualizados: "+ articulo);
				}
				else
					logger.info("No se encontró artículo con ID:" + idArticulo);
			}
			case 5 ->{
				logger.info("--- Eliminar Articulo de Inventario ---");
				logger.info("Ingrese del ID del articulo: ");
				var idArticulo= Integer.parseInt(consola.nextLine());
				var articulo= articuloServicio.buscarArticuloPorId(idArticulo);
				if(articulo != null) {
					articuloServicio.eliminarArticulo(articulo);
					logger.info("Articulo con " + idArticulo+ " eliminado.");
				}
				else
					logger.info("No se encontró articulo con ID " +idArticulo );

			}
			default -> logger.info("Opcion invalida.");
		}
	}
	private void ejecutarOpcionesClientes(){
		var consola = new Scanner(System.in);
		var opcion = mostrarMenu(menuClientes, consola);
		switch (opcion){
			case 1->{
				logger.info("--- Listado de clientes ---");
				var clientes = clienteServicio.listarClientes();
				clientes.forEach(cliente -> logger.info(cliente.toString()));
			}
			case 2->{
				logger.info("--- Buscar Cliente por ID ---");
				logger.info("Ingrese el ID del cliente: ");
				var idCliente= Integer.parseInt(consola.nextLine());
				var cliente= clienteServicio.buscarClienteporID(idCliente);
				logger.info(cliente.toString());
			}
			case 3 -> {
				logger.info("--- Actualizar informacion del cliente ---");
				logger.info("Ingrese el id del cliente: ");
				var idCliente= Integer.parseInt(consola.nextLine());
				var cliente = clienteServicio.buscarClienteporID(idCliente);
				if(cliente != null){
					logger.info("Nombre (Si no desea cambiarlo, ingrese el mismo nombre): ");
					var nombre= consola.nextLine();
					logger.info("Celular (Si no desea cambiarlo, ingrese el mismo celular): ");
					var celular= consola.nextLine();
					logger.info("Dirección (Si no desea cambiarlo, ingrese la misma dirección): ");
					var direccion= consola.nextLine();
					logger.info("Sector (Si no desea cambiarlo, ingrese el mismo sector):");
					var sector= consola.nextLine();
					logger.info("Referencia (Si no desea cambiarlo, ingrese el mismo sector):");
					var referencia= consola.nextLine();
					cliente.setNombre(nombre);
					cliente.setCelular(celular);
					cliente.setDireccion(direccion);
					cliente.setSector(sector);
					cliente.setReferencia(referencia);
					clienteServicio.modificarCliente(cliente);
					logger.info("La información ha sido actualizada. "+ cliente);
				}
				else
					logger.info("No existe el cliente con ID: " +idCliente);
			}
			case 4 ->{
				logger.info("--- Eliminar cliente ---");
				logger.info("Ingrese el id del cliente");
				var idCliente = Integer.parseInt(consola.nextLine());
				var cliente= clienteServicio.buscarClienteporID(idCliente);
				clienteServicio.eliminarCliente(cliente);
				logger.info("El cliente ID "+idCliente+ " fue eliminado");
			}
		}
	}
	private void ejecutarOpcionesVentas(){
		var consola= new Scanner(System.in);
		var opcion= mostrarMenu(menuVentas,consola);
		switch (opcion){
			case 1 ->{
				logger.info("--- Listado de Ventas Realizadas");
				var ventas= ventaServicio.listarVentas();
				ventas.forEach(venta -> logger.info(venta.toString()));
			}
			case 2 ->{;
				logger.info("--- Realizar Venta ---");
				logger.info("Es nuevo cliente (Si/No)? ");
				var respuesta= consola.nextLine().toLowerCase().strip();
				if(respuesta.equals("si")){
					logger.info("Nombre: ");
					var nombre= consola.nextLine();
					logger.info("Celular: ");
					var celular= consola.nextLine();
					logger.info("Dirección: ");
					var direccion= consola.nextLine();
					logger.info("Sector:");
					var sector= consola.nextLine();
					logger.info("Referencia:");
					var referencia= consola.nextLine();
					logger.info("ID del articulo: ");
					var idArticulo= Integer.parseInt(consola.nextLine());
					logger.info("Dia de cobro: ");
					var diaCobro= consola.nextLine();
					logger.info("Monto del inicial: ");
					var inicial= Double.parseDouble(consola.nextLine());
					logger.info("Vendedor");
					var vendedor = consola.nextLine();
					var articulo = articuloServicio.buscarArticuloPorId(idArticulo);
					var cliente= new Cliente();
					var venta= new Venta();
					var montoArticulo = articulo.getPrecioCredito();
					var montoDeuda= montoArticulo - inicial;
					//Creacion de cliente
					cliente.setNombre(nombre);
					cliente.setCelular(celular);
					cliente.setDireccion(direccion);
					cliente.setSector(sector);
					cliente.setReferencia(referencia);
					cliente.setArticulos(articulo.getNombre());
					cliente.setDiaCobro(diaCobro);
					cliente.setTotalComprado(montoArticulo);
					cliente.setTotalAdeudado(montoDeuda);
					clienteServicio.modificarCliente(cliente);
					//Creacion de Venta realizada
					venta.setVendedor(vendedor);
					venta.setNombreCliente(nombre);
					venta.setArticulo(articulo.getNombre());
					venta.setMontoVenta(montoArticulo);
					venta.setFecha(LocalDate.now().toString());
					ventaServicio.agregarVenta(venta);
					logger.info("La venta se ha realizada con exito. "+ venta);
					logger.info("El cliente fue creado correctamente. "+cliente);
				}
				else if(respuesta.equals("no")) {
					logger.info("Digite el ID del cliente: ");
					var idCliente = Integer.parseInt(consola.nextLine());
					var cliente= clienteServicio.buscarClienteporID(idCliente);

					logger.info("Digite el ID del articulo: ");
					var idArticulo= Integer.parseInt(consola.nextLine());
					logger.info("Vendedor: ");
					var vendedor = consola.nextLine();
					var articulo = articuloServicio.buscarArticuloPorId(idArticulo);
					var montoActualCompra= cliente.getTotalComprado();
					var montoActualDeuda= cliente.getTotalAdeudado();
					var articuloActual= cliente.getArticulos();
					var nuevoMontoCompra= montoActualCompra + articulo.getPrecioCredito();
					var nuevoMontoDeuda= montoActualDeuda + articulo.getPrecioCredito();
					//Modificacion de los datos del cliente
					cliente.setArticulos(articuloActual+", "+articulo.getNombre());
					cliente.setTotalComprado(nuevoMontoCompra);
					cliente.setTotalAdeudado(nuevoMontoDeuda);
					clienteServicio.modificarCliente(cliente);
					//Creacion de la venta
					var venta = new Venta();
					venta.setVendedor(vendedor);
					venta.setNombreCliente(cliente.getNombre());
					venta.setArticulo(articulo.getNombre());
					venta.setMontoVenta(articulo.getPrecioCredito());
					venta.setFecha(LocalDate.now().toString());
					ventaServicio.agregarVenta(venta);
					logger.info("La venta se ha realizado con exito. "+venta);
				}
				else
					logger.info("La respuesta no es valida. Digite una opción valida.");
			}
		}
	}
}
