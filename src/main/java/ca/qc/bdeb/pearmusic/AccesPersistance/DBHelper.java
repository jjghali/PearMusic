package ca.qc.bdeb.pearmusic.AccesPersistance;

import ca.qc.bdeb.pearmusic.Application.ChansonDTO;
import ca.qc.bdeb.pearmusic.Application.ListeDeLectureDTO;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.ISqlJetTransaction;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

/**
 * Classe qui continent le code nécessaire à la création et à l'utilisation de la base de données qui enregistre la
 * bibliothèque de chansons et de liste de lecture de l'utilisateur
 *
 * @author Stu Ureta
 */
public class DBHelper implements ConstantesDB {
    /**
     * Volatile permet d'éviter le cas où DBHelper.instance est non-nul,
     * mais pas encore "réellement instancié.
     */
    private static volatile DBHelper instance = null;
    /**
     * Liste des listes de lecture de l'utilisateur
     */
    private ArrayList<ListeDeLectureDTO> listeDeLectureDTOs;
    /**
     * Fichier qui contient la BD
     */
    private File dbFile;
    /**
     * Module externe qui contrôle la BD
     */
    private SqlJetDb dataBase;

    /**
     * Constructeur privé, permet de faire un Singleton
     */
    private DBHelper() {
        String defaultFolder = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();

        dbFile = new File(defaultFolder + DB_NAME);

        if (dbFile.getParentFile() != null) {
            dbFile.getParentFile().mkdir();
        }
        if (!dbFile.exists() && !dbFile.isDirectory()) {
            try {
                dataBase = SqlJetDb.open(dbFile, true);
                dataBase.getOptions().setAutovacuum(true);
                dataBase.runTransaction(new ISqlJetTransaction() {
                    public Object run(SqlJetDb db) throws SqlJetException {
                        db.getOptions().setUserVersion(1);
                        return true;
                    }
                }, SqlJetTransactionMode.WRITE);

                dataBase.beginTransaction(SqlJetTransactionMode.WRITE);
                try {
                    dataBase.createTable(CREATE_TABLE_BIBLIOTHEQUE);
                    dataBase.createTable(CREATE_TABLE_LISTE_LECTURE);
                    dataBase.createIndex(CREATE_PATH_INDEX_QUERY_PATH);
                    dataBase.createIndex(CREATE_PATH_INDEX_QUERY_ARTIST);
                    dataBase.createIndex(CREATE_PATH_INDEX_QUERY_ALBUM);
                    dataBase.createIndex(CREATE_PATH_INDEX_QUERY_TITRE);
                    dataBase.createIndex(CREATE_PATH_INDEX_QUERY_GENRE);
                    dataBase.createIndex(CREATE_PATH_INDEX_QUERY_ANNEE);
                    dataBase.createIndex(CREATE_PATH_INDEX_QUERY_PATH_LISTE);
                    dataBase.createIndex(CREATE_PATH_INDEX_QUERY_NOM_LISTE);
                } finally {
                    dataBase.commit();
                }
                dataBase.close();
            } catch (SqlJetException e) {
                System.out.println(e.getMessage() + " Creation db");
            }
        }
    }

    /**
     * Methode qui permet d'obtenir l'instance unique de DBHelper
     *
     * @return l'instance du singleton
     */
    public static DBHelper getInstance() {
        // Double vérification de l'instance pour éviter un appel couteux à synchronized
        if (DBHelper.instance == null) {
            // Synchronized empêche toute instanciation multiple, même par un thread
            synchronized (DBHelper.class) {
                if (DBHelper.instance == null) {
                    DBHelper.instance = new DBHelper();
                }
            }
        }
        return DBHelper.instance;
    }

    /**
     * Méthode qui permet d'effacer les données de la table Musique. SQLite n'offre pas la fonction TRUNCATE, alors
     * on détruit la table et on la recrée.
     */
    public void truncateTableMusique() throws SqlJetException {
        try {
            try {
                dataBase = SqlJetDb.open(dbFile, true);
                dataBase.beginTransaction(SqlJetTransactionMode.WRITE);

                dataBase.dropTable(TABLE_BIBLIOTHEQUE);

                dataBase.createTable(CREATE_TABLE_BIBLIOTHEQUE);
                dataBase.createIndex(CREATE_PATH_INDEX_QUERY_PATH);
                dataBase.createIndex(CREATE_PATH_INDEX_QUERY_ARTIST);
                dataBase.createIndex(CREATE_PATH_INDEX_QUERY_ALBUM);
                dataBase.createIndex(CREATE_PATH_INDEX_QUERY_TITRE);
                dataBase.createIndex(CREATE_PATH_INDEX_QUERY_GENRE);
                dataBase.createIndex(CREATE_PATH_INDEX_QUERY_ANNEE);
            } finally {
                dataBase.commit();
            }
        } catch (SqlJetException e) {
            System.out.println(e.getMessage() + " Truncate table Musique");
        }
        dataBase.close();
        dataBase = SqlJetDb.open(dbFile, true);
    }

    /**
     * Methode qui permet d'ajouter une chanson à la base de données
     *
     * @param chansonDTO
     */
    public void ajouterChanson(ChansonDTO chansonDTO) throws SqlJetException {
        dataBase = SqlJetDb.open(dbFile, true);

        dataBase.beginTransaction(SqlJetTransactionMode.WRITE);
        try {
            ISqlJetTable table = dataBase.getTable(TABLE_BIBLIOTHEQUE);
            table.insert(chansonDTO.getPath(), chansonDTO.getArtiste(), chansonDTO.getAlbum(), chansonDTO.getTitre(),
                    chansonDTO.getPiste(), chansonDTO.getAnnee(), chansonDTO.getGenre(), chansonDTO.getDuree(),
                    chansonDTO.getPathImage());
        } finally {
            dataBase.commit();
        }
        dataBase.close();
    }

    /**
     * Methode qui permet d'effacer une chansons de la base de données
     *
     * @param path
     */
    public void deleteChanson(String path) throws SqlJetException {
        dataBase = SqlJetDb.open(dbFile, true);
        ISqlJetTable table = dataBase.getTable(TABLE_BIBLIOTHEQUE);
        dataBase.beginTransaction(SqlJetTransactionMode.WRITE);
        try {
            ISqlJetCursor deleteCursor = table.lookup(BIBLIOTHEQUE_INDEX_PATH, path);
            while (!deleteCursor.eof()) {
                deleteCursor.delete();
            }
            deleteCursor.close();
        } finally {
            dataBase.commit();
        }
        dataBase.close();
    }

    /**
     * Méthode qui supprime un album de la BD.
     * @param album Le nom de l'album.
     */
    public void deleteAlbum(String album) throws SqlJetException {
        dataBase = SqlJetDb.open(dbFile, true);
        ISqlJetTable table = dataBase.getTable(TABLE_BIBLIOTHEQUE);
        dataBase.beginTransaction(SqlJetTransactionMode.WRITE);
        try {
            ISqlJetCursor deleteCursor = table.lookup(BIBLIOTHEQUE_INDEX_ALBUM, album);
            while (!deleteCursor.eof()) {
                deleteCursor.delete();
            }
            deleteCursor.close();
        } finally {
            dataBase.commit();
        }
        dataBase.close();
    }

    /**
     * Supprime une liste de lecture de la BD.
     * @param artiste Le nom de l'artiste.
     * @throws SqlJetException
     */
    public void deleteArtiste(String artiste) throws SqlJetException {
        dataBase = SqlJetDb.open(dbFile, true);
        ISqlJetTable table = dataBase.getTable(TABLE_BIBLIOTHEQUE);
        dataBase.beginTransaction(SqlJetTransactionMode.WRITE);
        try {
            ISqlJetCursor deleteCursor = table.lookup(BIBLIOTHEQUE_INDEX_ARTIST, artiste);
            while (!deleteCursor.eof()) {
                deleteCursor.delete();
            }
            deleteCursor.close();
        } finally {
            dataBase.commit();
        }
        dataBase.close();
    }

    /**
     * Supprime une liste de lecture de la BD.
     * @param path Le chemin de la liste de lecture.
     */
    public void deleteListeDeLecture(String path) throws SqlJetException {
        dataBase = SqlJetDb.open(dbFile, true);
        ISqlJetTable table = dataBase.getTable(TABLE_LISTE_LECTURE);
        dataBase.beginTransaction(SqlJetTransactionMode.WRITE);
        try {
            ISqlJetCursor deleteCursor = table.lookup(LISTE_LECTURE_INDEX_PATH, path);
            while (!deleteCursor.eof()) {
                deleteCursor.delete();
            }
            deleteCursor.close();
        }
        finally {
            dataBase.commit();
        }
        dataBase.close();
    }

    /**
     * Methode qui met a jour une chanson si les données de cette dernière ont été changées
     *
     * @param chanson
     */
    public void updateMusique(ChansonDTO chanson) throws SqlJetException {
        dataBase = SqlJetDb.open(dbFile, true);
        ISqlJetTable table = dataBase.getTable(TABLE_BIBLIOTHEQUE);
        dataBase.beginTransaction(SqlJetTransactionMode.WRITE);
        try {
            ISqlJetCursor updateCursor = table.lookup(BIBLIOTHEQUE_INDEX_PATH, chanson.getPath());
            do {
                updateCursor.update(updateCursor.getString(BIBLIOTHEQUE_PATH), chanson.getArtiste(), chanson.getAlbum(),
                        chanson.getTitre(), chanson.getPiste(), chanson.getAnnee(), chanson.getGenre(),
                        chanson.getDuree(), chanson.getPathImage());
            } while (updateCursor.next());
            updateCursor.close();
        } finally {
            dataBase.commit();
        }
        dataBase.close();
    }

    /**
     * Méthode qui permet d'obtenir la bibliothèque des chansons
     *
     * @return toutes les chansons de la bibliothèque
     */
    public ArrayList<ChansonDTO> getChansonsBibliotheque() throws SqlJetException {

        ArrayList<ChansonDTO> chansonDTO = new ArrayList<ChansonDTO>();

        dataBase = SqlJetDb.open(dbFile, true);
        ISqlJetTable table = dataBase.getTable(TABLE_BIBLIOTHEQUE);
        dataBase.beginTransaction(SqlJetTransactionMode.READ_ONLY);
        ISqlJetCursor cursor = table.open();
        try {
            if (!cursor.eof()) {
                do {
                    chansonDTO.add(new ChansonDTO(
                            cursor.getString(BIBLIOTHEQUE_PATH),
                            cursor.getString(BIBLIOTHEQUE_PISTE),
                            cursor.getString(BIBLIOTHEQUE_ARTISTE),
                            cursor.getString(BIBLIOTHEQUE_TITRE),
                            cursor.getString(BIBLIOTHEQUE_ALBUM),
                            cursor.getString(BIBLIOTHEQUE_ANNEE),
                            (int) cursor.getInteger(BIBLIOTHEQUE_GENRE),
                            cursor.getInteger(BIBLIOTHEQUE_DUREE),
                            cursor.getString(BIBLIOTHEQUE_IMAGE)));
                } while (cursor.next());
            }
        } finally {
            cursor.close();
        }
        dataBase.close();
        return chansonDTO;
    }

    /**
     * Méthode qui permet d'obtenir les chansons d'un album
     *
     * @param nomAlbum
     * @return toutes les chansons de l'album
     */
    public ArrayList<ChansonDTO> getChansonsAlbumBibliotheque(String nomAlbum) throws SqlJetException {

        ArrayList<ChansonDTO> chansonDTO = new ArrayList<ChansonDTO>();

        dataBase = SqlJetDb.open(dbFile, true);
        ISqlJetTable table = dataBase.getTable(TABLE_BIBLIOTHEQUE);
        dataBase.beginTransaction(SqlJetTransactionMode.READ_ONLY);
        ISqlJetCursor cursor = table.open();
        try {
            if (!cursor.eof()) {
                do {
                    if (cursor.getString(BIBLIOTHEQUE_ALBUM).equals(nomAlbum))
                        chansonDTO.add(new ChansonDTO(
                                cursor.getString(BIBLIOTHEQUE_PATH),
                                cursor.getString(BIBLIOTHEQUE_PISTE),
                                cursor.getString(BIBLIOTHEQUE_ARTISTE),
                                cursor.getString(BIBLIOTHEQUE_TITRE),
                                cursor.getString(BIBLIOTHEQUE_ALBUM),
                                cursor.getString(BIBLIOTHEQUE_ANNEE),
                                (int) cursor.getInteger(BIBLIOTHEQUE_GENRE),
                                cursor.getInteger(BIBLIOTHEQUE_DUREE),
                                cursor.getString(BIBLIOTHEQUE_IMAGE)));
                } while (cursor.next());
            }
        } finally {
            cursor.close();
        }
        dataBase.close();
        return chansonDTO;
    }
    /**
     * Méthode qui permet d'obtenir les chansons d'un artiste
     *
     * @param nomArtiste
     * @return toutes les chansons de l'artiste
     */
    public ArrayList<ChansonDTO> getChansonsArtisteBibliotheque(String nomArtiste) throws SqlJetException {
        ArrayList<ChansonDTO> chansonDTO = new ArrayList<ChansonDTO>();
        dataBase = SqlJetDb.open(dbFile, true);
        ISqlJetTable table = dataBase.getTable(TABLE_BIBLIOTHEQUE);
        dataBase.beginTransaction(SqlJetTransactionMode.READ_ONLY);
        ISqlJetCursor cursor = table.open();
        try {
            if (!cursor.eof()) {
                do {
                    if (cursor.getString(BIBLIOTHEQUE_ARTISTE).equals(nomArtiste))
                        chansonDTO.add(new ChansonDTO(
                                cursor.getString(BIBLIOTHEQUE_PATH),
                                cursor.getString(BIBLIOTHEQUE_PISTE),
                                cursor.getString(BIBLIOTHEQUE_ARTISTE),
                                cursor.getString(BIBLIOTHEQUE_TITRE),
                                cursor.getString(BIBLIOTHEQUE_ALBUM),
                                cursor.getString(BIBLIOTHEQUE_ANNEE),
                                (int) cursor.getInteger(BIBLIOTHEQUE_GENRE),
                                cursor.getInteger(BIBLIOTHEQUE_DUREE),
                                cursor.getString(BIBLIOTHEQUE_IMAGE)));
                } while (cursor.next());
            }
        } finally {
            cursor.close();
        }
        dataBase.close();
        return chansonDTO;
    }

    /**
     * Methode qui recupere les paths des musique pour une table donnee
     *
     * @param index
     * @param colonne
     * @param nomTable
     */
    public ArrayList<String> getElementsIndex(String index, String colonne, String nomTable) throws SqlJetException {
        ArrayList<String> paths = new ArrayList<String>();

        dataBase = SqlJetDb.open(dbFile, true);

        ISqlJetTable table = dataBase.getTable(nomTable);
        dataBase.beginTransaction(SqlJetTransactionMode.READ_ONLY);
        ISqlJetCursor cursor = table.lookup(index, colonne); // ex: index_album, album
        try {
            do {
                paths.add(cursor.getString(BIBLIOTHEQUE_PATH));
            } while (cursor.next());

        } finally {
            cursor.close();
        }
        dataBase.close();
        return paths;
    }

    /**
     * Cette méthode permet d'ajouter plusieurs chansons à la blibliothèque
     * à l'aide d'une ArrayList de chansonDTO
     *
     * @param fichiersTraites
     * @throws SqlJetException
     */
    public void insererSurBD(ArrayList<ChansonDTO> fichiersTraites) throws SqlJetException {
        if (fichiersTraites != null) {
            int compteur = 0;
            try {
                while (compteur < fichiersTraites.size()) {
                    ajouterChanson(fichiersTraites.get(compteur));
                    compteur++;
                }
            } catch (SqlJetException e) {
                System.out.println(e.getMessage() + " classe dbHelper");
            }
        }
    }

    /**
     * Methode qui recupere toutes les chansons d'un album
     *
     * @param album
     * @return une ArrayList de path
     */
    public ArrayList<String> getChansonsAlbums(String album) {
        try {
            return getElementsIndex(BIBLIOTHEQUE_INDEX_ALBUM, album, TABLE_BIBLIOTHEQUE);
        } catch (SqlJetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Methode qui recupere les chansons d'un artiste
     *
     * @param artiste
     * @return
     */
    public ArrayList<String> getChansonsArtiste(String artiste) {
        try {
            return getElementsIndex(BIBLIOTHEQUE_INDEX_ARTIST, artiste, TABLE_BIBLIOTHEQUE);
        } catch (SqlJetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Méthode qui insère une liste de lecture dans la bibliothèque
     *
     * @param nom  que l'utilisateur à choisi
     * @param path endroit où l'utilisateur à enregistré son fichier .pear
     * @throws SqlJetException
     */
    public void insererListeDeLecture(String nom, String path) throws SqlJetException {
        dataBase = SqlJetDb.open(dbFile, true);
        ISqlJetTable table = dataBase.getTable(TABLE_LISTE_LECTURE);

        dataBase.beginTransaction(SqlJetTransactionMode.WRITE);
        try {
            table.insert(nom, path);
        } finally {
            dataBase.commit();
        }
        dataBase.close();
    }

    /**
     * Methode qui recupere les listeDeLectureDTOs
     *
     * @return Une liste d'objet ListeDeLecture
     */
    public ArrayList<ListeDeLectureDTO> getListeDeLecture() {
        listeDeLectureDTOs = new ArrayList<ListeDeLectureDTO>();

        try {
            dataBase = SqlJetDb.open(dbFile, true);
            ISqlJetTable table = dataBase.getTable(TABLE_LISTE_LECTURE);
            dataBase.beginTransaction(SqlJetTransactionMode.READ_ONLY);
            ISqlJetCursor cursor = table.open();
            try {
                if (!cursor.eof()) {
                    do {
                        listeDeLectureDTOs.add(new ListeDeLectureDTO(cursor.getString(LISTE_LECTURE_NOM), cursor.getString(LISTE_LECTURE_PATH)));
                    } while (cursor.next());
                }
            } finally {
                cursor.close();
            }
        } catch (SqlJetException e) {
            e.printStackTrace();
        }
        try {
            dataBase.close();
        } catch (SqlJetException e) {
            System.out.println(e.getMessage());
        }
        return listeDeLectureDTOs;
    }

    /**
     * Methode qui recupere la position d'une liste de lecture dans la liste des listeDeLectureDTOs
     *
     * @param nomListeDeLecture
     * @return l'index de la liste de lecture
     */
    private int recupererIndexListeLecture(String nomListeDeLecture) {
        int index = -1;
        for (ListeDeLectureDTO liste : listeDeLectureDTOs) {
            if (liste.getNomListe().equals(nomListeDeLecture)) {
                index = listeDeLectureDTOs.indexOf(liste);
            }
        }
        return index;
    }

    /**
     * Méthode qui remplace count (*)
     *
     * @return le nombre d'entrée de la table MUSIQUE
     */
    public int getTailleTabMusique() {
        int nombreDeLigne = 0;

        try {
            dataBase = SqlJetDb.open(dbFile, true);

            ISqlJetTable table = dataBase.getTable(TABLE_BIBLIOTHEQUE);
            dataBase.beginTransaction(SqlJetTransactionMode.READ_ONLY);
            ISqlJetCursor cursor = table.open();

            if (!cursor.eof()) {
                do {
                    nombreDeLigne++;
                } while (cursor.next());
            }
        } catch (SqlJetException e) {
            e.printStackTrace();
        }
        return nombreDeLigne;
    }
}
