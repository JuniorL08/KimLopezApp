package gm.app_ruta.servicio;

import gm.app_ruta.modelo.Articulo;

import java.util.List;

public interface IArticuloServicio {
    public List<Articulo> listarArticulos();
    public Articulo buscarArticuloPorId(Integer idArticulo);
    public void agregarArticulo(Articulo articulo);
    public void eliminarArticulo(Articulo articulo);
}
