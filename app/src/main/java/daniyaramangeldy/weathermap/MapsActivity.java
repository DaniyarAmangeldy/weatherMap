package daniyaramangeldy.weathermap;

import android.Manifest;
import android.animation.LayoutTransition;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import daniyaramangeldy.weathermap.models.City;
import daniyaramangeldy.weathermap.models.Info;
import daniyaramangeldy.weathermap.models.Weather;
import daniyaramangeldy.weathermap.models.WeatherResponse;

import static daniyaramangeldy.weathermap.LocationService.EXTRA_REQUEST_RESULT;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnMapClickListener {

    public static final int REQUEST_LOCATION = 10;
    private static final String TAG = "MapsActivity";
    public static final int REQUEST_CAMERA = 11;

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private BottomSheetBehavior mBottomSheetBehavior;
    private LatLng currentCoor;
    private LatLng almaty;
    private RecyclerView list;
    private ProgressBar progressBar;
    private ConstraintLayout todayLayout;
    private TextView weatherDesc;
    private TextView weatherTemp;
    private TextView weatherDate;
    private TextView currentCity;
    private ImageView weatherIcon;
    private View bottomSheet;
    private WeatherResponse response;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        bindViews();
        list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setPeekHeight(0);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        almaty = new LatLng(43.2220, 76.8512);
    }

    private void bindViews() {
        bottomSheet = findViewById(R.id.bottom_sheet);
        list = (RecyclerView) findViewById(R.id.rv);
        todayLayout = (ConstraintLayout) findViewById(R.id.todayLayout);
        weatherTemp = (TextView) findViewById(R.id.weatherTemp);
        weatherDate = (TextView) findViewById(R.id.weatherDate);
        weatherDesc = (TextView) findViewById(R.id.weatherName);
        currentCity = (TextView) findViewById(R.id.city);
        weatherIcon = (ImageView) findViewById(R.id.weatherImage);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocation();
                } else {
                    currentCoor = almaty;
                    MakeRequestWeather(currentCoor);
                }
                break;
            case REQUEST_CAMERA:
                break;

        }
    }

    private void MakeRequestWeather(LatLng currentCoor) {
        progressBar.setVisibility(View.VISIBLE);
        LocationService.startActionLocation(getApplicationContext(),currentCoor);
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Gson gson = new Gson();
                response = gson.fromJson(intent.getStringExtra(EXTRA_REQUEST_RESULT),WeatherResponse.class);
                progressBar.setVisibility(View.GONE);
                mBottomSheetBehavior.setPeekHeight(300);
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                if(list.getAdapter() == null) {
                    SimpleAdapter adapter = new SimpleAdapter();
                    adapter.setResponse(response);
                    list.setAdapter(adapter);
                }else{
                    ((SimpleAdapter) list.getAdapter()).setResponse(response);
                    list.getAdapter().notifyDataSetChanged();
                }
                fillTodayWeather(response.getCity(),response.getInfoList().get(0));

            }
        };
        broadcastManager.registerReceiver(receiver,new IntentFilter("filter1"));
    }

    private void fillTodayWeather(City city, Info info) {
        Icons icons = new Icons();
        Weather weather = info.getWeather().get(0);
        weatherIcon.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),icons.getIcon(weather.getIcon())));
        currentCity.setText(city.getName());
        weatherTemp.setText(String.format("%s°C",(int)info.getTemp().getTemp()));
        weatherDesc.setText(weather.getDesc());
        weatherDate.setText(DateUtils.setTime(info.getTimeInMillis()));


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        MarkerOptions marker = new MarkerOptions().position(almaty);
        mMap.addMarker(marker);
        mMap.animateCamera(CameraUpdateFactory.newLatLng(almaty));
        mMap.setOnMapClickListener(this);
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
                            LatLng myLocation = new LatLng(location.getLatitude(),location.getLongitude());
                            MakeRequestWeather(myLocation);
                        }

                    }
                });
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng));
        MakeRequestWeather(latLng);

    }
}
