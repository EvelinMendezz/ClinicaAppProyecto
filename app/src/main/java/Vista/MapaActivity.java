package Vista;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.clinicahospital.R;

import com.google.android.gms.maps.OnMapReadyCallback;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;


public class MapaActivity extends AppCompatActivity{

    private MapView mapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Configuration.getInstance().setUserAgentValue("ProyectoFinalClinicaHospital_Oaxaca");

        setContentView(R.layout.activity_mapa);


        mapa = findViewById(R.id.mapaOsmdroid);


        mapa.setMultiTouchControls(true);


        GeoPoint ubicacionClinica = new GeoPoint(17.0594, -96.7115);


        mapa.getController().setZoom(16.0);
        mapa.getController().setCenter(ubicacionClinica);

        Marker pin = new Marker(mapa);
        pin.setPosition(ubicacionClinica);
        pin.setTitle("Clínica Hospital Principal");
        pin.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);

        mapa.getOverlays().add(pin);
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