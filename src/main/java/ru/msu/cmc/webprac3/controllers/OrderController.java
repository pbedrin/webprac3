package ru.msu.cmc.webprac3.controllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.msu.cmc.webprac3.DAO.ClientDAO;
import ru.msu.cmc.webprac3.DAO.ManufacturerDAO;
import ru.msu.cmc.webprac3.DAO.CarDAO;
import ru.msu.cmc.webprac3.DAO.ModelDAO;
import ru.msu.cmc.webprac3.DAO.OrderDAO;
import ru.msu.cmc.webprac3.DAO.OrderDAO.Filter;
import ru.msu.cmc.webprac3.DAO.impl.*;
import ru.msu.cmc.webprac3.models.Car;
import ru.msu.cmc.webprac3.models.Client;
import ru.msu.cmc.webprac3.models.Manufacturer;
import ru.msu.cmc.webprac3.models.Order;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class OrderController {
    @Autowired
    private final ClientDAO clientDAO = new ClientDAOImpl();

    @Autowired
    private final OrderDAO orderDAO = new OrderDAOImpl();

    @Autowired
    private final ManufacturerDAO manufacturerDAO = new ManufacturerDAOImpl();

    @Autowired
    private final ModelDAO modelDAO = new ModelDAOImpl();

    @Autowired
    private final CarDAO carDAO = new CarDAOImpl();


    @GetMapping("/orders")
    public String ordersListPage(Model model) {
        List<Order> orders = (List<Order>) orderDAO.getAll();
        model.addAttribute("orders", orders);
        model.addAttribute("clientDAO", clientDAO);
        model.addAttribute("manufacturerDAO", manufacturerDAO);
        model.addAttribute("modelDAO", modelDAO);
        model.addAttribute("carDAO", carDAO);
        model.addAttribute("orderDAO", orderDAO);
        model.addAttribute("filter", new Filter());
        return "orders";
    }

    @GetMapping("/order")
    public String orderPage(@RequestParam(name = "orderId") Long orderId, Model model) {
        Order order = orderDAO.getById(orderId);
        if (order == null) {
            model.addAttribute(
                    "error_message", String.format("Заказ %d не найден в базе данных", orderId));
            return "errorPage";
        }
        model.addAttribute("order", order);
        model.addAttribute("orderDAO", orderDAO);
        return "order";
    }

    @GetMapping("/addOrder")
    public String addOrderPage(Model model) {
        List<Car> cars = (List<Car>) carDAO.getAll();
        List<Client> clients = (List<Client>) clientDAO.getAll();
        model.addAttribute("cars", cars);
        model.addAttribute("clients", clients);
        return "addOrder";
    }

    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping("/createOrder")
    public String createOrder(
            @RequestParam(name = "orderCarId") Long orderCarId,
            @RequestParam(name = "orderClientId") Long orderClientId,
            @RequestParam(name = "orderNeedTest") Boolean orderNeedTest,
            @RequestParam(name = "orderTested") Boolean orderTested,
            @RequestParam(name = "orderStatus", required = false) Order.Status orderStatus,
            Model model
    ) {
        Order order = new Order();

        Long lastId = entityManager.createQuery("SELECT c.id FROM Order c ORDER BY c.id DESC", Long.class)
                .setMaxResults(1)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);

        Long id = lastId != null ? lastId + 1 : 1L;

        LocalDateTime currentDateTime = LocalDateTime.now();
        Car car = carDAO.getById(orderCarId);
        Client client = clientDAO.getById(orderClientId);

        order.setCar(car);
        order.setClient(client);
        order.setDate_time(currentDateTime);
        order.setTested(orderTested);
        order.setNeed_test(orderNeedTest);
        order.setStatus(orderStatus);

        orderDAO.addEntity(order);
        return "redirect:/orders";
    }

    @PostMapping("/deleteOrder")
    public String deleteOrder(@RequestParam(name = "orderId") Long orderId, Model model) {
        Order order = orderDAO.getById(orderId);
        if (order == null) {
            model.addAttribute(
                    "error_message",
                    String.format("Заказ #%d не найден в базе данных.", orderId));
            return "errorPage";
        }
        orderDAO.delete(order);
        return "redirect:/orders";
    }

    @GetMapping("/editOrder")
    public String editOrderPage(@RequestParam(name = "orderId") Long orderId, Model model) {
        Order order = orderDAO.getById(orderId);
        if (order == null) {
            model.addAttribute(
                    "error_message", String.format("Заказ %d не найден в базе данных", orderId));
            return "errorPage";
        }
        model.addAttribute("order", order);
        model.addAttribute("orderDAO", orderDAO);
        return "editOrder";
    }

    @PostMapping("/saveOrder")
    public String saveOrder(
            @RequestParam(name = "orderId") Long orderId,
            @RequestParam(name = "orderNeed_test") Boolean orderNeed_test,
            @RequestParam(name = "orderTested") Boolean orderTested,
            @RequestParam(name = "orderStatus") Order.Status orderStatus,
            Model model
    ) {
        Order order = orderDAO.getById(orderId);
        if (order == null) {
            model.addAttribute(
                    "error_message",
                    String.format("Заказа с номером %d нет в базе данных", orderId));
            return "errorPage";
        }

        order.setNeed_test(orderNeed_test);
        order.setTested(orderTested);
        order.setStatus(orderStatus);
        orderDAO.update(order);
        return String.format("redirect:/order?orderId=%d", orderId);
    }

    @GetMapping("/orders/filter")
    public String filterOrders(
            @RequestParam(name = "clientId", required = false) Long clientId,
            @RequestParam(name = "carId", required = false) Long carId,
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> endDate,
            @RequestParam(name = "needTest", required = false) Boolean needTest,
            @RequestParam(name = "tested", required = false) Boolean tested,
            @RequestParam(name = "status", required = false) Order.Status status,
            Model model) {

        Filter filter = new Filter(clientId, carId,
                startDate.map(date -> date.atStartOfDay()).orElse(null),
                endDate.map(date -> date.atStartOfDay()).orElse(null),
                needTest,
                tested,
                status);

        List<Order> filteredOrders = orderDAO.getByFilter(filter);
        model.addAttribute("orders", filteredOrders);
        model.addAttribute("clientDAO", clientDAO);
        model.addAttribute("orderDAO", orderDAO);
        model.addAttribute("carDAO", carDAO);
        model.addAttribute("filter", filter);
        return "orders";
    }

}
