package Vista;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.clinicahospital.R;

import Controlador.Helper;

public class VerPacientesActivity extends AppCompatActivity {
    private LinearLayout contenedorPacientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Asegúrate de que este nombre coincida con tu archivo XML
        setContentView(R.layout.activity_ver_pacientes);

        contenedorPacientes = findViewById(R.id.contenedorPacientes);

        // Llamamos al método para cargar los datos en cuanto se abre la pantalla
        listarPacientes();
    }

    private void listarPacientes() {
        // 1. Conectamos con la base de datos en modo lectura
        Helper dbHelper = new Helper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // 2. Hacemos la consulta a la tabla pacientes
        Cursor cursor = db.rawQuery("SELECT * FROM pacientes", null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Obtenemos los datos de cada columna
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                String edad = cursor.getString(cursor.getColumnIndexOrThrow("edad"));
                String direccion = cursor.getString(cursor.getColumnIndexOrThrow("direccion"));

                // 3. Creamos un TextView nuevo por cada paciente
                TextView nuevoTexto = new TextView(this);
                nuevoTexto.setText("Paciente: " + nombre + "\nEdad: " + edad + " años\nDirección: " + direccion + "\n-----------------------------------");
                nuevoTexto.setTextSize(18);
                nuevoTexto.setPadding(0, 16, 0, 16);

                // 4. Lo agregamos al contenedor de la pantalla
                contenedorPacientes.addView(nuevoTexto);

            } while (cursor.moveToNext());

            cursor.close();
        } else {
            // Si la base de datos está vacía
            TextView textoVacio = new TextView(this);
            textoVacio.setText("No hay pacientes registrados aún.");
            textoVacio.setTextSize(18);
            contenedorPacientes.addView(textoVacio);
        }

        db.close();
    }
}