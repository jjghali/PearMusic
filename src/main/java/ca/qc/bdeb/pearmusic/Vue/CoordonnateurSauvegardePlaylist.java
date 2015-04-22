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
package ca.qc.bdeb.pearmusic.Vue;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * **
 * Créé par Patrick Nolin le 2014-10-30.
 * <p>
 * Coordonnateur pour la fenêtre de sauvegarde de liste de lecture.
 */
public class CoordonnateurSauvegardePlaylist extends Thread {
    /**
     * Champs de texte qui permet à l'utilisateur de choisir le nom de sa liste de lecture
     */
    @FXML
    public TextField txtNom;
    /**
     * Indique si la sauvegarde a été annulée ou non.
     */
    private boolean annuler;
    /**
     * Permet d'écouter le coordonnateur.
     */
    private ArrayList<SauvegardePlaylistListener> sauvegardePlaylistListeners = new ArrayList<SauvegardePlaylistListener>();

    public CoordonnateurSauvegardePlaylist() {
        annuler = false;
    }

    private String getTxtNom() {
        String nom = txtNom.getText();
        ;
        if (!annuler) {
            return nom;
        } else {
            return null;
        }
    }

    public void ajouterSauvegardePlaylistListener(SauvegardePlaylistListener listener) {
        sauvegardePlaylistListeners.add(listener);
    }

    public void enleverLecteurMusiqueListener(SauvegardePlaylistListener listener) {
        sauvegardePlaylistListeners.remove(listener);
    }

    /**
     * Traitement fait lorsque l'utilisateur sauvegarde une liste de lecture
     *
     * @param mouseEvent
     * @throws FileNotFoundException
     */
    @FXML
    protected void handleSauvegarderPlaylistAction(MouseEvent mouseEvent) throws FileNotFoundException {
        if (verifierSaisie()) {
            fermerFenetre();
            lancerSauvegardePlaylistEvent();
        }
    }

    /**
     * Traitement fait lorsque l'utilisateur annule une sauvegarde
     *
     * @param mouseEvent
     */
    @FXML
    protected void handleAnnulerAction(MouseEvent mouseEvent) {
        fermerFenetre();
    }

    /**
     * Permet à l'utilisateur d'appuyer sur Enter pour faire sa sauvegarde.
     *
     * @param keyEvent
     */
    @FXML
    protected void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case ENTER:
                if (verifierSaisie()) {
                    fermerFenetre();
                    lancerSauvegardePlaylistEvent();
                }
        }
    }

    /**
     * Méthode qui s'assure que l'utilisateur a entrée au moins un caractère
     *
     * @return
     */
    private boolean verifierSaisie() {
        if (txtNom.getText().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Méthode qui permet de faire disparaître la fenêtre
     */
    private void fermerFenetre() {
        Stage stage = (Stage) txtNom.getScene().getWindow();
        stage.close();
    }

    /**
     * Méthode qui permet de lancer la sauvegarde threadsafe
     */
    private synchronized void lancerSauvegardePlaylistEvent() {
        SauvegardePlaylistEvent event = new SauvegardePlaylistEvent(this);
        Iterator iterateurListeneur = sauvegardePlaylistListeners.iterator();
        while (iterateurListeneur.hasNext()) {
            ((SauvegardePlaylistListener) iterateurListeneur.next())
                    .sauvegardeDePlaylist(event, getTxtNom());
        }
    }

}
