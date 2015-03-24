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
