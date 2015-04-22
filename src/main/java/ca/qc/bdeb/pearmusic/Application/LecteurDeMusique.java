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

import ca.qc.bdeb.pearmusic.Vue.LecteurMusiqueEvent;
import ca.qc.bdeb.pearmusic.Vue.LecteurMusiqueListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Joshua Ghali
 * @since 29/08/2014
 * <p>
 * Cette classe contient le code des fonctionalités de base pour un lecteur de musique.
 */
class LecteurDeMusique implements ConstantesApplication {
    /**
     * Permet de connaître la position actuelle dans la liste de LECTURE
     */
    private int indexMusique;
    /**
     * Variable qui permet de mémoriser le volume choisi par l'utilisateur
     */
    private double volume = VOLUME_DEPART;
    /**
     * Si le lecteur est en muet.
     */
    private boolean muet;
    /**
     * Objet qui permet de lire les fichiers de musique
     */
    private MediaPlayer mediaPlayer;
    /**
     * Liste des fichiers musicaux
     */
    private ArrayList<Chanson> listeMusique;
    /**
     * Liste qui contient le nom des albums
     */
    private ArrayList<Album> listeAlbums;
    /**
     * Permet d'écouter quand le lecteur de musique change de chanson
     */
    private ArrayList<LecteurMusiqueListener> LecteurMusiqueListeners = new ArrayList<LecteurMusiqueListener>();
    /**
     * Objet qui gère l'aléatoire.
     */
    private Aleatoire aleatoire;
    /**
     * Etat de la repetition du lecteur de musique
     */
    private EtatRepetition etatRepetition;
    /**
     * Etat de lecture du lecteur de musique
     */
    private EtatLecture etatLecture;

    public LecteurDeMusique() throws URISyntaxException, MalformedURLException {
        aleatoire = new Aleatoire();
        listeMusique = new ArrayList<Chanson>();
        listeAlbums = new ArrayList<Album>();
        muet = false;
        etatRepetition = EtatRepetition.REPETER_DESACTIVE;
        etatLecture = EtatLecture.ARRET;
    }

    public EtatLecture getEtatLecture() {
        return etatLecture;
    }

    public EtatRepetition getEtatRepetition() {
        return etatRepetition;
    }

    public void setRepeterChanson() {
        etatRepetition = EtatRepetition.REPETER_CHANSON;
    }

    public void setRepeterListe() {
        etatRepetition = EtatRepetition.REPETER_LISTE;
    }

    public void setRepeterArret() {
        etatRepetition = EtatRepetition.REPETER_DESACTIVE;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public ArrayList<Chanson> getListeMusique() {
        return listeMusique;
    }

    public void setListeMusique(ArrayList<Chanson> listeMusique) {
        this.listeMusique = listeMusique;
    }

    public boolean hasUneListeInfosChansons() {
        return !listeMusique.isEmpty();
    }

    public int getIndexMusique() {
        return this.indexMusique;
    }

    public Aleatoire getAleatoire() {
        return aleatoire;
    }

    public void setLectureAleatoireActif() {
        aleatoire.demarrerAleatoire(listeMusique.size());
    }

    public void setEcouteAleatoireInactif() {
        aleatoire.arreterAleatoire();
    }

    public Duration getTempsCourant() {
        return mediaPlayer.getCurrentTime();
    }

    public String getMedia() {
        return mediaPlayer.getMedia().getSource();
    }

    /**
     * Méthode qui met le nouveau fichier musical dans un media pour le lire.
     *
     * @param fichierMusique Le fichier Le fichier contenant la chanson.
     * @throws MalformedURLException
     */
    public void setMedia(File fichierMusique) throws MalformedURLException {
        URL musique = fichierMusique.toURI().toURL();
        Media media = new Media(musique.toString());
        mediaPlayer = new MediaPlayer(media);
        jouerListeEcoute();
    }

    public Chanson getChanson() {
        if (listeMusique != null && listeMusique.size() > 0) {
            return listeMusique.get(indexMusique);
        }
        return null;
    }

    public void setPisteChanson(String piste) {
        getChanson().setPiste(piste);
    }

    public void setTitreChanson(String titre) {
        getChanson().setTitre(titre);
    }

    public void setPathImage(String pathImage) {
        getChanson().setPathImage(pathImage);
    }

    public void setArtisteChanson(String artiste) {
        getChanson().setArtiste(artiste);
    }

    public void setAlbumChanson(String album) {
        getChanson().setAlbum(album);
    }

    public void setAnneeChanson(String annee) {
        getChanson().setAnnee(annee);
    }

    public void setGenreChanson(int idGenre) {
        getChanson().setGenre(idGenre);
    }

    public void setMuetOn() {
        if (mediaPlayer != null) {
            muet = true;
            mediaPlayer.setMute(true);
        }
    }

    public void setMuetOff() {
        if (mediaPlayer != null) {
            muet = false;
            mediaPlayer.setMute(false);
        }
    }

    public ArrayList<ChansonDTO> getListeMusiqueDTO() {
        ArrayList<ChansonDTO> chansonsDTO = new ArrayList<ChansonDTO>();

        for (Chanson c : listeMusique) {
            chansonsDTO.add(c.asDTO());
        }
        return chansonsDTO;
    }

    /**
     * Méthode qui change la chanson en cours de LECTURE.
     *
     * @param index Index du fichier contenant la chanson dans la liste de fichiers.
     * @throws MalformedURLException
     */
    public void setChansonEnCours(int index) throws MalformedURLException {
        this.indexMusique = index;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            setMedia(new File(listeMusique.get(indexMusique).getPath()));
            jouerChanson();
        } else if (listeMusique.size() > 0) {
            setMedia(new File(listeMusique.get(indexMusique).getPath()));
            jouerChanson();
        }
    }

    /**
     * Méthode qui ajoute un listener au lecteur du musique.
     *
     * @param listener Le listener à ajouter.
     */
    public void ajouterLecteurMusiqueListener(LecteurMusiqueListener listener) {
        LecteurMusiqueListeners.add(listener);
    }

    /**
     * Méthode qui lit la chanson dans la liste
     */
    public void jouerChanson() {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
            mediaPlayer.play();
            etatLecture = EtatLecture.LECTURE;

            if (muet) {
                mediaPlayer.setMute(true);
            }
        }
    }

    /**
     * Méthode qui met la musique en PAUSE.
     */
    public void mettreSurPauseChanson() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            etatLecture = EtatLecture.PAUSE;
        }
    }

    /**
     * Méthode qui arrête la lecture et remmet la position à 0:00
     */
    public void arreterChanson() {
        if (mediaPlayer != null) {
            // Pause, sinon on ne peut plus ajuster de valeur de départ à une chanson chargé en mémoire.
            mediaPlayer.pause();
            modifierPositionChanson(0.0);
            etatLecture = EtatLecture.ARRET;
        }
    }

    /**
     * Méthode qui passe à la chanson suivante
     *
     * @throws MalformedURLException
     */
    public void passerChansonSuivante() throws MalformedURLException {
        if (mediaPlayer != null) {
            if (!aleatoire.estActif()) {
                selectionnerProchainIndex();
                this.setChansonEnCours(indexMusique);
            } else {
                setChansonEnCours(aleatoire.recupererProchainIndex());
            }
        }
    }

    /**
     * Méthode qui passe à la chanson précédente
     *
     * @throws MalformedURLException
     */
    public void passerChansonPrecedente() throws MalformedURLException {
        if (!aleatoire.estActif()) {
            selectionnerIndexPrecedent();
            this.setChansonEnCours(indexMusique);
        } else {
            setChansonEnCours(aleatoire.recupererIndexPrecedent());
        }
    }

    /**
     * Méthode qui recommence la chanson en cours de lecture
     *
     * @throws MalformedURLException
     */
    public void recommencerChanson() throws MalformedURLException {
        mediaPlayer.stop();
        setChansonEnCours(indexMusique);
    }

    /**
     * Méthode qui permet de jouer les chansons de la liste d'écoute
     * l'une à la suite de l'autre
     */
    public void jouerListeEcoute() {
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                if (etatRepetition != EtatRepetition.REPETER_CHANSON &&
                        ((etatRepetition == EtatRepetition.REPETER_LISTE &&
                                indexMusique == listeMusique.size() - 1) ||
                                indexMusique != listeMusique.size() - 1)) {
                    lancerChansonSuivanteEvent();
                } else if (etatRepetition == EtatRepetition.REPETER_CHANSON) {
                    try {
                        recommencerChanson();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * Méthode qui permet de monter la chanson dans la liste de chansons.
     *
     * @param index index de la chanson à déplacer
     * @return boolean si le déplacement a été effectué
     */
    public boolean monterChansonDansListe(int index) {
        boolean estMonter = false;
        if (index > 0) {
            Chanson courant = listeMusique.get(index);
            listeMusique.set(index, listeMusique.get(index - 1));
            listeMusique.set(index - 1, courant);
            estMonter = true;
        }
        return estMonter;
    }

    /**
     * Méthode qui permet de descendre la chanson dans la liste de chansons.
     *
     * @param index index de la chanson à déplacer
     * @return boolean si le déplacement a été effectu
     */
    public boolean descendreChansonDansListe(int index) {
        boolean estDescendu = false;
        if (index < listeMusique.size() - 1) {
            Chanson courant = listeMusique.get(index);
            listeMusique.set(index, listeMusique.get(index + 1));
            listeMusique.set(index + 1, courant);
            estDescendu = true;
        }
        return estDescendu;
    }

    /**
     * Méthode pour ajouter des chansons à la liste de LECTURE.
     *
     * @param fichiers Les chansons à ajouter.
     */
    public void ajouterChansons(ArrayList<ChansonDTO> fichiers) {
        int tailleDebut = listeMusique.size();
        ajouterChansonSansDuplication(fichiers);
        jouerSiNouvelleListeEcoute(tailleDebut);
    }

    /**
     * Méthode qui joue la premiere chanson s'il s'agit d'une nouvelle liste d'écoute
     *
     * @param tailleDebut
     */
    private void jouerSiNouvelleListeEcoute(int tailleDebut) {
        if (tailleDebut == 0) {
            try {
                setMedia(new File(listeMusique.get(indexMusique).getPath()));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            jouerChanson();
        }
    }

    /**
     * Méthode ajoutant les chansons dans la liste d'écoute s'il ne s'agit pas de duplication
     *
     * @param fichiers
     */
    private void ajouterChansonSansDuplication(ArrayList<ChansonDTO> fichiers) {
        boolean estDupliquee = false;
        for (ChansonDTO fichier : fichiers) {
            Chanson chanson = new Chanson(fichier);
            for (Chanson c : listeMusique) {
                if (chanson.getPath().equals(c.getPath())) {
                    estDupliquee = true;
                    break;
                }
            }
            if (!estDupliquee) {
                listeMusique.add(chanson);
            }
            estDupliquee = false;
        }
    }

    /**
     * Méthode qui permet de sélectionner l'index de la prochaine chanson
     */
    public void selectionnerProchainIndex() {
        if (indexMusique + 1 > listeMusique.size() - 1) {
            indexMusique = 0;
        } else {
            indexMusique++;
        }
    }

    /**
     * Méthode qui permet de sélectionner l'index de la chanson précédente
     */
    public void selectionnerIndexPrecedent() {
        System.out.println("nextindex");
        if (indexMusique - 1 < 0) {
            indexMusique = listeMusique.size() - 1;
        } else {
            indexMusique--;
        }
    }

    /**
     * Vide le mediaPlayer du lecteur.
     */
    public void viderLecteur() {
        mediaPlayer = null;
    }

    private synchronized void lancerChansonSuivanteEvent() {
        LecteurMusiqueEvent musicPlayerEvent = new LecteurMusiqueEvent(this);
        Iterator iterateurListeneur = LecteurMusiqueListeners.iterator();
        while (iterateurListeneur.hasNext()) {
            ((LecteurMusiqueListener) iterateurListeneur.next())
                    .prochaineChansonLancee(musicPlayerEvent);
        }
    }

    /**
     * Méthode qui retourne le temps MM:SS écoulé depuis le début de la LECTURE
     *
     * @return Une String MM:SS
     */
    public String calculerTempsEcoule() {
        if (mediaPlayer != null) {
            Duration mediaPlayerCurrentTime = mediaPlayer.getCurrentTime();
            String secondes;

            int min = 0;
            int sec = (int) mediaPlayerCurrentTime.toSeconds();

            if (sec >= CONVERSION_TEMPS_SOIXANTE) {
                min = sec / CONVERSION_TEMPS_SOIXANTE;
                sec = sec % CONVERSION_TEMPS_SOIXANTE;
            }

            if (sec < CONVERSION_TEMPS_DIX) {
                secondes = CONVERSION_TEMPS_ZERO + String.valueOf(sec);
            } else {
                secondes = String.valueOf(sec);
            }

            return min + SEPARATEUR_TEMPS + secondes;
        } else {
            return TEMPS_ZERO;
        }
    }

    /**
     * Méthode qui retourne le pourcentage de temps écoulé par rapport au temps total
     *
     * @return double entre 0.0 et 1.0
     */
    public double calculerPourcentageTempsEcoule() {
        if (mediaPlayer != null) {
            Duration joue = mediaPlayer.getCurrentTime();

            Duration total = mediaPlayer.getTotalDuration();

            double secondesJoue = (int) joue.toSeconds();
            int secondesTotal = (int) total.toSeconds();
            double pourcentage = 0.0;

            if (secondesJoue > 0) {
                pourcentage = secondesJoue / secondesTotal;
            }
            return pourcentage;
        } else {
            return 0.0;
        }
    }

    /**
     * Méthode qui modifie la position de LECTURE
     *
     * @param position nouvelle position entre 0.0 et 1.0
     */
    public void modifierPositionChanson(double position) {
        if (mediaPlayer != null) {
            Duration total = mediaPlayer.getTotalDuration();
            Duration nouveau = total.multiply(position);
            mediaPlayer.seek(nouveau);
        }
    }

    /**
     * Methode permettanat l'avance rapide de la chanson
     */
    public void avancerRapideChanson() {
        if (mediaPlayer != null) {
            mediaPlayer.seek(getTempsCourant().add(Duration.millis(TEMPS_AVANCE_REMBOBINAGE)));
        }
    }

    /**
     * Methode permettanat le rembobinage de la chanson
     */
    public void rembobinerChanson() {
        if (mediaPlayer != null) {
            mediaPlayer.seek(getTempsCourant().subtract(Duration.millis(TEMPS_AVANCE_REMBOBINAGE)));
        }
    }

    /**
     * Déplace la chanson à l'index sélectionné et décale les autres chansons selon la direction.
     * Si la direction est égale à 1, les chansons sont décalées vers le bas.
     * Si la direction est égale à -1, les chansons sont décalées vers le haut.
     *
     * @param indexMin
     * @param indexMax
     * @param direction
     */
    public void deplacerChansonDansListe(int indexMin, int indexMax, int direction) {
        int fin;
        Chanson temp;
        if (direction > 0) {
            fin = indexMin;
            temp = listeMusique.get(indexMax);
            decalerChansonsVersHaut(indexMax, indexMin);
        } else {
            fin = indexMax;
            temp = listeMusique.get(indexMin);
            decalerChansonsVersBas(indexMin, indexMax);
        }
        listeMusique.set(fin, temp);
    }

    /**
     * Permet le décalage des chansons vers le haut
     *
     * @param debut
     * @param fin
     */
    private void decalerChansonsVersHaut(int debut, int fin) {
        for (int i = debut; i > fin; i--) {
            listeMusique.set(i, listeMusique.get(i - 1));
        }
    }

    /**
     * Permet le décalage des chansons vers le bas
     *
     * @param debut
     * @param fin
     */
    private void decalerChansonsVersBas(int debut, int fin) {
        for (int i = debut; i < fin; i++) {
            listeMusique.set(i, listeMusique.get(i + 1));
        }
    }

    /**
     * Permet de passer a la chanson precedente dependant du mode du lecteur
     *
     * @throws MalformedURLException
     */
    public void gererChansonPrecedente() throws MalformedURLException {
        if (mediaPlayer != null) {
            int dureeCourante = (int) getTempsCourant().toSeconds();
            if (dureeCourante > SECONDE_MIN_CHANSON_PRECEDENTE) {
                recommencerChanson();
            } else {
                passerChansonPrecedente();
            }
        }
    }

    /**
     * Enleve une chanson de la liste avec son DTO.
     *
     * @param chansonDTO Le DTO.
     * @return Si la liste est vide après le retrait.
     */
    public boolean enleverChanson(ChansonDTO chansonDTO) {
        boolean listeVide = false;
        try {
            listeVide = gererSuppressionChanson(trouverIndexChanson(chansonDTO));
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }
        return listeVide;
    }

    /**
     * Gestion de la supression d'une chanson dans la liste et
     * vérifie si le mode aleatoire
     * a ete active.
     *
     * @param index
     * @return
     * @throws MalformedURLException
     */
    public boolean gererSuppressionChanson(int index) throws MalformedURLException {
        boolean listeVide = true;
        int indexEnCours = indexMusique;
        enleverChanson(index);
        if (listeMusique.size() > 0) {
            if (indexEnCours == listeMusique.size()) {
                setChansonEnCours(indexMusique - 1);
            } else {
                setChansonEnCours(indexMusique);
            }
            if (aleatoire.estActif()) {
                aleatoire.retirerChanson(index);
            }
            listeVide = false;
        } else {
            viderLecteur();
        }
        return listeVide;
    }

    /**
     * Méthode pour enlever des chansons à la liste de LECTURE.
     *
     * @param index L'index où se trouve la chanson dans la liste.
     */
    public void enleverChanson(int index) {
        mediaPlayer.stop();
        listeMusique.remove(index);
    }

    /**
     * Méthode qui enlève les chansons d'un album.
     *
     * @param album L'album.
     * @return Si la liste est vide après le retrait des chansons.
     */
    public boolean enleverChansonsAlbum(String album) {
        boolean listeVide = false;
        ArrayList<Integer> listeIndexs = trouverChansonsAlbum(album);
        for (Integer index : listeIndexs) {
            try {
                listeVide = gererSuppressionChanson(index);
                decalerIndexs(listeIndexs);
            } catch (MalformedURLException e) {
                System.out.println(e.getMessage());
            }
        }
        return listeVide;
    }

    /**
     * Enleve les chansons de la liste de lecture qui sont d'un artiste.
     *
     * @param artiste L'artiste.
     * @return Si la liste est vide après le retrait des chansons.
     */
    public boolean enleverChansonsArtiste(String artiste) {
        boolean listeVide = false;
        ArrayList<Integer> listeIndexs = trouverChansonsArtiste(artiste);
        for (Integer index : listeIndexs) {
            try {
                listeVide = gererSuppressionChanson(index);
                decalerIndexs(listeIndexs);
            } catch (MalformedURLException e) {
                System.out.println(e.getMessage());
            }
        }
        return listeVide;
    }

    /**
     * Méthode qui réduit de 1 les index dans une liste d'indexs.
     *
     * @param indexs La liste d'indexs.
     */
    private void decalerIndexs(ArrayList<Integer> indexs) {
        for (int i = 0; i < indexs.size(); ++i) {
            indexs.set(i, indexs.get(i) - 1);
        }
    }

    /**
     * Vide la liste de lecture.
     */
    public void viderListeDeLecture() {
        indexMusique = INDICE_PREMIERE_CHANSON;
        listeMusique.clear();
        mediaPlayer.stop();
        viderLecteur();
    }

    /**
     * Methode modifiant le volume du media Player
     * La valeur du volume peut etre entre 0.0 et 1.0.
     *
     * @param volume
     */
    public void setVolume(double volume) {
        this.volume = volume;
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume);
        }
    }

    /**
     * Permet de savoir si une chanson est dans la liste de lecture en cours.
     *
     * @param chansonDTO La chanson à trouver.
     * @return Index de la chanson.
     */
    public int trouverIndexChanson(ChansonDTO chansonDTO) {
        int index = 0;
        for (Chanson chanson : listeMusique) {
            if (chanson.asDTO().equals(chansonDTO)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    /**
     * Trouves les chansons qui font partis d'un album.
     *
     * @param album L'album.
     * @return Les indexs des chansons dans la liste.
     */
    private ArrayList<Integer> trouverChansonsAlbum(String album) {
        ArrayList<Integer> listeIndexs = new ArrayList<Integer>();
        int index = 0;
        for (Chanson chanson : listeMusique) {
            if (chanson.getAlbum().equals(album)) {
                listeIndexs.add(index);
            }
            index++;
        }
        return listeIndexs;
    }

    /**
     * Trouve les chansons d'un artiste.
     *
     * @param artiste L'artiste.
     * @return Les indexs des chansons dans la liste.
     */
    private ArrayList<Integer> trouverChansonsArtiste(String artiste) {
        ArrayList<Integer> listeIndexs = new ArrayList<Integer>();
        int index = 0;
        for (Chanson chanson : listeMusique) {
            if (chanson.getArtiste().equals(artiste)) {
                listeIndexs.add(index);
            }
            index++;
        }
        return listeIndexs;
    }

}
