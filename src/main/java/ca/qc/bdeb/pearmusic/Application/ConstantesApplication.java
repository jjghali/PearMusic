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

import java.io.File;

/**
 * Interface qui permet aux classe du paquet Application de partager les mêmes constantes
 * <p>
 * Created by May on 09/11/2014.
 */
interface ConstantesApplication {
    /**
     * Dossier où les playlist sont conservées.
     */
    static final String FICHIER_DEFAULT = File.separator + "Pear_Music" + File.separator;
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
     * Volume de départ
     */
    static final double VOLUME_DEPART = 1.0;
    /**
     * Lorsque le fichier ne contient pas l'information
     */
    static final String INCONNU = "inconnu";
    /**
     * Extension des fichiers de playlist.
     */
    static final String EXTENSION = ".pear";
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
    static final String GENRE_INCONNU = "Genre inconnu";
    /**
     * Image par defaut si l'album n'existe pas
     */
    static final Image IMAGE_ALBUM_DEFAUT = new Image("coverUnknown.png");
    /**
     * Chiffre pour verifier la conversion en heures, minutes ou secondes
     */
    static final int CONVERSION_TEMPS_SOIXANTE = 60;
    /**
     * Chiffre pour verifier si le temps atteint la dixaine d'unites
     */
    static final int CONVERSION_TEMPS_DIX = 10;
    /**
     * Chiffre pour la conversion de temps dans le cas ou le temps n'atteint pas la
     * dixaine d'unites
     */
    static final int CONVERSION_TEMPS_ZERO = 0;
    /**
     * Chaine de caratère du temps lorsqu'il est à zéro
     */
    static final String TEMPS_ZERO = "0:00";
    /**
     * Séparateur de temps pour distinguer les heures, les minutes et les secondes
     */
    static final String SEPARATEUR_TEMPS = ":";
    /**
     * Nombre de secondes minimales pour déterminer si la chanson doit être recommencée
     * ou pour passer à la chanson précédente
     */
    static final int SECONDE_MIN_CHANSON_PRECEDENTE = 2;
    /**
     * Tableau contenant les 147 styles musicaux
     */
    static final String[] TYPE_GENRE = {
            "Blues",
            "Classic Rock",
            "Country",
            "Dance",
            "Disco",
            "Funk",
            "Grunge",
            "Hip-Hop",
            "Jazz",
            "Metal",
            "New Age",
            "Oldies",
            "Other",
            "Pop",
            "R&B",
            "Rap",
            "Reggae",
            "Rock",
            "Techno",
            "Industrial",
            "Alternative",
            "Ska",
            "Death Metal",
            "Pranks",
            "Soundtrack",
            "Euro-Techno",
            "Ambient",
            "Trip-Hop",
            "Vocal",
            "Jazz+Funk",
            "Fusion",
            "Trance",
            "Classical",
            "Instrumental",
            "Acid",
            "House",
            "Game",
            "Sound Clip",
            "Gospel",
            "Noise",
            "Alternative Rock",
            "Bass",
            "Soul",
            "Punk",
            "Space",
            "Meditative",
            "Instrumental Pop",
            "Instrumental Rock",
            "Ethnic",
            "Gothic",
            "Darkwave",
            "Techno-Industrial",
            "Electronic",
            "Pop-Folk",
            "Eurodance",
            "Dream",
            "Southern Rock",
            "Comedy",
            "Cult",
            "Gangsta",
            "Top 40",
            "Christian Rap",
            "Pop/Funk",
            "Jungle",
            "Native US",
            "Cabaret",
            "New Wave",
            "Psychadelic",
            "Rave",
            "Showtunes",
            "Trailer",
            "Lo-Fi",
            "Tribal",
            "Acid Punk",
            "Acid Jazz",
            "Polka",
            "Retro",
            "Musical",
            "Rock & Roll",
            "Hard Rock",
            "Folk",
            "Folk-Rock",
            "National Folk",
            "Swing",
            "Fast Fusion",
            "Bebob",
            "Latin",
            "Revival",
            "Celtic",
            "Bluegrass",
            "Avantgarde",
            "Gothic Rock",
            "Progressive Rock",
            "Psychedelic Rock",
            "Symphonic Rock",
            "Slow Rock",
            "Big Band",
            "Chorus",
            "Easy Listening",
            "Acoustic",
            "Humour",
            "Speech",
            "Chanson",
            "Opera",
            "Chamber Music",
            "Sonata",
            "Symphony",
            "Booty Bass",
            "Primus",
            "Porn Groove",
            "Satire",
            "Slow Jam",
            "Club",
            "Tango",
            "Samba",
            "Folklore",
            "Ballad",
            "Power Ballad",
            "Rhytmic Soul",
            "Freestyle",
            "Duet",
            "Punk Rock",
            "Drum Solo",
            "Acapella",
            "Euro-House",
            "Dance Hall",
            "Goa",
            "Drum & Bass",
            "Club-House",
            "Hardcore",
            "Terror",
            "Indie",
            "BritPop",
            "Negerpunk",
            "Polsk Punk",
            "Beat",
            "Christian Gangsta",
            "Heavy Metal",
            "Black Metal",
            "Crossover",
            "Contemporary C",
            "Christian Rock",
            "Merengue",
            "Salsa",
            "Thrash Metal",
            "Anime",
            "JPop",
            "SynthPop",
    };
}
