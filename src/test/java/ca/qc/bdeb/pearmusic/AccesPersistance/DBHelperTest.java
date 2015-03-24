package ca.qc.bdeb.pearmusic.AccesPersistance;

import ca.qc.bdeb.pearmusic.Application.ChansonDTO;
import ca.qc.bdeb.pearmusic.Application.ListeDeLectureDTO;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.tmatesoft.sqljet.core.SqlJetException;

import java.io.IOException;
import java.util.ArrayList;


public class DBHelperTest extends TestCase {
    DBHelper instance;
    // ChansonDTO 1 de test
    final ChansonDTO CHANSON1 = new ChansonDTO(
            "/Pear_music/musique1.mp3",  // Path
            "1",                         // Piste
            "BonJovi",                   // Artiste
            "Keep the faith",            // Titre
            "Best of",                   // Album
            "1993",                      // Annee
            17,                          // Genre (rock)
            234,                         // Duree
            null                         // Path de l'image
    );
    // ChansonDTO 2 de test
    final ChansonDTO CHANSON2 = new ChansonDTO(
            "/Pear_music/musique2.mp3",  // Path
            "3",                         // Piste
            "Pink Floyd",                // Artiste
            "Money",                     // Titre
            "The Dark Side Of The Moon", // Album
            "1976",                      // Annee
            17,                          // Genre (rock)
            256,                         // Duree
            null                         // Path de l'image
    );

    @Before
    public void setUp() throws IOException, SqlJetException {
        instance = DBHelper.getInstance();
        instance.truncateTableMusique();
    }

    @After
    public void tearDown() throws SqlJetException {
        instance.truncateTableMusique();
    }

    /**
     * Ce test permet de voir si l'ajout à la bibliothèque fonctionne
     * ainsi que la fonction compter les éléments de la table musique
     */
    @Test
    public void testAjouterEtCompterNbElementTableMusique() {
        int nombreDeLigneEspere = 2;
        int nombreDeLigneFin;

        try {
            instance.ajouterChanson(CHANSON1);
            instance.ajouterChanson(CHANSON2);
        } catch (SqlJetException e) {
            e.printStackTrace();
        }
        nombreDeLigneFin = instance.getTailleTabMusique();

        assertEquals(nombreDeLigneEspere, nombreDeLigneFin);
    }

    /**
     * Ce test s'assure que la méthode deleteChanson retire un fichier de la bibliothèque
     *
     * @throws Exception
     */
    @Test
    public void testDeleteChanson() throws SqlJetException {
        int nombreDeLigneEsperee = 1;
        int nombreDeLigneDebut;
        int nombreDeLigneFin;

        instance.ajouterChanson(CHANSON1);
        instance.ajouterChanson(CHANSON2);

        nombreDeLigneDebut = instance.getTailleTabMusique();
        instance.deleteChanson(CHANSON1.getPath());
        nombreDeLigneFin = instance.getTailleTabMusique();

        assertEquals(nombreDeLigneEsperee, (nombreDeLigneDebut - nombreDeLigneFin));
    }

    /**
     * Test qui s'assure que la BD supprime bien un album en lui fournissant le nom de l'album.
     * Les deux chansons ajoutées ne sont pas du même album.
     *
     * @throws SqlJetException
     */
    @Test
    public void testDeleteAlbum() throws SqlJetException {
        int nombreDeLigneEsperee = 1;
        int nombreDeLigneDebut;
        int nombreDeLigneFin;

        instance.ajouterChanson(CHANSON1);
        instance.ajouterChanson(CHANSON2);

        nombreDeLigneDebut = instance.getTailleTabMusique();
        instance.deleteAlbum(CHANSON1.getAlbum());
        nombreDeLigneFin = instance.getTailleTabMusique();
        assertEquals(nombreDeLigneEsperee, (nombreDeLigneDebut - nombreDeLigneFin));
    }

    /**
     * Test qui s'assure que la BD ne fait aucune supression si on lui donne
     * un nom d'album qui n'existe pas.
     * @throws SqlJetException
     */
    @Test
    public void testDeleteAlbumQuiExistePas() throws SqlJetException {
        int nombreDeLigneEsperee = 2;
        int nombreDeLigneFin;

        instance.ajouterChanson(CHANSON1);
        instance.ajouterChanson(CHANSON2);

        instance.deleteAlbum("Album qui n'a jamais été fait");
        nombreDeLigneFin = instance.getTailleTabMusique();
        assertEquals(nombreDeLigneEsperee, nombreDeLigneFin);
    }

    /**
     * Test qui s'assure que la BD supprime bien un artiste en lui fournissant le nom de l'artiste.
     * Les deux chansons ajoutées ne sont pas du même artiste.
     *
     * @throws SqlJetException
     */
    @Test
    public void testDeleteArtiste() throws SqlJetException {
        int nombreDeLigneEsperee = 1;
        int nombreDeLigneDebut;
        int nombreDeLigneFin;

        instance.ajouterChanson(CHANSON1);
        instance.ajouterChanson(CHANSON2);

        nombreDeLigneDebut = instance.getTailleTabMusique();
        instance.deleteArtiste(CHANSON1.getArtiste());
        nombreDeLigneFin = instance.getTailleTabMusique();
        assertEquals(nombreDeLigneEsperee, (nombreDeLigneDebut - nombreDeLigneFin));
    }

    /**
     * Test qui s'assure que la BD ne fait aucune supression si on lui donne
     * un nom d'artiste qui n'existe pas.
     * @throws SqlJetException
     */
    @Test
    public void testDeleteArtisteQuiExistePas() throws SqlJetException {
        int nombreDeLigneEsperee = 2;
        int nombreDeLigneFin;

        instance.ajouterChanson(CHANSON1);
        instance.ajouterChanson(CHANSON2);

        instance.deleteArtiste("Richard Tartampion");
        nombreDeLigneFin = instance.getTailleTabMusique();
        assertEquals(nombreDeLigneEsperee, nombreDeLigneFin);
    }

    /**
     * Test
     */
    @Test
    public void testDerniereLigneEstDerniereLigneApresAjoutEtSupression() throws SqlJetException {
        ListeDeLectureDTO dernierePlaylistExpected;
        String[] nomEtCheminPlaylist1 = new String[]{"PlaylistDeTest1", "/Pear_music/PlaylistDeTest1.pear"};
        String[] nomEtCheminPlaylist2 = new String[]{"PlaylistDeTest2", "/Pear_music/PlaylistDeTest2.pear"};
        instance.insererListeDeLecture(nomEtCheminPlaylist1[0], nomEtCheminPlaylist1[1]);
        instance.insererListeDeLecture(nomEtCheminPlaylist2[0], nomEtCheminPlaylist2[1]);
        instance.deleteListeDeLecture(nomEtCheminPlaylist2[1]);
        dernierePlaylistExpected = instance.getListeDeLecture().get(instance.getListeDeLecture().size() - 1);
        assertEquals(dernierePlaylistExpected.getNomListe(), nomEtCheminPlaylist1[0]);
    }

    /**
     * Ce test s'assure que la méthode deleteChanson retire le fichier avec le path envoyé
     * Il test indirectement la méthode getChansonBibliotheque
     *
     * @throws Exception
     */
    @Test
    public void testDeleteChansonPath() throws Exception {
        ChansonDTO chansonRestante;

        try {
            // Ajoute deux éléments
            instance.ajouterChanson(CHANSON1);
            instance.ajouterChanson(CHANSON2);
        } catch (SqlJetException e) {
            e.printStackTrace();
        }

        instance.deleteChanson(CHANSON1.getPath());

        chansonRestante = instance.getChansonsBibliotheque().get(0);
        // Le fichier restant ne doit pas avoir le même path que la chanson effacé
        assertFalse(chansonRestante.getPath().equals(CHANSON1.getPath()));
    }


    /**
     * Ce test s'assure que la méthode deleteChanson n'efface pas de chanson si le path n'existe pas
     * dans la bibliothèque
     *
     * @throws Exception
     */
    @Test
    public void testDeleteChansonInexistante() throws Exception {
        int nombreDeLigneDebut;
        int nombreDeLigneFin;

        try {
            instance.ajouterChanson(CHANSON1);
            instance.ajouterChanson(CHANSON2);
        } catch (SqlJetException e) {
            e.printStackTrace();
        }

        nombreDeLigneDebut = instance.getTailleTabMusique();

        instance.deleteChanson("Path_inexistant");

        nombreDeLigneFin = instance.getTailleTabMusique();

        assertEquals(nombreDeLigneDebut, nombreDeLigneFin);
    }

    /**
     * Ce test permet de vérifier la mise a jour d'un champ String <-> TEXTE de l'application à la BD
     *
     * @throws SqlJetException
     */
    @Test
    public void testUpdateMusiqueAnnee() throws SqlJetException {
        ChansonDTO chansonModifiee;

        try {
            // Ajoute un élément à la bibliothèque
            instance.ajouterChanson(CHANSON1);
        } catch (SqlJetException e) {
            e.printStackTrace();
        }
        CHANSON1.setAnnee("1922");

        instance.updateMusique(CHANSON1);
        // On va chercher le seul élément présent dans la bibliothèque
        chansonModifiee = instance.getChansonsBibliotheque().get(0);

        assertEquals(CHANSON1.getAnnee(), chansonModifiee.getAnnee());
    }

    /**
     * Ce test permet de vérifier la mise a jour d'un champ int <-> Number de l'application à la BD
     * Aussi, elle permet de voir si l'inscription et la lecture d'un int se fait bien
     *
     * @throws SqlJetException
     */
    @Test
    public void testUpdateMusiqueGenre() throws SqlJetException {
        ChansonDTO chansonModifiee;

        try {
            // Ajoute un élément à la bibliothèque
            instance.ajouterChanson(CHANSON1);
        } catch (SqlJetException e) {
            e.printStackTrace();
        }
        // On modifie l'annee d'une chanson
        CHANSON1.setGenre(18);
        // On envoie le DTO. Avec le path, il va trouver l'entrée et mettre a jour les autres champs
        instance.updateMusique(CHANSON1);
        // On va chercher le seul élément présent dans la bibliothèque
        chansonModifiee = instance.getChansonsBibliotheque().get(0);
        // On vérifie que la mise a jour a été faite
        assertEquals(CHANSON1.getGenre(), chansonModifiee.getGenre());
    }

    /**
     * Ce test permet de vérifier la mise a jour d'un champ long <-> Number de l'application à la BD
     * Aussi, elle permet de voir si l'inscription et la lecture d'un long se fait bien
     *
     * @throws SqlJetException
     */
    @Test
    public void testUpdateMusiqueDuree() throws SqlJetException {
        ChansonDTO chansonModifiee;
        long nlleDuree = 230;

        try {
            instance.ajouterChanson(CHANSON1);
        } catch (SqlJetException e) {
            e.printStackTrace();
        }

        CHANSON1.setDuree(nlleDuree);
        instance.updateMusique(CHANSON1);
        chansonModifiee = instance.getChansonsBibliotheque().get(0);

        assertEquals(CHANSON1.getDuree(), chansonModifiee.getDuree());
    }


    @Test
    public void testInsererSurBD() throws Exception {
        int nbEntreeEspere = 2;
        ArrayList<ChansonDTO> liste = new ArrayList<ChansonDTO>();

        liste.add(CHANSON1);
        liste.add(CHANSON2);

        instance.insererSurBD(liste);

        assertEquals(nbEntreeEspere, instance.getTailleTabMusique());
    }

    @Ignore
    public void testGetElementsIndex() throws Exception {

    }

    @Ignore
    public void testGetElements() throws SqlJetException {


    }

}