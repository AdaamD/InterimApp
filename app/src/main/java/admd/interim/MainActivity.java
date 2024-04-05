package admd.interim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

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
    }
}
