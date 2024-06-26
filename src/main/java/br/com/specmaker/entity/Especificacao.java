package br.com.specmaker.entity;


import br.com.specmaker.record.EspecificacaoRecord;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

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
    private Long id;

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
        this.id = especificacaoRecord.id();
        this.titulo = especificacaoRecord.titulo();
        this.urlArquivo = especificacaoRecord.urlArquivo();
        this.queryId = especificacaoRecord.queryId();
    }

    public void setTitulo(String titulo){
        if( titulo != null && !titulo.isEmpty()){
            UUID uuid = UUID.randomUUID();
            this.titulo = titulo.concat( uuid.toString() ).concat(".docx");
        }
    }

    @Override
    public String toString() {
        return "Especificacao{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", urlArquivo='" + urlArquivo + '\'' +
                ", queryId='" + queryId + '\'' +
                '}';
    }
}
