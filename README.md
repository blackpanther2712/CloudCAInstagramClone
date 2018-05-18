# CloudCAInstagramClone

### A Basic Instagram Clone Utilizing Various Cloud Services Namely

1. AWS Cognito (Authentication)
2. AWS BeanStalk (Deployment)
3. AWS S3 (Object Storage)
4. AWS RDS (Database)
5. AWS Rekognition (Image Recognition)
6. Spring Boot (Backend)


### App Flow

1. Login Using Cognito
2. Get Token From Cognito (User ID)
   I.  If Present, Login
   II. Else, Make a new entry in the table
3. Enter main page & display the following 
   I. All images of users followed by current user
