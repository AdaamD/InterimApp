package admd.interim.logic;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AlertDialog;

import java.util.List;

import admd.interim.R;
import admd.interim.logic.DetailsCandidatActivityEmployeur;

public class CandidatureAccepteeAdapter extends RecyclerView.Adapter<CandidatureAccepteeAdapter.CandidatureViewHolder> {

    private Context context;
    private List<Candidature> candidatures;
    private DatabaseHelper databaseHelper;

    public CandidatureAccepteeAdapter(Context context, List<Candidature> candidatures) {
        this.context = context;
        this.candidatures = candidatures;
        this.databaseHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public CandidatureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_candidature_acceptee, parent, false);
        return new CandidatureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CandidatureViewHolder holder, int position) {
        Candidature candidature = candidatures.get(position);
        String nomOffre = databaseHelper.getOffreParId(candidature.getIdOffre());

        holder.textViewNomPrenom.setText(candidature.getNomCandidat() + " " + candidature.getPrenomCandidat());
        holder.textViewEmail.setText(candidature.getEmailCandidat());
        holder.textViewNomOffre.setText(nomOffre);

        holder.buttonConsulter.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailsCandidatActivityEmployeur.class);
            Candidat candidat = databaseHelper.getCandidatByID((int)candidature.getIdCandidat());
            if (candidat != null) {
                intent.putExtra("NOM", candidat.getNom());
                intent.putExtra("PRENOM", candidat.getPrenom());
                intent.putExtra("EMAIL", candidat.getEmail());
                intent.putExtra("NUMERO_TELEPHONE", candidat.getNumeroTelephone());
                intent.putExtra("DATE_NAISSANCE", candidat.getDateNaissance());
                intent.putExtra("NATIONALITE", candidat.getNationalite());
                intent.putExtra("VILLE", candidat.getVille());
            }
            intent.putExtra("CV", candidature.getCvCandidat());

            Offre offre = databaseHelper.getOffreById(candidature.getIdOffre());
            intent.putExtra("TITRE_OFFRE", offre.getTitre());
            intent.putExtra("DESCRIPTION_OFFRE", offre.getDescription());
            intent.putExtra("METIER", offre.getMetier());
            intent.putExtra("LIEU", offre.getLieu());
            intent.putExtra("DATE_DEBUT", offre.getDateDebut().toString());
            intent.putExtra("DATE_FIN", offre.getDateFin().toString());

            context.startActivity(intent);
        });

        holder.buttonContacter.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Choisir un moyen de contact");

            String[] options = {"Email", "Téléphone", "SMS"};

            builder.setItems(options, (dialog, which) -> {
                Candidat candidat = databaseHelper.getCandidatByID((int)candidature.getIdCandidat());
                if (candidat != null) {
                    switch (which) {
                        case 0:
                            Intent emailIntent = new Intent(Intent.ACTION_SEND);
                            emailIntent.setType("text/plain");
                            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{candidat.getEmail()});
                            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Sujet de votre email");
                            context.startActivity(Intent.createChooser(emailIntent, "Choisir une application pour envoyer l'email"));
                            break;
                        case 1:
                            Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
                            phoneIntent.setData(Uri.parse("tel:" + candidat.getNumeroTelephone()));
                            context.startActivity(phoneIntent);
                            break;
                        case 2:
                            Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
                            smsIntent.setData(Uri.parse("smsto:" + candidat.getNumeroTelephone()));
                            smsIntent.putExtra("sms_body", "Message SMS à envoyer");
                            context.startActivity(smsIntent);
                            break;
                    }
                }
            });

            builder.show();
        });
    }


    @Override
    public int getItemCount() {
        return candidatures.size();
    }

    public static class CandidatureViewHolder extends RecyclerView.ViewHolder {

        TextView textViewNomPrenom;
        TextView textViewEmail;
        TextView textViewNomOffre;
        Button buttonConsulter;
        Button buttonContacter;

        public CandidatureViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNomPrenom = itemView.findViewById(R.id.textViewNomPrenom);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            textViewNomOffre = itemView.findViewById(R.id.textViewNomOffre);
            buttonConsulter = itemView.findViewById(R.id.buttonConsulter);
            buttonContacter = itemView.findViewById(R.id.buttonContacter);
        }
    }
}