package ru.msu.cmc.webprac3.DAO;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Getter;
import ru.msu.cmc.webprac3.models.Car;

import java.math.BigDecimal;
import java.util.List;

public interface CarDAO extends CommonDAO<Car, Long> {

    Car getCarByVin(String vin);
    List<Car> getAllCarsByModel(String model);
    List<Car> getAllCarsByManufacturer(String manufacturer);
    void addAttributeToCar(JsonNode carAttributes, String key, String value, Long carId);
    List<Car> getByFilter(Filter filter);

    @Builder
    @Getter
    class Filter {
        private String model;
        private String manufacturer;
        private Short year;
        private BigDecimal priceStart;
        private BigDecimal priceEnd;
        private Boolean availability;
    }

    static Filter.FilterBuilder getFilterBuilder() {
        return Filter.builder();
    }
}
