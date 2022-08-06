package com.example.springboot;

public class Interview {

  public int number;
  public String question;
  
  
  public Interview(int number, String question) {
    this.number = number;
    this.question = question;

  }


  public int getNumber() {
    return number;
  }


  public void setNumber(int number) {
    this.number = number;
  }


  public String getQuestion() {
    return question;
  }


  public void setQuestion(String question) {
    this.question = question;
  }
}
