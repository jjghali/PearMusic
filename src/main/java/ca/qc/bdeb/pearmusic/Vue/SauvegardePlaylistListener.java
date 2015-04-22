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

import java.util.EventListener;

/**
 * Interface définissant les évènements que peut faire une interface de sauvegarde de playlist.
 * Created by Patrick Nolin on 2014-11-04.
 */
public interface SauvegardePlaylistListener extends EventListener {

    public void sauvegardeDePlaylist(SauvegardePlaylistEvent sauvegardePlaylistEvent, String nom);

}
