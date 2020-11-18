package com.example.BatchSystem;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;

import com.opencsv.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import java.lang.annotation.Annotation;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserData {
    @CsvBindByName(column = "Id", required = true)
    private String user_Id;
    @CsvBindByName(column = "Name", required = true)
    private String name;
    @CsvBindByName(column = "Age", required = true)
    private int age;

}
