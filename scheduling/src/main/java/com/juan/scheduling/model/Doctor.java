package com.juan.scheduling.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class Doctor {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "O nome não pode ser vazio.")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "A especialidade não pode ser vazia.")
    private String specialty;

    @Email(message = "Formato de email inválido")
    @NotBlank(message = "O email não pode ser vazio.")
    @Column(nullable = false, unique = true)
    private String email;
}
