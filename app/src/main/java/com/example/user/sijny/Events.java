package com.example.user.sijny;

public class Events {
    private String title;
    private String linkAddress;

    //Constructor

    public Events(String title, String linkAddress) {
        this.title = title;
        this.linkAddress = linkAddress;
    }

    //Setter, getter

    public String getName() {
        return title;
    }

    public void setName(String name) {
        this.title = name;
    }

    public String getDescription() {
        return linkAddress;
    }

    public void setDescription(String linkAddress) {
        this.linkAddress = linkAddress;
    }
}
