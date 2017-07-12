package daniyaramangeldy.weathermap;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.concurrent.Executor;


public class LocationService extends IntentService {

    private FusedLocationProviderClient mFusedLocationClient;

    public static final String ACTION_LOCATION = "action_location";
    public static final String EXTRA_MESSAGE = "extra_message";
    private static final String TAG = "LocationService";
    public static final String ACTION_ADDITION = "ACTION_ADDITION";
    public static final String EXTRA_NUMBER_1 = "EXTRA_NUMBER1";
    public static final String EXTRA_NUMBER_2 = "EXTRA_NUMBER2";


    public LocationService() {
        super("LocationService");
    }


    public static void startActionLocation(Context context) {
        Intent intent = new Intent(context, LocationService.class);
        intent.setAction(ACTION_LOCATION);
        intent.putExtra(EXTRA_MESSAGE, "Privet");
        context.startService(intent);
    }

    public static void startActionAddition(Context context,int first,int second){
        Intent intent = new Intent(context, LocationService.class);
        intent.setAction(ACTION_ADDITION);
        intent.putExtra(EXTRA_NUMBER_1, first);
        intent.putExtra(EXTRA_NUMBER_2, second);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            switch (action) {
                case ACTION_LOCATION:{
//                    mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//                    mFusedLocationClient.getLastLocation()
//                            .addOnSuccessListener(new Executor() {
//                                @Override
//                                public void execute(@NonNull Runnable command) {
//
//                                }
//                            }, new OnSuccessListener<Location>() {
//                                @Override
//                                public void onSuccess(Location location) {
//                                    // Got last known location. In some rare situations this can be null.
//                                    if (location != null) {
//                                        Log.d(TAG, String.format("onSuccess: %s %s", location.getLatitude(), location.getLongitude()));
//                                    }
//                                }
//                            });
                    break;
                }

                case ACTION_ADDITION: {
                    int numberOne = intent.getIntExtra(EXTRA_NUMBER_1,0);
                    int numberTwo = intent.getIntExtra(EXTRA_NUMBER_2,0);
                    Log.d(TAG, "onHandleIntent: "+add2Numbers(numberOne,numberTwo));
                    break;
                }

            }
        }
    }

    private int add2Numbers(int first,int second){
        return first+second;
    }
}
