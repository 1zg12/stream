package com.example.stream.controller;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@DgsComponent
public class GraphqlController {


    List<Staff> staffs = new ArrayList<>();
    Random random = new Random(100);
    {
        for (int i = 0; i < 1_000; i++) {
            staffs.add( new Staff("name"+i, i, "country"+i, random.nextDouble()));
        }
    }
    @DgsQuery
    public List<Staff> staffs(){
        return staffs;
    }
    @DgsQuery
    public Staff staff(@InputArgument String name){

        return staffs.stream().filter(s -> name.equals(s.name())).findFirst().orElse(null);
    }
}
