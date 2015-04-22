/*
*   Copyright Pear Music 2015
*
*   This file is part of PearMusic.
*
*   PearMusic.is free software: you can redistribute it and/or modify
*   it under the terms of the GNU General Public License as published by
*   the Free Software Foundation, either version 3 of the License, or
*   (at your option) any later version.
*
*   PearMusic is distributed in the hope that it will be useful,
*   but WITHOUT ANY WARRANTY; without even the implied warranty of
*   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*   GNU General Public License for more details.
*
*   You should have received a copy of the GNU General Public License
*   along with PearMusic   If not, see <http://www.gnu.org/licenses/>.
*/
package ca.qc.bdeb.pearmusic.Vue;

import ca.qc.bdeb.pearmusic.Application.AlbumDTO;
import ca.qc.bdeb.pearmusic.Application.ChansonDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Element qui contient les infos d'un album dans la listview de l'onglet Album
 *
 * @author Joshua Ghali
 * @since 18/10/2014
 */
public class ElementListeAlbums extends ListCell<AlbumDTO> implements ConstantesVue {
    /**
     * Bouton pour jouer toute les chansons de l'album
     */
    Button btnToutJouer;
    /**
     * Lien vers le coordonnateur
     */
    private Coordonnateur coordonnateur;
    /**
     * Toutes les chansons d'un album
     */
    private ArrayList<ChansonDTO> chansonsAlbum;
    /**
     * Composante graphique pour l'image de l'album
     */
    private ImageView imgAlbumArt;
    /**
     * Composante graphique pour le texte du nom de l'album
     */
    private Label lblNomAlbum;
    /**
     * Composante graphique pour le texte du nom de l'artiste
     */
    private Label lblArtiste;
    /**
     * Composante graphique pour le texte de l'année de l'album
     */
    private Label lblAnnee;
    /**
     * Composante graphique pour le panneau contenant les informations de l'album
     */
    private Pane panneau;
    /**
     * Composante graphique pour la liste des chansons de l'album
     */
    private ListView<String> lstChansons;
    /**
     * Lien vers le chanson
     */
    private AlbumDTO album;

    public ElementListeAlbums(Coordonnateur coordonnateur) {
        this.coordonnateur = coordonnateur;
    }

    /**
     * Methode qui contient les parametres de mise en page des elements de la liste ainsi que les elements eux meme.
     *
     * @param item
     * @param vide
     */
    @Override
    public void updateItem(AlbumDTO item, boolean vide) {
        this.album = item;
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
    private ArrayList<String> initialiserListeChansons(AlbumDTO item) {
        ArrayList<String> listeChansons = new ArrayList<String>();
        chansonsAlbum = coordonnateur.obtenirChansonsAlbum(item.getAlbum());
        for (ChansonDTO chanson : chansonsAlbum) {
            listeChansons.add(chanson.getTitre());
        }
        return listeChansons;
    }

    /**
     * Méthode initialisant les composantes graphiques
     *
     * @param item
     * @param listeChansons
     */
    private void initialiserComposantes(AlbumDTO item, ObservableList<String> listeChansons) {
        initialiserImageAlbum(item);
        initaliserLabelNomAlbum(item);
        initialiserLabelNomArtiste(item);
        initialiserLabelAnnee(item);
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
        btnToutJouer.setLayoutX(LISTE_ALBUM_BTN_TOUT_JOUER_POSITION_X);
        btnToutJouer.setLayoutY(LISTE_ALBUM_BTN_TOUT_JOUER_POSITION_Y);
        btnToutJouer.setPrefWidth(BTN_TOUT_JOUER_LARGEUR);
        btnToutJouer.setStyle(BTN_TOUT_JOUER_STYLE);
        btnToutJouer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                coordonnateur.lireAlbum(album.getAlbum());
            }
        });
    }

    /**
     * Méthode initialisant le panneau
     */
    private void initialiserPanneau() {
        panneau = new Pane();
        panneau.setMaxSize(PANNEAU_ELEMENTS_ALBUM_LARGEUR, PANNEAU_ELEMENTS_ALBUM_HAUTEUR);
        panneau.setPrefSize(PANNEAU_ELEMENTS_ALBUM_LARGEUR, PANNEAU_ELEMENTS_ALBUM_HAUTEUR);
        panneau.setMinSize(PANNEAU_ELEMENTS_ALBUM_LARGEUR, PANNEAU_ELEMENTS_ALBUM_HAUTEUR);
        panneau.getChildren().add(lstChansons);
        panneau.getChildren().add(imgAlbumArt);
        panneau.getChildren().add(lblNomAlbum);
        panneau.getChildren().add(lblArtiste);
        panneau.getChildren().add(lblAnnee);
        panneau.getChildren().add(btnToutJouer);
        setGraphic(panneau);
    }

    /**
     * Méthode initialisant la liste View contenant la liste de chansons
     *
     * @param listeChansons
     */
    private void initialiserListeViewChanson(ObservableList<String> listeChansons) {
        lstChansons = new ListView<String>();
        lstChansons.setItems(listeChansons);
        lstChansons.setPrefWidth(LISTE_VIEW_ELEMENT_ALBUM_LARGEUR);
        lstChansons.setPrefHeight(LISTE_VIEW_ELEMENT_ALBUM_HAUTEUR);
        lstChansons.setLayoutX(LISTE_VIEW_ELEMENT_ALBUM_POS_X);
        lstChansons.setLayoutY(LISTE_VIEW_ELEMENT_ALBUM_POS_Y);
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
     * Méthode initialisant le texte de l'année
     *
     * @param item
     */
    private void initialiserLabelAnnee(AlbumDTO item) {
        lblAnnee = new Label();
        lblAnnee.setText(item.getAnnee());
        lblAnnee.setLayoutX(TEXTE_ANNEE_POSITION_X);
        lblAnnee.setLayoutY(TEXTE_ANNEE_POSITION_Y);

    }

    /**
     * Méthode initialisant le texte du nom de l'artiste
     *
     * @param item
     */
    private void initialiserLabelNomArtiste(AlbumDTO item) {
        lblArtiste = new Label();
        lblArtiste.setText(item.getArtiste());
        lblArtiste.setLayoutX(TEXTE_ANNEE_POSITION_X);
        lblArtiste.setLayoutY(NOM_ARTISTE_POSITION_Y);
    }

    /**
     * Méthode initialisant le texte du nom de l'album
     *
     * @param item
     */
    private void initaliserLabelNomAlbum(AlbumDTO item) {
        lblNomAlbum = new Label();
        lblNomAlbum.setText(item.getAlbum());
        lblNomAlbum.setLayoutX(TEXTE_ANNEE_POSITION_X);
        lblNomAlbum.setLayoutY(NOM_ALBUM_POSITION_Y);
    }

    /**
     * Méthode initialisant l'image de l'album
     *
     * @param item
     */
    private void initialiserImageAlbum(AlbumDTO item) {
        imgAlbumArt = new ImageView();
        imgAlbumArt.setImage(item.getAlbumArt());
        imgAlbumArt.setFitHeight(IMAGE_ALBUM_TAILLE);
        imgAlbumArt.setFitWidth(IMAGE_ALBUM_TAILLE);
        imgAlbumArt.setPreserveRatio(true);
        imgAlbumArt.setPickOnBounds(false);
        imgAlbumArt.setSmooth(true);
        imgAlbumArt.setLayoutX(IMAGE_ALBUM_POSITION);
        imgAlbumArt.setLayoutY(IMAGE_ALBUM_POSITION);
    }

}
