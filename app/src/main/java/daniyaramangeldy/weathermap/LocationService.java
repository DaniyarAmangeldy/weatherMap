package daniyaramangeldy.weathermap;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;


public class LocationService extends IntentService {

    public static final String ACTION_LOCATION = "action_location";
    public static final String EXTRA_MESSAGE = "extra_message";
    private static final String TAG = "LocationService";

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
        //TODO: 1)запустите Интент с новым Action и добавьте 2 int в Extra
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            switch (action) {
                case ACTION_LOCATION:
                    break;
                //TODO: 3)создайте новый case и вызовите функцию
                //TODO: 4) не забудьте показать результат в log.d() :)
            }
        }
    }

    private int add2Numbers(int first,int second){
        //TODO: 2)сделайте сложение 2 чисел и верните результат
        return 0;
    }
}
