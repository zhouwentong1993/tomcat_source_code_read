package com.wentong.mytest;

import org.apache.tomcat.util.digester.Digester;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TestDegister {

    public static void main(String[] args) throws Exception {

        File file = new File("/Users/finup123/IdeaProjects/tomcat/test/com/wentong/mytest/degesterDemo.xml");
        Digester digester = new Digester();
        digester.setValidating(false);
        digester.addObjectCreate("employee", "com.wentong.mytest.Employee");
        digester.addSetProperties("employee");

        digester.addObjectCreate("employee/office", "com.wentong.mytest.Office");
        digester.addSetProperties("employee/offices");

//        digester.addSetNext("employee/office", "addOffices", "com.wentong.mytest.Address");

        digester.addObjectCreate("employee/office/address", "com.wentong.mytest.Address");
        digester.addSetProperties("employee/offices/address");

//        digester.addSetNext("employee/offices/address", "addAddress", "com.wentong.mytest.Address");
//		digester.addCallMethod("employee", "toString");
        try {
            Employee e = (Employee) digester.parse(file);
            System.out.println(e.getFirstName());
            System.out.println(e.getLastName());
            System.out.println(e.toString());
            ArrayList<Office> offices = e.getOffices();
            for (Office o : offices) {
                System.out.println(o.getName());
                ArrayList<Address> adress = o.getAddress();
                for (Address a : adress) {
                    System.out.println(a.getStreetName());
                    System.out.println(a.getStreetNumber());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }






}
