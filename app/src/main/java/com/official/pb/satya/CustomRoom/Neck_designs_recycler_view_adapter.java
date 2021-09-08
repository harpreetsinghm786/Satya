package com.official.pb.satya.CustomRoom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.official.pb.satya.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class Neck_designs_recycler_view_adapter extends RecyclerView.Adapter<Neck_designs_recycler_view_adapter.ViewHolder> {

    private Context context;
    private List<Neck_designs> uploads;

    public Neck_designs_recycler_view_adapter(Context context, List<Neck_designs> uploads) {
        this.uploads = uploads;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.neck_design_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Neck_designs list = uploads.get(position);
        holder.name.setText(list.getName());

        try {
            if(holder.imageView.getDrawable()==null)
                Picasso.with(context).load(list.getUrl()).fit().centerCrop().into(holder.imageView);
        }catch (Exception e){}

    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView imageView;
        CheckBox checkBox;


        public ViewHolder(final View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            imageView=itemView.findViewById(R.id.list_item_image);
            checkBox=itemView.findViewById(R.id.check_box);


        }



    }



}
