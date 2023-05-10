package ru.msu.cmc.webprac3.DAO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.msu.cmc.webprac3.models.Car;
import ru.msu.cmc.webprac3.models.Manufacturer;

import java.util.List;

public interface CarDAO extends CommonDAO<Car, Long> {

    Car getCarByVin(String vin);
    List<Car> getAllCarsByModel(String model);
    List<Car> getAllCarsByManufacturer(String manufacturer);
    void addAttributeToTechAttrs(Car car, String key, String value);
    void addAttributeToConsumersAttrs(Car car, String key, String value);
    void addAttributeToHistoryAttrs(Car car, String key, String value);
    List<Car> getByFilter(Filter filter);

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    class Filter {
        private String model;
        private Manufacturer manufacturer;
        private Short year;
        private Long priceStart;
        private Long priceEnd;
        private Boolean availability;
    }

    static Filter.FilterBuilder getFilterBuilder() {
        return Filter.builder();
    }
}
