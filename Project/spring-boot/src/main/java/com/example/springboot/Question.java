package com.example.springboot;


import java.util.ArrayList;
import java.util.List;

public class Question {
    
    String id="";
    String question="";
    List<String> options=new ArrayList<String>();
    String type="";
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }
    public List<String> getOptions() {
        return options;
    }
    public void setOptions(List<String> options) {
        this.options = options;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    

}