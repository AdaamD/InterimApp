package admd.interim.employeur;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import admd.interim.R;
import admd.interim.logic.DatabaseHelper;
import admd.interim.logic.Employeur;

public class EspaceEmployeurActivity extends AppCompatActivity {

    private Button buttonCreerOffre, buttonGererOffre, buttonGererCandidatures, buttonGererCandidaturesAcceptes;
    private TextView textViewEmployeurNom, textViewEmployeurDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espace_employeur);

        setupButtons();
        displayEmployeurDetails();
    }

    private void displayEmployeurDetails() {
        int employeurId = getIntent().getIntExtra("EMPLOYEUR_ID", -1);
        System.out.println("EspaceEmployeurActivity: Employeur ID: " + employeurId);

        if (employeurId != -1) {
            DatabaseHelper db = new DatabaseHelper(this);
            Employeur employeur = db.getEmployeurById(employeurId);
            if (employeur != null) {
                textViewEmployeurNom = findViewById(R.id.textViewEmployeurNom);
                textViewEmployeurDetails = findViewById(R.id.textViewEmployeurDetails);
                textViewEmployeurNom.setText(employeur.getNom());
                textViewEmployeurDetails.setText("Entreprise: " + employeur.getEntreprise());


            } else {
                System.out.println("EspaceEmployeurActivity: Aucune donnée pour l'employeur");
            }
        } else {
            System.out.println("EspaceEmployeurActivity: ID employeur non trouvé");
        }
    }


    private void setupButtons() {
        buttonCreerOffre = findViewById(R.id.buttonCreerOffre);
        buttonCreerOffre.setOnClickListener(v -> startActivity(new Intent(this, CreationOffreActivity.class)));

        buttonGererOffre = findViewById(R.id.buttonGererOffre);
        buttonGererOffre.setOnClickListener(v -> startActivity(new Intent(this, GestionOffreActivity.class)));

        buttonGererCandidatures = findViewById(R.id.buttonGererCandidatures);
        buttonGererCandidatures.setOnClickListener(v -> startActivity(new Intent(this, GestionCandidatureActivity.class)));

        buttonGererCandidaturesAcceptes = findViewById(R.id.buttonGererCandidaturesAcceptes);
        buttonGererCandidaturesAcceptes.setOnClickListener(v -> startActivity(new Intent(this, CandidaturesAccepteesActivity.class)));
    }
}
