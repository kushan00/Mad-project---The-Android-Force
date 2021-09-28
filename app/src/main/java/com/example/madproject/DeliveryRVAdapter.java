package com.example.madproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DeliveryRVAdapter extends RecyclerView.Adapter<DeliveryRVAdapter.ViewHolder> {
    int lastPos=-1;
    private ArrayList<DeliveryRVModal> deliveryRVModalArrayList;
    private Context context;
    private DeliveryClickInterface deliveryClickInterface;

    public DeliveryRVAdapter(ArrayList<DeliveryRVModal> deliveryRVModalArrayList, Context context, DeliveryClickInterface deliveryClickInterface) {
        this.deliveryRVModalArrayList = deliveryRVModalArrayList;
        this.context = context;
        this.deliveryClickInterface = deliveryClickInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.delivery_rv_item,parent,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
     DeliveryRVModal deliveryRVModal = deliveryRVModalArrayList.get(position);
     holder.customerNameTV.setText(deliveryRVModal.getCustomerName());
     holder.buildingNumberTV.setText(deliveryRVModal.getBuildingNumber());
     holder.streetTV.setText(deliveryRVModal.getStreetName());
     holder.cityTV.setText(deliveryRVModal.getCity());
     setAnimation(holder.itemView,position);

     holder.itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             deliveryClickInterface.onDeliveryClick(position);
         }
     });
    }

    private void setAnimation (View itemView, int position){

        if (position>lastPos){
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos=position;
        }

    }

    @Override
    public int getItemCount() {
        return deliveryRVModalArrayList.size();
    }
    public interface DeliveryClickInterface{
        void onDeliveryClick(int position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView customerNameTV,buildingNumberTV,streetTV,cityTV;
        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            customerNameTV = itemView.findViewById(R.id.idTVCustomer);
            buildingNumberTV = itemView.findViewById(R.id.idTVBuilding);
            streetTV = itemView.findViewById(R.id.idTVStreet);
            cityTV = itemView.findViewById(R.id.idTVCity);
        }
    }


}
