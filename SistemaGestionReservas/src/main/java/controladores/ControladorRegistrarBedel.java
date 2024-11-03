
package controladores;

import daos.Conexion;
import dtos.BedelDTO;
import excepciones.DatosInvalidosException;
import excepciones.PoliticasContraseniaException;
import excepciones.YaExisteUsuarioException;
import gestores.GestorBedel;
import interfaces.InterfazMainAdministrador;
import interfaces.InterfazRegistrarBedel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;


public class ControladorRegistrarBedel implements ActionListener, KeyListener, MouseListener {

    private InterfazRegistrarBedel irb;
    
    public ControladorRegistrarBedel() {}
    
    public ControladorRegistrarBedel(InterfazRegistrarBedel irb) {
        this.irb = irb;
        irb.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        irb.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                // Cerrar conexión y salir
                Conexion.closeEntityManagerFactory();
                irb.dispose();
            }
        });
    }
    
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        
        if(comando.equals("Guardar")) {
            
            irb.desmarcarCampos();
            
            try {
                
                if(!validarCampos()) throw new DatosInvalidosException();
              
                BedelDTO bedelDTO = new BedelDTO();
                bedelDTO.setNombreUsuario(irb.getCampoID().getText());
                String nombre = irb.getCampoNombre().getText().substring(0, 1).toUpperCase() + irb.getCampoNombre().getText().substring(1).toLowerCase();
                bedelDTO.setNombre(nombre);
                String apellido = irb.getCampoApellido().getText().substring(0, 1).toUpperCase() + irb.getCampoApellido().getText().substring(1).toLowerCase();
                bedelDTO.setApellido(apellido);
                bedelDTO.setTurno(irb.getTurno());
                bedelDTO.setContrasenia(irb.getCampoContrasenia().getText());
            
                GestorBedel gb = GestorBedel.obtenerInstancia();
                
                boolean exito = gb.registrarBedel(bedelDTO);
                
                if(exito) {
                    irb.crearPopUpExito();
                    irb.setearCamposEnBlanco();
                }
                else irb.crearPopUpFracaso();
            
            }
            catch(DatosInvalidosException e1) {
                marcarCampos();
            }
            catch(YaExisteUsuarioException e2) {
                Border redBorder = new LineBorder(Color.RED, 2);
                irb.setCampoID(redBorder, true);
                irb.setJLabelError10Visible(true);
                irb.crearPopUpAdvertencia();
            }
            catch(PoliticasContraseniaException e3) {
                marcarCampos(e3.getMensajes());
            }
            
        }
        else if(comando.equals("Cancelar")) {
            new InterfazMainAdministrador();
            irb.dispose();
        }
    }

    
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    
    public void keyReleased(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

   
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

   
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }

    
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    private boolean validarCampos() {
        boolean datosValidos =
        !irb.getCampoNombre().getText().trim().isEmpty() && 
        !irb.getCampoApellido().getText().trim().isEmpty() && 
        !irb.getTurno().equals("") &&
        !irb.getCampoID().getText().trim().isEmpty() && 
        !irb.getCampoContrasenia().getText().trim().isEmpty() && 
        !irb.getCampoConfirmarContrasenia().getText().trim().isEmpty() && 
        irb.getCampoNombre().getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+") && 
        irb.getCampoApellido().getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+") && 
        irb.getCampoContrasenia().getText().equals(irb.getCampoConfirmarContrasenia().getText());
                
        return datosValidos;        
    }
    
    private void marcarCampos() {
        Border redBorder = new LineBorder(Color.RED, 2);
        boolean visibilidad = true;
        boolean advertencia = false;
        
      if(irb.getCampoNombre().getText().trim().isEmpty() || !(irb.getCampoNombre().getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+"))){
         irb.setCampoNombre(redBorder, visibilidad);
         advertencia = true;
      }
      if(irb.getCampoApellido().getText().trim().isEmpty() || !(irb.getCampoApellido().getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+"))){
         irb.setCampoApellido(redBorder, visibilidad);
         advertencia = true;
      }
      if(irb.getCampoContrasenia().getText().trim().isEmpty()){
        irb.setCampoContrasenia(redBorder, visibilidad);
        advertencia = true;
      }
      if(irb.getCampoConfirmarContrasenia().getText().trim().isEmpty()){
        irb.setCampoConfirmarContrasenia(redBorder, visibilidad);
        advertencia = true;
      }
      if(irb.getCampoID().getText().trim().isEmpty()){
        irb.setCampoID(redBorder, visibilidad);
        advertencia = true;
      }
      if(!irb.getCampoContrasenia().getText().equals(irb.getCampoConfirmarContrasenia().getText())){
        advertencia = true;
        irb.setCampoContrasenia(redBorder, visibilidad);
        irb.setCampoConfirmarContrasenia(redBorder, visibilidad);
        irb.setJLabelError9Visible(visibilidad);
    }
      if(irb.getTurno().equals("")){
         advertencia = true;
         irb.setCampoTurno(redBorder, visibilidad);
      }
      
      if(advertencia) irb.crearPopUpAdvertencia();
    }
    
    private void marcarCampos(List<String> mensajes) {
        
        Border redBorder = new LineBorder(Color.RED, 2);
        boolean visibilidad = true;
        
        irb.setCampoContrasenia(redBorder, visibilidad);
        irb.setCampoConfirmarContrasenia(redBorder, visibilidad);
        irb.setJLabelError8Mensaje(mensajes);
        irb.setJLabelError8Visible(visibilidad);
        irb.crearPopUpAdvertencia();
        
    }
    
    
}
