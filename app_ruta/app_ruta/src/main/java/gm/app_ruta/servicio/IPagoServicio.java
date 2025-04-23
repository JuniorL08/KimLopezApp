package gm.app_ruta.servicio;

import gm.app_ruta.modelo.Pago;

import java.util.List;

public interface IPagoServicio {
    public List<Pago> listarPagos();
    public void realizarPago(Pago pago);
}
