
package controladores;

import daos.Conexion;
import dtos.ReservaDTO;
import excepciones.DatosInvalidosException;
import excepciones.FechaException;
import gestores.GestorReserva;
import gestores.GestorServiciosExternos;
import interfaces.InterfazIngresoDatosDocente;
import interfaces.InterfazMainBedel;
import interfaces.InterfazReservaEsporadica;
import interfaces.InterfazReservaPeriodica;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import sistemasexternos.Docente;

public class ControladorIngresoDatosDocente implements ActionListener {
    
    private InterfazIngresoDatosDocente iidd;
    
    public ControladorIngresoDatosDocente() {}
    
    public ControladorIngresoDatosDocente(InterfazIngresoDatosDocente iidd) {
        
        this.iidd = iidd;
        iidd.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        iidd.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                // Cerrar conexión y salir
                Conexion.closeEntityManagerFactory();
                iidd.dispose();
            }
        });
        
        // Listener para autocompletar el email del docente
        iidd.getListaDocentes().addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                // Cuando se selecciona un elemento en el JComboBox
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String seleccionado = (String) iidd.getListaDocentes().getSelectedItem();
                    iidd.getCampoEmail().setText(buscarEmail(seleccionado)); 
                }
            }
        });
    }
        

    public void actionPerformed(ActionEvent e) {
        
        String comando = e.getActionCommand();
        
        if(comando.equals("Siguiente")) {
            
            try {
                iidd.desmarcarCampos();
                
                if(!validarCampos()) throw new DatosInvalidosException();
                
                ReservaDTO reservaDTO = new ReservaDTO();
                reservaDTO.setNombre_docente(iidd.getDocente());
                reservaDTO.setNombre_catedra(iidd.getCatedra());
                reservaDTO.setEmail_docente(iidd.getCampoEmail().getText());
                reservaDTO.setFecha_reserva(new Date());
                reservaDTO.setId_bedel("usuario"); // ESTO HAY QUE VER SI HACEMOS AUTENTICACIÓN O QUÉ
                
                if(iidd.getBotonPeriodica().isSelected()) { // RESERVA PERIÓDICA
                    
                    reservaDTO.setTipo_reserva("Periódica");
                    reservaDTO.setPeriodo(iidd.getPeriodo());
                    GestorReserva.obtenerInstancia().verificarPeriodo(reservaDTO);
                  
                    new InterfazReservaPeriodica().getControlador().setDatos(reservaDTO);
                    iidd.dispose();
                }
                
                else if(iidd.getBotonEsporadica().isSelected()) { // RESERVA ESPORÁDICA
                    
                    reservaDTO.setTipo_reserva("Esporádica");
                    reservaDTO.setPeriodo("Anual");
                    
                    new InterfazReservaEsporadica().getControlador().setDatos(reservaDTO);
                    iidd.dispose();
                }
                
            } catch(DatosInvalidosException e1) {
                iidd.crearPopUpAdvertencia("Hay campos inválidos o sin rellenar.");
                marcarCampos();
            } catch(FechaException e2) {
                iidd.crearPopUpAdvertencia("El período seleccionado ya finalizó. Por favor, seleccione otro período.");
            }
            
        }
        else if(comando.equals("Cancelar")) {
            if(hayCambios()) {
                int confirmacion = iidd.confirmarContinuacion();
                if(confirmacion == JOptionPane.OK_OPTION) {
                    new InterfazMainBedel();
                    iidd.dispose();
                }
            }
            else {
                new InterfazMainBedel();
                iidd.dispose();
                }
            }
        }

    
    public void completarDatos() {
        
        GestorServiciosExternos gse = GestorServiciosExternos.obtenerInstancia();
        
        iidd.setearDocentes(gse.listarDocentes());
        iidd.setearCatedras(gse.listarCatedras());
        
    }
    
    private String buscarEmail(String docente) {
        
        GestorServiciosExternos gse = GestorServiciosExternos.obtenerInstancia();
        ArrayList<Docente> docentes = gse.listarDocentes();
        String email = null;
        
        for(Docente d : docentes) {
            String nombreDocente = d.getApellido() + " " + d.getNombre();
            if(docente.equals(nombreDocente)) email = d.getEmail();
        }
        
        return email;
        
    }
    
    private boolean validarCampos() {
        
        boolean valido = true;
        
        if(iidd.getDocente().equals("") ||
           iidd.getCatedra().equals("") ||
           (!iidd.getBotonPeriodica().isSelected() && !iidd.getBotonEsporadica().isSelected()) ||
           (iidd.getBotonPeriodica().isSelected() && iidd.getPeriodo().equals(""))) valido = false;
        
        return valido;
    }
    
    private void marcarCampos() {
        
        Border redBorder = new LineBorder(Color.RED, 2);
        boolean visibilidad = true;
        
        if(iidd.getDocente().equals("")) {
            iidd.setCampoDocente(redBorder, visibilidad);
        }
        if(iidd.getCatedra().equals("")) {
            iidd.setCampoCatedra(redBorder, visibilidad);
        }
        if(!iidd.getBotonPeriodica().isSelected() && !iidd.getBotonEsporadica().isSelected()) {
            iidd.getjLabel1().setVisible(true);
        }
        if(iidd.getBotonPeriodica().isSelected() && iidd.getPeriodo().equals("")) {
            iidd.setCampoPeriodo(redBorder, visibilidad);
        }
    }
    
    public void setearDatos(ReservaDTO reservaDTO) {
        
        completarDatos();
        iidd.getListaDocentes().setSelectedItem(reservaDTO.getNombre_docente());
        iidd.getListaCursos().setSelectedItem(reservaDTO.getNombre_catedra());
        if(reservaDTO.getTipo_reserva().equals("Periódica")) {
            iidd.getBotonPeriodica().setSelected(true);
            iidd.getListaPeriodos().setSelectedItem(reservaDTO.getPeriodo());
            iidd.getListaPeriodos().setVisible(true);
        }
        else iidd.getBotonEsporadica().setSelected(true);
    }
    
    public boolean hayCambios() {
        
        if (!iidd.getDocente().equals("")) {
            return true;
        }

        if (!iidd.getCatedra().equals("")) {
            return true;
        }
        
        if (iidd.getBotonPeriodica().isSelected() || iidd.getBotonEsporadica().isSelected()) {
            return true;
        }

        // Si ninguno de los valores cambió
        return false;
        
    }

    
}
