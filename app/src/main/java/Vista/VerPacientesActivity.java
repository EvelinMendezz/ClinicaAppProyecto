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
        setContentView(R.layout.activity_ver_pacientes);

        contenedorPacientes = findViewById(R.id.contenedorPacientes);

        listarPacientes();
    }

    private void listarPacientes() {
        Helper dbHelper = new Helper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();


        String query = "SELECT pacientes.id_paciente, pacientes.nombre, pacientes.edad, pacientes.direccion, doctores.nombre " +
                "FROM consultas " +
                "INNER JOIN pacientes ON consultas.id_paciente = pacientes.id_paciente " +
                "INNER JOIN doctores ON consultas.id_doctor = doctores.id_doctor";

        try {
            Cursor cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {

                    String idPaciente = cursor.getString(0);
                    String nombrePaciente = cursor.getString(1);
                    String edad = cursor.getString(2);
                    String direccion = cursor.getString(3);
                    String nombreDoctor = cursor.getString(4);


                    TextView nuevoTexto = new TextView(this);
                    nuevoTexto.setText("ID Paciente: " + idPaciente +
                            "\nPaciente: " + nombrePaciente +
                            "\nEdad: " + edad + " años" +
                            "\nDirección: " + direccion +
                            "\nAtendido por Dr(a): " + nombreDoctor +
                            "\n-----------------------------------");
                    nuevoTexto.setTextSize(18);
                    nuevoTexto.setPadding(0, 16, 0, 16);

                    contenedorPacientes.addView(nuevoTexto);

                } while (cursor.moveToNext());

                cursor.close();
            } else {
                TextView textoVacio = new TextView(this);
                textoVacio.setText("No hay pacientes ni consultas registradas aún.");
                textoVacio.setTextSize(18);
                contenedorPacientes.addView(textoVacio);
            }
        } catch (Exception e) {
            TextView textoError = new TextView(this);
            textoError.setText("Error en la consulta. Si en tu Helper los ID se llaman solo 'id' en lugar de 'id_paciente' o 'id_doctor', hay que ajustar el texto del JOIN.");
            textoError.setTextSize(16);
            contenedorPacientes.addView(textoError);
        } finally {
            db.close();
        }
    }
}