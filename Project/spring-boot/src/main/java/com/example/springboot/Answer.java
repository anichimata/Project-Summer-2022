package com.example.springboot;

public class Answer {
  String pPage="";
  String pAnswers="";
  public String getpPage() {
    return pPage;
  }
  public void setpPage(String pPage) {
    this.pPage = pPage;
  }
  public String getpAnswers() {
    return pAnswers;
  }
  public void setpAnswers(String pAnswers) {
    this.pAnswers = pAnswers;
  }
public Answer(String pPage,String pAnswers) {
  this.pPage=pPage;
  this.pAnswers=pAnswers;
}
}
