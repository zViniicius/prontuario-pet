package com.vinicius.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "animal")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Animal {
    public enum Species {
        CACHORRO,
        GATO,
        PASSARO,
        PEIXE,
        REPTIL,
        ROEDOR,
        OUTRO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Species species;

    private String breed;

    private String color;

    private String birthDate;

    @ManyToOne
    @JsonIgnore
    private Tutor tutor;

}
