package admd.interim;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import admd.interim.logic.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonEspaceCandidatClick = findViewById(R.id.button_espace_candidat);
        buttonEspaceCandidatClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Rediriger vers l'activité EspaceCandidatActivity
                Intent intent = new Intent(MainActivity.this, EspaceCandidatActivity.class);
                startActivity(intent);
            }
        });

        Button buttonEspaceEmployeurClick = findViewById(R.id.button_espace_employeur);
        buttonEspaceEmployeurClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Rediriger vers l'activité EspaceEmployeurActivity
                Intent intent = new Intent(MainActivity.this, EspaceEmployeurActivity.class);
                startActivity(intent);
            }
        });

        TextView textViewContinuerAnonymement = findViewById(R.id.textView_continuer_anonymement);
        textViewContinuerAnonymement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Rediriger vers l'activité AnonymeActivity
                Intent intent = new Intent(MainActivity.this, AnonymeActivity.class);
                startActivity(intent);
            }
        });

        // Instancier la classe DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Obtenir une instance de SQLiteDatabase en mode écriture
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        long Candidat1 = databaseHelper.insertCandidat("ADAM", "D", "01/01/2001", "francaise", "0611111111", "adam@mail.fr", "Montpellier", "CV-Adam");

        // Insérer un deuxième candidat
        long Candidat2 = databaseHelper.insertCandidat("Nabil", "Da", "10/10/2001", "marocain", "0622222222", "nabil@mail.fr", "Montpellier", "CV-Nabil");

        // Faire quelque chose avec les nouveaux IDs de ligne, par exemple les afficher dans un Toast
        //Toast.makeText(this, "Nouveaux candidats insérés avec les IDs : " + Candidat1 + " et " + Candidat2, Toast.LENGTH_SHORT).show();


    }



}
