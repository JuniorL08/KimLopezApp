package gm.app_ruta.gui.ventas;

import gm.app_ruta.modelo.Pago;
import gm.app_ruta.servicio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

@Component
public class PagosForma extends JFrame{
    private JButton volverAVentasButton;
    private JTextField idClienteTexto;
    private JTextField cobradorTexto;
    private JTextField montoPagoTexto;
    private JPanel panelPrincipal;
    private JButton realizarPagoButton;
    IPagoServicio pagoServicio;
    IClienteServicio clienteServicio;
    private Integer idClienteStr;

    @Autowired
    public PagosForma(PagoServicio pagoServicio, VentaServicio ventaServicio,
                      ClienteServicio clienteServicio, ArticuloServicio articuloServicio){
        this.pagoServicio = pagoServicio;
        this.clienteServicio = clienteServicio;
        iniciarForma();
        volverAVentasButton.addActionListener(e -> {
            var inicio = new VentasInicio(articuloServicio,clienteServicio,ventaServicio,pagoServicio);
            inicio.setVisible(true);
            this.dispose();
        });
        realizarPagoButton.addActionListener(e -> realizarPago());
    }

    public void iniciarForma(){
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900,700);
        setLocationRelativeTo(null);
    }

    private void realizarPago(){
        this.idClienteStr = Integer.parseInt(idClienteTexto.getText());
        var cliente = clienteServicio.buscarClienteporID(idClienteStr);
        var cobrador = cobradorTexto.getText();
        var montoPago= Double.parseDouble(montoPagoTexto.getText());
        if(cliente != null){
            var pago = new Pago();
            var montoActual = cliente.getTotalAdeudado();
            var nuevoMontoDeuda= montoActual - montoPago;
            pago.setCobrador(cobrador);
            pago.setNombreCliente(cliente.getNombre());
            pago.setMontoPago(montoPago);
            pago.setFecha(LocalDate.now().toString());
            cliente.setTotalAdeudado(nuevoMontoDeuda);
            pagoServicio.realizarPago(pago);
            clienteServicio.modificarCliente(cliente);
            mostrarMensaje("""
                    El pago ha sido registrado.
                    El cliente actualmente debe: RD$\s"""+cliente.getTotalAdeudado());
            limpiarForm();
        }
        else {
            mostrarMensaje("El cliente con ID " + this.idClienteStr + " no existe. Favor introduzca un ID valido");
            limpiarForm();
        }
    }

    private void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(this,mensaje);
    }
    private void limpiarForm(){
        this.idClienteStr = null;
        idClienteTexto.setText("");
        cobradorTexto.setText("");
        montoPagoTexto.setText("");
    }
}
