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

import ca.qc.bdeb.pearmusic.AccesPersistance.DBHelper;
import org.tmatesoft.sqljet.core.SqlJetException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe qui permet la persistance des fichiers mp3 de l'utilisateur afin de
 * l'afficher dans une bibliothèque.
 * <p>
 * Created by Stu Ureta on 2014-10-28.
 */
class Bibliotheque {
    /**
     * La bibliothèque garde en mémoire ses informations à l'aide d'une Base de donées
     */
    private final DBHelper DB_HELPER;

    public Bibliotheque() {
        DB_HELPER = DBHelper.getInstance();
    }

    /**
     * Sauvegarde les informations des fichiers musicaux ajoutées par l'utilisateur
     *
     * @param fichiers la liste des fichiers ajoutés à la bibliothèque
     * @throws IOException
     */
    public void sauvegarderFichier(ArrayList<File> fichiers) throws IOException {
        for (File fichier : fichiers) {
            Chanson chanson = new Chanson(fichier.getPath());
            ajouterChanson(chanson.asDTO());
        }
    }

    /**
     * Méthode qui ajoute une chanson à la bibliothèque à partir de son DTO
     *
     * @param chansonDTO
     */
    public void ajouterChanson(ChansonDTO chansonDTO) {
        try {
            DB_HELPER.ajouterChanson(chansonDTO);
        } catch (SqlJetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode qui retourne tous les fichiers contenus dans la bibliotheque
     *
     * @return DTO : une ArrayList<ChansonDTO>
     */
    public ArrayList<ChansonDTO> obtenirChansons() {
        ArrayList<ChansonDTO> DTO = null;
        try {
            DTO = DB_HELPER.getChansonsBibliotheque();
        } catch (SqlJetException e) {
            System.out.println(e.getMessage());
        }
        return DTO;
    }

    /**
     * Méthode qui retourne toutes les chansons d'un album en String
     *
     * @param album Le nom de l'album voulu
     * @return une liste des chemins d'accès
     */
    public ArrayList<String> obtenirChansonsStringAlbums(String album) {
        return DB_HELPER.getChansonsAlbums(album);
    }

    /**
     * Méthode qui retourne toutes les chansons d'un artiste en String
     *
     * @param artiste : l'artiste recherché
     * @return une liste des chemins d'accès
     */
    public ArrayList<String> obtenirChansonsStringArtiste(String artiste) {
        return DB_HELPER.getChansonsArtiste(artiste);
    }

    /**
     * Methode qui recupere les chansons d'un artiste en ChansonDTO
     *
     * @param nomArtiste
     * @return
     */
    public ArrayList<ChansonDTO> obtenirChansonsArtiste(String nomArtiste) {
        try {
            return DB_HELPER.getChansonsArtisteBibliotheque(nomArtiste);
        } catch (SqlJetException e) {
            e.printStackTrace();
        }
        return new ArrayList<ChansonDTO>();
    }

    /**
     * Méthode qui récupère tous les artistes de la bibliothèque de l'utilisateur
     *
     * @return liste de String
     */
    public ArrayList<ChansonDTO> obtenirArtistes() {
        ArrayList<ChansonDTO> DTO = null;
        try {
            DTO = DB_HELPER.getChansonsBibliotheque();
        } catch (SqlJetException e) {
            System.out.println(e.getMessage());
        }
        ArrayList<ChansonDTO> artistes = new ArrayList<ChansonDTO>();

        for (ChansonDTO dto : DTO) {
            if (!artisteExisteDansListeArtiste(dto.getArtiste(), artistes)) {
                artistes.add(dto);
            }
        }
        return artistes;
    }

    /**
     * Méthode qui permet de classer les chansons de la base de données par album
     *
     * @return
     * @throws SqlJetException
     * @throws IOException
     */
    public ArrayList<AlbumDTO> obtenirAlbums() {
        Album album;
        ArrayList<ChansonDTO> DTO = null;
        try {
            DTO = DB_HELPER.getChansonsBibliotheque();
        } catch (SqlJetException e) {
            System.out.println(e.getMessage());
        }
        ArrayList<AlbumDTO> albumsAAfficher = new ArrayList<>();

        for (ChansonDTO dto : DTO) {
            if (!albumExisteDansListeAlbum(dto.getAlbum(), albumsAAfficher)) {
                File temp = new File(dto.getPath());

                if (temp.exists()) {
                    Chanson chanson = new Chanson(dto);
                    album = new Album(chanson.getAlbumArt(), chanson.getAlbum(), chanson.getArtiste(), chanson.getAnnee());
                    albumsAAfficher.add(new AlbumDTO(album));
                } else {
                    try {
                        DB_HELPER.deleteChanson(dto.getPath());
                    } catch (SqlJetException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
        return albumsAAfficher;
    }

    /**
     * Methode qui recupere les chansons d'un album
     *
     * @param nomAlbum
     */
    public ArrayList<ChansonDTO> obtenirChansonsAlbum(String nomAlbum) {
        try {
            return DB_HELPER.getChansonsAlbumBibliotheque(nomAlbum);
        } catch (SqlJetException e) {
            e.printStackTrace();
        }
        return new ArrayList<ChansonDTO>();
    }


    /**
     * Methode qui verifie si l'album existe deja dans la liste des albums
     *
     * @param nomAlbum
     * @return existe
     */
    private boolean albumExisteDansListeAlbum(String nomAlbum, ArrayList<AlbumDTO> albumsAAfficher) {
        for (AlbumDTO info : albumsAAfficher) {
            if (info.getAlbum().equals(nomAlbum)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Methode qui verifie si l'artiste existe deja dans la liste des artistes
     *
     * @param nomArtiste
     * @return existe
     */
    private boolean artisteExisteDansListeArtiste(String nomArtiste, ArrayList<ChansonDTO> artistesAAfficher) {
        for (ChansonDTO info : artistesAAfficher) {
            if (info.getArtiste().equals(nomArtiste)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Méthode qui met a jour les informations d'une chanson si elles ont été changées sur l'interface
     *
     * @param info chanson modifie dans la liste de lecture
     */
    public void mettreAJourChanson(ChansonDTO info) {
        try {
            DB_HELPER.updateMusique(info);
        } catch (SqlJetException e) {
            System.out.println("Methode mettreAJourChanson de la Bibliotheque a généré une erreur : " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Methode mettreAJourChanson de la Bibliotheque a généré une erreur : " + e.getMessage());
        }
    }

    /**
     * Méthode qui efface une chanson sur la bd a l'aide de son chemin d'accès
     *
     * @param path chemin d'accès du fichier à retirer de la bibliothèque
     */
    public void deleteChanson(String path) {
        try {
            DB_HELPER.deleteChanson(path);
        } catch (SqlJetException e) {
            System.out.println("Methode deleteChanson de la Bibliotheque a généré une erreur : " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Methode deleteChanson de la Bibliotheque a généré une erreur : " + e.getMessage());
        }
    }

    /**
     * Méthode qui efface un album de la BD à l'aide de son nom.
     *
     * @param album Le nom de l'album.
     */
    public void deleteAlbum(String album) {
        try {
            DB_HELPER.deleteAlbum(album);
        } catch (SqlJetException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Méthode qui efface un artiste de la BD.
     *
     * @param artiste Le nom de l'artiste.
     */
    public void deleteArtiste(String artiste) {
        try {
            DB_HELPER.deleteArtiste(artiste);
        } catch (SqlJetException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Méthode qui efface une liste de lecture de la BD.
     *
     * @param path Le chemin de la liste de lecture.
     */
    public void deleteListeDeLecture(String path) {
        try {
            DB_HELPER.deleteListeDeLecture(path);
        } catch (SqlJetException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Méthode qui met à jour les chansons de la bibliothèque
     *
     * @param chanson
     */
    public void mettreAJourChansons(Chanson chanson) {
        ChansonDTO info = chanson.asDTO();
        mettreAJourChanson(info);
    }

    /**
     * Méthode qui ajoute une playlist à la bibliothèque
     *
     * @param nomEtCheminPlaylist
     */
    public void insererPlaylist(String[] nomEtCheminPlaylist) {
        try {
            DB_HELPER.insererListeDeLecture(nomEtCheminPlaylist[0], nomEtCheminPlaylist[1]);
        } catch (SqlJetException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Méthode qui permet d'obtenir les playlists enregistré par l'utilisateur
     *
     * @return
     */
    public ArrayList<ListeDeLectureDTO> obtenirPlaylists() {
        return DB_HELPER.getListeDeLecture();
    }

    /**
     * Méthode qui vérifie si le chemin d'accès existe. Si celui-ci n'existe pas la chanson sera retirée
     * de la base de données
     *
     * @param chanson
     * @return
     */
    public boolean verifierCheminExistant(ChansonDTO chanson) {
        boolean cheminExistant = true;
        File temp = new File(chanson.getPath());
        if (!temp.exists()) {
            deleteChanson(chanson.getPath());
            cheminExistant = false;
        }
        return cheminExistant;
    }

}
