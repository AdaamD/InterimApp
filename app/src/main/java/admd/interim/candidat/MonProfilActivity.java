package admd.interim.candidat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import admd.interim.R;
import admd.interim.logic.DatabaseHelper;
import admd.interim.logic.Candidat;

public class MonProfilActivity extends AppCompatActivity {

    private TextView textViewNomCandidat, textViewPrenomCandidat, textViewDateNaissanceCandidat,
            textViewNationaliteCandidat, textViewNumeroTelephoneCandidat, textViewEmailCandidat,
            textViewVilleCandidat, textViewCVCandidat;

    private EditText editTextNom, editTextPrenom, editTextDateNaissance, editTextNationalite,
            editTextNumeroTelephone, editTextEmail, editTextVille, editTextCV;

    private Button buttonEnregistrer;
    private ImageButton buttonModifier;

    private DatabaseHelper databaseHelper;
    private int candidatId;

    private boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_profil);

        // Récupérer les informations du candidat à partir de l'Intent
        String nom = getIntent().getStringExtra("candidat_nom");
        String prenom = getIntent().getStringExtra("candidat_prenom");
        String dateNaissance = getIntent().getStringExtra("candidat_date_naissance");
        candidatId = getIntent().getIntExtra("candidat_id", 0);
        String nationalite = getIntent().getStringExtra("candidat_nationalite");
        String numeroTelephone = getIntent().getStringExtra("candidat_numero_telephone");
        String email = getIntent().getStringExtra("candidat_email");
        String ville = getIntent().getStringExtra("candidat_ville");
        String cv = getIntent().getStringExtra("candidat_cv");

        // Initialiser les TextView et EditText
        textViewNomCandidat = findViewById(R.id.textViewNomCandidat);
        textViewPrenomCandidat = findViewById(R.id.textViewPrenomCandidat);
        textViewDateNaissanceCandidat = findViewById(R.id.textViewDateNaissanceCandidat);
        textViewNationaliteCandidat = findViewById(R.id.textViewNationaliteCandidat);
        textViewNumeroTelephoneCandidat = findViewById(R.id.textViewNumeroTelephoneCandidat);
        textViewEmailCandidat = findViewById(R.id.textViewEmailCandidat);
        textViewVilleCandidat = findViewById(R.id.textViewVilleCandidat);
        textViewCVCandidat = findViewById(R.id.textViewCVCandidat);

        editTextNom = findViewById(R.id.editTextNom);
        editTextPrenom = findViewById(R.id.editTextPrenom);
        editTextDateNaissance = findViewById(R.id.editTextDateNaissance);
        editTextNationalite = findViewById(R.id.editTextNationalite);
        editTextNumeroTelephone = findViewById(R.id.editTextNumeroTelephone);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextVille = findViewById(R.id.editTextVille);
        editTextCV = findViewById(R.id.editTextCV);

        buttonEnregistrer = findViewById(R.id.buttonEnregistrer);
        buttonModifier = findViewById(R.id.buttonModifier);

        databaseHelper = new DatabaseHelper(this);

        // Afficher les informations du candidat
        displayCandidatInfo(nom, prenom, dateNaissance, nationalite, numeroTelephone, email, ville, cv);

        // Ajouter un OnClickListener pour le bouton "Modifier"
        buttonModifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleEditMode();
            }
        });

        // Ajouter un OnClickListener pour le bouton "Enregistrer"
        buttonEnregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCandidatInfo();
                toggleEditMode();
            }
        });
    }

    private void displayCandidatInfo(String nom, String prenom, String dateNaissance, String nationalite,
                                     String numeroTelephone, String email, String ville, String cv) {

        textViewNomCandidat.setText(nom);
        textViewPrenomCandidat.setText(prenom);
        textViewDateNaissanceCandidat.setText(Html.fromHtml("<b>Date de naissance :</b> " + dateNaissance));
        textViewNationaliteCandidat.setText(Html.fromHtml("<b>Nationalité :</b> " + nationalite));
        textViewNumeroTelephoneCandidat.setText(Html.fromHtml("<b>Numéro de téléphone :</b> " + numeroTelephone));
        textViewEmailCandidat.setText(Html.fromHtml("<b>Email :</b> " + email));
        textViewVilleCandidat.setText(Html.fromHtml("<b>Ville :</b> " + ville));
        textViewCVCandidat.setText(Html.fromHtml("<b>CV :</b> " + cv));


        // Masquer les EditText en mode "Affichage"
        editTextNom.setVisibility(View.GONE);
        editTextPrenom.setVisibility(View.GONE);
        editTextDateNaissance.setVisibility(View.GONE);
        editTextNationalite.setVisibility(View.GONE);
        editTextNumeroTelephone.setVisibility(View.GONE);
        editTextEmail.setVisibility(View.GONE);
        editTextVille.setVisibility(View.GONE);
        editTextCV.setVisibility(View.GONE);
        buttonEnregistrer.setVisibility(View.GONE);
    }

    private void toggleEditMode() {
        if (isEditMode) {
            // Passer en mode "Affichage"
            editTextNom.setVisibility(View.GONE);
            editTextPrenom.setVisibility(View.GONE);
            editTextDateNaissance.setVisibility(View.GONE);
            editTextNationalite.setVisibility(View.GONE);
            editTextNumeroTelephone.setVisibility(View.GONE);
            editTextEmail.setVisibility(View.GONE);
            editTextVille.setVisibility(View.GONE);
            editTextCV.setVisibility(View.GONE);
            buttonEnregistrer.setVisibility(View.GONE);
            buttonModifier.setImageResource(R.drawable.crayonmodifier);
        } else {
            // Passer en mode "Modification"
            editTextNom.setVisibility(View.VISIBLE);
            editTextPrenom.setVisibility(View.VISIBLE);
            editTextDateNaissance.setVisibility(View.VISIBLE);
            editTextNationalite.setVisibility(View.VISIBLE);
            editTextNumeroTelephone.setVisibility(View.VISIBLE);
            editTextEmail.setVisibility(View.VISIBLE);
            editTextVille.setVisibility(View.VISIBLE);
            editTextCV.setVisibility(View.VISIBLE);
            buttonEnregistrer.setVisibility(View.VISIBLE);
            buttonModifier.setImageResource(R.drawable.annuler);
        }
        isEditMode = !isEditMode;
    }

    private void updateCandidatInfo() {
        String nouveauNom = editTextNom.getText().toString();
        String nouveauPrenom = editTextPrenom.getText().toString();
        String nouveaueDateNaissance = editTextDateNaissance.getText().toString();
        String nouvelleNationalite = editTextNationalite.getText().toString();
        String nouveauNumeroTelephone = editTextNumeroTelephone.getText().toString();
        String nouveauEmail = editTextEmail.getText().toString();
        String nouvelleVille = editTextVille.getText().toString();
        String nouveauCV = editTextCV.getText().toString();

        Candidat candidat = new Candidat(candidatId, nouveauNom, nouveauPrenom, nouveaueDateNaissance,
                nouvelleNationalite, nouveauNumeroTelephone, nouveauEmail, nouvelleVille, nouveauCV);
        int rowsUpdated = databaseHelper.updateCandidat(candidatId, nouveauNom, nouveauPrenom, nouveaueDateNaissance, nouvelleNationalite, nouveauNumeroTelephone, nouveauEmail, nouvelleVille, nouveauCV);

        if (rowsUpdated > 0) {
            System.out.println("Mise à jour réussie pour le candidat avec l'id : " + candidatId);

            // Afficher une boîte de dialogue pour indiquer que les informations ont été mises à jour avec succès
            new android.app.AlertDialog.Builder(this)
                    .setTitle("Informations mises à jour")
                    .setMessage("Les informations du candidat ont été mises à jour avec succès.")
                    .setPositiveButton("OK", null)
                    .show();
        } else {
            System.out.println("Mise à jour échouée pour le candidat avec l'id : " + candidatId);
        }
    }

}
