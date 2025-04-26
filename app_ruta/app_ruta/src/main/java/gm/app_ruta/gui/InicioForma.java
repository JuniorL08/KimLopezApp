package gm.app_ruta.gui;


import gm.app_ruta.servicio.ArticuloServicio;
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
    public InicioForma(ArticuloServicio articuloServicio){
        iniciarForma();
        ARTICULOSButton.addActionListener(e -> {
            var articulosForm = new ArticuloForma(articuloServicio);
            articulosForm.setVisible(true);
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
