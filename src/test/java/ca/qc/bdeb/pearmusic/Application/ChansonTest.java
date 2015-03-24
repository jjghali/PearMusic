package ca.qc.bdeb.pearmusic.Application;

import javafx.embed.swing.JFXPanel;
import junit.framework.TestCase;
import org.junit.*;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: dominiquecright
 * Date: 2014-09-15
 * Time: 17:57
 * To change this template use File | Settings | File Templates.
 */
public class ChansonTest extends TestCase {

    private File fichierMp3;
    private final String PISTE = "1 - ";
    private final String ARTISTE = "AC/DC";
    private final String TITRE = "Highway To Hell";
    private final String ALBUM = "Highway To Hell";
    private final String ANNEE = "1979";
    private final int GENRE = 79; // "Hard Rock";
    private final long DUREE = 208; // "Hard Rock";

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        JFXPanel panel = new JFXPanel();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testDureeEnString() {
        Chanson instance = new Chanson("", "AC/DC", "Highway to hell", "1", "Highway to hell", 1, "1972", 208L, null);
        String expected = "3:28";
        String result;
        result = instance.getDureeString();
        assertEquals(expected,result);
    }

    @Test
    public void testInfoDeFichierVide() {

        Chanson test = new Chanson("Highway_To_Hell_ID3v1.mp3", "", "", "", "", 0, "", 0L, null);
        assertEquals("Highway_To_Hell_ID3v1.mp3", test.getPath());
        assertEquals("Artiste inconnu", test.getArtiste());
        assertEquals("Album inconnu", test.getAlbum());
        assertEquals("Blues", test.getGenreString());
        assertEquals("Titre inconnu", test.getTitre());
        assertEquals("#?", test.getPiste());
        assertEquals("Année inconnue", test.getAnnee());
        assertEquals(0, test.getGenre());
        assertEquals(0, test.getDuree());
        assertEquals("0:00", test.getDureeString());

    }

    @Test
    public void testLireInfoDeFichierID3v1() throws IOException{
        fichierMp3 = new File("src" + File.separator + "test" + File.separator + "resources"
                + File.separator + "Highway_To_Hell_ID3v1.mp3");
        Chanson result = new Chanson(fichierMp3.getPath());
        Chanson expected = new Chanson(fichierMp3.getPath(), ARTISTE, TITRE, PISTE, ALBUM, GENRE, ANNEE, DUREE, null);

        if(!result.getPath().equals(expected.getPath())) {
            fail("Le path est invalide");
        }
        if(!result.getArtiste().equals(expected.getArtiste())) {
            fail("L'artiste est invalide");
        }
        if(!result.getTitre().equals(expected.getTitre())) {
            fail("Le titre est invalide");
        }
        if(result.getPiste().equals(expected.getPiste())) {
            fail("La piste est invalide");
        }
        if(!result.getAlbum().equals(expected.getAlbum())) {
            fail("L'album est invalide");
        }
        if(result.getGenre()!=expected.getGenre()) {
            fail("Le genre est invalide");
        }
        if(result.getAnnee().equals(expected.getTitre())) {
            fail("L'annee est invalide");
        }
        if(result.getDuree()!=expected.getDuree()) {
            fail("La duree est invalide");
        }
        if (!result.getGenreString().equals("Hard Rock")) {
            fail("Le genre est invalide");
        }
    }

    @Test
    public void testLireInfoDeFichierID3v2()throws IOException{
        fichierMp3 = new File("src" + File.separator + "test" + File.separator + "resources"
                + File.separator + "Highway_To_Hell_ID3v2.mp3");
        Chanson result = new Chanson(fichierMp3.getPath());
        Chanson expected = new Chanson(fichierMp3.getPath(),ARTISTE, TITRE, PISTE, ALBUM, GENRE, ANNEE, DUREE,null);

        if(!result.getPath().equals(expected.getPath())) {
            fail("Le path est invalide");
        }
        if(!result.getArtiste().equals(expected.getArtiste())) {
            fail("L'artiste est invalide");
        }
        if(!result.getTitre().equals(expected.getTitre())) {
            fail("Le titre est invalide");
        }
        if(result.getPiste().equals(expected.getPiste())) {
            fail("La piste est invalide");
        }
        if(!result.getAlbum().equals(expected.getAlbum())) {
            fail("L'album est invalide");
        }
        if(result.getGenre()!=expected.getGenre()) {
            fail("Le genre est invalide");
        }
        if(result.getAnnee().equals(expected.getTitre())) {
            fail("L'annee est invalide");
        }
        if(result.getDuree()!=expected.getDuree()) {
            fail("La duree est invalide");
        }
        if (!result.getGenreString().equals("Hard Rock")) {
            fail("Le genre est invalide");
        }
    }

    @Test
    public void testEstID3v1() throws IOException
    {
        fichierMp3 = new File("src" + File.separator + "test" + File.separator + "resources"
                + File.separator + "musiquetest_id3v1.mp3");
        Chanson instance = new Chanson(fichierMp3.getPath());
        boolean result = instance.estID3v1();
        assertTrue(result);
    }

    @Test
    public void testEstID3v2() throws IOException
    {
        fichierMp3 = new File("src" + File.separator + "test" + File.separator + "resources"
                + File.separator + "musiquetest_id3v2.mp3");
        Chanson instance = new Chanson(fichierMp3.getPath());
        boolean result = instance.estID3v2();
        assertTrue(result);
    }


}
