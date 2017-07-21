package daniyaramangeldy.weathermap;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.util.Calendar;

import daniyaramangeldy.weathermap.models.Info;
import daniyaramangeldy.weathermap.models.WeatherResponse;

/**
 * Created by daniyaramangeldy on 12.07.17.
 */

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.simpleVH> {
    private WeatherResponse response;


    public void setResponse(WeatherResponse response){
        this.response = response;
    }
    @Override
    public simpleVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_list_item,parent,false);
        return new simpleVH(v);
    }

    @Override
    public void onBindViewHolder(simpleVH holder, int position) {
        holder.bind(response.getInfoList().get(position));
    }

    @Override
    public int getItemCount() {
        return response.getInfoList().size();
    }

    public class simpleVH extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvDate;
        TextView tvTemp;
        ImageView ivWeatherIcon;
        Calendar calendar;
        Icons weatherIcons;

        public simpleVH(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.weatherName);
            tvDate = (TextView) itemView.findViewById(R.id.weatherDate);
            tvTemp = (TextView) itemView.findViewById(R.id.weatherTemp);
            ivWeatherIcon = (ImageView) itemView.findViewById(R.id.weatherImage);
            calendar = Calendar.getInstance();
            weatherIcons = new Icons();
        }


        private void bind(Info weatherInfo){
            String temp = String.format("%s°C",(int)weatherInfo.getTemp().getTemp());
            tvName.setText(weatherInfo.getWeather().get(0).getDesc());
            tvTemp.setText(temp);
            ivWeatherIcon.setImageDrawable(ContextCompat.getDrawable(this.itemView.getContext(),weatherIcons.getIcon(weatherInfo.getWeather().get(0).getIcon())));
            tvDate.setText(DateUtils.setTime(weatherInfo.getTimeInMillis()));
        }
    }
}
