package com.example.BatchSystem;

import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class model {
    public List<UserData> userModel() throws FileNotFoundException {
        List<UserData>list= (List<UserData>) new CsvToBeanBuilder(new FileReader("user_info.csv"));
        return list;
    }
}
