package gm.app_ruta.servicio;

import gm.app_ruta.modelo.Cliente;
import gm.app_ruta.repositorio.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ClienteServicio implements IClienteServicio{
    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Override
    public List<Cliente> listarClientes() {
        var clientes = clienteRepositorio.findAll();
        return clientes;
    }

    @Override
    public Cliente buscarClienteporID(Integer idCliente) {
        var articulo= clienteRepositorio.findById(idCliente).orElse(null);
        return articulo;
    }

    @Override
    public void modificarCliente(Cliente cliente) {
        clienteRepositorio.save(cliente);

    }

    @Override
    public void eliminarCliente(Cliente cliente) {
        clienteRepositorio.delete(cliente);
    }
}
