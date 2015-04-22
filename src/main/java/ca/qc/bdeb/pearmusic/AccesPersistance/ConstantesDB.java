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

 package ca.qc.bdeb.pearmusic.AccesPersistance;

import java.io.File;

/**
 * Interface qui permet de connaître toutes les constantes nécessaires à la création de la base de données
 *
 * Created by Dominique Cright on 2014-11-26.
 */
interface ConstantesDB {
    /**
     * Index de la colonne path de la table bibliothèque
     */
    static final String BIBLIOTHEQUE_INDEX_PATH = "index_path";
    /**
     * Index de la colonne album de la table bibliothèque
     */
    static final String BIBLIOTHEQUE_INDEX_ALBUM = "index_album";
    /**
     * Chemin d'accès de la base de données
     */
    final String DB_NAME = File.separator + "Pear_Music" + File.separator + "pearMusic.db";
    /**
     * Table bibliothèque de la base de données
     */
    final String TABLE_BIBLIOTHEQUE = "Bibliotheque";
    /**
     * Clé primaire de la table bibliothèque, colonne chemin d'accès du fichier
     */
    final String BIBLIOTHEQUE_PATH = "path";
    /**
     * Colonne artiste de la table bibliothèque
     */
    final String BIBLIOTHEQUE_ARTISTE = "artiste";
    /**
     * Colonne album de la table bibliothèque
     */
    final String BIBLIOTHEQUE_ALBUM = "album";
    /**
     * Colonne titre de la table bibliothèque
     */
    final String BIBLIOTHEQUE_TITRE = "titre";
    /**
     * Colonne piste de la table bibliothèque
     */
    final String BIBLIOTHEQUE_PISTE = "piste";
    /**
     * Colonne annee de la table bibliothèque
     */
    final String BIBLIOTHEQUE_ANNEE = "annee";
    /**
     * Colonne genre de la table bibliothèque
     */
    final String BIBLIOTHEQUE_GENRE = "genre";
    /**
     * Colonne duree de la table bibliothèque
     */
    final String BIBLIOTHEQUE_DUREE = "duree";
    /**
     * Colonne image de la table bibliothèque
     */
    final String BIBLIOTHEQUE_IMAGE = "image";
    /**
     * Appel de création de la table bibliotheque
     */
    final String CREATE_TABLE_BIBLIOTHEQUE = "CREATE TABLE " + TABLE_BIBLIOTHEQUE + " ("
            + BIBLIOTHEQUE_PATH + " TEXT NOT NULL PRIMARY KEY, "
            + BIBLIOTHEQUE_ARTISTE + " TEXT, "
            + BIBLIOTHEQUE_ALBUM + " TEXT, "
            + BIBLIOTHEQUE_TITRE + " TEXT, "
            + BIBLIOTHEQUE_PISTE + " TEXT, "
            + BIBLIOTHEQUE_ANNEE + " TEXT, "
            + BIBLIOTHEQUE_GENRE + " NUMBER, "
            + BIBLIOTHEQUE_DUREE + " NUMBER, "
            + BIBLIOTHEQUE_IMAGE + " TEXT)";
    /**
     * Index de la colonne artiste de la table bibliothèque
     */
    final String BIBLIOTHEQUE_INDEX_ARTIST = "index_artist";
    /**
     * Index de la colonne titre de la table bibliothèque
     */
    final String BIBLIOTHEQUE_INDEX_TITRE = "index_titre";
    /**
     * Index de la colonne annee de la table bibliothèque
     */
    final String BIBLIOTHEQUE_INDEX_ANNEE = "index_annee";
    /**
     * Index de la colonne genre de la table bibliothèque
     */
    final String BIBLIOTHEQUE_INDEX_GENRE = "index_genre";
    /**
     * Index de la colonne path de la table des listes de lecture.
     */
    final String LISTE_LECTURE_INDEX_PATH = "index_path_liste_lecture";
    /**
     * Index de la colonne nom de la table des listes de lecture.
     */
    final String INDEX_NOM_LISTE_LECTURE = "index_nom_liste_lecture";
    /**
     * Appel de création de l'index path de table bibliotheque
     */
    final String CREATE_PATH_INDEX_QUERY_PATH = "CREATE INDEX " + BIBLIOTHEQUE_INDEX_PATH + " ON " +
            TABLE_BIBLIOTHEQUE + "(" + BIBLIOTHEQUE_PATH + ")";
    /**
     * Appel de création de l'index artiste de table bibliotheque
     */
    final String CREATE_PATH_INDEX_QUERY_ARTIST = "CREATE INDEX " + BIBLIOTHEQUE_INDEX_ARTIST +
            " ON " + TABLE_BIBLIOTHEQUE + "(" + BIBLIOTHEQUE_ARTISTE + ")";
    /**
     * Appel de création de l'index album de table bibliotheque
     */
    final String CREATE_PATH_INDEX_QUERY_ALBUM = "CREATE INDEX " + BIBLIOTHEQUE_INDEX_ALBUM +
            " ON " + TABLE_BIBLIOTHEQUE + "(" + BIBLIOTHEQUE_ALBUM + ")";
    /**
     * Appel de création de l'index titre de table bibliotheque
     */
    final String CREATE_PATH_INDEX_QUERY_TITRE = "CREATE INDEX " + BIBLIOTHEQUE_INDEX_TITRE +
            " ON " + TABLE_BIBLIOTHEQUE + "(" + BIBLIOTHEQUE_TITRE + ")";
    /**
     * Appel de création de l'index annee de table bibliotheque
     */
    final String CREATE_PATH_INDEX_QUERY_ANNEE = "CREATE INDEX " + BIBLIOTHEQUE_INDEX_ANNEE +
            " ON " + TABLE_BIBLIOTHEQUE + "(" + BIBLIOTHEQUE_ANNEE + ")";
    /**
     * Appel de création de l'index genre de table bibliotheque
     */
    final String CREATE_PATH_INDEX_QUERY_GENRE = "CREATE INDEX " + BIBLIOTHEQUE_INDEX_GENRE +
            " ON " + TABLE_BIBLIOTHEQUE + "(" + BIBLIOTHEQUE_GENRE + ")";
    /**
     * Table playlist de la base de données
     */
    final String TABLE_LISTE_LECTURE = "ListeLecture";
    /**
     * Clé primaire de la table playlist, colonne chemin d'accès du fichier
     */
    final String LISTE_LECTURE_PATH = "path";
    /**
     * Colonne nom de la liste de lecture
     */
    final String LISTE_LECTURE_NOM = "nom";
    /**
     * Appel de création de la table playlist
     */
    final String CREATE_TABLE_LISTE_LECTURE = "CREATE TABLE " + TABLE_LISTE_LECTURE + " (" +
            LISTE_LECTURE_NOM + ", " + LISTE_LECTURE_PATH + " TEXT NOT NULL)";
    /**
     * Appel de la création de l'index path de la table des listes de lecture.
     */
    final String CREATE_PATH_INDEX_QUERY_PATH_LISTE = "CREATE INDEX " + LISTE_LECTURE_INDEX_PATH +
            " ON " + TABLE_LISTE_LECTURE + "(" + LISTE_LECTURE_PATH + ")";
    final String CREATE_PATH_INDEX_QUERY_NOM_LISTE = "CREATE INDEX " + INDEX_NOM_LISTE_LECTURE +
            " ON " + TABLE_LISTE_LECTURE + "(" + LISTE_LECTURE_NOM + ")";

}
