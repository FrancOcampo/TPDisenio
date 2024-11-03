
package controladores;

import daos.Conexion;
import dtos.BedelBusquedaDTO;
import dtos.BedelDTO;
import dtos.BedelGeneralDTO;
import excepciones.DatosInvalidosException;
import gestores.GestorBedel;
import interfaces.InterfazBuscarBedel;
import interfaces.InterfazMainAdministrador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;

public class ControladorBuscarBedel implements ActionListener, KeyListener, MouseListener {
    
    private InterfazBuscarBedel ibb;
    
    public ControladorBuscarBedel() {}
    
    public ControladorBuscarBedel(InterfazBuscarBedel ibb) {
        this.ibb = ibb;
        ibb.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        ibb.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                // Cerrar conexión y salir
                Conexion.closeEntityManagerFactory();
                ibb.dispose();
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        
        if(comando.equals("Buscar")) {
            
            try {
            
            if(!criteriosDeBusqueda()) throw new DatosInvalidosException();
            
            BedelGeneralDTO bedelGeneralDTO = new BedelGeneralDTO();
            String apellido = ibb.getCampoApellido().getText().substring(0, 1).toUpperCase() + ibb.getCampoApellido().getText().substring(1).toLowerCase();
            bedelGeneralDTO.setApellido(apellido);
            ArrayList<String> turnos = new ArrayList<>();
            
            if(ibb.getjCheckBox1().isSelected()) turnos.add("Mañana");
            if(ibb.getjCheckBox2().isSelected()) turnos.add("Tarde");
            if(ibb.getjCheckBox3().isSelected()) turnos.add("Noche");
            
            bedelGeneralDTO.setTurno(turnos);
            
            GestorBedel gb = GestorBedel.obtenerInstancia();
            List<BedelBusquedaDTO> bedeles = gb.buscarBedeles(bedelGeneralDTO);
            
            } catch(DatosInvalidosException exception) {
                ibb.crearPopUpAdvertencia();
            }
        }
        else if(comando.equals("Restaurar valores por defecto")) {
            
        }
        else if(comando.equals("Modificar bedel")) {
            
        }
        else if(comando.equals("Eliminar bedel")) {
            
        }
        else if(comando.equals("Cancelar")) {
            new InterfazMainAdministrador();
            ibb.dispose();
        }
    }
    
    private boolean criteriosDeBusqueda() {
        
        boolean criterios = true;
        
        if(ibb.getCampoApellido().getText().trim().isEmpty() &&
           !ibb.getjCheckBox1().isSelected() &&
           !ibb.getjCheckBox2().isSelected() &&
           !ibb.getjCheckBox3().isSelected()) criterios = false;
        
        return criterios;
    }

    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
    
    
    
}
