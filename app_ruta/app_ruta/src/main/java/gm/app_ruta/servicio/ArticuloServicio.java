package gm.app_ruta.servicio;

import gm.app_ruta.modelo.Articulo;
import gm.app_ruta.repositorio.ArticuloRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticuloServicio implements IArticuloServicio{

    @Autowired
    private ArticuloRepositorio articuloRepositorio;

    @Override
    public List<Articulo> listarArticulos() {
        var articulos= articuloRepositorio.findAll();
        return articulos;
    }

    @Override
    public Articulo buscarArticuloPorId(Integer idArticulo) {
        var articulo= articuloRepositorio.findById(idArticulo).orElse(null);
        return articulo;
    }

    @Override
    public void agregarArticulo(Articulo articulo) {
        articuloRepositorio.save(articulo);
    }

    @Override
    public void eliminarArticulo(Articulo articulo) {
        articuloRepositorio.delete(articulo);
    }
}
