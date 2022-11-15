package br.com.specmaker.controller;

import br.com.specmaker.entity.WorkItem;
import br.com.specmaker.service.WorkItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workitem")
public class WorkItemController {

    @Autowired
    private WorkItemService workItemService;

    @GetMapping("/{projectName}")
    public List<WorkItem> listByQueryID(
            @PathVariable(value = "projectName", required = true) String projectName,
            @RequestParam(value = "queryId", required = false) String queryId){
        return workItemService.listWorkItemByQueryID(projectName, queryId);
    }


}
