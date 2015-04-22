/*
 *   © PearMusic 2015
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

 package ca.qc.bdeb.pearmusic;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {
    /**
     * Nom du fichier fxml du UI de la fenêtre principale
     */
    private final String PRINCIPAL_XML = "uiPrincipal.fxml";
    /**
     * Titre de la fenetre principale
     */
    private final String FENETRE_PRINCIPAL_TITRE = "Pear Music RC1";
    /**
     * Nom du fichier de l'icone de Pear Music
     */
    private  final Image IMAGE_ICONE_APP = new Image("icon.png");

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getClassLoader().getResource(PRINCIPAL_XML));
        Scene scene = new Scene(root);
        primaryStage.setTitle(FENETRE_PRINCIPAL_TITRE);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
        primaryStage.getIcons().add(IMAGE_ICONE_APP);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
    }
}
