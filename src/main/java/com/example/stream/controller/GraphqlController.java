package com.example.stream.controller;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

@DgsComponent
@Slf4j
public class GraphqlController {


    List<Staff> staffs = new ArrayList<>();
    List<Address> address = new ArrayList<>();
    Random random = new Random(100);
    {
        for (int i = 0; i < 10; i++) {
            address.add(new Address(String.valueOf(i),  "distric"+i, random.nextInt()));
        }
        for (int i = 0; i < 1_000; i++) {
            staffs.add( new Staff("name"+i, i, "country"+i, random.nextDouble(), Optional.ofNullable(address.get(i % 10))));
        }
        log.info("check address at construct {}", address);
    }
    @DgsQuery
    public List<Staff> staffs(){
        return staffs;
    }
    @DgsQuery
    public List<Address> address(){
        log.info("check the address {}", address);
        return address;
    }

//    @DgsData(parentType = "staff", field = "address")
//    public Address getAddress(@InputArgument String staffId){
//        return dao.getAddress(staffId);
//    }
//    @DgsData(parentType = "staff", field = "address")
//    public CompletableFuture<Address> getAddressLazy(@InputArgument String staffId){
//        //dao.getAddress(staffId)
//        return new CompletableFuture<>(dao.getAddress(staffId));
//    }

    //TODO: pre loading, data loader
    @DgsQuery
    public Staff staff(@InputArgument String name){

        return staffs.stream().filter(s -> name.equals(s.name())).findFirst().orElse(null);
    }
}
