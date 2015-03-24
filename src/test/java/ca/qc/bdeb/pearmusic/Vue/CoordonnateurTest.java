package ca.qc.bdeb.pearmusic.Vue;

import ca.qc.bdeb.pearmusic.Application.ChansonDTO;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class CoordonnateurTest extends TestCase {


    public Button btnRepeter;

    public ComboBox<String> cmbGenre;

    public Label lblPiste;

    public Label lblChanson;

    public Label lblArtiste;

    public Label lblAlbum;

    public Label lblAnnee;

    public Label lblGenre;

    public Label lblDuree;

    public Label lblDureeEcoule;

    public ListView<String> lstChansonsEnCours;

    public ListView lstAlbumTab;

    public ListView lstArtisteTab;

    public ListView lstPlaylists;

    public ImageView imgAleatoire;

    public ImageView imgVolume;

    public ImageView imgRepeter;

    public ImageView imgPlay;

    public ImageView coverAlbum;

    public MenuBar mnuMenuBar;

    public MenuButton btnVolume;

    public MenuItem mnuItemVolume;

    public Slider sliderTemps;

    public Slider sliderVolume;

    public Tab ctnOngletChanson;

    public TabPane ctnOnglets;

    public TextField txtPiste;

    public TextField txtChanson;

    public TableView<ChansonDTO> tabChansons;

    public ToggleButton btnAssourdir;

    public ToggleButton btnAleatoire;

    public TextField txtRecherche;

    public TextField txtArtiste;

    public TextField txtAlbum;

    public TextField txtAnnee;

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    private Coordonnateur coordonnateur;

    @Before
    public void setUp() throws Exception {

        JFXPanel panel = new JFXPanel();
        coordonnateur = new Coordonnateur();

    }

    @Test
    public void testJouerChanson(){
        //tabChansons.getSelectionModel().selectFirst();
        //coordonnateur.lireChanson();
        //assertEquals(1, lstChansonsEnCours.getItems().size());
    }
}