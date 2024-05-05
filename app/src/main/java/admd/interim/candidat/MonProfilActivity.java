package admd.interim.candidat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import admd.interim.R;

public class MonProfilActivity extends AppCompatActivity {

    private TextView textViewNomCandidat, textViewPrenomCandidat, textViewDateNaissanceCandidat,
            textViewNationaliteCandidat, textViewNumeroTelephoneCandidat, textViewEmailCandidat,
            textViewVilleCandidat, textViewCVCandidat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_profil);

        // Récupérer les informations du candidat à partir de l'Intent
        String nom = getIntent().getStringExtra("candidat_nom");
        String prenom = getIntent().getStringExtra("candidat_prenom");
        String dateNaissance = getIntent().getStringExtra("candidat_date_naissance");
        int candidatId = getIntent().getIntExtra("candidat_id", 0);
        String nationalite = getIntent().getStringExtra("candidat_nationalite");
        String numeroTelephone = getIntent().getStringExtra("candidat_numero_telephone");
        String email = getIntent().getStringExtra("candidat_email");
        String ville = getIntent().getStringExtra("candidat_ville");
        String cv = getIntent().getStringExtra("candidat_cv");

        // Initialiser les TextView
        textViewNomCandidat = findViewById(R.id.textViewNomCandidat);
        textViewPrenomCandidat = findViewById(R.id.textViewPrenomCandidat);
        textViewDateNaissanceCandidat = findViewById(R.id.textViewDateNaissanceCandidat);
        textViewNationaliteCandidat = findViewById(R.id.textViewNationaliteCandidat);
        textViewNumeroTelephoneCandidat = findViewById(R.id.textViewNumeroTelephoneCandidat);
        textViewEmailCandidat = findViewById(R.id.textViewEmailCandidat);
        textViewVilleCandidat = findViewById(R.id.textViewVilleCandidat);
        textViewCVCandidat = findViewById(R.id.textViewCVCandidat);

        // Afficher les informations du candidat
        textViewNomCandidat.setText(nom);
        textViewPrenomCandidat.setText(prenom);
        textViewDateNaissanceCandidat.setText(Html.fromHtml("<b>Date de naissance :</b> " + dateNaissance));
        textViewNationaliteCandidat.setText(Html.fromHtml("<b>Nationalité :</b> " + nationalite));
        textViewNumeroTelephoneCandidat.setText(Html.fromHtml("<b>Numéro de téléphone :</b> " + numeroTelephone));
        textViewEmailCandidat.setText(Html.fromHtml("<b>Email :</b> " + email));
        textViewVilleCandidat.setText(Html.fromHtml("<b>Ville :</b> " + ville));
        textViewCVCandidat.setText(Html.fromHtml("<b>CV :</b> " + cv));
    }
}
