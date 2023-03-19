package ru.msu.cmc.webprac3.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "clients")
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Client implements CommonEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "client_id")
    private Long id;

    @Column(nullable = false, name = "name")
    @NonNull
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone", length = 11)
    private String phone;

    @Column(name = "email")
    private String email;

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