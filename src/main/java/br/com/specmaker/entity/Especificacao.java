package br.com.specmaker.entity;


import br.com.specmaker.record.EspecificacaoRecord;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Especificacao {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "url", nullable = false)
    private String urlArquivo;

    @Column(name = "query_id", nullable = false)
    private String queryId;

    @ManyToOne
    @JoinColumn(name = "id_projeto")
    private Projeto projeto;

    public Especificacao(EspecificacaoRecord especificacaoRecord) {
        this.titulo = especificacaoRecord.titulo();
        this.urlArquivo = especificacaoRecord.urlArquivo();
        this.queryId = especificacaoRecord.queryId();
    }
}
