
package ds.sistemagestionreservas;

import daos.BedelPostgreSQLDAO;
import daos.Conexion;
import gestores.GestorBedel;
import gestores.GestorServiciosExternos;
import interfaces.InterfazMainAdministrador;
import javax.swing.*;

public class SistemaGestionReservas {

    public static void main(String[] args) {
        
        GestorBedel.obtenerInstancia();
        BedelPostgreSQLDAO.obtenerInstancia();
        GestorServiciosExternos.obtenerInstancia();
        Conexion.getEntityManager();
        
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Crear la interfaz gráfica en el hilo de despacho de eventos
        SwingUtilities.invokeLater(() -> {
            InterfazMainAdministrador ima = new InterfazMainAdministrador();
        });
    }
}

