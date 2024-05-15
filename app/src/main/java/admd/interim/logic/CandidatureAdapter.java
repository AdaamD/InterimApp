package admd.interim.logic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import admd.interim.R;

public class CandidatureAdapter extends RecyclerView.Adapter<CandidatureAdapter.CandidatureViewHolder> {

    private List<Candidature> candidatures;
    private Context context;
    private DatabaseHelper databaseHelper;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onAcceptClick(int position);
        void onRejectClick(int position);
        void onRespondClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public CandidatureAdapter(Context context, List<Candidature> candidatures) {
        this.context = context;
        this.candidatures = candidatures;
        this.databaseHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public CandidatureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_candidature, parent, false);
        return new CandidatureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CandidatureViewHolder holder, int position) {
        Candidature candidature = candidatures.get(position);
        holder.bind(candidature);
    }

    @Override
    public int getItemCount() {
        return candidatures.size();
    }

    class CandidatureViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNomCandidat;
        private TextView textViewEmailCandidat;
        private TextView textViewOffreTitre;
        private Button buttonAccept;
        private Button buttonReject;

        CandidatureViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNomCandidat = itemView.findViewById(R.id.textViewNomCandidat);
            textViewEmailCandidat = itemView.findViewById(R.id.textViewEmailCandidat);
            textViewOffreTitre = itemView.findViewById(R.id.textViewOffreTitre);
            buttonAccept = itemView.findViewById(R.id.buttonAccept);
            buttonReject = itemView.findViewById(R.id.buttonReject);

            buttonAccept.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Candidature candidature = candidatures.get(position);
                        showStatusDialog("Candidature acceptée", "La candidature a été acceptée.", candidature, "acceptée");
                    }
                }
            });

            buttonReject.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Candidature candidature = candidatures.get(position);
                        showStatusDialog("Candidature refusée", "La candidature a été refusée.", candidature, "refusée");
                    }
                }
            });
        }

        private void showStatusDialog(String title, String message, Candidature candidature, String nouveauStatut) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(title)
                    .setMessage(message)
                    .setPositiveButton("OK", (dialog, which) -> {
                        updateCandidatureStatus(candidature, nouveauStatut);
                        reloadActivity();
                    })
                    .setCancelable(false)
                    .show();
        }

        private void updateCandidatureStatus(Candidature candidature, String nouveauStatut) {
            databaseHelper.updateStatutCandidature(candidature.getId(), nouveauStatut);
            candidature.setStatutCandidature(nouveauStatut);
            notifyItemChanged(candidatures.indexOf(candidature));
        }

        private void reloadActivity() {
            Activity activity = (Activity) context;
            Intent intent = activity.getIntent();
            activity.finish();
            activity.startActivity(intent);
        }

        void bind(Candidature candidature) {
            textViewNomCandidat.setText(candidature.getNomCandidat());
            textViewEmailCandidat.setText(candidature.getEmailCandidat());

            Offre offre = databaseHelper.getOffreById(candidature.getIdOffre());
            if (offre != null) {
                textViewOffreTitre.setText("Offre: " + offre.getTitre());
            } else {
                textViewOffreTitre.setText("Offre: non trouvée");
            }

            // Afficher le statut de la candidature dans le LogCat
            String statut = candidature.getStatutCandidature();
            System.out.println("Candidature ID: " + candidature.getId());
            System.out.println("Statut: " + statut);
            System.out.println("-------------------");
        }
    }
}
