package ru.msu.cmc.webprac3.DAO;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.msu.cmc.webprac3.models.Car;
import ru.msu.cmc.webprac3.models.Client;
import ru.msu.cmc.webprac3.models.Order;

import java.util.List;
import java.sql.Timestamp;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class ClientDAOTest {
    @Autowired
    private ClientDAO clientDAO;
    @Autowired
    private CarDAO carDAO;

    @Test
    void testGetClientByOrderId() {
        Client client = clientDAO.getClientByOrderId(1L);
        assertNotNull(client);
        assertEquals(client.getName(),"Вероника Валериевна Цветкова");

        client = clientDAO.getClientByOrderId(111L);
        assertNull(client);
    }

    @Test
    void testGetAllClientsByName() {
        List<Client> clients = clientDAO.getAllClientsByName("ника");
        assertNotNull(clients);
        assertEquals(1, clients.size());
        assertEquals(clients.get(0).getName(), "Вероника Валериевна Цветкова");
        System.out.print(clients.get(0).getName() + "\n");
    }

    @Test
    void testGetAllClientsByOrderCar() {
        Car car = carDAO.getById(19L);
        List<Client> clients = clientDAO.getAllClientsByOrderCar(car);
        assertNotNull(clients);
        assertEquals(1, clients.size());
        assertEquals(clients.get(0).getName(), "Вероника Валериевна Цветкова");
    }

    @Test
    void testGetFilterBuilder() {
        CarDAO.Filter filter = CarDAO.getFilterBuilder().build();
        assertNotNull(filter);
    }

    @Test
    void testGetByFilter() {
        // 1
        ClientDAO.Filter.FilterBuilder filterBuilder = new ClientDAO.Filter.FilterBuilder();
        List<Client> filteredClients = clientDAO.getByFilter(filterBuilder.build());
        assertEquals(5, filteredClients.size());

        // 2
        ClientDAO.Filter.FilterBuilder filterBuilder2 = new ClientDAO.Filter.FilterBuilder();
        filterBuilder2.status(Order.Status.CANCELED);
        filterBuilder2.needTest(Boolean.FALSE);
        filterBuilder2.tested(Boolean.FALSE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.SEPTEMBER, 27, 12, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Timestamp timestamp0 = new Timestamp(calendar.getTimeInMillis());
        calendar.set(2020, Calendar.SEPTEMBER, 30, 12, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Timestamp timestamp1 = new Timestamp(calendar.getTimeInMillis());
        filterBuilder2.startDate(timestamp0.toLocalDateTime());
        filterBuilder2.endDate(timestamp1.toLocalDateTime());

        List<Client> filteredClients2 = clientDAO.getByFilter(filterBuilder2.build());
        assertNotNull(filteredClients2);
        assertEquals(1, filteredClients2.size());
        assertEquals(filteredClients2.get(0).getName(), "Ситников Евлампий Измаилович");

        // 3
        ClientDAO.Filter.FilterBuilder filterBuilder3 = new ClientDAO.Filter.FilterBuilder();
        filterBuilder3.status(Order.Status.CANCELED);
        filterBuilder3.needTest(Boolean.FALSE);
        filterBuilder3.tested(Boolean.FALSE);

        calendar.set(2020, Calendar.SEPTEMBER, 27, 12, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        timestamp0 = new Timestamp(calendar.getTimeInMillis());
        filterBuilder3.startDate(timestamp0.toLocalDateTime());

        List<Client> filteredClients3 = clientDAO.getByFilter(filterBuilder3.build());
        assertNotNull(filteredClients3);
        assertEquals(1, filteredClients3.size());
        assertEquals(filteredClients3.get(0).getName(), "Ситников Евлампий Измаилович");

        // 4
        ClientDAO.Filter.FilterBuilder filterBuilder4 = new ClientDAO.Filter.FilterBuilder();
        filterBuilder4.status(Order.Status.CANCELED);
        filterBuilder4.needTest(Boolean.FALSE);
        filterBuilder4.tested(Boolean.FALSE);

        calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.SEPTEMBER, 30, 12, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        timestamp1 = new Timestamp(calendar.getTimeInMillis());
        filterBuilder4.endDate(timestamp1.toLocalDateTime());

        List<Client> filteredClients4 = clientDAO.getByFilter(filterBuilder4.build());
        assertNotNull(filteredClients4);
        assertEquals(1, filteredClients4.size());
        assertEquals(filteredClients4.get(0).getName(), "Ситников Евлампий Измаилович");
    }
}