package edu.upc.dsa;

import edu.upc.dsa.exceptions.ElementNotFoundException;
import edu.upc.dsa.exceptions.UserNotFoundException;
import edu.upc.dsa.models.ElementType;
import edu.upc.dsa.models.MapElement;
import edu.upc.dsa.models.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.TreeSet;

public interface ElementManager {
    User addUser(User u);
    User addUser(String name, String surname, String email, String birthday);
    TreeSet<User> listUsers();
    User getUser(String id) throws UserNotFoundException;
    MapElement getElement(int horizontal, int vertical) throws ElementNotFoundException;
    MapElement addElement(MapElement e);
    MapElement addElement(ElementType type, int horizontal, int vertical);
    void visitElement(String userId, int horizontal, int vertical) throws ElementNotFoundException, UserNotFoundException;
    TreeMap<LocalDateTime, MapElement> getVisitedElements(String userId) throws UserNotFoundException;
    TreeMap<LocalDateTime, User> getSeenUsers(int vertical, int horizontal) throws ElementNotFoundException;
    ArrayList<MapElement> getElements(ElementType type);
    void clear();
}
