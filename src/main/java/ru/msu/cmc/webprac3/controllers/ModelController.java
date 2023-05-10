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
import ru.msu.cmc.webprac3.DAO.CarDAO;
import ru.msu.cmc.webprac3.DAO.ManufacturerDAO;
import ru.msu.cmc.webprac3.DAO.ModelDAO;
import ru.msu.cmc.webprac3.DAO.impl.CarDAOImpl;
import ru.msu.cmc.webprac3.DAO.impl.ManufacturerDAOImpl;
import ru.msu.cmc.webprac3.DAO.impl.ModelDAOImpl;
import ru.msu.cmc.webprac3.models.Car;
import ru.msu.cmc.webprac3.models.Manufacturer;
import ru.msu.cmc.webprac3.models.Order;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class ModelController {
    @Autowired
    private final ModelDAO modelDAO = new ModelDAOImpl();
    @Autowired
    private final CarDAO carDAO = new CarDAOImpl();
    @Autowired
    private final ManufacturerDAO manufacturerDAO = new ManufacturerDAOImpl();

    @GetMapping("/models")
    public String modelsListPage(Model model) {
        List<ru.msu.cmc.webprac3.models.Model> models = (List<ru.msu.cmc.webprac3.models.Model>) modelDAO.getAll();
        List<Manufacturer> manufacturers = (List<Manufacturer>) manufacturerDAO.getAll();
        model.addAttribute("manufacturers", manufacturers);
        model.addAttribute("models", models);
        model.addAttribute("modelDAO", modelDAO);
        model.addAttribute("manufacturers", manufacturers);
        return "models";
    }

    @GetMapping("/model")
    public String modelPage(@RequestParam(name = "modelId") Long modelId, Model model) {
        ru.msu.cmc.webprac3.models.Model m = modelDAO.getById(modelId);
        if (m == null) {
            model.addAttribute(
                    "error_message", String.format("Производитель %d не найден в базе данных", modelId));
            return "errorPage";
        }
        List<Car> cars = carDAO.getAllCarsByModel(m.getModel());
        model.addAttribute("cars", cars);
        model.addAttribute("model", m);
        model.addAttribute("modelDAO", modelDAO);
        return "model";
    }

    @PostMapping("/saveModel")
    public String saveModel(
            @RequestParam(name = "modelId") Long modelId,
            @RequestParam(name = "manufacturerId") Long manufacturerId,
            @RequestParam(name = "modelName") String modelName,
            Model model
    ) {
        ru.msu.cmc.webprac3.models.Model m = modelDAO.getById(modelId);
        if (m == null) {
            model.addAttribute(
                    "error_message",
                    String.format("Модель с номером %d нет в салоне", modelId));
            return "errorPage";
        }
        Manufacturer manufacturer = manufacturerDAO.getById(manufacturerId);
        m.setManufacturer_id(manufacturer);
        m.setModel(modelName);
        modelDAO.update(m);
        return String.format("redirect:/model?modelId=%d", modelId);
    }

    @PostMapping("/deleteModel")
    public String deleteModel(@RequestParam(name = "modelId") Long modelId, Model model) {
        ru.msu.cmc.webprac3.models.Model m = modelDAO.getById(modelId);
        if (m == null) {
            model.addAttribute(
                    "error_message",
                    String.format("Модели с номером %d нет в салоне", modelId));
            return "errorPage";
        }
        CarDAO.Filter filter = CarDAO.Filter.builder().model(m.getModel()).build();
        List<Car> dependentCars = carDAO.getByFilter(filter);
        if (!dependentCars.isEmpty()) {
            model.addAttribute(
                    "error_message",
                    String.format("Невозможно удалить модель %s, поскольку есть зависимые автомобили", m.getModel()));
            return "errorPage";
        }
        modelDAO.delete(m);
        return "redirect:/models";
    }

    @GetMapping("/addModel")
    public String addModelPage(Model model) {
        List<Manufacturer> manufacturers = (List<Manufacturer>) manufacturerDAO.getAll();
        model.addAttribute("manufacturers", manufacturers);
        return "addModel";
    }

    @GetMapping("/editModel")
    public String editModelPage(@RequestParam(name = "modelId") Long modelId, Model model) {
        ru.msu.cmc.webprac3.models.Model m = modelDAO.getById(modelId);
        if (m == null) {
            model.addAttribute(
                    "error_message", String.format("Модель %d не найдена в базе данных", modelId));
            return "errorPage";
        }
        List<Manufacturer> manufacturers = (List<Manufacturer>) manufacturerDAO.getAll();
        model.addAttribute("manufacturers", manufacturers);
        model.addAttribute("model", m);
        model.addAttribute("modelDAO", modelDAO);
        return "editModel";
    }


    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping("/createModel")
    public String createModel(
            @RequestParam(name = "modelManufacturerId") Long modelManufacturerId,
            @RequestParam(name = "modelName") String modelName,
            Model model
    ) {
        ru.msu.cmc.webprac3.models.Model m = new ru.msu.cmc.webprac3.models.Model(modelName);

        Long lastId = entityManager.createQuery("SELECT c.id FROM ru.msu.cmc.webprac3.models.Model c ORDER BY c.id DESC", Long.class)
                .setMaxResults(1)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);

        Long id = lastId != null ? lastId + 1 : 1L;
        Manufacturer manufacturer = manufacturerDAO.getById(modelManufacturerId);
        m.setId(id);
        m.setManufacturer_id(manufacturer);
        m.setModel(modelName);

        modelDAO.addEntity(m);
        return "redirect:/models";
    }

    @GetMapping("/models/filter")
    public String filterModels(
            @RequestParam(name = "manufacturer", required = false) Long manufacturerId,
            @RequestParam(name = "name", required = false) String name,
            Model model) {

        Manufacturer manufacturer = null;
        if (manufacturerId != null) {
            manufacturer = manufacturerDAO.getById(manufacturerId);
        }
        ru.msu.cmc.webprac3.DAO.ModelDAO.Filter filter = new ru.msu.cmc.webprac3.DAO.ModelDAO.Filter(
                manufacturer,
                name);
        List<ru.msu.cmc.webprac3.models.Model> filteredModels = modelDAO.getByFilter(filter);
        model.addAttribute("models", filteredModels);
        model.addAttribute("modelDAO", modelDAO);
        model.addAttribute("filter", filter);
        return "models";
    }

}
