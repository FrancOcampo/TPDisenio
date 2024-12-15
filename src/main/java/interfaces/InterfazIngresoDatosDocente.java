
package interfaces;

import controladores.ControladorIngresoDatosDocente;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;
import sistemasexternos.Catedra;
import sistemasexternos.Docente;

public class InterfazIngresoDatosDocente extends javax.swing.JFrame {
    
    private ControladorIngresoDatosDocente controlador;
    
    public InterfazIngresoDatosDocente() {
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/imagen.png")).getImage());
        controlador = new ControladorIngresoDatosDocente(this);
        botonSiguiente.addActionListener(controlador);
        botonCancelar.addActionListener(controlador);
        setLocationRelativeTo(null);
        setTitle("Nueva reserva");
        setResizable(false);
        campoEmail.setEnabled(false);
        jLabel1.setVisible(false);
        jLabelError1.setVisible(false);
        jLabelError2.setVisible(false);
        jLabelError4.setVisible(false);
        listaPeriodos.setVisible(false);
        listaPeriodos.addItem("");
        listaPeriodos.addItem("1° cuatrimestre");
        listaPeriodos.addItem("2° cuatrimestre");
        listaPeriodos.addItem("Anual");
        listaPeriodos.setSelectedIndex(0);
        setVisible(true);
        ButtonGroup group = new ButtonGroup();
        group.add(botonPeriodica);
        group.add(botonEsporadica);
        
        botonPeriodica.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Hacer visible la lista de períodos si se selecciona el radio button de reserva periódica
                if (botonPeriodica.isSelected()) {
                    listaPeriodos.setVisible(true);
                }
            }
        });
        
        botonEsporadica.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Hacer invisible la lista de períodos si se selecciona el radio button de reserva esporádica
                if (botonEsporadica.isSelected()) {
                    listaPeriodos.setVisible(false);
                    Border defaultBorder = new JTextField().getBorder();
                    setCampoPeriodo(defaultBorder, false);
                }
            }
        });
    }

    public ControladorIngresoDatosDocente getControlador() {
        return controlador;
    }
    
    public JTextField getCampoEmail() {
        return campoEmail;
    }

    public JComboBox<String> getListaCursos() {
        return listaCursos;
    }

    public JComboBox<String> getListaDocentes() {
        return listaDocentes;
    }

    public JComboBox<String> getListaPeriodos() {
        return listaPeriodos;
    }

    public JRadioButton getBotonEsporadica() {
        return botonEsporadica;
    }

    public JRadioButton getBotonPeriodica() {
        return botonPeriodica;
    }

    public JLabel getjLabel1() {
        return jLabel1;
    }
    
    public void setCampoDocente(Border borde, boolean visibilidad){
         listaDocentes.setBorder(borde);
         jLabelError2.setVisible(visibilidad);
    }
    
    public void setCampoCatedra(Border borde, boolean visibilidad){
         listaCursos.setBorder(borde);
         jLabelError1.setVisible(visibilidad);
    }
    
    public void setCampoPeriodo(Border borde, boolean visibilidad){
         listaPeriodos.setBorder(borde);
         jLabelError4.setVisible(visibilidad);
    }
    
    public void setearDocentes(ArrayList<Docente> docentes) {
        
        listaDocentes.addItem("");
        
        for(Docente d : docentes) {
            String docente = d.getApellido() + " " + d.getNombre();
            listaDocentes.addItem(docente);
        }
    }
    
    public void setearCatedras(ArrayList<Catedra> catedras) {
        
        listaCursos.addItem("");
        
        for(Catedra c : catedras) {
            listaCursos.addItem(c.getNombre());
        }
        
    }
    
    public String getDocente() {
        
        String seleccion = (String) listaDocentes.getSelectedItem();
        return seleccion;
    }
    
    public String getCatedra() {
        
        String seleccion = (String) listaCursos.getSelectedItem();
        return seleccion;
    }
    
    public String getPeriodo() {
        
        String seleccion = (String) listaPeriodos.getSelectedItem();
        return seleccion;
    }
    
   
    public void desmarcarCampos() {
        
      Border defaultBorder = new JTextField().getBorder();
      boolean visibilidad = false;
      
      setCampoDocente(defaultBorder, visibilidad);
      setCampoCatedra(defaultBorder, visibilidad);
      jLabel1.setVisible(visibilidad);
      jLabelError1.setVisible(visibilidad);
      jLabelError2.setVisible(visibilidad);
      jLabelError4.setVisible(visibilidad);
      setCampoPeriodo(defaultBorder, visibilidad);
    }

    public void crearPopUpAdvertencia(String mensaje) {
        JPanel panel = new JPanel();
        JLabel label = new JLabel(mensaje);
        label.setForeground(Color.BLACK); 
        label.setFont(new Font("Arial", Font.BOLD, 13)); 
        panel.add(label);
        
        JOptionPane.showMessageDialog(null, panel, "ADVERTENCIA", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public int confirmarContinuacion() {
        String[] opciones = {"Aceptar", "Cancelar"};
        // Mostrar el diálogo con las opciones personalizadas
        int respuesta = JOptionPane.showOptionDialog(
            null,                                // Componente padre (null para centrar)
            "¿Está seguro de que desea cancelar la reserva?", // Mensaje
            "ADVERTENCIA",                       // Título
            JOptionPane.DEFAULT_OPTION,          // Tipo de opción (sin botones por defecto)
            JOptionPane.INFORMATION_MESSAGE,     // Tipo de mensaje
            null,                                // Ícono (null para usar el ícono por defecto)
            opciones,                            // Los botones personalizados
            opciones[0]                          // Botón por defecto (primera opción)
        );
        
        return respuesta;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        botonCancelar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        listaCursos = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        listaDocentes = new javax.swing.JComboBox<>();
        campoEmail = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        botonPeriodica = new javax.swing.JRadioButton();
        botonEsporadica = new javax.swing.JRadioButton();
        listaPeriodos = new javax.swing.JComboBox<>();
        jLabelError1 = new javax.swing.JLabel();
        jLabelError2 = new javax.swing.JLabel();
        jLabelError4 = new javax.swing.JLabel();
        botonSiguiente = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));
        jPanel3.setPreferredSize(new java.awt.Dimension(512, 503));

        botonCancelar.setBackground(new java.awt.Color(102, 0, 0));
        botonCancelar.setForeground(new java.awt.Color(255, 255, 255));
        botonCancelar.setText("Cancelar");
        botonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCancelarActionPerformed(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(0, 0, 0));
        jLabel3.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Ingresar datos del solicitante");
        jLabel3.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jLabel4.setFont(new java.awt.Font("Dubai", 0, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Nombre del curso");

        listaCursos.setBackground(new java.awt.Color(255, 255, 255));
        listaCursos.setForeground(new java.awt.Color(102, 102, 102));
        listaCursos.setSelectedItem("Tarde");

        jLabel5.setFont(new java.awt.Font("Dubai", 0, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Docente solicitante");

        listaDocentes.setBackground(new java.awt.Color(255, 255, 255));
        listaDocentes.setForeground(new java.awt.Color(102, 102, 102));
        listaDocentes.setSelectedItem("Tarde");

        campoEmail.setBackground(new java.awt.Color(255, 255, 255));
        campoEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoEmailActionPerformed(evt);
            }
        });
        campoEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                campoEmailKeyTyped(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Dubai", 0, 15)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Correo electrónico de contacto");

        jLabel8.setFont(new java.awt.Font("Dubai", 0, 15)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Tipo de reserva");

        botonPeriodica.setBackground(new java.awt.Color(204, 204, 204));
        botonPeriodica.setForeground(new java.awt.Color(51, 51, 51));
        botonPeriodica.setText("Periódica");

        botonEsporadica.setBackground(new java.awt.Color(204, 204, 204));
        botonEsporadica.setForeground(new java.awt.Color(51, 51, 51));
        botonEsporadica.setText("Esporádica");
        botonEsporadica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonEsporadicaActionPerformed(evt);
            }
        });

        listaPeriodos.setBackground(new java.awt.Color(255, 255, 255));
        listaPeriodos.setForeground(new java.awt.Color(102, 102, 102));
        listaPeriodos.setSelectedItem("Tarde");

        jLabelError1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelError1.setForeground(new java.awt.Color(255, 0, 0));
        jLabelError1.setText("!");

        jLabelError2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelError2.setForeground(new java.awt.Color(255, 0, 0));
        jLabelError2.setText("!");

        jLabelError4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelError4.setForeground(new java.awt.Color(255, 0, 0));
        jLabelError4.setText("!");

        botonSiguiente.setBackground(new java.awt.Color(102, 102, 102));
        botonSiguiente.setForeground(new java.awt.Color(0, 0, 0));
        botonSiguiente.setText("Siguiente");
        botonSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSiguienteActionPerformed(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("Seleccione una opción");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(campoEmail, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(listaDocentes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelError2))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                            .addComponent(listaPeriodos, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabelError4))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(botonPeriodica, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(64, 64, 64)
                            .addComponent(botonEsporadica, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(listaCursos, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelError1)))
                .addContainerGap(76, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(botonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botonSiguiente, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(listaCursos, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelError1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(listaDocentes, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelError2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(campoEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonPeriodica)
                    .addComponent(botonEsporadica))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(listaPeriodos, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelError4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonSiguiente, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 539, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonEsporadicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonEsporadicaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonEsporadicaActionPerformed

    private void campoEmailKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoEmailKeyTyped
        if (campoEmail.getText().length() >= 50) {
            evt.consume();  // Evita que se sigan ingresando caracteres
        }
    }//GEN-LAST:event_campoEmailKeyTyped

    private void campoEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoEmailActionPerformed

    private void botonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarActionPerformed

    }//GEN-LAST:event_botonCancelarActionPerformed

    private void botonSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSiguienteActionPerformed

    }//GEN-LAST:event_botonSiguienteActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InterfazIngresoDatosDocente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterfazIngresoDatosDocente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterfazIngresoDatosDocente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfazIngresoDatosDocente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InterfazIngresoDatosDocente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonCancelar;
    private javax.swing.JRadioButton botonEsporadica;
    private javax.swing.JRadioButton botonPeriodica;
    private javax.swing.JButton botonSiguiente;
    private javax.swing.JTextField campoEmail;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabelError1;
    private javax.swing.JLabel jLabelError2;
    private javax.swing.JLabel jLabelError4;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JComboBox<String> listaCursos;
    private javax.swing.JComboBox<String> listaDocentes;
    private javax.swing.JComboBox<String> listaPeriodos;
    // End of variables declaration//GEN-END:variables
}
