package gm.app_ruta.gui.ventas;

import gm.app_ruta.servicio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class PagosHistorico extends JFrame{
    private JPanel panelPrincipal;
    private JButton volverAlInicioButton;
    private JTable pagosTabla;
    private IPagoServicio pagoServicio;
    private DefaultTableModel pagosTablaModelo;

    @Autowired
    public PagosHistorico(PagoServicio pagoServicio, VentaServicio ventaServicio,
                          ClienteServicio clienteServicio, ArticuloServicio articuloServicio){
        this.pagoServicio = pagoServicio;
        iniciarForma();
        volverAlInicioButton.addActionListener(e -> {
            var inicio = new VentasInicio(articuloServicio,clienteServicio,ventaServicio,pagoServicio);
            inicio.setVisible(true);
            this.dispose();
        });
    }

    public void iniciarForma(){
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900,700);
        setLocationRelativeTo(null);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.pagosTablaModelo = new DefaultTableModel(0,4){
            @Override
            public boolean isCellEditable(int row, int column) {return false;}
        };
        String[] cabeceros= {"ID", "Cobrador", "Nombre del cliente", "Monto pagado", "Fecha"};
        pagosTablaModelo.setColumnIdentifiers(cabeceros);
        this.pagosTabla = new JTable(pagosTablaModelo);
        this.pagosTabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listarPagos();
    }

    private void listarPagos(){
        this.pagosTablaModelo.setRowCount(0);
        var pagos = pagoServicio.listarPagos();
        pagos.forEach(pago -> {
            Object[] renglonPagos = {
                    pago.getId(),
                    pago.getCobrador(),
                    pago.getNombreCliente(),
                    pago.getMontoPago(),
                    pago.getFecha()
            };
            pagosTablaModelo.addRow(renglonPagos);
        });
    }
}
