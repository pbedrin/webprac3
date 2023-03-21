package ru.msu.cmc.webprac3.DAO;

import lombok.Builder;
import lombok.Getter;
import ru.msu.cmc.webprac3.models.Order;
import ru.msu.cmc.webprac3.models.Client;

import java.sql.Timestamp;
import java.util.List;

public interface OrderDAO extends CommonDAO<Order, Long> {

    List<Order> getAllOrdersByClient(Client client);
    //List<Order> getAllOrdersByDateRange(Timestamp border1, Timestamp border2);
    List<Order> getByFilter(Filter filter);

    @Builder
    @Getter
    class Filter {
        private Long id;
        private Long client_id;
        private Long car_id;
        private Timestamp date_time;
        private Boolean need_test;
        private Boolean tested;
        private Order.Status status;
    }

    static Filter.FilterBuilder getFilterBuilder() {
        return Filter.builder();
    }
}
