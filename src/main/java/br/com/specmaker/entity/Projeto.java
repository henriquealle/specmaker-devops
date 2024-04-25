package br.com.specmaker.entity;


import br.com.specmaker.record.CadastroProjetoRecord;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "projeto")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Projeto {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "produto")
    private String produto;

    @Column(name = "cliente")
    private String cliente;

    @Column(name = "nome_projeto")
    private String nomeProjeto;

    @Column(name = "responsavel")
    private String responsavel;

    @Column(name = "data_inicio")
    @Temporal(TemporalType.DATE)
    private LocalDate dataInicioProjeto;

    @Column(name = "ts_criacao")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dtCriacao;

    @Column(name = "ts_atualizacao")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dtAtualizacao;

    @OneToMany(mappedBy = "projeto", cascade = CascadeType.ALL)
    private List<Especificacao> especificacoes;


    public Projeto(CadastroProjetoRecord cadastroProjeto) {
        this.cliente = cadastroProjeto.cliente();
        this.produto = cadastroProjeto.produto();
        this.nomeProjeto = cadastroProjeto.nomeProjeto();
        this.responsavel = cadastroProjeto.responsavel();
        this.dataInicioProjeto = cadastroProjeto.dataInicioProjeto();
        this.dtAtualizacao = LocalDateTime.now();
        this.dtCriacao = LocalDateTime.now();
        this.especificacoes = cadastroProjeto.especificacoes().stream().map(Especificacao::new).toList();
        this.especificacoes.forEach(e -> e.setProjeto(this));
    }

}
