package gm.app_ruta.servicio;

import gm.app_ruta.modelo.Pago;
import gm.app_ruta.repositorio.PagoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PagoServicio implements IPagoServicio{
    @Autowired
    private PagoRepositorio pagoRepositorio;

    @Override
    public List<Pago> listarPagos() {
        var pagos = pagoRepositorio.findAll();
        return pagos;
    }

    @Override
    public void realizarPago(Pago pago) {
        pagoRepositorio.save(pago);
    }
}
