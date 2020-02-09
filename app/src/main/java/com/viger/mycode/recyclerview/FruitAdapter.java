package com.viger.mycode.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.viger.mycode.MainActivity;
import com.viger.mycode.R;
import com.viger.mycode.glide.Glide;

import java.util.List;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {

    public static final String TAG = "FruitAdapter";
    private List<Fruit> mFruitList;
    private Context mContext;

    public FruitAdapter(Context context, List<Fruit> list) {
        this.mContext = context;
        this.mFruitList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_main, parent);
        ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Fruit fruit = mFruitList.get(position);
                String name = fruit.getName();
                int iamgeId = fruit.getImageId();
                //
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("imageid", iamgeId);
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Fruit fruit = mFruitList.get(position);
        holder.textView.setText("");
        Glide.with(mContext).load("").into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mFruitList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.btn);
            imageView = itemView.findViewById(R.id.btn);
            textView = itemView.findViewById(R.id.btn);
        }
    }


}
