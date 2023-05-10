package ru.msu.cmc.webprac3.DAO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.msu.cmc.webprac3.models.Client;
import ru.msu.cmc.webprac3.models.Manufacturer;
import ru.msu.cmc.webprac3.models.Order;

import java.time.LocalDateTime;
import java.util.List;

public interface ManufacturerDAO extends CommonDAO<Manufacturer, Long> {

    //List<Manufacturer> getAllManufacturersByName(String manufacturer);
    Manufacturer getManufacturerByName(String manufacturer);

    List<Manufacturer> getByFilter(ManufacturerDAO.Filter filter);
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public class Filter {
        private String manufacturer;
    }
}
