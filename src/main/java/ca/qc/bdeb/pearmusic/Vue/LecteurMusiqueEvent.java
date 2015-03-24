package ca.qc.bdeb.pearmusic.Vue;

import java.util.EventObject;

/**
 * Événement du lecteur de musique, permet au coordonnateur de mettre à jour la vue
 *
 * @author Patrick Nolin
 */
public class LecteurMusiqueEvent extends EventObject {

    public LecteurMusiqueEvent(Object source) {
        super(source);
    }
}
