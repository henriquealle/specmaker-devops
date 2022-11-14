package br.com.specmaker.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Repositorio {

    private Long id;
    private String nome;

}
