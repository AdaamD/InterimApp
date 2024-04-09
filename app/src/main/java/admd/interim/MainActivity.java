package admd.interim;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import admd.interim.anonyme.AnonymeActivity;
import admd.interim.candidat.EspaceCandidatActivity;
import admd.interim.employeur.EspaceEmployeurActivity;
import admd.interim.logic.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonEspaceCandidatClick = findViewById(R.id.button_espace_candidat);
        buttonEspaceCandidatClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Rediriger vers l'activité EspaceCandidatActivity
                Intent intent = new Intent(MainActivity.this, EspaceCandidatActivity.class);
                startActivity(intent);
            }
        });

        Button buttonEspaceEmployeurClick = findViewById(R.id.button_espace_employeur);
        buttonEspaceEmployeurClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Rediriger vers l'activité EspaceEmployeurActivity
                Intent intent = new Intent(MainActivity.this, EspaceEmployeurActivity.class);
                startActivity(intent);
            }
        });

        TextView textViewContinuerAnonymement = findViewById(R.id.textView_continuer_anonymement);
        textViewContinuerAnonymement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Rediriger vers l'activité AnonymeActivity
                Intent intent = new Intent(MainActivity.this, AnonymeActivity.class);
                startActivity(intent);
            }
        });

        // Instancier la classe DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtenir une instance de SQLiteDatabase en mode écriture
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

    // Insérer un nouvel candidat
        long Candidat1 = databaseHelper.insertCandidat("ADAM", "D", "01/01/2001", "francaise", "0611111111", "adam@mail.fr", "Montpellier", "CV-Adam");

        if (Candidat1 == -1) {
            // Le candidat existe déjà, afficher un message
            Toast.makeText(this, "Le candidat existe déjà dans la base de données", Toast.LENGTH_SHORT).show();
        } else {
            // Le candidat  a été inséré avec succès, afficher un message
            Toast.makeText(this, "Nouvel candidat inséré avec l'ID : " + Candidat1, Toast.LENGTH_SHORT).show();
        }

        long Candidat2 = databaseHelper.insertCandidat("Nabil", "Da", "10/10/2001", "marocain", "0622222222", "nabil@mail.fr", "Montpellier", "CV-Nabil");



    // Insérer un nouvel employeur
        long employeurId = databaseHelper.insertEmployeur(
                "John Doe",
                "Acme Inc.",
                "john.doe@acme.com",
                "0612345678",
                "123 Main Street, Anytown USA",
                "https://www.acme.com"
        );

        if (employeurId == -1) {
            // L'employeur existe déjà, afficher un message
            Toast.makeText(this, "L'employeur existe déjà dans la base de données", Toast.LENGTH_LONG).show();
        } else {
            // L'employeur a été inséré avec succès, afficher un message
            Toast.makeText(this, "Nouvel employeur inséré avec l'ID : " + employeurId, Toast.LENGTH_LONG).show();
        }

    // Insérer une nouvelle offre
        long offreId = databaseHelper.insertOffre(
                "Développeur Web",
                "Nous recherchons un développeur web expérimenté pour rejoindre notre équipe dynamique. Vous serez responsable de la conception, du développement et de la maintenance de nos applications web.",
                "Développement Web",
                "Montpellier",
                "01/06/2023",
                "31/12/2023",
                1
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
                "01/07/2023",
                "31/12/2023",
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
                "15/06/2023",
                "31/12/2023",
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
                "Montpellier",
                "01/08/2023",
                "31/12/2023",
                1 // ID de l'employeur Acme Inc.
        );

        if (offreId4 == -1) {
            // L'offre existe déjà, afficher un message
            Toast.makeText(this, "L'offre existe déjà dans la base de données", Toast.LENGTH_SHORT).show();
        } else {
            // L'offre a été insérée avec succès, afficher un message
            Toast.makeText(this, "Nouvelle offre insérée avec l'ID : " + offreId4, Toast.LENGTH_SHORT).show();
        }


    }



}
