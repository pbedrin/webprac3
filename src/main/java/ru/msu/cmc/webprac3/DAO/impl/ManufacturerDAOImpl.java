package ru.msu.cmc.webprac3.DAO.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.webprac3.DAO.ManufacturerDAO;
import ru.msu.cmc.webprac3.models.Manufacturer;

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



}