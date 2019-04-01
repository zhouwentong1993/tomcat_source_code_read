package com.wentong.mytest;

import org.apache.tomcat.util.digester.Digester;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DegisterDemo1 {
    private School school;

    public School getSchool() {
        return school;
    }

    public void setSchool(School s) {
        this.school = s;
    }

    public void digester() throws IOException, SAXException {
        //读取School.xml
        File file = new File("/Users/finup123/IdeaProjects/tomcat/test/com/wentong/mytest/degesterDemo.xml");
        InputStream inputStream = new FileInputStream(file);
        InputSource inputSource = new InputSource(file.toURI().toURL().toString());;
        inputSource.setByteStream(inputStream);
        //创建Digester
        Digester digester = new Digester();
        //是否需要用DTD验证XML文档的合法性
        digester.setValidating(false);
        //将当前对象放到对象堆的最顶层
        digester.push(this);

        /* 下面开始为Digester创建匹配规则
         * 在Digester内School、School/Grade、School/Grade/Class
         * 分别对应School.xml的School、Grade、Class节点
         */

        //为School创建规则
        /*
         * Digester.addObjectCreate(String pattern, String className, String attributeName)
         * pattern--匹配的节点
         * className--该节点对应的默认实体类
         * attributeName--如果该节点有className属性,用className的值替换默认实体类
         * Digester匹配到School节点，如果School节点没有className属性，将创建digester.School对象；如果School节点有className属性，将创建指定的(className属性的值)对象
         */
        digester.addObjectCreate("School",
                "com.wentong.mytest.School",
                "className");
        //将指定节点的属性映射到对象，即将School节点的name的属性映射到School.java
        digester.addSetProperties("School");
        /*
         * Digester.addSetNext(String pattern, String methodName, String paramType)
         * pattern--匹配的节点
         * methodName--调用父节点的方法
         * paramType--父节点的方法接收的参数类型
         * Digester匹配到School节点，将调用DigesterTest(School的父节点)的setSchool方法，参数为School对象
         */
        digester.addSetNext("School",
                "setSchool",
                "com.wentong.mytest.School");

        //为School/Grade创建规则
        digester.addObjectCreate("School/Grade",
                "com.wentong.mytest.Grade",
                "className");
        digester.addSetProperties("School/Grade");
        //Grade的父节点为School
        digester.addSetNext("School/Grade",
                "addGrade",
                "com.wentong.mytest.Grade");

        //为School/Grade/Class创建规则
        digester.addObjectCreate("School/Grade/Class",
                "com.wentong.mytest.Class",
                "className");
        digester.addSetProperties("School/Grade/Class");
        digester.addSetNext("School/Grade/Class",
                "addClass",
                "com.wentong.mytest.Class");
        digester.parse(inputSource);
    }

    //打印School信息
    public void print(School s){
        if(s!=null){
            System.out.println(s.getName() + "有" + s.getGrades().length + "个年级");
            for(int i=0;i<s.getGrades().length;i++){
                if(s.getGrades()[i] !=null){
                    Grade g = s.getGrades()[i];
                    System.out.println(g.getName() + "年级 有 " + g.getClasses().length + "个班：");
                    for(int j=0;j<g.getClasses().length;j++){
                        if(g.getClasses()[j] !=null){
                            Class c = g.getClasses()[j];
                            System.out.println(c.getName() + "班有" + c.getNumber() + "人");
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException, SAXException {
        DegisterDemo1 digesterTest = new DegisterDemo1();
        digesterTest.digester();
        digesterTest.print(digesterTest.school);
    }

}
