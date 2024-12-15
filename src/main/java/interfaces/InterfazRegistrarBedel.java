
package interfaces;

import controladores.ControladorRegistrarBedel;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class InterfazRegistrarBedel extends javax.swing.JFrame {
    
    public InterfazRegistrarBedel() {
        ControladorRegistrarBedel controlador = new ControladorRegistrarBedel(this);
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/imagen.png")).getImage());
        jPanel1.setLayout(null);
        setLocationRelativeTo(null);
        botonGuardar.addActionListener(controlador);
        botonCancelar.addActionListener(controlador);
        setTitle("Registrar Bedel");
        setResizable(false);
        jLabelError1.setVisible(false);
        jLabelError2.setVisible(false);
        jLabelError3.setVisible(false);
        jLabelError4.setVisible(false);
        jLabelError5.setVisible(false);
        jLabelError6.setVisible(false);
        jLabelError7.setVisible(false);
        jLabelError8.setVisible(false);
        jLabelError9.setVisible(false);
        jLabelError10.setVisible(false);
        listaTurnos.addItem("");
        listaTurnos.addItem("Mañana");
        listaTurnos.addItem("Tarde");
        listaTurnos.addItem("Noche");
        listaTurnos.setSelectedIndex(2);
        setVisible(true);
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

    public JTextField getCampoID() {
        return campoID;
    }

    public JTextField getCampoNombre() {
        return campoNombre;
    }
    
    public String getTurno() {
        String seleccion = (String) listaTurnos.getSelectedItem();
        return seleccion;
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
    
    public void setCampoID(Border borde, boolean visibilidad){
        campoID.setBorder(borde);
        jLabelError7.setVisible(visibilidad);
        
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
      setCampoID(defaultBorder, visibilidad);
      setCampoTurno(defaultBorder, visibilidad);
      setCampoContrasenia(defaultBorder, visibilidad);
      setCampoConfirmarContrasenia(defaultBorder, visibilidad);
      jLabelError8.setText("");
      jLabelError9.setVisible(visibilidad);
      jLabelError10.setVisible(visibilidad);
      
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
    
    public JLabel getjLabelError10() {
        return jLabelError10;
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
        JLabel label = new JLabel("El bedel se registró con éxito.");
        label.setForeground(Color.BLACK); 
        label.setFont(new Font("Arial", Font.BOLD, 13)); 
        panel.add(label);
        
        JOptionPane.showMessageDialog(null, panel, "BEDEL REGISTRADO", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void crearPopUpFracaso() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Error al registrar el bedel. Por favor, inténtelo de nuevo.");
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
    
    public void setearCamposEnBlanco() {
        campoNombre.setText("");
        campoApellido.setText("");
        listaTurnos.setSelectedIndex(2);
        campoID.setText("");
        campoContrasenia.setText("");
        campoConfirmarContrasenia.setText("");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel9 = new javax.swing.JLabel();
        jLabelError4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        campoNombre = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        campoApellido = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        campoID = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        listaTurnos = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        botonGuardar = new javax.swing.JButton();
        botonCancelar = new javax.swing.JButton();
        campoContrasenia = new javax.swing.JPasswordField();
        campoConfirmarContrasenia = new javax.swing.JPasswordField();
        jLabelError1 = new javax.swing.JLabel();
        jLabelError2 = new javax.swing.JLabel();
        jLabelError5 = new javax.swing.JLabel();
        jLabelError6 = new javax.swing.JLabel();
        jLabelError7 = new javax.swing.JLabel();
        jLabelError3 = new javax.swing.JLabel();
        jLabelError8 = new javax.swing.JLabel();
        jLabelError9 = new javax.swing.JLabel();
        jLabelError10 = new javax.swing.JLabel();

        jLabel9.setText("jLabel9");

        jLabelError4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelError4.setForeground(new java.awt.Color(255, 0, 0));
        jLabelError4.setText("!");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel1.setMaximumSize(new java.awt.Dimension(469, 377));
        jPanel1.setMinimumSize(new java.awt.Dimension(469, 377));

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Ingresar datos del bedel");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

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

        jLabel2.setFont(new java.awt.Font("Dubai", 0, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Turno");

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

        jLabel3.setFont(new java.awt.Font("Dubai", 0, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Apellido/s");

        jLabel4.setFont(new java.awt.Font("Dubai", 0, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Identificador (nombre de usuario)");

        campoID.setBackground(new java.awt.Color(255, 255, 255));
        campoID.setNextFocusableComponent(campoContrasenia);
        campoID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoIDActionPerformed(evt);
            }
        });
        campoID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                campoIDKeyTyped(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Dubai", 0, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Contraseña");

        jLabel6.setFont(new java.awt.Font("Dubai", 0, 15)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Confirmar contraseña");

        listaTurnos.setBackground(new java.awt.Color(255, 255, 255));
        listaTurnos.setForeground(new java.awt.Color(102, 102, 102));
        listaTurnos.setSelectedItem("Tarde");
        listaTurnos.setNextFocusableComponent(campoID);

        jLabel7.setFont(new java.awt.Font("Dubai", 0, 15)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText(" Nombre/s");

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

        jLabelError7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelError7.setForeground(new java.awt.Color(255, 0, 0));
        jLabelError7.setText("!");

        jLabelError3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelError3.setForeground(new java.awt.Color(255, 0, 0));
        jLabelError3.setText("!");

        jLabelError8.setForeground(new java.awt.Color(255, 0, 0));
        jLabelError8.setText("La contraseña no cumple con: ");

        jLabelError9.setForeground(new java.awt.Color(255, 0, 0));
        jLabelError9.setText("Las contraseñas no coinciden.");

        jLabelError10.setForeground(new java.awt.Color(255, 0, 0));
        jLabelError10.setText("Ya existe un usuario con ese identificador.");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(189, 189, 189))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(campoNombre)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelError1)
                                .addGap(69, 69, 69)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(campoApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelError2))
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(31, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(listaTurnos, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabelError3)))
                                .addGap(95, 95, 95)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(campoID, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabelError7))
                                    .addComponent(jLabel4)
                                    .addComponent(jLabelError10)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(campoContrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabelError5, javax.swing.GroupLayout.PREFERRED_SIZE, 6, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(70, 70, 70)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
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
                        .addGap(0, 9, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(campoNombre))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
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
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelError7)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(campoID, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(listaTurnos, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelError3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelError10)
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(campoContrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelError5))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(campoConfirmarContrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabelError6)))
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

    private void botonGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGuardarActionPerformed

    }//GEN-LAST:event_botonGuardarActionPerformed

    private void campoIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoIDActionPerformed

    private void campoApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoApellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoApellidoActionPerformed

    private void campoNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoNombreActionPerformed

    private void campoNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoNombreKeyTyped
        if (campoNombre.getText().length() >= 50) { 
        evt.consume();  // Evita que se sigan ingresando caracteres
        }
    }//GEN-LAST:event_campoNombreKeyTyped

    private void campoApellidoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoApellidoKeyTyped
        if (campoApellido.getText().length() >= 50) { 
        evt.consume();  // Evita que se sigan ingresando caracteres
        }
    }//GEN-LAST:event_campoApellidoKeyTyped

    private void campoIDKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoIDKeyTyped
       if (campoID.getText().length() >= 20) { 
        evt.consume();  // Evita que se sigan ingresando caracteres
        }
    }//GEN-LAST:event_campoIDKeyTyped

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

    private void botonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarActionPerformed
        
    }//GEN-LAST:event_botonCancelarActionPerformed

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
            java.util.logging.Logger.getLogger(InterfazRegistrarBedel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterfazRegistrarBedel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterfazRegistrarBedel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfazRegistrarBedel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InterfazRegistrarBedel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonCancelar;
    private javax.swing.JButton botonGuardar;
    private javax.swing.JTextField campoApellido;
    private javax.swing.JPasswordField campoConfirmarContrasenia;
    private javax.swing.JPasswordField campoContrasenia;
    private javax.swing.JTextField campoID;
    private javax.swing.JTextField campoNombre;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelError1;
    private javax.swing.JLabel jLabelError10;
    private javax.swing.JLabel jLabelError2;
    private javax.swing.JLabel jLabelError3;
    private javax.swing.JLabel jLabelError4;
    private javax.swing.JLabel jLabelError5;
    private javax.swing.JLabel jLabelError6;
    private javax.swing.JLabel jLabelError7;
    private javax.swing.JLabel jLabelError8;
    private javax.swing.JLabel jLabelError9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JComboBox<String> listaTurnos;
    // End of variables declaration//GEN-END:variables
}
