package gm.app_ruta.repositorio;

import gm.app_ruta.modelo.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepositorio extends JpaRepository<Venta, Integer> {
}
