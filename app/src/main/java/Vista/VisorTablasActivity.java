package Vista;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.clinicahospital.R;

import Controlador.Helper;

public class VisorTablasActivity extends AppCompatActivity {

    private Spinner spinnerTablas;
    private LinearLayout contenedorVisor;


    private String[] tablas = {"usuarios", "pacientes", "doctores", "consultas"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visor_tablas);

        spinnerTablas = findViewById(R.id.spinnerTablas);
        contenedorVisor = findViewById(R.id.contenedorVisor);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, tablas);
        spinnerTablas.setAdapter(adapter);


        spinnerTablas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tablaSeleccionada = tablas[position];
                mostrarDatosDeTabla(tablaSeleccionada);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void mostrarDatosDeTabla(String nombreTabla) {

        contenedorVisor.removeAllViews();

        Helper dbHelper = new Helper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();


        Cursor cursor = db.rawQuery("SELECT * FROM " + nombreTabla, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                StringBuilder registro = new StringBuilder();


                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    String nombreColumna = cursor.getColumnName(i);
                    String valor = cursor.getString(i);
                    registro.append(nombreColumna).append(": ").append(valor).append("\n");
                }


                TextView nuevoTexto = new TextView(this);
                nuevoTexto.setText(registro.toString() + "-----------------------------------");
                nuevoTexto.setTextSize(16);
                nuevoTexto.setPadding(0, 16, 0, 16);

                contenedorVisor.addView(nuevoTexto);

            } while (cursor.moveToNext());

            cursor.close();
        } else {
            TextView textoVacio = new TextView(this);
            textoVacio.setText("La tabla " + nombreTabla + " está vacía.");
            textoVacio.setTextSize(18);
            contenedorVisor.addView(textoVacio);
        }

        db.close();
    }
}