package admd.interim.employeur;

import android.app.AlertDialog;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import admd.interim.R;
import admd.interim.logic.Candidature;
import admd.interim.logic.CandidatureAccepteeAdapter;
import admd.interim.logic.DatabaseHelper;

public class CandidaturesAccepteesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewCandidaturesAcceptees;
    private CandidatureAccepteeAdapter candidatureAdapter;

    private DatabaseHelper databaseHelper;
    private int employeurId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidatures_acceptees);

        recyclerViewCandidaturesAcceptees = findViewById(R.id.recyclerViewCandidaturesAcceptees);
        recyclerViewCandidaturesAcceptees.setLayoutManager(new LinearLayoutManager(this));

        employeurId = getIntent().getIntExtra("EMPLOYEUR_ID", 0);
        databaseHelper = new DatabaseHelper(this);

        // Récupérer les candidatures acceptées pour l'employeur
        List<Candidature> candidatures = databaseHelper.getCandidaturesParEmployeurAcceptee(employeurId);

        if (!candidatures.isEmpty()) {
            candidatureAdapter = new CandidatureAccepteeAdapter(this, candidatures);
            recyclerViewCandidaturesAcceptees.setAdapter(candidatureAdapter);
        } else {
            // Afficher un message si aucune candidature acceptée n'est trouvée
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Aucune candidature acceptée")
                    .setMessage("Il n'y a aucune candidature acceptée pour le moment.")
                    .setPositiveButton("OK", (dialog, which) -> finish())
                    .setCancelable(false)
                    .show();
        }
    }
}
