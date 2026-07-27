package Vista;

import android.content.Intent;
import android.database.Cursor;
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

public class LoginActivity extends AppCompatActivity {

    private EditText etUsuario, etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        etUsuario = findViewById(R.id.etUsuario);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = etUsuario.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (usuario.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Por favor llena todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }


                Helper dbHelper = new Helper(LoginActivity.this);

                SQLiteDatabase db = dbHelper.getReadableDatabase();


                Cursor cursor = db.rawQuery("SELECT * FROM usuarios WHERE nombre = ? AND pass = ?", new String[]{usuario, password});

                if (cursor != null && cursor.moveToFirst()) {

                    Toast.makeText(LoginActivity.this, "¡Bienvenida, " + usuario + "!", Toast.LENGTH_SHORT).show();

                    cursor.close();
                    db.close(); // Siempre cerrar la conexión


                    Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                    startActivity(intent);
                    finish(); // Cerrar el login
                } else {

                    Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    if (cursor != null) {
                        cursor.close();
                    }
                    db.close();
                }
            }
        });
    }
}