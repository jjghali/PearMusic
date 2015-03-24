package ca.qc.bdeb.pearmusic.Application;

import org.junit.Test;
import junit.framework.TestCase;

public class AleatoireTest extends TestCase{

    Aleatoire instance;

    public void setUp() throws Exception {
        instance = new Aleatoire();
    }

    public void tearDown() throws Exception {

    }

    @Test
    public void testAleatoireDemarreBien(){
        instance.demarrerAleatoire(0);
        assertTrue(instance.estActif());
    }

    @Test
    public void testAleatoireArreteBien(){
        instance.demarrerAleatoire(0);
        instance.arreterAleatoire();
        assertFalse(instance.estActif());
    }

    @Test
    public void testIndexSuivant(){
        instance.demarrerAleatoire(10);
        int index = instance.recupererProchainIndex();
        int indexSuivant = instance.recupererProchainIndex();
        assertNotSame(index, indexSuivant);
    }

    @Test
    public void testIndexPrecedent(){
        instance.demarrerAleatoire(10);
        int index = instance.recupererProchainIndex();
        instance.recupererProchainIndex();
        int indexPrecedent = instance.recupererIndexPrecedent();
        assertEquals(index, indexPrecedent);
    }

    @Test
    public void testAjoutChansons(){
        int tailleListe = 10;
        instance.demarrerAleatoire(5);
        instance.ajouterChansons(5);
        assertEquals(tailleListe, instance.getIndexChansons().size());
    }

    @Test
    public void retirerChanson(){
        int tailleListe = 4;
        instance.demarrerAleatoire(5);
        instance.retirerChanson(1);
        assertEquals(tailleListe, instance.getIndexChansons().size());
    }
}