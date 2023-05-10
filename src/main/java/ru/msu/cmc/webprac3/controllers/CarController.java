package ru.msu.cmc.webprac3.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.msu.cmc.webprac3.DAO.ManufacturerDAO;
import ru.msu.cmc.webprac3.DAO.CarDAO;
import ru.msu.cmc.webprac3.DAO.ModelDAO;
import ru.msu.cmc.webprac3.DAO.CarDAO.Filter;
import ru.msu.cmc.webprac3.DAO.impl.*;
import ru.msu.cmc.webprac3.models.Car;
import ru.msu.cmc.webprac3.models.Manufacturer;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;
import java.util.Map;

@Controller
public class CarController {
    @Autowired
    private final CarDAO carDAO = new CarDAOImpl();

    @Autowired
    private final ManufacturerDAO manufacturerDAO = new ManufacturerDAOImpl();

    @Autowired
    private final ModelDAO modelDAO = new ModelDAOImpl();


    @GetMapping("/cars")
    public String carsListPage(Model model) {
        List<Car> cars = (List<Car>) carDAO.getAll();
        List<Manufacturer> manufacturers = (List<Manufacturer>) manufacturerDAO.getAll();
        List<ru.msu.cmc.webprac3.models.Model> models = (List<ru.msu.cmc.webprac3.models.Model>) modelDAO.getAll();
        model.addAttribute("cars", cars);
        model.addAttribute("carDAO", carDAO);
        model.addAttribute("manufacturerDAO", manufacturerDAO);
        model.addAttribute("modelDAO", modelDAO);
        model.addAttribute("manufacturers", manufacturers);
        model.addAttribute("models", models);
        model.addAttribute("filter", new Filter());
        return "cars";
    }

    @GetMapping("/car")
    public String carPage(@RequestParam(name = "carId") Long carId, Model model) {
        Car car = carDAO.getById(carId);
        if (car == null) {
            model.addAttribute(
                    "error_message", String.format("Автомобиль %d не найден в базе данных", carId));
            return "errorPage";
        }
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, String> consumersAttrsMap = null;
        Map<String, String> techAttrsMap = null;
        Map<String, String> historyAttrsMap = null;
        try {
            consumersAttrsMap = objectMapper.convertValue(car.getConsumersAttrs(), Map.class);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error_message", "Не удалось преобразовать атрибуты потребителей в Map");
            return "errorPage";
        }
        try {
            techAttrsMap = objectMapper.convertValue(car.getTechAttrs(), Map.class);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error_message", "Не удалось преобразовать технические атрибуты в Map");
            return "errorPage";
        }
        try {
            historyAttrsMap = objectMapper.convertValue(car.getHistoryAttrs(), Map.class);
        } catch (IllegalArgumentException e) {
            model.addAttribute("error_message", "Не удалось преобразовать исторические атрибуты в Map");
            return "errorPage";
        }
        model.addAttribute("car", car);
        model.addAttribute("carDAO", carDAO);
        model.addAttribute("consumersAttrsJson", consumersAttrsMap);
        model.addAttribute("techAttrsJson", techAttrsMap);
        model.addAttribute("historyAttrsJson", historyAttrsMap);
        return "car";
    }


    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/addCar")
    public String addCarPage(Model model) {
        List<ru.msu.cmc.webprac3.models.Model> models = (List<ru.msu.cmc.webprac3.models.Model>) modelDAO.getAll();
        List<Manufacturer> manufacturers = (List<Manufacturer>) manufacturerDAO.getAll();
        model.addAttribute("models", models);
        model.addAttribute("manufacturers", manufacturers);
        return "addCar";
    }

    @PostMapping("/createCar")
    public String createCar(
            @RequestParam(name = "carModelId") Long carModelId,
            @RequestParam(name = "carVin") String carVin,
            @RequestParam(name = "carYear") Short carYear,
            @RequestParam(name = "carPrice") Long carPrice,
            @RequestParam(name = "ckey[]", required = false) List<String> ckeys,
            @RequestParam(name = "cvalue[]", required = false) List<String> cvalues,
            @RequestParam(name = "tkey[]", required = false) List<String> tkeys,
            @RequestParam(name = "tvalue[]", required = false) List<String> tvalues,
            @RequestParam(name = "hkey[]", required = false) List<String> hkeys,
            @RequestParam(name = "hvalue[]", required = false) List<String> hvalues,
            @RequestParam(name = "device[]", required = false) List<String> devices,
            Model model
    ) {
        Car car = new Car();

        Long lastId = entityManager.createQuery("SELECT c.id FROM Car c ORDER BY c.id DESC", Long.class)
                .setMaxResults(1)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);

        Long id = lastId != null ? lastId + 1 : 1L;
        ru.msu.cmc.webprac3.models.Model m = modelDAO.getById(carModelId);
        car.setId(id);
        car.setModel_id(m);
        car.setPrice(carPrice);
        car.setYear(carYear);
        car.setVin(carVin);
        car.setAvailability(Boolean.TRUE);
        car.setDevices(devices);

        System.out.println(ckeys);
        System.out.println(cvalues);
        if (ckeys != null && cvalues != null && ckeys.size() == cvalues.size()) {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode consumersAttrs = mapper.createObjectNode();
            for (int i = 0; i < ckeys.size(); i++) {
                consumersAttrs.put(ckeys.get(i), cvalues.get(i));
            }
            car.setConsumersAttrs(consumersAttrs);
        }
        if (tkeys != null && tvalues != null && tkeys.size() == tvalues.size()) {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode techAttrs = mapper.createObjectNode();
            for (int i = 0; i < tkeys.size(); i++) {
                techAttrs.put(tkeys.get(i), tvalues.get(i));
            }
            car.setTechAttrs(techAttrs);
        }
        if (hkeys != null && hvalues != null && hkeys.size() == hvalues.size()) {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode historyAttrs = mapper.createObjectNode();
            for (int i = 0; i < hkeys.size(); i++) {
                historyAttrs.put(hkeys.get(i), hvalues.get(i));
            }
            car.setHistoryAttrs(historyAttrs);
        }

        carDAO.addEntity(car);
        return "redirect:/cars";
    }

    @PostMapping("/deleteCar")
    public String deleteCar(@RequestParam(name = "carId") Long carId, Model model) {
        Car car = carDAO.getById(carId);
        if (car == null) {
            model.addAttribute(
                    "error_message",
                    String.format("Автомобиль #%d не найден в базе данных.", carId));
            return "errorPage";
        }
        carDAO.delete(car);
        return "redirect:/cars";
    }

    @GetMapping("/editCar")
    public String editCarPage(@RequestParam(name = "carId") Long carId, Model model) {
        Car car = carDAO.getById(carId);
        if (car == null) {
            model.addAttribute(
                    "error_message", String.format("Заказ %d не найден в базе данных", carId));
            return "errorPage";
        }
        List<ru.msu.cmc.webprac3.models.Model> models = (List<ru.msu.cmc.webprac3.models.Model>) modelDAO.getAll();
        List<Manufacturer> manufacturers = (List<Manufacturer>) manufacturerDAO.getAll();
        model.addAttribute("models", models);
        model.addAttribute("manufacturers", manufacturers);
        model.addAttribute("car", car);
        model.addAttribute("carDAO", carDAO);
        return "editCar";
    }

    @PostMapping("/saveCar")
    public String saveCar(
            @RequestParam(name = "carId") Long carId,
            @RequestParam(name = "carModelId") Long carModelId,
            @RequestParam(name = "carPrice") Long carPrice,
            @RequestParam(name = "carYear") Short carYear,
            @RequestParam(name = "carVin") String carVin,
            @RequestParam(name = "carAvailability") Boolean carAvailability,
            @RequestParam(name = "ckey[]", required = false) List<String> ckeys,
            @RequestParam(name = "cvalue[]", required = false) List<String> cvalues,
            @RequestParam(name = "tkey[]", required = false) List<String> tkeys,
            @RequestParam(name = "tvalue[]", required = false) List<String> tvalues,
            @RequestParam(name = "hkey[]", required = false) List<String> hkeys,
            @RequestParam(name = "hvalue[]", required = false) List<String> hvalues,
            @RequestParam(name = "device[]", required = false) List<String> devices,
            Model model
    ) {
        Car car = carDAO.getById(carId);
        if (car == null) {
            model.addAttribute(
                    "error_message",
                    String.format("Автомобиля с номером %d нет в базе данных", carId));
            return "errorPage";
        }
        ru.msu.cmc.webprac3.models.Model m = modelDAO.getById(carModelId);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode newCustomerAttrs = objectMapper.createObjectNode();
        ObjectNode newTechAttrs = objectMapper.createObjectNode();
        ObjectNode newHistoryAttrs = objectMapper.createObjectNode();

        if (ckeys != null && cvalues != null && ckeys.size() == cvalues.size()) {
            for (int i = 0; i < ckeys.size(); i++) {
                newCustomerAttrs.put(ckeys.get(i), cvalues.get(i));
            }
        }
        if (tkeys != null && tvalues != null && tkeys.size() == tvalues.size()) {
            for (int i = 0; i < tkeys.size(); i++) {
                newTechAttrs.put(tkeys.get(i), tvalues.get(i));
            }
        }
        if (hkeys != null && hvalues != null && hkeys.size() == hvalues.size()) {
            for (int i = 0; i < hkeys.size(); i++) {
                newHistoryAttrs.put(hkeys.get(i), hvalues.get(i));
            }
        }

        car.setDevices(devices);
        car.setConsumersAttrs(newCustomerAttrs);
        car.setTechAttrs(newTechAttrs);
        car.setHistoryAttrs(newHistoryAttrs);
        car.setPrice(carPrice);
        car.setAvailability(carAvailability);
        car.setModel_id(m);
        car.setYear(carYear);
        car.setVin(carVin);
        carDAO.update(car);
        return String.format("redirect:/car?carId=%d", carId);
    }

    @GetMapping("/cars/filter")
    public String filterCars(
            @RequestParam(name = "model", required = false) String carModel,
            @RequestParam(name = "manufacturer", required = false) Long manufacturerId,
            @RequestParam(name = "year", required = false) Short year,
            @RequestParam(name = "priceStart", required = false) Long priceStart,
            @RequestParam(name = "priceEnd", required = false) Long priceEnd,
            @RequestParam(name = "availability", required = false) Boolean availability,
            Model model) {

        Manufacturer manufacturer = null;
        if (manufacturerId != null) {
            manufacturer = manufacturerDAO.getById(manufacturerId);
        }
        Filter filter = new Filter(
                carModel,
                manufacturer,
                year,
                priceStart,
                priceEnd,
                availability
        );

        System.out.println("Start:" + priceStart);
        System.out.println("End:" + priceEnd);

        List<Manufacturer> manufacturers = (List<Manufacturer>) manufacturerDAO.getAll();
        List<ru.msu.cmc.webprac3.models.Model> models = (List<ru.msu.cmc.webprac3.models.Model>) modelDAO.getAll();
        List<Car> filteredCars = carDAO.getByFilter(filter);
        model.addAttribute("cars", filteredCars);
        model.addAttribute("carDAO", carDAO);
        model.addAttribute("modelDAO", modelDAO);
        model.addAttribute("manufacturerDAO", manufacturerDAO);
        model.addAttribute("manufacturers", manufacturers);
        model.addAttribute("models", models);
        model.addAttribute("filter", filter);
        return "cars";
    }

}