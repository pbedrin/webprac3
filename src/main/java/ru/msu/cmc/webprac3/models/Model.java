package ru.msu.cmc.webprac3.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "models")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Model implements CommonEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "model_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "manufacturer_id", referencedColumnName = "id", nullable = false)
    @NonNull
    private Manufacturer manufacturer_id;

    @Column(nullable = false, unique = true, name = "model")
    @NonNull
    private String model;

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