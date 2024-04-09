package admd.interim.logic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "interim.db";
    private static final int DATABASE_VERSION = 1;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Créer la table "candidats"
        String createCandidatsTable = "CREATE TABLE candidats (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nom TEXT," +
                "prenom TEXT," +
                "date_naissance TEXT," +
                "nationalite TEXT," +
                "numero_telephone TEXT," +
                "email TEXT," +
                "ville TEXT," +
                "cv TEXT" +
                ")";
        db.execSQL(createCandidatsTable);

        // Créer la table "employeurs"
        String createEmployeursTable = "CREATE TABLE employeurs (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nom TEXT," +
                "entreprise TEXT," +
                "email TEXT," +
                "numero_telephone TEXT," +
                "adresse TEXT," +
                "liens_public TEXT" +
                ")";
        db.execSQL(createEmployeursTable);

        // Créer la table "offres"
        String createOffresTable = "CREATE TABLE offres (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "titre TEXT," +
                "description TEXT," +
                "metier TEXT," +
                "lieu TEXT," +
                "date_debut TEXT," +
                "date_fin TEXT," +
                "id_employeur INTEGER," +
                "FOREIGN KEY (id_employeur) REFERENCES employeurs(id)" +
                ")";
        db.execSQL(createOffresTable);
    }

// Gérer les mises à jour de la base de données ici
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // Supprimer les tables si elles existent
        db.execSQL("DROP TABLE IF EXISTS candidats");
        db.execSQL("DROP TABLE IF EXISTS employeurs");
        db.execSQL("DROP TABLE IF EXISTS offres");

        // Recréer les tables
        onCreate(db);
    }

    // Vérifier si un candidat existe déjà dans la base de données
    public boolean candidatExists(String nom, String prenom, String dateNaissance, String nationalite, String numeroTelephone, String email, String ville, String cv) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM candidats WHERE nom = ? AND prenom = ? AND date_naissance = ? AND nationalite = ? AND numero_telephone = ? AND email = ? AND ville = ? AND cv = ?";
        Cursor cursor = db.rawQuery(query, new String[]{nom, prenom, dateNaissance, nationalite, numeroTelephone, email, ville, cv});
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count > 0;
    }

    // Vérifier si un employeur existe déjà dans la base de données
    private boolean employeurExists(String nom, String entreprise, String email, String numeroTelephone, String adresse, String liensPublic) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM employeurs WHERE nom = ? AND entreprise = ? AND email = ? AND numero_telephone = ? AND adresse = ? AND liens_public = ?";
        Cursor cursor = db.rawQuery(query, new String[]{nom, entreprise, email, numeroTelephone, adresse, liensPublic});
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count > 0;
    }

    // Vérifier si une offre existe déjà dans la base de données
    private boolean offreExists(String titre, String description, String metier, String lieu, String dateDebut, String dateFin, int idEmployeur) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM offres WHERE titre = ? AND description = ? AND metier = ? AND lieu = ? AND date_debut = ? AND date_fin = ? AND id_employeur = ?";
        Cursor cursor = db.rawQuery(query, new String[]{titre, description, metier, lieu, dateDebut, dateFin, String.valueOf(idEmployeur)});
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count > 0;
    }

// Insérer un candidat dans la base de données
    public long insertCandidat(String nom, String prenom, String dateNaissance, String nationalite, String numeroTelephone, String email, String ville, String cv) {
        // Vérifier si le candidat existe déjà
        if (candidatExists(nom, prenom, dateNaissance, nationalite, numeroTelephone, email, ville, cv)) {
            // Le candidat existe déjà, ne pas l'insérer
            return -1;
        }

        // Obtenir une instance de SQLiteDatabase en mode écriture
        SQLiteDatabase db = this.getWritableDatabase();
        // Créer un objet ContentValues pour stocker les valeurs du candidat
        ContentValues values = new ContentValues();
        values.put("nom", nom);
        values.put("prenom", prenom);
        values.put("date_naissance", dateNaissance);
        values.put("nationalite", nationalite);
        values.put("numero_telephone", numeroTelephone);
        values.put("email", email);
        values.put("ville", ville);
        values.put("cv", cv);

        // Insérer le candidat dans la table "candidats"
        long newRowId = db.insert("candidats", null, values);

        // Fermer la connexion à la base de données
        db.close();

        if (newRowId == -1) {
            // L'insertion a échoué
            System.out.println("L'insertion a échoué");
        } else {
            // L'insertion a réussi
            System.out.println("L'insertion a réussi");}
        return newRowId;
    }

    // Insérer un employeur dans la base de données
    public long insertEmployeur(String nom, String entreprise, String email, String numeroTelephone, String adresse, String liensPublic) {
        // Vérifier si l'employeur existe déjà
        if (employeurExists(nom, entreprise, email, numeroTelephone, adresse, liensPublic)) {
            // L'employeur existe déjà, ne pas l'insérer
            return -1;
        }

        // Obtenir une instance de SQLiteDatabase en mode écriture
        SQLiteDatabase db = this.getWritableDatabase();

        // Créer un objet ContentValues pour stocker les valeurs de l'employeur
        ContentValues values = new ContentValues();
        values.put("nom", nom);
        values.put("entreprise", entreprise);
        values.put("email", email);
        values.put("numero_telephone", numeroTelephone);
        values.put("adresse", adresse);
        values.put("liens_public", liensPublic);

        // Insérer l'employeur dans la table "employeurs"
        long newRowId = db.insert("employeurs", null, values);

        // Fermer la connexion à la base de données
        db.close();

        return newRowId;
    }
    public long insertOffre(String titre, String description, String metier, String lieu, String dateDebut, String dateFin, int idEmployeur) {
        // Vérifier si l'offre existe déjà
        if (offreExists(titre, description, metier, lieu, dateDebut, dateFin, idEmployeur)) {
            // L'offre existe déjà, ne pas l'insérer
            return -1;
        }

        // Obtenir une instance de SQLiteDatabase en mode écriture
        SQLiteDatabase db = this.getWritableDatabase();

        // Créer un objet ContentValues pour stocker les valeurs de l'offre
        ContentValues values = new ContentValues();
        values.put("titre", titre);
        values.put("description", description);
        values.put("metier", metier);
        values.put("lieu", lieu);
        values.put("date_debut", dateDebut);
        values.put("date_fin", dateFin);
        values.put("id_employeur", idEmployeur);

        // Insérer l'offre dans la table "offres"
        long newRowId = db.insert("offres", null, values);

        // Fermer la connexion à la base de données
        db.close();

        return newRowId;
    }

    // Récupérer toutes les offres de la base de données
    public List<Offre> getAllOffres() {
        List<Offre> offres = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM offres";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Offre offre = new Offre();

                int idIndex = cursor.getColumnIndex("id");
                if (idIndex != -1) {
                    offre.setId(cursor.getInt(idIndex));
                }

                int titreIndex = cursor.getColumnIndex("titre");
                if (titreIndex != -1) {
                    offre.setTitre(cursor.getString(titreIndex));
                }

                int descriptionIndex = cursor.getColumnIndex("description");
                if (descriptionIndex != -1) {
                    offre.setDescription(cursor.getString(descriptionIndex));
                }

                int metierIndex = cursor.getColumnIndex("metier");
                if (metierIndex != -1) {
                    offre.setMetier(cursor.getString(metierIndex));
                }

                int lieuIndex = cursor.getColumnIndex("lieu");
                if (lieuIndex != -1) {
                    offre.setLieu(cursor.getString(lieuIndex));
                }

                int dateDebutIndex = cursor.getColumnIndex("date_debut");
                if (dateDebutIndex != -1) {
                    offre.setDateDebut(cursor.getString(dateDebutIndex));
                }

                int dateFinIndex = cursor.getColumnIndex("date_fin");
                if (dateFinIndex != -1) {
                    offre.setDateFin(cursor.getString(dateFinIndex));
                }

                int idEmployeurIndex = cursor.getColumnIndex("id_employeur");
                if (idEmployeurIndex != -1) {
                    offre.setIdEmployeur(cursor.getInt(idEmployeurIndex));
                }

                offres.add(offre);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return offres;
    }


}
