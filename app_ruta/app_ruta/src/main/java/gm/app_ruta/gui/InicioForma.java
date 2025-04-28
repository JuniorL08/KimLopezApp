package gm.app_ruta.gui;


import gm.app_ruta.gui.ventas.VentasInicio;
import gm.app_ruta.servicio.ArticuloServicio;
import gm.app_ruta.servicio.ClienteServicio;
import gm.app_ruta.servicio.PagoServicio;
import gm.app_ruta.servicio.VentaServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class InicioForma extends JFrame{
    private JPanel panelPrincipal;
    private JButton ARTICULOSButton;
    private JButton CLIENTESButton;
    private JButton VENTASButton;

    @Autowired
    public InicioForma(ArticuloServicio articuloServicio, ClienteServicio clienteServicio,
                       VentaServicio ventaServicio, PagoServicio pagoServicio){
        iniciarForma();
        ARTICULOSButton.addActionListener(e -> {
            var articulosForm = new ArticuloForma(articuloServicio,clienteServicio,ventaServicio,pagoServicio);
            articulosForm.setVisible(true);
            this.dispose();
        });
        CLIENTESButton.addActionListener(e-> {
            var clientesForm= new ClienteForma(articuloServicio,clienteServicio,ventaServicio,pagoServicio);
            clientesForm.setVisible(true);
            this.dispose();
        });
        VENTASButton.addActionListener(e -> {
            var ventasForm= new VentasInicio(articuloServicio,clienteServicio,ventaServicio,pagoServicio);
            ventasForm.setVisible(true);
            this.dispose();
        });
    }

    private void iniciarForma(){
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900,700);
        setLocationRelativeTo(null);
    }
}
