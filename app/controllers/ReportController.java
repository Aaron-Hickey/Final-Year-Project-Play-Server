package controllers;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.pusher.pushnotifications.PushNotifications;
import com.pusher.rest.Pusher;
import play.mvc.Result;
import views.html.index;
import play.data.DynamicForm;
import play.data.FormFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.channels.Channel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import static play.mvc.Results.ok;


public class ReportController {
   @Inject FormFactory formFactory;


    public Result handleReport() {

        System.out.println("volley http POST mapped correctly to this method.");
      //  DynamicForm dynamicForm = formFactory.form().bindFromRequest();
     //   System.out.println("Description: " + dynamicForm.get("description"));
     //   System.out.println("Latitude: " + dynamicForm.get("latitude"));
      //  System.out.println("Longitude: " + dynamicForm.get("longitude"));

     //   SQLController sqlc = SQLController.getInstance();
     //   String number = sqlc.getNumbers();

     //   SMSController smsc = new SMSController();
     //   smsc.sendSMS("Athlone", "Test Message", "Test User", number);

        return ok(index.render("Project"));

    }

    public Result demoReport() throws SQLException {
        DynamicForm dynamicForm = formFactory.form().bindFromRequest();
        int userID = Integer.parseInt(dynamicForm.get("id"));
        String message = dynamicForm.get("message");
       // String name = dynamicForm.get("firstname");

        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String currentTime = sdf.format(dt);

        SQLController sqlc = SQLController.getInstance();
        ResultSet rs1 = sqlc.SelectFromMySQL("Select * from users where id !="+ userID);
        ResultSet rs2 = sqlc.SelectFromMySQL("Select * from users where id = "+ userID);

        SMSController smsc = new SMSController();

        sqlc.InsertToMySQL("Insert into reports (User_ID, MESSAGE, TIMEOFREPORT) VALUES (" +
                userID  + "," +

                "'" + message  + "'," +
                "'" + currentTime  + "'" +
                ");");

        if(rs2.next()) {


            while (rs1.next()) {
                // System.out.println(rs.getString("phonenumber"));
                // smsc.sendSMS("Test Location", message, "Test User", ""+rs.getString("phonenumber"));
                double longitude1 = rs1.getDouble("Longitude");
                double latitude1 = rs1.getDouble("Latitude");


                double longitude2 = rs2.getDouble("Longitude");
                double latitude2 = rs2.getDouble("Latitude");


                //Haversine formula
                double R = 6371e3; // metres
                double φ1 = Math.toRadians(latitude1);
                double φ2 = Math.toRadians(latitude2);
                double Δφ = Math.toRadians(latitude2 - latitude1);
                double Δλ = Math.toRadians(longitude2 - longitude1);

                double a = Math.sin(Δφ / 2) * Math.sin(Δφ / 2) +
                        Math.cos(φ1) * Math.cos(φ2) *
                                Math.sin(Δλ / 2) * Math.sin(Δλ / 2);
                double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

                double d = R * c;

                double distanceInKilometres = d/1000;


                System.out.println("Distance is " + distanceInKilometres +" km");

         /*       Gson g = new Gson();
                Message messageToSend = new Message(message, rs2.getString("FirstName") + " " + rs2.getString("LastName"),false);
                String str = g.toJson(messageToSend);
                System.out.println("JSON "+str);

                Pusher pusher = new Pusher("727521", "bec6b057b12a49314680", "00a88a7795ea279b0f18");
                pusher.setCluster("eu");
                pusher.setEncrypted(true);



                pusher.trigger(""+rs1.getString("ID"), "my-event", str);

*/
                //beams

                if(distanceInKilometres <= 10) {
                    try {
                        String instanceId = "a1e05091-6b9b-46b6-b848-69b980dca55e";
                        String secretKey = "798BECB1811E7F5C45FAA83826E652B69723AE7823735BA4C307439C1B034FD2";

                        PushNotifications beamsClient = new PushNotifications(instanceId, secretKey);


                        List<String> interests = Arrays.asList("" + rs1.getInt("id"));

                        Map<String, Map> publishRequest = new HashMap();
                        Map<String, String> fcmNotification = new HashMap();
                        fcmNotification.put("title", rs2.getString("FirstName"));
                        fcmNotification.put("body", message);
                        Map<String, Map> fcm = new HashMap();
                        fcm.put("data", fcmNotification);
                        publishRequest.put("fcm", fcm);
                        //   publishRequest.put("body", "HELLO THERE");


                        System.out.println("SENDING " + message + " to " + rs1.getInt("id"));
                        beamsClient.publish(interests, publishRequest);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                }

            }
            rs1.close();
            rs2.close();
        }
        return ok("Message Sent");

    }
}
