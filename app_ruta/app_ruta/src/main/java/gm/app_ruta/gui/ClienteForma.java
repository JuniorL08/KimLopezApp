package gm.app_ruta.gui;

import gm.app_ruta.modelo.Cliente;
import gm.app_ruta.servicio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@Component
public class ClienteForma extends JFrame{
    private JPanel panelPrincipalCliente;
    private JTextField nombreTexto;
    private JTextField celularTexto;
    private JTextField direccionTexto;
    private JTextField sectorTexto;
    private JTextField referenciaTexto;
    private JButton guardarButton;
    private JButton eliminarButton;
    private JButton limpiarButton;
    private JButton volverAInicioButton;
    private JTable clientesTabla;
    private DefaultTableModel tablaModeloCliente;
    IClienteServicio clienteServicio;
    private Integer idCliente;

    @Autowired
    public ClienteForma(ArticuloServicio articuloServicio, ClienteServicio clienteServicio,
                        VentaServicio ventaServicio, PagoServicio pagoServicio){
        this.clienteServicio = clienteServicio;
        iniciarForma();
        volverAInicioButton.addActionListener(e -> {
            var inicio = new InicioForma(articuloServicio,clienteServicio, ventaServicio, pagoServicio);
            inicio.setVisible(true);
            this.dispose();
        });
        clientesTabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarClienteSeleccionado();
            }
        });
        guardarButton.addActionListener(e -> guardarCliente());
        limpiarButton.addActionListener(e -> limpiarForm());
        eliminarButton.addActionListener(e -> eliminarCliente());
    }

    public void iniciarForma(){
        setContentPane(panelPrincipalCliente);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.tablaModeloCliente = new DefaultTableModel(0,9){
            //Se desactivó el poder editar desde la tabla
            @Override
            public boolean isCellEditable(int row, int column ){return false;}
        };
        String[] cabeceros = {"ID", "Nombre", "Celular", "Direccion","Sector","Referencia","Articulos", "Dia de Cobro", "Total Comprado", "Total Adeudado" };
        this.tablaModeloCliente.setColumnIdentifiers(cabeceros);
        this.clientesTabla= new JTable(tablaModeloCliente);
        //Se limitó la tabla a solo seleccionar una fila
        this.clientesTabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listarClientes();
    }

    private void listarClientes(){
        this.tablaModeloCliente.setRowCount(0);
        var clientes= this.clienteServicio.listarClientes();
        clientes.forEach(cliente -> {
            Object[] renglonCliente= {
                    cliente.getId(),
                    cliente.getNombre(),
                    cliente.getCelular(),
                    cliente.getDireccion(),
                    cliente.getSector(),
                    cliente.getReferencia(),
                    cliente.getArticulos(),
                    cliente.getDiaCobro(),
                    cliente.getTotalComprado(),
                    cliente.getTotalAdeudado()
            };
            this.tablaModeloCliente.addRow(renglonCliente);
        });
    }
    private void cargarClienteSeleccionado(){
        var renglon = clientesTabla.getSelectedRow();
        if(renglon != -1){
            var id = clientesTabla.getModel().getValueAt(renglon, 0).toString();
            this.idCliente= Integer.parseInt(id);
            var nombre= clientesTabla.getModel().getValueAt(renglon,1).toString();
            nombreTexto.setText(nombre);
            var celular= clientesTabla.getModel().getValueAt(renglon,2).toString();
            celularTexto.setText(celular);
            var direccion= clientesTabla.getModel().getValueAt(renglon,3).toString();
            direccionTexto.setText(direccion);
            var sector= clientesTabla.getModel().getValueAt(renglon,4).toString();
            sectorTexto.setText(sector);
            var referencia = clientesTabla.getModel().getValueAt(renglon,5).toString();
            referenciaTexto.setText(referencia);
        }
    }

    private void guardarCliente(){
        if(nombreTexto.getText().equals("")){
            mostrarMensaje("Proporciona un nombre");
            nombreTexto.requestFocusInWindow();
            return;
        }
        if(celularTexto.getText().equals("")){
            mostrarMensaje("Proporciona el numero de celular");
            celularTexto.requestFocusInWindow();
            return;
        }
        if(direccionTexto.getText().equals("")){
            mostrarMensaje("Proporciona la dirreccion");
            direccionTexto.requestFocusInWindow();
            return;
        }
        if(sectorTexto.getText().equals("")){
            mostrarMensaje("Indique el sector");
            sectorTexto.requestFocusInWindow();
            return;
        }
        var nombre = nombreTexto.getText();
        var celular= celularTexto.getText();
        var direccion= direccionTexto.getText();
        var sector= sectorTexto.getText();
        var referencia = referenciaTexto.getText();
        var cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setCelular(celular);
        cliente.setDireccion(direccion);
        cliente.setSector(sector);
        cliente.setReferencia(referencia);
        this.clienteServicio.modificarCliente(cliente);
        mostrarMensaje("Los cambios han sido realizados");
        limpiarForm();
        listarClientes();
    }
    private void eliminarCliente(){
        var renglon = clientesTabla.getSelectedRow();
        if(renglon != -1){
            var idClienteStr = clientesTabla.getModel().getValueAt(renglon,0).toString();
            this.idCliente =Integer.parseInt(idClienteStr);
            var cliente= clienteServicio.buscarClienteporID(this.idCliente);
            clienteServicio.eliminarCliente(cliente);
            mostrarMensaje("El cliente con ID "+this.idCliente+ " ha sido eliminado");
            limpiarForm();
            listarClientes();
        }
    }

    private void limpiarForm(){
        nombreTexto.setText("");
        celularTexto.setText("");
        direccionTexto.setText("");
        sectorTexto.setText("");
        referenciaTexto.setText("");
        this.idCliente = null;
        this.clientesTabla.getSelectionModel().clearSelection();
    }
    private void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(this,mensaje);
    }
}
