package com.vinicius.backend.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tutor")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tutor {

    public enum IdentificationType {
        CPF,
        RG,
        CNH,
        PASSAPORTE,
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String idNumber;

    @Column(nullable = false)
    private IdentificationType idType;

    private String phone;

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "tutor")
    private List<Animal> animals;

}
