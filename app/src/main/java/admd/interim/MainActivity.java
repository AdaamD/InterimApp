package admd.interim;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import admd.interim.anonyme.AnonymeActivity;
import admd.interim.candidat.MenuCandidatActivity;
import admd.interim.employeur.InscriptionEmployeurActivity;
import admd.interim.employeur.MenuEmployeurActivity;
import admd.interim.logic.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private static final String PREF_LOCATION_ACCEPTED = "location_accepted";
    private boolean locationAccepted;

    public static String getPrefLocationAcceptedKey() {
        return PREF_LOCATION_ACCEPTED;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        locationAccepted = preferences.getBoolean(PREF_LOCATION_ACCEPTED, false);

        if (!locationAccepted) {
            // Afficher une boîte de dialogue ou une demande d'autorisation pour la localisation
            showLocationPermissionDialog();
        }

        Button buttonEspaceCandidatClick = findViewById(R.id.button_espace_candidat);
        buttonEspaceCandidatClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Rediriger vers l'activité EspaceCandidatActivity
                Intent intent = new Intent(MainActivity.this, MenuCandidatActivity.class);
                startActivity(intent);
            }
        });

        Button buttonEspaceEmployeurClick = findViewById(R.id.button_espace_employeur);
        buttonEspaceEmployeurClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Rediriger vers l'activité EspaceEmployeurActivity
                Intent intent = new Intent(MainActivity.this, MenuEmployeurActivity.class);
                startActivity(intent);
            }
        });

        TextView textViewContinuerAnonymement = findViewById(R.id.textView_continuer_anonymement);
        textViewContinuerAnonymement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Rediriger vers l'activité AnonymeActivity avec un indicateur
                Intent intent = new Intent(MainActivity.this, AnonymeActivity.class);
                intent.putExtra("ask_location_permission", true);
                startActivity(intent);
            }
        });

        // Instancier la classe DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtenir une instance de SQLiteDatabase en mode écriture
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        // Insérer un nouvel candidat
        long Candidat1 = databaseHelper.insertCandidat("ADAM", "D", "01/01/2001", "francaise", "0611111111", "adam@mail.fr", "Montpellier", "CV-Adam","Adam");

        if (Candidat1 == -1) {
            // Le candidat existe déjà, afficher un message
            Toast.makeText(this, "Le candidat existe déjà dans la base de données", Toast.LENGTH_SHORT).show();
        } else {
            // Le candidat  a été inséré avec succès, afficher un message
            Toast.makeText(this, "Nouvel candidat inséré avec l'ID : " + Candidat1, Toast.LENGTH_SHORT).show();
        }

        long Candidat2 = databaseHelper.insertCandidat("Nabil", "Da", "10/10/2001", "marocain", "0622222222", "nabil@mail.fr", "Montpellier", "CV-Nabil", "Nabil");


        // Insérer un nouvel employeur
        long employeurId = databaseHelper.insertEmployeur(
                "John Doe",
                "Acme Inc.",
                "0612345678",
                "123 Main Street, Anytown USA",
                "https://www.acme.com",
                "john.doe@acme.com",
                "John"
        );

        if (employeurId == -1) {
            // L'employeur existe déjà, afficher un message
            Toast.makeText(this, "L'employeur existe déjà dans la base de données", Toast.LENGTH_LONG).show();
        } else {
            // L'employeur a été inséré avec succès, afficher un message
            Toast.makeText(this, "Nouvel employeur inséré avec l'ID : " + employeurId, Toast.LENGTH_LONG).show();
        }

        // Employeur 1 (John Doe existant)

// Insérer un nouvel employeur (employeur 2)
        long employeurId2 = databaseHelper.insertEmployeur(
                "Sophie Lefebvre",
                "InnovaConseil",
                "0678901234",
                "27 Avenue des Champs-Élysées, 34000 Toulouse, France",
                "https://www.innovaconseil.fr",
                "sophie.lefebvre@innovaconseil.fr",
                "Sophie"
        );

        if (employeurId2 == -1) {
            Toast.makeText(this, "L'employeur Sophie Lefebvre existe déjà dans la base de données", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Nouvel employeur Sophie Lefebvre inséré avec l'ID : " + employeurId2, Toast.LENGTH_LONG).show();
        }

// Insérer un nouvel employeur (employeur 3)
        long employeurId3 = databaseHelper.insertEmployeur(
                "Marc Girard",
                "CyberTech Solutions",
                "0612345678",
                "5 Place Bellecour, 3400 Toulouse, France",
                "https://www.cybertechsolutions.fr",
                "marc.girard@cybertechsolutions.fr",
                "Marc"
        );

        if (employeurId3 == -1) {
            Toast.makeText(this, "L'employeur Marc Girard existe déjà dans la base de données", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Nouvel employeur Marc Girard inséré avec l'ID : " + employeurId3, Toast.LENGTH_LONG).show();
        }

// Insérer un nouvel employeur (employeur 4)
        long employeurId4 = databaseHelper.insertEmployeur(
                "Isabelle Mercier",
                "ConseilPro",
                "0687654321",
                "8 Rue du Jardin Public, 94000 Paris, France",
                "https://www.conseilpro.fr",
                "isabelle.mercier@conseilpro.fr",
                "Isabelle"
        );

        if (employeurId4 == -1) {
            Toast.makeText(this, "L'employeur Isabelle Mercier existe déjà dans la base de données", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Nouvel employeur Isabelle Mercier inséré avec l'ID : " + employeurId4, Toast.LENGTH_LONG).show();
        }

// Insérer un nouvel employeur (employeur 5)
        long employeurId5 = databaseHelper.insertEmployeur(
                "Pierre Dubois",
                "TechSolutions",
                "0676543210",
                "12 Rue de la Paix, 75002 Paris, France",
                "https://www.techsolutions.fr",
                "pierre.dubois@techsolutions.fr",
                "Pierre"
        );

        if (employeurId5 == -1) {
            Toast.makeText(this, "L'employeur Pierre Dubois existe déjà dans la base de données", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Nouvel employeur Pierre Dubois inséré avec l'ID : " + employeurId5, Toast.LENGTH_LONG).show();
        }




// Insérer une nouvelle offre
        long offreId = databaseHelper.insertOffre(
                "Developpeur Web",
                "Nous recherchons un développeur web expérimenté pour rejoindre notre équipe dynamique. Vous serez responsable de la conception, du développement et de la maintenance de nos applications web.",
                "Développement Web",
                "Montpellier",
                getDateFromString("01/06/2023"), // Date de début
                getDateFromString("31/12/2023"), // Date de fin
                1 // ID de l'employeur Acme Inc.
        );

        if (offreId == -1) {
            // L'offre existe déjà, afficher un message
            Toast.makeText(this, "L'offre existe déjà dans la base de données", Toast.LENGTH_SHORT).show();
        } else {
            // L'offre a été insérée avec succès, afficher un message
            Toast.makeText(this, "Nouvelle offre insérée avec l'ID : " + offreId, Toast.LENGTH_SHORT).show();
        }

// Insérer une nouvelle offre
        long offreId2 = databaseHelper.insertOffre(
                "Gestionnaire de Projet",
                "Nous recherchons un gestionnaire de projet expérimenté pour superviser et coordonner nos projets de développement logiciel. Vous serez responsable de la planification, de l'organisation et du suivi des projets, ainsi que de la gestion de l'équipe et de la communication avec les parties prenantes.",
                "Gestion de Projet",
                "Montpellier",
                getDateFromString("01/07/2023"), // Date de début
                getDateFromString("31/12/2023"), // Date de fin
                1 // ID de l'employeur Acme Inc.
        );

        if (offreId2 == -1) {
            // L'offre existe déjà, afficher un message
            Toast.makeText(this, "L'offre existe déjà dans la base de données", Toast.LENGTH_SHORT).show();
        } else {
            // L'offre a été insérée avec succès, afficher un message
            Toast.makeText(this, "Nouvelle offre insérée avec l'ID : " + offreId2, Toast.LENGTH_SHORT).show();
        }

// Insérer une nouvelle offre
        long offreId3 = databaseHelper.insertOffre(
                "Analyste Commercial",
                "Nous recherchons un analyste commercial expérimenté pour rejoindre notre équipe de vente. Vous serez responsable de l'analyse des données de vente, de l'identification des tendances et des opportunités, ainsi que de la présentation des rapports aux équipes de direction et de vente.",
                "Analyse Commerciale",
                "Montpellier",
                getDateFromString("15/06/2023"), // Date de début
                getDateFromString("31/06/2023"), // Date de fin
                1 // ID de l'employeur Acme Inc.
        );

        if (offreId3 == -1) {
            // L'offre existe déjà, afficher un message
            Toast.makeText(this, "L'offre existe déjà dans la base de données", Toast.LENGTH_SHORT).show();
        } else {
            // L'offre a été insérée avec succès, afficher un message
            Toast.makeText(this, "Nouvelle offre insérée avec l'ID : " + offreId3, Toast.LENGTH_SHORT).show();
        }

// Insérer une nouvelle offre
        long offreId4 = databaseHelper.insertOffre(
                "Responsable des Ressources Humaines",
                "Nous recherchons un responsable des ressources humaines expérimenté pour gérer et développer notre équipe. Vous serez responsable du recrutement, de la formation, de la gestion des performances et de la conformité aux réglementations en matière d'emploi.",
                "Ressources Humaines",
                "Paris",
                getDateFromString("01/08/2023"), // Date de début
                getDateFromString("31/12/2023"), // Date de fin
                1 // ID de l'employeur Acme Inc.
        );

        if (offreId4 == -1) {
            // L'offre existe déjà, afficher un message
            Toast.makeText(this, "L'offre existe déjà dans la base de données", Toast.LENGTH_SHORT).show();
        } else {
            // L'offre a été insérée avec succès, afficher un message
            Toast.makeText(this, "Nouvelle offre insérée avec l'ID : " + offreId4, Toast.LENGTH_SHORT).show();
        }

// Offre pour l'employeur 2 (Sophie Lefebvre)
        long offreId5 = databaseHelper.insertOffre(
                "Développeur Mobile",
                "Nous recherchons un développeur mobile expérimenté pour rejoindre notre équipe. Vous serez responsable de la conception, du développement et de la maintenance de nos applications mobiles pour iOS et Android.",
                "Développement Mobile",
                "Toulouse",
                getDateFromString("01/06/2023"), // Date de début
                getDateFromString("31/12/2023"), // Date de fin
                2 // ID de l'employeur Sophie Lefebvre
        );

        if (offreId5 == -1) {
            Toast.makeText(this, "L'offre existe déjà dans la base de données", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Nouvelle offre insérée avec l'ID : " + offreId5, Toast.LENGTH_SHORT).show();
        }

// Offre pour l'employeur 3 (Marc Girard)
        long offreId6 = databaseHelper.insertOffre(
                "Analyste Cybersécurité",
                "Nous recherchons un analyste cybersécurité expérimenté pour rejoindre notre équipe. Vous serez responsable de l'analyse des risques de sécurité, de la mise en place de mesures de protection et de la gestion des incidents de sécurité.",
                "Cybersécurité",
                "Toulouse",
                getDateFromString("15/07/2023"), // Date de début
                getDateFromString("31/12/2023"), // Date de fin
                3 // ID de l'employeur Marc Girard
        );

        if (offreId6 == -1) {
            Toast.makeText(this, "L'offre existe déjà dans la base de données", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Nouvelle offre insérée avec l'ID : " + offreId6, Toast.LENGTH_SHORT).show();
        }

// Offre pour l'employeur 4 (Isabelle Mercier)
        long offreId7 = databaseHelper.insertOffre(
                "Consultant en Stratégie Digitale",
                "Nous recherchons un consultant en stratégie digitale expérimenté pour rejoindre notre équipe. Vous serez responsable de l'élaboration de stratégies digitales pour nos clients, de l'analyse des données et de la recommandation de solutions innovantes.",
                "Conseil en Stratégie Digitale",
                "Paris",
                getDateFromString("01/08/2023"), // Date de début
                getDateFromString("31/12/2023"), // Date de fin
                4 // ID de l'employeur Isabelle Mercier
        );

        if (offreId7 == -1) {
            Toast.makeText(this, "L'offre existe déjà dans la base de données", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Nouvelle offre insérée avec l'ID : " + offreId7, Toast.LENGTH_SHORT).show();
        }

// Offre pour l'employeur 5 (Pierre Dubois)
        long offreId8 = databaseHelper.insertOffre(
                "Ingénieur DevOps",
                "Nous recherchons un ingénieur DevOps expérimenté pour rejoindre notre équipe. Vous serez responsable de la mise en place et de la gestion des processus d'intégration continue et de déploiement automatisé pour nos applications.",
                "DevOps",
                "Paris",
                getDateFromString("15/06/2023"), // Date de début
                getDateFromString("30/09/2023"), // Date de fin
                5 // ID de l'employeur Pierre Dubois
        );

        if (offreId8 == -1) {
            Toast.makeText(this, "L'offre existe déjà dans la base de données", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Nouvelle offre insérée avec l'ID : " + offreId8, Toast.LENGTH_SHORT).show();
        }


    }

    // Création du format de date
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    // Méthode pour convertir une chaîne de caractères en objet Date
    private Date getDateFromString(String dateString) {
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void showLocationPermissionDialog() {
        // Afficher une boîte de dialogue demandant l'autorisation de la localisation
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Autoriser la localisation");
        builder.setMessage("Pour afficher des annonces pertinentes près de chez vous, nous avons besoin d'accéder à votre localisation.");
        builder.setPositiveButton("Accepter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // L'utilisateur accepte, enregistrer l'acceptation dans les préférences
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit();
                editor.putBoolean(PREF_LOCATION_ACCEPTED, true);
                editor.apply();
                locationAccepted = true;
                // Afficher les annonces en fonction de la localisation
                displayLocationBasedAds();
            }
        });
        builder.setNegativeButton("Refuser", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // L'utilisateur refuse, afficher des annonces sélectionnées par un algorithme
                displayDefaultAds();
            }
        });
        builder.show();
    }


    private void displayLocationBasedAds() {
        // Afficher les annonces basées sur la localisation de l'utilisateur
        // Vous pouvez implémenter cette fonction en fonction de votre logique métier
    }

    private void displayDefaultAds() {
        // Afficher des annonces sélectionnées par défaut
        // Cela pourrait inclure des annonces récentes, les plus populaires, etc.
        // Implémentez cette fonction en fonction de votre algorithme de sélection
    }



}