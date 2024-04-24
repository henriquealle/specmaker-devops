package br.com.specmaker.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "especificacao")
public class Especificacao {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "url", nullable = false)
    private String urlArquivo;

    @ManyToOne
    @JoinColumn(name = "id_projeto")
    private Projeto projeto;

}
