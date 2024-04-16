package admd.interim.logic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS candidats");
        db.execSQL("DROP TABLE IF EXISTS employeurs");
        db.execSQL("DROP TABLE IF EXISTS offres");

        onCreate(db);
    }

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

    private boolean offreExists(String titre, String description, String metier, String lieu, Date dateDebut, Date dateFin, int idEmployeur) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM offres WHERE titre = ? AND description = ? AND metier = ? AND lieu = ? AND date_debut = ? AND date_fin = ? AND id_employeur = ?";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateDebutStr = dateFormat.format(dateDebut);
        String dateFinStr = dateFormat.format(dateFin);
        Cursor cursor = db.rawQuery(query, new String[]{titre, description, metier, lieu, dateDebutStr, dateFinStr, String.valueOf(idEmployeur)});
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count > 0;
    }


    public long insertCandidat(String nom, String prenom, String dateNaissance, String nationalite, String numeroTelephone, String email, String ville, String cv) {
        if (candidatExists(nom, prenom, dateNaissance, nationalite, numeroTelephone, email, ville, cv)) {
            return -1;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nom", nom);
        values.put("prenom", prenom);
        values.put("date_naissance", dateNaissance);
        values.put("nationalite", nationalite);
        values.put("numero_telephone", numeroTelephone);
        values.put("email", email);
        values.put("ville", ville);
        values.put("cv", cv);

        long newRowId = db.insert("candidats", null, values);
        db.close();

        return newRowId;
    }

    public boolean candidatExistsByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM candidats WHERE email = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count > 0;
    }

    public Candidat getCandidatByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM candidats WHERE email = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});
        Candidat candidat = null;
        if (cursor.moveToFirst()) {
            candidat = new Candidat();
            candidat.setId(cursor.getInt(cursor.getColumnIndex("id")));
            candidat.setNom(cursor.getString(cursor.getColumnIndex("nom")));
            candidat.setPrenom(cursor.getString(cursor.getColumnIndex("prenom")));
            candidat.setDateNaissance(cursor.getString(cursor.getColumnIndex("date_naissance")));
            candidat.setNationalite(cursor.getString(cursor.getColumnIndex("nationalite")));
            candidat.setNumeroTelephone(cursor.getString(cursor.getColumnIndex("numero_telephone")));
            candidat.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            candidat.setVille(cursor.getString(cursor.getColumnIndex("ville")));
            candidat.setCv(cursor.getString(cursor.getColumnIndex("cv")));
        }
        cursor.close();
        return candidat;
    }


    public boolean employeurExistsByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM employeurs WHERE email = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email});
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count > 0;
    }

    public long insertEmployeur(String nom, String entreprise, String email, String numeroTelephone, String adresse, String liensPublic) {
        if (employeurExists(nom, entreprise, email, numeroTelephone, adresse, liensPublic)) {
            return -1;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nom", nom);
        values.put("entreprise", entreprise);
        values.put("email", email);
        values.put("numero_telephone", numeroTelephone);
        values.put("adresse", adresse);
        values.put("liens_public", liensPublic);

        long newRowId = db.insert("employeurs", null, values);
        db.close();

        return newRowId;
    }

    public long insertOffre(String titre, String description, String metier, String lieu, Date dateDebut, Date dateFin, int idEmployeur) {
        // Vérifier si une offre similaire existe déjà
        if (offreExists(titre, description, metier, lieu, dateDebut, dateFin, idEmployeur)) {
            // Offre existante, ne rien insérer et retourner -1 ou gérer l'erreur de votre manière
            return -1;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("titre", titre);
        values.put("description", description);
        values.put("metier", metier);
        values.put("lieu", lieu);

        // Formater les dates en chaînes de caractères
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateDebutStr = dateFormat.format(dateDebut);
        String dateFinStr = dateFormat.format(dateFin);

        values.put("date_debut", dateDebutStr);
        values.put("date_fin", dateFinStr);
        values.put("id_employeur", idEmployeur);

        long newRowId = db.insert("offres", null, values);
        db.close();

        return newRowId;
    }

    public List<Offre> getAllOffres() {
        List<Offre> offres = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM offres";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                Offre offre = new Offre();
                offre.setId(cursor.getInt(cursor.getColumnIndex("id")));
                offre.setTitre(cursor.getString(cursor.getColumnIndex("titre")));
                offre.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                offre.setMetier(cursor.getString(cursor.getColumnIndex("metier")));
                offre.setLieu(cursor.getString(cursor.getColumnIndex("lieu")));

                // Conversion des dates
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                try {
                    Date dateDebut = dateFormat.parse(cursor.getString(cursor.getColumnIndex("date_debut")));
                    Date dateFin = dateFormat.parse(cursor.getString(cursor.getColumnIndex("date_fin")));
                    offre.setDateDebut(dateDebut);
                    offre.setDateFin(dateFin);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                offre.setIdEmployeur(cursor.getInt(cursor.getColumnIndex("id_employeur")));

                offres.add(offre);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return offres;
    }

    public List<Offre> getOffresFiltered(String metier, String lieu, String dateDebut, String dateFin) {
        List<Offre> offres = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM offres WHERE ";
        List<String> conditions = new ArrayList<>();

        if (!metier.isEmpty()) {
            conditions.add("metier LIKE '%" + metier + "%'");
        }

        if (!lieu.isEmpty()) {
            conditions.add("lieu LIKE '%" + lieu + "%'");
        }

        if (!dateDebut.isEmpty() && !dateFin.isEmpty()) {
            conditions.add("date_debut >= '" + dateDebut + "' AND date_fin <= '" + dateFin + "'");
        } else if (!dateDebut.isEmpty()) {
            conditions.add("date_debut >= '" + dateDebut + "'");
        } else if (!dateFin.isEmpty()) {
            conditions.add("date_fin <= '" + dateFin + "'");
        }

        if (!conditions.isEmpty()) {
            query += String.join(" AND ", conditions);
        } else {
            query += "1";
        }

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Offre offre = new Offre();
                offre.setId(cursor.getInt(cursor.getColumnIndex("id")));
                offre.setTitre(cursor.getString(cursor.getColumnIndex("titre")));
                offre.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                offre.setMetier(cursor.getString(cursor.getColumnIndex("metier")));
                offre.setLieu(cursor.getString(cursor.getColumnIndex("lieu")));

                // Conversion des dates
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                try {
                    Date dateDebutObj = dateFormat.parse(cursor.getString(cursor.getColumnIndex("date_debut")));
                    Date dateFinObj = dateFormat.parse(cursor.getString(cursor.getColumnIndex("date_fin")));
                    offre.setDateDebut(dateDebutObj);
                    offre.setDateFin(dateFinObj);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                offre.setIdEmployeur(cursor.getInt(cursor.getColumnIndex("id_employeur")));

                offres.add(offre);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return offres;
    }

    public List<Offre> getOffresParLieu(String lieu) {
        List<Offre> offres = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM offres WHERE lieu = ?";
        Cursor cursor = db.rawQuery(query, new String[]{lieu});

        if (cursor.moveToFirst()) {
            do {
                Offre offre = new Offre();
                offre.setId(cursor.getInt(cursor.getColumnIndex("id")));
                offre.setTitre(cursor.getString(cursor.getColumnIndex("titre")));
                offre.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                offre.setMetier(cursor.getString(cursor.getColumnIndex("metier")));
                offre.setLieu(cursor.getString(cursor.getColumnIndex("lieu")));

                // Conversion des dates
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                try {
                    Date dateDebut = dateFormat.parse(cursor.getString(cursor.getColumnIndex("date_debut")));
                    Date dateFin = dateFormat.parse(cursor.getString(cursor.getColumnIndex("date_fin")));
                    offre.setDateDebut(dateDebut);
                    offre.setDateFin(dateFin);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                offre.setIdEmployeur(cursor.getInt(cursor.getColumnIndex("id_employeur")));

                offres.add(offre);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return offres;
    }

}
