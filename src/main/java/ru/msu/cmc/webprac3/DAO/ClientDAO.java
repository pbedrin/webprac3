package ru.msu.cmc.webprac3.DAO;

import ru.msu.cmc.webprac3.models.Client;
import ru.msu.cmc.webprac3.models.Order;

import java.util.List;
import java.sql.Timestamp;

import lombok.Builder;
import lombok.Getter;

public interface ClientDAO extends CommonDAO<Client, Long> {

    List<Client> getAllClientsByName(String name);
    Client getClientByOrderId(Long order_id);
    //List<Client> getAllClientsByOrderDateRange(Timestamp border1, Timestamp border2);
    List<Client> getAllClientsByOrderCar(Long car_id);
    List<Client> getByFilter(ClientDAO.Filter filter);
    @Builder
    @Getter
    public class Filter {
        private Order.Status status;
        private Timestamp startDate;
        private Timestamp endDate;
        private Boolean needTest;
        private Boolean tested;
    }
}
