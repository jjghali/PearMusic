package ca.qc.bdeb.pearmusic.Application;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;


public class GenreTest extends TestCase {

    private Genre instance;

    @Before
    public void setUp() {
        instance = new Genre();
    }

    public void testGenreInconnu(){
        String expected = "Genre inconnu";
        //il n'y a pas de genre à l'indice -1.
        String result = instance.getGenre(-1);
        assertEquals(expected,result);
    }

    @Test
    public void testGetIDGenre()
    {
        int expected = 1;
        int result = instance.getIDGenre("Classic Rock");
        assertEquals(expected,result);
    }

    @Test
    public void testGetIDGenreInconnu()
    {
        int expected = -1;
        int result = instance.getIDGenre("inexistant");
        assertEquals(expected,result);
    }

}