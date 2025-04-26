package gm.app_ruta.gui;

import gm.app_ruta.modelo.Articulo;
import gm.app_ruta.servicio.ArticuloServicio;
import gm.app_ruta.servicio.ClienteServicio;
import gm.app_ruta.servicio.IArticuloServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Component
public class ArticuloForma extends JFrame{
    private JPanel panelPrincipalArticulo;
    private JTextField articuloNombreTexto;
    private JTextField articuloMarcaTexto;
    private JTextField articuloModeloTexto;
    private JTextField articuloPrecioCompraTexto;
    private JTextField articuloPrecioCashTexto;
    private JTextField precioCredito;
    private JTable articuloTabla;
    private JButton guardarButton;
    private JButton eliminarButton;
    private JButton limpiarButton;
    private JButton volverAlInicioButton;
    IArticuloServicio articuloServicio;
    private DefaultTableModel tablaModeloArticulo;
    private Integer idArticulo;

    @Autowired
    public ArticuloForma(ArticuloServicio articuloServicio, ClienteServicio clienteServicio){
        this.articuloServicio = articuloServicio;
        iniciarForma();
        volverAlInicioButton.addActionListener(e -> {
            var inicio= new InicioForma(articuloServicio, clienteServicio);
            inicio.setVisible(true);
            this.dispose();
        });
        guardarButton.addActionListener(e -> guardarArticulo());
        articuloTabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarArticuloSeleccionado();
            }
        });
        limpiarButton.addActionListener(e -> limpiarFormulario());
        eliminarButton.addActionListener(e -> eliminarCliente());
    }

    private void iniciarForma(){
        setContentPane(panelPrincipalArticulo);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900,700);
        setLocationRelativeTo(null);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.tablaModeloArticulo = new DefaultTableModel(0, 6){
            //Se desactivo que la tabla pueda ser editable
            public boolean isCellEditable(int row, int column ){return false;}
        };
        String[] cabeceros= {"Id", "Nombre", "Marca", "Modelo", "Precio de Compra", "Precio al Cash", "Precio a Credito"};
        this.tablaModeloArticulo.setColumnIdentifiers(cabeceros);
        this.articuloTabla = new JTable(tablaModeloArticulo);
        //Restringimos la tabla a que solo pueda seleccionar una fila
        this.articuloTabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //Cargar Listado de clientes
        listarArticulos();
    }
    private void listarArticulos(){
        this.tablaModeloArticulo.setRowCount(0);
        var articulos = this.articuloServicio.listarArticulos();
        articulos.forEach(articulo -> {
            Object[] renglonArticulo= {
                    articulo.getId(),
                    articulo.getNombre(),
                    articulo.getMarca(),
                    articulo.getModelo(),
                    articulo.getPrecioCompra(),
                    articulo.getPrecioAlContado(),
                    articulo.getPrecioCredito()
            };
            this.tablaModeloArticulo.addRow(renglonArticulo);
        });
    }
    private void guardarArticulo(){
        if(articuloNombreTexto.getText().equals("")){
            mostrarMensaje("Proporciona un nombre");
            articuloNombreTexto.requestFocusInWindow();
            return;
        }
        if(articuloNombreTexto.getText().equals("")){
            mostrarMensaje("Proporciona un nombre");
            articuloNombreTexto.requestFocusInWindow();
            return;
        }
        if(articuloMarcaTexto.getText().equals("")){
            mostrarMensaje("Proporciona la marca");
            articuloMarcaTexto.requestFocusInWindow();
            return;
        }
        if(articuloPrecioCompraTexto.getText().equals("")){
            mostrarMensaje("Proporciona el precio de compra");
            articuloPrecioCompraTexto.requestFocusInWindow();
            return;
        }
        if(articuloPrecioCashTexto.getText().equals("")){
            mostrarMensaje("Proporciona el precio al cash");
            articuloPrecioCashTexto.requestFocusInWindow();
            return;
        }
        if(precioCredito.getText().equals("")){
            mostrarMensaje("Proporciona el precio a Credito");
            precioCredito.requestFocusInWindow();
            return;
        }
        //Recuperar los valores del formulario
        var nombre= articuloNombreTexto.getText();
        var marca= articuloMarcaTexto.getText();
        var modelo= articuloModeloTexto.getText();
        var precioCompra= Double.parseDouble(articuloPrecioCompraTexto.getText());
        var precioCash= Double.parseDouble(articuloPrecioCompraTexto.getText());
        var precioCreditoP= Double.parseDouble(precioCredito.getText());
        var articulo = new Articulo(this.idArticulo, nombre, marca, modelo,precioCompra,precioCash,precioCreditoP);
        this.articuloServicio.agregarArticulo(articulo);//Se inserta el articulo
        if(this.idArticulo == null){
            mostrarMensaje("""
                El articulo ha sido creado.
                Info del nuevo articulo:\s"""+ articulo);
        } else{
            mostrarMensaje("""
                    El articulo ha sido modificado.
                    Nueva Info:\s""" +articulo);
        }
        limpiarFormulario();
        listarArticulos();
    }
    private void cargarArticuloSeleccionado(){
        var renglon = articuloTabla.getSelectedRow();
        if(renglon != -1){
            var id = articuloTabla.getModel().getValueAt(renglon, 0).toString();
            this.idArticulo= Integer.parseInt(id);
            var nombre =articuloTabla.getModel().getValueAt(renglon, 1).toString();
            this.articuloNombreTexto.setText(nombre);
            var marca= articuloTabla.getModel().getValueAt(renglon, 2).toString();
            this.articuloMarcaTexto.setText(marca);
            var modelo= articuloTabla.getModel().getValueAt(renglon, 3).toString();
            this.articuloModeloTexto.setText(modelo);
            var precioCompra= articuloTabla.getModel().getValueAt(renglon, 4).toString();
            this.articuloPrecioCompraTexto.setText(precioCompra);
            var precioCash = articuloTabla.getModel().getValueAt(renglon, 5).toString();
            this.articuloPrecioCashTexto.setText(precioCash);
            var precioCreditoP = articuloTabla.getModel().getValueAt(renglon, 6).toString();
            this.precioCredito.setText(precioCreditoP);
        }
    }
    private void eliminarCliente(){
        var renglon = articuloTabla.getSelectedRow();
        if(renglon != -1){
            var idArticuloStr = articuloTabla.getModel().getValueAt(renglon, 0).toString();
            this.idArticulo = Integer.parseInt(idArticuloStr);
            var articulo= new Articulo();
            articulo.setId(this.idArticulo);
            this.articuloServicio.eliminarArticulo(articulo);
            mostrarMensaje("Cliente con ID "+ this.idArticulo+ "eliminado");
            limpiarFormulario();
            listarArticulos();
        }
    }

    private void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(this, mensaje);
    }
    private void limpiarFormulario(){
        articuloNombreTexto.setText("");
        articuloMarcaTexto.setText("");
        articuloModeloTexto.setText("");
        articuloPrecioCompraTexto.setText("");
        articuloPrecioCashTexto.setText("");
        precioCredito.setText("");
        this.idArticulo = null;
        //Deseleccionamos el registro seleccionado de la tabla
        this.articuloTabla.getSelectionModel().clearSelection();
    }
}
