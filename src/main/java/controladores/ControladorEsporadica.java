package controladores;

import daos.Conexion;
import dtos.ReservaDTO;
import excepciones.DatosInvalidosException;
import interfaces.InterfazReservaEsporadica;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class ControladorEsporadica implements ActionListener {
    
    private InterfazReservaEsporadica ire;
    ReservaDTO reservaDTO;
    
    public ControladorEsporadica() {}
    
    public ControladorEsporadica(InterfazReservaEsporadica ire) {
        
        this.ire = ire;
        ire.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        ire.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                // Cerrar conexión y salir
                Conexion.closeEntityManagerFactory();
                ire.dispose();
            }
        });
    }
    
    public void setDatos(ReservaDTO reservaDTO) {
        this.reservaDTO = reservaDTO;
    }
    
    public void actionPerformed(ActionEvent e) {
        
        String comando = e.getActionCommand();
        
        if(comando.equals("Confirmar día")) {
            
            try {
                ire.desmarcarCampos();
                if(!validarCampos()) throw new DatosInvalidosException();
                
            } catch(DatosInvalidosException e1) {
                marcarCampos();
            }
    }
    
 }
    
    private boolean validarCampos() {
        
        boolean valido = true;
        
        if(ire.getCampoCantidadAlumnos().getText().equals("") ||
           !ire.getCampoCantidadAlumnos().getText().matches("\\d+") ||
           ire.getTipoAula().equals("") ||
           ire.getFecha().equals("")) valido = false;
        
        return valido;
    }
    
    private void marcarCampos() {
        
        Border redBorder = new LineBorder(Color.RED, 2);
        boolean visibilidad = true;
        
        if(ire.getCampoCantidadAlumnos().getText().equals("") || !ire.getCampoCantidadAlumnos().getText().matches("\\d+")) {
            ire.setCampoCantidadAlumnos(redBorder, visibilidad);
        }
        if(ire.getTipoAula().equals("")) {
            ire.setCampoAula(redBorder, visibilidad);
        }
        if(ire.getFecha().equals("")) {
            ire.setCampoFecha(redBorder, visibilidad);
        }
    }
    
    
    
    
}

