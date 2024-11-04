
package controladores;

import daos.Conexion;
import dtos.BedelBusquedaDTO;
import dtos.BedelGeneralDTO;
import excepciones.DatosInvalidosException;
import excepciones.NoExisteBedelException;
import gestores.GestorBedel;
import interfaces.InterfazBuscarBedel;
import interfaces.InterfazMainAdministrador;
import interfaces.InterfazModificarBedel;
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
            
            busqueda();
              
        }
        else if(comando.equals("Restaurar valores por defecto")) {
            ibb.getCampoApellido().setText("");
            ibb.getjCheckBox1().setSelected(false);
            ibb.getjCheckBox2().setSelected(false);
            ibb.getjCheckBox3().setSelected(false);
            ibb.getModel().setRowCount(0); 
        }
        else if(comando.equals("Modificar bedel")) {
            int row = ibb.getjTable1().getSelectedRow();
            
            try {
                if(row == -1) throw new DatosInvalidosException();
                else {
                    BedelBusquedaDTO bedelBusquedaDTO = new BedelBusquedaDTO();
                    bedelBusquedaDTO.setNombreUsuario((String) ibb.getjTable1().getValueAt(row,0));
                    bedelBusquedaDTO.setApellido((String) ibb.getjTable1().getValueAt(row,1));
                    bedelBusquedaDTO.setNombre((String) ibb.getjTable1().getValueAt(row,2));
                    bedelBusquedaDTO.setTurno((String) ibb.getjTable1().getValueAt(row,3));
                    (new InterfazModificarBedel()).getControlador().setControladorBuscarBedel(this, bedelBusquedaDTO);
                    
                }
            } catch(DatosInvalidosException e1) {
                ibb.crearPopUpFila();
            }
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
    
    private void cargarDatosEnTabla(List<BedelBusquedaDTO> bedelesBusquedaDTO) {
        
        for(BedelBusquedaDTO bedelBusquedaDTO : bedelesBusquedaDTO) {
            Object [] filaDatos = {
                bedelBusquedaDTO.getNombreUsuario(),
                bedelBusquedaDTO.getApellido(),
                bedelBusquedaDTO.getNombre(),
                bedelBusquedaDTO.getTurno()  
            };
            
            ibb.getModel().addRow(filaDatos);
        }
    }
    
    public void busqueda() {
        
        try {
            
            if(!criteriosDeBusqueda()) throw new DatosInvalidosException();
            
            BedelGeneralDTO bedelGeneralDTO = new BedelGeneralDTO();
            
            if(!ibb.getCampoApellido().getText().trim().isEmpty()) {
                String apellido = ibb.getCampoApellido().getText().substring(0, 1).toUpperCase() + ibb.getCampoApellido().getText().substring(1).toLowerCase();
                bedelGeneralDTO.setApellido(apellido);
            }
            else bedelGeneralDTO.setApellido(null);
            
            ArrayList<String> turnos = new ArrayList<>();
            
            if(ibb.getjCheckBox1().isSelected()) turnos.add("Mañana");
            if(ibb.getjCheckBox2().isSelected()) turnos.add("Tarde");
            if(ibb.getjCheckBox3().isSelected()) turnos.add("Noche");
            
            bedelGeneralDTO.setTurnos(turnos);
            
            GestorBedel gb = GestorBedel.obtenerInstancia();
            List<BedelBusquedaDTO> bedelesBusquedaDTO = gb.buscarBedeles(bedelGeneralDTO);
            
            ibb.getModel().setRowCount(0); 
            
            if(!bedelesBusquedaDTO.isEmpty()) {
                cargarDatosEnTabla(bedelesBusquedaDTO);
            }
            else throw new NoExisteBedelException();
            
            } catch(DatosInvalidosException e1) {
                ibb.crearPopUpAdvertencia();
                
            } catch(NoExisteBedelException e2) {
                ibb.crearPopUpBusqueda();
            }
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
