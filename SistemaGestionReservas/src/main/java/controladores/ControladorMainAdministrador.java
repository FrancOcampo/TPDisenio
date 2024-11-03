
package controladores;

import daos.Conexion;
import interfaces.InterfazBuscarBedel;
import interfaces.InterfazMainAdministrador;
import interfaces.InterfazRegistrarBedel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class ControladorMainAdministrador implements ActionListener {
    
    private InterfazMainAdministrador ima;
    
    public ControladorMainAdministrador() {}
    
    public ControladorMainAdministrador(InterfazMainAdministrador ima) {
        this.ima = ima;
        ima.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        ima.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                // Cerrar conexión y salir
                Conexion.closeEntityManagerFactory();
                ima.dispose();
            }
        });
    }
    
    public void actionPerformed(ActionEvent e) {
        
        String comando = e.getActionCommand();
        
        if(comando.equals("Registrar bedel")) {
            new InterfazRegistrarBedel();
            ima.dispose();
        }
        else if(comando.equals("Buscar bedel")) {
            new InterfazBuscarBedel();
            ima.dispose();
        }
        else if(comando.equals("Cancelar")) {
                Conexion.closeEntityManagerFactory();
                System.exit(0);
        }
    }
    
    
}
