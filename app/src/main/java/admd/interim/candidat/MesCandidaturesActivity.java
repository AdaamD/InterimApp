package admd.interim.candidat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import admd.interim.R;
import admd.interim.logic.Candidature;
import admd.interim.logic.DatabaseHelper;

public class MesCandidaturesActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerViewCandidatures;
    private CandidatureAdapter candidatureAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidat_mes_candidatures);

        databaseHelper = new DatabaseHelper(this);

        long candidatId = getIntent().getLongExtra("candidat_id", 0);
        List<Candidature> candidatures = databaseHelper.getCandidaturesParCandidat(candidatId);

        recyclerViewCandidatures = findViewById(R.id.recyclerViewCandidatures);
        recyclerViewCandidatures.setLayoutManager(new LinearLayoutManager(this));
        candidatureAdapter = new CandidatureAdapter(candidatures);
        recyclerViewCandidatures.setAdapter(candidatureAdapter);
    }

    private class CandidatureAdapter extends RecyclerView.Adapter<CandidatureViewHolder> {
        private List<Candidature> candidatures;

        public CandidatureAdapter(List<Candidature> candidatures) {
            this.candidatures = candidatures;
        }

        @Override
        public CandidatureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_candidat_mescandidatures, parent, false);
            return new CandidatureViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CandidatureViewHolder holder, int position) {
            Candidature candidature = candidatures.get(position);
            holder.bind(candidature);
        }

        @Override
        public int getItemCount() {
            return candidatures.size();
        }
    }

    private class CandidatureViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewOffre;
        private TextView textViewStatut;
        private TextView textViewDate;

        public CandidatureViewHolder(View itemView) {
            super(itemView);
            textViewOffre = itemView.findViewById(R.id.textViewOffre);
            textViewStatut = itemView.findViewById(R.id.textViewStatut);
            textViewDate = itemView.findViewById(R.id.textViewDate);
        }

        public void bind(Candidature candidature) {
            textViewOffre.setText(candidature.getNomCandidat() + " - " + candidature.getPrenomCandidat());
            textViewStatut.setText(candidature.getStatutCandidat());

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String dateFormatted = dateFormat.format(candidature.getDateCandidat());
            textViewDate.setText(dateFormatted);
        }
    }
}
