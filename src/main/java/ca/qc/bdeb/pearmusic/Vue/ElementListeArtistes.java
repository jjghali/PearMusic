package ca.qc.bdeb.pearmusic.Vue;

import ca.qc.bdeb.pearmusic.Application.ChansonDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Element qui contient les infos d'un album dans la listview de l'onglet Album
 *
 * @author Joshua Ghali
 * @since 18/10/2014
 */
public class ElementListeArtistes extends ListCell<ChansonDTO> implements ConstantesVue {

    /**
     * Lien vers le coordonnateur
     */
    private Coordonnateur coordonnateur;
    /**
     * Toutes les chansons d'un album
     */
    private ArrayList<ChansonDTO> chansonsAlbum;
    /**
     * Composante graphique pour le texte du nom de l'artiste
     */
    private Label lblArtiste;
    /**
     * Composante graphique pour le panneau contenant les informations de l'album
     */
    private Pane panneau;
    /**
     * Composante graphique pour la liste des chansons de l'album
     */
    private ListView<String> lstChansons;
    /**
     * Bouton qui permet de tout jouer ce qui est dans le panneau
     */
    private Button btnToutJouer;
    /**
     * Nom de l'artiste
     */
    private String nomArtiste;

    public ElementListeArtistes(Coordonnateur coordonnateur) {
        this.coordonnateur = coordonnateur;
    }


    /**
     * Methode qui contient les parametres de mise en page des elements de la liste ainsi que les elements eux meme.
     *
     * @param item
     * @param vide
     */
    @Override
    public void updateItem(ChansonDTO item, boolean vide) {
        super.updateItem(item, vide);
        if (item != null) {
            ObservableList<String> listeChansons = FXCollections.observableArrayList();
            ArrayList<String> listeChansonChaine = initialiserListeChansons(item);
            listeChansons.addAll(listeChansonChaine);
            initialiserComposantes(item, listeChansons);
        }
    }

    /**
     * Méthode initialisant la liste des chansons de l'album
     *
     * @param item
     * @return
     */
    private ArrayList<String> initialiserListeChansons(ChansonDTO item) {
        ArrayList<String> listeChansons = new ArrayList<String>();
        nomArtiste = item.getArtiste();
        chansonsAlbum = coordonnateur.obtenirChansonsArtiste(item.getArtiste());
        listeChansons.addAll(chansonsAlbum.stream().map(ChansonDTO::getTitre).collect(Collectors.toList()));
        return listeChansons;
    }

    /**
     * Méthode initialisant les composantes graphiques
     *
     * @param item
     * @param listeChansons
     */
    private void initialiserComposantes(ChansonDTO item, ObservableList<String> listeChansons) {
        initialiserLabelNomArtiste(item);
        initialiserListeViewChanson(listeChansons);
        initialiserBoutonToutJouer();
        initialiserPanneau();
    }

    /**
     * Méthode qui initialise le bouton tout jouer
     */
    private void initialiserBoutonToutJouer() {
        btnToutJouer = new Button();
        btnToutJouer.setText(TOUT_JOUER);
        btnToutJouer.setLayoutX(LISTE_ARTISTE_BTN_TOUT_JOUER_X);
        btnToutJouer.setLayoutY(LISTE_ARTISTE_BTN_TOUT_JOUER_Y);
        btnToutJouer.setPrefWidth(BTN_TOUT_JOUER_LARGEUR);
        btnToutJouer.setStyle(BTN_TOUT_JOUER_STYLE);
        btnToutJouer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                coordonnateur.lireArtiste(nomArtiste);
            }
        });
    }

    /**
     * Méthode initialisant le panneau
     */
    private void initialiserPanneau() {
        panneau = new Pane();
        panneau.setMaxSize(PANNEAU_ELEMENTS_ARTISTE_LARGEUR, PANNEAU_ELEMENTS_ARTISTE_HAUTEUR);
        panneau.setPrefSize(PANNEAU_ELEMENTS_ARTISTE_LARGEUR, PANNEAU_ELEMENTS_ARTISTE_HAUTEUR);
        panneau.setMinSize(PANNEAU_ELEMENTS_ARTISTE_LARGEUR, PANNEAU_ELEMENTS_ARTISTE_HAUTEUR);
        panneau.getChildren().add(lstChansons);
        panneau.getChildren().add(lblArtiste);
        panneau.getChildren().add(btnToutJouer);
        setGraphic(panneau);
    }

    /**
     * Méthode initialisant la liste View contenant la liste de chansons
     *
     * @param listeChansons
     */
    private void initialiserListeViewChanson(ObservableList<String> listeChansons) {
        lstChansons = new ListView<>();
        lstChansons.setItems(listeChansons);
        lstChansons.setPrefWidth(LISTE_VIEW_ELEMENT_ARTISTE_LARGEUR);
        lstChansons.setPrefHeight(LISTE_VIEW_ELEMENT_ARTISTE_HAUTEUR);
        lstChansons.setLayoutX(LISTE_VIEW_ELEMENT_ARTISTE_POS_X);
        lstChansons.setLayoutY(LISTE_VIEW_ELEMENT_ARTISTE_POS_Y);
        lstChansons.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int index = lstChansons.getSelectionModel().getSelectedIndex();
                if (index != -1) {
                    ArrayList<ChansonDTO> chanson = new ArrayList<ChansonDTO>();
                    chanson.add(chansonsAlbum.get(lstChansons.getSelectionModel().getSelectedIndex()));
                    try {
                        coordonnateur.cliquerChansonAlbum(chanson);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * Méthode initialisant le texte du nom de l'artiste
     *
     * @param item
     */
    private void initialiserLabelNomArtiste(ChansonDTO item) {
        lblArtiste = new Label();
        lblArtiste.setText(item.getArtiste());
        lblArtiste.setLayoutX(TEXTE_LBL_ARTISTE_POSITION_X);
        lblArtiste.setLayoutY(NOM_ARTISTE_POSITION_Y);
    }
}
