package admd.interim.employeur;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import admd.interim.R;
import admd.interim.logic.DatabaseHelper;

public class InscriptionEmployeurActivity extends AppCompatActivity {

    private EditText editTextNom, editTextEntreprise, editTextEmail, editTextTelephone, editTextAdresse, editTextLienPublic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription_employeur);

        // Référencer les vues
        editTextNom = findViewById(R.id.editTextNom);
        editTextEntreprise = findViewById(R.id.editTextEntreprise);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextTelephone = findViewById(R.id.editTextTelephone);
        editTextAdresse = findViewById(R.id.editTextAdresse);
        editTextLienPublic = findViewById(R.id.editTextLienPublic);

        Button buttonEnregistrer = findViewById(R.id.buttonEnregistrer);
        buttonEnregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupérer les valeurs saisies par l'utilisateur
                String nom = editTextNom.getText().toString().trim();
                String entreprise = editTextEntreprise.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String telephone = editTextTelephone.getText().toString().trim();
                String adresse = editTextAdresse.getText().toString().trim();
                String lienPublic = editTextLienPublic.getText().toString().trim();

                // Vérifier si les champs obligatoires sont remplis
                if (nom.isEmpty() || email.isEmpty()) {
                    showMissingFieldsDialog();
                    return;
                }

                // Enregistrez les informations dans votre base de données
                enregistrerEmployeur(nom, entreprise, email, telephone, adresse, lienPublic);
            }
        });
    }

    private void enregistrerEmployeur(String nom, String entreprise, String email, String telephone, String adresse, String lienPublic) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        long employeurId = databaseHelper.insertEmployeur(nom, entreprise, email, telephone, adresse, lienPublic);

        if (employeurId == -1) {
            // L'employeur existe déjà, afficher une boîte de dialogue
            showEmployeurExistsDialog();
        } else {
            // L'employeur a été inséré avec succès, afficher une boîte de dialogue de succès
            showSuccessDialog();
        }
    }

    // Affiche une boîte de dialogue pour informer l'utilisateur que les champs obligatoires ne sont pas remplis
    private void showMissingFieldsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Champs obligatoires manquants");
        builder.setMessage("Veuillez remplir les champs Nom et Email.");
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Affiche une boîte de dialogue pour informer l'utilisateur que l'employeur existe déjà
    private void showEmployeurExistsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Employeur existant");
        builder.setMessage("Un employeur avec les mêmes informations existe déjà dans la base de données.");
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Affiche une boîte de dialogue pour informer l'utilisateur que l'inscription a réussi
    private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Inscription réussie");
        builder.setMessage("Votre inscription en tant qu'employeur a été effectuée avec succès. Vous allez être redirigé vers l'espace employeur.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Rediriger vers EspaceEmployeurActivity
                Intent intent = new Intent(InscriptionEmployeurActivity.this, EspaceEmployeurActivity.class);
                startActivity(intent);
                finish(); // Fermer l'activité InscriptionEmployeurActivity
            }
        });
        builder.setCancelable(false); // Empêcher la fermeture du dialogue en cliquant à l'extérieur
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
