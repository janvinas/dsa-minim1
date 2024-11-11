package edu.upc.dsa.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.upc.dsa.util.RandomUtils;

import java.time.LocalDateTime;
import java.util.TreeMap;

public class MapElement {
    @JsonIgnore
    String id;
    ElementType type;
    int horizontal;
    int vertical;
    @JsonIgnore
    TreeMap<LocalDateTime, User> usersSeen;

    public MapElement(){
        id = RandomUtils.getId();
        usersSeen = new TreeMap<>();
    }

    public MapElement(ElementType type, int horizontal, int vertical){
        this();
        this.type = type;
        this.horizontal = horizontal;
        this.vertical = vertical;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ElementType getType() {
        return type;
    }

    public void setType(ElementType type) {
        this.type = type;
    }

    public int getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(int horizontal) {
        this.horizontal = horizontal;
    }

    public int getVertical() {
        return vertical;
    }

    public void setVertical(int vertical) {
        this.vertical = vertical;
    }

    public TreeMap<LocalDateTime, User> getUsersSeen() {
        return usersSeen;
    }

    public void setUsersSeen(TreeMap<LocalDateTime, User> usersSeen) {
        this.usersSeen = usersSeen;
    }

    @Override
    public String toString(){
        return "MapElement{type=" + type + ", h=" + horizontal + ", v=" + vertical + "}";
    }
}
