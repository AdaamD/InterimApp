package admd.interim.anonyme;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import admd.interim.R;
import admd.interim.logic.DatabaseHelper;
import admd.interim.logic.Offre;


public class AnonymeActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private ListView listOffres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anonyme);

        // Instancier la classe DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Récupérer la référence à la ListView
        listOffres = findViewById(R.id.list_offers);

        // Récupérer les offres depuis la base de données
        List<Offre> offres = databaseHelper.getAllOffres();

        // Créer un adaptateur pour la ListView
        OffreAdapter adapter = new OffreAdapter(this, offres);
        listOffres.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Fermer la connexion à la base de données
        databaseHelper.close();
    }

    public class OffreAdapter extends ArrayAdapter<Offre> {
        private Map<Integer, Boolean> detailsVisibles = new HashMap<>();
        private Context context;
        private List<Offre> offres;

        public OffreAdapter(Context context, List<Offre> offres) {
            super(context, 0, offres);
            this.context = context;
            this.offres = offres;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_offre, parent, false);
            }

            Offre offre = offres.get(position);

            TextView titreOffre = convertView.findViewById(R.id.titre_offre);
            TextView metierOffre = convertView.findViewById(R.id.metier_offre);
            TextView lieuOffre = convertView.findViewById(R.id.lieu_offre);
            ImageButton buttonPlus = convertView.findViewById(R.id.button_plus);

            TextView descriptionOffre = convertView.findViewById(R.id.description_offre);
            TextView dateDebutOffre = convertView.findViewById(R.id.date_debut_offre);
            TextView dateFinOffre = convertView.findViewById(R.id.date_fin_offre);

            titreOffre.setText(offre.getTitre());
            metierOffre.setText(Html.fromHtml("<b>Métier :</b> " + offre.getMetier()));
            lieuOffre.setText(Html.fromHtml("<b>Lieu :</b> " + offre.getLieu()));

            boolean detailsVisible = detailsVisibles.containsKey(position) && detailsVisibles.get(position);

            descriptionOffre.setVisibility(detailsVisible ? View.VISIBLE : View.GONE);
            dateDebutOffre.setVisibility(detailsVisible ? View.VISIBLE : View.GONE);
            dateFinOffre.setVisibility(detailsVisible ? View.VISIBLE : View.GONE);

            if (detailsVisible) {
                descriptionOffre.setText(Html.fromHtml("<b>Description :</b> " + offre.getDescription()));
                dateDebutOffre.setText(Html.fromHtml("<b>Date de début :</b> " + offre.getDateDebut()));
                dateFinOffre.setText(Html.fromHtml("<b>Date de fin :</b> " + offre.getDateFin()));
            }

            buttonPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleDetailsVisibility(position);
                }
            });

            return convertView;
        }

        private void toggleDetailsVisibility(int position) {
            boolean detailsVisible = detailsVisibles.containsKey(position) && detailsVisibles.get(position);
            detailsVisibles.put(position, !detailsVisible);
            notifyDataSetChanged();
        }

    }


}

