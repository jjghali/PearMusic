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

import java.util.ArrayList;
import java.util.Collections;

/**
 * Classe qui gère la lecture aléatoire d'une liste de lecture.
 *
 * @author Patrick Nolin
 * @date 2014-10-13
 */
class Aleatoire {
    /**
     * Position dans la liste des index.
     */
    private int position;
    /**
     * Indique le le mode aléatoire est actif ou non.
     */
    private boolean actif;
    /**
     * Liste qui contient les indexs des chansons à faire jouer.
     * Les index seront dans un ordre aléatoire.
     */
    private ArrayList<Integer> indexChansons;

    public Aleatoire() {
        indexChansons = new ArrayList<Integer>();
        position = 0;
        actif = false;
    }

    public boolean estActif() {
        return actif;
    }

    public ArrayList<Integer> getIndexChansons() {
        return indexChansons;
    }

    /**
     * Indique si l'index en cours est le dernier de la liste.
     *
     * @return Vrai si oui, sinon non.
     */
    public boolean estDernierIndex() {
        boolean dernier = false;
        if (position == indexChansons.size() - 1) {
            dernier = true;
        }
        return dernier;
    }

    /**
     * Méthode qui démarre la lecture aléatoire d'une liste.
     *
     * @param taille Taille de la liste à faire jouer de manière aléatoire.
     */
    public void demarrerAleatoire(int taille) {
        actif = true;
        remplirIndexs(taille);
        Collections.shuffle(indexChansons);
    }

    /**
     * Remplit la liste des indexs.
     *
     * @param taille Taille de la liste à faire jouer de manière aléatoire.
     */
    private void remplirIndexs(int taille) {
        int compteur = 0;
        while (compteur < taille) {
            indexChansons.add(compteur);
            compteur++;
        }
    }

    /**
     * Choisit le prochain index dans la liste et le renvoit.
     *
     * @return L'index.
     */
    public int recupererProchainIndex() {
        int index = 0;
        position++;
        if (position >= indexChansons.size()) {
            position = 0;
        }
        index = indexChansons.get(position);
        return index;
    }

    /**
     * Choisit l'index précedent dans la liste et le renvoit.
     *
     * @return L'index.
     */
    public int recupererIndexPrecedent() {
        int index = 0;
        if (position == 0) {
            position = indexChansons.size() - 1;
        } else {
            position--;
            index = indexChansons.get(position);
        }
        return index;
    }

    /**
     * Permet d'ajouter des chansons à une liste de lecture écoutée
     * aléatoirement.
     *
     * @param taille Le nombre de chansons à ajouter.
     */
    public void ajouterChansons(int taille) {
        int compteur = 0;
        while (compteur < taille) {
            indexChansons.add(compteur + indexChansons.size());
            compteur++;
        }
    }

    /**
     * Permet de retirer une chanson d'une liste de lecture écoutée
     * aléatoirement.
     *
     * @param index
     */
    public void retirerChanson(int index) {
        indexChansons.remove(new Integer(index));
        for (int i = 0; i < indexChansons.size(); i++) {
            if (indexChansons.get(i) > index) {
                indexChansons.set(i, indexChansons.get(i) - 1);
            }
        }
    }

    /**
     * Arrete l'écoute aléatoire de la liste de lecture en cours.
     */
    public void arreterAleatoire() {
        actif = false;
        indexChansons.clear();
        position = 0;
    }

}
