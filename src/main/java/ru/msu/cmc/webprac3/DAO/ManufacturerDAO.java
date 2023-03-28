package ru.msu.cmc.webprac3.DAO;

import ru.msu.cmc.webprac3.models.Manufacturer;

public interface ManufacturerDAO extends CommonDAO<Manufacturer, Long> {

    //List<Manufacturer> getAllManufacturersByName(String manufacturer);
    Manufacturer getManufacturerByName(String manufacturer);
}
