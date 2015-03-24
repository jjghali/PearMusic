package ca.qc.bdeb.pearmusic.Vue;

import java.util.EventObject;

/**
 * Classe qui gère les évènements de la fenêtre de sauvegarde de chansons.
 * <p>
 * Created by Patrick Nolin on 2014-11-04.
 */
public class SauvegardePlaylistEvent extends EventObject {

    public SauvegardePlaylistEvent(Object source) {
        super(source);
    }

}
