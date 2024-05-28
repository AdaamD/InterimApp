package admd.interim.employeur;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import admd.interim.R;
import admd.interim.logic.DatabaseHelper;
import admd.interim.logic.Offre;

import java.util.Date;

public class ModifierOffreActivity extends AppCompatActivity {

    private EditText editTextTitre, editTextDescription, editTextMetier, editTextLieu, editTextDateDebut, editTextDateFin;
    private Button buttonModifier;

    private DatabaseHelper dbHelper;
    private int offreId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_offre);

        dbHelper = new DatabaseHelper(this);

        // Récupérer l'ID de l'offre à modifier depuis l'Intent
        offreId = getIntent().getIntExtra("offre_id", 0);

        // Récupérer les informations de l'offre depuis la base de données
        Offre offre = dbHelper.getOffreById(offreId);

        // Vérifier si l'objet Offre n'est pas null
        if (offre != null) {
            // Initialiser les vues
            editTextTitre = findViewById(R.id.editTextTitre);
            editTextDescription = findViewById(R.id.editTextDescription);
            editTextMetier = findViewById(R.id.editTextMetier);
            editTextLieu = findViewById(R.id.editTextLieu);
            editTextDateDebut = findViewById(R.id.editTextDateDebut);
            editTextDateFin = findViewById(R.id.editTextDateFin);
            buttonModifier = findViewById(R.id.buttonModifier);

            // Remplir les champs avec les informations de l'offre
            editTextTitre.setText(offre.getTitre());
            editTextDescription.setText(offre.getDescription());
            editTextMetier.setText(offre.getMetier());
            editTextLieu.setText(offre.getLieu());
            editTextDateDebut.setText(offre.getDateDebut().toString());
            editTextDateFin.setText(offre.getDateFin().toString());


            // Ajouter un OnClickListener pour le bouton "Modifier"
            buttonModifier.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    modifierOffre(offre.getIdEmployeur());
                }
            });
        } else {
            // Gérer le cas où l'objet Offre est null (afficher un message d'erreur, fermer l'activité, etc.)
            Toast.makeText(this, "Erreur : Offre introuvable", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void modifierOffre(int idEmployeurExistant) {
        String nouveauTitre = editTextTitre.getText().toString();
        String nouvelleDescription = editTextDescription.getText().toString();
        String nouveauMetier = editTextMetier.getText().toString();
        String nouveauLieu = editTextLieu.getText().toString();
        Date nouvelleDateDebut = new Date(editTextDateDebut.getText().toString());
        Date nouvelleDateFin = new Date(editTextDateFin.getText().toString());

        // Créer un nouvel objet Offre avec les nouvelles valeurs et l'ID de l'employeur existant
        Offre offre = new Offre(nouveauTitre, nouvelleDescription, nouveauMetier, nouveauLieu, nouvelleDateDebut, nouvelleDateFin, idEmployeurExistant);
        offre.setId(offreId);

        int rowsUpdated = dbHelper.updateOffre(offreId, nouveauTitre, nouvelleDescription, nouveauMetier, nouveauLieu, nouvelleDateDebut, nouvelleDateFin, idEmployeurExistant);
        if (rowsUpdated > 0) {
            Toast.makeText(this, "Offre modifiée avec succès", Toast.LENGTH_SHORT).show();
            finish(); // Fermer l'activité après la modification
        } else {
            Toast.makeText(this, "Échec de la modification de l'offre", Toast.LENGTH_SHORT).show();
        }
    }
}
