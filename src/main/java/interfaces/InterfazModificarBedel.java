
package interfaces;

import controladores.ControladorModificarBedel;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class InterfazModificarBedel extends javax.swing.JFrame {
    
    private ControladorModificarBedel controlador;
    
    public InterfazModificarBedel() {
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/imagen.png")).getImage());
        jPanel1.setLayout(null);
        setLocationRelativeTo(null);
        setTitle("Modificar Bedel");
        setResizable(false);
        jLabelError1.setVisible(false);
        jLabelError2.setVisible(false);
        jLabelError3.setVisible(false);
        jLabelError1.setVisible(false);
        jLabelError5.setVisible(false);
        jLabelError6.setVisible(false);
        jLabelError8.setVisible(false);
        jLabelError9.setVisible(false);
        listaTurnos.addItem("");
        listaTurnos.addItem("Mañana");
        listaTurnos.addItem("Tarde");
        listaTurnos.addItem("Noche");
        controlador = new ControladorModificarBedel(this);
        botonGuardar.addActionListener(controlador);
        botonCancelar.addActionListener(controlador);
        setVisible(true);
    }

    public ControladorModificarBedel getControlador() {
        return controlador;
    }
    
    public JTextField getCampoApellido() {
        return campoApellido;
    }

    public JTextField getCampoConfirmarContrasenia() {
        return campoConfirmarContrasenia;
    }

    public JTextField getCampoContrasenia() {
        return campoContrasenia;
    }

    public JTextField getCampoNombre() {
        return campoNombre;
    }
    
    public String getTurno() {
        String seleccion = (String) listaTurnos.getSelectedItem();
        return seleccion;
    }

    public JComboBox<String> getListaTurnos() {
        return listaTurnos;
    }

    public JLabel getIdentificador() {
        return identificador;
    }
    
    public void setCampoNombre(Border borde, boolean visibilidad){
         campoNombre.setBorder(borde);
         jLabelError1.setVisible(visibilidad);
    }
    
    public void setCampoApellido(Border borde, boolean visibilidad){
        campoApellido.setBorder(borde);
        jLabelError2.setVisible(visibilidad);
    }
    
    public void setCampoContrasenia(Border borde, boolean visibilidad){
        campoContrasenia.setBorder(borde);
        jLabelError5.setVisible(visibilidad);
    }
    
    public void setCampoConfirmarContrasenia(Border borde, boolean visibilidad){
        campoConfirmarContrasenia.setBorder(borde);
        jLabelError6.setVisible(visibilidad);
        
    }
    
    public void setCampoTurno(Border borde, boolean visibilidad){
        listaTurnos.setBorder(borde);
        jLabelError3.setVisible(visibilidad);
    }
    
    public void desmarcarCampos() {
        
      Border defaultBorder = new JTextField().getBorder();
      boolean visibilidad = false;
         
      setCampoNombre(defaultBorder, visibilidad);
      setCampoApellido(defaultBorder, visibilidad);
      setCampoTurno(defaultBorder, visibilidad);
      setCampoContrasenia(defaultBorder, visibilidad);
      setCampoConfirmarContrasenia(defaultBorder, visibilidad);
      jLabelError8.setText("");
      jLabelError9.setVisible(visibilidad);
      
    }

    public JLabel getjLabelError8() {
        return jLabelError8;
    }
    
    public void setjLabelError8Mensaje(List<String> mensajes) {
        
        StringBuilder mensajesHtml = new StringBuilder("<html>La contraseña no cumple con los siguientes requisitos:<br>"); 

        for (String mensaje : mensajes) {
        mensajesHtml.append(mensaje).append("<br>"); // Agregar cada mensaje seguido de un salto de línea HTML
        }

        mensajesHtml.append("</html>"); // Cerrar la etiqueta HTML

        jLabelError8.setText(mensajesHtml.toString()); 
        
    }
    
    public JLabel getjLabelError9() {
        return jLabelError9;
    }
     
    public void crearPopUpAdvertencia(String mensaje) {
        JPanel panel = new JPanel();
        JLabel label = new JLabel(mensaje);
        label.setForeground(Color.BLACK); 
        label.setFont(new Font("Arial", Font.BOLD, 13)); 
        panel.add(label);
        
        JOptionPane.showMessageDialog(null, panel, "ADVERTENCIA", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void crearPopUpExito() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Los cambios se guardaron con éxito.");
        label.setForeground(Color.BLACK); 
        label.setFont(new Font("Arial", Font.BOLD, 13)); 
        panel.add(label);
        
        JOptionPane.showMessageDialog(null, panel, "CAMBIOS GUARDADOS", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void crearPopUpError(String mensaje) {
        JPanel panel = new JPanel();
        JLabel label = new JLabel(mensaje);
        label.setForeground(Color.BLACK); 
        label.setFont(new Font("Arial", Font.BOLD, 13)); 
        panel.add(label);
        
        JOptionPane.showMessageDialog(null, panel, "ERROR", JOptionPane.ERROR_MESSAGE);
    }
    
    public int confirmarGuardar() {
        String[] opciones = {"Aceptar", "Cancelar"};
        // Mostrar el diálogo con las opciones personalizadas
        int respuesta = JOptionPane.showOptionDialog(
            null,                                // Componente padre (null para centrar)
            "¿Está seguro de que desea guardar los cambios?", // Mensaje
            "ADVERTENCIA",                       // Título
            JOptionPane.DEFAULT_OPTION,          // Tipo de opción (sin botones por defecto)
            JOptionPane.INFORMATION_MESSAGE,     // Tipo de mensaje
            null,                                // Ícono (null para usar el ícono por defecto)
            opciones,                            // Los botones personalizados
            opciones[0]                          // Botón por defecto (primera opción)
        );
        
        return respuesta;
    }
    
    public int confirmarContinuacion() {
        String[] opciones = {"Aceptar", "Cancelar"};
        // Mostrar el diálogo con las opciones personalizadas
        int respuesta = JOptionPane.showOptionDialog(
            null,                                // Componente padre (null para centrar)
            "Hay cambios sin guardar. ¿Desea continuar?", // Mensaje
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

        jPanel1 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        campoNombre = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        campoApellido = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        listaTurnos = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        botonGuardar = new javax.swing.JButton();
        botonCancelar = new javax.swing.JButton();
        campoContrasenia = new javax.swing.JPasswordField();
        campoConfirmarContrasenia = new javax.swing.JPasswordField();
        jLabelError1 = new javax.swing.JLabel();
        jLabelError2 = new javax.swing.JLabel();
        jLabelError5 = new javax.swing.JLabel();
        jLabelError6 = new javax.swing.JLabel();
        jLabelError3 = new javax.swing.JLabel();
        jLabelError8 = new javax.swing.JLabel();
        jLabelError9 = new javax.swing.JLabel();
        identificador = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel1.setMaximumSize(new java.awt.Dimension(469, 377));
        jPanel1.setMinimumSize(new java.awt.Dimension(469, 377));

        jLabel10.setBackground(new java.awt.Color(0, 0, 0));
        jLabel10.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Modificar datos del bedel");
        jLabel10.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        campoNombre.setBackground(new java.awt.Color(255, 255, 255));
        campoNombre.setNextFocusableComponent(campoApellido);
        campoNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoNombreActionPerformed(evt);
            }
        });
        campoNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                campoNombreKeyTyped(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Dubai", 0, 15)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 0));
        jLabel11.setText("Turno");

        campoApellido.setBackground(new java.awt.Color(255, 255, 255));
        campoApellido.setNextFocusableComponent(listaTurnos);
        campoApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoApellidoActionPerformed(evt);
            }
        });
        campoApellido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                campoApellidoKeyTyped(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Dubai", 0, 15)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 0));
        jLabel12.setText("Apellido/s");

        jLabel13.setFont(new java.awt.Font("Dubai", 0, 15)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 0));
        jLabel13.setText("Identificador");

        jLabel14.setFont(new java.awt.Font("Dubai", 0, 15)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 0));
        jLabel14.setText("Contraseña");

        jLabel15.setFont(new java.awt.Font("Dubai", 0, 15)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 0));
        jLabel15.setText("Confirmar contraseña");

        listaTurnos.setBackground(new java.awt.Color(255, 255, 255));
        listaTurnos.setForeground(new java.awt.Color(102, 102, 102));
        listaTurnos.setSelectedItem("Tarde");
        listaTurnos.setNextFocusableComponent(campoContrasenia);

        jLabel16.setFont(new java.awt.Font("Dubai", 0, 15)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setText(" Nombre/s");

        botonGuardar.setBackground(new java.awt.Color(102, 102, 102));
        botonGuardar.setForeground(new java.awt.Color(0, 0, 0));
        botonGuardar.setText("Guardar");
        botonGuardar.setNextFocusableComponent(botonCancelar);
        botonGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGuardarActionPerformed(evt);
            }
        });

        botonCancelar.setBackground(new java.awt.Color(102, 0, 0));
        botonCancelar.setForeground(new java.awt.Color(255, 255, 255));
        botonCancelar.setText("Cancelar");
        botonCancelar.setNextFocusableComponent(campoNombre);
        botonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCancelarActionPerformed(evt);
            }
        });

        campoContrasenia.setBackground(new java.awt.Color(255, 255, 255));
        campoContrasenia.setForeground(new java.awt.Color(0, 0, 0));
        campoContrasenia.setNextFocusableComponent(campoConfirmarContrasenia);
        campoContrasenia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                campoContraseniaKeyTyped(evt);
            }
        });

        campoConfirmarContrasenia.setBackground(new java.awt.Color(255, 255, 255));
        campoConfirmarContrasenia.setForeground(new java.awt.Color(0, 0, 0));
        campoConfirmarContrasenia.setNextFocusableComponent(botonGuardar);
        campoConfirmarContrasenia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                campoConfirmarContraseniaKeyTyped(evt);
            }
        });

        jLabelError1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelError1.setForeground(new java.awt.Color(255, 0, 0));
        jLabelError1.setText("!");

        jLabelError2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelError2.setForeground(new java.awt.Color(255, 0, 0));
        jLabelError2.setText("!");

        jLabelError5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelError5.setForeground(new java.awt.Color(255, 0, 0));
        jLabelError5.setText("!");

        jLabelError6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelError6.setForeground(new java.awt.Color(255, 0, 0));
        jLabelError6.setText("!");

        jLabelError3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelError3.setForeground(new java.awt.Color(255, 0, 0));
        jLabelError3.setText("!");

        jLabelError8.setForeground(new java.awt.Color(255, 0, 0));
        jLabelError8.setText("La contraseña no cumple con: ");

        jLabelError9.setForeground(new java.awt.Color(255, 0, 0));
        jLabelError9.setText("Las contraseñas no coinciden.");

        identificador.setFont(new java.awt.Font("Dubai", 1, 15)); // NOI18N
        identificador.setForeground(new java.awt.Color(0, 0, 0));
        identificador.setText("identificador");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(listaTurnos, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelError3)))
                        .addGap(95, 95, 95)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(identificador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(189, 189, 189))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(campoNombre)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabelError1)
                                        .addGap(69, 69, 69)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(campoApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabelError2))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(campoContrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabelError5, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(70, 70, 70)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel15)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(campoConfirmarContrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabelError6))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(botonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(207, 207, 207)
                                .addComponent(botonGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabelError8, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabelError9)))
                        .addGap(0, 15, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(campoNombre))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabelError2))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(campoApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelError1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(listaTurnos, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelError3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(identificador))
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(campoContrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoConfirmarContrasenia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelError6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelError5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabelError9)
                        .addGap(0, 89, Short.MAX_VALUE))
                    .addComponent(jLabelError8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void campoNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoNombreActionPerformed

    private void campoNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoNombreKeyTyped
        if (campoNombre.getText().length() >= 50) {
            evt.consume();  // Evita que se sigan ingresando caracteres
        }
    }//GEN-LAST:event_campoNombreKeyTyped

    private void campoApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoApellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoApellidoActionPerformed

    private void campoApellidoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoApellidoKeyTyped
        if (campoApellido.getText().length() >= 50) {
            evt.consume();  // Evita que se sigan ingresando caracteres
        }
    }//GEN-LAST:event_campoApellidoKeyTyped

    private void botonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGuardarActionPerformed

    }//GEN-LAST:event_botonGuardarActionPerformed

    private void botonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarActionPerformed

    }//GEN-LAST:event_botonCancelarActionPerformed

    private void campoContraseniaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoContraseniaKeyTyped
        if (campoContrasenia.getText().length() >= 20) {
            evt.consume();  // Evita que se sigan ingresando caracteres
        }
    }//GEN-LAST:event_campoContraseniaKeyTyped

    private void campoConfirmarContraseniaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoConfirmarContraseniaKeyTyped
        if (campoConfirmarContrasenia.getText().length() >= 20) {
            evt.consume();  // Evita que se sigan ingresando caracteres
        }
    }//GEN-LAST:event_campoConfirmarContraseniaKeyTyped

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(InterfazModificarBedel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterfazModificarBedel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterfazModificarBedel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfazModificarBedel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonCancelar;
    private javax.swing.JButton botonGuardar;
    private javax.swing.JTextField campoApellido;
    private javax.swing.JPasswordField campoConfirmarContrasenia;
    private javax.swing.JPasswordField campoContrasenia;
    private javax.swing.JTextField campoNombre;
    private javax.swing.JLabel identificador;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabelError1;
    private javax.swing.JLabel jLabelError2;
    private javax.swing.JLabel jLabelError3;
    private javax.swing.JLabel jLabelError5;
    private javax.swing.JLabel jLabelError6;
    private javax.swing.JLabel jLabelError8;
    private javax.swing.JLabel jLabelError9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JComboBox<String> listaTurnos;
    // End of variables declaration//GEN-END:variables
}
