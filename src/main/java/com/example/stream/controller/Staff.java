package com.example.stream.controller;

import java.util.Optional;

record Staff(String name, int age, String country, double salary, Optional<Address> address) {//one record is 70B

}
