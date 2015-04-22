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

 package ca.qc.bdeb.pearmusic.Application;

import javafx.scene.image.Image;

import java.util.Objects;

/**
 * Classe DTO sert à transférer les informations des fichiers de l'utilisateur entre la bibliothèque et la vue.
 *
 * @author Stu Ureta
 * @since 2014-10-24
 */
public class ChansonDTO {
    /**
     * Path de la chanson
     */
    private String path;
    /**
     * Piste
     */
    private String piste;
    /**
     * Nom de l'artiste
     */
    private String artiste;
    /**
     * Titre de la chanson
     */
    private String titre;
    /**
     * Album sur lequel la chanson apparaît
     */
    private String album;
    /**
     * Année de publication de la chanson
     */
    private String annee;
    /**
     * Genre de la chanson
     */
    private int genre;
    /**
     * Genre transformée en String pour l'affichage
     */
    private String genreString;
    /**
     * Durée de la chanson
     */
    private long duree;
    /**
     * Durée transformée en string pour l'affichage
     */
    private String dureeString;
    /**
     * Chemin d'accès de l'image du fichier, si elle a été modifiée par l'utilisateur
     */
    private String pathImage;
    /**
     * Pochette du disque
     */
    private Image albumArt;

    public ChansonDTO() {

    }

    /**
     * Ce constructeur paramétrique doit recevoir :
     * - le chemin d'accès sur le disque
     * - le numéro de la piste
     * - le nom de l'artiste
     * - le titre de la chanson
     * - l'album sur lequel la chanson a été enregistré
     * - l'année de parution
     * - le numéro correspondant au genre (int), * voir classe Genre *
     * - la durée en secondes (long)
     *
     * @param path
     * @param piste
     * @param artiste
     * @param titre
     * @param album
     * @param annee
     * @param genre
     * @param duree
     * @param pathImage
     */
    public ChansonDTO(String path, String piste, String artiste, String titre, String album, String annee, int genre,
                      long duree, String pathImage) {
        this.path = path;
        this.piste = piste;
        this.artiste = artiste;
        this.titre = titre;
        this.album = album;
        this.annee = annee;
        this.genre = genre;
        this.duree = duree;
        this.pathImage = pathImage;
        this.albumArt = null;
        this.dureeString = null;
        this.genreString = null;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getArtiste() {
        return artiste;
    }

    public void setArtiste(String artiste) {
        this.artiste = artiste;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getPiste() {
        return piste;
    }

    public void setPiste(String piste) {
        this.piste = piste;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public int getGenre() {
        return genre;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }

    public long getDuree() {
        return duree;
    }

    public void setDuree(long duree) {
        this.duree = duree;
    }

    public String getGenreString() {
        return genreString;
    }

    public void setGenreString(String genreString) {
        this.genreString = genreString;
    }

    public String getDureeString() {
        return dureeString;
    }

    public void setDureeString(String dureeString) {
        this.dureeString = dureeString;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public Image getAlbumArt() {
        return albumArt;
    }

    public void setAlbumArt(Image albumArt) {
        this.albumArt = albumArt;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ChansonDTO other = (ChansonDTO) obj;
        if (!this.path.equals(other.path)) {
            return false;
        }
        if (!Objects.equals(this.titre, other.titre)) {
            return false;
        }
        if (!Objects.equals(this.artiste, other.artiste)) {
            return false;
        }
        if (!Objects.equals(this.album, other.album)) {
            return false;
        }
        return true;
    }
}
