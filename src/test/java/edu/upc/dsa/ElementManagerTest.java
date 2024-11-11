package edu.upc.dsa;

import edu.upc.dsa.exceptions.ElementNotFoundException;
import edu.upc.dsa.models.ElementType;
import edu.upc.dsa.models.MapElement;
import edu.upc.dsa.models.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ElementManagerTest {
    ElementManager em;
    User testUser;

    @Before
    public void setUp(){
        em = ElementManagerImpl.getInstance();
        testUser = em.addUser("Jan", "Viñas", "jan.vinas@estudiantat.upc.edu", "1/4/2003");
        em.addElement(ElementType.WALL, 1, 5);
        em.addElement(ElementType.SWORD, 1, 6);
        em.addElement(ElementType.POTION, 1, 7);
        em.addElement(ElementType.GRASS, 1, 8);
    }

    @After
    public void tearDown(){
        em.clear();
        em = null;
    }

    @Test
    public void addElementTest(){
        em.addElement(ElementType.WALL, 2, 5);
        em.getElement(2, 5); //should not throw exception
    }

    @Test
    public void getElementTest(){
        Assert.assertEquals(ElementType.POTION, em.getElement(1, 7).getType());
        Assert.assertEquals(ElementType.GRASS, em.getElement(1, 8).getType());

        // no element at this location
        Assert.assertThrows(ElementNotFoundException.class, () -> em.getElement(1, 9));
    }

    @Test
    public void visitElementTest(){
        // no hauria de sortir error si troba l'usuari i l'element
        em.visitElement(testUser.getId(), 1, 6);
        // comprovem que s'hagi registrat
        Assert.assertEquals(1, em.getElement(1, 6).getUsersSeen().size());
        Assert.assertEquals(1, em.getVisitedElements(testUser.getId()).size());
;   }

    @Test
    public void getElementsByTypeTest(){
        Assert.assertEquals(1, em.getElements(ElementType.SWORD).size());

        MapElement sword = em.getElements(ElementType.SWORD).get(0);
        Assert.assertEquals(1, sword.getHorizontal());
        Assert.assertEquals(6, sword.getVertical());
    }
}
