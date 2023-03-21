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

}