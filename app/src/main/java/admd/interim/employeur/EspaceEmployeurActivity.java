package admd.interim.employeur;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import admd.interim.R;
import admd.interim.logic.DatabaseHelper;

public class EspaceEmployeurActivity extends AppCompatActivity {

    private EditText editTextNom, editTextEntreprise, editTextEmail, editTextTelephone, editTextAdresse, editTextLienPublic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espace_employeur);

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
                String nom = editTextNom.getText().toString();
                String entreprise = editTextEntreprise.getText().toString();
                String email = editTextEmail.getText().toString();
                String telephone = editTextTelephone.getText().toString();
                String adresse = editTextAdresse.getText().toString();
                String lienPublic = editTextLienPublic.getText().toString();

                // Enregistrez les informations dans votre base de données
                enregistrerEmployeur(nom, entreprise, email, telephone, adresse, lienPublic);
            }
        });
    }

    private void enregistrerEmployeur(String nom, String entreprise, String email, String telephone, String adresse, String lienPublic) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        long employeurId = databaseHelper.insertEmployeur(nom, entreprise, email, telephone, adresse, lienPublic);

        if (employeurId == -1) {
            // L'employeur existe déjà, afficher un message
            Toast.makeText(this, "L'employeur existe déjà dans la base de données", Toast.LENGTH_SHORT).show();
        } else {
            // L'employeur a été inséré avec succès, afficher un message
            Toast.makeText(this, "Employeur enregistré avec succès", Toast.LENGTH_SHORT).show();
        }
    }
}
