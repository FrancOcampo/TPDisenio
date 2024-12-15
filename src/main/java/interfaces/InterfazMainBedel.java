
package interfaces;

import controladores.ControladorMainBedel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class InterfazMainBedel extends javax.swing.JFrame {

    public InterfazMainBedel() {
        initComponents();
        setIconImage(new ImageIcon(getClass().getResource("/imagen.png")).getImage());
        ControladorMainBedel controlador = new ControladorMainBedel(this);
        botonListarReservasDia.setActionCommand("Reservas dia");
        botonListarReservasCurso.setActionCommand("Reservas curso");
        botonRegistrarReserva.addActionListener(controlador);
        botonBuscarAula.addActionListener(controlador);
        botonListarReservasDia.addActionListener(controlador);
        botonListarReservasCurso.addActionListener(controlador);
        botonCancelar.addActionListener(controlador);
        botonListarReservasDia.setText("<html>Listar reservas<br>&nbsp;&nbsp;para un día<html>");
        botonListarReservasCurso.setText("<html>Listar reservas<br>&nbsp;para un curso<html>");
        jPanel1.setLayout(null);
        setLocationRelativeTo(null);
        setTitle("Main Bedel");
        setResizable(false);
        setVisible(true);
    }
    
    public void generarPopUpNoDisponible() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Esta acción no se encuentra disponible.");
        label.setForeground(Color.BLACK); 
        label.setFont(new Font("Arial", Font.BOLD, 13)); 
        panel.add(label);
        
        JOptionPane.showMessageDialog(null, panel, "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        botonBuscarAula = new javax.swing.JButton();
        botonRegistrarReserva = new javax.swing.JButton();
        botonCancelar = new javax.swing.JButton();
        botonListarReservasDia = new javax.swing.JButton();
        botonListarReservasCurso = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setPreferredSize(new java.awt.Dimension(512, 503));

        jLabel1.setFont(new java.awt.Font("Ebrima", 1, 22)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Seleccione una opción");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        botonBuscarAula.setBackground(new java.awt.Color(102, 102, 102));
        botonBuscarAula.setFont(new java.awt.Font("Dubai", 0, 18)); // NOI18N
        botonBuscarAula.setForeground(new java.awt.Color(0, 0, 0));
        botonBuscarAula.setText("Buscar aula");
        botonBuscarAula.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        botonBuscarAula.setNextFocusableComponent(botonListarReservasDia);
        botonBuscarAula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonBuscarAulaActionPerformed(evt);
            }
        });

        botonRegistrarReserva.setBackground(new java.awt.Color(102, 102, 102));
        botonRegistrarReserva.setFont(new java.awt.Font("Dubai", 0, 18)); // NOI18N
        botonRegistrarReserva.setForeground(new java.awt.Color(0, 0, 0));
        botonRegistrarReserva.setText("Registrar reserva");
        botonRegistrarReserva.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        botonRegistrarReserva.setNextFocusableComponent(botonBuscarAula);
        botonRegistrarReserva.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRegistrarReservaActionPerformed(evt);
            }
        });

        botonCancelar.setBackground(new java.awt.Color(102, 0, 0));
        botonCancelar.setForeground(new java.awt.Color(255, 255, 255));
        botonCancelar.setText("Cancelar");
        botonCancelar.setNextFocusableComponent(botonRegistrarReserva);
        botonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCancelarActionPerformed(evt);
            }
        });

        botonListarReservasDia.setBackground(new java.awt.Color(102, 102, 102));
        botonListarReservasDia.setFont(new java.awt.Font("Dubai", 0, 18)); // NOI18N
        botonListarReservasDia.setForeground(new java.awt.Color(0, 0, 0));
        botonListarReservasDia.setText("Listar reservas para un día");
        botonListarReservasDia.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        botonListarReservasDia.setNextFocusableComponent(botonListarReservasCurso);
        botonListarReservasDia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonListarReservasDiaActionPerformed(evt);
            }
        });

        botonListarReservasCurso.setBackground(new java.awt.Color(102, 102, 102));
        botonListarReservasCurso.setFont(new java.awt.Font("Dubai", 0, 18)); // NOI18N
        botonListarReservasCurso.setForeground(new java.awt.Color(0, 0, 0));
        botonListarReservasCurso.setText("Listar reservas para un curso");
        botonListarReservasCurso.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        botonListarReservasCurso.setNextFocusableComponent(botonCancelar);
        botonListarReservasCurso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonListarReservasCursoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(191, Short.MAX_VALUE)
                .addComponent(botonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(189, 189, 189))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(botonRegistrarReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonListarReservasDia, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(botonListarReservasCurso, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonBuscarAula, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonBuscarAula, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonRegistrarReserva, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonListarReservasCurso, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(botonListarReservasDia, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                .addComponent(botonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonBuscarAulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonBuscarAulaActionPerformed

    }//GEN-LAST:event_botonBuscarAulaActionPerformed

    private void botonRegistrarReservaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRegistrarReservaActionPerformed

    }//GEN-LAST:event_botonRegistrarReservaActionPerformed

    private void botonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarActionPerformed

    }//GEN-LAST:event_botonCancelarActionPerformed

    private void botonListarReservasDiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonListarReservasDiaActionPerformed
        
    }//GEN-LAST:event_botonListarReservasDiaActionPerformed

    private void botonListarReservasCursoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonListarReservasCursoActionPerformed
        
    }//GEN-LAST:event_botonListarReservasCursoActionPerformed

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
            java.util.logging.Logger.getLogger(InterfazMainBedel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InterfazMainBedel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InterfazMainBedel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InterfazMainBedel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InterfazMainBedel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonBuscarAula;
    private javax.swing.JButton botonCancelar;
    private javax.swing.JButton botonListarReservasCurso;
    private javax.swing.JButton botonListarReservasDia;
    private javax.swing.JButton botonRegistrarReserva;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
