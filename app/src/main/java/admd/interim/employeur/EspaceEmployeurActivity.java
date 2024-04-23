package admd.interim.employeur;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import admd.interim.R;
import android.widget.Button;


public class EspaceEmployeurActivity extends AppCompatActivity {

    private Button buttonCreerOffre;
    private  Button buttonGererOffre;
    private Button buttonGererCandidatures;

    private Button buttonGererCandidaturesAcceptes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espace_employeur);

        // Récupérer le TextView pour afficher le message de réussite
        TextView textViewConnectionSuccess = findViewById(R.id.textViewConnectionSuccess);

        // Récupérer le nom de l'employeur inscrit avec succès depuis l'intent
        String nomEmployeur = getIntent().getStringExtra("NOM_ENTREPRISE"); // Remplacez "NOM_ENTREPRISE" par la clé appropriée

        // Personnaliser le message de réussite avec le nom de l'employeur
        if (nomEmployeur != null && !nomEmployeur.isEmpty()) {
            String messageSuccess = "Bienvenue, " + nomEmployeur + " ! Inscription réussie.";
            textViewConnectionSuccess.setText(messageSuccess);
        } else {
            textViewConnectionSuccess.setText("Inscription réussie.");
        }

        buttonCreerOffre = findViewById(R.id.buttonCreerOffre);
        buttonCreerOffre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer une Intent pour naviguer vers CreationOffreActivity
                Intent intent = new Intent(EspaceEmployeurActivity.this, CreationOffreActivity.class);
                startActivity(intent);
            }
        });

        buttonGererOffre = findViewById(R.id.buttonGererOffre);
        buttonGererOffre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer une Intent pour naviguer vers GestionOffreActivity
                Intent intent = new Intent(EspaceEmployeurActivity.this, GestionOffreActivity.class);
                startActivity(intent);
            }
        });

        buttonGererCandidatures = findViewById(R.id.buttonGererCandidatures);
        buttonGererCandidatures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EspaceEmployeurActivity.this, GestionCandidatureActivity.class);
                startActivity(intent);
            }
        });

        buttonGererCandidaturesAcceptes = findViewById(R.id.buttonGererCandidaturesAcceptes);
        buttonGererCandidaturesAcceptes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EspaceEmployeurActivity.this, CandidaturesAccepteesActivity.class);
                startActivity(intent);
            }
        });


    }
}