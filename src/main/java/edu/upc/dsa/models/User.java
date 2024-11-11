package edu.upc.dsa.models;

import edu.upc.dsa.util.RandomUtils;

import java.time.LocalDateTime;
import java.util.TreeMap;

public class User {
    String id;
    String name;
    String surname;
    String email;
    String birthday;
    TreeMap<LocalDateTime, MapElement> visitedElements;

    public User(){
        id = RandomUtils.getId();
        visitedElements = new TreeMap<>();
    }

    public User(String name, String surname, String email, String birthday){
        this();
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.birthday = birthday;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public TreeMap<LocalDateTime, MapElement> getVisitedElements() {
        return visitedElements;
    }

    public void setVisitedElements(TreeMap<LocalDateTime, MapElement> visitedElements) {
        this.visitedElements = visitedElements;
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", name=" + name + "}";
    }
}
