package controllers;

import play.mvc.Result;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import static play.mvc.Results.ok;



public class SMSController {
    public SMSController(){};

  /*   public boolean sendSMS(String location, String message, String user, String number){
        if (args.length < 3) {
            System.out.println("Please specify your access key, one ore more phone numbers and a message body example : java -jar <this jar file> test_accesskey 31612345678,3161112233 \"My message to be send\"");
            return;
        }

        // First create your service object
        final MessageBirdService wsr = new MessageBirdServiceImpl("pmk5CCmKTXw8ljRstCawXM1bblv0cte9");

        // Add the service to the client
        final MessageBirdClient messageBirdClient = new MessageBirdClient(wsr);

        try {
            // Get Hlr using msgId and msisdn
            System.out.println("Sending message:");
            final List<BigInteger> phones = new ArrayList<>();
            for (final String phoneNumber : number.split(",")) {
                phones.add(new BigInteger(phoneNumber));
            }

            final MessageResponse response = messageBirdClient.sendMessage("MessageBird", "testing...", phones);
            //Display message response
            System.out.println(response.toString());
        } catch (UnauthorizedException | GeneralException exception) {
            if (exception.getErrors() != null) {
                System.out.println(exception.getErrors().toString());
            }
            exception.printStackTrace();
        }



        return true;
    }*/

    //text local api code - no longer used
   public Result testSMS() {
        try {
            // Construct data
            String apiKey = "apikey=" + "fnl4MNxc9Ww-m3T2oQy8nGQH1CTOvAcqSxlbTjny1B";
            String message = "&message=" + "SMS Test";
            String sender = "&sender=" + "Community Watch";
            String numbers = "&numbers=" + "+353876032236";

            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.txtlocal.com/send/?").openConnection();
            String data = apiKey + numbers + message + sender;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line);
            }
            rd.close();

            return ok(stringBuffer.toString());
        } catch (Exception e) {
            System.out.println("Error SMS "+e);
            return ok("Error "+e);
        }
    }

    public boolean sendSMS(String location, String messageParam, String user, String number) {
        try {
            // Construct data
            String apiKey = "apikey=" + "fnl4MNxc9Ww-m3T2oQy8nGQH1CTOvAcqSxlbTjny1B";
            String message = "&message=" + "SMS Test";
            String sender = "&sender=" + "Community Watch";
            String numbers = "&numbers=" + number;

            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.txtlocal.com/send/?").openConnection();
            String data = apiKey + numbers + message + sender;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line);
            }
            rd.close();

            return true;
        } catch (Exception e) {
            System.out.println("Error SMS " + e);
            return false;
        }
    }
}