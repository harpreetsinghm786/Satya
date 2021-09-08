package com.official.pb.satya;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class History_adapter extends RecyclerView.Adapter<History_adapter.ViewHolder> {

    private Context context;
    private List<History_list> uploads;

    EditText text;

    public History_adapter(Context context, List<History_list> uploads, EditText text) {
        this.uploads = uploads;
        this.context = context;
        this.text = text;


    }

    @Override
    public History_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item, parent, false);
        History_adapter.ViewHolder viewHolder = new History_adapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final History_adapter.ViewHolder holder, final int position) {
        final History_list list = uploads.get(position);
        holder.name.setText(list.getHistory());
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text.setText(list.getHistory());
                //text.setSelection(text.getText().length());
                text.clearFocus();
                Bundle bundle = new Bundle();

                bundle.putString("key", text.getText().toString());

                search_results frag = new search_results();
                frag.setArguments(bundle);
                ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, frag).commit();
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(holder.item.getWindowToken(), 0);

            }
        });


    }

    @Override
    public int getItemCount() {
        return uploads.size();


    }




    class ViewHolder extends RecyclerView.ViewHolder {

        View item;
        TextView name;


        public ViewHolder(final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.suggestions);

            this.item = itemView;

        }


    }


}
