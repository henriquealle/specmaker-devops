package br.com.specmaker.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class AmazonS3Service {

    @Autowired
    private AmazonS3 amazonS3;

    @Value("${aws.s3.bucketName}")
    private String bucketName;


    public String uploadRepositorioAndGetUrl(File file){
        uploadFile(file);
        return amazonS3.getUrl(bucketName, file.getName()).toString();
    }

    public void uploadFile(File file) {
        String fileName = file.getName();
        amazonS3.putObject( new PutObjectRequest( bucketName, fileName, file)
                .withCannedAcl( CannedAccessControlList.PublicRead ) );
    }

    public List<String> listFiles(){
        List<String> nomeArquivos = new ArrayList<String>(0);
        ListObjectsV2Request request = new ListObjectsV2Request().withBucketName(bucketName);
        ListObjectsV2Result result = null;

        do {
            result = amazonS3.listObjectsV2(request);

            List<S3ObjectSummary> objects = result.getObjectSummaries();
            objects.forEach( files -> nomeArquivos.add( files.getKey() ) );
            request.setContinuationToken(result.getNextContinuationToken());
        } while (result.isTruncated());

        return nomeArquivos;
    }

}
