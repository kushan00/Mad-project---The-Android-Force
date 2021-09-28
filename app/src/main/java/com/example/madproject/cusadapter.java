package com.example.madproject;

import android.content.Context;
        import android.content.Intent;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import com.bumptech.glide.Glide;
        import com.firebase.ui.database.FirebaseRecyclerAdapter;
        import com.firebase.ui.database.FirebaseRecyclerOptions;

        import de.hdodenhof.circleimageview.CircleImageView;

public class cusadapter extends FirebaseRecyclerAdapter<cusmodel,cusadapter.cusviewholder>

{
    public cusadapter(@NonNull FirebaseRecyclerOptions<cusmodel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull cusviewholder holder, int position, @NonNull cusmodel model)
    {
        holder.name1.setText(model.getName());
        holder.description1.setText(model.getDescription());
        holder.price1.setText(model.getPrice());
        Glide.with(holder.img2.getContext()).load(model.getFurl()).into(holder.img2);



    }


    @NonNull
    @Override
    public cusviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cusfood,parent,false);

        return new cusviewholder(view);



    }

    class cusviewholder extends RecyclerView.ViewHolder

    {
        ImageView  img , img2;
        TextView name1,description1,price1;

        public cusviewholder(@NonNull View itemView) {
            super(itemView);
            name1=(TextView)itemView.findViewById(R.id.nametext);
            description1=(TextView)itemView.findViewById(R.id.descriptiontext);
            price1=(TextView)itemView.findViewById(R.id.pricetext);
            img2=(CircleImageView)itemView.findViewById(R.id.img1);
//photo
        }

    }

}