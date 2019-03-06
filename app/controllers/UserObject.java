package controllers;

public class UserObject {

    public UserObject(int id, String firstName, String lastName, String email, String phoneNumber, String town, String county, String country, double longitude, double latitude) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.town = town;
        this.county = county;
        this.country = country;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String town;
    private String county;
    private String country;
    private double longitude;
    private double latitude;

}
