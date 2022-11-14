package br.com.specmaker.entity;


import br.com.specmaker.enuns.WorkItemType;
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
        this.atribuidoPara = dados.fields().atribuidoPara();
        this.status = dados.fields().status();
        this.abertoPor = dados.fields().abertoPor();
        this.dataCriacao = dados.fields().createdDate();

        if (dados.fields().workItemType().equals( WorkItemType.BUG.getWorkItemType() ) ){
            this.detalhes = dados.fields().reproSteps();
        } else {
            this.detalhes = dados.fields().description();
        }
    }


}
