package ru.msu.cmc.webprac3.DAO.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webprac3.DAO.OrderDAO;
import ru.msu.cmc.webprac3.models.Client;
import ru.msu.cmc.webprac3.models.Car;
import ru.msu.cmc.webprac3.models.Order;

import jakarta.persistence.*;
import jakarta.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderDAOImpl extends CommonDAOImpl<Order, Long> implements OrderDAO {

    public OrderDAOImpl(){
        super(Order.class);
    }

    @Override
    public List<Order> getAllOrdersByClient(Client client) {
        try (Session session = sessionFactory.openSession()) {
            Query<Order> query = session.createQuery(
                            "FROM Order WHERE client = :client",
                            Order.class)
                    .setParameter("client", client);
            return query.getResultList();
        }
    }

    @Override
    public List<Order> getByFilter(Filter filter) {
        try (Session session = sessionFactory.openSession()) {
            EntityManagerFactory entityManagerFactory = sessionFactory.unwrap(EntityManagerFactory.class);

            CriteriaBuilder builder = entityManagerFactory.getCriteriaBuilder();

            CriteriaQuery<Order> query = builder.createQuery(Order.class);
            Root<Order> root = query.from(Order.class);

            List<Predicate> predicates = new ArrayList<>();

            if (filter.getStartDate() != null && filter.getEndDate() != null) {
                predicates.add(builder.between(root.get("date_time"), filter.getStartDate(), filter.getEndDate()));
            } else if (filter.getStartDate() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("date_time"), filter.getStartDate()));
            } else if (filter.getEndDate() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("date_time"), filter.getEndDate()));
            }

            if (filter.getId() != null) {
                //System.out.println(root.get("id").toString());
                //System.out.println(filter.getId());
                predicates.add(builder.equal(root.get("id"), filter.getId()));
            }

            if (filter.getClientId() != null) {
                Join<Order, Client> clients = root.join("client");
                predicates.add(builder.equal(clients.get("id"), filter.getClientId()));
            }

            if (filter.getCarId() != null) {
                Join<Order, Car> cars = root.join("car");
                predicates.add(builder.equal(cars.get("id"), filter.getCarId()));
            }

            if (filter.getTested() != null) {
                predicates.add(builder.equal(root.get("tested"), filter.getTested()));
            }

            if (filter.getNeedTest() != null) {
                predicates.add(builder.equal(root.get("need_test"), filter.getNeedTest()));
            }

            if (filter.getStatus() != null) {
                predicates.add(builder.equal(root.get("status").as(String.class), filter.getStatus().toString()));
            }

            if (predicates.size() != 0)
                query.select(root).where(predicates.toArray(new Predicate[0]));

            return session.getEntityManagerFactory().createEntityManager().createQuery(query).getResultList();
        }
    }

}