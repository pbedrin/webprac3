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
import ru.msu.cmc.webprac3.DAO.ModelDAO;
import ru.msu.cmc.webprac3.DAO.ManufacturerDAO;
import ru.msu.cmc.webprac3.DAO.impl.ManufacturerDAOImpl;
import ru.msu.cmc.webprac3.DAO.impl.ModelDAOImpl;
import ru.msu.cmc.webprac3.models.Car;
import ru.msu.cmc.webprac3.models.Client;
import ru.msu.cmc.webprac3.models.Manufacturer;
import ru.msu.cmc.webprac3.models.Order;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class ManufacturerController {
    @Autowired
    private final ManufacturerDAO manufacturerDAO = new ManufacturerDAOImpl();

    @Autowired
    private final ModelDAO modelDAO = new ModelDAOImpl();

    @GetMapping("/manufacturers")
    public String manufacturersListPage(Model model) {
        List<Manufacturer> manufacturers = (List<Manufacturer>) manufacturerDAO.getAll();
        model.addAttribute("manufacturers", manufacturers);
        model.addAttribute("manufacturerDAO", manufacturerDAO);
        return "manufacturers";
    }

    @GetMapping("/manufacturer")
    public String manufacturerPage(@RequestParam(name = "manufacturerId") Long manufacturerId, Model model) {
        Manufacturer manufacturer = manufacturerDAO.getById(manufacturerId);
        if (manufacturer == null) {
            model.addAttribute(
                    "error_message", String.format("Производитель %d не найден в базе данных", manufacturerId));
            return "errorPage";
        }
        List<ru.msu.cmc.webprac3.models.Model> models = modelDAO.getAllModelsByManufacturer(manufacturer.getManufacturer());
        model.addAttribute("models", models);
        model.addAttribute("manufacturer", manufacturer);
        model.addAttribute("manufacturerDAO", manufacturerDAO);
        return "manufacturer";
    }



    @PostMapping("/saveManufacturer")
    public String saveManufacturer(
            @RequestParam(name = "manufacturerId") Long manufacturerId,
            @RequestParam(name = "manufacturerName") String manufacturerName,
            Model model
    ) {
        Manufacturer manufacturer = manufacturerDAO.getById(manufacturerId);
        if (manufacturer == null) {
            model.addAttribute(
                    "error_message",
                    String.format("Производителя с номером %d нет в салоне", manufacturerId));
            return "errorPage";
        }
        manufacturer.setManufacturer(manufacturerName);
        manufacturerDAO.update(manufacturer);
        return String.format("redirect:/manufacturer?manufacturerId=%d", manufacturerId);
    }

    @PostMapping("/deleteManufacturer")
    public String deleteManufacturer(@RequestParam(name = "manufacturerId") Long manufacturerId, Model model) {
        Manufacturer manufacturer = manufacturerDAO.getById(manufacturerId);
        if (manufacturer == null) {
            model.addAttribute(
                    "error_message",
                    String.format("Производителя с номером %d нет в салоне", manufacturerId));
            return "errorPage";
        }

        ModelDAO.Filter filter = ModelDAO.Filter.builder().manufacturer(manufacturer).build();
        List<ru.msu.cmc.webprac3.models.Model> dependentModels = modelDAO.getByFilter(filter);
        if (!dependentModels.isEmpty()) {
            model.addAttribute(
                    "error_message",
                    String.format("Невозможно удалить производителя %s, поскольку есть зависимые модели автомобилей", manufacturer.getManufacturer()));
            return "errorPage";
        }

        manufacturerDAO.delete(manufacturer);
        return "redirect:/manufacturers";
    }

    @GetMapping("/addManufacturer")
    public String addManufacturerPage(Model model) {
        return "addManufacturer";
    }

    @GetMapping("/editManufacturer")
    public String editManufacturerPage(@RequestParam(name = "manufacturerId") Long manufacturerId, Model model) {
        Manufacturer manufacturer = manufacturerDAO.getById(manufacturerId);
        if (manufacturer == null) {
            model.addAttribute(
                    "error_message", String.format("Производитель %d не найден в базе данных", manufacturerId));
            return "errorPage";
        }
        model.addAttribute("manufacturer", manufacturer);
        model.addAttribute("manufacturerDAO", manufacturerDAO);
        return "editManufacturer";
    }


    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping("/createManufacturer")
    public String createManufacturer(
            @RequestParam(name = "manufacturerName") String manufacturerName,
            Model model
    ) {
        Manufacturer manufacturer = new Manufacturer(manufacturerName);

        Long lastId = entityManager.createQuery("SELECT c.id FROM Manufacturer c ORDER BY c.id DESC", Long.class)
                .setMaxResults(1)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);

        Long id = lastId != null ? lastId + 1 : 1L;
        System.out.println(lastId);
        System.out.println(id);
        manufacturer.setId(id);

        manufacturerDAO.addEntity(manufacturer);
        return "redirect:/manufacturers";
    }
@GetMapping("/manufacturers/filter")
public String filterManufacturers(
        @RequestParam(name = "manufacturer", required = false) String manufacturer,
        Model model) {

    ManufacturerDAO.Filter filter = new ManufacturerDAO.Filter(manufacturer);

    List<Manufacturer> filteredManufacturers = manufacturerDAO.getByFilter(filter);
    model.addAttribute("manufacturers", filteredManufacturers);
    model.addAttribute("manufacturerDAO", manufacturerDAO);
    model.addAttribute("filter", filter);
    return "manufacturers";
}

}
