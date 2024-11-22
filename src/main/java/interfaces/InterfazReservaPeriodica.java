
package interfaces;

import controladores.ControladorPeriodica;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class InterfazReservaPeriodica extends javax.swing.JFrame {
    
    ControladorPeriodica controlador;
    
    public InterfazReservaPeriodica() {
        initComponents();
        controlador = new ControladorPeriodica(this);
        botonConfirmarDia.addActionListener(controlador);
        botonRegistrarReserva.addActionListener(controlador);
        botonCancelar.addActionListener(controlador);
        setResizable(false);
        setLocationRelativeTo(null);
        jLabelError1.setVisible(false);
        jLabelError2.setVisible(false);
        jLabelError3.setVisible(false);
        jTable1.setRowSelectionAllowed(false);
        listaDias.addItem("");
        listaDias.addItem("Lunes");
        listaDias.addItem("Martes");
        listaDias.addItem("Miércoles");
        listaDias.addItem("Jueves");
        listaDias.addItem("Viernes");
        listaDias.addItem("Sábado");
        listaTiposAula.addItem("");
        listaTiposAula.addItem("Informática");
        listaTiposAula.addItem("Multimedios");
        listaTiposAula.addItem("Sin recursos adicionales");
        listaDias.setSelectedIndex(0);
        llenarHoras(listaHoraInicio, 8, 0);
        listaHoraInicio.setSelectedItem("8:00");
        llenarHoras(listaHoraFin, 8, 30);
        listaHoraFin.setSelectedItem("8:30");
       
        setTitle("Nueva reserva");
        // Establece un ícono transparente para evitar que se muestre el ícono de Java
        BufferedImage transparentImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        setIconImage(transparentImage);
        
        for (int i = 0; i < jTable1.getColumnCount(); i++) {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(customRenderer);
        }
        
        jSeparator1.setBackground(Color.BLACK); // Cambiar color del separador
        jSeparator1.setOpaque(true); // Hacerlo opaco
        
        jScrollPane1.getViewport().setBackground(new Color(153,153,153)); // Cambia el fondo del área vacía del JScrollPane
        jTable1.setRowHeight(20); // Cambia el alto de todas las filas 
        
        // Personalizar los encabezados de columna
        JTableHeader header = jTable1.getTableHeader();
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer() {
            
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                // Establecer el fondo negro y texto blanco
                JLabel headerLabel = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                headerLabel.setBackground(new Color(51,51,51));
                headerLabel.setForeground(Color.WHITE);
                headerLabel.setHorizontalAlignment(SwingConstants.CENTER); // Centrar el texto
                return headerLabel;
            }
        };
        
        // Asignar el renderizador de encabezado a cada columna
        header.setDefaultRenderer(headerRenderer);
        
        setVisible(true);

    }

    // Crear un renderizador personalizado para las celdas
    DefaultTableCellRenderer customRenderer = new DefaultTableCellRenderer() {
            
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            
            Component cellComponent = super.getTableCellRendererComponent(jTable1, value, isSelected, hasFocus, row, column);

            // Establecer el fondo y color de texto 
            if (isSelected) {
                cellComponent.setBackground(new Color(102,102,102)); // Color de fondo cuando está seleccionada
                cellComponent.setForeground(Color.WHITE); // Color del texto cuando está seleccionada
                setBorder(new LineBorder(new Color(51,51,51)));
            } else {
                cellComponent.setBackground(new Color(153,153,153)); // Fondo para las celdas no seleccionadas
                cellComponent.setForeground(Color.BLACK); // Texto 
                setBorder(new LineBorder(new Color(102,102,102)));
            }
            
            setHorizontalAlignment(SwingConstants.CENTER); // Centrar el texto
            
            return cellComponent;
        }
    };
    
    // Método para llenar el JComboBox con las horas
    private static void llenarHoras(JComboBox<String> comboBox, int h, int m) {
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
        Calendar calendar = Calendar.getInstance();

        // Establecer la hora y minuto iniciales
        calendar.set(Calendar.HOUR_OF_DAY, h);
        calendar.set(Calendar.MINUTE, m);

        // Establecer los límites para el día (00:00 a 23:59)
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.set(Calendar.HOUR_OF_DAY, 23);
        endCalendar.set(Calendar.MINUTE, 59);

        // Agregar las horas y minutos al JComboBox
        while (calendar.before(endCalendar) || calendar.equals(endCalendar)) {
            Date hora = calendar.getTime();
            comboBox.addItem(formatoHora.format(hora));
            calendar.add(Calendar.MINUTE, 1); // Incrementar 1 minuto
        }
    }

    public ControladorPeriodica getControlador() {
        return controlador;
    }

    public JTextField getCampoCantidadAlumnos() {
        return campoCantidadAlumnos;
    }

    public void setCampoCantidadAlumnos(JTextField campoCantidadAlumnos) {
        this.campoCantidadAlumnos = campoCantidadAlumnos;
    }

    public JLabel getjLabelError1() {
        return jLabelError1;
    }

    public void setjLabelError1(JLabel jLabelError1) {
        this.jLabelError1 = jLabelError1;
    }

    public JLabel getjLabelError2() {
        return jLabelError2;
    }

    public void setjLabelError2(JLabel jLabelError2) {
        this.jLabelError2 = jLabelError2;
    }

    public JLabel getjLabelError3() {
        return jLabelError3;
    }

    public void setjLabelError3(JLabel jLabelError3) {
        this.jLabelError3 = jLabelError3;
    }

    public JComboBox<String> getListaDias() {
        return listaDias;
    }

    public void setListaDias(JComboBox<String> listaDias) {
        this.listaDias = listaDias;
    }

    public JComboBox<String> getListaHoraFin() {
        return listaHoraFin;
    }

    public void setListaHoraFin(JComboBox<String> listaHoraFin) {
        this.listaHoraFin = listaHoraFin;
    }

    public JComboBox<String> getListaHoraInicio() {
        return listaHoraInicio;
    }

    public void setListaHoraInicio(JComboBox<String> listaHoraInicio) {
        this.listaHoraInicio = listaHoraInicio;
    }

    public JComboBox<String> getListaTiposAula() {
        return listaTiposAula;
    }

    public void setListaTiposAula(JComboBox<String> listaTiposAula) {
        this.listaTiposAula = listaTiposAula;
    }
    
    public String getTipoAula() {
        
        String seleccion = (String) listaTiposAula.getSelectedItem();
        return seleccion;
    }
    
    public String getHoraInicio() {
        
        String seleccion = (String) listaHoraInicio.getSelectedItem();
        return seleccion;
    }
    
    public String getHoraFin() {
        
        String seleccion = (String) listaHoraFin.getSelectedItem();
        return seleccion;
    }
    
    public String getDia() {
        
        String seleccion = (String) listaDias.getSelectedItem();
        return seleccion;
    }
    
    public void setCampoCantidadAlumnos(Border borde, boolean visibilidad){
         campoCantidadAlumnos.setBorder(borde);
         jLabelError1.setVisible(visibilidad);
    }
    
    public void setCampoAula(Border borde, boolean visibilidad){
         listaTiposAula.setBorder(borde);
         jLabelError2.setVisible(visibilidad);
    }
    
    public void setCampoDia(Border borde, boolean visibilidad){
         listaDias.setBorder(borde);
         jLabelError3.setVisible(visibilidad);
    }
    
    public void setCamposHora(Border borde, boolean visibilidad){
         listaHoraInicio.setBorder(borde);
         listaHoraFin.setBorder(borde);
    }
    
    public void desmarcarCampos() {
        
      Border defaultBorder = new JTextField().getBorder();
      boolean visibilidad = false;
      
      setCampoCantidadAlumnos(defaultBorder, visibilidad);
      setCampoAula(defaultBorder, visibilidad);
      setCampoDia(defaultBorder, visibilidad);
      setCamposHora(defaultBorder, visibilidad);
      jLabelError1.setVisible(visibilidad);
      jLabelError2.setVisible(visibilidad);
      jLabelError3.setVisible(visibilidad);
    }
    
    public DefaultTableModel getModel() {
        return (DefaultTableModel) jTable1.getModel();
    }
    
    public void crearPopUpAdvertencia(String mensaje) {
        JPanel panel = new JPanel();
        JLabel label = new JLabel(mensaje);
        label.setForeground(Color.BLACK); 
        label.setFont(new Font("Arial", Font.BOLD, 13)); 
        panel.add(label);
        
        JOptionPane.showMessageDialog(null, panel, "ADVERTENCIA", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public int confirmarContinuacion(String mensaje) {
        String[] opciones = {"Aceptar", "Cancelar"};
        // Mostrar el diálogo con las opciones personalizadas
        int respuesta = JOptionPane.showOptionDialog(
            null,                                // Componente padre (null para centrar)
            mensaje, // Mensaje
            "ADVERTENCIA",                       // Título
            JOptionPane.DEFAULT_OPTION,          // Tipo de opción (sin botones por defecto)
            JOptionPane.INFORMATION_MESSAGE,     // Tipo de mensaje
            null,                                // Ícono (null para usar el ícono por defecto)
            opciones,                            // Los botones personalizados
            opciones[0]                          // Botón por defecto (primera opción)
        );
        
        return respuesta;
    }
    
    public void crearPopUpExito() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("La reserva se registró con éxito.");
        label.setForeground(Color.BLACK); 
        label.setFont(new Font("Arial", Font.BOLD, 13)); 
        panel.add(label);
        
        JOptionPane.showMessageDialog(null, panel, "RESERVA REGISTRADA", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void setearCamposEnBlanco() {
        campoCantidadAlumnos.setText("");
        listaTiposAula.setSelectedItem("");
        listaDias.setSelectedItem("");
        listaHoraInicio.setSelectedItem("08:00");
        listaHoraFin.setSelectedItem("08:30");
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Eliminar todas las filas de la tabla
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        campoCantidadAlumnos = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        botonCancelar = new javax.swing.JButton();
        jLabelError1 = new javax.swing.JLabel();
        jLabelError2 = new javax.swing.JLabel();
        listaTiposAula = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        listaDias = new javax.swing.JComboBox<>();
        jLabelError3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        listaHoraInicio = new javax.swing.JComboBox<>();
        listaHoraFin = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        botonConfirmarDia = new javax.swing.JButton();
        botonRegistrarReserva = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Ingresar datos de la reserva");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jLabel3.setFont(new java.awt.Font("Dubai", 0, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Cantidad de alumnos:");

        campoCantidadAlumnos.setBackground(new java.awt.Color(255, 255, 255));
        campoCantidadAlumnos.setNextFocusableComponent(listaTiposAula);
        campoCantidadAlumnos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoCantidadAlumnosActionPerformed(evt);
            }
        });
        campoCantidadAlumnos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                campoCantidadAlumnosKeyTyped(evt);
            }
        });

        jTable1.setBackground(new java.awt.Color(102, 102, 102));
        jTable1.setForeground(new java.awt.Color(0, 0, 0));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Aula", "Tipo", "Curso", "Día", "Hora inicio", "Hora fin"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        botonCancelar.setBackground(new java.awt.Color(102, 0, 0));
        botonCancelar.setForeground(new java.awt.Color(255, 255, 255));
        botonCancelar.setText("Cancelar");
        botonCancelar.setNextFocusableComponent(campoCantidadAlumnos);
        botonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCancelarActionPerformed(evt);
            }
        });

        jLabelError1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelError1.setForeground(new java.awt.Color(255, 0, 0));
        jLabelError1.setText("!");

        jLabelError2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelError2.setForeground(new java.awt.Color(255, 0, 0));
        jLabelError2.setText("!");

        listaTiposAula.setBackground(new java.awt.Color(255, 255, 255));
        listaTiposAula.setForeground(new java.awt.Color(102, 102, 102));
        listaTiposAula.setSelectedItem("Tarde");
        listaTiposAula.setNextFocusableComponent(listaDias);

        jLabel5.setFont(new java.awt.Font("Dubai", 0, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Tipo de aula");

        jLabel6.setFont(new java.awt.Font("Dubai", 0, 15)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Tipo de reserva: PERIÓDICA");

        jLabel7.setFont(new java.awt.Font("Dubai", 0, 15)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Día");

        listaDias.setBackground(new java.awt.Color(255, 255, 255));
        listaDias.setForeground(new java.awt.Color(102, 102, 102));
        listaDias.setSelectedItem("Tarde");
        listaDias.setNextFocusableComponent(listaHoraInicio);

        jLabelError3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabelError3.setForeground(new java.awt.Color(255, 0, 0));
        jLabelError3.setText("!");

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jPanel2.setBackground(new java.awt.Color(226, 224, 224));

        listaHoraInicio.setBackground(new java.awt.Color(255, 255, 255));
        listaHoraInicio.setForeground(new java.awt.Color(102, 102, 102));
        listaHoraInicio.setSelectedItem("Tarde");
        listaHoraInicio.setNextFocusableComponent(listaHoraFin);

        listaHoraFin.setBackground(new java.awt.Color(255, 255, 255));
        listaHoraFin.setForeground(new java.awt.Color(102, 102, 102));
        listaHoraFin.setSelectedItem("Tarde");
        listaHoraFin.setNextFocusableComponent(botonConfirmarDia);

        jLabel8.setFont(new java.awt.Font("Dubai", 0, 15)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Hora de inicio");

        jLabel9.setFont(new java.awt.Font("Dubai", 0, 15)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Hora de fin");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(listaHoraFin, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(listaHoraInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel8))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel9)))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(listaHoraInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(listaHoraFin, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        botonConfirmarDia.setBackground(new java.awt.Color(89, 89, 89));
        botonConfirmarDia.setForeground(new java.awt.Color(255, 255, 255));
        botonConfirmarDia.setText("Confirmar día");
        botonConfirmarDia.setNextFocusableComponent(botonRegistrarReserva);
        botonConfirmarDia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonConfirmarDiaActionPerformed(evt);
            }
        });

        botonRegistrarReserva.setBackground(new java.awt.Color(102, 102, 102));
        botonRegistrarReserva.setForeground(new java.awt.Color(0, 0, 0));
        botonRegistrarReserva.setText("Finalizar reserva");
        botonRegistrarReserva.setNextFocusableComponent(botonCancelar);
        botonRegistrarReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRegistrarReservaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(listaTiposAula, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabelError2, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel5)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(campoCantidadAlumnos, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabelError1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel6))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel7)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(listaDias, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabelError3, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(66, 66, 66)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)))
                .addGap(29, 29, 29))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(botonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botonRegistrarReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botonConfirmarDia, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(campoCantidadAlumnos, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelError1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(listaTiposAula, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelError2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(27, 27, 27)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(listaDias, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelError3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(botonConfirmarDia, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonRegistrarReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
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

    private void botonConfirmarDiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonConfirmarDiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonConfirmarDiaActionPerformed

    private void botonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarActionPerformed

    }//GEN-LAST:event_botonCancelarActionPerformed

    private void campoCantidadAlumnosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoCantidadAlumnosKeyTyped
        if (campoCantidadAlumnos.getText().length() >= 3) {
            evt.consume();  // Evita que se sigan ingresando caracteres
        }
    }//GEN-LAST:event_campoCantidadAlumnosKeyTyped

    private void campoCantidadAlumnosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoCantidadAlumnosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoCantidadAlumnosActionPerformed

    private void botonRegistrarReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRegistrarReservaActionPerformed

    }//GEN-LAST:event_botonRegistrarReservaActionPerformed

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
            java.util.logging.Logger.getLogger(InterfazReservaPeriodica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterfazReservaPeriodica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterfazReservaPeriodica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfazReservaPeriodica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InterfazReservaPeriodica().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonCancelar;
    private javax.swing.JButton botonConfirmarDia;
    private javax.swing.JButton botonRegistrarReserva;
    private javax.swing.JTextField campoCantidadAlumnos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelError1;
    private javax.swing.JLabel jLabelError2;
    private javax.swing.JLabel jLabelError3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox<String> listaDias;
    private javax.swing.JComboBox<String> listaHoraFin;
    private javax.swing.JComboBox<String> listaHoraInicio;
    private javax.swing.JComboBox<String> listaTiposAula;
    // End of variables declaration//GEN-END:variables
}
