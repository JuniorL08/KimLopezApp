package gm.app_ruta.servicio;

import gm.app_ruta.modelo.Cliente;

import java.util.List;

public interface IClienteServicio {
    public List<Cliente> listarClientes();
    public Cliente buscarClienteporID(Integer idCliente);
    public void modificarCliente(Cliente cliente);
    public void eliminarCliente(Cliente cliente);
}
