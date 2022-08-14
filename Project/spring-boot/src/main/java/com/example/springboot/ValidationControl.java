package com.example.springboot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class ValidationControl {

  @GetMapping("/getDetails")
  public Validation greeting(String username, String password) {
    return new Validation("ABC", "DEF");
  }


  @PostMapping("/validate")
  public boolean validate(@RequestBody Validation V) {

    Connection connection = null;
    boolean check = false;
    try {
        // below two lines are used for connectivity.
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/user",
            "root", "anirudh1");

        // mydb is database
        // mydbuser is name of database
        // mydbuser is password of database

        Statement statement;
        statement = connection.createStatement();
        ResultSet resultSet;
        resultSet = statement.executeQuery(
            "select * from user");
        int code;
        String title;
        String pass;
       
        while (resultSet.next()) {
            code = resultSet.getInt("user_id");
            title = resultSet.getString("user_name");
            System.out.println("Code : " + code
                               + " Title : " + title);
            pass = resultSet.getString("password");
            check = false;
            System.out.println("1 mysql   "+title);
            System.out.println("1 eclipse "+V.getUsername());
            System.out.println("2 mysql   "+pass);
//            System.out.println("2 eclipse "+V.getPassword());
            if (title.equals(V.getUsername()) && pass.equals(V.getPassword())){
              check = true;
              return check;
            }
              
        }
        resultSet.close();
        statement.close();
        connection.close();
    }
    catch (Exception exception) {
        System.out.println(exception);
    }    
    


    System.out.println(check);
    return check;

  }

}
