package ru.msu.cmc.webprac3.DAO.impl;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webprac3.DAO.ModelDAO;
import ru.msu.cmc.webprac3.DAO.OrderDAO;
import ru.msu.cmc.webprac3.models.*;
import ru.msu.cmc.webprac3.models.Order;

import java.util.ArrayList;
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

    @Override
    public List<Model> getByFilter(ModelDAO.Filter filter) {
        try (Session session = sessionFactory.openSession()) {
            EntityManagerFactory entityManagerFactory = sessionFactory.unwrap(EntityManagerFactory.class);

            CriteriaBuilder builder = entityManagerFactory.getCriteriaBuilder();

            CriteriaQuery<Model> query = builder.createQuery(Model.class);
            Root<Model> root = query.from(Model.class);

            List<Predicate> predicates = new ArrayList<>();

            if (filter.getManufacturer() != null) {
                Join<Model, Manufacturer> manufacturers = root.join("manufacturer_id");
                predicates.add(builder.equal(manufacturers.get("id"), filter.getManufacturer().getId()));

            }

            if (filter.getModel() != null) {
                predicates.add(builder.like(root.get("model"), "%" + filter.getModel() + "%"));
            }

            if (predicates.size() != 0)
                query.select(root).where(predicates.toArray(new Predicate[0]));

            return session.getEntityManagerFactory().createEntityManager().createQuery(query).getResultList();
        }
    }

}