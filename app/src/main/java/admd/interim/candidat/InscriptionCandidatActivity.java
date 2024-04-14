package admd.interim.candidat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import admd.interim.R;
import admd.interim.logic.DatabaseHelper;

public class InscriptionCandidatActivity extends AppCompatActivity {

    private EditText editTextNom, editTextPrenom, editTextDateNaissance, editTextNationalite, editTextNumeroTelephone, editTextEmail, editTextVille, editTextCV;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription_candidat);

        editTextNom = findViewById(R.id.editText_nom);
        editTextPrenom = findViewById(R.id.editText_prenom);
        editTextDateNaissance = findViewById(R.id.editText_date_naissance);
        editTextNationalite = findViewById(R.id.editText_nationalite);
        editTextNumeroTelephone = findViewById(R.id.editText_numero_telephone);
        editTextEmail = findViewById(R.id.editText_email);
        editTextVille = findViewById(R.id.editText_ville);
        editTextCV = findViewById(R.id.editText_cv);

        Button buttonInscrire = findViewById(R.id.button_inscrire);
        buttonInscrire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inscrireCandidat();
            }
        });

        databaseHelper = new DatabaseHelper(this);
    }

    private void inscrireCandidat() {
        String nom = editTextNom.getText().toString().trim();
        String prenom = editTextPrenom.getText().toString().trim();
        String dateNaissance = editTextDateNaissance.getText().toString().trim();
        String nationalite = editTextNationalite.getText().toString().trim();
        String numeroTelephone = editTextNumeroTelephone.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String ville = editTextVille.getText().toString().trim();
        String cv = editTextCV.getText().toString().trim();

        long candidatId = databaseHelper.insertCandidat(nom, prenom, dateNaissance, nationalite, numeroTelephone, email, ville, cv);

        if (candidatId == -1) {
            Toast.makeText(this, "Le candidat existe déjà dans la base de données", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Candidat inscrit avec succès", Toast.LENGTH_SHORT).show();
            finish(); // Fermer l'activité après l'inscription réussie
        }
    }
}
