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

    /*public void downloadFile(User user) {

        try {
            //Utility.jsonfile(user);
            System.out.println("Downloading an object");
            S3Object s3object = s3client.getObject(new GetObjectRequest(bucketName, ""));
            System.out.println("Content-Type: "  + s3object.getObjectMetadata().getContentType());

            Utility.displayText(s3object.getObjectContent());
            //logger.info("===================== Import File - Done! =====================");

        } catch (AmazonServiceException ase) {
            *//*logger.info("Caught an AmazonServiceException from GET requests, rejected reasons:");
            logger.info("Error Message:    " + ase.getMessage());
            logger.info("HTTP Status Code: " + ase.getStatusCode());
            logger.info("AWS Error Code:   " + ase.getErrorCode());
            logger.info("Error Type:       " + ase.getErrorType());
            logger.info("Request ID:       " + ase.getRequestId());*//*
            System.out.println("error");
        } catch (AmazonClientException ace) {
           *//* logger.info("Caught an AmazonClientException: ");
            System.out.println("error");
            logger.info("Error Message: " + ace.getMessage());*//*
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/


   /* public void userFile(User user) {

        try {

            JSONObject obj = new JSONObject();
            obj.put("email", user.getEmail());
            obj.put("name",user.getFirstname()+" "+user.getLastname());
            File tempFile = File.createTempFile("newFile", ".JSON");
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            writer.write(obj.toString());
            writer.close();
            uploadFiles(tempFile, user);

        }
        catch(AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            System.out.println("JSON ERROR");
            e.printStackTrace();
        }
        catch(SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            System.out.println("JSON ERROR");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    public String uploadFiles(File file, User user) {

        String fileUrl = "";
        try {
            String fileName = user.getEmail();
            fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
            uploadFileTos3bucket(fileName, file);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileUrl;
    }*/


   public void elasticAdd(User user) throws IOException {

       String host = "search-usersearch-2ul6qtvv46zwdqc4o44jtx4e4m.ap-southeast-1.es.amazonaws.com"; // For example, my-test-domain.us-east-1.es.amazonaws.com
       String index = "users";
       String type = "user";
       String id = user.getId().toString();

       /*String json = "{" + "\"name\":," + "\"director\":\"alter test\"," + "\"year\":\"2010\""
               + "}";*/

       User userSearch = new User();
       userSearch.setFirstname(user.getFirstname());
       userSearch.setLastname(user.getLastname());
       userSearch.setEmail(user.getEmail());
       userSearch.setImage(user.getImage());

       ObjectMapper objectMapper = new ObjectMapper();
       String json = objectMapper.writeValueAsString(userSearch);

       System.out.println("userSearch " +json);

       RestClient client = RestClient.builder(new HttpHost(host, 443, "https")).build();

       HttpEntity entity = new NStringEntity(json, ContentType.APPLICATION_JSON);

       Response response = client.performRequest("POST", "/" + index + "/" + type + "/" + id,
               Collections.<String, String>emptyMap(), entity);

       System.out.println(response.toString());


   }

}