package daniyaramangeldy.weathermap;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener {

    public static final int REQUEST_LOCATION = 10;
    private static final String TAG = "MapsActivity";
    public static final int REQUEST_CAMERA = 11;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private BottomSheetBehavior mBottomSheetBehavior;
    private ArrayList<String> listField = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        View bottomSheet = findViewById(R.id.bottom_sheet);
        RecyclerView list = (RecyclerView) findViewById(R.id.list);
//        for(int i=0;i<50;i++){
//            listField.add(i+"");
//        }
        Log.d(TAG, "onCreate: "+listField.size());
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setPeekHeight(300);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//        SimpleAdapter adapter = new SimpleAdapter(listField);
//        list.setAdapter(adapter);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocation();
                }
                break;
            case REQUEST_CAMERA:
                break;

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng almaty = new LatLng(43.2220, 76.8512);
        MarkerOptions marker = new MarkerOptions().position(almaty);
        mMap.addMarker(marker);
        mMap.animateCamera(CameraUpdateFactory.newLatLng(almaty));
        mMap.setOnMapClickListener(this);
//        checkCameraPermissionAndRequest();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        REQUEST_LOCATION
                );
            }
        } else
            enableMyLocation();

    }

    private void checkCameraPermissionAndRequest() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                        new String[]{Manifest.permission.CAMERA},
                        REQUEST_CAMERA
                );
            }
        }
    }


    private void enableMyLocation() {
        mMap.setMyLocationEnabled(true);
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            Log.d(TAG,String.format("onSuccess: %s %s", location.getLatitude(), location.getLongitude()));
                        }

                    }
                });
    }

    @Override
    public void onMapClick(LatLng latLng) {
        String template = "onMapClick: %s %s";
        double latitude = latLng.latitude;
        double longitude = latLng.longitude;
        Log.d(TAG, String.format(template,latitude,longitude));

    }
}
