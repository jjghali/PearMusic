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

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Classe qui contient les informations d'une liste de lecture
 * <p>
 * Created by Joshua Ghali on 11/2/2014.
 */
class ListeDeLecture implements ConstantesApplication {
    /**
     * Le nom de la liste de lecture
     */
    private String nomListe;
    /**
     * Le chemin d'acces vers cette liste
     */
    private String cheminListe;
    /**
     * La liste des chansons ouvert par l'utilisateur.
     */
    private ArrayList<ChansonDTO> fichiersChansons;
    /**
     * Les chemins des chansons ouverts par l'utilisateur.
     */
    private ArrayList<String> cheminChansons;

    public ListeDeLecture(ArrayList<ChansonDTO> fichiersChansons) {
        this.fichiersChansons = fichiersChansons;
    }

    public ListeDeLecture() {
        fichiersChansons = new ArrayList<ChansonDTO>();
    }

    /**
     * Méthode qui sauvegarde les chemins des chansons dans un fichier texte.
     *
     * @throws java.io.FileNotFoundException
     */
    public void sauvegarderChemins(String nomFichier) throws FileNotFoundException {
        int index = 0;
        String cheminFichier = "";
        String defaultFolder = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
        creerDossier(defaultFolder);
        PrintWriter createurFichier = null;
        cheminFichier = FICHIER_DEFAULT + nomFichier + EXTENSION;
        createurFichier = new PrintWriter(
                new BufferedOutputStream(new FileOutputStream(defaultFolder + cheminFichier)));
        convertirFichiersEnChemins();
        for (String chemin : cheminChansons) {
            createurFichier.println(chemin);
        }
        createurFichier.close();
    }

    /**
     * Méthode qui va créer des fichiers avec les chemins dans chansons sauvegarder dans un fichier.
     *
     * @param nomFichier Le nom du fichier a charger.
     * @return La liste des fichiers.
     * @throws java.io.IOException
     */
    public ArrayList<ChansonDTO> chargerChansons(String nomFichier) throws IOException {
        fichiersChansons.clear();
        String defaultFolder = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
        BufferedReader lecteurFichier = null;
        String ligne = "";
        boolean fichierExiste = true;
        try {
            String cheminFichierTxt = "";
            cheminFichierTxt = FICHIER_DEFAULT + nomFichier + EXTENSION;

            lecteurFichier = new BufferedReader(new FileReader(defaultFolder + cheminFichierTxt));
        } catch (FileNotFoundException e) {
            fichierExiste = false;
        }
        if (fichierExiste) {
            while ((ligne = lecteurFichier.readLine()) != null) {
                Chanson chanson = new Chanson(ligne);
                fichiersChansons.add(chanson.asDTO());
            }
            lecteurFichier.close();
        }
        return fichiersChansons;
    }

    /**
     * Méthode qui créer un chemin à partir d'un fichier.
     */
    private void convertirFichiersEnChemins() {
        cheminChansons = new ArrayList<String>();
        for (ChansonDTO fichier : fichiersChansons) {
            cheminChansons.add(fichier.getPath());
        }
    }

    /**
     * Méthode qui créer le dossier où sera mit le fichier des chansons.
     * S'il existe déjà il n'est pas créer.
     *
     * @param defaultFolder Le dossier par défaut du user.
     */
    private void creerDossier(String defaultFolder) {
        File file = null;
        file = new File(defaultFolder + FICHIER_DEFAULT);

        if (file.getParentFile() != null) {
            file.getParentFile().mkdir();
        }
    }

    /**
     * Retourne le nom d'un fichier.
     *
     * @param nom Le nom du fichier.
     * @return Le path du fichier.
     */
    public String getPath(String nom) {
        String defaultFolder = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
        String cheminFichierTxt = "";
        cheminFichierTxt = FICHIER_DEFAULT + nom + EXTENSION;

        return defaultFolder + cheminFichierTxt;
    }

    /**
     * Ajoute une chanson à une liste de lecture.
     *
     * @param chanson           La chanson
     * @param nomListeDeLecture La liste de lecture dans laquelle on fait l'ajout.
     */
    public void ajouterChanons(ChansonDTO chanson, String nomListeDeLecture) {
        String pathListeDeLecture = getPath(nomListeDeLecture);
        PrintWriter fichier = null;
        try {
            fichier = new PrintWriter(new BufferedWriter(new FileWriter(pathListeDeLecture, true)));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        fichier.println(chanson.getPath());
        fichier.close();
    }
}
