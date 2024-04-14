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

public class ConnexionEmployeurActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion_employeur);

        editTextEmail = findViewById(R.id.editText_email);
        Button buttonConnexion = findViewById(R.id.button_connexion);

        databaseHelper = new DatabaseHelper(this);

        buttonConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();

                if (databaseHelper.employeurExistsByEmail(email)) {
                    // Connexion réussie, démarrer EspaceEmployeurActivity
                    showSuccessDialog();
                } else {
                    // Adresse email invalide
                    showInvalidCredentialsDialog();
                }
            }
        });
    }

    // Ajoutez cette méthode pour afficher un dialogue d'informations incorrectes
    private void showInvalidCredentialsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Informations incorrectes");
        builder.setMessage("Les informations de connexion saisies sont incorrectes. Veuillez réessayer.");
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // Ajoutez cette méthode pour afficher un dialogue de connexion réussie
    private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Connexion réussie");
        builder.setMessage("Vous allez être redirigé vers l'espace employeur.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Démarrer EspaceEmployeurActivity
                Intent intent = new Intent(ConnexionEmployeurActivity.this, EspaceEmployeurActivity.class);
                startActivity(intent);
            }
        });
        builder.setCancelable(false); // Empêcher la fermeture du dialogue en cliquant à l'extérieur
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
