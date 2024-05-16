package admd.interim.employeur;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import admd.interim.R;
import admd.interim.logic.DatabaseHelper;
import admd.interim.logic.Employeur;
import admd.interim.logic.Offre;

public class EspaceEmployeurActivity extends AppCompatActivity {

    private Button buttonCreerOffre, buttonGererOffre, buttonGererCandidatures, buttonGererCandidaturesAcceptes;
    private TextView textViewEmployeurNom, textViewEmployeurDetails, textViewMonProfil,textViewDeconnexion;
    private ImageButton buttonDeconnexion, buttonMonProfil;
    private List<Offre> offres; // Liste pour stocker les offres
    private int selectedOffreId; // ID de l'offre sélectionnée

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_espace_employeur);

        int employeurId = getIntent().getIntExtra("EMPLOYEUR_ID", -1);
        System.out.println("EspaceEmployeurActivity: Employeur ID: " + employeurId);

        // Initialiser les offres pour cet employeur
        DatabaseHelper db = new DatabaseHelper(this);
        offres = db.getOffresByEmployeurId(employeurId);

        setupButtons(employeurId);
        displayEmployeurDetails(employeurId);
    }

    private void displayEmployeurDetails(int employeurId) {
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
    }

    private void setupButtons(int employeurId) {
        buttonCreerOffre = findViewById(R.id.buttonCreerOffre);
        buttonCreerOffre.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreationOffreActivity.class);
            intent.putExtra("EMPLOYEUR_ID", employeurId);
            startActivity(intent);
        });

        buttonGererOffre = findViewById(R.id.buttonGererOffre);
        buttonGererOffre.setOnClickListener(v -> {
            Intent intent = new Intent(this, GestionOffreActivity.class);
            intent.putExtra("EMPLOYEUR_ID", employeurId);
            startActivity(intent);
        });

        buttonGererCandidatures = findViewById(R.id.buttonGererCandidatures);
        buttonGererCandidatures.setOnClickListener(v -> {
            // Sélectionner une offre ici, par exemple, la première offre de la liste
            if (offres != null && !offres.isEmpty()) {
                selectedOffreId = offres.get(0).getId(); // Utiliser la première offre comme exemple
                Intent intent = new Intent(this, GestionCandidatureActivity.class);
                intent.putExtra("EMPLOYEUR_ID", employeurId);
                startActivity(intent);

            } else {
                System.out.println("Aucune offre disponible pour cet employeur");
            }
        });

        buttonGererCandidaturesAcceptes = findViewById(R.id.buttonGererCandidaturesAcceptes);
        buttonGererCandidaturesAcceptes.setOnClickListener(v -> {
            Intent intent = new Intent(this, CandidaturesAccepteesActivity.class);
            intent.putExtra("EMPLOYEUR_ID", employeurId);
            startActivity(intent);
        });

        buttonDeconnexion = findViewById(R.id.buttonDeconnexion);
        buttonDeconnexion.setOnClickListener(v -> {
            finish();
        });

        textViewDeconnexion = findViewById(R.id.textViewDeconnexion);
        textViewDeconnexion.setOnClickListener(v -> {
            finish();
        });

        buttonMonProfil = findViewById(R.id.buttonMonProfil);
        buttonMonProfil.setOnClickListener(v -> {
            // Par exemple, ouvrir une nouvelle activité pour afficher les détails du profil
            Intent intent = new Intent(this, MonProfilEmployeurActivity.class);
            intent.putExtra("EMPLOYEUR_ID", employeurId);
            startActivity(intent);
        });

        textViewMonProfil = findViewById(R.id.textViewMonProfil);
        textViewMonProfil.setOnClickListener(v -> {
            // Par exemple, ouvrir une nouvelle activité pour afficher les détails du profil
            Intent intent = new Intent(this, MonProfilEmployeurActivity.class);
            intent.putExtra("EMPLOYEUR_ID", employeurId);
            startActivity(intent);
        });
    }


}