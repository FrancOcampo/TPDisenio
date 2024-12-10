
package interfaces;

import controladores.ControladorEsporadica;
import java.awt.Color;
import java.awt.Component;
import java.awt.image.BufferedImage;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import controladores.ControladorPeriodica;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

public class InterfazAulasSolapadas extends javax.swing.JFrame {

    public InterfazAulasSolapadas() {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Nueva reserva");
        botonContinuar.requestFocusInWindow();
        jTable1.setRowSelectionAllowed(false);
        // Establece un ícono transparente para evitar que se muestre el ícono de Java
        BufferedImage transparentImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        setIconImage(transparentImage);
        
        for (int i = 0; i < jTable1.getColumnCount(); i++) {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(customRenderer);
        }
        
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
        
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        
        setVisible(true);

    }

    public void setControlador(ControladorPeriodica controlador) {
        botonContinuar.addActionListener(controlador);
    }
    
    public void setControlador(ControladorEsporadica controlador) {
        botonContinuar.addActionListener(controlador);
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
    
    public DefaultTableModel getModel() {
        return (DefaultTableModel) jTable1.getModel();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        botonContinuar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jTable1.setBackground(new java.awt.Color(102, 102, 102));
        jTable1.setForeground(new java.awt.Color(0, 0, 0));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Aula", "Solicitante", "Curso", "Contacto", "Hora inicio", "Hora fin"
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
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(100);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(1).setMinWidth(150);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(150);
            jTable1.getColumnModel().getColumn(4).setMinWidth(70);
            jTable1.getColumnModel().getColumn(4).setMaxWidth(70);
            jTable1.getColumnModel().getColumn(5).setMinWidth(70);
            jTable1.getColumnModel().getColumn(5).setMaxWidth(70);
        }

        jLabel16.setFont(new java.awt.Font("Dubai", 0, 15)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 0));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Listado de aulas que menos se solapan");

        botonContinuar.setBackground(new java.awt.Color(102, 102, 102));
        botonContinuar.setForeground(new java.awt.Color(0, 0, 0));
        botonContinuar.setText("Continuar");
        botonContinuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonContinuarActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(153, 0, 0));

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Ebrima", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("Advertencia: no se encontraron aulas que cumplan los requisitos.");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(4, 4, 4)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 767, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(botonContinuar, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addComponent(botonContinuar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonContinuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonContinuarActionPerformed

    }//GEN-LAST:event_botonContinuarActionPerformed

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
            java.util.logging.Logger.getLogger(InterfazAulasSolapadas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterfazAulasSolapadas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterfazAulasSolapadas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfazAulasSolapadas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InterfazAulasSolapadas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonContinuar;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
