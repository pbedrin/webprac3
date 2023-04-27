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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.msu.cmc.webprac3.DAO.*;
import ru.msu.cmc.webprac3.DAO.impl.ClientDAOImpl;
import ru.msu.cmc.webprac3.DAO.impl.OrderDAOImpl;
import ru.msu.cmc.webprac3.models.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

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
        //System.out.println(client.getId());
        //System.out.println(client.getName());

        clientDAO.addEntity(client);
        return "redirect:/clients";
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
        return "redirect:/clients";
    }
}
