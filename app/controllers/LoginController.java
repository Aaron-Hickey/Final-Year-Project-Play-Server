package controllers;

import com.google.gson.Gson;
import com.google.inject.Inject;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import static play.mvc.Results.ok;

public class LoginController {
    @Inject
    FormFactory formFactory;

    public Result login()
    {
        DynamicForm dynamicForm = formFactory.form().bindFromRequest();
        String email = dynamicForm.get("email");
        String password = dynamicForm.get("password");


        SQLController sqlController = SQLController.getInstance();

     /*   String checkLogin= "Select count(*) as 'no' from users where email = '"+email+"' && userpassword = '"+password+"';";

        try {
            ResultSet rs = sqlController.SelectFromMySQL(checkLogin);
            int numberOfAccounts = 0;
            while(rs.next())
            {
                numberOfAccounts = rs.getInt("no");
            }
            if(numberOfAccounts == 0)
            {
                System.out.println("Invalid Login Details");
                return ok("Invalid Login Details");
            }
            else if(numberOfAccounts ==1)
            {

                return ok("Login Successful");
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return ok("An Error Occured, Please Try Again");
        }*/

        String getUserStatement = "Select * from users where email = '"+email+"' && userpassword = '"+password+"';";

        try{
            ResultSet rs = sqlController.SelectFromMySQL(getUserStatement);
            if (rs.next() ) {
                int id = rs.getInt("id");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
              //  String email = rs.getString("email");
                String phoneNumber = rs.getString("phoneNumber");
                String town = rs.getString("town");
                String county = rs.getString("county");
                String country = rs.getString("country");
                double longitude = rs.getDouble("longitude");
                double latitude = rs.getDouble("latitude");

                UserObject uo = new UserObject(id,firstName,lastName,email,phoneNumber,town,county,country,longitude,latitude);



                Gson g = new Gson();

                String str = g.toJson(uo);

                System.out.println("Logged in: "+email);

                return ok(str);
            }
            else
            {
                System.out.println("Login Failed");
                return ok("!Invalid Login Details");
            }


        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return ok("!An Error Occured, Please Try Again");
        }
    }
}
