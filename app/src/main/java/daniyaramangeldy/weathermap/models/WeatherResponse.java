package daniyaramangeldy.weathermap.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by daniyaramangeldy on 19.07.17.
 */

public class WeatherResponse {
    private City city;
    @SerializedName("list")
    private List<Info> infoList;


    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<Info> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<Info> infoList) {
        this.infoList = infoList;
    }

}
