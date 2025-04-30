package gm.app_ruta.gui.ventas;

import gm.app_ruta.gui.InicioForma;
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
public class VentasInicio extends JFrame{
    private JPanel panelPrincipal;
    private JButton volverAlInicioButton;
    private JButton historicoDeVentasButton;
    private JButton registrarUnaVentaButton;
    private JButton registrarUnPagoButton;
    private JButton historialDePagosButton;

    @Autowired
    public VentasInicio(ArticuloServicio articuloServicio, ClienteServicio clienteServicio,
                        VentaServicio ventaServicio, PagoServicio pagoServicio){
        iniciarForma();
        volverAlInicioButton.addActionListener(e -> {
            var inicio = new InicioForma(articuloServicio,clienteServicio, ventaServicio,pagoServicio);
            inicio.setVisible(true);
            this.dispose();
        });
        historicoDeVentasButton.addActionListener(e -> {
            var historicoVentas= new VentasHistorico(ventaServicio,articuloServicio,clienteServicio,pagoServicio);
            historicoVentas.setVisible(true);
            this.dispose();
        });
        historialDePagosButton.addActionListener(e -> {
            var historicoPagos= new PagosHistorico(pagoServicio,ventaServicio,clienteServicio,articuloServicio);
            historicoPagos.setVisible(true);
            this.dispose();
        });
        registrarUnPagoButton.addActionListener(e -> {
            var realizarPagos= new PagosForma(pagoServicio,ventaServicio,clienteServicio,articuloServicio);
            realizarPagos.setVisible(true);
            this.dispose();
        });
        registrarUnaVentaButton.addActionListener(e -> {
            var realizarVentas= new VentasForma(ventaServicio,pagoServicio,clienteServicio,articuloServicio);
            realizarVentas.setVisible(true);
            this.dispose();
        });
    }

    private void iniciarForma(){
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900,700);
        setLocationRelativeTo(null);
    }
}
