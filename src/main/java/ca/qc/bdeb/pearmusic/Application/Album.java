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
