package ru.msu.cmc.webprac3.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "manufacturers")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Manufacturer implements CommonEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "manufacturer_id")
    private Long id;

    @Column(nullable = false, unique = true, name = "manufacturer")
    @NonNull
    private String manufacturer;

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