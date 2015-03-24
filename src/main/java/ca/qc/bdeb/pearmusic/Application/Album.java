package ca.qc.bdeb.pearmusic.Application;

import javafx.scene.image.Image;

/**
 * Classe qui permet de connaître toutes les informations nécessaire à l'affichage et à l'utilisation du Tab Album
 * <p>
 * Created by Joshua Ghali on 10/25/2014.
 */
class Album {
    /**
     * Nom de l'album
     */
    private String album;
    /**
     * Nom de l'artiste de l'album
     */
    private String artiste;
    /**
     * Année de parution de l'album
     */
    private String annee;
    /**
     * Pochette de l'album
     */
    private Image albumArt;

    /**
     * Constructeur
     *
     * @param albumArt
     * @param album
     * @param artiste
     * @param annee
     */
    public Album(Image albumArt, String album, String artiste, String annee) {
        this.albumArt = albumArt;
        this.album = album;
        this.artiste = artiste;
        this.annee = annee;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public String getArtiste() {
        return artiste;
    }

    public void setArtiste(String artiste) {
        this.artiste = artiste;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public Image getAlbumArt() {
        return albumArt;
    }

    public void setAlbumArt(Image albumArt) {
        this.albumArt = albumArt;
    }
}
