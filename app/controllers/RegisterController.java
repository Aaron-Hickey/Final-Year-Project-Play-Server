package controllers;

import com.google.inject.Inject;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Result;

import java.sql.ResultSet;
import java.sql.SQLException;

import static play.mvc.Results.ok;


public class RegisterController {
    @Inject FormFactory formFactory;


    public Result addUserToDatabase() {
        System.out.println("Adding user to database");
        DynamicForm dynamicForm = formFactory.form().bindFromRequest();
        SQLController sqlController = SQLController.getInstance();

        String nameFirst = dynamicForm.get("nameFirst");
        String nameLast = dynamicForm.get("nameLast");
        String email = dynamicForm.get("email");
        String password = dynamicForm.get("password");
        String phone = dynamicForm.get("phone");
        String town = dynamicForm.get("town");
        String county = dynamicForm.get("county");
        String country = dynamicForm.get("country");

        String checkEmail= "Select count(*) as 'no' from users where email = '"+email+"';";
        try {
            ResultSet rs = sqlController.SelectFromMySQL(checkEmail);
            int numberOfEmails = 0;
            while(rs.next())
            {
                numberOfEmails = rs.getInt("no");
            }
            if(numberOfEmails > 0)
            {
                System.out.println("An account with that email already exists");
                return ok("An account with that email already exists");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return ok("An Error Occured, Please Try Again");
        }

        String checkPhone= "Select count(*) as 'no' from users where phonenumber = '"+phone+"';";
        try {
            ResultSet rs = sqlController.SelectFromMySQL(checkPhone);
            int numberOfPhones = 0;
            while(rs.next())
            {
                numberOfPhones = rs.getInt("no");
            }
            if(numberOfPhones > 0)
            {
                System.out.println("An account with that phone number already exists");
                return ok("An account with that phone number already exists");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return ok("An Error Occured, Please Try Again");
        }

        String insertStatement = "Insert into users (firstname,lastname,email,userpassword,phonenumber,town,county,country,longitude,latitude) values (" +
                "'"+nameFirst+"',"+
                "'"+nameLast+"',"+
                "'"+email+"',"+
                "'"+password+"',"+
                "'"+phone+"',"+
                "'"+town+"',"+
                "'"+county+"',"+
                "'"+country+"',"+
                +0.0+","+
                +0.0+

                ");";


        boolean isSuccess = sqlController.InsertToMySQL(insertStatement);
        System.out.println(insertStatement);

        if(isSuccess)
        {
            System.out.println("User Added");
            return ok("Registered Successfully");
        }
        else
        {
            System.out.println("Failed to add User");

            return ok("Registration Failed, Please Try Again");
        }
    }
}
