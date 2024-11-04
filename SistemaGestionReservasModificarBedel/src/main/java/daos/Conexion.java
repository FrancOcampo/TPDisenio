
package daos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Conexion {
    
   private static EntityManagerFactory entityManagerFactory;
   private static ThreadLocal<EntityManager> entityManagerThreadLocal = new ThreadLocal<>();

    static {
        // Crea el EntityManagerFactory una vez cuando la clase se carga
        entityManagerFactory = Persistence.createEntityManagerFactory("SistemaReservasAulas");
    }

    // Método para obtener el EntityManager
    public static EntityManager getEntityManager() {
        // Verifica si ya hay un EntityManager para el hilo actual
        EntityManager entityManager = entityManagerThreadLocal.get();
        if (entityManager == null) {
            // Si no hay, crea uno nuevo y lo guarda en el ThreadLocal
            entityManager = entityManagerFactory.createEntityManager();
            entityManagerThreadLocal.set(entityManager);
        }
        return entityManager;
    }

    // Método para cerrar el EntityManager
    public static void closeEntityManager() {
        EntityManager entityManager = entityManagerThreadLocal.get();
        if (entityManager != null) {
            entityManager.close();
            entityManagerThreadLocal.remove(); // Limpia el ThreadLocal
        }
    }

    // Método para cerrar el EntityManagerFactory
    public static void closeEntityManagerFactory() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }     
}
