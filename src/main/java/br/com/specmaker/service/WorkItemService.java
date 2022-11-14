package br.com.specmaker.service;

import br.com.specmaker.azuredevops.AzureDevopsRestWorkItemClient;
import br.com.specmaker.entity.WorkItem;
import br.com.specmaker.record.QueryWorkItemRecord;
import br.com.specmaker.record.WorkItemRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkItemService {

    @Autowired
    private AzureDevopsRestWorkItemClient azureDevopsRestWorkItemClient;
    public List<WorkItem> listByQueryID(String queryID){
        final List<WorkItem> workItems = new ArrayList<>(0);
        QueryWorkItemRecord queryWorkItemRecord = azureDevopsRestWorkItemClient
                .listWorkItemByQueryId(queryID);

        if (queryWorkItemRecord != null && queryWorkItemRecord.workItems() != null) {
            queryWorkItemRecord.workItems().forEach(wit -> {
                workItems.add( getWorkItemById( wit.id() ) );
            });
        }

        return workItems;
    }

    public WorkItem getWorkItemById(Long id){
        WorkItemRecord workItemRecord = azureDevopsRestWorkItemClient.getWorkItemById(id);
        return new WorkItem( workItemRecord );
    }
}
