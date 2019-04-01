package com.wentong.mytest;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Employee {
    private String firstName;
    private String lastName;
    private ArrayList<Office> offices = new ArrayList<>();

    public void addOffices(Office office) {
        offices.add(office);
    }
}
