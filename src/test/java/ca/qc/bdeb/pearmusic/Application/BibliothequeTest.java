package ca.qc.bdeb.pearmusic.Application;

import ca.qc.bdeb.pearmusic.AccesPersistance.DBHelper;
import javafx.scene.image.Image;
import junit.framework.TestCase;
import org.junit.*;
import org.tmatesoft.sqljet.core.SqlJetException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class BibliothequeTest extends TestCase {

    Bibliotheque instance;
    ArrayList<File> fichiers;
    DBHelper db;

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws URISyntaxException, IOException, SqlJetException {
        db = DBHelper.getInstance();
        // On vide la table musique avant de faire les tests
        db.truncateTableMusique();

        fichiers = creerListeChansons();

        instance = new Bibliotheque();
    }

    @After
    public void tearDown() throws SqlJetException {
        // On retire toutes les données de tests qui pourraient rester
        db.truncateTableMusique();
    }

    @Test
    public void testCreationBibliotheque() {
        assertNotNull(instance);
    }

    @Test
    public void testObtenirChansons() throws SqlJetException {
        ArrayList<ChansonDTO> listeAttendue;
        ArrayList<ChansonDTO> listeRecue;

        // Données de test dans la bd
        ajouterDesChansonsALaBD();
        // Données de test dans la listeDTO
        listeAttendue = creerListeDTODeTest();

        listeRecue = instance.obtenirChansons();

        assertEquals(listeAttendue.get(1).getPath(), listeRecue.get(1).getPath());
    }

    @Test
    public void testSauvegarderFichiers()throws IOException{
        instance.sauvegarderFichier(fichiers);

        ArrayList<ChansonDTO> chansons = instance.obtenirChansons();
        int countExpected = chansons.size();

        assertEquals(countExpected, fichiers.size());
    }

    @Test
    public void testAjouterChanson()  {
        int tailleAttendue = 1;

        instance.ajouterChanson(new ChansonDTO("/Pear_music/musique2.mp3", "5",
                "Pink Floyd", "Money", "The Dark Side Of The Moon", "1976", 4, 321, ""));

        // Vérifie qu'il y a bien une entrée dans la bd
        assertEquals(tailleAttendue, db.getTailleTabMusique());
    }

    @Test
    public void testUpdateChanson() throws Exception {
        ChansonDTO chansonDTOTest;
        String resultatAttendu = "Echoes";

        chansonDTOTest = new ChansonDTO("/Pear_music/musique2.mp3", "5",
                "Pink Floyd", "Money", "Echoes", "1976", 4, 321, "");

        db.ajouterChanson(new ChansonDTO("/Pear_music/musique2.mp3", "5",
                "Pink Floyd", "Money", "The Dark Side Of The Moon", "1976", 4, 321, ""));

        instance.mettreAJourChanson(chansonDTOTest);

        assertEquals(resultatAttendu, db.getChansonsBibliotheque().get(0).getAlbum());

    }

    /**
     * Test qui verifie si la methode obtenirChansonsAlbum retourne les bonnes chansons pour un album voulu
     * @throws Exception
     */
    @Test
    public void testObtenirChansonsAlbum() throws Exception {
        String nomAlbum = "Echoes";
        ArrayList<ChansonDTO> expected = new ArrayList<ChansonDTO>();
        ArrayList<ChansonDTO> result;

        expected.add(new ChansonDTO("/Pear_music/musique2.mp3", "6",
                "Pink Floyd", "Money", nomAlbum, "1976", 4, 321, ""));
        expected.add(new ChansonDTO("/Pear_music/musique3.mp3", "5",
                "Pink Floyd", "Cash", nomAlbum, "1976", 4, 326, ""));

        db.ajouterChanson(new ChansonDTO("/Pear_music/musique2.mp3", "6",
                "Pink Floyd", "Money", nomAlbum, "1976", 4, 321, ""));
        db.ajouterChanson(new ChansonDTO("/Pear_music/musique3.mp3", "5",
                "Pink Floyd", "Cash", nomAlbum, "1976", 4, 326, ""));
        db.ajouterChanson(new ChansonDTO("/Pear_music/musique4.mp3", "7",
                "Pink Floyd", "Money", "Not the same album", "1972", 4, 212, ""));

        result = db.getChansonsAlbumBibliotheque(nomAlbum);

        for (int i = 0; i < expected.size(); ++i){
            if(!result.get(i).getAlbum().equals(nomAlbum) && !result.get(i).getTitre().equals(expected.get(i).getTitre())){
                fail("Les valeurs retournes ne correspondent pas aux valeurs attendus.");
            }
        }
    }

    @Test
    public void testObtenirChansonArtiste(){
        instance.ajouterChanson(new ChansonDTO("/Pear_music/musique2.mp3", "5",
                "Pink Floyd", "Money", "The Dark Side Of The Moon", "1976", 4, 321, ""));
        instance.ajouterChanson(new ChansonDTO("/Pear_music/musique3.mp3", "5",
                "Pink Floyd", "Cash", "Echoes", "1976", 4, 326, ""));
        instance.ajouterChanson(new ChansonDTO("/Pear_music/musique4.mp3", "7",
                "Pink Floyd", "Money", "Not the same album", "1972", 4, 212, ""));
        ArrayList<ChansonDTO> expected = new ArrayList<>();
        expected.add(new ChansonDTO("/Pear_music/musique2.mp3", "5",
                "Pink Floyd", "Money", "The Dark Side Of The Moon", "1976", 4, 321, ""));
        expected.add(new ChansonDTO("/Pear_music/musique3.mp3", "5",
                "Pink Floyd", "Cash", "Echoes", "1976", 4, 326, ""));
        expected.add(new ChansonDTO("/Pear_music/musique4.mp3", "7",
                "Pink Floyd", "Money", "Not the same album", "1972", 4, 212, ""));

        ArrayList<ChansonDTO> result = instance.obtenirChansonsArtiste("Pink Floyd");
        for (int i = 0; i < result.size(); i++) {
            assertEquals(expected.get(i), result.get(i));
        }
    }

    @Test
    public void testObtenirChansonAlbum(){
        instance.ajouterChanson(new ChansonDTO("/Pear_music/musique2.mp3", "5",
                "Pink Floyd", "Money", "Echoes", "1976", 4, 321, ""));
        instance.ajouterChanson(new ChansonDTO("/Pear_music/musique3.mp3", "5",
                "Pink Floyd", "Cash", "Echoes", "1976", 4, 326, ""));
        instance.ajouterChanson(new ChansonDTO("/Pear_music/musique4.mp3", "7",
                "Pink Floyd", "Money", "Echoes", "1972", 4, 212, ""));
        ArrayList<ChansonDTO> expected = new ArrayList<>();
        expected.add(new ChansonDTO("/Pear_music/musique2.mp3", "5",
                "Pink Floyd", "Money", "Echoes", "1976", 4, 321, ""));
        expected.add(new ChansonDTO("/Pear_music/musique3.mp3", "5",
                "Pink Floyd", "Cash", "Echoes", "1976", 4, 326, ""));
        expected.add(new ChansonDTO("/Pear_music/musique4.mp3", "7",
                "Pink Floyd", "Money", "Echoes", "1972", 4, 212, ""));

        ArrayList<ChansonDTO> result = instance.obtenirChansonsAlbum("Echoes");
        for (int i = 0; i < result.size(); i++) {
            assertEquals(expected.get(i), result.get(i));
        }
    }

    @Test
    public void testDeleteChanson() throws SqlJetException {
        int tailleBibliothequeAttendue = 1;
        String cheminDuFichierAEnlever = "/Pear_music/musique1.mp3";

        ajouterDesChansonsALaBD();

        instance.deleteChanson(cheminDuFichierAEnlever);

        // La bd ne devrait contenir qu'un fichier
        assertEquals(tailleBibliothequeAttendue, db.getTailleTabMusique());
        // On s'assure que le fichier qui reste n'est pas celui qu'on voulait effacher
        assertFalse(db.getChansonsBibliotheque().get(0).getPath() == cheminDuFichierAEnlever);
    }


    /**
     * Test qui s'assure que la bibliothèque demande bien à la BD de supprimer un album.
     * @throws SqlJetException
     */
    @Test
    public void testDeleteAlbum() {
        int result;
        int expected = 0;
        String nomAlbum = "Echoes";

        instance.ajouterChanson(new ChansonDTO("/Pear_music/musique2.mp3", "6",
                "Pink Floyd", "Money", nomAlbum, "1976", 4, 321, ""));
        instance.ajouterChanson(new ChansonDTO("/Pear_music/musique3.mp3", "5",
                "Pink Floyd", "Cash", nomAlbum, "1976", 4, 326, ""));
        instance.ajouterChanson(new ChansonDTO("/Pear_music/musique4.mp3", "7",
                "Pink Floyd", "Money", nomAlbum, "1972", 4, 212, ""));

        instance.deleteAlbum(nomAlbum);
        result = instance.obtenirAlbums().size();
        assertEquals(expected, result);
    }

    /**
     * Test qui s'assure que la bibliothèque demande bien à la BD de supprimer un artiste.
     * @throws SqlJetException
     */
    @Test
    public void testDeleteArtiste() {
        int result;
        int expected = 0;
        String nomArtiste = "Pink Floyd";

        instance.ajouterChanson(new ChansonDTO("/Pear_music/musique2.mp3", "6",
                nomArtiste, "Money", "Echoes", "1976", 4, 321, ""));
        instance.ajouterChanson(new ChansonDTO("/Pear_music/musique3.mp3", "5",
                nomArtiste, "Cash", "Echoes", "1976", 4, 326, ""));
        instance.ajouterChanson(new ChansonDTO("/Pear_music/musique4.mp3", "7",
                nomArtiste, "Money", "Not the same album", "1972", 4, 212, ""));

        instance.deleteArtiste(nomArtiste);
        result = instance.obtenirArtistes().size();
        assertEquals(expected, result);
    }

    /**
     * Test qui s'assure que la bibliothèque demande bien à la BD
     * de supprimer une playlist.
     */
    @Test
    public void testDeleteListeDeLecture(){
        int nombrePlaylistAuDebut;
        int nombreDePlaylistFin;

        String nomPlaylist = "PlaylistDeTest";
        String cheminPlaylist = "/Pear_music/PlaylistDeTest.pear";
        String[] nomEtCheminPlaylist = new String[]{nomPlaylist, cheminPlaylist};
        nombrePlaylistAuDebut = instance.obtenirPlaylists().size();
        instance.insererPlaylist(nomEtCheminPlaylist);
        instance.deleteListeDeLecture(cheminPlaylist);
        nombreDePlaylistFin = instance.obtenirPlaylists().size();
        assertEquals(nombrePlaylistAuDebut, nombreDePlaylistFin);
    }

    @Test
    public void testObtenirChansonStringArtiste(){
        String artiste = "Pink Floyd";

        instance.ajouterChanson(new ChansonDTO("/Pear_music/musique2.mp3", "5",
                "Pink Floyd", "Money", "The Dark Side Of The Moon", "1976", 4, 321, ""));
        instance.ajouterChanson(new ChansonDTO("/Pear_music/musique3.mp3", "5",
                "Pink Floyd", "Cash", "Echoes", "1976", 4, 326, ""));
        instance.ajouterChanson(new ChansonDTO("/Pear_music/musique4.mp3", "7",
                "Pink Floyd", "Money", "Not the same album", "1972", 4, 212, ""));

        ArrayList<String> expected = new ArrayList<>();
        expected.add("/Pear_music/musique2.mp3");
        expected.add("/Pear_music/musique3.mp3");
        expected.add("/Pear_music/musique4.mp3");
        ArrayList<String> chansons = instance.obtenirChansonsStringArtiste(artiste);

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), chansons.get(i));
        }

    }

    @Test
    public void testObtenirChansonStringAlbum (){
        String album = "The Dark Side Of The Moon";

        instance.ajouterChanson(new ChansonDTO("/Pear_music/musique2.mp3", "5",
                "Pink Floyd", "Money", "The Dark Side Of The Moon", "1976", 4, 321, ""));
        instance.ajouterChanson(new ChansonDTO("/Pear_music/musique3.mp3", "5",
                "Pink Floyd", "Cash", "The Dark Side Of The Moon", "1976", 4, 326, ""));
        instance.ajouterChanson(new ChansonDTO("/Pear_music/musique4.mp3", "7",
                "Pink Floyd", "Money", "The Dark Side Of The Moon", "1972", 4, 212, ""));

        ArrayList<String> expected = new ArrayList<>();
        expected.add("/Pear_music/musique2.mp3");
        expected.add("/Pear_music/musique3.mp3");
        expected.add("/Pear_music/musique4.mp3");
        ArrayList<String> chansons = instance.obtenirChansonsStringAlbums(album);

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i), chansons.get(i));
        }

    }

    @Test
    public void testObtenirArtiste(){
        instance.ajouterChanson(new ChansonDTO("/Pear_music/musique2.mp3", "5",
                "Pink Floyd", "Money", "The Dark Side Of The Moon", "1976", 4, 321, ""));
        instance.ajouterChanson(new ChansonDTO("/Pear_music/musique3.mp3", "5",
                "Pink Floyd", "Cash", "The Dark Side Of The Moon", "1976", 4, 326, ""));
        instance.ajouterChanson(new ChansonDTO("/Pear_music/musique4.mp3", "7",
                "Pink Floyd", "Money", "The Dark Side Of The Moon", "1972", 4, 212, ""));

        ArrayList<ChansonDTO> expected = new ArrayList<>();
        expected.add(new ChansonDTO("/Pear_music/musique2.mp3", "5",
                "Pink Floyd", "Money", "The Dark Side Of The Moon", "1976", 4, 321, ""));
        expected.add(new ChansonDTO("/Pear_music/musique3.mp3", "5",
                "Pink Floyd", "Cash", "The Dark Side Of The Moon", "1976", 4, 326, ""));
        expected.add(new ChansonDTO("/Pear_music/musique4.mp3", "7",
                "Pink Floyd", "Money", "The Dark Side Of The Moon", "1972", 4, 212, ""));

        ArrayList<ChansonDTO> resultat =instance.obtenirArtistes();

        for (int i = 0; i < resultat.size(); i++) {
            assertEquals(expected.get(i), resultat.get(i));
        }

    }

    @Test
    public void testUpdateInfo(){
        instance.ajouterChanson(new ChansonDTO("/Pear_music/musique2.mp3", "5",
                "Pink Floyd", "Money", "The Dark Side Of The Moon", "1976", 4, 321, ""));
        instance.ajouterChanson(new ChansonDTO("/Pear_music/musique3.mp3", "5",
                "Pink Floyd", "Cash", "The Dark Side Of The Moon", "1976", 4, 326, ""));
        instance.ajouterChanson(new ChansonDTO("/Pear_music/musique4.mp3", "7",
                "Pink Floyd", "Money", "The Dark Side Of The Moon", "1972", 4, 212, ""));

        ChansonDTO update = new ChansonDTO("/Pear_music/musique2.mp3", "5",
                "Pink Freud", "Money", "The Dark Side Of The Moon", "1976", 4, 321, ""); // nom de l'artiste changee

        instance.mettreAJourChanson(update);

        ArrayList<ChansonDTO> chansons = instance.obtenirChansons();
        ChansonDTO resultat = chansons.get(0);

        assertEquals(update.getArtiste(), resultat.getArtiste());
    }

    @Test
    public void testVerifierCheminExistant(){
        instance.ajouterChanson(new ChansonDTO("/Pear_music/musique2.mp3", "5",
                "Pink Floyd", "Money", "The Dark Side Of The Moon", "1976", 4, 321, ""));
        instance.ajouterChanson(new ChansonDTO("/Pear_music/musique3.mp3", "5",
                "Pink Floyd", "Cash", "The Dark Side Of The Moon", "1976", 4, 326, ""));
        instance.ajouterChanson(new ChansonDTO("/Pear_music/musique4.mp3", "7",
                "Pink Floyd", "Money", "The Dark Side Of The Moon", "1972", 4, 212, ""));

        ChansonDTO DTOchemin = new ChansonDTO("/Pear_music/musique2.mp3", "5",
                "Pink Floyd", "Money", "The Dark Side Of The Moon", "1976", 4, 321, ""); // Chanson a verifier

        assertFalse(instance.verifierCheminExistant(DTOchemin));
    }

    /**
     * Création de données de test dans la BD
     * @throws SqlJetException
     */
    private void ajouterDesChansonsALaBD() throws SqlJetException {
        // On ajoute des chansons à la BD
        db.ajouterChanson(new ChansonDTO("/Pear_music/musique1.mp3", "1",
                "BonJovi", "Keep the Faith", "Best Of", "1992", 8, 234, ""));
        db.ajouterChanson(new ChansonDTO("/Pear_music/musique2.mp3", "5",
                "Pink Floyd", "Money", "The Dark Side Of The Moon", "1976", 4, 321, ""));
    }

    /**
     * Méthode qui créé une liste d'infoChansonDTO de test
     * @return
     */
    private ArrayList<ChansonDTO> creerListeDTODeTest() {
        ArrayList<ChansonDTO> listeAttendue = new ArrayList<ChansonDTO>();

        listeAttendue.add(new ChansonDTO("/Pear_music/musique1.mp3", "1",
                "BonJovi", "Keep the Faith", "Best Of", "1992", 8, 234, ""));
        listeAttendue.add(new ChansonDTO("/Pear_music/musique2.mp3", "5",
                "Pink Floyd", "Money", "The Dark Side Of The Moon", "1976", 4, 321, ""));

        return listeAttendue;
    }

    private ArrayList<File> creerListeChansons() throws URISyntaxException, IOException {
        URL pathFichier = ClassLoader.getSystemResource("testchanson1.mp3");
        URL pathFichier2 = ClassLoader.getSystemResource("testchanson2.mp3");
        ArrayList<File> fichiers = new ArrayList<File>();
        fichiers.add(new File(pathFichier.toURI()));
        fichiers.add(new File(pathFichier2.toURI()));
        return fichiers;
    }
}