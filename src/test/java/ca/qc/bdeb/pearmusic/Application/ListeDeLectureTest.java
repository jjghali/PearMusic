package ca.qc.bdeb.pearmusic.Application;

import javafx.embed.swing.JFXPanel;
import junit.framework.TestCase;
import org.junit.*;

import javax.swing.*;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class ListeDeLectureTest extends TestCase {

    ListeDeLecture instance;
    ArrayList<ChansonDTO> chansonDTOs;

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws IOException, URISyntaxException {
        JFXPanel panel = new JFXPanel();
        creerListeFichiers();
        instance = new ListeDeLecture(chansonDTOs);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testSauvegardeCheminsAvecCreationDuFichier() throws IOException {
        String nomFichier = "playlistDeTest";
        String defaultFolder = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
        String ligne = "";
        int compteur = 0;
        instance.sauvegarderChemins(nomFichier);
        BufferedReader lecteurFichier = null;
        try {
            lecteurFichier = new BufferedReader(new FileReader(defaultFolder + File.separator + "Pear_Music"
                    + File.separator + nomFichier + ".pear"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if(lecteurFichier != null) {
            while ((ligne = lecteurFichier.readLine()) != null) {
                assertEquals(chansonDTOs.get(compteur).getPath(), ligne);
                compteur++;
            }
        }
        else {
            fail();
        }

        lecteurFichier.close();
    }

    @Test
    public void testChargementChansons() throws IOException {
        String nomFichier = "playlistDeTest";
        instance.sauvegarderChemins(nomFichier);
        ArrayList<ChansonDTO> fichiersCharger = new ArrayList<ChansonDTO>();
        int compteur = 0;
        fichiersCharger.addAll(instance.chargerChansons(nomFichier));
        for(ChansonDTO fichier : fichiersCharger){
            assertSame(chansonDTOs.get(compteur), fichier);
            compteur++;
        }
    }

    /**
     * S'assure que le chemin d'une chanson est bien ajouté au fichier de la playlist choisie.
     * @throws IOException
     */
    @Test
    public void testAjouterChansonFichier() throws IOException {
        String nomFichier = "playlistDeTest";
        ChansonDTO chanson = chansonDTOs.get(0);
        int chansonAvantAjout = instance.chargerChansons(nomFichier).size();
        instance.ajouterChanons(chanson, nomFichier);
        int resultat = instance.chargerChansons(nomFichier).size();
        int expected = chansonAvantAjout + 1;
        assertEquals(expected, resultat);
    }

    private void creerListeFichiers() throws URISyntaxException, IOException {
        URL pathFichier = ClassLoader.getSystemResource("musiquetest.mp3");
        URL pathFichier2 = ClassLoader.getSystemResource("musiquetest2.mp3");
        URL pathFichier3 = ClassLoader.getSystemResource("musiquetest3.mp3");
        URL pathFichier4 = ClassLoader.getSystemResource("musiquetest4.mp3");
        URL pathFichier5 = ClassLoader.getSystemResource("musiquetest5.mp3");
        ArrayList<File> fichiers = new ArrayList<File>();
        fichiers.add(new File(pathFichier.toURI()));
        fichiers.add(new File(pathFichier2.toURI()));
        fichiers.add(new File(pathFichier3.toURI()));
        fichiers.add(new File(pathFichier4.toURI()));
        fichiers.add(new File(pathFichier5.toURI()));

        chansonDTOs = new ArrayList<ChansonDTO>();
        for(File f : fichiers){
            Chanson c = new Chanson(f.getAbsolutePath());
            chansonDTOs.add(c.asDTO());
        }

    }

}