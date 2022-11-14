package br.com.specmaker.record;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record WorkItemFields(
        @JsonProperty("System.Title")
        String titulo,
        @JsonProperty("System.WorkItemType")
        String workItemType,
        @JsonProperty("Microsoft.VSTS.TCM.ReproSteps")
        String descricao,

        @JsonProperty("System.CreatedBy")
        String abertoPor,

        @JsonProperty("System.AssignedTo")
        String atribuidoPara,

        @JsonProperty("System.State")
        String status,

        @JsonProperty("System.CreatedDate")
        LocalDateTime createdDate

) {
}
