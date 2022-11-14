package br.com.specmaker.entity;


import br.com.specmaker.record.WorkItemRecord;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class WorkItem {

    private Long id;
    private String titulo;
    private String detalhes;
    private String tipoWorkItem;
    private String status;
    private String atribuidoPara;
    private String abertoPor;
    private String url;

    private LocalDateTime dataCriacao;

    public WorkItem (WorkItemRecord dados){
        this.id = dados.id();
        this.url = dados.url();
        this.titulo = dados.fields().titulo();
        this.tipoWorkItem = dados.fields().workItemType();
        this.detalhes = dados.fields().descricao();
        this.atribuidoPara = dados.fields().atribuidoPara();
        this.status = dados.fields().status();
        this.abertoPor = dados.fields().abertoPor();
        this.dataCriacao = dados.fields().createdDate();
    }

}
