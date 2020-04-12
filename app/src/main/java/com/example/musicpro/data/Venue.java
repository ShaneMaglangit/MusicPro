package com.example.musicpro.data;

public class Venue {
    private Long id;
    private String name;
    private String address;
    private String openingTime;

    public Venue(Long id, String name, String address, String openingTime) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.openingTime = openingTime;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }
}
