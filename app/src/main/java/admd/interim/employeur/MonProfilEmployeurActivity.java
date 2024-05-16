package admd.interim.employeur;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import admd.interim.R;
import admd.interim.logic.DatabaseHelper;
import admd.interim.logic.Employeur;

public class MonProfilEmployeurActivity extends AppCompatActivity {

    private TextView textViewNomEmployeur, textViewEntrepriseEmployeur, textViewNumeroTelephoneEmployeur,
            textViewAdresseEmployeur, textViewLiensPublicEmployeur, textViewEmailEmployeur;

    private EditText editTextNom, editTextEntreprise, editTextNumeroTelephone, editTextAdresse,
            editTextLiensPublic, editTextEmail;

    private Button buttonEnregistrer;
    private ImageButton buttonModifier;

    private DatabaseHelper databaseHelper;
    private int employeurId;

    private boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_profil_employeur);

        // Récupérer l'ID de l'employeur à partir de l'Intent
        employeurId = getIntent().getIntExtra("EMPLOYEUR_ID", 0);
System.out.println("MonProfilEmployeurActivity: Employeur ID: " + employeurId);


        // Initialiser les TextView et EditText
        initializeViews();

        databaseHelper = new DatabaseHelper(this);

        // Récupérer et afficher les informations de l'employeur
        displayEmployeurInfo();

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
                updateEmployeurInfo();
                toggleEditMode();
            }
        });
    }

    private void initializeViews() {
        textViewNomEmployeur = findViewById(R.id.textViewNomEmployeur);
        textViewEntrepriseEmployeur = findViewById(R.id.textViewEntrepriseEmployeur);
        textViewNumeroTelephoneEmployeur = findViewById(R.id.textViewNumeroTelephoneEmployeur);
        textViewAdresseEmployeur = findViewById(R.id.textViewAdresseEmployeur);
        textViewLiensPublicEmployeur = findViewById(R.id.textViewLiensPublicEmployeur);
        textViewEmailEmployeur = findViewById(R.id.textViewEmailEmployeur);

        editTextNom = findViewById(R.id.editTextNom);
        editTextEntreprise = findViewById(R.id.editTextEntreprise);
        editTextNumeroTelephone = findViewById(R.id.editTextNumeroTelephone);
        editTextAdresse = findViewById(R.id.editTextAdresse);
        editTextLiensPublic = findViewById(R.id.editTextLiensPublic);
        editTextEmail = findViewById(R.id.editTextEmail);

        buttonEnregistrer = findViewById(R.id.buttonEnregistrer);
        buttonModifier = findViewById(R.id.buttonModifier);
    }

    private void displayEmployeurInfo() {
        Employeur employeur = databaseHelper.getEmployeurById(employeurId);
        if (employeur != null) {
            textViewNomEmployeur.setText(employeur.getNom());
            textViewEntrepriseEmployeur.setText(Html.fromHtml("<b>Entreprise :</b> " + employeur.getEntreprise()));
            textViewNumeroTelephoneEmployeur.setText(Html.fromHtml("<b>Numéro de téléphone :</b> " + employeur.getNumeroTelephone()));
            textViewAdresseEmployeur.setText(Html.fromHtml("<b>Adresse :</b> " + employeur.getAdresse()));
            textViewLiensPublicEmployeur.setText(Html.fromHtml("<b>Liens public :</b> " + employeur.getLiensPublic()));
            textViewEmailEmployeur.setText(Html.fromHtml("<b>Email :</b> " + employeur.getEmail()));

            // Masquer les EditText en mode "Affichage"
            editTextNom.setVisibility(View.GONE);
            editTextEntreprise.setVisibility(View.GONE);
            editTextNumeroTelephone.setVisibility(View.GONE);
            editTextAdresse.setVisibility(View.GONE);
            editTextLiensPublic.setVisibility(View.GONE);
            editTextEmail.setVisibility(View.GONE);
            buttonEnregistrer.setVisibility(View.GONE);
        } else {
            System.out.println("MonProfilEmployeurActivity: Aucune donnée pour l'employeur");
        }
    }

    private void toggleEditMode() {
        if (isEditMode) {
            // Passer en mode "Affichage"
            editTextNom.setVisibility(View.GONE);
            editTextEntreprise.setVisibility(View.GONE);
            editTextNumeroTelephone.setVisibility(View.GONE);
            editTextAdresse.setVisibility(View.GONE);
            editTextLiensPublic.setVisibility(View.GONE);
            editTextEmail.setVisibility(View.GONE);
            buttonEnregistrer.setVisibility(View.GONE);
            buttonModifier.setImageResource(R.drawable.crayonmodifier);
        } else {
            // Passer en mode "Modification"
            editTextNom.setVisibility(View.VISIBLE);
            editTextEntreprise.setVisibility(View.VISIBLE);
            editTextNumeroTelephone.setVisibility(View.VISIBLE);
            editTextAdresse.setVisibility(View.VISIBLE);
            editTextLiensPublic.setVisibility(View.VISIBLE);
            editTextEmail.setVisibility(View.VISIBLE);
            buttonEnregistrer.setVisibility(View.VISIBLE);
            buttonModifier.setImageResource(R.drawable.annuler);
        }
        isEditMode = !isEditMode;
    }

    private void updateEmployeurInfo() {
        String nouveauNom = editTextNom.getText().toString();
        String nouvelleEntreprise = editTextEntreprise.getText().toString();
        String nouveauNumeroTelephone = editTextNumeroTelephone.getText().toString();
        String nouvelleAdresse = editTextAdresse.getText().toString();
        String nouveauLiensPublic = editTextLiensPublic.getText().toString();
        String nouveauEmail = editTextEmail.getText().toString();

        Employeur employeur = new Employeur(employeurId, nouveauNom, nouvelleEntreprise, nouveauNumeroTelephone,
                nouvelleAdresse, nouveauLiensPublic, nouveauEmail);
        int rowsUpdated = databaseHelper.updateEmployeur(employeurId, nouveauNom, nouvelleEntreprise, nouveauNumeroTelephone,
                nouvelleAdresse, nouveauLiensPublic, nouveauEmail);

        if (rowsUpdated > 0) {
            System.out.println("Mise à jour réussie pour l'employeur avec l'id : " + employeurId);

            // Afficher une boîte de dialogue pour indiquer que les informations ont été mises à jour avec succès
            new android.app.AlertDialog.Builder(this)
                    .setTitle("Informations mises à jour")
                    .setMessage("Les informations de l'employeur ont été mises à jour avec succès.")
                    .setPositiveButton("OK", (dialog, which) -> {
                        // Recharger l'activité
                        finish();
                        startActivity(getIntent());
                    })
                    .show();
        } else {
            System.out.println("Mise à jour échouée pour l'employeur avec l'id : " + employeurId);
        }
    }

}
