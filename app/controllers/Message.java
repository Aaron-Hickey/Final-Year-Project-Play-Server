package controllers;

public class Message {
    private String text; // message body
    private String name; // data of the user that sent this message
    private boolean belongsToCurrentUser; // is this message sent by us?

    public Message(String text, String name, boolean belongsToCurrentUser) {
        this.text = text;
        this.name = name;
        this.belongsToCurrentUser = belongsToCurrentUser;
    }

    public String getText() {
        return text;
    }

    public String getName() {
        return name;
    }

    public boolean isBelongsToCurrentUser() {
        return belongsToCurrentUser;
    }
}