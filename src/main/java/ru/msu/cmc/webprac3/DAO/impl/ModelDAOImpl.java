package ru.msu.cmc.webprac3.DAO.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webprac3.DAO.ModelDAO;
import ru.msu.cmc.webprac3.models.Model;

import java.util.List;

@Repository
public class ModelDAOImpl extends CommonDAOImpl<Model, Long> implements ModelDAO {

    public ModelDAOImpl(){
        super(Model.class);
    }

    @Override
    public Model getModelByName(String model) {
        try (Session session = sessionFactory.openSession()) {
            Query<Model> query = session.createQuery("FROM Model WHERE model " +
                    "LIKE :model", Model.class).setParameter("model", "%" + model + "%");
            return query.uniqueResult();
        }
    }

    @Override
    public List<Model> getAllModelsByManufacturer(String manufacturer) {
        try (Session session = sessionFactory.openSession()) {
            Query<Model> query = session.createQuery("FROM Model WHERE manufacturer_id.manufacturer LIKE :manufacturer", Model.class)
                    .setParameter("manufacturer", "%" + manufacturer + "%");
            return query.getResultList();
        }
    }

}