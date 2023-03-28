package ru.msu.cmc.webprac3.DAO;

import ru.msu.cmc.webprac3.models.Model;

import java.util.List;

public interface ModelDAO extends CommonDAO<Model, Long> {

    Model getModelByName(String model);
    List<Model> getAllModelsByManufacturer(String manufacturer);

}
