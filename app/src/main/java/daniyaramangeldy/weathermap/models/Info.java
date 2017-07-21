package daniyaramangeldy.weathermap.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by daniyaramangeldy on 19.07.17.
 */

public class Info {
    private Temp temp;
    private List<Weather> weather;
    @SerializedName("dt")
    private long timeInMillis;

    public Temp getTemp() {
        return temp;
    }

    public void setTemp(Temp temp) {
        this.temp = temp;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public long getTimeInMillis() {
        return timeInMillis;
    }

    public void setTimeInMillis(long timeInMillis) {
        this.timeInMillis = timeInMillis;
    }
}
