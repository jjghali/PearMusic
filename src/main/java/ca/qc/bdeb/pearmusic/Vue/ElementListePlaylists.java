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

import ca.qc.bdeb.pearmusic.Application.ListeDeLectureDTO;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.Pane;

/**
 *
 *
 * Created by Joshua Ghali on 11/4/2014.
 */
public class ElementListePlaylists extends ListCell<ListeDeLectureDTO> {

    @Override
    public void updateItem(ListeDeLectureDTO item, boolean vide) {
        super.updateItem(item, vide);
        Label lblNomPlaylist = new Label();
        Pane pane = new Pane();

        if (item != null) {
            pane.setMaxWidth(376);
            pane.setPrefWidth(376);
            pane.setMinWidth(376);
            lblNomPlaylist.setText(item.getNomListe());
            pane.getChildren().add(lblNomPlaylist);
            setGraphic(pane);
        }
    }
}
