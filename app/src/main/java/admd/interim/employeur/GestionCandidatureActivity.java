package admd.interim.employeur;

import android.os.Bundle;
import android.util.Log;
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
    private int offreId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_candidature);

        recyclerViewCandidatures = findViewById(R.id.recyclerViewCandidatures);
        recyclerViewCandidatures.setLayoutManager(new LinearLayoutManager(this));

        // Récupérer l'ID de l'offre à partir de l'Intent
        offreId = getIntent().getIntExtra("offre_id", 0);
        Log.d("GestionCandidature", "Offer ID: " + offreId);

        databaseHelper = new DatabaseHelper(this);

        // Récupérer les candidatures pour l'offre spécifique
        List<Candidature> candidatures = databaseHelper.getCandidaturesParOffre(offreId);

        if (candidatures.isEmpty()) {
            Log.d("GestionCandidature", "No candidatures found for offer ID: " + offreId);
        } else {
            Log.d("GestionCandidature", "Candidatures found: " + candidatures.size());
        }

        candidatureAdapter = new CandidatureAdapter(this, candidatures);
        recyclerViewCandidatures.setAdapter(candidatureAdapter);

        candidatureAdapter.setOnItemClickListener(new CandidatureAdapter.OnItemClickListener() {
            @Override
            public void onAcceptClick(int position) {
                // Traitement pour accepter la candidature à la position donnée
                Candidature candidatureAcceptee = candidatures.get(position);
                // Implémentez la logique nécessaire pour accepter la candidature
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
