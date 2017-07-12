package daniyaramangeldy.weathermap;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by daniyaramangeldy on 12.07.17.
 */

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.simpleVH> {
    private ArrayList<String> list;

    public SimpleAdapter(ArrayList<String> list){
        this.list = list;
    }

    @Override
    public simpleVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_list_item,parent,false);
        return new simpleVH(v);
    }

    @Override
    public void onBindViewHolder(simpleVH holder, int position) {
        holder.text.setText(list.get(position));
        holder.text.setTextColor(ContextCompat.getColor(holder.itemView.getContext(),android.R.color.white));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class simpleVH extends RecyclerView.ViewHolder{
        TextView text;

        public simpleVH(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text1);
        }
    }
}
