package ca.qc.bdeb.pearmusic.Vue;

import java.util.EventListener;

/**
 * Interface définissant les évènements que peut faire un lecteur de musique.
 *
 * @author Patrick Nolin
 */
public interface LecteurMusiqueListener extends EventListener {
    public void prochaineChansonLancee(LecteurMusiqueEvent evenement);
}
