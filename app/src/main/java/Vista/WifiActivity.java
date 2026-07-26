package Vista;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.clinicahospital.R;

import java.util.Locale;

public class WifiActivity extends AppCompatActivity {

    private TextView tvDetallesWifi;
    private Button btnObtenerWifi;
    // Código interno para saber cuándo el usuario acepta el permiso
    private static final int CODIGO_PERMISO = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);

        tvDetallesWifi = findViewById(R.id.tvDetallesWifi);
        btnObtenerWifi = findViewById(R.id.btnObtenerWifi);

        btnObtenerWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verificar si tenemos el permiso en tiempo real
                if (ContextCompat.checkSelfPermission(WifiActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // Si no lo tenemos, lanzamos la ventanita para pedirlo
                    ActivityCompat.requestPermissions(WifiActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, CODIGO_PERMISO);
                } else {
                    // Si ya lo tenemos, leemos el WiFi directo
                    obtenerDatosWifi();
                }
            }
        });
    }

    private void obtenerDatosWifi() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();

            String ssid = wifiInfo.getSSID(); // Nombre de la red
            int ipAddress = wifiInfo.getIpAddress();
            int linkSpeed = wifiInfo.getLinkSpeed();

            // Convertir la IP de un número entero a un formato legible (ej. 192.168.1.10)
            String ipFormateada = String.format(Locale.getDefault(), "%d.%d.%d.%d",
                    (ipAddress & 0xff), (ipAddress >> 8 & 0xff),
                    (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));

            // Armamos el texto final
            String detalles = "Red (SSID): " + ssid + "\n\n" +
                    "Dirección IP: " + ipFormateada + "\n\n" +
                    "Velocidad: " + linkSpeed + " Mbps";

            tvDetallesWifi.setText(detalles);
        } else {
            tvDetallesWifi.setText("No se pudo acceder al hardware de WiFi.");
        }
    }

    // Este método "escucha" lo que el usuario responde en la ventanita de permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CODIGO_PERMISO) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Si el usuario dijo que "Sí", leemos los datos
                obtenerDatosWifi();
            } else {
                tvDetallesWifi.setText("Permiso denegado. Se requiere el permiso de ubicación para leer la red WiFi por reglas de Android.");
            }
        }
    }
}
