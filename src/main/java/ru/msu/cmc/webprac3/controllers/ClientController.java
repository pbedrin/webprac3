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
import ru.msu.cmc.webprac3.DAO.ClientDAO.Filter;
import ru.msu.cmc.webprac3.DAO.OrderDAO;
import ru.msu.cmc.webprac3.DAO.impl.ClientDAOImpl;
import ru.msu.cmc.webprac3.DAO.impl.OrderDAOImpl;
import ru.msu.cmc.webprac3.models.Client;
import ru.msu.cmc.webprac3.models.Order;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
public class ClientController {
    @Autowired
    private final ClientDAO clientDAO = new ClientDAOImpl();

    @Autowired
    private final OrderDAO orderDAO = new OrderDAOImpl();

    @GetMapping("/clients")
    public String clientsListPage(Model model) {
        List<Client> clients = (List<Client>) clientDAO.getAll();
        model.addAttribute("clients", clients);
        model.addAttribute("clientDAO", clientDAO);
        model.addAttribute("orderDAO", orderDAO);
        model.addAttribute("filter", new Filter());
        return "clients";
    }

    @GetMapping("/client")
    public String clientPage(@RequestParam(name = "clientId") Long clientId, Model model) {
        Client client = clientDAO.getById(clientId);
        if (client == null) {
            model.addAttribute(
                    "error_message", String.format("Клиент %d не найден в базе данных", clientId));
            return "errorPage";
        }
        List<Order> orders = orderDAO.getAllOrdersByClient(client);
        model.addAttribute("orders", orders);
        model.addAttribute("client", client);
        model.addAttribute("clientDAO", clientDAO);
        return "client";
    }



    @PostMapping("/saveClient")
    public String saveClient(
            @RequestParam(name = "clientId") Long clientId,
            @RequestParam(name = "clientName") String clientName,
            @RequestParam(name = "clientAddress") String clientAddress,
            @RequestParam(name = "clientPhone") String clientPhone,
            @RequestParam(name = "clientEmail") String clientEmail,
            Model model
    ) {
        Client client = clientDAO.getById(clientId);
        if (client == null) {
            model.addAttribute(
                    "error_message",
                    String.format("Клиента с номером %d нет в салоне", clientId));
            return "errorPage";
        }
        client.setName(clientName);
        client.setAddress(clientAddress);
        client.setPhone(clientPhone);
        client.setEmail(clientEmail);
        clientDAO.update(client);
        return String.format("redirect:/client?clientId=%d", clientId);
    }

    @PostMapping("/deleteClient")
    public String deleteClient(@RequestParam(name = "clientId") Long clientId, Model model) {
        Client client = clientDAO.getById(clientId);
        if (client == null) {
            model.addAttribute(
                    "error_message",
                    String.format("Клиента с номером %d нет в салоне", clientId));
            return "errorPage";
        }
        clientDAO.delete(client);
        return "redirect:/clients";
    }

    @GetMapping("/addClient")
    public String addClientPage(Model model) {
        return "addClient";
    }

    @GetMapping("/editClient")
    public String editClientPage(@RequestParam(name = "clientId") Long clientId, Model model) {
        Client client = clientDAO.getById(clientId);
        if (client == null) {
            model.addAttribute(
                    "error_message", String.format("Клиент %d не найден в базе данных", clientId));
            return "errorPage";
        }
        model.addAttribute("client", client);
        model.addAttribute("clientDAO", clientDAO);
        return "editClient";
    }


    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping("/createClient")
    public String createClient(
            @RequestParam(name = "clientName") String clientName,
            @RequestParam(name = "clientAddress") String clientAddress,
            @RequestParam(name = "clientPhone") String clientPhone,
            @RequestParam(name = "clientEmail") String clientEmail,
            Model model
    ) {
        Client client = new Client(clientName);
        client.setAddress(clientAddress);
        client.setPhone(clientPhone);
        client.setEmail(clientEmail);
        //client.setId(1L);

        Long lastId = entityManager.createQuery("SELECT c.id FROM Client c ORDER BY c.id DESC", Long.class)
                .setMaxResults(1)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);

        Long id = lastId != null ? lastId + 1 : 1L;
        client.setId(id);

        clientDAO.addEntity(client);
        return "redirect:/clients";
    }


    @GetMapping("/clients/filter")
    public String filterClients(
            @RequestParam(name = "status", required = false) Order.Status status,
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> endDate,
            @RequestParam(name = "needTest", required = false) Boolean needTest,
            @RequestParam(name = "tested", required = false) Boolean tested,
            @RequestParam(name = "name", required = false) String name,
            Model model) {

        Filter filter = new Filter(status,
                startDate.map(date -> date.atStartOfDay()).orElse(null),
                endDate.map(date -> date.atStartOfDay()).orElse(null),
                needTest,
                tested,
                name);

        List<Client> filteredClients = clientDAO.getByFilter(filter);
        model.addAttribute("clients", filteredClients);
        model.addAttribute("clientDAO", clientDAO);
        model.addAttribute("orderDAO", orderDAO);
        model.addAttribute("filter", filter);
        return "clients";
    }

}
