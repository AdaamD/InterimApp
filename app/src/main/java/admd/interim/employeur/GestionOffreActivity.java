package admd.interim.employeur;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import admd.interim.R;
import admd.interim.logic.OffreAdapter;

// GestionOffreActivity.java
public class GestionOffreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_offre);

        RecyclerView recyclerViewOffers = findViewById(R.id.recyclerViewOffers);
        List<String> offres = new ArrayList<>(); // Remplacez ceci par votre liste d'offres

        // Remplissez la liste d'offres (exemple)
        offres.add("Offre 1");
        offres.add("Offre 2");
        offres.add("Offre 3");

        OffreAdapter adapter = new OffreAdapter(offres);
        recyclerViewOffers.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewOffers.setAdapter(adapter);
    }
}

