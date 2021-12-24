package com.example.dm_prediciton;

public class House {
    String price ;
    String ng_name ;
    String admin_area  ;
    String city  ;
    double rooms  ;
    double bathrooms  ;
    double sqm  ;
    double elev  ;
    double pool  ;
    double driver  ;
    double garden  ;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNg_name() {
        return ng_name;
    }

    public void setNg_name(String ng_name) {
        this.ng_name = ng_name;
    }

    public String getAdmin_area() {
        return admin_area;
    }

    public void setAdmin_area(String admin_area) {
        this.admin_area = admin_area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getRooms() {
        return rooms;
    }

    public void setRooms(double rooms) {
        this.rooms = rooms;
    }

    public double getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(double bathrooms) {
        this.bathrooms = bathrooms;
    }

    public double getSqm() {
        return sqm;
    }

    public void setSqm(double sqm) {
        this.sqm = sqm;
    }

    public double getElev() {
        return elev;
    }

    public void setElev(double elev) {
        this.elev = elev;
    }

    public double getPool() {
        return pool;
    }

    public void setPool(double pool) {
        this.pool = pool;
    }

    public double getDriver() {
        return driver;
    }

    public void setDriver(double driver) {
        this.driver = driver;
    }

    public double getGarden() {
        return garden;
    }

    public void setGarden(double garden) {
        this.garden = garden;
    }

    public House(String price, String ng_name, String admin_area, String city, double rooms, double bathrooms, double sqm, double elev, double pool, double driver, double garden) {
        this.price = price;
        this.ng_name = ng_name;
        this.admin_area = admin_area;
        this.city = city;
        this.rooms = rooms;
        this.bathrooms = bathrooms;
        this.sqm = sqm;
        this.elev = elev;
        this.pool = pool;
        this.driver = driver;
        this.garden = garden;
    }

    public House() {
    }
}
