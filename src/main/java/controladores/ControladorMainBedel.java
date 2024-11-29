
package controladores;

import daos.Conexion;
import interfaces.InterfazIngresoDatosDocente;
import interfaces.InterfazMainBedel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class ControladorMainBedel implements ActionListener {
    
    private InterfazMainBedel imb;
    
    public ControladorMainBedel() {}
    
    public ControladorMainBedel(InterfazMainBedel imb) {
        this.imb = imb;
        imb.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        imb.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                // Cerrar conexión y salir
                Conexion.closeEntityManagerFactory();
                imb.dispose();
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        
        String comando = e.getActionCommand();
        
        if(comando.equals("Registrar reserva")) {
            InterfazIngresoDatosDocente interfaz;
            interfaz = new InterfazIngresoDatosDocente();
            interfaz.getControlador().completarDatos();
            imb.dispose();
        }
        else if(comando.equals("Buscar aula")) {
            imb.generarPopUpNoDisponible();
        }
        else if(comando.equals("Reservas dia")) {
                imb.generarPopUpNoDisponible();
        }
        else if(comando.equals("Reservas curso")) {
                imb.generarPopUpNoDisponible();
        }
        else if(comando.equals("Cancelar")) {
                Conexion.closeEntityManagerFactory();
                System.exit(0);
        }
    }
    
}
