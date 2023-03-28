package ru.msu.cmc.webprac3.DAO;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import ru.msu.cmc.webprac3.models.Car;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource(locations="classpath:application.properties")
public class CarDAOTest {
    @Autowired
    private CarDAO carDAO;

    @Test
    void testGetCarByVin() {
        Car car = carDAO.getCarByVin("1MEHM40WX9CVTUZAL");
        assertNotNull(car);
        assertEquals(5, car.getId());

        Car badCar = carDAO.getCarByVin("BAD1MEHM40WX9CVTUZAL");
        assertNull(badCar);
    }

    @Test
    void testGetAllCarsByModel() {
        List<Car> cars = carDAO.getAllCarsByModel("LX");
        assertNotNull(cars);
        assertEquals(3, cars.size());
    }

    @Test
    void testGetAllCarsByManufacturer() {
        List<Car> cars = carDAO.getAllCarsByManufacturer("Kia");
        assertNotNull(cars);
        assertEquals(9, cars.size());
    }

    //addAttributeToCar
    @Test
    void testAddAttributeToConsumersAttrs() {
        Long carTestId = 28L;
        Car car = carDAO.getById(carTestId);
        System.out.println("id: " + car.getId() + " ConsAttrs: " + car.getConsumersAttrs());
        String jsonString = "{\"color\":\"Синий\",\"upholstery\":\"Хром\"}";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            car.setConsumersAttrs(jsonNode);
            carDAO.update(car);
            //System.out.println(jsonNode.toString());
        } catch (Exception e) {
            System.out.println("Error parsing JSON string: " + e.getMessage());
        }
        System.out.println("id: " + car.getId() + " RestoreConsAttrs: " + car.getConsumersAttrs());
        String key = "test_key";
        String value = "test_val_str";
        carDAO.addAttributeToConsumersAttrs(car, key, value);
        System.out.println("id: " + car.getId() + " UpdConsAttrs: " + car.getConsumersAttrs());
        JsonNode jsonNode = car.getConsumersAttrs();
        if (jsonNode.has(key)) {
            System.out.println("The JSON ConsumersAttrs has the key: " + key);
            String actual_value = jsonNode.get(key).asText();
            System.out.println("Key value: " + actual_value);
            assertEquals(value, actual_value);
        }
    }

    @Test
    void testAddAttributeToTechAttrs() {
        Long carTestId = 28L;
        Car car = carDAO.getById(carTestId);
        System.out.println("id: " + car.getId() + " TechAttrs: " + car.getTechAttrs());
        String jsonString = "{\"trunk_v\": 683, \"engine_p\": 81, \"engine_v\": 2.2, \"fuel_cons\": 13, \"fuel_type\": \"АИ-95\", \"num_doors\": 2, \"num_seats\": 6, \"cruise_control\": \"Есть\", \"transmission_type\": \"Вариатор\"}}";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            car.setTechAttrs(jsonNode);
            carDAO.update(car);
            //System.out.println(jsonNode.toString());
        } catch (Exception e) {
            System.out.println("Error parsing JSON string: " + e.getMessage());
        }
        System.out.println("id: " + car.getId() + " RestoreTechAttrs: " + car.getTechAttrs());
        String key = "test_key";
        String value = "abcd";
        carDAO.addAttributeToTechAttrs(car, key, value);
        System.out.println("id: " + car.getId() + " UpdTechAttrs: " + car.getTechAttrs());
        JsonNode jsonNode = car.getTechAttrs();
        if (jsonNode.has(key)) {
            System.out.println("The JSON TechAttrs has the key: " + key);
            String actual_value = jsonNode.get(key).asText();
            System.out.println("Key value: " + actual_value);
            assertEquals(value, actual_value);
        }
    }

    @Test
    void testAddAttributeTo() {
        Long carTestId = 28L;
        Car car = carDAO.getById(carTestId);
        System.out.println("id: " + car.getId() + " HistoryAttrs: " + car.getHistoryAttrs());
        String jsonString = "{\"mileage\": 20958, \"last_checkup\": 2020}";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            car.setHistoryAttrs(jsonNode);
            carDAO.update(car);
            //System.out.println(jsonNode.toString());
        } catch (Exception e) {
            System.out.println("Error parsing JSON string: " + e.getMessage());
        }
        System.out.println("id: " + car.getId() + " RestoreHistoryAttrs: " + car.getHistoryAttrs());
        String key = "test_key";
        String value = "test_val_str";
        carDAO.addAttributeToHistoryAttrs(car, key, value);
        System.out.println("id: " + car.getId() + " UpdHistoryAttrs: " + car.getHistoryAttrs());
        JsonNode jsonNode = car.getHistoryAttrs();
        if (jsonNode.has(key)) {
            System.out.println("The JSON HistoryAttrs has the key: " + key);
            String actual_value = jsonNode.get(key).asText();
            System.out.println("Key value: " + actual_value);
            assertEquals(value, actual_value);
        }
    }

    @Test
    void testGetFilterBuilder() {
        CarDAO.Filter filter = CarDAO.getFilterBuilder().build();
        assertNotNull(filter);
    }

    @Test
    void testGetByFilter() {
        CarDAO.Filter.FilterBuilder filterBuilder = new CarDAO.Filter.FilterBuilder();
        List<Car> filteredCars = carDAO.getByFilter(filterBuilder.build());
        assertEquals(36, filteredCars.size());

        CarDAO.Filter.FilterBuilder filterBuilder2 = new CarDAO.Filter.FilterBuilder();
        filterBuilder2.availability(Boolean.TRUE);
        filterBuilder2.priceStart(2000000L);
        filterBuilder2.priceEnd(2500000L);
        filterBuilder2.model("RAPID");
        filterBuilder2.year((short) 2021);
        filterBuilder2.manufacturer("Skoda");
        List<Car> filteredCars2 = carDAO.getByFilter(filterBuilder2.build());
        //System.out.println(filteredCars2.size());
        //System.out.println(filteredCars2.get(0).getVin());
        assertEquals(1, filteredCars2.size());

        CarDAO.Filter.FilterBuilder filterBuilder3 = new CarDAO.Filter.FilterBuilder();
        filterBuilder3.availability(Boolean.TRUE);
        filterBuilder3.priceStart(2000000L);
        filterBuilder3.model("RAPID");
        filterBuilder3.year((short) 2021);
        filterBuilder3.manufacturer("Skoda");
        List<Car> filteredCars3 = carDAO.getByFilter(filterBuilder3.build());
        //System.out.println(filteredCars2.size());
        //System.out.println(filteredCars2.get(0).getVin());
        assertEquals(1, filteredCars3.size());

        CarDAO.Filter.FilterBuilder filterBuilder4 = new CarDAO.Filter.FilterBuilder();
        filterBuilder4.availability(Boolean.TRUE);
        filterBuilder4.priceEnd(3900000L);
        filterBuilder4.model("RAPID");
        filterBuilder4.manufacturer("Skoda");
        List<Car> filteredCars4 = carDAO.getByFilter(filterBuilder4.build());
        //System.out.println(filteredCars4.size());
        //System.out.println(filteredCars2.get(0).getVin());
        assertEquals(3, filteredCars4.size());
    }

    @Test
    void testGetAll() {
        List<Car> carListAll = (List<Car>) carDAO.getAll();
        assertEquals(36, carListAll.size());
    }


}
