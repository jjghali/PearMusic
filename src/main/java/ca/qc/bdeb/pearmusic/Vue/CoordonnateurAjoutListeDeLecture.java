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

import ca.qc.bdeb.pearmusic.Application.ChansonDTO;
import ca.qc.bdeb.pearmusic.Application.Facade;
import ca.qc.bdeb.pearmusic.Application.ListeDeLectureDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * Created by Patrick Nolin on 2014-12-05.
 * <p>
 * Coordonnateur pour la vue qui permet d'ajouter une chanson à une liste de lecture.
 */
public class CoordonnateurAjoutListeDeLecture extends Thread {

    /**
     * Combo box qui contient toutes les listes de lectures.
     */
    @FXML
    private ComboBox cboListesDeLecture;
    /**
     * Bouton qui confirme l'ajout.
     */
    @FXML
    private Button btnOk;
    /**
     * Listes de lecture de l'application.
     */
    private ObservableList<ListeDeLectureDTO> listesDeLecture;
    /**
     * La chanson à ajouter à une liste de lecture.
     */
    private ChansonDTO chanson;
    /**
     * La facade de l'application.
     */
    private Facade facade;


    public CoordonnateurAjoutListeDeLecture() {
    }

    /**
     * Initialise les données dont à besoin la vue.
     *
     * @param listesDeLecture Les listes de lecture dans l'application.
     * @param chanson         La chanson à ajouter.
     * @param facade          La facade pour pouvoir accèder à l'application.
     */
    void initialiserDonnes(ObservableList<ListeDeLectureDTO> listesDeLecture, ChansonDTO chanson, Facade facade) {
        this.listesDeLecture = listesDeLecture;
        this.chanson = chanson;
        this.facade = facade;
        populerComboBox();
        btnOk.setDisable(true);
        ajouterListenerComboBox();
    }

    /**
     * Popule le combo box avec la liste des listes de lecture.
     */
    private void populerComboBox() {
        cboListesDeLecture.getItems().clear();
        for (ListeDeLectureDTO listeDeLecture : listesDeLecture) {
            cboListesDeLecture.getItems().add(listeDeLecture.getNomListe());
        }
    }

    /**
     * Ajoute un listener de changement au combo box.
     */
    private void ajouterListenerComboBox() {
        cboListesDeLecture.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                btnOk.setDisable(false);
            }
        });
    }

    /**
     * Traitement d'ajout de la chanson dans la playlist.
     */
    @FXML
    protected void handleCliqueOk() {
        effectuerAjout();
        fermerFenetre();
    }

    /**
     * Annulation de l'ajout.
     */
    @FXML
    protected void handleCliqueAnnuler() {
        fermerFenetre();
    }

    /**
     * Permet à l'utilisateur d'appuyer sur enter pour annuler ou compléter l'ajout.
     *
     * @param keyEvent
     */
    @FXML
    protected void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case ENTER:
                if (!btnOk.isDisable()) {
                    effectuerAjout();
                    fermerFenetre();
                }
                fermerFenetre();
        }
    }

    /**
     * Méthode qui permet de faire disparaître la fenêtre
     */
    private void fermerFenetre() {
        Stage stage = (Stage) cboListesDeLecture.getScene().getWindow();
        stage.close();
    }

    /**
     * Appel la facade pour faire l'ajout de la chanson.
     */
    private void effectuerAjout() {
        String nomPlaylist = cboListesDeLecture.getSelectionModel().getSelectedItem().toString();
        facade.ajouterChansonPlaylist(chanson, nomPlaylist);
    }


}
