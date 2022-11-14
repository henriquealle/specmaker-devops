package br.com.specmaker.entity;


import lombok.*;

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

}
