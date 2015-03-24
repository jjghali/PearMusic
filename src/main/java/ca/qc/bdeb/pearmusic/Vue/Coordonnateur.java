package ca.qc.bdeb.pearmusic.Vue;

import ca.qc.bdeb.pearmusic.Application.AlbumDTO;
import ca.qc.bdeb.pearmusic.Application.ChansonDTO;
import ca.qc.bdeb.pearmusic.Application.Facade;
import ca.qc.bdeb.pearmusic.Application.ListeDeLectureDTO;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.tmatesoft.sqljet.core.SqlJetException;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe qui permet de modifier la vue et de traiter les actions faites par l'utilisateur
 *
 * @author Dominique Cright
 */
public class Coordonnateur extends Thread implements ConstantesVue {
    @FXML
    public Button btnRepeter;
    @FXML
    public ComboBox<String> cmbGenre;
    @FXML
    public Label lblPiste;
    @FXML
    public Label lblChanson;
    @FXML
    public Label lblArtiste;
    @FXML
    public Label lblAlbum;
    @FXML
    public Label lblAnnee;
    @FXML
    public Label lblGenre;
    @FXML
    public Label lblDuree;
    @FXML
    public Label lblDureeEcoule;
    @FXML
    public ListView<String> lstChansonsEnCours;
    @FXML
    public ListView lstAlbumTab;
    @FXML
    public ListView lstArtisteTab;
    @FXML
    public ListView lstPlaylists;
    @FXML
    public ImageView imgAleatoire;
    @FXML
    public ImageView imgLoupe;
    @FXML
    public ImageView imgVolume;
    @FXML
    public ImageView imgRepeter;
    @FXML
    public ImageView imgPlay;
    @FXML
    public ImageView coverAlbum;
    @FXML
    public MenuBar mnuMenuBar;
    @FXML
    public MenuButton btnVolume;
    @FXML
    public MenuItem mnuItemVolume;
    @FXML
    public Slider sliderTemps;
    @FXML
    public Slider sliderVolume;
    @FXML
    public Tab ctnOngletChanson;
    @FXML
    public TabPane ctnOnglets;
    @FXML
    public TextField txtPiste;
    @FXML
    public TextField txtChanson;
    @FXML
    public TextField txtArtiste;
    @FXML
    public TextField txtAlbum;
    @FXML
    public TextField txtAnnee;
    @FXML
    public TextField txtRecherche;
    @FXML
    public ToggleButton btnAleatoire;
    @FXML
    public ToggleButton btnAssourdir;
    @FXML
    private Pane pnlPanneauPrincipal;
    @FXML
    private ProgressBar progressBarTemps;
    @FXML
    private TableColumn<ChansonDTO, String> colChanson;
    @FXML
    private TableColumn<ChansonDTO, String> colArtiste;
    @FXML
    private TableColumn<ChansonDTO, String> colAlbum;
    @FXML
    private TableView<ChansonDTO> tabChansons;

    /**
     * Pourcentage de temps écoulé en lecture pour le positionnement du Slider
     */
    private double tempsEcoule;
    /**
     * Pourcentage de volume choisi par l'utilisateur à l'aide du Slider
     */
    private double volume = VOLUME_DEFAUT;
    /**
     * Permet de savoir si le bouton  suivant est enfoncé ou relâché
     */
    private boolean estSuivantEnfonce = false;
    /**
     * Permet de savoir si le bouton  précédent est enfoncé ou relâché
     */
    private boolean estPrecedentEnfonce = false;
    /**
     * Permet de savoir si on doit jouer la liste de LECTURE de façon aléatoire
     */
    private boolean aleatoire = false;
    /**
     * Permet de savoir si l'option assourdir (mute) est activée
     */
    private boolean mute = false;
    /**
     * Image pour le bouton répeter quand il est actif.
     */
    private javafx.scene.image.Image imageRepeterActif = IMAGE_REPETER_VERT;
    /**
     * Image pour le bouton de repetition d'une chanson
     */
    private javafx.scene.image.Image imageRepeterChanson = IMAGE_REPETER_CHANSON_VERT;
    /**
     * Image pour le bouton aléatoire quand il est actif.
     */
    private javafx.scene.image.Image imageAleatoireActif = IMAGE_ALEATOIRE_VERT;
    /**
     * Toutes les chansons.
     */
    private ArrayList<ChansonDTO> toutesChansons;
    /**
     * Menu d'options quand on fait un clique droit.
     */
    private ContextMenu menuOptions;
    /**
     * Le coordonnateur envoie ses instructions à l'application
     */
    private Facade facade;
    /**
     * Option de retirer une chanson, un album ou un artiste.
     */
    private MenuItem optionRetirer;
    /**
     * Option de lire une chanson, un album ou un artiste.
     */
    private MenuItem optionLire;
    /**
     * Option d'ajouter une chanson à une playlist.
     */
    private MenuItem optionAjouterPlaylist;
    /**
     * Option d'afficher la chason dans l'explorateur.
     */
    private MenuItem optionAfficherExplorateur;
    /**
     * Pour que le listView sache quoi afficher
     */
    private ObservableList<String> chansonsAffichees;
    /**
     * Liste des chansons filtré selon la recherche
     */
    private ObservableList<ChansonDTO> filteredData;
    /**
     * Pour que l'onglet Chanson puisse gerer les filtres
     */
    private ObservableList<ChansonDTO> masterData;
    /**
     * Pour que l'onglet Album sache quoi afficher
     */
    private ObservableList<ChansonDTO> artistesAffiches;
    /**
     * Pour que l'onglet Album sache quoi afficher
     */
    private ObservableList<AlbumDTO> albumsAffiches;
    /**
     * Pour que la liste de playlists sache quoi afficher
     */
    private ObservableList<ListeDeLectureDTO> listesChansonAffichees;
    /**
     * Fenêtre de sélection de fichier
     */
    private SelecteurFichier selecteurFichier;
    /**
     * Fenêtre de sauvegarde
     */
    private Stage stageSauvegardeListe;
    /**
     * Fenêtre d'ajout.
     */
    private Stage stageAjoutListeDeLecture;
    /**
     * Fenêtre du À Propos
     */
    private Stage stageAPropos;
    /**
     * Thread qui permet l'avance rapide
     */
    private Thread threadAvanceRapide;
    /**
     * Thread qui permet le retour rapide
     */
    private Thread threadRembobinage;

    public Coordonnateur() throws IOException, URISyntaxException, SqlJetException {
        toutesChansons = new ArrayList<ChansonDTO>();
        facade = new Facade();
        selecteurFichier = new SelecteurFichier();
        threadAvanceRapide = null;
        menuOptions = new ContextMenu();

        setLecteurMusiqueListener();
        miseAJourTempsEcoule();
        creerItemDuMenu();
        creerListeChansonsFiltres();
        configurerListViewAlbumTab();
        configurerListViewArtisteTab();
        configurerListViewListeLectureTab();
    }

    @FXML
    protected void setLblPiste(ChansonDTO chanson) {
        lblPiste.setText(chanson.getPiste());
    }

    @FXML
    protected void setLblChanson(ChansonDTO chanson) {
        lblChanson.setText(chanson.getTitre());
    }

    @FXML
    protected void setLblArtiste(ChansonDTO chanson) {
        lblArtiste.setText(chanson.getArtiste());
    }

    @FXML
    protected void setLblAlbum(ChansonDTO chanson) {
        lblAlbum.setText(chanson.getAlbum());
    }

    @FXML
    protected void setLblAnnee(ChansonDTO chanson) {
        lblAnnee.setText(chanson.getAnnee());
    }

    @FXML
    protected void setLblGenre(ChansonDTO chanson) {
        lblGenre.setText(chanson.getGenreString());
    }

    @FXML
    protected void setLblDuree(ChansonDTO chanson) {
        lblDuree.setText(chanson.getDureeString());
    }

    @FXML
    protected void setCoverAlbum(ChansonDTO chanson) {
        coverAlbum.setImage(chanson.getAlbumArt());
    }

    private Thread getThread() {
        return new Thread(this);
    }

    /**
     * Méthode qui ajoute un écouteur sur l'application
     */
    private void setLecteurMusiqueListener() {
        facade.ajouterLecteurMusiqueListener(new LecteurMusiqueListener() {
            @Override
            public void prochaineChansonLancee(LecteurMusiqueEvent evenement) {
                try {
                    facade.passerChansonSuivante();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                mettreAJourInfoMusique();
                txtAlbum.setVisible(false);
                selectionnerChansonEnCoursListView();
            }
        });
    }

    /**
     * Traitement lorsque l'utilisateur appuie sur le bouton Play/Pause
     *
     * @param actionEvent
     */
    @FXML
    protected void handleJouerAction(ActionEvent actionEvent) {
        jouerOuMettreSurPause();
    }

    /**
     * Traitement lorsque l'utilisateur appuie sur le bouton Stop
     *
     * @param actionEvent
     */
    @FXML
    protected void handleArreterAction(ActionEvent actionEvent) {
        arretChanson();
    }

    /**
     * Traitement lorsque l'utilisateur sélectionne l'option "Ajouter des fichiers à la bibliothèque" du menu
     *
     * @param actionEvent
     * @throws Exception
     */
    @FXML
    protected void handleOuvrirFichierAction(ActionEvent actionEvent) throws Exception {
        ArrayList<File> listeMusique = selecteurFichier.choisirFichier(new Stage());

        if (listeMusique != null) {
            facade.sauvegarderFichier(listeMusique);

            creerListeChansonsFiltres();
            configurerListViewArtisteTab();
            configurerListViewAlbumTab();
            initialize();
        }
    }

    /**
     * Traitement lorsque l'utilisateur sélectionne l'option "À propos..." du menu
     *
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    protected void handleAProposClique(ActionEvent actionEvent) throws IOException {
        if (stageAPropos == null) {
            stageAPropos = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(A_PROPOS_FXML));
            Parent root = loader.load();
            loader.getController();
            Scene scene = new Scene(root);
            stageAPropos.setScene(scene);
            stageAPropos.setTitle(FENETRE_A_PROPOS_TITRE);
            stageAPropos.setResizable(false);
            stageAPropos.getIcons().add(IMAGE_ICONE_APP);
        }
        stageAPropos.show();
    }

    /**
     * Traitement lorsque l'utilisateur ferme la fenêtre avec l'option du menu ou le bouton fermer [x]
     *
     * @param actionEvent
     * @throws Exception
     */
    @FXML
    protected void handleFermerAction(ActionEvent actionEvent) throws Exception {
        Platform.exit();
        if (stageAPropos != null && stageAPropos.isShowing()) {
            stageAPropos.close();
        }
        if (stageSauvegardeListe != null && stageSauvegardeListe.isShowing()) {
            stageSauvegardeListe.close();
        }
    }

    /**
     * Traitement fait lorsque l'utilisateur appuie sur le bouton [-] de la liste de lecture, qui permet d'enlever une
     * chanson de la liste
     *
     * @param mouseEvent
     * @throws IOException
     */
    @FXML
    protected void handleEnleverAction(MouseEvent mouseEvent) throws IOException {
        int index = lstChansonsEnCours.getSelectionModel().getSelectedIndex();
        if (index > -1) {
            boolean listeVide = facade.enleverChanson(index);
            gererRetraitChansonsListeDeLecture(listeVide);
        }
    }

    /**
     * Lorsqu'un click de souris a été fait sur la barre de progression, on envoie la valeur à la facade.
     *
     * @param mouseEvent
     * @throws IOException
     */
    @FXML
    protected void handleSliderClick(MouseEvent mouseEvent) throws IOException {
        double deplacement = sliderTemps.getValue();
        facade.modifierPositionChanson(deplacement);
        lblDureeEcoule.setText(facade.getTempsEcoule());
    }

    /**
     * Traitement lorsque l'utilisateur modifie le volume à l'aide du slider
     *
     * @param mouseEvent
     */
    @FXML
    protected void handleSliderVolumeClique(MouseEvent mouseEvent) {
        volume = sliderVolume.getValue();
        facade.setVolume(volume);
        if (mute) {
            gererAssourdissementVolume();
        } else {
            ajusterImageVolume();
        }
    }

    /**
     * Traitement fait lorsque l'utilisateur clic sur la flèche bas pour déplacer la chanson dans la liste de lecture
     *
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    protected void handleBasAction(ActionEvent actionEvent) throws IOException {
        if (lstChansonsEnCours != null && lstChansonsEnCours.getItems().size() > 0) {
            int index = lstChansonsEnCours.getSelectionModel().getSelectedIndex();
            boolean estDescendu = facade.descendreChansonDansListe(index);
            if (estDescendu) {
                mettreAJourInfoMusique();
                configurerListViewListeDEcoute(facade.getListeChansonsEnCours());
            }
        }
    }

    /**
     * Traitement fait lorsque l'utilisateur appuie sur la flèche haut pour déplacer une chanson dans la liste
     *
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    protected void handleHautAction(ActionEvent actionEvent) throws IOException {
        if (lstChansonsEnCours != null && lstChansonsEnCours.getItems().size() > 0) {
            int index = lstChansonsEnCours.getSelectionModel().getSelectedIndex();
            boolean estMonter = facade.monterChansonDansListe(index);
            if (estMonter) {
                mettreAJourInfoMusique();
                configurerListViewListeDEcoute(facade.getListeChansonsEnCours());
            }
        }
    }

    /**
     * Traitement fait lorsque l'utilisateur appuie sur l'option aléatoire
     *
     * @param mouseEvent
     * @throws IOException
     */
    @FXML
    protected void handleAleatoireAction(MouseEvent mouseEvent) throws IOException {
        gererModeAleatoire();
    }

    /**
     * Traitement fait lorsque l'utilisateur appuie sur le bouton rététer
     *
     * @param mouseEvent
     */
    @FXML
    protected void handleRepeterAction(MouseEvent mouseEvent) {
        repeterListeLecture();
    }

    /**
     * Traitmemnt fait lorsque l'utilisateur décider de sauvegarder sa liste de lecture en cours
     *
     * @param mouseEvent
     * @throws IOException
     */
    @FXML
    protected void handleSauvegarderAction(MouseEvent mouseEvent) throws IOException {
        sauvegarderListeDeLecture();
    }

    /**
     * Traitement fait lorsque l'utilisateur appuie sur le bouton volume pour assourdir le lecteur ou remettre à l'état
     * précédant.
     *
     * @param mouseEvent
     */
    @FXML
    protected void handleAssourdirAction(MouseEvent mouseEvent) {
        gererAssourdissementVolume();
    }

    /**
     * Traitement fait lorsque l'utilisateur clic sur une piste de la liste de lecture
     *
     * @param mouseEvent
     */
    @FXML
    protected void handlePisteClique(MouseEvent mouseEvent) {
        if (facade.hasUneListeChansons()) {
            reinitialiserTextFields(txtPiste.getId());
            txtPiste.addEventFilter(KeyEvent.KEY_TYPED, numeric_Validation(2));
            txtPiste.setText(facade.getChansonEnCours().getPiste());
        }
    }

    /**
     * Traitement fait lorsque l'utilisateur veut modifier le titre de la chanson en cours de lecture
     *
     * @param mouseEvent
     */
    @FXML
    protected void handleTitreClique(MouseEvent mouseEvent) {
        if (facade.hasUneListeChansons()) {
            reinitialiserTextFields(txtChanson.getId());
            txtChanson.setText(facade.getChansonEnCours().getTitre());
        }
    }

    /**
     * Traitement fait lorsque l'utilisateur veut modifier l'artiste de la chanson en cours de lecture
     *
     * @param mouseEvent
     */
    @FXML
    protected void handleArtisteClique(MouseEvent mouseEvent) {
        if (facade.hasUneListeChansons()) {
            reinitialiserTextFields(txtArtiste.getId());
            txtArtiste.setText(facade.getChansonEnCours().getArtiste());
        }
    }

    /**
     * Traitement fait lorsque l'utilisateur veut modifier l'année de la chanson en cours de lecture
     *
     * @param mouseEvent
     */
    @FXML
    protected void handleAnneeClique(MouseEvent mouseEvent) {
        if (facade.hasUneListeChansons()) {
            reinitialiserTextFields(txtAnnee.getId());
            txtAnnee.addEventFilter(KeyEvent.KEY_TYPED, numeric_Validation(4));
            txtAnnee.setText(facade.getChansonEnCours().getAnnee());
        }
    }

    /**
     * Traitement fait lorsque l'utilisateur veut modifier l'album de la chanson en cours de lecture
     *
     * @param mouseEvent
     */
    @FXML
    protected void handleAlbumClique(MouseEvent mouseEvent) {
        if (facade.hasUneListeChansons()) {
            reinitialiserTextFields(txtAlbum.getId());
            txtAlbum.setText(facade.getChansonEnCours().getAlbum());
        }
    }

    /**
     * Traitement fait lorsque l'utilisateur veut modifier le genre de la chanson en cours
     *
     * @param mouseEvent
     */
    @FXML
    protected void handleGenreClique(MouseEvent mouseEvent) {
        if (facade.hasUneListeChansons()) {
            reinitialiserTextFields(cmbGenre.getId());
            cmbGenre.getItems().addAll(facade.getGenres());
            cmbGenre.setValue(facade.getGenre(facade.getChansonEnCours().getGenre()));
            cmbGenre.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> selected, String vieilleValeur, String nouvelleValeur) {
                    facade.setGenreChansonEnCours(nouvelleValeur);
                    mettreAJourInfoMusique();
                    mettreAJourListView();
                    cmbGenre.setVisible(false);
                    try {
                        facade.mettreAJourTraitementInfoDuFichierEnCours();
                    } catch (InvalidDataException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (UnsupportedTagException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * Traitement fait lorsque l'utilisateur veut modifier la pochette de l'album de la chanson en cours de lecture
     *
     * @param actionEvent
     */
    @FXML
    protected void handleImageAlbumClique(MouseEvent actionEvent) throws Exception {
        if (facade.hasUneListeChansons()) {
            String pathImage = selecteurFichier.choisirImage(new Stage());

            if (pathImage != null) {
                facade.setPathImage(pathImage);
                mettreAJourInfoMusique();
                facade.mettreAJourTraitementInfoDuFichierEnCours();
            }
        }
    }

    /**
     * Traitement fait lorsque l'utilisateur maintient le bouton avance rapide enfoncé
     *
     * @param actionEvent
     */
    @FXML
    protected void handleAvanceRapideChansonEnfonce(MouseEvent actionEvent) {
        if (threadAvanceRapide == null) {
            threadAvanceRapide = getThread();
            estSuivantEnfonce = true;
            threadAvanceRapide.start();
        }
    }

    /**
     * Traitement fait lorsque l'utilisateur maintient le bouton retour rapide enfoncé
     *
     * @param actionEvent
     */
    @FXML
    protected void handleAvanceRapideChansonRelache(MouseEvent actionEvent) {
        if (threadAvanceRapide != null) {
            estSuivantEnfonce = false;
            threadAvanceRapide.interrupt();
            threadAvanceRapide = null;
        }
    }

    /**
     * Traitement lorsque l'utilisateur appuie sur le bouton prochaine chanson
     *
     * @param actionEvent
     * @throws MalformedURLException
     */
    @FXML
    protected void handleProchaineChansonAction(MouseEvent actionEvent) throws MalformedURLException {
        choisirChansonSuivante();
    }

    /**
     * Traitement lorsque l'utilisateur maintient le bouton rembobinage enfoncé
     *
     * @param actionEvent
     */
    @FXML
    protected void handleRembobinageChansonEnfonce(MouseEvent actionEvent) {
        if (threadRembobinage == null) {
            threadRembobinage = getThread();
            threadRembobinage.start();
            estPrecedentEnfonce = true;
        }
    }

    /**
     * Traitement lorsque le bouton rembobinage est relaché
     *
     * @param actionEvent
     */
    @FXML
    protected void handleRembobinageChansonRelache(MouseEvent actionEvent) {
        if (threadRembobinage != null) {
            estPrecedentEnfonce = false;
            threadRembobinage.interrupt();
            threadRembobinage = null;
        }
    }

    /**
     * Traitement lorsque l'utilisateur appuie sur le bouton chanson précédente
     *
     * @param actionEvent
     * @throws MalformedURLException
     */
    @FXML
    protected void handlePrecedenteChansonAction(MouseEvent actionEvent) throws MalformedURLException {
        choisirChansonPrecedente();
    }

    /**
     * Traitement lorsque l'utilisateur fait un clic dans la liste de chanson en cours
     *
     * @param event
     * @throws MalformedURLException
     */
    @FXML
    protected void handleCliqueListLectureEnCours(MouseEvent event) throws MalformedURLException {
        if (lstChansonsEnCours.getItems().size() > 0 &&
                lstChansonsEnCours.getSelectionModel().getSelectedIndex() != facade.getIndexMusique()) {
            facade.setChansonEnCours(lstChansonsEnCours.getSelectionModel().getSelectedIndex());
            if (btnAleatoire.isSelected()) {
                facade.setLectureAleatoireActif();
            }
            mettreAJourInfoMusique();
        }
    }

    /**
     * Traitement lorsque l'utilisateur fait un clic dans le panneau chanson
     *
     * @param event
     */
    @FXML
    protected void handleCliqueListChansonTab(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            lireChanson();
        } else if (event.getButton() == MouseButton.SECONDARY) {
            menuOptions.getItems().clear();
            menuOptions.getItems().addAll(optionLire, optionRetirer, optionAjouterPlaylist, optionAfficherExplorateur);
        }
    }

    /**
     * Traitement lorsque l'utilisateur fait un clic dans le panneau album
     *
     * @param event
     */
    @FXML
    protected void handleCliqueListAlbumTab(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            lireChanson();
        } else if (event.getButton() == MouseButton.SECONDARY) {
            menuOptions.getItems().clear();
            menuOptions.getItems().addAll(optionLire, optionRetirer);
        }
    }

    /**
     * Traitement lorsque l'utilisateur fait un clic dans le panneau artiste
     *
     * @param event
     */
    @FXML
    protected void handleCliqueListArtisteTab(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            lireChanson();
        } else if (event.getButton() == MouseButton.SECONDARY) {
            menuOptions.getItems().clear();
            menuOptions.getItems().addAll(optionLire, optionRetirer);
        }
    }

    /**
     * Traitement lorsque l'utilisateur fait un clic dans le panneau des listes de lecture
     *
     * @param event
     */
    @FXML
    protected void handleCliqueListePlaylist(MouseEvent event) throws IOException {
        if (event.getButton() == MouseButton.PRIMARY) {
            int index = lstPlaylists.getSelectionModel().getSelectedIndex();
            if (index != -1) {
                ArrayList<ChansonDTO> chansons = new ArrayList<ChansonDTO>();
                if (facade.getListeChansonsEnCours().size() > 0) {
                    viderListViewEcouteEnCours();
                    facade.viderListeDeLectureEnCours();
                }
                chansons = facade.chargerPlaylist(listesChansonAffichees.get(index).getNomListe());

                facade.ajouterListeDeChansons(chansons);

                facade.setChansonEnCours(INDICE_PREMIERE_CHANSON);

                configurerListViewListeDEcoute(facade.getListeChansonsEnCours());
                mettreAJourInfoMusique();
            }
        } else if (event.getButton() == MouseButton.SECONDARY) {
            menuOptions.getItems().clear();
            menuOptions.getItems().addAll(optionLire, optionRetirer);
        }
    }

    /**
     * Traitement lorsque l'utilisateur écrit un caractère dans la barre de recherche
     *
     * @param keyEvent
     */
    @FXML
    protected void handleRechercheAction(KeyEvent keyEvent) {
        String recherche = txtRecherche.getText();
        changerOnglet(ctnOngletChanson);
        filtrerResultats(recherche.toLowerCase());
    }

    /**
     * Traiment lorsque l'utilisateur appuie sur le bouton arrêt
     *
     * @param actionEvent
     */
    @FXML
    public void handleActionStop(ActionEvent actionEvent) {
        arretChanson();
    }

    /**
     * Traitement lorsque l'utilisateur appuie sur le bouton lecture/pause
     *
     * @param actionEvent
     */
    @FXML
    public void handleActionPlayPause(ActionEvent actionEvent) {
        jouerOuMettreSurPause();
    }

    /**
     * Traitement lorsque l'utilisateur appuie sur le bouton mélanger (shuffle)
     *
     * @param actionEvent
     */
    @FXML
    public void handleActionShuffle(ActionEvent actionEvent) {
        gererModeAleatoire();
    }

    /**
     * Traitement lorsque l'utilisateur appuie sur le bouton répétition
     *
     * @param actionEvent
     */
    @FXML
    public void handleActionRepeat(ActionEvent actionEvent) {
        repeterListeLecture();
    }

    /**
     * Traitement lorsque l'utilisateur appuie sur le bouton assourdir
     *
     * @param actionEvent
     */
    @FXML
    public void handleActionMute(ActionEvent actionEvent) {
        gererAssourdissementVolume();
    }

    /**
     * Traitement lorsque l'utilisateur appuie sur le bouton chanson précédente
     *
     * @param actionEvent
     * @throws MalformedURLException
     */
    @FXML
    public void handleActionPrevious(ActionEvent actionEvent) throws MalformedURLException {
        choisirChansonPrecedente();
    }

    /**
     * Traitement lorsque l'utilisateur appuie sur le bouton chanson suivante
     *
     * @param actionEvent
     * @throws MalformedURLException
     */
    @FXML
    public void handleActionNext(ActionEvent actionEvent) throws MalformedURLException {
        choisirChansonSuivante();
    }

    /**
     * Traitement qui permet de valider les entrées de l'utilisateur lorsqu'il veut modifier le numéro de piste ou
     * l'année de parution d'un album.
     *
     * @param max_Lengh
     * @return
     */
    public EventHandler<KeyEvent> numeric_Validation(final Integer max_Lengh) {
        return new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                TextField txt_TextField = (TextField) e.getSource();
                if (txt_TextField.getText().length() >= max_Lengh) {
                    e.consume();
                }
                if (e.getCharacter().matches("[0-9.]")) {
                    if (txt_TextField.getText().contains(".") && e.getCharacter().matches("[.]")) {
                        e.consume();
                    } else if (txt_TextField.getText().length() == 0 && e.getCharacter().matches("[.]")) {
                        e.consume();
                    }
                } else {
                    e.consume();
                }
            }
        };
    }

    /**
     * Option du menu qui perment d'appliquer un css avec les options de couleurs bleus
     */
    @FXML
    protected void handleChangeSkinBleu() {
        pnlPanneauPrincipal.getStylesheets().remove(pnlPanneauPrincipal.getStylesheets().size() - 1);
        pnlPanneauPrincipal.getStylesheets().add(THEME_BLEU);
        imgLoupe.setImage(IMAGE_RECHERCHE_BLEU);
        imageAleatoireActif = IMAGE_ALEATOIRE_BLEU;
        imageRepeterActif = IMAGE_REPETER_BLEU;
        imageRepeterChanson = IMAGE_REPETER_CHANSON_BLEU;
        majBoutonListeLecture();
        majBoutonModeAleatoire();
    }

    /**
     * Option du menu qui perment d'appliquer un css avec les option de couleurs rouges
     */
    @FXML
    protected void handleChangeSkinRouge() {
        pnlPanneauPrincipal.getStylesheets().remove(pnlPanneauPrincipal.getStylesheets().size() - 1);
        pnlPanneauPrincipal.getStylesheets().add(THEME_ROUGE);
        imgLoupe.setImage(IMAGE_RECHERCHE_ROUGE);
        imageAleatoireActif = IMAGE_ALEATOIRE_ROUGE;
        imageRepeterActif = IMAGE_REPETER_ROUGE;
        imageRepeterChanson = IMAGE_REPETER_CHANSON_ROUGE;
        majBoutonListeLecture();
        majBoutonModeAleatoire();
    }

    /**
     * Option du menu qui perment d'appliquer un css avec les option de couleurs vertes, note : c'est le skin par défaut
     */
    @FXML
    protected void handleChangeSkinVert() {
        pnlPanneauPrincipal.getStylesheets().remove(pnlPanneauPrincipal.getStylesheets().size() - 1);
        pnlPanneauPrincipal.getStylesheets().add(THEME_VERT);
        imgLoupe.setImage(IMAGE_RECHERCHE_VERT);
        imageAleatoireActif = IMAGE_ALEATOIRE_VERT;
        imageRepeterActif = IMAGE_REPETER_VERT;
        imageRepeterChanson = IMAGE_REPETER_CHANSON_VERT;
        majBoutonListeLecture();
        majBoutonModeAleatoire();
    }

    /**
     * Traitement lorsque l'utilisateur valide la nouvelle donnée de piste qu'il a entrée
     *
     * @throws InvalidDataException
     * @throws IOException
     * @throws UnsupportedTagException
     */
    @FXML
    public void onEnterPiste() throws InvalidDataException, IOException, UnsupportedTagException {
        facade.setPisteChansonEnCours(txtPiste.getText());
        mettreAJourInfoMusique();
        mettreAJourListView();
        txtPiste.setVisible(false);
        facade.mettreAJourTraitementInfoDuFichierEnCours();
    }

    /**
     * Traitement lorsque l'utilisateur valide la nouvelle donnée de nom de chanson qu'il a entrée
     *
     * @throws InvalidDataException
     * @throws IOException
     * @throws UnsupportedTagException
     */
    @FXML
    public void onEnterChanson() throws InvalidDataException, IOException, UnsupportedTagException {
        facade.setTitreChansonEnCours(txtChanson.getText());
        mettreAJourInfoMusique();
        mettreAJourListView();
        txtChanson.setVisible(false);
        facade.mettreAJourTraitementInfoDuFichierEnCours();
    }

    /**
     * Traitement lorsque l'utilisateur valide la nouvelle donnée d'artiste qu'il a entré
     *
     * @throws InvalidDataException
     * @throws IOException
     * @throws UnsupportedTagException
     */
    @FXML
    public void onEnterArtiste() throws InvalidDataException, IOException, UnsupportedTagException {
        facade.setArtisteChansonEnCours(txtArtiste.getText());
        mettreAJourInfoMusique();
        mettreAJourListView();
        txtArtiste.setVisible(false);
        facade.mettreAJourTraitementInfoDuFichierEnCours();
    }

    /**
     * Traitement lorsque l'utilisateur valide la nouvelle donnée d'album qu'il a entré
     *
     * @throws InvalidDataException
     * @throws IOException
     * @throws UnsupportedTagException
     */
    @FXML
    public void onEnterAlbum() throws InvalidDataException, IOException, UnsupportedTagException {
        facade.setAlbumChansonEnCours(txtAlbum.getText());
        mettreAJourInfoMusique();
        mettreAJourListView();
        txtAlbum.setVisible(false);
        facade.mettreAJourTraitementInfoDuFichierEnCours();
    }

    /**
     * Traitement lorsque l'utilisateur valide la nouvelle donnée d'année qu'il a entrée
     *
     * @throws InvalidDataException
     * @throws IOException
     * @throws UnsupportedTagException
     */
    @FXML
    public void onEnterAnnee() throws InvalidDataException, IOException, UnsupportedTagException {
        facade.getAnneeChansonEnCours(txtAnnee.getText());
        mettreAJourInfoMusique();
        mettreAJourListView();
        txtAnnee.setVisible(false);
        facade.mettreAJourTraitementInfoDuFichierEnCours();
    }

    /**
     * Méthode qui permet à l'utilisateur d'utiliser des racourcis clavier pour contrôller le
     * lecteur de musique
     * : SPACE = play / PAUSE
     * P = précédent
     * S = suivant
     * A = arrêt
     * M = mute / unmute
     * V = aléatoire
     * R = rétéper
     * PLAY = play/PAUSE
     *
     * @param ke : touche pesée
     * @throws MalformedURLException
     */
    @FXML
    protected void onKeyPressed(KeyEvent ke) throws MalformedURLException {
        switch (ke.getCode()) {
            case SPACE:
                jouerOuMettreSurPause();
                break;
            case P:
                choisirChansonPrecedente();
                break;
            case S:
                choisirChansonSuivante();
                break;
            case A:
                arretChanson();
                break;
            case M:
                gererAssourdissementVolume();
                break;
            case V:
                gererModeAleatoire();
                break;
            case R:
                repeterListeLecture();
                break;
            case PLAY:
                jouerOuMettreSurPause();
                break;
        }
    }

    /**
     * Methode permettant de changer le focus sur un onglet du tabPane
     *
     * @param onglet
     */
    private void changerOnglet(Tab onglet) {
        SingleSelectionModel<Tab> selection = ctnOnglets.getSelectionModel();
        selection.select(onglet);
    }

    /**
     * Méthode qui ajoute les item au menu du clic droit
     */
    private void creerItemDuMenu() {
        optionRetirer = new MenuItem("Retirer");
        optionLire = new MenuItem("Lire");
        optionAjouterPlaylist = new MenuItem("Ajouter à une liste de lecture");
        optionAfficherExplorateur = new MenuItem("Afficher dans l'explorateur");
        ajouterEcouteurSurMenuItem();
    }

    /**
     * Méthode qui écoute les actions faites sur le menu du clic droit
     */
    private void ajouterEcouteurSurMenuItem() {
        optionRetirer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                retirerSelection();
            }
        });

        optionLire.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lireSelection();
            }
        });

        optionAjouterPlaylist.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ajouterListeDeLecture();
            }
        });

        optionAfficherExplorateur.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                afficherDansExplorateur();
            }
        });
    }

    /**
     * Méthode qui enlève les textField qui permettent d'éditer les chansons de sa bibliothèque
     *
     * @param id le numéro du textfield à rendre invisible
     */
    protected void reinitialiserTextFields(String id) {
        txtPiste.setVisible(false);
        txtPiste.setText("");
        txtChanson.setVisible(false);
        txtChanson.setText("");
        txtArtiste.setVisible(false);
        txtArtiste.setText("");
        txtAlbum.setVisible(false);
        txtAlbum.setText("");
        txtAnnee.setVisible(false);
        txtAnnee.setText("");
        cmbGenre.setVisible(false);
        if (id.equals(txtPiste.getId())) {
            txtPiste.setVisible(true);
        } else if (id.equals(txtChanson.getId())) {
            txtChanson.setVisible(true);
        } else if (id.equals(txtArtiste.getId())) {
            txtArtiste.setVisible(true);
        } else if (id.equals(txtAlbum.getId())) {
            txtAlbum.setVisible(true);
        } else if (id.equals(txtAnnee.getId())) {
            txtAnnee.setVisible(true);
        } else {
            cmbGenre.setVisible(true);
        }
    }

    /**
     * Méthode qui ajoute une chanson de la liste d'un album dans la liste d'écoute
     */
    protected void cliquerChansonAlbum(ArrayList<ChansonDTO> chanson) throws IOException {
        if (facade.verifierCheminExistant(chanson.get(0))) {
            facade.ajouterListeDeChansons(chanson);
        } else {
            configurerListViewAlbumTab();
        }
        configurerListViewListeDEcoute(facade.getListeChansonsEnCours());
        mettreAJourInfoMusique();
    }

    /**
     * Méthode qui ajoute une chanson à la liste de lecture en cours.
     */
    protected void lireChanson() {
        int index = tabChansons.getSelectionModel().getSelectedIndex();
        if (index != -1) {
            ArrayList<ChansonDTO> chanson = new ArrayList<ChansonDTO>();
            chanson.add(masterData.get(index));
            if (facade.verifierCheminExistant(chanson.get(0))) {
                facade.ajouterListeDeChansons(chanson);
            }
            configurerListViewListeDEcoute(facade.getListeChansonsEnCours());
            mettreAJourInfoMusique();
        }
    }

    /**
     * Méthode qui ajoute un album à la liste de lecture en cours.
     */
    protected void lireAlbum(String album) {
        facade.ajouterListeDeChansons(facade.obtenirChansonsAlbum(album));
        configurerListViewListeDEcoute(facade.getListeChansonsEnCours());
        mettreAJourInfoMusique();
    }

    /**
     * Méthode qui ajoute un artiste à la liste de lecture en cours.
     */
    protected void lireArtiste(String artiste) {
        facade.ajouterListeDeChansons(facade.obtenirChansonsArtiste(artiste));
        configurerListViewListeDEcoute(facade.getListeChansonsEnCours());
        mettreAJourInfoMusique();
    }

    /**
     * Méthode qui applique un filtre de recherche selon les caractères entrée par l'utilisateur
     * dans la barre de recherche
     *
     * @param recherche : le terme recherché par l'utilisateur
     */
    private void filtrerResultats(String recherche) {
        filteredData.clear();
        for (ChansonDTO chansonDTO : toutesChansons) {
            if (chansonDTO.getTitre().toLowerCase().indexOf(recherche) >= 0
                    || chansonDTO.getArtiste().toLowerCase().indexOf(recherche) >= 0
                    || chansonDTO.getAlbum().toLowerCase().indexOf(recherche) >= 0) {
                filteredData.add(chansonDTO);
            }
        }
    }

    /**
     * Méthode qui met à jour les étiquettes concernant les
     * informations de la chanson en cours
     */
    protected void mettreAJourInfoMusique() {
        ChansonDTO chansonEnCours = facade.getChansonEnCours();
        if (chansonEnCours != null) {
            setLblPiste(chansonEnCours);
            setLblChanson(chansonEnCours);
            setLblArtiste(chansonEnCours);
            setLblAlbum(chansonEnCours);
            setLblAnnee(chansonEnCours);
            setLblGenre(chansonEnCours);
            setLblDuree(chansonEnCours);
            setCoverAlbum(chansonEnCours);

            mettreAJourBoutonPlay(); // Met à jour le bouton play lorsqu'on ouvre un fichier
        }
    }

    /**
     * Méthode qui permet d'utiliser un seul bouton pour les fonctions play et PAUSE
     */
    private void mettreAJourBoutonPlay() {
        switch (facade.getEtatDuLecteur()) {
            case LECTURE:
                imgPlay.setImage(IMAGE_PAUSE);
                break;
            case PAUSE:
                imgPlay.setImage(IMAGE_PLAY);
                break;
            case ARRET:
                imgPlay.setImage(IMAGE_PLAY);
                break;
        }
    }

    /**
     * Méthode qui permet de mettre à jour les éléments dans la ListView
     */
    private void mettreAJourListView() {
        chansonsAffichees.set(facade.getIndexMusique(), facade.getChansonEnCours().getArtiste() +
                " - " + facade.getChansonEnCours().getTitre());
    }

    /**
     * Méthode qui met à jour le temps écoulé et ajuste le slider à toutes les secondes
     */
    private void miseAJourTempsEcoule() {

        Task<Void> tache = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                while (true) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            tempsEcoule = facade.getPourcentageTempsEcoule();
                            sliderTemps.adjustValue(tempsEcoule);
                            progressBarTemps.setProgress(tempsEcoule);
                            lblDureeEcoule.setText(facade.getTempsEcoule());
                        }
                    });
                    Thread.sleep(1000);
                }
            }
        };
        Thread th = new Thread(tache);
        th.setDaemon(true);
        th.start();
    }

    /**
     * Méthode qui permet d'afficher la liste d'écoute de chansons dans la
     * ListView
     *
     * @param infosChansons
     */
    protected void configurerListViewListeDEcoute(ArrayList<ChansonDTO> infosChansons) {
        chansonsAffichees = FXCollections.observableArrayList();
        for (ChansonDTO info : infosChansons) {
            chansonsAffichees.add(info.getArtiste() + " - " + info.getTitre());
        }
        lstChansonsEnCours.setItems(chansonsAffichees);
        lstChansonsEnCours.getSelectionModel().select(facade.getIndexMusique());
        deplacerChansonDansListeEvenement();
    }

    /**
     * Initialisation des évènements pour le drag & drop avec un CellFactory
     * <p>
     * Source:  D, James. Stack Overflow: Java fx frop drag and drop issue,
     * [http://stackoverflow.com/questions/25684503/java-fx-frop-drag-and-drop-issue],
     * (page consultée le 23 octobre 2014).
     */
    private void deplacerChansonDansListeEvenement() {
        final IntegerProperty dragFromIndex = new SimpleIntegerProperty(-1);

        lstChansonsEnCours.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                final ListCell<String> cell = new ListCell<String>() {
                    @Override
                    public void updateItem(String chanson, boolean estVide) {
                        super.updateItem(chanson, estVide);
                        if (estVide) {
                            setText(null);
                        } else {
                            setText(chanson);
                        }
                    }
                };

                cell.setOnDragDetected(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (!cell.isEmpty()) {
                            dragFromIndex.set(cell.getIndex());
                            Dragboard db = cell.startDragAndDrop(TransferMode.MOVE);
                            ClipboardContent cc = new ClipboardContent();
                            cc.putString(cell.getItem());
                            db.setContent(cc);
                        }
                    }
                });

                cell.setOnDragOver(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        if (dragFromIndex.get() >= 0 && dragFromIndex.get() != cell.getIndex()) {
                            event.acceptTransferModes(TransferMode.MOVE);
                        }
                    }
                });

                cell.setOnDragEntered(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        if (dragFromIndex.get() >= 0 && dragFromIndex.get() != cell.getIndex()) {
                            cell.setStyle("-fx-background-color: green;");
                        }
                    }
                });

                cell.setOnDragExited(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        cell.setStyle("");
                    }
                });

                cell.setOnDragDropped(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {

                        int dragItemsStartIndex;
                        int dragItemsEndIndex;
                        int direction;
                        if (cell.isEmpty()) {
                            dragItemsStartIndex = dragFromIndex.get();
                            dragItemsEndIndex = lstChansonsEnCours.getItems().size();
                            direction = -1;
                        } else {
                            if (cell.getIndex() < dragFromIndex.get()) {
                                dragItemsStartIndex = cell.getIndex();
                                dragItemsEndIndex = dragFromIndex.get() + 1;
                                direction = 1;
                            } else {
                                dragItemsStartIndex = dragFromIndex.get();
                                dragItemsEndIndex = cell.getIndex() + 1;
                                direction = -1;
                            }
                        }

                        List<String> rotatingItems = lstChansonsEnCours.getItems().subList(dragItemsStartIndex, dragItemsEndIndex);
                        List<String> rotatingItemsCopy = new ArrayList(rotatingItems);
                        Collections.rotate(rotatingItemsCopy, direction);
                        rotatingItems.clear();
                        rotatingItems.addAll(rotatingItemsCopy);
                        facade.deplacerChansonDansListe(dragItemsStartIndex, dragItemsEndIndex - 1, direction);
                        dragFromIndex.set(-1);
                    }
                });
                return cell;
            }
        });
    }

    /**
     * Methode qui initialise la liste des playlists dans l'onglet "liste de LECTURE"
     */
    private void configurerListViewListeLectureTab() {
        listesChansonAffichees = FXCollections.observableArrayList();
        listesChansonAffichees.addAll(facade.getPlaylists());
    }

    /**
     * Methode qui permet d'afficher les albums avec leurs infos dans l'onglet album
     */
    private void configurerListViewAlbumTab() {
        albumsAffiches = FXCollections.observableArrayList();
        albumsAffiches.addAll(facade.obtenirBibliothequeAlbums());
    }

    /**
     * Methode qui permet d'afficher les artistes dans l'onglet Artiste
     */
    private void configurerListViewArtisteTab() {
        artistesAffiches = FXCollections.observableArrayList();
        artistesAffiches.addAll(facade.obtenirBibliothequeArtistes());
    }

    /**
     * Méthode qui enlève les informations quand il n'y a plus de chansons dans le lecteur.
     */
    private void effacerInfos() {
        lblChanson.setText(TITRE);
        lblArtiste.setText(ARTISTE);
        lblAlbum.setText(ALBUM);
        lblAlbum.setText(ANNEE);
        lblGenre.setText(GENRE);
        coverAlbum.setImage(null);
    }

    /**
     * Méthode qui vide le listView quand il n'y a plus de chansons dans le lecteur.
     */
    private void viderListViewEcouteEnCours() {
        chansonsAffichees.clear();
    }

    /**
     * Méthode qui met la chanson en cours en surbrillance dans la listView.
     */
    private void selectionnerChansonEnCoursListView() {
        lstChansonsEnCours.getSelectionModel().select(facade.getIndexMusique());
    }

    /**
     * Méthode qui gère la répétition
     */
    private void repeterListeLecture() {
        switch (facade.getEtatRepetition()) {
            case REPETER_DESACTIVE:
                facade.setRepeterListe();
                break;
            case REPETER_CHANSON:
                facade.setRepeterArret();
                break;
            case REPETER_LISTE:
                facade.setRepeterChanson();
                break;
        }
        majBoutonListeLecture();
    }

    /**
     * Méthode qui gère le bouton répétition selon le skin
     */
    private void majBoutonListeLecture() {
        switch (facade.getEtatRepetition()) {
            case REPETER_DESACTIVE:
                imgRepeter.setImage(IMAGE_REPETER_INACTIF);
                break;
            case REPETER_CHANSON:
                imgRepeter.setImage(imageRepeterChanson);
                break;
            case REPETER_LISTE:
                imgRepeter.setImage(imageRepeterActif);
                break;
        }
    }

    /**
     * Méthode qui gère l'action aléatoire
     */
    private void gererModeAleatoire() {
        if (aleatoire) {
            facade.setLectureAleatoireInactif();
            aleatoire = false;
        } else {
            facade.setLectureAleatoireActif();
            aleatoire = true;
        }
        majBoutonModeAleatoire();
    }

    /**
     * Méthode qui gère le bouton aléatoire selon l'état ou le skin
     */
    private void majBoutonModeAleatoire() {
        if (aleatoire) {
            imgAleatoire.setImage(imageAleatoireActif);
        } else {
            imgAleatoire.setImage(IMAGE_ALEATOIRE_INACTIF);
        }
    }

    /**
     * Méthode qui gère l'action mute / unmute
     */
    private void gererAssourdissementVolume() {
        if (mute) {
            ajusterImageVolume();
            sliderVolume.setValue(volume);
            facade.setLecteurMuetInactif();
            mute = false;
        } else {
            imgVolume.setImage(IMAGE_VOLUME_MUTE);
            sliderVolume.setValue(0.0);
            facade.setLecteurMuetActif();
            mute = true;
        }
    }

    /**
     * Méthode qui ajuste l'icône du volume
     */
    private void ajusterImageVolume() {
        if (volume < VOLUME_QUART) {
            imgVolume.setImage(IMAGE_VOLUME_0);
        } else if (volume >= VOLUME_QUART && volume < VOLUME_MOITIE) {
            imgVolume.setImage(IMAGE_VOLUME_1);
        } else if (volume >= VOLUME_MOITIE && volume < VOLUME_TROIS_QUARTS) {
            imgVolume.setImage(IMAGE_VOLUME_2);
        } else if (volume >= VOLUME_TROIS_QUARTS) {
            imgVolume.setImage(IMAGE_VOLUME_3);
        }
    }

    /**
     * Méthode qui gère l'action arrêt
     */
    private void arretChanson() {
        facade.arreterChanson();
        mettreAJourBoutonPlay();
    }

    /**
     * Méthode qui gère l'action chanson précédente
     *
     * @throws MalformedURLException
     */
    private void choisirChansonPrecedente() throws MalformedURLException {
        facade.gererBoutonPrecendent();
        mettreAJourInfoMusique();
        selectionnerChansonEnCoursListView();
    }

    /**
     * Méthode qui active l'option passer à la chanson suivante
     */
    private void choisirChansonSuivante() throws MalformedURLException {
        facade.passerChansonSuivante();
        mettreAJourInfoMusique();
        selectionnerChansonEnCoursListView();
    }

    /**
     * Traitement du bouton Play/Pause et de la barre d'espacement
     * Le bouton a deux fonctions : Play et Pause
     * Selon l'état de l'application, on met sur PAUSE ou en LECTURE
     */
    private void jouerOuMettreSurPause() {
        switch (facade.getEtatDuLecteur()) {
            case LECTURE:
                facade.mettreSurPauseChanson();
                break;
            case PAUSE:
                facade.jouerChanson();
                break;
            case ARRET:
                facade.jouerChanson();
                break;
        }
        mettreAJourBoutonPlay();
    }

    /**
     * Méthode qui permet à l'utilisateur de sauvegarder sa liste de lecture
     *
     * @throws IOException
     */
    private void sauvegarderListeDeLecture() throws IOException {
        if (facade.getListeMusique().size() > 0) {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(SAUVEGARDE_FXML));
            Parent root = loader.load();
            final CoordonnateurSauvegardePlaylist coordonnateurSauvegardePlaylist = loader.getController();
            Scene scene = new Scene(root);
            initialiserStageSauvegardeListeEcoute(scene);

            coordonnateurSauvegardePlaylist.ajouterSauvegardePlaylistListener(new SauvegardePlaylistListener() {
                @Override
                public void sauvegardeDePlaylist(SauvegardePlaylistEvent sauvegardePlaylistEvent, String nom) {
                    try {
                        String pathFichierSauvegarde = facade.sauvegarderListeDeLecture(nom);
                        if (pathFichierSauvegarde != null) {
                            facade.persisterPlaylist(new String[]{nom, pathFichierSauvegarde});
                        }
                    } catch (SqlJetException e) {
                        e.printStackTrace();
                    }
                    configurerListViewListeLectureTab();
                    initialize();
                }
            });
            stageSauvegardeListe = null;
        }
    }

    /**
     * Ouvre une fenêtre qui permet de choisir une liste de lecture à laquelle on veut ajouter
     * une chanson.
     */
    private void ajouterListeDeLecture() {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(AJOUT_FXML));

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        final CoordonnateurAjoutListeDeLecture coordonnateurAjoutListeDeLecture = loader.getController();
        Scene scene = new Scene(root);
        initialiserStageAjoutListeDeLecture(scene);
        coordonnateurAjoutListeDeLecture.initialiserDonnes(listesChansonAffichees,
                tabChansons.getSelectionModel().getSelectedItem(), facade);
        stageAjoutListeDeLecture = null;
    }

    /**
     * Méthode qui recupere toutes les chansons de la BD et le transforme en observableList
     */
    private void creerListeChansonsFiltres() {
        ArrayList<ChansonDTO> DTO = facade.getChansonsDeLaBibliotheque();
        masterData = FXCollections.observableArrayList(DTO);
        filteredData = FXCollections.observableArrayList();
        filteredData.addAll(masterData);
        toutesChansons.addAll(filteredData);
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    protected void initialize() {
        initialiserElementsColonne();
        ajouterFiltresTabChanson();
        initialiserListViewAlbum();
        initialiserListViewPlaylist();
        initialiserListViewArtiste();
        changerOnglet(ctnOngletChanson);
    }

    /**
     * Méthode qui permet d'initialiser la liste view des artistes
     */
    private void initialiserListViewArtiste() {
        Coordonnateur coord = this;
        lstArtisteTab.setItems(artistesAffiches);
        lstArtisteTab.setCellFactory(new Callback<ListView, ListCell<ChansonDTO>>() {
            @Override
            public ListCell call(ListView param) {
                return new ElementListeArtistes(coord);
            }
        });
        lstArtisteTab.setContextMenu(menuOptions);
    }

    /**
     * Méthode qui permet d'initialiser la liste view des playlists
     */
    private void initialiserListViewPlaylist() {
        lstPlaylists.setItems(listesChansonAffichees);
        lstPlaylists.setCellFactory(new Callback<ListView, ListCell>() {
            @Override
            public ListCell call(ListView param) {
                return new ElementListePlaylists();
            }
        });
        lstPlaylists.setContextMenu(menuOptions);
    }

    /**
     * Méthode qui permet d'initialiser la liste view des albums
     */
    private void initialiserListViewAlbum() {
        Coordonnateur coord = this;
        lstAlbumTab.setItems(albumsAffiches);
        lstAlbumTab.setCellFactory(new Callback<ListView, ListCell<ChansonDTO>>() {
            @Override
            public ListCell call(ListView param) {
                return new ElementListeAlbums(coord);
            }
        });
        lstAlbumTab.setContextMenu(menuOptions);
    }

    /**
     * Methode qui initialise la fenetre de sauvegarde de liste d'ecoute
     *
     * @param scene
     * @throws IOException
     */
    private void initialiserStageSauvegardeListeEcoute(Scene scene) throws IOException {
        if (stageSauvegardeListe == null) {
            stageSauvegardeListe = new Stage();
            stageSauvegardeListe.setTitle(FENETRE_SAUVEGARDE_TITRE);
            stageSauvegardeListe.setScene(scene);
            stageSauvegardeListe.setResizable(false);
            stageSauvegardeListe.getIcons().add(IMAGE_ICONE_APP);
        }
        stageSauvegardeListe.show();
    }

    private void initialiserStageAjoutListeDeLecture(Scene scene) {
        if (stageAjoutListeDeLecture == null) {
            stageAjoutListeDeLecture = new Stage();
            stageAjoutListeDeLecture.setTitle(FENETRE_AJOUT_TITRE);
            stageAjoutListeDeLecture.setScene(scene);
            stageAjoutListeDeLecture.setResizable(false);
            stageAjoutListeDeLecture.getIcons().add(IMAGE_ICONE_APP);
        }
        stageAjoutListeDeLecture.show();
    }

    /**
     * Méthode qui ajoute des filtres à l'onglet Chansons
     */
    private void ajouterFiltresTabChanson() {
        tabChansons.setItems(filteredData);
        tabChansons.getItems().addListener(new ListChangeListener<ChansonDTO>() {
            @Override
            public void onChanged(Change<? extends ChansonDTO> c) {
                masterData.clear();
                masterData.setAll(filteredData);
            }
        });
        tabChansons.setContextMenu(menuOptions);
    }

    /**
     * Méthode qui permet d'initialiser les éléments des colonnes
     */
    private void initialiserElementsColonne() {
        colChanson.setCellValueFactory(
                new PropertyValueFactory<ChansonDTO, String>(TITRE));
        colArtiste.setCellValueFactory(
                new PropertyValueFactory<ChansonDTO, String>(ARTISTE));
        colAlbum.setCellValueFactory(
                new PropertyValueFactory<ChansonDTO, String>(ALBUM));

    }

    /**
     * Méthode qui lit la sélection de l'utilisateur.
     * Cela peut être une chanson, un artiste, un album ou une liste de lecture.
     */
    private void lireSelection() {
        switch (ctnOnglets.getSelectionModel().getSelectedItem().getId()) {
            case "ctnOngletChanson":
                lireChanson();
                break;
            case "ctnOngletAlbums":
                AlbumDTO albumChoisi = (AlbumDTO) lstAlbumTab.getSelectionModel().getSelectedItem();
                lireAlbum(albumChoisi.getAlbum());
                break;
            case "ctnOngletArtistes":
                ChansonDTO artisteChoisi = (ChansonDTO) lstArtisteTab.getSelectionModel().getSelectedItem();
                System.out.println(artisteChoisi);
                lireArtiste(artisteChoisi.getArtiste());
                break;
        }
    }

    /**
     * Méthode qui retire ce que l'utilisateur a choisit.
     * Cela peut être une chanson, un artiste, un album ou une liste de lecture.
     */
    private void retirerSelection() {
        switch (ctnOnglets.getSelectionModel().getSelectedItem().getId()) {
            case "ctnOngletChanson":
                supprimerChanson();
                break;
            case "ctnOngletAlbums":
                supprimerAlbum();
                break;
            case "ctnOngletArtistes":
                supprimerArtiste();
                break;
            case "ctnOngletPlaylist":
                supprimerListeDeLecture();
                break;
        }
    }

    /**
     * Ouvre un explorateur window dans le dossier où se trouve la chanson choisie.
     */
    private void afficherDansExplorateur() {
        ChansonDTO chansonChoisie = tabChansons.getSelectionModel().getSelectedItem();
        String cheminChanson = chansonChoisie.getPath();
        File dossier = new File(chansonChoisie.getPath().substring(0, cheminChanson.lastIndexOf(File.separator)));
        try {
            Desktop.getDesktop().open(dossier);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Fait la gestion quand l'utilisateur enlève une chanson de la BD.
     */
    private void supprimerChanson() {
        if (facade.trouverIndexDeLectureDUneChanson(tabChansons.getSelectionModel().getSelectedItem()) >= 0) {
            boolean listeVide = facade.enleverChanson(tabChansons.getSelectionModel().getSelectedItem());
            gererRetraitChansonsListeDeLecture(listeVide);
        }
        facade.supprimerChanson(tabChansons.getSelectionModel().getSelectedItem().getPath());
        creerListeChansonsFiltres();
        configurerListViewAlbumTab();
        configurerListViewArtisteTab();
        initialize();
    }

    /**
     * Fait la gestion quand l'utilisateur enlève un album de la BD.
     */
    private void supprimerAlbum() {
        boolean listeVide;
        AlbumDTO albumChoisi = (AlbumDTO) lstAlbumTab.getSelectionModel().getSelectedItem();
        if (lstChansonsEnCours.getItems().size() > 0) {
            listeVide = facade.enleverChansonsAlbum(albumChoisi.getAlbum());
            gererRetraitChansonsListeDeLecture(listeVide);
        }
        facade.supprimerAlbum(albumChoisi.getAlbum());
        configurerListViewAlbumTab();
        creerListeChansonsFiltres();
        configurerListViewArtisteTab();
        initialize();
    }

    /**
     * Fait la gestion quand l'utilisateur enlève un artiste de la BD.
     */
    private void supprimerArtiste() {
        boolean listeVide;
        ChansonDTO chanson = (ChansonDTO) lstArtisteTab.getSelectionModel().getSelectedItem();
        if (lstChansonsEnCours.getItems().size() > 0) {
            listeVide = facade.enleverChansonsArtiste(chanson.getArtiste());
            gererRetraitChansonsListeDeLecture(listeVide);
        }
        facade.supprimerArtiste(chanson.getArtiste());
        configurerListViewArtisteTab();
        configurerListViewAlbumTab();
        creerListeChansonsFiltres();
        initialize();
    }

    /**
     * Gère la suppression d'une liste de lecture de la bd.
     */
    private void supprimerListeDeLecture() {
        ListeDeLectureDTO playlistChoisie = (ListeDeLectureDTO) lstPlaylists.getSelectionModel().getSelectedItem();
        facade.supprimerListeDeLecture(playlistChoisie.getCheminListe());
        configurerListViewListeLectureTab();
        initialize();
    }

    /**
     * Méthode qui gère le retrait de chanson en fonction de la liste
     *
     * @param listeVide test sur la taille de la liste
     */
    private void gererRetraitChansonsListeDeLecture(boolean listeVide) {
        if (!listeVide) {
            mettreAJourInfoMusique();
            configurerListViewListeDEcoute(facade.getListeChansonsEnCours());
        } else {
            effacerInfos();
            viderListViewEcouteEnCours();
        }
    }

    /**
     * Méthode permettant d'obtenir la liste de chansons provenant d'un même album
     *
     * @param nomAlbum Nom de l'album ciblé
     * @return Liste de chansons de l'album
     */
    public ArrayList<ChansonDTO> obtenirChansonsAlbum(String nomAlbum) {
        return facade.obtenirChansonsAlbum(nomAlbum);
    }

    /**
     * Méthode permettant d'obtenir la liste de chansons provenant d'un même artiste
     *
     * @param nomArtiste Nom de l'artiste ciblé
     * @return Liste de chansons de l'artiste
     */
    public ArrayList<ChansonDTO> obtenirChansonsArtiste(String nomArtiste) {
        return facade.obtenirChansonsArtiste(nomArtiste);
    }

    /**
     * Thread activé lorsque l'utilisateur maintient le bouton avance rapide ou retour rapide enfoncé
     */
    @Override
    public void run() {
        while (estSuivantEnfonce) {
            facade.avancerRapideChanson();
            try {
                threadAvanceRapide.sleep(TEMPS_AVANCE_REMBOBINAGE / 10);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
        while (estPrecedentEnfonce) {
            facade.rembobinerChanson();
            try {
                threadRembobinage.sleep(TEMPS_AVANCE_REMBOBINAGE / 10);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

}