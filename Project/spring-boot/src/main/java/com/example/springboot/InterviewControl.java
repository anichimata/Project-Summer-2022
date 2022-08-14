package com.example.springboot;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

@RestController
@CrossOrigin
public class InterviewControl {

  public int score;

  @GetMapping("/easy")
  public List<Question> easy() {
    List<Question> myQuestions = new ArrayList<Question>();
    try {
      Question q1 = new Question();
      q1.setId("1");
      q1.setQuestion("Question 1");
      List<String> myOptions = new ArrayList<String>();
      myOptions.add("option 1");
      myOptions.add("option 2");
      myOptions.add("option 3");
      myOptions.add("option 4");
      q1.setOptions(myOptions);
      q1.setType("radio");

      Question q2 = new Question();
      q2.setId("2");
      q2.setQuestion("Question 2");
      List<String> myOptions2 = new ArrayList<String>();
      myOptions2.add("option 1");
      myOptions2.add("option 2");
      myOptions2.add("option 3");
      myOptions2.add("option 4");
      q2.setType("text");
      q2.setOptions(myOptions2);


      myQuestions.add(q1);
      myQuestions.add(q2);

    } catch (Exception exception) {
      System.out.println(exception);
    }

    return myQuestions;
  }

  @GetMapping("/easyQuestions")
  public List<Question> easyQuestions() {
    List<Question> myQuestions = new ArrayList<Question>();
    Connection connection = null;
    try {
      // below two lines are used for connectivity.
      Class.forName("com.mysql.cj.jdbc.Driver");
      connection =
          DriverManager.getConnection("jdbc:mysql://localhost:3306/interview", "root", "anirudh1");

      Statement statement;
      statement = connection.createStatement();
      ResultSet resultSet;
      resultSet = statement.executeQuery(
          "select t.QNo, d.DNo, d.Difficulty, t.Question from text t, difficulty d where t.DNo = d.DNo and t.DNo = 11");

      while (resultSet.next()) {
        Question q = new Question();
        int qNumber = resultSet.getInt("QNo");
        String question = resultSet.getString("Question");
        String diff = resultSet.getString("Difficulty");

        Statement statement_option = null;
        statement_option = connection.createStatement();
        ResultSet resultSet_option;
        resultSet_option =
            statement_option.executeQuery("select  answer, QNo from answer where QNo =" + qNumber);
        List<String> myOptions = new ArrayList<String>();
        while (resultSet_option.next()) {
          String answer = resultSet_option.getString("answer");
          myOptions.add(answer);
        }
        q.setId(String.valueOf(qNumber));
        q.setQuestion(question);
        q.setType("radio");
        q.setOptions(myOptions);

        System.out.println("" + qNumber + "   " + question);
        myQuestions.add(q);

      }
      resultSet.close();
      statement.close();
      connection.close();
    } catch (Exception exception) {
      System.out.println(exception);
    }

    return myQuestions;
  }

  @GetMapping("/mediumQuestions")
  public List<Question> mediumQuestions() {
    List<Question> myQuestions = new ArrayList<Question>();
    Connection connection = null;
    try {
      // below two lines are used for connectivity.
      Class.forName("com.mysql.cj.jdbc.Driver");
      connection =
          DriverManager.getConnection("jdbc:mysql://localhost:3306/interview", "root", "anirudh1");

      Statement statement;
      statement = connection.createStatement();
      ResultSet resultSet;
      resultSet = statement.executeQuery(
          "select t.QNo, d.DNo, d.Difficulty, t.Question from text t, difficulty d where t.DNo = d.DNo and t.DNo = 12");

      while (resultSet.next()) {
        Question q = new Question();
        int qNumber = resultSet.getInt("QNo");
        String question = resultSet.getString("Question");
        String diff = resultSet.getString("Difficulty");

        Statement statement_option = null;
        statement_option = connection.createStatement();
        ResultSet resultSet_option;
        resultSet_option =
            statement_option.executeQuery("select  answer, QNo from answer where QNo =" + qNumber);
        List<String> myOptions = new ArrayList<String>();
        while (resultSet_option.next()) {
          String answer = resultSet_option.getString("answer");
          myOptions.add(answer);
        }
        q.setId(String.valueOf(qNumber));
        q.setQuestion(question);
        if (qNumber == 9 || qNumber == 11) {
          q.setType("text");
        } else {
          q.setType("radio");
        }
        q.setOptions(myOptions);

        System.out.println("" + qNumber + "   " + question);
        myQuestions.add(q);

      }
      resultSet.close();
      statement.close();
      connection.close();
    } catch (Exception exception) {
      System.out.println(exception);
    }

    return myQuestions;
  }

  @GetMapping("/hardQuestions")
  public List<Question> hardQuestions() {
    List<Question> myQuestions = new ArrayList<Question>();
    Connection connection = null;
    try {
      // below two lines are used for connectivity.
      Class.forName("com.mysql.cj.jdbc.Driver");
      connection =
          DriverManager.getConnection("jdbc:mysql://localhost:3306/interview", "root", "anirudh1");

      Statement statement;
      statement = connection.createStatement();
      ResultSet resultSet;
      resultSet = statement.executeQuery(
          "select t.QNo, d.DNo, d.Difficulty, t.Question from text t, difficulty d where t.DNo = d.DNo and t.DNo = 13");

      while (resultSet.next()) {
        Question q = new Question();
        int qNumber = resultSet.getInt("QNo");
        String question = resultSet.getString("Question");
        String diff = resultSet.getString("Difficulty");

        Statement statement_option = null;
        statement_option = connection.createStatement();
        ResultSet resultSet_option;
        resultSet_option =
            statement_option.executeQuery("select  answer, QNo from answer where QNo =" + qNumber);
        List<String> myOptions = new ArrayList<String>();
        while (resultSet_option.next()) {
          String answer = resultSet_option.getString("answer");
          myOptions.add(answer);
        }
        q.setId(String.valueOf(qNumber));
        q.setQuestion(question);
        if (qNumber == 16 || qNumber == 17) {
          q.setType("text");
        } else {
          q.setType("radio");
        }
        q.setOptions(myOptions);

        System.out.println("" + qNumber + "   " + question);
        myQuestions.add(q);

      }
      resultSet.close();
      statement.close();
      connection.close();
    } catch (Exception exception) {
      System.out.println(exception);
    }

    return myQuestions;
  }

  @GetMapping("/freeResponse")
  public List<Question> freeResponse() {
    List<Question> myQuestions = new ArrayList<Question>();
    Connection connection = null;
    try {
      // below two lines are used for connectivity.
      Class.forName("com.mysql.cj.jdbc.Driver");
      connection =
          DriverManager.getConnection("jdbc:mysql://localhost:3306/interview", "root", "anirudh1");

      Statement statement;
      statement = connection.createStatement();
      ResultSet resultSet;
      resultSet = statement.executeQuery(
          "select t.QNo, d.DNo, d.Difficulty, t.Question from text t, difficulty d where t.DNo = d.DNo and t.DNo = 14");

      while (resultSet.next()) {
        Question q = new Question();
        int qNumber = resultSet.getInt("QNo");
        String question = resultSet.getString("Question");
        String diff = resultSet.getString("Difficulty");

        q.setId(String.valueOf(qNumber));
        q.setQuestion(question);
        q.setType("textarea");
        q.setOptions(null);

        System.out.println("" + qNumber + "   " + question);
        myQuestions.add(q);

      }
      resultSet.close();
      statement.close();
      connection.close();
    } catch (Exception exception) {
      System.out.println(exception);
    }

    return myQuestions;
  }

  @PostMapping("/submitAnswers")
  public boolean submitAnswers(@RequestParam String pPage, @RequestParam String pAnswers) {

    if (score != 0) {
      score = 0;
    }

    boolean check = false;
    System.out.println(pAnswers);
    System.out.println(pPage);

    String output = pAnswers.toUpperCase();
    String[] splitAnswers = output.split("@@");
    for (int i = 0; i < splitAnswers.length; i++) {
      System.out.println(splitAnswers[i]);
    }
    int multiplier = Integer.valueOf(pPage);

    if (Integer.valueOf(pPage) == 1) {
      score(splitAnswers, 1, 6, multiplier);
      if (score >= 4) {
        check = true;
      }
    } else if (Integer.valueOf(pPage) == 2) {
      score(splitAnswers, 7, 11, multiplier);
      System.out.println("break");
      System.out.println(score);
      if (score >= 8) {
        check = true;
      }
    } else if (Integer.valueOf(pPage) == 3) {
      score(splitAnswers, 12, 17, multiplier);
      if (score >= 15) {
        check = true;
      }
    } else if (Integer.valueOf(pPage) == 4) {
     systemDesign(splitAnswers, 18, 20, multiplier);
      if (score >= 15) {
        check = true;
      }
    }
    else {
      
    }
    return check;
  }

  private List<String> removeStopWords(List<String> answer) {
    File stopWords = new File("stopWords.txt");


    List<String> myStopWords = new ArrayList<String>();
    try {

      Scanner sc = new Scanner(stopWords);

      while (sc.hasNextLine()) {

        String word = sc.next();
        if (word != null && !word.isEmpty()) {
          myStopWords.add(word);
        }
      }
      sc.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    answer.removeAll(myStopWords);
    return answer;
  }

  private List<String> tokenizeAnswer(String answer) {
    List<String> answerList = new ArrayList<String>();
    try {
    System.out.println(answer);
    answer = answer.replaceAll("\\p{Punct}", "");
    System.out.println(answer);


    StringTokenizer st1 = new StringTokenizer(answer);

    for (int i = 1; st1.hasMoreTokens(); i++) {
      String eachAnswer = st1.nextToken();
      eachAnswer = eachAnswer.trim();
      answerList.add(eachAnswer.toLowerCase());
    }
    } catch (Exception e){
      e.printStackTrace();
    }
    return answerList;
  }

  private void systemDesign(String[] answers, int start, int end, int multiplier) {
    try {
      List<String> input ;
      for(int i = 1; i < answers.length; i++) {
        input = tokenizeAnswer(answers[i]);
        System.out.println("T"+input);
        input = removeStopWords(input);
        System.out.println("R"+input);
      }
    } catch (Exception exception) {
      System.out.println(exception);
    }
  }
  private void score(String[] answers, int start, int end, int multiplier) {
    Connection connection = null;
    try {
      // below two lines are used for connectivity.
      Class.forName("com.mysql.cj.jdbc.Driver");
      connection =
          DriverManager.getConnection("jdbc:mysql://localhost:3306/interview", "root", "anirudh1");

      Statement statement;
      statement = connection.createStatement();
      ResultSet resultSet;
      resultSet = statement
          .executeQuery("select * from CorrectAnswers where QNo between " + start + " and " + end);

      while (resultSet.next()) {
        int question = resultSet.getInt("QNo");
        String answer = resultSet.getString("answer");
        System.out.println("Current score is "+score);
        // use if statements to check which difficulty using another resultSet
        System.out.println(answers[question - start + 1]);
        System.out.println("----------");
        System.out.println(answer);
        if (answers[question - start + 1].contains(answer)) {
          System.out.println("enters if");
          score += (1 * multiplier);
        }
        System.out.println(score + "   after if");
      }
      resultSet.close();
      statement.close();
      connection.close();
    } catch (Exception exception) {
      System.out.println(exception);
    }
  }

  @PostMapping("/appScore")
  public void appScore(@RequestParam int number) {

    Connection connection = null;
    try {
      // below two lines are used for connectivity.
      Class.forName("com.mysql.cj.jdbc.Driver");
      connection =
          DriverManager.getConnection("jdbc:mysql://localhost:3306/interview", "root", "anirudh1");

      Statement statement;
      statement = connection.createStatement();
      ResultSet resultSet;
      resultSet = statement.executeQuery(
          "select SNo, a.ID,i.QNo,t.DNo from applicant a, interview i, text t where a.ID = i.ID and i.QNo = t.QNo and a.ID ="
              + number);

      while (resultSet.next()) {
        int ID = resultSet.getInt("ID");
        // use if statements to check which difficulty using another resultSet
        String sql = "update applicant set score = score + 1 where ID = " + ID;
        Statement stmt = connection.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();

      }
      resultSet.close();
      statement.close();
      connection.close();
    } catch (Exception exception) {
      System.out.println(exception);
    }

  }

  @PostMapping("/resetScore")
  public void resetScore() {
    Connection connection = null;
    try {
      // below two lines are used for connectivity.
      Class.forName("com.mysql.cj.jdbc.Driver");
      connection =
          DriverManager.getConnection("jdbc:mysql://localhost:3306/interview", "root", "anirudh1");

      String sql = "update applicant set score = 0 ";
      Statement stmt = connection.createStatement();
      stmt.executeUpdate(sql);

      stmt.close();
      connection.close();
    } catch (Exception exception) {
      System.out.println(exception);
    }

  }

  @PostMapping("/interview")
  public List<Interview> extractQuestions() {
    List<Interview> myQuestions = new ArrayList<Interview>();
    Connection connection = null;
    try {
      // below two lines are used for connectivity.
      Class.forName("com.mysql.cj.jdbc.Driver");
      connection =
          DriverManager.getConnection("jdbc:mysql://localhost:3306/interview", "root", "anirudh1");

      // mydb is database
      // mydbuser is name of database
      // mydbuser is password of database

      Statement statement;
      statement = connection.createStatement();
      ResultSet resultSet;
      resultSet = statement.executeQuery("select * from text");

      while (resultSet.next()) {
        int qNumber = resultSet.getInt("interview.QNo");
        String question = resultSet.getString("Question");
        Interview myQuestion = new Interview(qNumber, question);

        System.out.println("" + qNumber + "   " + question);
        myQuestions.add(myQuestion);

      }
      resultSet.close();
      statement.close();
      connection.close();
    } catch (Exception exception) {
      System.out.println(exception);
    }

    return myQuestions;
  }

}
