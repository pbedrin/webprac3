package ru.msu.cmc.webprac3.models;

import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.array.ListArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.*;

import org.hibernate.annotations.Type;

import java.util.List;

@Entity
@Table(name = "cars")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Car implements CommonEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "car_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "model_id", nullable = false)
    @NonNull
    private Model model_id;

    @Column(name = "vin", unique = true, length = 17)
    private String vin;

    @Column(name = "year")
    private Short year;

    @Column(name = "price")
    private Long price;

    @Type(ListArrayType.class)
    @Column(
            name = "devices",
            columnDefinition = "text[]"
    )
    private List<String> devices;

    @Type(JsonBinaryType.class)
    @Column(name = "consumers_attrs", columnDefinition = "jsonb")
    private JsonNode consumersAttrs;

    @Type(JsonBinaryType.class)
    @Column(name = "tech_attrs", columnDefinition = "jsonb")
    private JsonNode techAttrs;

    @Type(JsonBinaryType.class)
    @Column(name = "history_attrs", columnDefinition = "jsonb")
    private JsonNode historyAttrs;

    @Column(name = "availability")
    private Boolean availability = true;


}