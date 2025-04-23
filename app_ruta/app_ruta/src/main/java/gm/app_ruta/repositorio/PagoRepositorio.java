package gm.app_ruta.repositorio;

import gm.app_ruta.modelo.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoRepositorio extends JpaRepository<Pago, Integer> {
}
