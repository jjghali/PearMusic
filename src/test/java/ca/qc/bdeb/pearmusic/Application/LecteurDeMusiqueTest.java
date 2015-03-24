package ca.qc.bdeb.pearmusic.Application;

import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.util.Duration;
import junit.framework.TestCase;
import org.junit.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class LecteurDeMusiqueTest extends TestCase implements ConstantesApplication{

    LecteurDeMusique instance;
    ArrayList<Chanson> fichiers;
    final int TEMPS_AVANCE_REMBOBINAGE = 1000;
    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws URISyntaxException, IOException {
        JFXPanel panel = new JFXPanel();
        fichiers = creerListeChansons();
        instance = new LecteurDeMusique();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testIfMusicPlays() throws IOException, URISyntaxException {
        setChansonAJouer();
        instance.jouerChanson();
        assertEquals(EtatLecture.LECTURE, instance.getEtatLecture());
    }

    @Test
    public void testMusicHasStopped() throws IOException, URISyntaxException {
        setChansonAJouer();
        instance.jouerChanson();
        instance.arreterChanson();
        assertEquals(EtatLecture.ARRET, instance.getEtatLecture());
    }

    @Test
    public void testMusicHasPaused() throws IOException, URISyntaxException {
        setChansonAJouer();
        instance.jouerChanson();
        instance.mettreSurPauseChanson();
        assertEquals(EtatLecture.PAUSE, instance.getEtatLecture());
    }

    @Test
    public void testChangementDeChanson() throws IOException, URISyntaxException {
        URL pathFichier = ClassLoader.getSystemResource("musiquetest.mp3");
        File f = new File(pathFichier.toURI());
        ArrayList<Chanson> fichier = new ArrayList<Chanson>();
        fichier.add(new Chanson(f.getAbsolutePath()));
        int index = 0;
        Media mediaVoulu = new Media(pathFichier.toString());
        instance.setListeMusique(fichier);
        instance.setChansonEnCours(index);
        String sourceMediaVoulu = mediaVoulu.getSource();
        String sourceMediaRecu = instance.getMedia();
        assertEquals(sourceMediaVoulu, sourceMediaRecu);
    }

    @Test
    public void testTempsEcoule() throws IOException, URISyntaxException, InterruptedException {
        String resultatAttendu = "0:02";

        setChansonAJouer();
        instance.jouerChanson();
        dormirThread(2500);
        instance.mettreSurPauseChanson();
        assertEquals(resultatAttendu, instance.calculerTempsEcoule());
    }

    @Test
    public void testPourcentageEcoule() throws IOException, URISyntaxException {
        double resultatAttendu = 0.01904761904761905;

        setChansonAJouer();
        instance.jouerChanson();
        dormirThread(2500);
        instance.mettreSurPauseChanson();
        assertEquals(resultatAttendu, instance.calculerPourcentageTempsEcoule());
    }

    private void setChansonAJouer() throws IOException, URISyntaxException {
        URL pathFichier = ClassLoader.getSystemResource("musiquetest.mp3");
        File f = new File(pathFichier.toURI());
        ArrayList<Chanson> fichier = new ArrayList<Chanson>();
        fichier.add(new Chanson(f.getAbsolutePath()));
        int index = 0;
        instance.setListeMusique(fichier);
        instance.setChansonEnCours(index);
    }

    @Test
    public void testAjoutChansons()throws IOException{
        instance.ajouterChansons(creerListeFichier());
        assertEquals(fichiers.size(), instance.getListeMusique().size());
    }

    @Test
    public void testAjoutChansonsDejaExistante()throws IOException{
        ArrayList<ChansonDTO> fichierAjout = new ArrayList<ChansonDTO>();
        fichierAjout.add(fichiers.get(1).asDTO());

        instance.ajouterChansons(creerListeFichier());
        instance.ajouterChansons(fichierAjout);
        assertEquals(fichiers.size(), instance.getListeMusique().size());
    }

    @Ignore
    public void testEnleverChanson()throws IOException{
        instance.ajouterChansons(creerListeFichier());
        instance.enleverChanson(1);
        assertEquals(fichiers.size() - 1, instance.getListeMusique().size());
    }

    private ArrayList<Chanson> creerListeChansons() throws URISyntaxException, IOException {
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
        ArrayList<Chanson> chansons = new ArrayList<Chanson>();
        for(File f : fichiers){
            chansons.add(new Chanson(f.getAbsolutePath()));
        }
        return chansons;
    }

    private ArrayList<ChansonDTO> creerListeFichier(){
        ArrayList<ChansonDTO> files = new ArrayList<ChansonDTO>();
        for(Chanson c : fichiers){
            files.add(c.asDTO());
        }
        return files;
    }


    public void testMonterChansonDansListe() throws URISyntaxException, IOException {
        int index = 1;
        ArrayList<Chanson> expected =  creerListeChansons();
        instance.setListeMusique(fichiers);
        instance.monterChansonDansListe(index);
        assertEquals(expected.get(index-1).getPath(),fichiers.get(index).getPath());
    }

    public void testDescendreChansonDansListe() throws URISyntaxException, IOException {
        int index = 1;
        ArrayList<Chanson> expected =  creerListeChansons();
        instance.setListeMusique(fichiers);
        instance.descendreChansonDansListe(index);
        if (index < instance.getListeMusique().size()){
            assertEquals(expected.get(index+1).getPath(),fichiers.get(index).getPath());
        } else {
            assertEquals(expected.get(index).getPath(),fichiers.get(index).getPath());
        }

    }

    public void testRecommencertChanson() throws MalformedURLException {
        instance.setListeMusique(fichiers);
        instance.setMedia(new File(instance.getChanson().getPath()));
        instance.jouerChanson();
        dormirThread(300);
        instance.arreterChanson();
        dormirThread(300);
        instance.jouerChanson();
        Duration tempsResultat = instance.getTempsCourant();
        Duration tempsExpected = Duration.ZERO;
        assertEquals(tempsExpected, tempsResultat);
    }

    @Test
    public void testModifierPositionChanson() throws IOException, URISyntaxException {
        double pourcentageTempsEcouleAttendu = 0.26666666666666666;
        double position = 0.25;

        setChansonAJouer();

        instance.jouerChanson();
        dormirThread(2000);

        instance.modifierPositionChanson(position);
        dormirThread(2000);

        assertEquals(pourcentageTempsEcouleAttendu, instance.calculerPourcentageTempsEcoule());
    }

    @Test
    public void testAvancerRapideChanson() throws IOException, URISyntaxException {
        Duration tempsAttendu;
        setChansonAJouer();
        instance.jouerChanson();
        dormirThread(2000);
        instance.mettreSurPauseChanson();
        tempsAttendu = instance.getTempsCourant();
        instance.avancerRapideChanson();
        dormirThread(2000);
       int expected = (int)tempsAttendu.add(Duration.millis(TEMPS_AVANCE_REMBOBINAGE)).toMillis();
       int result = (int)instance.getTempsCourant().toMillis();
        assertEquals(expected, result);
    }

    @Test
    public void testRembobinerChanson() throws IOException, URISyntaxException {
        Duration tempsAttendu;
        setChansonAJouer();
        instance.jouerChanson();
        dormirThread(2000);
        instance.mettreSurPauseChanson();
        tempsAttendu = instance.getTempsCourant();
        instance.rembobinerChanson();
        dormirThread(2000);
        int expected = (int)tempsAttendu.subtract(Duration.millis(TEMPS_AVANCE_REMBOBINAGE)).toMillis();
        int result = (int)instance.getTempsCourant().toMillis();
        assertEquals(expected, result);
    }

    private void dormirThread(int nb){
        try {
            Thread.sleep(nb);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAssourdirVolume() throws IOException, URISyntaxException {
        setChansonAJouer();
        double volumeMuet = 1.0;
        instance.setMuetOn();
        assertEquals(volumeMuet, instance.getMediaPlayer().getVolume());
    }

    @Test
    public void testRetablirVolume() throws IOException, URISyntaxException {
        setChansonAJouer();
        double volumeActuel = instance.getMediaPlayer().getVolume();
        instance.setMuetOn();
        instance.setMuetOff();
        assertEquals(volumeActuel, instance.getMediaPlayer().getVolume());
    }
    @Test
    public void testDeplacerChansonDansListeHaut() throws URISyntaxException, IOException {
        int indexDebut = 2;
        int indexFin = 4;
        int direction  =  1;
        ArrayList<Chanson> expected =  creerListeChansons();
        instance.setListeMusique(fichiers);
        instance.setMedia(new File(instance.getChanson().getPath()));
        instance.deplacerChansonDansListe(indexDebut, indexFin, direction);
        assertEquals(expected.get(indexFin).getPath(), instance.getListeMusique().get(indexDebut).getPath());
    }
    @Test
    public void testDeplacerChansonDansListeBas() throws URISyntaxException, IOException {
        int indexDebut = 0;
        int indexFin = 4;
        int direction  =  -1;
        ArrayList<Chanson> expected =  creerListeChansons();
        instance.setListeMusique(fichiers);
        instance.setMedia(new File(instance.getChanson().getPath()));
        instance.deplacerChansonDansListe(indexDebut, indexFin, direction);
        assertEquals(expected.get(indexDebut).getPath(), instance.getListeMusique().get(indexFin).getPath());
    }

    @Test
    public void testViderListe(){
        instance.setListeMusique(fichiers);
        instance.viderLecteur();
        assertSame(null, instance.getMediaPlayer());
    }


    @Test
    public void testGererBoutonPrecendentRecommencer() throws Exception {
        instance.setListeMusique(fichiers);
        instance.setMedia(new File(instance.getChanson().getPath()));
        dormirThread(5000);
        instance.mettreSurPauseChanson();
        instance.gererChansonPrecedente();
        assertEquals(Duration.ZERO, instance.getTempsCourant());
    }

    @Test
    public void testGererBoutonPrecendentReculerListe() throws Exception {
        instance.setListeMusique(fichiers);
        int indexAttendu = 2;
        instance.setMedia(new File(instance.getChanson().getPath()));
        instance.setChansonEnCours(indexAttendu);
        dormirThread(1000);
        instance.mettreSurPauseChanson();
        instance.gererChansonPrecedente();
        assertEquals(indexAttendu - 1, instance.getIndexMusique());
    }

    @Test
    public void testRepetageSiRepetageEstActif() throws MalformedURLException {
        int positionAttendue = 0;
        instance.setListeMusique(fichiers);
        instance.setChansonEnCours(0);
        instance.setRepeterListe();
        for (int i = 0; i < fichiers.size(); i++){
            instance.passerChansonSuivante();
        }
        assertEquals(positionAttendue, instance.getIndexMusique());
    }

    @Test
    public void testRepetageChansonSiRepetageEstActif() throws MalformedURLException {
        int positionAttendue = 0;
        instance.setListeMusique(fichiers);
        instance.setChansonEnCours(0);
        instance.setRepeterChanson();
        for (int i = 0; i < fichiers.size(); i++){
            instance.passerChansonSuivante();
        }
        assertEquals(positionAttendue, instance.getIndexMusique());
    }

    @Test
    public void testPasserChansonSiRepetageEstPasActif() throws MalformedURLException {
        int positionAttendue = 1;
        instance.setListeMusique(fichiers);
        instance.setChansonEnCours(0);
        instance.setRepeterChanson();
        for (int i = 0; i < fichiers.size(); i++){
            instance.passerChansonSuivante();
        }
        instance.setRepeterArret();
        instance.passerChansonSuivante();
        assertEquals(positionAttendue, instance.getIndexMusique());
    }

    @Test
    public void testPasserChansonAleatoirePasActif() throws MalformedURLException {
        instance.setListeMusique(fichiers);
        instance.setChansonEnCours(0);
        instance.setLectureAleatoireActif();
        int positionAttendue = instance.getAleatoire().getIndexChansons().get(0);
        for (int i = 0; i < fichiers.size(); i++){
            instance.passerChansonSuivante();
        }
        instance.setEcouteAleatoireInactif();
        instance.passerChansonSuivante();
        positionAttendue++;
        assertEquals(positionAttendue, instance.getIndexMusique());

    }

    @Test
    public void testRepetageSiRepetageActifEtAleatoireActif() throws MalformedURLException {
        instance.setListeMusique(fichiers);
        instance.setChansonEnCours(0);
        instance.setLectureAleatoireActif();
        instance.setRepeterListe();
        int positionAttendue = instance.getAleatoire().getIndexChansons().get(0);
        for (int i = 0; i < fichiers.size(); i++){
            instance.passerChansonSuivante();
        }
        assertEquals(positionAttendue, instance.getIndexMusique());
    }


    @Test
    public void testGererSuppressionChansons() throws IOException, URISyntaxException {
        instance.setListeMusique(fichiers);
        ArrayList<Chanson> expected = creerListeChansons();
        int tailleListe  = instance.getListeMusique().size();
        int indexChansonEnlevee= 1;
        instance.setChansonEnCours(indexChansonEnlevee);
        instance.gererSuppressionChanson(indexChansonEnlevee);
        assertEquals(tailleListe - 1, instance.getListeMusique().size());
        assertEquals(expected.get(2).getPath(), instance.getListeMusique().get(indexChansonEnlevee).getPath());
    }

    @Test
    public void testSetVolume() throws MalformedURLException {
        instance.setListeMusique(fichiers);
        instance.setChansonEnCours(0);
        instance.setVolume(0.5);
        assertEquals(0.5,instance.getMediaPlayer().getVolume());
    }

    @Test
    public void testTrouverChansonQuiEstDansListeDeLecture() throws IOException {
        int resultat;
        int expected = 0;
        ArrayList<ChansonDTO> chansons = new ArrayList<ChansonDTO>();
        chansons.add(fichiers.get(0).asDTO());
        instance.ajouterChansons(chansons);
        resultat = instance.trouverIndexChanson(chansons.get(0));
        assertEquals(expected, resultat);
    }

    @Test
    public void testPasTrouverChansonQuiEstPasDansListeDeLecture() throws IOException {
        int resultat;
        int expected = -1;
        ArrayList<ChansonDTO> chansons = new ArrayList<ChansonDTO>();
        chansons.add(fichiers.get(0).asDTO());
        instance.ajouterChansons(chansons);
        resultat = instance.trouverIndexChanson(fichiers.get(1).asDTO());
        assertEquals(expected, resultat);
    }

    @Test
    public void testPasTrouverUneChansonApresQuonLenleve() throws IOException {
        int resultat;
        int expected = -1;
        ArrayList<ChansonDTO> chansons = new ArrayList<ChansonDTO>();
        chansons.add(fichiers.get(0).asDTO());
        instance.ajouterChansons(chansons);
        instance.enleverChanson(chansons.get(0));
        resultat = instance.trouverIndexChanson(fichiers.get(1).asDTO());
        assertEquals(expected, resultat);
    }

    @Test
    public void testEnleverChansonAvecUnDTO() throws IOException {
        int result;
        int expected = 1;
        ArrayList<ChansonDTO> chansons = new ArrayList<ChansonDTO>();
        chansons.add(fichiers.get(0).asDTO());
        chansons.add(fichiers.get(1).asDTO());
        instance.ajouterChansons(chansons);
        instance.enleverChanson(chansons.get(0));
        result = instance.getListeMusique().size();
        assertEquals(expected, result);
    }

    @Test
    public void testListeVideApresAvoirToutEnlever() throws IOException {
        boolean result;
        boolean expected = true;
        ArrayList<ChansonDTO> chansons = new ArrayList<ChansonDTO>();
        chansons.add(fichiers.get(0).asDTO());
        chansons.add(fichiers.get(1).asDTO());
        instance.ajouterChansons(chansons);
        result = instance.enleverChanson(chansons.get(0));
        result = instance.enleverChanson(chansons.get(1));
        assertEquals(expected, result);
    }

    /**
     * Test qui s'assure que le lecteur supprime les chansons d'un album de sa liste de lecture.
     * L'album envoyé en paramètre et l'album de la 2em chanson. La liste possède 4 chanson avec
     * cet album.
     * @throws IOException
     */
    @Test
    public void testEnleverChansonsAlbum() throws IOException {
        int result;
        int expected = 1;
        ArrayList<ChansonDTO> chansons = new ArrayList<ChansonDTO>();
        chansons = creerListeFichier();
        instance.ajouterChansons(chansons);
        instance.enleverChansonsAlbum(chansons.get(1).getAlbum());
        result = instance.getListeMusique().size();
        assertEquals(expected, result);
    }

    /**
     * Test qui s'assure que le lecteur ne fait aucune supression si on lui
     * envoi un album qui n'existe pas.
     * @throws IOException
     */
    @Test
    public void testEnleverAlbumQuiExistePas() throws IOException {
        int result;
        int expeced = 5;
        ArrayList<ChansonDTO> chansons = new ArrayList<ChansonDTO>();
        chansons = creerListeFichier();
        instance.ajouterChansons(chansons);
        instance.enleverChansonsAlbum("Album qui n'existe pas.");
        result = instance.getListeMusique().size();
        assertEquals(expeced, result);
    }

    /**
     * Test qui s'assure que le lecteur supprime les chansons d'un artiste de sa liste de lecture.
     * L'artiste envoyé en paramètre et l'album de la 2em chanson. La liste possède 4 chanson avec
     * cet artiste.
     * @throws IOException
     */
    @Test
    public void testEnleverChansonsArtiste() throws IOException {
        int result;
        int expected = 1;
        ArrayList<ChansonDTO> chansons = new ArrayList<ChansonDTO>();
        chansons = creerListeFichier();
        instance.ajouterChansons(chansons);
        instance.enleverChansonsArtiste(chansons.get(1).getArtiste());
        result = instance.getListeMusique().size();
        assertEquals(expected, result);
    }

    /**
     * Test qui s'assure que le lecteur ne fait aucune supression si on lui
     * envoi un artiste qui n'existe pas.
     * @throws IOException
     */
    @Test
    public void testEnleverArtisteQuiExistePas() throws IOException {
        int result;
        int expeced = 5;
        ArrayList<ChansonDTO> chansons = new ArrayList<ChansonDTO>();
        chansons = creerListeFichier();
        instance.ajouterChansons(chansons);
        instance.enleverChansonsArtiste("Richard Tartampion");
        result = instance.getListeMusique().size();
        assertEquals(expeced, result);
    }

    @Test
    public void testGetChansonNull(){
        Chanson expected = null;
        instance.setListeMusique(null);
        Chanson result = instance.getChanson();
        assertEquals(expected, result);
    }

    @Test
    public void testSetPisteChanson()throws IOException{
        instance.setListeMusique(fichiers);
        instance.setChansonEnCours(0);
        String pisteExpected = "1";
        instance.setPisteChanson(pisteExpected);
        String result = instance.getChanson().getPiste();
        assertEquals(pisteExpected, result);

    }

    @Test
    public void testSetTitreChanson()throws IOException{
        instance.setListeMusique(fichiers);
        instance.setChansonEnCours(0);
        String expected = "titre";
        instance.setTitreChanson(expected);
        String result = instance.getChanson().getTitre();
        assertEquals(expected, result);

    }

    @Test
    public void testSetArtisteChanson()throws IOException{
        instance.setListeMusique(fichiers);
        instance.setChansonEnCours(0);
        String expected = "artiste";
        instance.setArtisteChanson(expected);
        String result = instance.getChanson().getArtiste();
        assertEquals(expected, result);

    }

    @Test
    public void testSetAlbumChanson()throws IOException{
        instance.setListeMusique(fichiers);
        instance.setChansonEnCours(0);
        String expected = "album";
        instance.setAlbumChanson(expected);
        String result = instance.getChanson().getAlbum();
        assertEquals(expected, result);

    }

    @Test
    public void testSetAnneeChanson()throws IOException{
        instance.setListeMusique(fichiers);
        instance.setChansonEnCours(0);
        String expected = "1111";
        instance.setAnneeChanson(expected);
        String result = instance.getChanson().getAnnee();
        assertEquals(expected, result);

    }

    @Test
    public void testSetGenreChanson()throws IOException{
        instance.setListeMusique(fichiers);
        instance.setChansonEnCours(0);
        int expected = 1;
        instance.setGenreChanson(expected);
        int result = instance.getChanson().getGenre();
        assertEquals(expected, result);

    }

    @Test
    public void testSetPathErroneImage()throws IOException{
        instance.setListeMusique(fichiers);
        instance.setChansonEnCours(0);
        String exp = "/path.png"; // path cause erreur
        instance.setPathImage(exp);
        Image result = instance.getChanson().getAlbumArt();
        Image expected = IMAGE_ALBUM_DEFAUT;
        assertEquals(expected, result);
    }

    @Test
    public void testgetListeDTO()throws IOException{
        instance.setListeMusique(fichiers);
        ArrayList<ChansonDTO> expected = new ArrayList<ChansonDTO>();
        for (Chanson c : fichiers) {
            expected.add(c.asDTO());
        }
        ArrayList<ChansonDTO> result = instance.getListeMusiqueDTO();
        assertEquals(expected, result);
    }

    @Test
    public void testSetListeVide()throws IOException{
        instance.setListeMusique(fichiers);
        instance.setChansonEnCours(0);
        instance.arreterChanson();

        instance.viderListeDeLecture();
        EtatLecture etat = instance.getEtatLecture();
        assertEquals(EtatLecture.ARRET, etat);
        assertEquals(0, instance.getListeMusique().size());
    }

    @Test
    public void testPasserETRevenirChansonPrecedentModeAleatoire()throws IOException{
        instance.setListeMusique(fichiers);
        instance.setChansonEnCours(0);
        instance.setLectureAleatoireActif();
        instance.passerChansonSuivante();
        int expected = instance.getIndexMusique();
        instance.passerChansonSuivante();
        instance.passerChansonPrecedente();
        int result = instance.getIndexMusique();
        assertEquals(expected, result);

    }

    @Test
    public void testRecommencerChanson()throws IOException{
        instance.setListeMusique(fichiers);
        int indexExpected = 0;
        instance.setChansonEnCours(indexExpected);
        instance.arreterChanson();
        instance.recommencerChanson();

        EtatLecture resultat = instance.getEtatLecture();
        assertEquals(EtatLecture.LECTURE, resultat);
        assertEquals(indexExpected, instance.getIndexMusique());
    }
}