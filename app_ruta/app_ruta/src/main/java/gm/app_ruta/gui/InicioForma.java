package gm.app_ruta.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
@Component
public class InicioForma extends JFrame{
    private JPanel panelPrincipal;
    private JButton ARTICULOSButton;
    private JButton CLIENTESButton;
    private JButton VENTASButton;

    @Autowired
    public InicioForma(){
        iniciarForma();
    }
    private void iniciarForma(){
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900,700);
        setLocationRelativeTo(null);
    }
}
