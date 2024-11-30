
package ds.sistemagestionreservas;

import daos.Conexion;
import gestores.GestorBedel;
import gestores.GestorReserva;
import gestores.GestorServiciosExternos;
import interfaces.InterfazMainPrincipal;
import javax.swing.*;

public class SistemaGestionReservas {

    public static void main(String[] args) {
        
        GestorBedel.obtenerInstancia();
        GestorReserva.obtenerInstancia();
        GestorServiciosExternos.obtenerInstancia();
        Conexion.getEntityManager();
        
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al intentar iniciar la aplicación.", "ERROR", JOptionPane.ERROR_MESSAGE);
        }

        // Crear la interfaz gráfica en el hilo de despacho de eventos
        SwingUtilities.invokeLater(() -> {
            new InterfazMainPrincipal();
        });
    }
}

