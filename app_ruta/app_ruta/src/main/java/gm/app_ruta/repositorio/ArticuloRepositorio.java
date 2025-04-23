package gm.app_ruta.repositorio;

import gm.app_ruta.modelo.Articulo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticuloRepositorio extends JpaRepository<Articulo, Integer> {
}
