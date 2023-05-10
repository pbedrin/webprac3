package ru.msu.cmc.webprac3.DAO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.msu.cmc.webprac3.models.Manufacturer;
import ru.msu.cmc.webprac3.models.Model;
import ru.msu.cmc.webprac3.models.Order;

import java.time.LocalDateTime;
import java.util.List;

public interface ModelDAO extends CommonDAO<Model, Long> {

    Model getModelByName(String model);
    List<Model> getAllModelsByManufacturer(String manufacturer);

    List<Model> getByFilter(ModelDAO.Filter filter);

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public class Filter {
        private Manufacturer manufacturer;
        private String model;
    }

    static ModelDAO.Filter.FilterBuilder getFilterBuilder() {
        return ModelDAO.Filter.builder();
    }

}
