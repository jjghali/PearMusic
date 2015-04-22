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

/**
 * DTO de playlist pour pouvoir transporter des données d'une playlist
 * sans exposer la playlist.
 * <p>
 * Created by Patrick Nolin on 2014-11-19.
 */
public class ListeDeLectureDTO {
    /**
     * Le nom de la liste de lecture
     */
    private String nomListe;
    /**
     * Le chemin d'acces vers cette liste
     */
    private String cheminListe;

    public ListeDeLectureDTO(String nomListe, String cheminListe) {
        this.nomListe = nomListe;
        this.cheminListe = cheminListe;
    }

    public String getNomListe() {
        return nomListe;
    }

    public void setNomListe(String nomListe) {
        this.nomListe = nomListe;
    }

    public String getCheminListe() {
        return cheminListe;
    }

    public void setCheminListe(String cheminListe) {
        this.cheminListe = cheminListe;
    }
}
