package daniyaramangeldy.weathermap;

import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

/**
 * Created by daniyaramangeldy on 19.07.17.
 */

public class Icons {
    public static final String NULL = "null";
    private ArrayMap<String,Integer> iconMap;

    public Icons() {
        fillMap();
    }

    private void fillMap() {
        iconMap = new ArrayMap<>();
        iconMap.put("50d",R.drawable.w50d);
        iconMap.put("50n",R.drawable.w50n);
        iconMap.put("01d",R.drawable.w01d);
        iconMap.put("01n",R.drawable.w01n);
        iconMap.put("02d",R.drawable.w02d);
        iconMap.put("02n",R.drawable.w02n);
        iconMap.put("03d",R.drawable.w03d);
        iconMap.put("03n",R.drawable.w03n);
        iconMap.put("04d",R.drawable.w04d);
        iconMap.put("04n",R.drawable.w04n);
        iconMap.put("09d",R.drawable.w09d);
        iconMap.put("09n",R.drawable.w09n);
        iconMap.put("10d",R.drawable.w10d);
        iconMap.put("10n",R.drawable.w10n);
        iconMap.put("11d",R.drawable.w11d);
        iconMap.put("11n",R.drawable.w11n);
        iconMap.put("13d",R.drawable.w12d);
        iconMap.put("13n",R.drawable.w12n);
        iconMap.put("-1",R.drawable.w00d);
    }

    public int getIcon(String key){
        String iconResId = String.valueOf(iconMap.get(key));
        if(TextUtils.isEmpty(iconResId) || iconResId.equals(NULL)){
            return iconMap.get("-1");
        } else{
            return Integer.parseInt(iconResId);
        }
    }
}
