package Vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.clinicahospital.R;

public class MenuActivity extends AppCompatActivity {

    private Button btnWifi, btnMapa, btnRegistro, btnVerPacientes, btnVisorTablas, btnCerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Enlazamos las variables de Java con los IDs del XML
        btnWifi = findViewById(R.id.btnWifi);
        btnMapa = findViewById(R.id.btnMapa);
        btnRegistro = findViewById(R.id.btnRegistro);
        btnVerPacientes = findViewById(R.id.btnVerPacientes);
        btnVisorTablas = findViewById(R.id.btnVisorTablas);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        // Navegación 1: WiFi
        btnWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, WifiActivity.class));
            }
        });

        // Navegación 2: Mapa
        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, MapaActivity.class));
            }
        });

        // Navegación 3: Registro (Aquí irá el fragment)
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, RegistroActivity.class));
            }
        });

        // Navegación 4: Ver Pacientes y Doctores
        btnVerPacientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, VerPacientesActivity.class));
            }
        });

        // Navegación 5: Visor Dinámico de Tablas
        btnVisorTablas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, VisorTablasActivity.class));
            }
        });
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}