# TechnicalAssessment

1. Set up the MySQL Database in docker using the command "docker compose up"
2. Connect to the DB using the Database Manager (e.g. DBeaver) of your choice, using the following information:

Port: 1433, Microsoft MySQL DB, Username: sa Password: MyStrongPassword123!

3. Run the application using the command "mvn spring-boot:run"
4. Using the API Client of your choice (e.g. Postman, ping the endpoint using "localhost:8080/Processing/ImportFile", with the following information in the request body as-is:

   18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1 \n
   3ce2d17b-e66a-4c1e-bca3-40eb1c9222c7|2X2D24|Mike Smith|Likes Grape|Drives an SUV|35.0|95.5 \n
   1afb6f5d-a7c2-4311-a92d-974f3180ff5e|3X3D35|Jenny Walters|Likes Avocados|Rides A Scooter|8.5|15.3

5. Once the output/response is observed in the endpoint client, the content and a record of the transaction with be observed in the DB, in the tables "Content" and "Interaction", this should be observable in your database manager