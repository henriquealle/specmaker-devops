package br.com.specmaker.enuns;

import br.com.specmaker.service.WorkItemService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public enum WorkItemType {

    BUG("Bug"),
    PBI("Product Backlog Item"),
    FEATURE("Feature"),
    EPIC("Epic");

    private String workItemType;

}
