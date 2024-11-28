package TestDao;

import daos.Conexion;
import daos.ReservaPostgreSQLDAO;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import model.Reserva;
import excepciones.OperacionException;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Conexion.class})
@ExtendWith(MockitoExtension.class)
public class ReservaPostgreSQLDAOTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private EntityTransaction transaction;

    @InjectMocks
    private ReservaPostgreSQLDAO reservaPostgreSQLDAO;

    private Reserva reserva;

    @BeforeEach
    public void setUp() {
        reserva = new Reserva();
        // Inicializa los atributos de reserva según sea necesario
    }

    @Test
    public void testRegistrarReserva() throws OperacionException {
        PowerMockito.mockStatic(Conexion.class);
        when(Conexion.getEntityManager()).thenReturn(entityManager);
        when(entityManager.getTransaction()).thenReturn(transaction);

        reservaPostgreSQLDAO.registrarReserva(reserva);

        verify(transaction).begin();
        verify(entityManager).persist(reserva);
        verify(transaction).commit();
    }

    /*@Test
    public void testRegistrarReservaWithException() {
        when(entityManager.getTransaction()).thenReturn(transaction);
        doThrow(new RuntimeException()).when(entityManager).persist(reserva);

        assertThrows(OperacionException.class, () -> {
            reservaPostgreSQLDAO.registrarReserva(reserva);
        });

        verify(transaction).begin();
        verify(transaction).rollback();
    }*/
}