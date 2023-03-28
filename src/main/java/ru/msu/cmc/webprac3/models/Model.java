package ru.msu.cmc.webprac3.models;

import lombok.*;

import jakarta.persistence.*;

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
    @JoinColumn(name = "manufacturer_id", nullable = false)
    private Manufacturer manufacturer_id;

    @Column(nullable = false, unique = true, name = "model")
    @NonNull
    private String model;

}