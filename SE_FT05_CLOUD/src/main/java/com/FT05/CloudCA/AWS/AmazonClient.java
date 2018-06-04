package com.FT05.CloudCA.AWS;

import com.FT05.CloudCA.Entity.User;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Collections;
import java.util.Date;


@Service
public class AmazonClient {

    private AmazonS3 s3client;

    @Value("${amazonProperties.endpointUrl}")
    private String endpointUrl;
    @Value("${amazonProperties.bucketName}")
    private String bucketName;
    @Value("${amazonProperties.accessKey}")
    private String accessKey;
    @Value("${amazonProperties.secretKey}")
    private String secretKey;
    @Value("${amazonProperties.region}")
    private String region;
    @Value("${amazonProperties.userBucketName}")
    private String userBucket;
    @Value("${amazonProperties.elasticDomian}")
    private String host;

    private String index = "users";
    private String type = "user";



    /*private String host = "search-user-search-wiyvojt6kxc6wrq63pzso72bwq.ap-southeast-1.es.amazonaws.com";
   */





    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        //this.s3client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
        this.s3client = new AmazonS3Client(credentials);
    }


    public String uploadFile(MultipartFile multipartFile) {

        String fileUrl = "";
        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
            uploadFileTos3bucket(fileName, file);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileUrl;
    }


    public String deleteFileFromS3Bucket(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        s3client.deleteObject(new DeleteObjectRequest(bucketName + "/", fileName));
        return "Successfully deleted";
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }



   public void elasticAdd(User user) throws IOException {

       String id = user.getId().toString();


       User userSearch = new User();
       userSearch.setFirstname(user.getFirstname());
       userSearch.setLastname(user.getLastname());
       userSearch.setEmail(user.getEmail());
       userSearch.setImage(user.getImage());
       userSearch.setId(user.getId());

       userSearch.setHighSchool(user.getHighSchool());
       userSearch.setUniversity(user.getUniversity());
       userSearch.setCurrentCity(user.getCurrentCity());
       userSearch.setCountry(user.getCountry());

       ObjectMapper objectMapper = new ObjectMapper();
       String json = objectMapper.writeValueAsString(userSearch);

       RestClient client = RestClient.builder(new HttpHost(host, 443, "https")).build();

       HttpEntity entity = new NStringEntity(json, ContentType.APPLICATION_JSON);

       Response response = client.performRequest("POST", "/" + index + "/" + type + "/" + id,
               Collections.<String, String>emptyMap(), entity);


   }



    public void elasticUpdate(User user) throws IOException {

        String id = user.getId().toString();

        User userSearch = new User();
        userSearch.setFirstname(user.getFirstname());
        userSearch.setLastname(user.getLastname());
        userSearch.setEmail(user.getEmail());
        userSearch.setImage(user.getImage());
        userSearch.setId(user.getId());


        userSearch.setHighSchool(user.getHighSchool());
        userSearch.setUniversity(user.getUniversity());
        userSearch.setCurrentCity(user.getCurrentCity());
        userSearch.setCountry(user.getCountry());


        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(userSearch);

        RestClient client = RestClient.builder(new HttpHost(host, 443, "https")).build();

        HttpEntity entity = new NStringEntity(json, ContentType.APPLICATION_JSON);

        Response response = client.performRequest("PUT", "/" + index + "/" + type + "/" + id,
                Collections.<String, String>emptyMap(), entity);


    }

}