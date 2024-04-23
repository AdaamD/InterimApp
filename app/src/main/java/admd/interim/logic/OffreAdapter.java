package admd.interim.logic;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import admd.interim.R;

public class OffreAdapter extends RecyclerView.Adapter<OffreAdapter.OffreViewHolder> {

    private List<String> offres; // Liste des titres d'offres

    public OffreAdapter(List<String> offres) {
        this.offres = offres;
    }

    @NonNull
    @Override
    public OffreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemm_offre, parent, false);
        return new OffreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OffreViewHolder holder, int position) {
        String titreOffre = offres.get(position);
        holder.bind(titreOffre);
    }

    @Override
    public int getItemCount() {
        return offres.size();
    }

    static class OffreViewHolder extends RecyclerView.ViewHolder {
        TextView textOfferTitle;
        Button buttonConsulter;
        Button buttonModifier;
        Button buttonSupprimer;

        OffreViewHolder(@NonNull View itemView) {
            super(itemView);
            textOfferTitle = itemView.findViewById(R.id.textOfferTitle);
            buttonConsulter = itemView.findViewById(R.id.buttonConsulter);
            buttonModifier = itemView.findViewById(R.id.buttonModifier);
            buttonSupprimer = itemView.findViewById(R.id.buttonSupprimer);
        }

        void bind(String titreOffre) {
            textOfferTitle.setText(titreOffre);

            buttonConsulter.setOnClickListener(v -> {
                Toast.makeText(itemView.getContext(), "Consulter: " + titreOffre, Toast.LENGTH_SHORT).show();
            });

            buttonModifier.setOnClickListener(v -> {
                Toast.makeText(itemView.getContext(), "Modifier: " + titreOffre, Toast.LENGTH_SHORT).show();
            });

            buttonSupprimer.setOnClickListener(v -> {
                Toast.makeText(itemView.getContext(), "Supprimer: " + titreOffre, Toast.LENGTH_SHORT).show();
            });

        }
    }
}

