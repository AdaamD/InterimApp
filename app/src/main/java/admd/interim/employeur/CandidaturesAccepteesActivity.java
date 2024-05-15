package admd.interim.employeur;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import admd.interim.R;
import admd.interim.logic.Candidature;
import admd.interim.logic.DatabaseHelper;
import admd.interim.logic.DetailsCandidatActivity;

public class CandidaturesAccepteesActivity extends AppCompatActivity {

    private TextView textViewNomPrenom;
    private TextView textViewEmail;

    private DatabaseHelper databaseHelper;
    private int employeurId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidatures_acceptees);

        textViewNomPrenom = findViewById(R.id.textViewNomPrenom);
        textViewEmail = findViewById(R.id.textViewEmail);

        employeurId = getIntent().getIntExtra("EMPLOYEUR_ID", 0);

        databaseHelper = new DatabaseHelper(this);

        // Récupérer les candidatures acceptées pour l'employeur
        List<Candidature> candidatures = databaseHelper.getCandidaturesParEmployeurAcceptee(employeurId);

        if (!candidatures.isEmpty()) {
            Candidature candidature = candidatures.get(0); // Afficher la première candidature acceptée
            textViewNomPrenom.setText(candidature.getNomCandidat() + " " + candidature.getPrenomCandidat());
            textViewEmail.setText(candidature.getEmailCandidat());
        } else {
            textViewNomPrenom.setText("Aucune candidature acceptée");
            textViewEmail.setText("");
        }
    }

    public void consulterCandidat(View view) {
        Intent intent = new Intent(this, DetailsCandidatActivity.class);
        // Passez les informations nécessaires pour afficher les détails du candidat
        intent.putExtra("NOM", "John");
        intent.putExtra("PRENOM", "Doe");
        intent.putExtra("EMAIL", "john.doe@example.com");
        startActivity(intent);
    }

    public void afficherOptionsContact(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choisir un moyen de contact");

        // Options de contact
        String[] options = {"Email", "Téléphone", "SMS"};

        builder.setItems(options, (dialog, which) -> {
            switch (which) {
                case 0:
                    // Option Email
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setType("text/plain");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"john.doe@example.com"});
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Sujet de votre email");
                    startActivity(Intent.createChooser(emailIntent, "Choisir une application pour envoyer l'email"));
                    break;
                case 1:
                    // Option Téléphone
                    Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
                    phoneIntent.setData(Uri.parse("tel:0123456789"));
                    startActivity(phoneIntent);
                    break;
                case 2:
                    // Option SMS
                    Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
                    smsIntent.setData(Uri.parse("smsto:0123456789"));
                    smsIntent.putExtra("sms_body", "Message SMS à envoyer");
                    startActivity(smsIntent);
                    break;
            }
        });

        builder.show();
    }

}
