package gm.app_ruta.servicio;

import gm.app_ruta.modelo.Venta;

import java.util.List;

public interface IVentaServicio {
    public List<Venta> listarVentas();
    public void agregarVenta(Venta venta);
}
