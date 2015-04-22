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
 * Les différents genres musicaux ont été définis dans l'ID3v1 par une valeur numérique de 0 à 79.
 * Cependant, le logiciel Winamp a étendu cette liste jusqu'à 147.
 * La classe Genre permet de connaître le style correspondant au numéro contenu dans l'id3 du fichier musical
 *
 * @author Dominique Cright
 * @date 2014-09-16
 */
class Genre implements ConstantesApplication {
    /**
     * Permet d'obtenir la liste en string pour affichage
     *
     * @return les styles dans un tableau, la no de la case correspond au Tag ID3
     */
    public static String[] getGenres() {
        return TYPE_GENRE;
    }

    /**
     * Méthode permettant d'avoir une String correspondante au id du style musical
     *
     * @param id
     * @return
     */
    public static String getGenre(int id) {
        if (id >= 0 && id < TYPE_GENRE.length) {
            return TYPE_GENRE[id];
        } else {
            return GENRE_INCONNU;
        }
    }

    /**
     * Méthode qui retourne le no du genre selon la liste de l'ID3v2
     *
     * @param genre le nom du genre
     * @return son numéro, -1 si il n'est pas dans la liste
     */
    public static int getIDGenre(String genre) {
        int id = -1;
        for (int i = 0; i < TYPE_GENRE.length; i++) {
            if (genre.matches(TYPE_GENRE[i])) {
                id = i;
            }
        }
        if (id >= 0 && id < TYPE_GENRE.length) {
            return id;
        } else {
            return -1;
        }
    }

}
