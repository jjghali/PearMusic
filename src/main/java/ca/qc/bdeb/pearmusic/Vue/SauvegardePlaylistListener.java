package ca.qc.bdeb.pearmusic.Vue;

import java.util.EventListener;

/**
 * Interface définissant les évènements que peut faire une interface de sauvegarde de playlist.
 * Created by Patrick Nolin on 2014-11-04.
 */
public interface SauvegardePlaylistListener extends EventListener {

    public void sauvegardeDePlaylist(SauvegardePlaylistEvent sauvegardePlaylistEvent, String nom);

}
