package ru.msu.cmc.webprac3.models;

import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "сars")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Car implements CommonEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "car_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "model_id", referencedColumnName = "id", nullable = false)
    @NonNull
    private Model model_id;

    @Column(name = "vin", unique = true, length = 17)
    private String vin;

    @Column(name = "year")
    private Short year;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "devices", columnDefinition = "text[]")
    private String[] devices;

    @Type(type = "jsonb")
    @Column(name = "consumers_attrs", columnDefinition = "jsonb")
    private JsonNode consumersAttrs;

    @Type(type = "jsonb")
    @Column(name = "tech_attrs", columnDefinition = "jsonb")
    private JsonNode techAttrs;

    @Type(type = "jsonb")
    @Column(name = "history_attrs", columnDefinition = "jsonb")
    private JsonNode historyAttrs;

    @Column(name = "availability")
    private Boolean availability = true;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Cars other = (Cars) o;
//        return Objects.equals(model_id, other.model_id)
//                && name.equals(other.name)
//                && gender.equals(other.gender)
//                && Objects.equals(birth, other.birth)
//                && Objects.equals(death, other.death)
//                && character.equals(other.character);
//    }
}