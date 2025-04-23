package gm.app_ruta.servicio;

import gm.app_ruta.modelo.Venta;
import gm.app_ruta.repositorio.VentaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VentaServicio implements IVentaServicio{
    @Autowired
    private VentaRepositorio ventaRepositorio;

    @Override
    public List<Venta> listarVentas() {
        var ventas= ventaRepositorio.findAll();
        return ventas;
    }

    @Override
    public void agregarVenta(Venta venta) {
        ventaRepositorio.save(venta);
    }
}
