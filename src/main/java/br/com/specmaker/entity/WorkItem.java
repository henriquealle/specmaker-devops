package br.com.specmaker.entity;


import lombok.*;

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



}
