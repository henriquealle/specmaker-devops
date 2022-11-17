package br.com.specmaker.entity;


import br.com.specmaker.apiclient.records.QueryRecord;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Query {

    private String id;
    private String nome;
    private String path;
    private boolean isFolder;
    private boolean hasChildren;

    private List<Query> children;

    private List<WorkItem> workItems;

    public Query(QueryRecord dados){
        this.id = dados.id();
        this.nome = dados.name();
        this.path = dados.path();
        this.hasChildren = dados.hasChildren();
        this.isFolder = dados.isFolder();

        if( dados.children() != null ){
            this.children = new ArrayList<Query>();

            dados.children().forEach( (child) -> {
                children.add( new Query(child) );
            });
        }

    }

}
