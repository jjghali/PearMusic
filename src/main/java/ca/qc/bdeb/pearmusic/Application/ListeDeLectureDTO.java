package ca.qc.bdeb.pearmusic.Application;

/**
 * DTO de playlist pour pouvoir transporter des données d'une playlist
 * sans exposer la playlist.
 * <p>
 * Created by Patrick Nolin on 2014-11-19.
 */
public class ListeDeLectureDTO {
    /**
     * Le nom de la liste de lecture
     */
    private String nomListe;
    /**
     * Le chemin d'acces vers cette liste
     */
    private String cheminListe;

    public ListeDeLectureDTO(String nomListe, String cheminListe) {
        this.nomListe = nomListe;
        this.cheminListe = cheminListe;
    }

    public String getNomListe() {
        return nomListe;
    }

    public void setNomListe(String nomListe) {
        this.nomListe = nomListe;
    }

    public String getCheminListe() {
        return cheminListe;
    }

    public void setCheminListe(String cheminListe) {
        this.cheminListe = cheminListe;
    }
}
