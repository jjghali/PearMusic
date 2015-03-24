package ca.qc.bdeb.pearmusic.Vue;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Fenêtre de sélection de fichiers, pour ajouter des chansons à la bibliothèque ou sauvegarder/récupérer une liste de
 * lecture
 *
 * @author Patrick Nolin
 */
public class SelecteurFichier {

    public SelecteurFichier() {

    }

    /**
     * Méthode qui ouvre un sélecteur de fichier pour que l'utilisateur choisisse sa chanson.
     *
     * @param stage Le stage où s'ouvrira le sélecteur de fichier.
     * @return Le fichier choisi.
     */
    public ArrayList<File> choisirFichier(Stage stage) {
        List<File> listeFichiers;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une chanson.");
        appliquerFiltre(fileChooser);
        listeFichiers = fileChooser.showOpenMultipleDialog(stage);

        if (listeFichiers != null)
            return new ArrayList<File>(listeFichiers);
        else
            return null;
    }

    /**
     * Méthode qui ouvre un sélecteur de fichier pour que l'utilisateur choisisse son image
     *
     * @param stage Le stage où s'ouvrira le sélecteur de fichier.
     * @return L'image choisi.
     */
    public String choisirImage(Stage stage) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image.");

        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        File file = fileChooser.showOpenDialog(null);

        return file.getAbsolutePath();
    }

    /**
     * Méthode qui applique un filtre à un sélecteur de fichier.
     *
     * @param fileChooser Le sélecteur de fichier auquel on applique le filtre.
     */
    private void appliquerFiltre(FileChooser fileChooser) {
        FileChooser.ExtensionFilter filtreMP3 = new FileChooser.ExtensionFilter("Fichier MP3 (*.mp3)", "*.mp3");
        fileChooser.getExtensionFilters().add(filtreMP3);
    }
}
