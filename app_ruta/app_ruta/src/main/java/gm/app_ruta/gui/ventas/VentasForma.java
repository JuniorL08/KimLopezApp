package gm.app_ruta.gui.ventas;

import gm.app_ruta.modelo.Cliente;
import gm.app_ruta.modelo.Pago;
import gm.app_ruta.modelo.Venta;
import gm.app_ruta.servicio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

@Component
public class VentasForma extends JFrame{
    private JPanel panelPrincipal;
    private JCheckBox nuevoClienteCheckBox;
    private JPanel panelPrimario;
    private JPanel panelSecundario;
    private JButton volverAVentasButton;
    private JTextField nombreText;
    private JTextField celularText;
    private JTextField direccionText;
    private JTextField sectorText;
    private JTextField referenciaText;
    private JTextField idArticuloText;
    private JTextField inicialTexto;
    private JTextField vendedorTexto;
    private JButton realizarVentaButton;
    private JTextField idClienteText;
    private JTextField idArticuloText2;
    private JTextField vendedorText;
    private JButton realizarVentaButton1;
    private JTextField diaCobroText;
    private JCheckBox contadoCheckBox;
    private JTextField inicialText2;
    IVentaServicio ventaServicio;
    IClienteServicio clienteServicio;
    IArticuloServicio articuloServicio;
    IPagoServicio pagoServicio;
    private Integer idClienteStr;
    private Integer idArticuloStr;
    private String contado;

    @Autowired
    public VentasForma(VentaServicio ventaServicio, PagoServicio pagoServicio,
                       ClienteServicio clienteServicio,ArticuloServicio articuloServicio){
        this.ventaServicio = ventaServicio;
        this.clienteServicio = clienteServicio;
        this.articuloServicio = articuloServicio;
        this.pagoServicio= pagoServicio;
        this.contado = "";

        iniciarForma();
        volverAVentasButton.addActionListener(e -> {
            var inicio= new VentasInicio(articuloServicio,clienteServicio,ventaServicio,pagoServicio);
            inicio.setVisible(true);
            this.dispose();
        });
        nuevoClienteCheckBox.addItemListener(e -> {
            if(e.getStateChange() == e.SELECTED){
                panelPrimario.setVisible(true);
                panelSecundario.setVisible(false);
            }
            else {
                panelSecundario.setVisible(true);
                panelPrimario.setVisible(false);
            }
        });
        contadoCheckBox.addItemListener(e -> {
            if(e.getStateChange() == e.SELECTED)
                this.contado = "C";
        });
        realizarVentaButton.addActionListener(e -> ventaNuevoCliente());
        realizarVentaButton1.addActionListener(e -> ventaClienteExistente());
    }
    private void iniciarForma(){
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900,700);
        setLocationRelativeTo(null);
        panelPrimario.setVisible(false);
        panelSecundario.setVisible(false);
    }

    private void ventaNuevoCliente(){
        if(nombreText.getText().isEmpty())
            mostrarMensaje("Proporciona un nombre");
        if (celularText.getText().isEmpty())
            mostrarMensaje("Proporciona un celular");
        if(direccionText.getText().isEmpty())
            mostrarMensaje("Proporciona la dirección");
        if(sectorText.getText().isEmpty())
            mostrarMensaje("Proporciona el sector");
        if(referenciaText.getText().isEmpty())
            mostrarMensaje("Proporciona una referencia");
        if(idArticuloText.getText().isEmpty())
            mostrarMensaje("Proporciona el id del articulo");
        if (diaCobroText.getText().isEmpty())
            mostrarMensaje("Proporciona el dia de cobro");
        if(vendedorTexto.getText().isEmpty())
            mostrarMensaje("Proporciona el vendedor");

        this.idArticuloStr = Integer.parseInt(idArticuloText.getText());
        var articulo = articuloServicio.buscarArticuloPorId(this.idArticuloStr);
        var pago = new Pago();
        var cliente= new Cliente();
        var venta = new Venta();
        double compra;

        if (articulo != null){
            var inicial = Double.parseDouble(inicialTexto.getText());
            //Seteo de los valores del objeto cliente
            cliente.setNombre(nombreText.getText());
            cliente.setCelular(celularText.getText());
            cliente.setDireccion(direccionText.getText());
            cliente.setSector(sectorText.getText());
            cliente.setReferencia(referenciaText.getText());
            cliente.setArticulos(articulo.getNombre());
            cliente.setDiaCobro(diaCobroText.getText());
            //Comprobación de si la compra es al contado o a Credito
            if(this.contado.equals("C"))
                compra = articulo.getPrecioAlContado();
            else
                compra = articulo.getPrecioCredito();
            cliente.setTotalComprado(compra);
            //Comprobación de si hay o no inicial para proceder con el registro del inicial como un pago
            if (!inicialTexto.getText().isEmpty()){
                pago.setCobrador(vendedorTexto.getText());
                pago.setNombreCliente(nombreText.getText());
                pago.setMontoPago(inicial);
                pago.setFecha(LocalDate.now().toString());
                pagoServicio.realizarPago(pago);
            }
            else
                inicial= 0.0;

            var deuda = compra -inicial;
            cliente.setTotalAdeudado((deuda));
            //Seteo de los valores del objeto venta
            venta.setVendedor(vendedorTexto.getText());
            venta.setNombreCliente(nombreText.getText());
            venta.setArticulo(articulo.getNombre());
            venta.setMontoVenta(compra);
            venta.setFecha(LocalDate.now().toString());
            //Creación de los objetos venta y cliente
            ventaServicio.agregarVenta(venta);
            clienteServicio.modificarCliente(cliente);
            mostrarMensaje("""
                    La venta ha sido registrada.
                    Información de nuevo cliente:\s"""+ cliente);
            limpiarFormNuevo();
        }
        else
            mostrarMensaje("El ID del Articulo no existe, favor ingresar un ID correcto");
    }

    private void ventaClienteExistente(){
        if (idClienteText.getText().isEmpty())
            mostrarMensaje("Proporcione el ID del cliente");
        if(idArticuloText2.getText().isEmpty())
            mostrarMensaje("Proporcione el ID del articulo");
        if(vendedorText.getText().isEmpty())
            mostrarMensaje("Proporcione el vendedor");

        this.idClienteStr = Integer.parseInt(idClienteText.getText());
        this.idArticuloStr = Integer.parseInt(idArticuloText2.getText());
        var cliente = clienteServicio.buscarClienteporID(this.idClienteStr);
        var articulo = articuloServicio.buscarArticuloPorId(this.idArticuloStr);
        var venta = new Venta();
        var pago = new Pago();
        var inicial = Double.parseDouble(inicialText2.getText());
        double compra;
        if(cliente != null && articulo != null){
            var montoActualCompra= cliente.getTotalComprado();
            var montoActualDeuda= cliente.getTotalAdeudado();
            var articuloActual= cliente.getArticulos();

            if(this.contado.equals("C"))
                compra= articulo.getPrecioAlContado();
            else
                compra= articulo.getPrecioCredito();

            var nuevoMontoCompra= montoActualCompra + compra;
            //Comprobación de si hay o no inicial para proceder con el registro del inicial como un pago
            if (!inicialText2.getText().isEmpty()){
                pago.setCobrador(vendedorText.getText());
                pago.setNombreCliente(cliente.getNombre());
                pago.setMontoPago(inicial);
                pago.setFecha(LocalDate.now().toString());
                pagoServicio.realizarPago(pago);
            }
            else
                inicial= 0.0;
            var nuevoMontoDeuda= montoActualDeuda + compra - inicial;
            //Modificacion de los datos del cliente
            cliente.setArticulos(articuloActual+", "+articulo.getNombre());
            cliente.setTotalComprado(nuevoMontoCompra);
            cliente.setTotalAdeudado(nuevoMontoDeuda);
            //Seteo de los valores del objeto venta
            venta.setVendedor(vendedorText.getText());
            venta.setNombreCliente(cliente.getNombre());
            venta.setArticulo(articulo.getNombre());
            venta.setMontoVenta(compra);
            venta.setFecha(LocalDate.now().toString());
            //Modificación y creación de los objetos
            ventaServicio.agregarVenta(venta);
            clienteServicio.modificarCliente(cliente);
            mostrarMensaje("La venta ha sido registrada. Nuevo monto de deuda: "+ nuevoMontoDeuda);
            limpiarForm();
        } else if (cliente == null) {
            mostrarMensaje("El ID digitado para el cliente no existe. Favor introduzca un ID correcto");
            idClienteText.requestFocusInWindow();
        }
        else
            mostrarMensaje("Los IDs de cliente y articulo son incorrectos.");
    }

    private void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(this, mensaje);
    }
    private void limpiarFormNuevo(){
        this.idArticuloStr= null;
        nombreText.setText("");
        celularText.setText("");
        direccionText.setText("");
        sectorText.setText("");
        referenciaText.setText("");
        idArticuloText.setText("");
        inicialTexto.setText("");
        diaCobroText.setText("");
        vendedorTexto.setText("");
    }

    private void limpiarForm(){
        this.idClienteStr = null;
        this.idArticuloStr= null;
        idClienteText.setText("");
        idArticuloText2.setText("");
        inicialText2.setText("");
        vendedorText.setText("");
    }
}


