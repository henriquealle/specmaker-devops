package br.com.specmaker.service;

import br.com.specmaker.apiclient.RestWorkItemClient;
import br.com.specmaker.apiclient.records.QueryWorkItemRecord;
import br.com.specmaker.apiclient.records.WorkItemRecord;
import br.com.specmaker.entity.WorkItem;
import br.com.specmaker.entity.WorkItemImage;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Service
public class WorkItemService {

    @Autowired
    private RestWorkItemClient apiWorkItemClient;

    public List<WorkItem> listWorkItemBy(String projectName, String queryID){
        final List<WorkItem> workItems = new ArrayList<>(0);
        QueryWorkItemRecord queryWorkItemRecord = apiWorkItemClient
                .listWorkItemByQueryId(projectName, queryID);

        if ( !Objects.isNull(queryWorkItemRecord)
                && !Objects.isNull(queryWorkItemRecord.workItems()) ) {
            queryWorkItemRecord.workItems().forEach(wit -> {
                workItems.add( getWorkItemBy( projectName, wit.id() ) );
            });
        }

        return workItems;
    }

    public WorkItem getWorkItemBy(String projectName, Long id){
        WorkItemRecord workItemRecord = apiWorkItemClient.getWorkItemById(projectName, id);
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

        WorkItemImage workItemImage = new WorkItemImage();
        byte[] imagem = null;

        if( ! UrlValidator.getInstance().isValid(imageUrl) ) {
            imageUrl = imageUrl.replace("data:image/jpeg;base64,","");
            imagem = Base64.getDecoder().decode(imageUrl);
        } else {
            imagem = apiWorkItemClient.getImageByUrl(imageUrl);
        }

        BufferedImage bff = ImageIO.read( new ByteArrayInputStream(imagem) );

        if ( !Objects.isNull(bff) ){
            Dimension originalDimension = new Dimension(bff.getWidth(), bff.getHeight());
            Dimension boundary = new Dimension(450, 300);
            Dimension newDimension = getScaledDimension(originalDimension, boundary);

            workItemImage.setImgFile(imagem);
            workItemImage.setWidth( (int) newDimension.getWidth() );
            workItemImage.setHeight((int) newDimension.getHeight() );
        }

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
