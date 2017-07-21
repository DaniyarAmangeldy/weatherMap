package daniyaramangeldy.weathermap.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by daniyaramangeldy on 19.07.17.
 */

public class Weather {
    @SerializedName("main")
    private String desc;
    private String icon;


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
