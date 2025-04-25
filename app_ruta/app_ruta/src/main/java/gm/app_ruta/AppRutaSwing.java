package gm.app_ruta;

import gm.app_ruta.gui.InicioForma;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;

@SpringBootApplication
public class AppRutaSwing {
    public static void main(String[] args) {
        //Activar el modo oscuro

        //Instancia de la fabrica de Spring
        ConfigurableApplicationContext contextSpring = new SpringApplicationBuilder(AppRutaSwing.class).
                headless(false).
                web(WebApplicationType.NONE).
                run(args);
        //Creacion de un objeto Swing
        SwingUtilities.invokeLater(() ->{
            InicioForma inicioForma = contextSpring.getBean(InicioForma.class);
            inicioForma.setVisible(true);
        });
    }
}
