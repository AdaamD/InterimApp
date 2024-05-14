package admd.interim.employeur;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import admd.interim.R;
import admd.interim.logic.DatabaseHelper;
import admd.interim.logic.Offre;
import admd.interim.logic.OffreAdapter;

public class GestionOffreActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OffreAdapter offreAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_offre);

        recyclerView = findViewById(R.id.recyclerViewOffers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Récupérer les offres depuis la base de données
        List<Offre> offres = retrieveOffresFromDatabase();

        // Afficher les offres dans la RecyclerView
        offreAdapter = new OffreAdapter(offres);
        recyclerView.setAdapter(offreAdapter);
    }

    private List<Offre> retrieveOffresFromDatabase() {
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        // Récupérer l'ID de l'employeur à partir de l'intent
        int employeurId = getIntent().getIntExtra("EMPLOYEUR_ID", -1);

        // Récupérer les offres de l'employeur depuis la base de données
        List<Offre> offres = dbHelper.getOffresByEmployeurId(employeurId);

        // Afficher les informations des offres récupérées dans le LogCat
        for (Offre offre : offres) {
            System.out.println("Offre ID: " + offre.getId());
            System.out.println("Titre: " + offre.getTitre());
            System.out.println("Description: " + offre.getDescription());
            System.out.println("Métier: " + offre.getMetier());
            System.out.println("Lieu: " + offre.getLieu());
            System.out.println("Date de début: " + offre.getDateDebut());
            System.out.println("Date de fin: " + offre.getDateFin());
            System.out.println("ID Employeur: " + offre.getIdEmployeur());
            System.out.println("-------------------");
        }

        return offres;
    }


}
