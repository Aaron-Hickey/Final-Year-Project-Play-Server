package controllers;

import com.google.gson.Gson;
import com.google.inject.Inject;
import play.data.DynamicForm;
import play.data.FormFactory;
import play.mvc.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import static play.mvc.Results.ok;

public class UpdateUserLocationController {
    @Inject
    FormFactory formFactory;

    public Result UpdateLocation()
    {
        DynamicForm dynamicForm = formFactory.form().bindFromRequest();
        String id = dynamicForm.get("id");
        String longitude = dynamicForm.get("longitude");
        String latitude = dynamicForm.get("latitude");



        System.out.println("User: "+ id + " Position "+ longitude + " "+ latitude);

        SQLController sqlController = SQLController.getInstance();



        String updateUserStatement = "UPDATE users SET `Longitude` =  "+longitude+" , `Latitude` = "+ latitude +" WHERE `id` = "+id+";";

        sqlController.InsertToMySQL(updateUserStatement);


     return ok();
    }
}
