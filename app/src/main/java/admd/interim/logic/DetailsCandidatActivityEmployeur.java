package admd.interim.logic;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import admd.interim.R;

public class DetailsCandidatActivityEmployeur extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailsemployeur_candidat);

        // Récupérer les informations du candidat et de l'offre depuis l'intent
        String nom = getIntent().getStringExtra("NOM");
        String prenom = getIntent().getStringExtra("PRENOM");
        String email = getIntent().getStringExtra("EMAIL");
        String numeroTelephone = getIntent().getStringExtra("NUMERO_TELEPHONE");
        String dateNaissance = getIntent().getStringExtra("DATE_NAISSANCE");
        String nationalite = getIntent().getStringExtra("NATIONALITE");
        String ville = getIntent().getStringExtra("VILLE");

        String titreOffre = getIntent().getStringExtra("TITRE_OFFRE");
        String descriptionOffre = getIntent().getStringExtra("DESCRIPTION_OFFRE");
        String metier = getIntent().getStringExtra("METIER");
        String lieu = getIntent().getStringExtra("LIEU");
        String dateDebut = getIntent().getStringExtra("DATE_DEBUT");
        String dateFin = getIntent().getStringExtra("DATE_FIN");

        // Afficher les informations du candidat
        TextView textViewNomPrenom = findViewById(R.id.textViewNomPrenom);
        TextView textViewEmail = findViewById(R.id.textViewEmail);
        TextView textViewNumeroTelephone = findViewById(R.id.textViewNumeroTelephone);
        TextView textViewDateNaissance = findViewById(R.id.textViewDateNaissance);
        TextView textViewNationalite = findViewById(R.id.textViewNationalite);
        TextView textViewVille = findViewById(R.id.textViewVille);

        textViewNomPrenom.setText(nom + " " + prenom);
        textViewEmail.setText(email);
        textViewNumeroTelephone.setText(numeroTelephone);
        textViewDateNaissance.setText(dateNaissance);
        textViewNationalite.setText(nationalite);
        textViewVille.setText(ville);

        // Afficher les informations de l'offre
        TextView textViewTitreOffre = findViewById(R.id.textViewTitreOffre);
        TextView textViewDescriptionOffre = findViewById(R.id.textViewDescriptionOffre);
        TextView textViewMetier = findViewById(R.id.textViewMetier);
        TextView textViewLieu = findViewById(R.id.textViewLieu);
        TextView textViewDateDebut = findViewById(R.id.textViewDateDebut);
        TextView textViewDateFin = findViewById(R.id.textViewDateFin);

        textViewTitreOffre.setText(titreOffre);
        textViewDescriptionOffre.setText(descriptionOffre);
        textViewMetier.setText(metier);
        textViewLieu.setText(lieu);
        textViewDateDebut.setText(dateDebut);
        textViewDateFin.setText(dateFin);
    }
}
