package Vista;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.clinicahospital.R;

import com.google.android.gms.maps.OnMapReadyCallback;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;


public class MapaActivity extends AppCompatActivity{

    private MapView mapa;
    private TextView tvCoordenadas;
    private LocationManager locationManager;
    private Marker pinUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configuración de OSMDroid
        Configuration.getInstance().setUserAgentValue("ProyectoFinalClinicaHospital_Oaxaca");
        setContentView(R.layout.activity_mapa);

        mapa = findViewById(R.id.mapaOsmdroid);
        tvCoordenadas = findViewById(R.id.tvCoordenadas);
        mapa.setMultiTouchControls(true);
        mapa.getController().setZoom(18.0);

        // Inicializamos el pin
        pinUsuario = new Marker(mapa);
        pinUsuario.setTitle("Tu ubicación actual");
        pinUsuario.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        mapa.getOverlays().add(pinUsuario);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Pedimos permiso de ubicación si no lo tenemos
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        } else {
            iniciarGPS();
        }
    }

    private void iniciarGPS() {
        try {
            // Lee la ubicación del GPS cada segundo
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    double latitud_X = location.getLatitude();
                    double longitud_Y = location.getLongitude();

                    // Actualizamos el cuadro de texto
                    tvCoordenadas.setText("X (Lat): " + latitud_X + "\nY (Lon): " + longitud_Y);

                    // Movemos el mapa y el pin a donde estás
                    GeoPoint miUbicacion = new GeoPoint(latitud_X, longitud_Y);
                    pinUsuario.setPosition(miUbicacion);
                    mapa.getController().setCenter(miUbicacion);
                }
            });
        } catch (SecurityException e) {
            Toast.makeText(this, "No hay permisos de GPS", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            iniciarGPS();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapa.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapa.onPause();
    }
}