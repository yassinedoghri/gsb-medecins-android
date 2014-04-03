package y.doghri.medecins;

import android.app.ActionBar;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ydoghri
 */
public class MapActivity extends FragmentActivity {

    private ActionBar actionBar;

    // Map
    private GoogleMap map;
    private LatLng COO;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        Intent inter = getIntent();
        String nom = inter.getStringExtra("nom");
        String prenom = inter.getStringExtra("prenom");
        String adresse = inter.getStringExtra("adresse");

        Geocoder geocoder = new Geocoder(this);
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocationName(adresse, 1);

            COO = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
        } catch (IOException ex) {
            Logger.getLogger(MapActivity.class.getName()).log(Level.SEVERE, null, ex);
        }

        map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                .getMap();

        if (map != null) {
            Marker marker = map.addMarker(new MarkerOptions()
                    .position(COO)
                    .title(prenom + " " + nom)
                    .snippet(adresse)
                    .icon(BitmapDescriptorFactory
                            .fromResource(R.drawable.ic_marker)));

            // Move the camera instantly to hamburg with a zoom of 15.
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(COO, 15));

            // Zoom in, animating the camera.
            map.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);
        }
        
        actionBar = getActionBar();
    }

}
