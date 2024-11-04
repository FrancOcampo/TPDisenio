
package controladores;

import dtos.BedelBusquedaDTO;
import dtos.BedelDTO;
import excepciones.DatosInvalidosException;
import excepciones.PoliticasContraseniaException;
import gestores.GestorBedel;
import interfaces.InterfazModificarBedel;
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
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class ControladorModificarBedel implements ActionListener, KeyListener, MouseListener {
    
    private InterfazModificarBedel imb;
    private ControladorBuscarBedel cbb;
    private BedelBusquedaDTO bedelBusquedaDTO;
    
    public ControladorModificarBedel() {}
    
    public ControladorModificarBedel(InterfazModificarBedel imb) {
        
        this.imb = imb;
        
        imb.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        imb.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                imb.dispose();
            }
        });
    }
    
    public void setControladorBuscarBedel(ControladorBuscarBedel controlador, BedelBusquedaDTO bedelBusquedaDTO) {
        cbb = controlador;
        this.bedelBusquedaDTO = bedelBusquedaDTO;
        setearCampos();
    }
    
    private void setearCampos() {
        GestorBedel gb = GestorBedel.obtenerInstancia();
        BedelDTO bedelDTO = gb.buscarPorUsuario(bedelBusquedaDTO);
        
        imb.getIdentificador().setText(bedelDTO.getNombreUsuario());
        imb.getCampoNombre().setText(bedelDTO.getNombre());
        imb.getCampoApellido().setText(bedelDTO.getApellido());
        imb.getListaTurnos().setSelectedItem(bedelDTO.getTurno());
        imb.getCampoContrasenia().setText(bedelDTO.getContrasenia());
        imb.getCampoConfirmarContrasenia().setText(bedelDTO.getContrasenia());
    }

    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        
        if(comando.equals("Guardar")) {
            
            imb.desmarcarCampos();
            
            try {
                
                if(!validarCampos()) throw new DatosInvalidosException();
              
                int confirmacion = imb.confirmarGuardar();
                if(confirmacion == JOptionPane.OK_OPTION) {
                    
                    BedelDTO bedelDTO = new BedelDTO();
                    bedelDTO.setNombreUsuario(imb.getIdentificador().getText());
                    String nombre = imb.getCampoNombre().getText().substring(0, 1).toUpperCase() + imb.getCampoNombre().getText().substring(1).toLowerCase();
                    bedelDTO.setNombre(nombre);
                    String apellido = imb.getCampoApellido().getText().substring(0, 1).toUpperCase() + imb.getCampoApellido().getText().substring(1).toLowerCase();
                    bedelDTO.setApellido(apellido);
                    bedelDTO.setTurno(imb.getTurno());
                    bedelDTO.setContrasenia(imb.getCampoContrasenia().getText());
            
                    GestorBedel gb = GestorBedel.obtenerInstancia();
                
                    boolean exito = gb.modificarBedel(bedelDTO);
                
                    if(exito) {
                        imb.crearPopUpExito();
                        imb.dispose();
                        cbb.busqueda();
                    }
                    else imb.crearPopUpFracaso();
                }
            
            }
            catch(DatosInvalidosException e1) {
                marcarCampos();
            }
            catch(PoliticasContraseniaException e2) {
                marcarCampos(e2.getMensajes());
            }
            
        }
        else if(comando.equals("Cancelar")) {
            
            // Primero chequear si cambió algún campo, y si hubo cambios hacer int confirmacion, sino no
            int confirmacion = imb.confirmarContinuacion();
            if(confirmacion == JOptionPane.OK_OPTION) {
                imb.dispose();
            }
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
        !imb.getCampoNombre().getText().trim().isEmpty() && 
        !imb.getCampoApellido().getText().trim().isEmpty() && 
        !imb.getTurno().equals("") &&
        !imb.getCampoContrasenia().getText().trim().isEmpty() && 
        !imb.getCampoConfirmarContrasenia().getText().trim().isEmpty() && 
         imb.getCampoNombre().getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+") && 
         imb.getCampoApellido().getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+") && 
         imb.getCampoContrasenia().getText().equals(imb.getCampoConfirmarContrasenia().getText());
                
        return datosValidos;        
    }
    
    private void marcarCampos() {
        Border redBorder = new LineBorder(Color.RED, 2);
        boolean visibilidad = true;
        boolean advertencia = false;
        
      if(imb.getCampoNombre().getText().trim().isEmpty() || !(imb.getCampoNombre().getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+"))){
         imb.setCampoNombre(redBorder, visibilidad);
         advertencia = true;
      }
      if(imb.getCampoApellido().getText().trim().isEmpty() || !(imb.getCampoApellido().getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+"))){
         imb.setCampoApellido(redBorder, visibilidad);
         advertencia = true;
      }
      if(imb.getCampoContrasenia().getText().trim().isEmpty()){
        imb.setCampoContrasenia(redBorder, visibilidad);
        advertencia = true;
      }
      if(imb.getCampoConfirmarContrasenia().getText().trim().isEmpty()){
            imb.setCampoConfirmarContrasenia(redBorder, visibilidad);
            advertencia = true;
      }
      if(!imb.getCampoContrasenia().getText().equals(imb.getCampoConfirmarContrasenia().getText())){
            advertencia = true;
            imb.setCampoContrasenia(redBorder, visibilidad);
            imb.setCampoConfirmarContrasenia(redBorder, visibilidad);
            imb.setJLabelError9Visible(visibilidad);
    }
      if(imb.getTurno().equals("")){
            advertencia = true;
            imb.setCampoTurno(redBorder, visibilidad);
      }
      
      if(advertencia) imb.crearPopUpAdvertencia();
    }
    
    private void marcarCampos(List<String> mensajes) {
        
        Border redBorder = new LineBorder(Color.RED, 2);
        boolean visibilidad = true;
        
        imb.setCampoContrasenia(redBorder, visibilidad);
        imb.setCampoConfirmarContrasenia(redBorder, visibilidad);
        imb.setJLabelError8Mensaje(mensajes);
        imb.setJLabelError8Visible(visibilidad);
        imb.crearPopUpAdvertencia();
        
    }
    
    
}