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

import com.mpatric.mp3agic.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Classe contenant toutes les informations d'une chanson ainsi que les traitements individuels fait sur chaque
 * chanson à la sauvegarde où pendant la lecture.
 *
 * @author Joshua Ghali, Merge de l'autre classe par Patrick Nolin.
 */
class Chanson implements ConstantesApplication {
    /**
     * Chemin d'accès au fichier de la chanson.
     */
    private String pathFichier;
    /**
     * Version 2 du type de métadonnées du fichier
     */
    private ID3v2 id3v2;
    /**
     * Version 1 du type de métadonnées du fichier
     */
    private ID3v1 id3v1;
    /**
     * Fichier Mp3 en lecture
     */
    private Mp3File mp3FileLecture;
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
     * Durée de la chanson
     */
    private long duree;
    /**
     * Pochette du disque
     */
    private Image albumArt;
    /**
     * Chemin d'accès à la nouvelle image, choisie par l'utilisateur
     */
    private String pathImage;

    public Chanson(String path) throws IOException {
        this.pathFichier = path;
        try {
            mp3FileLecture = new Mp3File(path);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedTagException e) {
            e.printStackTrace();
        } catch (InvalidDataException e) {
            e.printStackTrace();
        }
        remplirInfoChanson(mp3FileLecture.getLengthInSeconds());
    }

    /**
     * Constructeur paramétrique qui met en mémoire les info contenues dans le fichier mp3
     *
     * @param artiste
     * @param titre
     * @param piste
     * @param album
     * @param genre
     * @param annee
     */
    public Chanson(String path, String artiste, String titre, String piste, String album, int genre, String annee, long length, javafx.scene.image.Image albumArt) {
        setPath(path);
        setArtiste(artiste);
        setTitre(titre);
        setPiste(piste);
        setAlbum(album);
        setGenre(genre);
        setAnnee(annee);
        setDuree(length);
        setAlbumArt(albumArt);
    }

    /**
     * Constructeur d'une chanson a partir des infos contenues dans la BD
     *
     * @param dto
     */
    public Chanson(ChansonDTO dto) {
        try {
            mp3FileLecture = new Mp3File(dto.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedTagException e) {
            e.printStackTrace();
        } catch (InvalidDataException e) {
            e.printStackTrace();
        }
        remplirInfoChanson(dto);
    }

    boolean estID3v2() {
        return mp3FileLecture.hasId3v2Tag();
    }

    boolean estID3v1() {
        return mp3FileLecture.hasId3v1Tag();
    }

    public String getPath() {
        return pathFichier;
    }

    public void setPath(String path) {
        this.pathFichier = path;
    }

    public String getPiste() {
        return piste;
    }

    public void setPiste(String piste) {
        if (piste == null || piste.trim().length() == 0) {
            this.piste = "#?";
        } else {
            this.piste = piste;
        }
    }

    public String getArtiste() {
        return artiste;
    }

    public void setArtiste(String artiste) {
        if (artiste == null || artiste.trim().length() == 0) {
            this.artiste = ARTISTE + ' ' + INCONNU;
        } else {
            this.artiste = artiste;
        }
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        if (titre == null || titre.trim().length() == 0) {
            this.titre = TITRE + ' ' + INCONNU;
        } else {
            this.titre = titre;
        }
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        if (album == null || album.trim().length() == 0) {
            this.album = ALBUM + ' ' + INCONNU;
        } else {
            this.album = album;
        }
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        if (annee == null || annee.trim().length() == 0) {
            this.annee = ANNEE + ' ' + INCONNU + 'e';
        } else {
            this.annee = annee;
        }
    }

    public long getDuree() {
        return duree;
    }

    public void setDuree(long duree) {
        this.duree = duree;
    }

    public int getGenre() {
        return genre;
    }

    public void setGenre(int genre) {
        this.genre = genre;
    }

    public String getGenreString() {
        return Genre.getGenre(genre);
    }

    public javafx.scene.image.Image getAlbumArt() {
        return this.albumArt;
    }

    public void setAlbumArt(javafx.scene.image.Image albumArt) {
        if (albumArt == null) {
            this.albumArt = IMAGE_ALBUM_DEFAUT;
        } else {
            this.albumArt = albumArt;
        }
    }

    /**
     * Méthode qui permet à l'utilisateur de modifier la pochette de l'album
     *
     * @param pathImage
     */
    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
        Image image;
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(pathImage));
            image = SwingFXUtils.toFXImage(bufferedImage, null);
            setAlbumArt(image);
        } catch (IOException ex) {
            System.out.println("Erreur au moment de choisir l'image");
        }
    }

    /**
     * Méthode qui transforme la durée (long) en une String HH:MM:SS
     *
     * @return durée du fichier HH:MM:SS ou MM:SS (si moins d'une heure)
     */
    public String getDureeString() {
        long heure = TimeUnit.SECONDS.toHours(duree);
        long minute = TimeUnit.SECONDS.toMinutes(duree) - (TimeUnit.SECONDS.toHours(duree) * 60);
        long seconde = TimeUnit.SECONDS.toSeconds(duree) - (TimeUnit.SECONDS.toMinutes(duree) * 60);
        String sHeure, sMinute, sSeconde;
        String[] sDuree = convertirDureeEnString(heure, minute, seconde);

        sHeure = sDuree[0];
        sMinute = sDuree[1];
        sSeconde = sDuree[2];

        // Retourne une String HH:MM:SS si c'est plus long que 59:59
        if (heure > 0) {
            return sHeure + SEPARATEUR_TEMPS + sMinute + SEPARATEUR_TEMPS + sSeconde;
        }
        // Sinon retourne MM:SS
        else {
            return sMinute + SEPARATEUR_TEMPS + sSeconde;
        }
    }

    /**
     * Méthode qui transforme la Chanson en DTO
     *
     * @return un DTO de l'objet
     */
    ChansonDTO asDTO() {
        ChansonDTO dto = new ChansonDTO();

        dto.setPath(this.getPath());
        dto.setTitre(this.getTitre());
        dto.setArtiste(this.getArtiste());
        dto.setAlbum(this.getAlbum());
        dto.setPiste(this.getPiste());
        dto.setAnnee(this.getAnnee());
        dto.setGenre(this.getGenre());
        dto.setDuree(this.getDuree());
        dto.setPathImage(this.pathImage);
        dto.setDureeString(this.getDureeString());
        dto.setGenreString(this.getGenreString());
        dto.setAlbumArt(this.getAlbumArt());
        return dto;
    }

    /**
     * Méthode qui permet de remplir les informations dans Chanson
     * selon la version du type de métadonnées du fichier
     *
     * @param length
     */
    private void remplirInfoChanson(long length) throws IOException {
        if (estID3v2()) {
            id3v2 = mp3FileLecture.getId3v2Tag();
            setPath(pathFichier);
            setArtiste(id3v2.getArtist());
            setTitre(id3v2.getTitle());
            setPiste(id3v2.getTrack());
            setAlbum(id3v2.getAlbum());
            setGenre(id3v2.getGenre());
            setAnnee(id3v2.getYear());
            setDuree(length);
            setAlbumArt(convertirImage(id3v2.getAlbumImage()));
        } else if (estID3v1()) {
            id3v1 = mp3FileLecture.getId3v1Tag();
            setPath(pathFichier);
            setArtiste(id3v1.getArtist());
            setTitre(id3v1.getTitle());
            setPiste(id3v1.getTrack());
            setAlbum(id3v1.getAlbum());
            setGenre(id3v1.getGenre());
            setAnnee(id3v1.getYear());
            setDuree(length);
            setAlbumArt(null);
        }
    }

    /**
     * Méthode qui permet de remplir les informations dans Chanson
     * selon les données sauvegardées dans la BD
     *
     * @param dto
     */
    private void remplirInfoChanson(ChansonDTO dto) {
        setPath(dto.getPath());
        setArtiste(dto.getArtiste());
        setTitre(dto.getTitre());
        setPiste(dto.getPiste());
        setAlbum(dto.getAlbum());
        setGenre(dto.getGenre());
        setAnnee(dto.getAnnee());
        setDuree(dto.getDuree());
        if (dto.getPathImage() != null) {
            setPathImage(dto.getPathImage());
        } else {
            if (estID3v2()) {
                id3v2 = mp3FileLecture.getId3v2Tag();
                try {
                    setAlbumArt(convertirImage(id3v2.getAlbumImage()));
                } catch (IOException e) {
                    System.out.println("Erreur : id3v2.getAlbumImage() for file : " + mp3FileLecture.getFilename());
                    e.printStackTrace();
                }
            } else
                setAlbumArt(null);
        }
    }

    /**
     * Permet de convertir un tableau de byte en une image.
     *
     * @param bytes Le tableau de bytes.
     * @return L'image
     * @throws IOException
     */
    public javafx.scene.image.Image convertirImage(byte[] bytes) throws IOException {
        javafx.scene.image.Image imagefx = null;
        if (bytes != null) {
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            BufferedImage image = ImageIO.read(bais);
            imagefx = SwingFXUtils.toFXImage(image, null);
        }

        return imagefx;
    }

    /**
     * Methode qui permet de convertir une duree en string
     *
     * @param hours
     * @param minute
     * @param second
     * @return sDuree
     */
    private String[] convertirDureeEnString(long hours, long minute, long second) {
        String[] sDuree = new String[3];

        sDuree[0] = String.valueOf(hours);
        if (hours > 0 && minute < CONVERSION_TEMPS_DIX) {
            sDuree[1] = CONVERSION_TEMPS_ZERO + String.valueOf(minute);
        } else {
            sDuree[1] = String.valueOf(minute);
        }
        if (second < CONVERSION_TEMPS_DIX) {
            sDuree[2] = CONVERSION_TEMPS_ZERO + String.valueOf(second);
        } else {
            sDuree[2] = String.valueOf(second);
        }
        return sDuree;
    }

}