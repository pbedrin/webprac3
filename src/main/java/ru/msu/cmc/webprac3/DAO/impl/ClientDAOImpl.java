package ru.msu.cmc.webprac3.DAO.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webprac3.DAO.ClientDAO;
import ru.msu.cmc.webprac3.models.Client;
import ru.msu.cmc.webprac3.models.Car;
import ru.msu.cmc.webprac3.models.Order;

import jakarta.persistence.*;
import jakarta.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;


@Repository
public class ClientDAOImpl extends CommonDAOImpl<Client, Long> implements ClientDAO {

    public ClientDAOImpl(){
        super(Client.class);
    }

    @Override
    public Client getClientByOrderId(Long orderId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Order> query = session.createQuery(
                            "FROM Order WHERE id = :orderId",
                            Order.class)
                    .setParameter("orderId", orderId);
            if (query.getResultList().size() == 0) {
                return null;
            }
            return query.uniqueResult().getClient();
        }
    }

    @Override
    public List<Client> getAllClientsByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            Query<Client> query = session.createQuery("FROM Client WHERE name " +
                    "LIKE :name", Client.class).setParameter("name", "%" + name + "%");
            return query.getResultList();
        }
    }

    //    @Override
    //    public List<Client> getAllClientsByOrderCar(Car car) {
    //        try (Session session = sessionFactory.openSession()) {
    //            Query<Client> query = session.createQuery("FROM Client WHERE name " +
    //                    "LIKE :name", Client.class).setParameter("name", "%" + name + "%");
    //            return query.getResultList();
    //        }
    //    }
    @Override
    public List<Client> getAllClientsByOrderCar(Car car) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT DISTINCT c FROM Client c " +
                    "JOIN Order o ON c.id = o.client.id " +
                    "WHERE o.car.id = :carId";
            Query<Client> query = session.createQuery(hql, Client.class)
                    .setParameter("carId", car.getId());
            return query.getResultList();
        }
    }


    @Override
    public List<Client> getByFilter(Filter filter) {
        try (Session session = sessionFactory.openSession()) {
            EntityManagerFactory entityManagerFactory = sessionFactory.unwrap(EntityManagerFactory.class);

            CriteriaBuilder builder = entityManagerFactory.getCriteriaBuilder();

            CriteriaQuery<Client> query = builder.createQuery(Client.class);
            Root<Client> root = query.from(Client.class);

            List<Predicate> predicates = new ArrayList<>();


            if (filter.getStatus() != null) {
                Join<Client, Order> orders = root.join("orders");
                Expression<String> statusAsString = orders.get("status").as(String.class);
                predicates.add(builder.equal(statusAsString, filter.getStatus().toString()));
            }

            if (filter.getStartDate() != null && filter.getEndDate() != null) {
                Join<Client, Order> orders = root.join("orders");
                predicates.add(builder.between(orders.get("date_time"), filter.getStartDate(), filter.getEndDate()));
            } else if (filter.getStartDate() != null) {
                Join<Client, Order> orders = root.join("orders");
                predicates.add(builder.greaterThanOrEqualTo(orders.get("date_time"), filter.getStartDate()));
            } else if (filter.getEndDate() != null) {
                Join<Client, Order> orders = root.join("orders");
                predicates.add(builder.lessThanOrEqualTo(orders.get("date_time"), filter.getEndDate()));
            }

            if (filter.getNeedTest() != null) {
                Join<Client, Order> orders = root.join("orders");
                predicates.add(builder.equal(orders.get("need_test"), filter.getNeedTest()));
            }

            if (filter.getTested() != null) {
                Join<Client, Order> orders = root.join("orders");
                predicates.add(builder.equal(orders.get("tested"), filter.getTested()));
            }

            if (filter.getName() != null) {
                predicates.add(builder.like(root.get("name"), "%" + filter.getName() + "%"));
            }

            if (predicates.size() != 0)
                query.select(root).where(predicates.toArray(new Predicate[0]));

            return session.getEntityManagerFactory().createEntityManager().createQuery(query).getResultList();

        }
    }

}