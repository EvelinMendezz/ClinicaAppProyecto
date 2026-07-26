package Vista;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.clinicahospital.R;

import Controlador.Helper;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class RegistroFragment extends Fragment {

    private EditText etNombrePaciente, etEdadPaciente, etDireccionPaciente;
    private Button btnGuardarPaciente;

    public RegistroFragment() {
        // Un Fragment requiere un constructor vacío por defecto
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflamos (cargamos) el diseño visual que acabas de hacer
        View view = inflater.inflate(R.layout.fragment_registro, container, false);

        // Enlazamos las variables con los IDs de tu diseño
        etNombrePaciente = view.findViewById(R.id.etNombrePaciente);
        etEdadPaciente = view.findViewById(R.id.etEdadPaciente);
        etDireccionPaciente = view.findViewById(R.id.etDireccionPaciente);
        btnGuardarPaciente = view.findViewById(R.id.btnGuardarPaciente);

        // Le damos acción al botón
        btnGuardarPaciente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarPaciente();
            }
        });

        return view;
    }

    private void guardarPaciente() {
        String nombre = etNombrePaciente.getText().toString().trim();
        String edadStr = etEdadPaciente.getText().toString().trim();
        String direccion = etDireccionPaciente.getText().toString().trim();

        // Validamos que la usuaria no deje campos vacíos
        if (nombre.isEmpty() || edadStr.isEmpty() || direccion.isEmpty()) {
            Toast.makeText(getContext(), "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // 1. Instanciamos tu Helper (Aplicando MVC)
        Helper dbHelper = new Helper(getContext());
        // 2. Abrimos la conexión en modo escritura
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // 3. Preparamos los datos con ContentValues (como lo pide la maestra)
        ContentValues valores = new ContentValues();
        valores.put("nombre", nombre);
        valores.put("edad", Integer.parseInt(edadStr));
        valores.put("direccion", direccion);

        // 4. Insertamos en la tabla pacientes
        long resultado = db.insert("pacientes", null, valores);

        if (resultado != -1) {
            Toast.makeText(getContext(), "Paciente registrado con éxito", Toast.LENGTH_SHORT).show();
            // Limpiamos los campos para registrar a otro
            etNombrePaciente.setText("");
            etEdadPaciente.setText("");
            etDireccionPaciente.setText("");
        } else {
            Toast.makeText(getContext(), "Error al guardar", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }
}