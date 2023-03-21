package ru.msu.cmc.webprac3.DAO.impl;

import com.fasterxml.jackson.databind.JsonNode;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webprac3.DAO.CarDAO;
import ru.msu.cmc.webprac3.models.Car;
import ru.msu.cmc.webprac3.models.Model;
import ru.msu.cmc.webprac3.models.Manufacturer;

import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CarDAOImpl extends CommonDAOImpl<Car, Long> implements CarDAO {

    public CarDAOImpl(){
        super(Car.class);
    }

    @Override
    public Car getCarByVin(String vin) {
        try (Session session = sessionFactory.openSession()) {
            Query<Car> query = session.createQuery("FROM Car WHERE vin = :vin",
                    Car.class).setParameter("vin", vin);
            return query.uniqueResult();
        }
    }

    @Override
    public List<Car> getAllCarsByModel(String model) {
        try (Session session = sessionFactory.openSession()) {
            Query<Car> query = session.createQuery("FROM Car WHERE model_id.model " +
                    "LIKE :model", Car.class).setParameter("model", "%" + model + "%");
            return query.getResultList();
        }
    }

    @Override
    public List<Car> getAllCarsByManufacturer(String manufacturer) {
        try (Session session = sessionFactory.openSession()) {
            Query<Car> query = session.createQuery("FROM Car WHERE model_id.manufacturer_id.manufacturer LIKE :manufacturer", Car.class)
                    .setParameter("manufacturer", "%" + manufacturer + "%");
            return query.getResultList();
        }
    }

    public void addAttributeToCar(JsonNode carAttributes, String key, String value, Long carId) {
        try (Session session = sessionFactory.openSession()) {
            Car car = session.get(Car.class, carId);
            if (car != null) {
                ((ObjectNode) carAttributes).put(key, value);
                car.setConsumersAttrs(carAttributes);
                session.beginTransaction();
                session.update(car);
                session.getTransaction().commit();
            } else {
                throw new IllegalArgumentException("Car with id " + carId + " not found.");
            }
        }
    }

    @Override
    public List<Car> getByFilter(Filter filter) {
        try (Session session = sessionFactory.openSession()) {
            EntityManagerFactory entityManagerFactory = sessionFactory.unwrap(EntityManagerFactory.class);

            CriteriaBuilder builder = entityManagerFactory.getCriteriaBuilder();

            CriteriaQuery<Car> query = builder.createQuery(Car.class);
            Root<Car> root = query.from(Car.class);

            List<Predicate> predicates = new ArrayList<>();

            if (filter.getModel() != null && !filter.getModel().isEmpty()) {
                Join<Car, Model> modelJoin = root.join("model_id");
                predicates.add(builder.equal(modelJoin.get("model"), filter.getModel()));
            }

            if (filter.getManufacturer() != null && !filter.getManufacturer().isEmpty()) {
                Join<Car, Model> modelJoin = root.join("model_id");
                Join<Model, Manufacturer> manufacturerJoin = modelJoin.join("manufacturer_id");
                predicates.add(builder.equal(manufacturerJoin.get("manufacturer"), filter.getManufacturer()));
            }

            if (filter.getYear() != null) {
                predicates.add(builder.equal(root.get("year"), filter.getYear()));
            }

            if (filter.getPriceStart() != null && filter.getPriceEnd() != null) {
                predicates.add(builder.between(root.get("price"), filter.getPriceStart(), filter.getPriceEnd()));
            } else if (filter.getPriceStart() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("price"), filter.getPriceStart()));
            } else if (filter.getPriceEnd() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("price"), filter.getPriceEnd()));
            }

            if (filter.getAvailability() != null) {
                predicates.add(builder.equal(root.get("availability"), filter.getAvailability()));
            }

            if (predicates.size() != 0)
                query.select(root).where(predicates.toArray(new Predicate[0]));

            return session.getEntityManagerFactory().createEntityManager().createQuery(query).getResultList();
        }
    }

}