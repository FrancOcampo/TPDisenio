
package controladores;

import dtos.BedelBusquedaDTO;
import dtos.BedelDTO;
import excepciones.OperacionException;
import gestores.GestorBedel;
import interfaces.InterfazEliminarBedel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class ControladorEliminarBedel implements ActionListener {

    private InterfazEliminarBedel ieb;
    private ControladorBuscarBedel cbb;
    private BedelBusquedaDTO bedelBusquedaDTO;
    
    public ControladorEliminarBedel() {}
    
    public ControladorEliminarBedel(InterfazEliminarBedel ieb) {
        
        this.ieb = ieb;
        
        ieb.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        ieb.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                ieb.dispose();
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
        
        ieb.getCampoNombre().setText(bedelDTO.getNombre());
        ieb.getCampoApellido().setText(bedelDTO.getApellido());
        ieb.getCampoTurno().setText(bedelDTO.getTurno());
    }
    
    
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        
        if(comando.equals("Eliminar")) {
            try {
                
                GestorBedel gb = GestorBedel.obtenerInstancia();
                
                gb.eliminarBedel(bedelBusquedaDTO);
                
                ieb.crearPopUpExito();
                ieb.dispose();
                cbb.busqueda();
                
            } catch(OperacionException e1) {
                ieb.crearPopUpFracaso();
            }
        }
        
        else if(comando.equals("Cancelar")) {
                ieb.dispose();
            }
        }
    
}
