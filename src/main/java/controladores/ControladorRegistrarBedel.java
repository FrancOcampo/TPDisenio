
package controladores;

import daos.Conexion;
import dtos.BedelDTO;
import excepciones.DatosInvalidosException;
import excepciones.OperacionException;
import excepciones.PoliticasContraseniaException;
import excepciones.YaExisteUsuarioException;
import gestores.GestorBedel;
import interfaces.InterfazMainAdministrador;
import interfaces.InterfazRegistrarBedel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;


public class ControladorRegistrarBedel implements ActionListener {

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
            
            try {
                
                irb.desmarcarCampos();
                
                if(!validarCampos()) throw new DatosInvalidosException();
              
                int confirmacion = irb.confirmarGuardar();
                if(confirmacion == JOptionPane.OK_OPTION) {
                    
                    BedelDTO bedelDTO = new BedelDTO();
                    bedelDTO.setNombreUsuario(irb.getCampoID().getText().toLowerCase());
                    String nombre = irb.getCampoNombre().getText().substring(0, 1).toUpperCase() + irb.getCampoNombre().getText().substring(1).toLowerCase();
                    bedelDTO.setNombre(nombre);
                    String apellido = irb.getCampoApellido().getText().substring(0, 1).toUpperCase() + irb.getCampoApellido().getText().substring(1).toLowerCase();
                    bedelDTO.setApellido(apellido);
                    bedelDTO.setTurno(irb.getTurno());
                    bedelDTO.setContrasenia(irb.getCampoContrasenia().getText());
            
                    GestorBedel gb = GestorBedel.obtenerInstancia();
                
                    gb.registrarBedel(bedelDTO);
                
                    irb.crearPopUpExito();
                    irb.setearCamposEnBlanco();
                }
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
            catch(OperacionException e4) {
                irb.crearPopUpFracaso();
            }
            
        }
        else if(comando.equals("Cancelar")) {
            // Verificar que el usuario haya completado algún campo para preguntarle si desea cancelar
            if(camposNoVacios()) {
                int confirmacion = irb.confirmarContinuacion();
                if(confirmacion == JOptionPane.OK_OPTION) {
                    new InterfazMainAdministrador();
                    irb.dispose();
                }
            }
            else {
                new InterfazMainAdministrador();
                irb.dispose();
            }
        }
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
    
    private boolean camposNoVacios() {
        boolean noVacio =
        !irb.getCampoNombre().getText().trim().isEmpty() || 
        !irb.getCampoApellido().getText().trim().isEmpty() || 
        !irb.getTurno().equals("Tarde") ||
        !irb.getCampoID().getText().trim().isEmpty() ||
        !irb.getCampoContrasenia().getText().trim().isEmpty() || 
        !irb.getCampoConfirmarContrasenia().getText().trim().isEmpty() || 
        !irb.getCampoContrasenia().getText().trim().isEmpty();
        
        return noVacio;
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