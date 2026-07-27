package Vista;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

    // Usando los IDs que aparecen en tu captura image_f78020.png
    private EditText etIdDoctor, etIdPaciente, etDetalles, etHoraSalida;
    private Button btnGuardarConsulta;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflamos el diseño que hiciste
        View view = inflater.inflate(R.layout.fragment_registro, container, false);

        etIdDoctor = view.findViewById(R.id.editTextNumber);
        etIdPaciente = view.findViewById(R.id.editTextText);
        etDetalles = view.findViewById(R.id.editTextText4);
        etHoraSalida = view.findViewById(R.id.editTextTime);
        btnGuardarConsulta = view.findViewById(R.id.button);

        btnGuardarConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarConsulta();
            }
        });

        return view;
    }

    private void guardarConsulta() {
        Helper dbHelper = new Helper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("id_doctor", etIdDoctor.getText().toString());
        values.put("id_paciente", etIdPaciente.getText().toString());
        values.put("detalles", etDetalles.getText().toString());
        values.put("hora_salida", etHoraSalida.getText().toString());

        // Asegúrate de que la tabla en tu Helper se llame "consulta"
        long resultado = db.insert("consulta", null, values);

        if (resultado == -1) {
            Toast.makeText(getActivity(), "Error al guardar consulta", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Consulta guardada con éxito", Toast.LENGTH_SHORT).show();
            etIdDoctor.setText("");
            etIdPaciente.setText("");
            etDetalles.setText("");
            etHoraSalida.setText("");
        }
        db.close();
    }
}