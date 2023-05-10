package ru.msu.cmc.webprac3.DAO.impl;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webprac3.DAO.ClientDAO;
import ru.msu.cmc.webprac3.DAO.ManufacturerDAO;
import ru.msu.cmc.webprac3.models.Client;
import ru.msu.cmc.webprac3.models.Manufacturer;
import ru.msu.cmc.webprac3.models.Order;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ManufacturerDAOImpl extends CommonDAOImpl<Manufacturer, Long> implements ManufacturerDAO {
    public ManufacturerDAOImpl(){
        super(Manufacturer.class);
    }

    @Override
    public Manufacturer getManufacturerByName(String manufacturer) {
        try (Session session = sessionFactory.openSession()) {
            Query<Manufacturer> query = session.createQuery("FROM Manufacturer WHERE manufacturer = :name",
                    Manufacturer.class).setParameter("name", manufacturer);
            return query.uniqueResult();
        }
    }

    @Override
    public List<Manufacturer> getByFilter(ManufacturerDAO.Filter filter) {
        try (Session session = sessionFactory.openSession()) {
            EntityManagerFactory entityManagerFactory = sessionFactory.unwrap(EntityManagerFactory.class);

            CriteriaBuilder builder = entityManagerFactory.getCriteriaBuilder();

            CriteriaQuery<Manufacturer> query = builder.createQuery(Manufacturer.class);
            Root<Manufacturer> root = query.from(Manufacturer.class);

            List<Predicate> predicates = new ArrayList<>();

            if (filter.getManufacturer() != null) {
                predicates.add(builder.like(root.get("manufacturer"), "%" + filter.getManufacturer() + "%"));
            }

            if (predicates.size() != 0)
                query.select(root).where(predicates.toArray(new Predicate[0]));

            return session.getEntityManagerFactory().createEntityManager().createQuery(query).getResultList();

        }
    }



}