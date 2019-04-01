package com.wentong.mytest;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Office {
    private String name;
    private ArrayList<Address> address = new ArrayList<>();

    public void addAddress(Address addr) {
        address.add(addr);
    }
}
