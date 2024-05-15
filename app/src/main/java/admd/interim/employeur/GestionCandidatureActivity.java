package admd.interim.employeur;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import admd.interim.R;
import admd.interim.logic.Candidature;
import admd.interim.logic.CandidatureAdapter;
import admd.interim.logic.DatabaseHelper;

public class GestionCandidatureActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCandidatures;
    private CandidatureAdapter candidatureAdapter;
    private DatabaseHelper databaseHelper;
    private int employeurId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_candidature);

        recyclerViewCandidatures = findViewById(R.id.recyclerViewCandidatures);
        recyclerViewCandidatures.setLayoutManager(new LinearLayoutManager(this));

        // Récupérer l'ID de l'employeur à partir de l'Intent
        employeurId = getIntent().getIntExtra("EMPLOYEUR_ID", 0);
        Log.d("GestionCandidature", "Employeur ID: " + employeurId);

        databaseHelper = new DatabaseHelper(this);

        // Récupérer les candidatures pour l'employeur spécifique
        List<Candidature> candidatures = databaseHelper.getCandidaturesParEmployeur(employeurId);

        if (candidatures.isEmpty()) {
            Log.d("GestionCandidature", "No candidatures found for employer ID: " + employeurId);
            showNoCandidaturesDialog();
        } else {
            Log.d("GestionCandidature", "Candidatures found: " + candidatures.size());
            candidatureAdapter = new CandidatureAdapter(this, candidatures);
            recyclerViewCandidatures.setAdapter(candidatureAdapter);

            candidatureAdapter.setOnItemClickListener(new CandidatureAdapter.OnItemClickListener() {
                @Override
                public void onAcceptClick(int position) {
                    Candidature candidatureAcceptee = candidatures.get(position);

                    // Passer les informations du candidat à l'activité CandidaturesAccepteesActivity
                    Intent intent = new Intent(GestionCandidatureActivity.this, CandidaturesAccepteesActivity.class);
                    intent.putExtra("NOM_PRENOM", candidatureAcceptee.getNomCandidat() + " " + candidatureAcceptee.getPrenomCandidat());
                    intent.putExtra("EMAIL", candidatureAcceptee.getEmailCandidat());
                    startActivity(intent);
                }

                @Override
                public void onRejectClick(int position) {
                    // Traitement pour refuser la candidature à la position donnée
                    Candidature candidatureRejetee = candidatures.get(position);
                    // Implémentez la logique nécessaire pour refuser la candidature
                }

                @Override
                public void onRespondClick(int position) {
                    // Traitement pour répondre à la candidature à la position donnée
                    Candidature candidatureARespondre = candidatures.get(position);
                    // Implémentez la logique nécessaire pour répondre à la candidature
                }
            });
        }
    }

    private void showNoCandidaturesDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Aucune candidature en attente")
                .setMessage("Il n'y a aucune candidature en attente pour le moment.")
                .setPositiveButton("OK", (dialog, which) -> finish())
                .setCancelable(false) // Empêche la fermeture du dialogue en cliquant en dehors
                .show();
    }

}
