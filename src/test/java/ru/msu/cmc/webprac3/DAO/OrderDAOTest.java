package ru.msu.cmc.webprac3.DAO;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.msu.cmc.webprac3.models.Client;
import ru.msu.cmc.webprac3.models.Order;

import java.util.List;
import java.sql.Timestamp;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class OrderDAOTest {
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private ClientDAO clientDAO;

    @Test
    void testGetAllOrdersByClient() {
        Client client = clientDAO.getById(1L);
        List<Order> orders = orderDAO.getAllOrdersByClient(client);
        assertNotNull(orders);
        assertEquals(2, orders.size());
    }

    @Test
    void testGetFilterBuilder() {
        OrderDAO.Filter filter = OrderDAO.getFilterBuilder().build();
        assertNotNull(filter);
    }

    @Test
    void testGetByFilter() {
        // 1
        OrderDAO.Filter.FilterBuilder filterBuilder = new OrderDAO.Filter.FilterBuilder();
        List<Order> filteredClients = orderDAO.getByFilter(filterBuilder.build());
        assertEquals(8, filteredClients.size());

        // 2
        OrderDAO.Filter.FilterBuilder filterBuilder2 = new OrderDAO.Filter.FilterBuilder();
        filterBuilder2.status(Order.Status.CANCELED);
        filterBuilder2.needTest(Boolean.FALSE);
        filterBuilder2.tested(Boolean.FALSE);
        filterBuilder2.clientId(1L);
        filterBuilder2.carId(30L);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.SEPTEMBER, 27, 12, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Timestamp timestamp0 = new Timestamp(calendar.getTimeInMillis());
        calendar.set(2020, Calendar.SEPTEMBER, 30, 12, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Timestamp timestamp1 = new Timestamp(calendar.getTimeInMillis());
        filterBuilder2.startDate(timestamp0.toLocalDateTime());
        filterBuilder2.endDate(timestamp1.toLocalDateTime());

        List<Order> filteredClients2 = orderDAO.getByFilter(filterBuilder2.build());
        System.out.println(filteredClients2.get(0).getClient().getName());
        assertNotNull(filteredClients2);
        assertEquals(1, filteredClients2.size());

        // 3
        OrderDAO.Filter.FilterBuilder filterBuilder3 = new OrderDAO.Filter.FilterBuilder();
        filterBuilder3.status(Order.Status.CANCELED);
        filterBuilder3.needTest(Boolean.FALSE);
        filterBuilder3.tested(Boolean.FALSE);
        filterBuilder3.clientId(1L);
        filterBuilder3.carId(30L);
        calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.SEPTEMBER, 27, 12, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        timestamp0 = new Timestamp(calendar.getTimeInMillis());
        filterBuilder3.startDate(timestamp0.toLocalDateTime());

        List<Order> filteredClients3 = orderDAO.getByFilter(filterBuilder3.build());
        System.out.println(filteredClients3.get(0).getClient().getName());
        assertNotNull(filteredClients3);
        assertEquals(1, filteredClients3.size());

        // 4
        OrderDAO.Filter.FilterBuilder filterBuilder4 = new OrderDAO.Filter.FilterBuilder();
        filterBuilder4.status(Order.Status.CANCELED);
        filterBuilder4.needTest(Boolean.FALSE);
        filterBuilder4.tested(Boolean.FALSE);
        filterBuilder4.clientId(1L);
        filterBuilder4.carId(30L);
        calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.OCTOBER, 30, 12, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        timestamp1 = new Timestamp(calendar.getTimeInMillis());
        filterBuilder4.endDate(timestamp1.toLocalDateTime());

        List<Order> filteredClients4 = orderDAO.getByFilter(filterBuilder4.build());
        System.out.println(filteredClients4.get(0).getClient().getName());
        assertNotNull(filteredClients4);
        assertEquals(1, filteredClients4.size());
    }
}