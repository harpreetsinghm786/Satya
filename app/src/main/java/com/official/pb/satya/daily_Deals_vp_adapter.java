package com.official.pb.satya;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class daily_Deals_vp_adapter extends PagerAdapter {
    private List<Banner_list>models;
    private LayoutInflater layoutInflater;
    private Context context;

    public daily_Deals_vp_adapter(List<Banner_list> models, Context context) {
        this.models = models;
        this.context = context;
    }



    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.image_slider_layout_item,container,false);
        RoundedImageView imageView;
//        TextView title,desc;
        imageView=view.findViewById(R.id.iv_auto_image_slider);
//        title=view.findViewById(R.id.title);
//        desc=view.findViewById(R.id.desc);

        Picasso.with(context).load(models.get(position).getBanner_url()).into(imageView);
//        title.setText(models.get(position).getTitle());
//        desc.setText(models.get(position).getDes());
        container.addView(view,0);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle=new Bundle();
                bundle.putString("key",models.get(position).getBanner_key());
                category_expander frag= new category_expander();
                frag.setArguments(bundle);
                ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,frag).commit();


            }
        });
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
