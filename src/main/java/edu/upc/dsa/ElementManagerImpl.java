package edu.upc.dsa;

import edu.upc.dsa.exceptions.ElementNotFoundException;
import edu.upc.dsa.exceptions.UserNotFoundException;
import edu.upc.dsa.models.ElementType;
import edu.upc.dsa.models.MapElement;
import edu.upc.dsa.models.User;
import org.apache.log4j.Logger;

import javax.lang.model.element.Element;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class ElementManagerImpl implements ElementManager {
    private static final ElementManager instance = new ElementManagerImpl();
    final static Logger logger = Logger.getLogger(ElementManagerImpl.class);

    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<MapElement> elements = new ArrayList<>();

    private ElementManagerImpl() {}

    public static ElementManager getInstance() {
        return instance;
    }

    @Override
    public User addUser(User u) {
        users.add(u);
        logger.info("Added user " + u.getId());
        return u;
    }

    @Override
    public User addUser(String name, String surname, String email, String birthday) {
        User u = new User(name, surname, email, birthday);
        return addUser(u);
    }

    @Override
    public ArrayList<User> listUsers() {
        logger.info(users);
        return users;
    }

    @Override
    public User getUser(String id) {
        for (User u : users) {
            if (u.getId().equals(id)) {
                logger.info(u);
                return u;
            }
        }
        logger.error("User " + id + " does not exist");
        throw new UserNotFoundException("User " + id + " does not exist");
    }

    @Override
    public MapElement getElement(int horizontal, int vertical){
        for(MapElement e : elements){
            if(e.getHorizontal() == horizontal && e.getVertical() == vertical){
                logger.info(e);
                return e;
            }
        }
        logger.error("No element found at " + horizontal + ", " + vertical);
        throw new ElementNotFoundException("No element found at " + horizontal + ", " + vertical);
    }

    @Override
    public MapElement addElement(MapElement e) {
        elements.add(e);
        logger.info("New element added: " + e);
        return e;
    }

    @Override
    public MapElement addElement(ElementType type, int horizontal, int vertical) {
        MapElement e = new MapElement(type, horizontal, vertical);
        return addElement(e);
    }

    @Override
    public void visitElement(String userId, int horizontal, int vertical) throws ElementNotFoundException, UserNotFoundException {
        User u = getUser(userId);
        MapElement e = getElement(horizontal, vertical);
        u.getVisitedElements().put(LocalDateTime.now(), e);
        e.getUsersSeen().put(LocalDateTime.now(), u);
        logger.info(u + " has visited " + e);
    }

    @Override
    public TreeMap<LocalDateTime, MapElement> getVisitedElements(String userId) throws UserNotFoundException{
        User u = getUser(userId);
        logger.info(u.getVisitedElements());
        return u.getVisitedElements();
    }

    @Override
    public TreeMap<LocalDateTime, User> getSeenUsers(int vertical, int horizontal) throws ElementNotFoundException {
        MapElement e = getElement(horizontal, vertical);
        logger.info(e.getUsersSeen());
        return e.getUsersSeen();
    }

    @Override
    public ArrayList<MapElement> getElements(ElementType type) {
        ArrayList<MapElement> filteredElements = new ArrayList<>();
        for (MapElement e : elements) {
            if (e.getType().equals(type)) { filteredElements.add(e); }
        }
        logger.info("Elements of type " + type + ": " + filteredElements);
        return filteredElements;
    }

    @Override
    public void clear(){
        users.clear();
        elements.clear();
    }
}
