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

}