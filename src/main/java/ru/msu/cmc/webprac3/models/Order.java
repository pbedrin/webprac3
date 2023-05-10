package ru.msu.cmc.webprac3.models;

import lombok.*;

import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Order implements CommonEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "car_id")
    private Car car;

    @Column(nullable = false, name = "date_time")
    @NonNull
    private LocalDateTime date_time;

    @Column(name = "need_test")
    private Boolean need_test = false;

    @Column(name = "tested")
    private Boolean tested = false;


    public enum Status {
        IN_WORK, CANCELED, PENDING_PAYMENT, PAID, COMPLETED, ON_TEST_DRIVE
    }
    @Column(columnDefinition = "status")
    @Enumerated(EnumType.STRING)
    //@Column(columnDefinition = "genre_info")
    private Status status = Status.IN_WORK;

    public String getStatusAlias() {
        switch (status) {
            case IN_WORK:
                return "В работе";
            case CANCELED:
                return "Отменено";
            case PENDING_PAYMENT:
                return "Ожидает оплаты";
            case PAID:
                return "Оплачено";
            case COMPLETED:
                return "Завершено";
            case ON_TEST_DRIVE:
                return "На тест-драйве";
            default:
                return "";
        }
    }
}