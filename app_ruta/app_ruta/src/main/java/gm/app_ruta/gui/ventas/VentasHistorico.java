package gm.app_ruta.gui.ventas;

import gm.app_ruta.servicio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class VentasHistorico extends JFrame{
    private JPanel panelPrincipal;
    private JTable ventasTabla;
    private JButton volverAlInicioButton;
    private IVentaServicio ventaServicio;
    private DefaultTableModel tablaModeloVentas;

    @Autowired
    public VentasHistorico(VentaServicio ventaServicio, ArticuloServicio articuloServicio,
                           ClienteServicio clienteServicio, PagoServicio pagoServicio){
        this.ventaServicio = ventaServicio;
        inicioForma();
        volverAlInicioButton.addActionListener(e -> {
            var inicio = new VentasInicio(articuloServicio, clienteServicio, ventaServicio, pagoServicio);
            inicio.setVisible(true);
            this.dispose();
        });
    }

    public void inicioForma(){
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900,700);
        setLocationRelativeTo(null);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        // Se define la estructura de la tabla
        this.tablaModeloVentas= new DefaultTableModel(0,5){
            //Se desactiva la edicion de la tabla
            @Override
            public boolean isCellEditable(int row, int column) {return false;}
        };
        String[] cabeceros= {"ID", "Vendedor", "Nombre del Cliente", "Articulo Vendido", "Monto de la venta", "Fecha de Venta"};
        tablaModeloVentas.setColumnIdentifiers(cabeceros);
        //Se crea la tabla con el modelo establecido en el objeto tabla modelo Ventas
        this.ventasTabla = new JTable(tablaModeloVentas);
        this.ventasTabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //Cargar el historico de ventas
        listarVentas();
    }

    public void listarVentas(){
        this.tablaModeloVentas.setRowCount(0);
        var ventas = ventaServicio.listarVentas();
        ventas.forEach(venta -> {
            Object[] renglonArticulos= {
                    venta.getId(),
                    venta.getVendedor(),
                    venta.getNombreCliente(),
                    venta.getArticulo(),
                    venta.getMontoVenta(),
                    venta.getFecha()
            };
            this.tablaModeloVentas.addRow(renglonArticulos);
        });
    }
}
