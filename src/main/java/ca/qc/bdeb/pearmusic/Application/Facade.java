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

import ca.qc.bdeb.pearmusic.Vue.LecteurMusiqueListener;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import org.tmatesoft.sqljet.core.SqlJetException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;


/**
 * La classe application fait le lien entre le coordonateur et les fonctionnalités offertes dans le package Application
 *
 * @author Dominic Cright
 */
public class Facade {
    /**
     * Lecteur de musique du coordonateur
     */
    private LecteurDeMusique lecteur;
    /**
     * Bibliothèque des fichiers mp3 de l'utilisateur
     */
    private Bibliotheque bibliotheque;
    /**
     * Objet liste de lecture permet de sauvegarder et de charger des listes de lecture .pear
     */
    private ListeDeLecture listeDeLecture;

    public Facade() throws MalformedURLException, URISyntaxException {
        lecteur = new LecteurDeMusique();
        bibliotheque = new Bibliotheque();
        listeDeLecture = new ListeDeLecture();
    }

    public boolean hasUneListeChansons() {
        return lecteur.hasUneListeInfosChansons();
    }

    public EtatRepetition getEtatRepetition() {
        return lecteur.getEtatRepetition();
    }

    public String[] getGenres() {
        return Genre.getGenres();
    }

    public String getGenre(int id) {
        return Genre.getGenre(id);
    }

    public int getIdGenre(String genre) {
        return Genre.getIDGenre(genre);
    }

    public void getAnneeChansonEnCours(String annee) {
        lecteur.setAnneeChanson(annee);
    }

    public int getIndexMusique() {
        return lecteur.getIndexMusique();
    }

    public ArrayList<ListeDeLectureDTO> getPlaylists() {
        return bibliotheque.obtenirPlaylists();
    }

    public EtatLecture getEtatDuLecteur() {
        return lecteur.getEtatLecture();
    }

    public ChansonDTO getChansonEnCours() {
        Chanson chanson = lecteur.getChanson();
        if (chanson != null) {
            return chanson.asDTO();
        }
        return null;
    }

    public void setChansonEnCours(int index) throws MalformedURLException {
        lecteur.setChansonEnCours(index);
    }

    public ArrayList<Chanson> getListeMusique() {
        return lecteur.getListeMusique();
    }

    public ArrayList<ChansonDTO> getListeMusiqueDTO() {
        return lecteur.getListeMusiqueDTO();
    }

    public ArrayList<ChansonDTO> getListeChansonsEnCours() {
        ArrayList<ChansonDTO> chansonDTOs = null;
        ArrayList<Chanson> chansons;

        chansons = lecteur.getListeMusique();

        if (chansons != null) {
            chansonDTOs = new ArrayList<>();

            for (Chanson chanson : chansons) {
                chansonDTOs.add(chanson.asDTO());
            }
        }
        return chansonDTOs;
    }

    public ArrayList<ChansonDTO> getChansonsDeLaBibliotheque() {
        return bibliotheque.obtenirChansons();
    }

    public String getTempsEcoule() {
        return lecteur.calculerTempsEcoule();
    }

    public double getPourcentageTempsEcoule() {
        return lecteur.calculerPourcentageTempsEcoule();
    }

    public void setRepeterListe() {
        lecteur.setRepeterListe();
    }

    public void setRepeterChanson() {
        lecteur.setRepeterChanson();
    }

    public void setRepeterArret() {
        lecteur.setRepeterArret();
    }

    public void setVolume(double volume) {
        lecteur.setVolume(volume);
    }

    public void setPisteChansonEnCours(String piste) {
        lecteur.setPisteChanson(piste);
    }

    public void setTitreChansonEnCours(String titre) {
        lecteur.setTitreChanson(titre);
    }

    public void setPathImage(String pathImage) {
        lecteur.setPathImage(pathImage);
    }

    public void setArtisteChansonEnCours(String artiste) {
        lecteur.setArtisteChanson(artiste);
    }

    public void setAlbumChansonEnCours(String album) {
        lecteur.setAlbumChanson(album);
    }

    public void setGenreChansonEnCours(String nouvelleValeur) {
        lecteur.setGenreChanson(getIdGenre(nouvelleValeur));
    }

    public void setLecteurMuetActif() {
        lecteur.setMuetOn();
    }

    public void setLecteurMuetInactif() {
        lecteur.setMuetOff();
    }

    public void setLectureAleatoireActif() {
        lecteur.setLectureAleatoireActif();
    }

    public void setLectureAleatoireInactif() {
        lecteur.setEcouteAleatoireInactif();
    }

    /**
     * Ajoute des écouteur sur le lecteur de musique pour connaître l'endroit où il se trouve
     * dans la liste de LECTURE.
     *
     * @param lecteurMusiqueListener
     */
    public void ajouterLecteurMusiqueListener(LecteurMusiqueListener lecteurMusiqueListener) {
        lecteur.ajouterLecteurMusiqueListener(lecteurMusiqueListener);
    }

    /**
     * Envoie l'instruction permettant la sauvegarde des informations des
     * chansons ajoutes par l'utilisateur
     * dans une ArrayList de Chanson
     */
    public void sauvegarderFichier(ArrayList<File> fichiers) throws IOException {
        bibliotheque.sauvegarderFichier(fichiers);
    }

    /**
     * Envoie l'instruction «Lecture» au lecteur de musique
     */
    public void jouerChanson() {
        lecteur.jouerChanson();
    }

    /**
     * Envoie l'instruction «Pause» au lecteur de musique
     */
    public void mettreSurPauseChanson() {
        lecteur.mettreSurPauseChanson();
    }

    /**
     * Envoie l'instruction «Arrêt» au lecteur de musique
     */
    public void arreterChanson() {
        lecteur.arreterChanson();
    }

    /**
     * Envoie l'instruction de passer à la chanson précédente de la liste de lecture
     *
     * @throws MalformedURLException
     */
    public void gererBoutonPrecendent() throws MalformedURLException {
        lecteur.gererChansonPrecedente();
    }

    /**
     * Envoie l'instruction au lecteur d'enlever la chanson dont l'indice est passe en parametre
     *
     * @param index L'index de la chanson à retirer.
     * @return Si la liste est vide après le retrait.
     * @throws MalformedURLException
     */
    public boolean enleverChanson(int index) throws MalformedURLException {
        return lecteur.gererSuppressionChanson(index);
    }

    /**
     * Envoie l'instruction au lecteur d'enlever une chansons à partir de son DTO.
     *
     * @param chansonDTO La chanson.
     * @return Si la liste est vide après le retrait.
     */
    public boolean enleverChanson(ChansonDTO chansonDTO) {
        return lecteur.enleverChanson(chansonDTO);
    }

    /**
     * Envoie l'instruction au lecteur d'enlever les chansons faisant partie d'un album.
     *
     * @return
     */
    public boolean enleverChansonsAlbum(String album) {
        return lecteur.enleverChansonsAlbum(album);
    }

    public boolean enleverChansonsArtiste(String artiste) {
        return lecteur.enleverChansonsArtiste(artiste);
    }

    /**
     * Méthode qui supprime une chanson de la Bibliothèque.
     *
     * @param path Le path de la chanson.
     */
    public void supprimerChanson(String path) {
        bibliotheque.deleteChanson(path);
    }

    /**
     * Envoie l'instruction au lecteur de passer à la chanson suivante.
     *
     * @throws MalformedURLException
     */
    public void passerChansonSuivante() throws MalformedURLException {
        lecteur.passerChansonSuivante();
    }

    /**
     * Envoie l'instruction au lecteur de monter la chanson selectionnée
     * dans la liste de chansons
     *
     * @param index index de la chanson à monter
     * @return un boolean pour voir si le déplacement a été effectué
     */
    public boolean monterChansonDansListe(int index) {
        return lecteur.monterChansonDansListe(index);
    }

    /**
     * Envoie l'instruction au lecteur de descendre la chanson selectionnée
     * dans la liste de chansons
     *
     * @param index index de la chanson à descendre
     * @return un boolean pour voir si le déplacement a été effectué
     */
    public boolean descendreChansonDansListe(int index) {
        return lecteur.descendreChansonDansListe(index);
    }

    /**
     * Méthode qui fait une avance rapide de la chanson actuellement jouée.
     */
    public void avancerRapideChanson() {
        lecteur.avancerRapideChanson();
    }

    /**
     * Méthode qui rebobine la chanson actuellement jouée
     */
    public void rembobinerChanson() {
        lecteur.rembobinerChanson();
    }

    /**
     * Si l'utilisateur modifie une information d'un fichier, on le met à jour
     *
     * @throws InvalidDataException
     * @throws IOException
     * @throws UnsupportedTagException
     */
    public void mettreAJourTraitementInfoDuFichierEnCours() throws InvalidDataException,
            IOException, UnsupportedTagException {
        mettreAJourChansonsBibliotheque();
    }

    /**
     * Modifie l'endroit où est rendu la LECTURE
     *
     * @param deplacement le nouvel emplacement en pourcentage (0.0 à 1.0)
     */
    public void modifierPositionChanson(double deplacement) {
        lecteur.modifierPositionChanson(deplacement);
    }

    /**
     * Méthode qui met à jour les chansons de la bibliothèque
     */
    private void mettreAJourChansonsBibliotheque() {
        bibliotheque.mettreAJourChansons(lecteur.getChanson());
    }

    /**
     * Méthode qui déplace les chansons dans la liste de lecture en cours.
     *
     * @param indexDebut
     * @param indexFin
     * @param direction
     */
    public void deplacerChansonDansListe(int indexDebut, int indexFin, int direction) {
        lecteur.deplacerChansonDansListe(indexDebut, indexFin, direction);
    }

    /**
     * Méthode qui insère une listeDeLecture dans la base de données
     *
     * @param nomEtcheminPlaylist Le nom et le chemin de la liste de lecture.
     * @throws SqlJetException
     */
    public void persisterPlaylist(String[] nomEtcheminPlaylist) throws SqlJetException {
        bibliotheque.insererPlaylist(nomEtcheminPlaylist);
    }

    /**
     * Méthode qui ajoute une liste de chansons à la lecture en cours.
     *
     * @param chanson
     */
    public void ajouterListeDeChansons(ArrayList<ChansonDTO> chanson) {
        lecteur.ajouterChansons(chanson);
    }

    /**
     * Méthode qui récupère les albums de la bibliothèque
     *
     * @return liste des albums
     * @throws IOException
     * @throws SqlJetException
     */
    public ArrayList<AlbumDTO> obtenirBibliothequeAlbums() {
        return bibliotheque.obtenirAlbums();
    }

    /**
     * Methode qui recupere les chansons d'un album
     *
     * @param nomAlbum
     */
    public ArrayList<ChansonDTO> obtenirChansonsAlbum(String nomAlbum) {
        return bibliotheque.obtenirChansonsAlbum(nomAlbum);
    }

    /**
     * Methode qui recupere les chansons d'un album
     *
     * @param nomArtiste
     */
    public ArrayList<ChansonDTO> obtenirChansonsArtiste(String nomArtiste) {
        return bibliotheque.obtenirChansonsArtiste(nomArtiste);
    }

    /**
     * Méthode qui récupère les artistes de la bibliothèque
     *
     * @return liste des albums
     * @throws IOException
     * @throws SqlJetException
     */
    public ArrayList<ChansonDTO> obtenirBibliothequeArtistes() {
        return bibliotheque.obtenirArtistes();
    }

    /**
     * Sauvegarde une liste de lecture avec son nom.
     *
     * @param nom Le nom de la liste de lecture.
     * @return Le path de la liste de lecture sauvegardé.
     */
    public String sauvegarderListeDeLecture(String nom) {
        String pathFichierSauvegarde = "";
        listeDeLecture = new ListeDeLecture(getListeMusiqueDTO());
        try {
            listeDeLecture.sauvegarderChemins(nom);
            pathFichierSauvegarde = listeDeLecture.getPath(nom);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return pathFichierSauvegarde;
    }

    /**
     * Charge un fichier listeDeLecture avec son nom.
     *
     * @param nom Le nom du fichier.
     * @return La liste de chansons dans la listeDeLecture.
     */
    public ArrayList<ChansonDTO> chargerPlaylist(String nom) throws IOException {
        listeDeLecture = new ListeDeLecture();
        return listeDeLecture.chargerChansons(nom);
    }

    /**
     * Permet de vider la liste du mediaPlayer
     */
    public void viderListeDeLectureEnCours() {
        lecteur.viderListeDeLecture();
    }

    /**
     * Appel le lecteur de musique pour connaître l'index d'une chanson
     *
     * @param chansonDTO La chanson à trouver.
     * @return son index, -1 si la chanson n'est pas dans la liste
     */
    public int trouverIndexDeLectureDUneChanson(ChansonDTO chansonDTO) {
        return lecteur.trouverIndexChanson(chansonDTO);
    }

    /**
     * Appel la bibliothèque pour qu'elle supprime un album de la BD.
     *
     * @param album Le nom de l'album.
     */
    public void supprimerAlbum(String album) {
        bibliotheque.deleteAlbum(album);
    }

    /**
     * Appel la bibliothèque pour qu'elle supprime un artiste de lecture de la BD.
     *
     * @param artiste Le nom de l'artiste.
     */
    public void supprimerArtiste(String artiste) {
        bibliotheque.deleteArtiste(artiste);
    }

    /**
     * Appel la bibliothèque pour qu'elle supprime une liste de lecture de la bibliothèque.
     *
     * @param path Le chemin de la listeDeLecture.
     */
    public void supprimerListeDeLecture(String path) {
        bibliotheque.deleteListeDeLecture(path);
    }

    /**
     * Appel de la bibliothèque pour qu'elle vérifie l'existance du chemin d'accès de la chanson
     *
     * @param chanson
     * @return
     */
    public boolean verifierCheminExistant(ChansonDTO chanson) {
        return bibliotheque.verifierCheminExistant(chanson);
    }

    /**
     * Appel la liste de lecture pour qu'elle ajoute une chanson à celle-ci.
     *
     * @param chanson  La chansons.
     * @param nomListe Le nom de la liste de lecture.
     */
    public void ajouterChansonPlaylist(ChansonDTO chanson, String nomListe) {
        listeDeLecture.ajouterChanons(chanson, nomListe);
    }
}
