package ru.msu.cmc.webprac3.models;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Order implements CommonEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car_id;

    @Column(name = "date_time")
    private Timestamp date_time;

    @Column(name = "need_test")
    private Boolean need_test = false;

    @Column(name = "tested")
    private Boolean tested = false;

    public enum Status {
        В_РАБОТЕ, ОТМЕНЁН, ОЖИДАНИЕ_ОПЛАТЫ, ОПЛАЧЕН, ЗАВЕРШЁН, НА_ТЕСТ_ДРАЙВЕ
    }
    @Column(name = "status")
    private Status status = Status.В_РАБОТЕ;

}