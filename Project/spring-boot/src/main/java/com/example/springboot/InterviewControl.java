package com.example.springboot;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import opennlp.tools.stemmer.PorterStemmer;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

@RestController
@CrossOrigin
/**
 * The InterviewControl class controls all the functions of the interview.
 * @author Anirudh Chimata
 *
 */
public class InterviewControl {

  public int score;
  public int finalScore;
  public String loggedUser;
  public List<String> myStopWords;

  /**
   * The setUser method stores the username provided by the user until it can be retrieved for
   * insertion into the database
   * 
   * @param username the username of the user
   */
  @PostMapping("/setUser")
  public void setUser(@RequestParam String username) {
    loggedUser = username;
  }

  /**
   * The easy method is an unused method originally used to learn the workings of the get Mapping.
   * The four methods easyQuestions, mediumQUestions, hardQuestions and freeResponse are modeled on
   * this method. A question is added if there is a question within a table in the database
   * associated with the corresponding difficulty
   * 
   * @return it returns a list of questions.
   * 
   */
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

  /**
   * The easyQuestions is a GET method associated with the easy Round of the interview. It selects
   * the questions from the text table in the MySQL database using the unique code , using the MySQL
   * connector functions. This API is called by the JavaScript for the first round of the interview
   * where the questions are printed onto the window. In this round, each question is worth one
   * point
   * 
   * @return returns a list of easy questions that appear on the window
   */
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

  /**
   * The mediumQuestions is a GET method associated with the medium Round of the interview. It
   * selects the questions from the text table in the MySQL database using the unique code 12, using
   * the MySQL connector functions. This API is called by the JavaScript for the second round of the
   * interview where the questions are printed onto the window. In this round, each question is
   * worth two points
   * 
   * @return returns a list of medium questions that appear on the window
   */
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

  /**
   * The hardQuestions is a GET method associated with the hard Round of the interview. It selects
   * the questions from the text table in the MySQL database using the unique code 13, using the
   * MySQL connector functions. This API is called by the JavaScript for the third round of the
   * interview where the questions are printed onto the window. In this round, each question is
   * worth three points
   * 
   * @return returns a list of easy questions that appear on the window
   */
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

  /**
   * The freeResponse is a GET method associated with the System Design Round of the interview. It
   * selects the questions from the text table in the MySQL database using the unique code 14, using
   * the MySQL connector functions. This API is called by the JavaScript for the fourth round of the
   * interview where the questions are printed onto the window. In this round, each question is
   * worth five points
   * 
   * @return returns a list of System Design questions that appear on the window
   */
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

  /**
   * The submitAnswers method exclusively used by the first three rounds of the interview to compute
   * the score per round. It also calls the score method to help compute score based on question
   * number It adds the score of each individual round to the finalScore public variable
   * 
   * @param pPage    is the page number, it is responsible for directing the compiler to the right
   *                 part of the if statement based on the difficulty level
   * @param pAnswers is the concatenated string of all the answers provided by the user in the HTML
   *                 page
   * @return whether the user passed the round that the method is called for
   */
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
      finalScore = score;
      System.out.println("Final Score after round One is " + finalScore);
    } else if (Integer.valueOf(pPage) == 2) {
      score(splitAnswers, 7, 11, multiplier);
      System.out.println("break");
      System.out.println(score);
      if (score >= 6) {
        check = true;
      }
      finalScore += score;
      System.out.println("Final Score after round Two is " + finalScore);
    } else if (Integer.valueOf(pPage) == 3) {
      score(splitAnswers, 12, 17, multiplier);
      if (score >= 12) {
        check = true;
        finalScore += score;
        System.out.println("Final Score after round Three is " + finalScore);
      }
    } else if (Integer.valueOf(pPage) == 4) {
      systemDesign(splitAnswers, 18, 20, multiplier);
      finalScore += score;
      System.out.println("Final Score after round Four is " + finalScore);
      if (score >= 5) {
        check = true;
      }
    } else {

    }
    return check;
  }

  /**
   * The submitAnswers method exclusively used by the last round of the interview to compute the
   * score per round. It also calls the systemDesign method to help compute score based on question
   * number. It adds the score of the round to the finalScore public variable
   * 
   * @param pAnswer is the body provided by JavaScript containing the page Number 4 and the answers
   *                provided by the user in the HTML page
   * @return true, as the user will not know the results of the System Design Round
   */
  @PostMapping("/submitDesign")
  public boolean submitDesign(@RequestBody Answer pAnswer) {
    String pPage = pAnswer.getpPage();
    String pAnswers = pAnswer.getpAnswers();

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
      finalScore = score;
      System.out.println("Final Score after round One is " + finalScore);
    } else if (Integer.valueOf(pPage) == 2) {
      score(splitAnswers, 7, 11, multiplier);
      System.out.println("break");
      System.out.println(score);
      if (score >= 8) {
        check = true;
      }
      finalScore += score;
      System.out.println("Final Score after round Two is " + finalScore);
    } else if (Integer.valueOf(pPage) == 3) {
      score(splitAnswers, 12, 17, multiplier);
      if (score >= 15) {
        check = true;
        finalScore += score;
        System.out.println("Final Score after round Three is " + finalScore);
      }
    } else if (Integer.valueOf(pPage) == 4) {
      systemDesign(splitAnswers, 18, 20, 5);
      finalScore += score;
      System.out.println("Final Score after round Four is " + finalScore);
      check = true;
    } else {

    }
    return check;
  }

  /**
   * The removeStopWords compares the answer provided by the user in the System Design round to an
   * existing list of stop words, or words that do not hold value. These words are deleted from the
   * answer and returned back in there respective positions in the list of words within the
   * respective answer
   * 
   * @param answer is a list of words in the answer containing stop words
   * @return a list of the words in the answer without the stop words
   */
  private List<String> removeStopWords(List<String> answer) {
    File stopWords = new File("stopWords.txt");


    myStopWords = new ArrayList<String>();
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

  /**
   * tokenizeAnswer trims each word within the answer and puts each individual word into a list of
   * words provided by the user in response to a certain question
   * 
   * @param answer is the answer provided by the user in response to a certain question in the
   *               System Design Round
   * @return a list of words in the answer
   */
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
    } catch (Exception e) {
      e.printStackTrace();
    }
    return answerList;
  }

  /**
   * systemDesign method performs all the functions to be performed on the answers provided. The
   * answer is tokenized, stemmed and the stop words are removed. Then, the resulting list of words
   * is compared to a data trainingSet with many potential answers for the particular question.
   * 
   * @param answers    is an array of answers provided by the user in response to the System Design
   *                   questions
   * @param start      is the starting question number - 18
   * @param end        is the ending question number - 20
   * @param multiplier is the value to be multiplied with, 5
   */
  private void systemDesign(String[] answers, int start, int end, int multiplier) {
    double totalDesign = 0;
    try {
      List<String> input;
      for (int i = 1; i < answers.length; i++) {
        input = tokenizeAnswer(answers[i]);
        // System.out.println("T"+input);
        input = removeStopWords(input);
        // System.out.println("R"+input);
        List<String> myRootWords = stemAnswers(input);
        System.out.println("S" + myRootWords);
        Map<String, Integer> myDataset = trainDatasets(start + i - 1);
        String question = "question" + (start + i - 1) + ".txt";
        Collection<Integer> myValues = myDataset.values();
        int sum = 0;
        double totalProbability = 1;
        for (Integer value : myValues) {
          sum += value;
        }
        Set<String> hSet = new HashSet<String>();
        for (String x : myRootWords) {
          hSet.add(x);
        }
        for (String myRootWord : hSet) {
          long x = 0L;
          long y = 0L;
          if (myDataset.get(myRootWord) != null) {
            x = myDataset.get(myRootWord);
            y = (long) sum;
          }
          double eachProbability = ((double) (x * 100.0 / y)) / 100;

          if (eachProbability > 0.01) {
            System.out.println(myRootWord + eachProbability);
            totalProbability *= eachProbability;
          }
        }
        for (int j = 1; j < 100; j++) {
          totalProbability = totalProbability * 10;
          if (totalProbability >= 10) {
            break;
          }
        }
        System.out.println(totalProbability);
        System.out.println(5 * totalProbability / 100);
        double eachScore = 0;
        if (Double.compare(totalProbability, 10.0) != 0) {

          System.out.println("YES");
          eachScore = totalProbability * 5 / 100;
          System.out.println(eachScore);
        }
        if (eachScore > 3) {
          writeToFile(question, answers[i]);
        }
        totalDesign += eachScore;
      }
    } catch (Exception exception) {
      System.out.println(exception);
    }
    System.out.println(totalDesign);
    totalDesign = Math.ceil(totalDesign);
    System.out.println(totalDesign);
    int finalDesign = (int) totalDesign;
    System.out.println(finalDesign);
    score = finalDesign;
  }

  /**
   * stemAnswers method stems the root words provided by the user. The answer can then be compared
   * to get a suitable score
   * 
   * @param input is the answer provided by the user
   * @return stemmed answer
   */
  private List<String> stemAnswers(List<String> input) {
    PorterStemmer s = new PorterStemmer();
    List<String> myRootWords = new ArrayList<String>();
    for (String myEachInput : input) {
      String myRootWord = s.stem(myEachInput);
      myRootWords.add(myRootWord);
    }
    return myRootWords;
  }

  /**
   * The writeToFile method is an unused method that was intended to be used to write very well
   * written answers into the files that contain their answers. This was not implemented as there is
   * no good way to gauge if an answer is 100% accurate. Over time, the source file will be
   * corrupted with an undesirable training set and this will affect future scores.
   * 
   * @param question the question being answered
   * @param answer   the answer to add to the text file
   */
  private void writeToFile(String question, String answer) {
    try {
      Files.write(Paths.get(question), answer.getBytes(), StandardOpenOption.APPEND);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * the trainDatasets goes through the source files containing the desirable answers and finds a
   * list of words that should exist in the user's answer in order for the answer to be considered
   * correct. It follows the same method followed by the systemDesign method but implements it on
   * the trainingSet instead of the user's answers
   * 
   * @param QNo the number of the question being answered
   * @return a map containing a word in the answer and its occurrence within the number
   */
  private Map<String, Integer> trainDatasets(int QNo) {
    Map<String, Integer> bagOfWords = new HashMap<String, Integer>();
    File trainingSet = new File("question" + QNo + ".txt");


    StringBuilder wordSetBuilder = new StringBuilder();
    try {

      Scanner sc = new Scanner(trainingSet);

      while (sc.hasNextLine()) {

        String word = sc.nextLine();
        if (word != null && !word.isEmpty()) {
          wordSetBuilder.append(word);
        }
      }
      sc.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

    List<String> wordSet = tokenizeAnswer(wordSetBuilder.toString());

    wordSet.removeAll(myStopWords);
    wordSet = stemAnswers(wordSet);
    for (String eachWord : wordSet) {
      if (bagOfWords.containsKey(eachWord)) {
        int count = bagOfWords.get(eachWord);
        count++;
        bagOfWords.put(eachWord, count);
      } else {
        bagOfWords.put(eachWord, 1);
      }
    }
    return bagOfWords;
  }

  /**
   * The score method is called by the submitAnswers to compute the score of an individual round. It
   * gives the respective score for each correct answer. The score per round is added up and set in
   * the score public variable, which is later referenced in submitAnswers to add up the finalScore.
   * Using MySQL connection, the correct answer is checked and if the answer is correct, a suitable
   * number of points is added
   * 
   * @param answers    is a list of answers provided by the user in response to the questions of the
   *                   respective round
   * @param start      is the starting question number of the round
   * @param end        is the ending question number of the round
   * @param multiplier is the score per question
   */
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
        System.out.println("Current score is " + score);
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

  /**
   * the appScore method is an unused method that was originally used to learn how to compute the
   * finalScore of each user. A different method was created later on.
   * 
   * @param number is the id number of the applicant
   */
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

  /**
   * The resetScore method is an unused method that was first used to test the score addition in the
   * previous stages, now it is done automatically whenever the easy round is opened
   */
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

  /**
   * The interview method is an unused method that was originally used to read the questions inputed
   * in the back end through MySQL
   * 
   * @return a list of the questions
   */
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

  /**
   * The recordUser method saves the username and finalScore of every user in a MySQL table after
   * the interview is completed.
   * 
   * @return the username of the user
   */
  @PostMapping("/recordUser")
  public String recordUser() {
    Connection connection = null;
    try {
      // below two lines are used for connectivity.
      Class.forName("com.mysql.cj.jdbc.Driver");
      connection =
          DriverManager.getConnection("jdbc:mysql://localhost:3306/interview", "root", "anirudh1");

      Statement statement;
      statement = connection.createStatement();
      ResultSet resultSet;
      resultSet =
          statement.executeQuery("select * from user where username = '" + loggedUser + "'");

      if (resultSet.next()) {
        String sql =
            "update user set score = " + finalScore + " where username = '" + loggedUser + "'";
        statement = connection.createStatement();
        statement.executeUpdate(sql);
      } else {
        String sql = "insert into user values('" + loggedUser + "'," + finalScore + ")";
        statement.executeUpdate(sql);
      }
      resultSet.close();
      statement.close();
      connection.close();
    } catch (Exception exception) {
      System.out.println(exception);
    }
    return loggedUser;
  }

}
