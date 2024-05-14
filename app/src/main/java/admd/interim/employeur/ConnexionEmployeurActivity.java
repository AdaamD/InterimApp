package admd.interim.employeur;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import admd.interim.R;
import admd.interim.logic.DatabaseHelper;
import admd.interim.logic.Employeur;

public class ConnexionEmployeurActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private DatabaseHelper databaseHelper;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion_employeur);

        databaseHelper = new DatabaseHelper(this);
        editTextEmail = findViewById(R.id.editText_email);
        editTextPassword = findViewById(R.id.editTextPassword);
        TextView textViewToggle = findViewById(R.id.textViewToggle);
        Button buttonConnexion = findViewById(R.id.button_connexion);

        // Toggle visibility of password
        textViewToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordVisible) {
                    editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    textViewToggle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_off, 0);
                    isPasswordVisible = false;
                } else {
                    editTextPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    textViewToggle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility, 0);
                    isPasswordVisible = true;
                }
                editTextPassword.setSelection(editTextPassword.getText().length());
            }
        });

        // Handle the login process
        buttonConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginEmployeur();
            }
        });
    }

    private void loginEmployeur() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (databaseHelper.verifyEmployeurCredentials(email, password)) {
            Employeur employeur = databaseHelper.getEmployeurByEmail(email);
            if (employeur != null) {
                showSuccessDialog(employeur);
            } else {
                showInvalidCredentialsDialog();
            }
        } else {
            showInvalidCredentialsDialog();
        }
    }

    private void showInvalidCredentialsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Informations incorrectes");
        builder.setMessage("Les informations de connexion saisies sont incorrectes. Veuillez réessayer.");
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showSuccessDialog(Employeur employeur) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Connexion réussie");
        builder.setMessage("Bienvenue " + employeur.getNom() + ", vous allez être redirigé vers l'espace employeur.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email = editTextEmail.getText().toString().trim();
                Employeur employeur = databaseHelper.getEmployeurByEmail(email);
                if (employeur != null) {
                    int employeurId = employeur.getId();
                    Intent intent = new Intent(ConnexionEmployeurActivity.this, EspaceEmployeurActivity.class);
                    intent.putExtra("EMPLOYEUR_ID", employeurId);
                    System.out.println("ConnexionEmployeurActivity: ID employeur trouvé: " + employeurId);
                    startActivity(intent);
                } else {
                    // Gérer le cas où l'employeur n'est pas trouvé dans la base de données
                    System.out.println("ConnexionEmployeurActivity: Aucun employeur n'a été trouvé avec ces informations de connexion.");
                }
            }
        });
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
