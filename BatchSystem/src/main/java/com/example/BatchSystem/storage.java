package com.example.BatchSystem;

import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.FileNotFoundException;
import java.util.List;

@Configuration
public class storage {
    @Value("${spring.data.mongodb.uri}")
    private String mongohost;
    @Value("${spring.data.dbName.testDb}")
    private String mongodb;
    //UserDatastorage这部分还在看文档实现中
   public void UserDataStorage(){
       final String destinationProjectId = "MY_DESTINATION_PROJECT_ID";
       final String destinationDatasetId = "MY_DESTINATION_DATASET_ID";
       final String sourceProjectId = "MY_SOURCE_PROJECT_ID";
       final String sourceDatasetId = "MY_SOURCE_DATASET_ID";
       Map<String, Value> params = new HashMap<>();
       params.put("source_project_id", Value.newBuilder().setStringValue(sourceProjectId).build());
       params.put("source_dataset_id", Value.newBuilder().setStringValue(sourceDatasetId).build());
       TransferConfig transferConfig =
               TransferConfig.newBuilder()
                       .setDestinationDatasetId(destinationDatasetId)
                       .setDisplayName("Your Dataset Copy Name")
                       .setDataSourceId("cross_region_copy")
                       .setParams(Struct.newBuilder().putAllFields(params).build())
                       .setSchedule("every 24 hours")
                       .build();
       copyDataset(destinationProjectId, transferConfig);
   }
    public static void copyDataset(String projectId, TransferConfig transferConfig)
            throws IOException {
        try (DataTransferServiceClient dataTransferServiceClient = DataTransferServiceClient.create()) {
            ProjectName parent = ProjectName.of(projectId);
            CreateTransferConfigRequest request =
                    CreateTransferConfigRequest.newBuilder()
                            .setParent(parent.toString())
                            .setTransferConfig(transferConfig)
                            .build();
            TransferConfig config = dataTransferServiceClient.createTransferConfig(request);
            System.out.println("Copy dataset created successfully :" + config.getName());
        } catch (ApiException ex) {
            System.out.print("Copy dataset was not created." + ex.toString());
        }
    }


   public void UserDataDatabase() throws FileNotFoundException {
       MongoClient mongoClient = MongoClients.create(mongohost);
       MongoCollection<Document> user_collection = mongoClient.getDatabase("BatchSystem").getCollection("user_info");
       model models=new model();
       List<UserData>userData=models.userModel();
       for(UserData user:userData){
           Document document = new Document();
           document.put("id",user.getUser_Id());
           document.put("name",user.getName());
           document.put("age",user.getAge());
           user_collection.insertOne(document);
       }
   }
}
