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

import javafx.scene.image.Image;

import java.io.File;

/**
 * Created by May on 09/11/2014.
 */
public interface ConstantesVue {
    /**
     * Indice de la premiere chanson
     */
    static final int INDICE_PREMIERE_CHANSON = 0;
    /**
     * Constante de l'augmentation du temps pour l'avance rapide
     * et le rembobinage
     */
    static final int TEMPS_AVANCE_REMBOBINAGE = 1000;
    /**
     * Image PAUSE
     */
    static final Image IMAGE_PAUSE = new Image("white_versions" + '/' + "pause.png");
    /**
     * Image play
     */
    static final Image IMAGE_PLAY = new Image("white_versions" + '/' + "play.png");
    /**
     * Image pour le bouton aléatoire quand il est innactif.
     */
    static final Image IMAGE_ALEATOIRE_INACTIF = new Image("white_versions" + '/' + "shuffleOff.png");
    /**
     * Image pour le bouton volume avec aucune barre.
     */
    static final Image IMAGE_VOLUME_3 = new Image("white_versions" + '/' + "Volume3.png");
    /**
     * Image pour le bouton volume avec aucune barre.
     */
    static final Image IMAGE_VOLUME_2 = new Image("white_versions" + '/' + "Volume2.png");
    /**
     * Image pour le bouton volume avec aucune barre.
     */
    static final Image IMAGE_VOLUME_1 = new Image("white_versions" + '/' + "Volume1.png");
    /**
     * Image pour le bouton volume avec aucune barre.
     */
    static final Image IMAGE_VOLUME_0 = new Image("white_versions" + '/' + "Volume0.png");
    /**
     * Image pour le bouton aléatoire quand il est à mute.
     */
    static final Image IMAGE_VOLUME_MUTE = new Image("white_versions" + '/' + "VolumeMute.png");
    /**
     * Image pour la recherche dans le skin bleu
     */
    static final Image IMAGE_RECHERCHE_BLEU = new Image("search_icon_blue.png");
    /**
     * Image pour la recherche dans le skin rouge
     */
    static final Image IMAGE_RECHERCHE_ROUGE = new Image("search_icon_red.png");
    /**
     * Image pour la recherche dans le skin vert
     */
    static final Image IMAGE_RECHERCHE_VERT = new Image("search_icon_default.png");
    /**
     * Image pour le bouton répeter quand il est innactif.
     */
    static final Image IMAGE_REPETER_INACTIF = new Image("white_versions" + '/' + "repeatoff.png");
    /**
     * Image répéter une chanson pour le skin bleu
     */
    static final Image IMAGE_REPETER_CHANSON_BLEU = new Image("white_versions" + '/' + "repeatSongBlue.png");
    /**
     * Image répéter une chanson pour le skin rouge
     */
    static final Image IMAGE_REPETER_CHANSON_ROUGE = new Image("white_versions" + '/' + "repeatSongRed.png");
    /**
     * Image répéter une chanson pour le skin vert
     */
    static final Image IMAGE_REPETER_CHANSON_VERT = new Image("white_versions" + '/' + "repeatSong.png");
    /**
     * Image répéter toutes les chansons pour le skin bleu
     */
    static final Image IMAGE_REPETER_BLEU = new Image("white_versions" + '/' + "repeatonBlue.png");
    /**
     * Image répéter toutes les chansons pour le skin rouge
     */
    static final Image IMAGE_REPETER_ROUGE = new Image("white_versions" + '/' + "repeatonRed.png");
    /**
     * Image répéter toutes les chansons pour le skin vert
     */
    static final Image IMAGE_REPETER_VERT = new Image("white_versions" + '/' + "repeaton.png");
    /**
     * Image aléatoire pour le skin bleu
     */
    static final Image IMAGE_ALEATOIRE_BLEU = new Image("white_versions" + '/' + "shuffleOnBlue.png");
    /**
     * Image aléatoire pour le skin rouge
     */
    static final Image IMAGE_ALEATOIRE_ROUGE = new Image("white_versions" + '/' + "shuffleOnRed.png");
    /**
     * Image aléatoire pour le skin vert
     */
    static final Image IMAGE_ALEATOIRE_VERT = new Image("white_versions" + '/' + "shuffleOn.png");
    /**
     * CSS pour le theme bleu
     */
    static final String THEME_BLEU = "blue_skin.css";
    /**
     * CSS pour le theme rouge
     */
    static final String THEME_ROUGE = "red_skin.css";
    /**
     * CSS pour le theme vert
     */
    static final String THEME_VERT = "default_skin.css";
    /**
     * Titre
     */
    static final String TITRE = "Titre";
    /**
     * Album
     */
    static final String ALBUM = "Album";
    /**
     * Artiste
     */
    static final String ARTISTE = "Artiste";
    /**
     * Annee
     */
    static final String ANNEE = "Année";
    /**
     * Genre
     */
    static final String GENRE = "Genre";
    /**
     * Titre de la fenetre de sauvegarde
     */
    static final String FENETRE_SAUVEGARDE_TITRE = "Sauvegarde d'une liste d'écoute";
    /**
     * Titre de la fenêtre d'ajout.
     */
    static final String FENETRE_AJOUT_TITRE = "Ajout à une liste de lecture";
    /**
     * Nom du fichier fxml du UI de la sauvegarde de playlist
     */
    static final String SAUVEGARDE_FXML = "uiSauvegardePlaylist.fxml";
    /**
     * Nom du fichier fxml du UI d'ajout de chanson à une playlist.
     */
    static final String AJOUT_FXML = "uiAjoutPlaylist.fxml";
    /**
     * Pourcentage du volume par defaut
     */
    static final double VOLUME_DEFAUT = 1.0;
    /**
     * Pourcentage du volume equivalent au quart
     */
    static final double VOLUME_QUART = 0.25;
    /**
     * Pourcentage du volume equivalent a la moitie
     */
    static final double VOLUME_MOITIE = 0.5;
    /**
     * Pourcentage du volume equivalent au 3/4
     */
    static final double VOLUME_TROIS_QUARTS = 0.75;
    /**
     * Titre de la fenetre A Propos
     */
    static final String FENETRE_A_PROPOS_TITRE = "À Propos de Pear Music";
    /**
     * Nom du fichier de l'icone de Pear Music
     */
    public static final Image IMAGE_ICONE_APP = new Image("icon.png");
    /**
     * Nom du fichier fxml du UI de la fenetre A Propos
     */
    static final String A_PROPOS_FXML = "aPropos.fxml";
    /**
     * Largeur du panneau des élements de l'album
     */
    static final double PANNEAU_ELEMENTS_ALBUM_LARGEUR = 376.0;
    /**
     * Hauteur du panneau des élements de l'album
     */
    static final double PANNEAU_ELEMENTS_ALBUM_HAUTEUR = 151.0;
    /**
     * Taille de l'image de l'album
     */
    static final double IMAGE_ALBUM_TAILLE = 130.0;
    /**
     * Position de l'image de l'album
     */
    static final double IMAGE_ALBUM_POSITION = 10.0;
    /**
     * Position en X des labels dans le
     * panneau des élements de l'album
     */
    static final double TEXTE_ANNEE_POSITION_X = 152.0;
    /**
     * Position en Y du label du nom de l'album dans le
     * panneau des élements de l'album
     */
    static final double NOM_ALBUM_POSITION_Y = 35.0;
    /**
     * Position en Y du label du l'année dans le
     * panneau des élements de l'album
     */
    static final double TEXTE_ANNEE_POSITION_Y = 60.0;
    /**
     * Position en Y du label du nom de l'artiste dans le
     * panneau des élements de l'album
     */
    static final double NOM_ARTISTE_POSITION_Y = 10.0;
    /**
     * Largeur de la liste view dans le
     * panneau des élements de l'album
     */
    static final double LISTE_VIEW_ELEMENT_ALBUM_LARGEUR = 232.0;
    /**
     * Hauteur de la liste view dans le
     * panneau des élements de l'album
     */
    static final double LISTE_VIEW_ELEMENT_ALBUM_HAUTEUR = 152.0;
    /**
     * Largeur de la liste view dans le
     * panneau des élements de l'album
     */
    static final double LISTE_VIEW_ELEMENT_ALBUM_POS_X = 285.0;
    /**
     * Largeur de la liste view dans le
     * panneau des élements de l'album
     */
    static final double LISTE_VIEW_ELEMENT_ALBUM_POS_Y = 0.0;
    /**
     * Largeur des elements du panneau des artiste
     */
    static final double PANNEAU_ELEMENTS_ARTISTE_LARGEUR = 376.0;
    /**
     * Hauteur des elements du panneau des artiste
     */
    static final double PANNEAU_ELEMENTS_ARTISTE_HAUTEUR = 100.0;
    /**
     * largeur de la liste des artiste
     */
    static final double LISTE_VIEW_ELEMENT_ARTISTE_LARGEUR = 355.0;
    /**
     * Hauteur de la liste des artiste
     */
    static final double LISTE_VIEW_ELEMENT_ARTISTE_HAUTEUR = 100.0;
    /**
     * Position X de la liste des chansons de l'artiste dans l'element artiste
     */
    static final double LISTE_VIEW_ELEMENT_ARTISTE_POS_X = 150.0;
    /**
     * Position Y de la liste des chansons de l'artiste dans l'element artiste
     */
    static final double LISTE_VIEW_ELEMENT_ARTISTE_POS_Y = 0.0;
    /**
     * Position X du label des artistes
     */
    static final double TEXTE_LBL_ARTISTE_POSITION_X = 0.0;
    /**
     * Position X du bouton tout jouer sur la liste des albums
     */
    static final double LISTE_ALBUM_BTN_TOUT_JOUER_POSITION_X = 160.0;
    /**
     * Position X du bouton tout jouer sur la liste des albums
     */
    static final double LISTE_ALBUM_BTN_TOUT_JOUER_POSITION_Y = 120.0;
    /**
     * Largeur du bouton tout jouer
     */
    static final double BTN_TOUT_JOUER_LARGEUR = 100.0;
    /**
     * Style du bouton tout jouer
     */
    static final String BTN_TOUT_JOUER_STYLE = " -fx-background-color: #CCCCCC;" +
            "-fx-text-fill: black";
    /**
     * texte du bouton tout jouer
     */
    static final String TOUT_JOUER = "Tout ajouter";
    /**
     * Position X du bouton tout jouer sur la liste des artistes
     */
    static final double LISTE_ARTISTE_BTN_TOUT_JOUER_X = 30.0;
    /**
     * Position X du bouton tout jouer sur la liste des artistes
     */
    static final double LISTE_ARTISTE_BTN_TOUT_JOUER_Y = 80.0;
}
