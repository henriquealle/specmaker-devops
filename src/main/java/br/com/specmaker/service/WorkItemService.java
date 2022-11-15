package br.com.specmaker.service;

import br.com.specmaker.azuredevops.AzureDevopsRestWorkItemClient;
import br.com.specmaker.entity.WorkItem;
import br.com.specmaker.entity.WorkItemImage;
import br.com.specmaker.record.QueryWorkItemRecord;
import br.com.specmaker.record.WorkItemRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Service
public class WorkItemService {

    @Autowired
    private AzureDevopsRestWorkItemClient azureDevopsRestWorkItemClient;
    public List<WorkItem> listWorkItemByQueryID(String queryID){
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
        WorkItem workItem = new WorkItem( workItemRecord );
        final List<WorkItemImage> imagens = new ArrayList<>(0);

        if( !Objects.isNull( workItem.getAttachmentsUrls() ) && !workItem.getAttachmentsUrls().isEmpty() ){
            workItem.getAttachmentsUrls().forEach(attachUrl ->{
                try {
                    imagens.add( getWorkItemImageBy(attachUrl) );
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        workItem.setDescriptionImagens(imagens);

        return workItem;
    }

    public WorkItemImage getWorkItemImageBy(String imageUrl)
            throws IOException, ExecutionException, InterruptedException {

        byte[] imagem = azureDevopsRestWorkItemClient.getImageByUrl(imageUrl);
        //ImageInputStream iis = ImageIO.createImageInputStream(new ByteArrayInputStream(imagem));

        WorkItemImage workItemImage = new WorkItemImage();
        workItemImage.setImgFile(imagem);
        workItemImage.setWidth(400);
        workItemImage.setHeight(250);
        workItemImage.setFileName("");
        return workItemImage;
    }
}
