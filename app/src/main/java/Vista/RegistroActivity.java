package Vista;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.clinicahospital.R;

import Controlador.Helper;

public class RegistroActivity extends AppCompatActivity {


    private EditText etNombre, etEdad, etDireccion;
    private Button btnGuardarPaciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etNombre = findViewById(R.id.editTextText2);
        etEdad = findViewById(R.id.editTextNumber2);
        etDireccion = findViewById(R.id.editTextText3);
        btnGuardarPaciente = findViewById(R.id.button2);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contenedorFragment, new RegistroFragment())
                    .commit();
        }

        btnGuardarPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarPaciente();
            }
        });
    }

    private void guardarPaciente() {
        Helper dbHelper = new Helper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nombre", etNombre.getText().toString());
        values.put("edad", etEdad.getText().toString());
        values.put("direccion", etDireccion.getText().toString());


        long resultado = db.insert("pacientes", null, values);

        if (resultado == -1) {
            Toast.makeText(this, "Error al guardar paciente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Paciente guardado con éxito", Toast.LENGTH_SHORT).show();

            etNombre.setText("");
            etEdad.setText("");
            etDireccion.setText("");

            RegistroFragment fragmento = (RegistroFragment) getSupportFragmentManager().findFragmentById(R.id.contenedorFragment);

            if (fragmento != null) {
                fragmento.recibirIdPaciente(String.valueOf(resultado));
            }
        }
        db.close();
    }
}