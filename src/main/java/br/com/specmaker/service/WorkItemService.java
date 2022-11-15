package br.com.specmaker.service;

import br.com.specmaker.azuredevops.AzureDevopsRestWorkItemClient;
import br.com.specmaker.entity.WorkItem;
import br.com.specmaker.entity.WorkItemImage;
import br.com.specmaker.record.QueryWorkItemRecord;
import br.com.specmaker.record.WorkItemRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
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
        BufferedImage bff = ImageIO.read( new ByteArrayInputStream(imagem) );

        Dimension originalDimension = new Dimension(bff.getWidth(), bff.getHeight());
        Dimension boundary = new Dimension(450, 300);
        Dimension newDimension = getScaledDimension(originalDimension, boundary);

        WorkItemImage workItemImage = new WorkItemImage();
        workItemImage.setImgFile(imagem);
        workItemImage.setWidth( (int) newDimension.getWidth() );
        workItemImage.setHeight((int) newDimension.getHeight() );
        return workItemImage;
    }

    private Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {

        int original_width = imgSize.width;
        int original_height = imgSize.height;
        int bound_width = boundary.width;
        int bound_height = boundary.height;
        int new_width = original_width;
        int new_height = original_height;

        // first check if we need to scale width
        if (original_width > bound_width) {
            //scale width to fit
            new_width = bound_width;
            //scale height to maintain aspect ratio
            new_height = (new_width * original_height) / original_width;
        }

        // then check if we need to scale even with the new height
        if (new_height > bound_height) {
            //scale height to fit instead
            new_height = bound_height;
            //scale width to maintain aspect ratio
            new_width = (new_height * original_width) / original_height;
        }

        return new Dimension(new_width, new_height);
    }
}
