package daniyaramangeldy.weathermap;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class LocationService extends IntentService {

    public static final String ACTION_LOCATION = "action_location";
    public static final String EXTRA_LATITUDE = "lat";
    private static final String TAG = "LocationService";
    public static final String EXTRA_LONGITUDE = "lon";
    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily";
    public static final int TIMEOUT = 15;
    public static final String KEY_UNITS = "units";
    public static final String VALUE_METRIC = "metric";
    public static final String KEY_DAYS = "cnt";
    public static final String KEY_LANG = "lang";
    public static final String LANG_RU = "ru";
    public static final String KEY_TOKEN = "appid";
    public static final String EXTRA_TOKEN = "extra_token";
    public static final String PREFERENCES = "preferences";
    public static final String EMPTY_STRING = "";
    public static final String EXTRA_REQUEST_RESULT = "extra_request_result";
    private OkHttpClient client;

    public LocationService() {
        super("LocationService");
        client = createOkHttpClient();
    }

    private OkHttpClient createOkHttpClient() {
        return new OkHttpClient.Builder()
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();
    }


    public static void startActionLocation(Context context, LatLng coordinates) {
        String token = getToken(context);
        Intent intent = new Intent(context, LocationService.class);
        intent.setAction(ACTION_LOCATION);
        intent.putExtra(EXTRA_LATITUDE, coordinates.latitude);
        intent.putExtra(EXTRA_LONGITUDE, coordinates.longitude);
        intent.putExtra(EXTRA_TOKEN, token);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            switch (action) {
                case ACTION_LOCATION: {
                    String url = createUrl(intent);
                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    makeRequest(request);
                    break;
                }

            }
        }
    }

    private void makeRequest(Request request) {
        try {
            Response response = client.newCall(request).execute();
            String result = response.body().string();
            Intent resultIntent = new Intent("filter1");
            resultIntent.putExtra(EXTRA_REQUEST_RESULT,result);
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(resultIntent);
            Log.d(TAG, "onHandleIntent: " + result);

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private String createUrl(Intent intent) {
        String lat = String.valueOf(intent.getDoubleExtra(EXTRA_LATITUDE, 0));
        String lon = String.valueOf(intent.getDoubleExtra(EXTRA_LONGITUDE, 0));
        String token = intent.getStringExtra(EXTRA_TOKEN);
        Uri uri = new Uri.Builder()
                .encodedPath(BASE_URL)
                .appendQueryParameter(EXTRA_LATITUDE, lat)
                .appendQueryParameter(EXTRA_LONGITUDE, lon)
                .appendQueryParameter(KEY_UNITS, VALUE_METRIC)
                .appendQueryParameter(KEY_DAYS, "16")
                .appendQueryParameter(KEY_LANG, LANG_RU)
                .appendQueryParameter(KEY_TOKEN, token)
                .build();
        return uri.toString();
    }

    private static String getToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        if (TextUtils.isEmpty(sp.getString(EXTRA_TOKEN, EMPTY_STRING))) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(EXTRA_TOKEN, "70036e60bd96aef0b0c812f889829cde");
            editor.apply();
        }
        return sp.getString(EXTRA_TOKEN, EMPTY_STRING);
    }
}
