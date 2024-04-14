package admd.interim.candidat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import admd.interim.R;
import admd.interim.logic.DatabaseHelper;

public class ConnexionCandidatActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion_candidat);

        editTextEmail = findViewById(R.id.editText_email);
        Button buttonConnexion = findViewById(R.id.button_connexion);

        databaseHelper = new DatabaseHelper(this);

        buttonConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();

                if (databaseHelper.candidatExistsByEmail(email)) {
                    // Connexion réussie, démarrer EspaceCandidatActivity
                    showSuccessDialog();
                } else {
                    // Adresse email invalide
                    showInvalidCredentialsDialog();
                }
            }
        });
    }

    // Affiche une boîte de dialogue pour informer l'utilisateur que les informations de connexion saisies sont incorrectes
    private void showInvalidCredentialsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Informations incorrectes");
        builder.setMessage("Les informations de connexion saisies sont incorrectes. Veuillez réessayer.");
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Affiche une boîte de dialogue pour informer l'utilisateur que la connexion a réussi
    private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Connexion réussie");
        builder.setMessage("Vous allez être redirigé vers l'espace candidat.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Démarrer EspaceCandidatActivity
                Intent intent = new Intent(ConnexionCandidatActivity.this, EspaceCandidatActivity.class);
                startActivity(intent);
            }
        });
        builder.setCancelable(false); // Empêcher la fermeture du dialogue en cliquant à l'extérieur
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
