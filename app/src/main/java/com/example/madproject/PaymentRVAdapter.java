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

public class PaymentRVAdapter extends RecyclerView.Adapter<PaymentRVAdapter.ViewHolder> {

    private ArrayList<PaymentDetialsRVModal> paymentDetialsRVModalArrayList;
    private Context context;
    int lastPos = -1;
    private PaymentCLickInterface paymentCLickInterface;

    public PaymentRVAdapter(ArrayList<PaymentDetialsRVModal> paymentDetialsRVModalArrayList, Context context, PaymentCLickInterface paymentCLickInterface) {
        this.paymentDetialsRVModalArrayList = paymentDetialsRVModalArrayList;
        this.context = context;
        this.paymentCLickInterface = paymentCLickInterface;
    }

    @NonNull
    @Override
    public PaymentRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.payment_rv_details,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentRVAdapter.ViewHolder holder, int position) {
            PaymentDetialsRVModal paymentDetialsRVModal = paymentDetialsRVModalArrayList.get(position);
            holder.regidTV.setText(paymentDetialsRVModal.getRegID());
            holder.fullNameTV.setText(paymentDetialsRVModal.getFullname());
            holder.addressTV.setText(paymentDetialsRVModal.getAddress());
            holder.accNUmberTV.setText(paymentDetialsRVModal.getAccNumber());
            setAnimation(holder.itemView, position);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    paymentCLickInterface.onPaymentClick(position);
                }
            });
    }

    private void setAnimation(View itemView,int position){
        if(position>lastPos){
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;
        }
    }


    @Override
    public int getItemCount() {
        return paymentDetialsRVModalArrayList.size();
    }


    public interface PaymentCLickInterface{
        void onPaymentClick(int position);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView regidTV,fullNameTV,addressTV,accNUmberTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            regidTV = itemView.findViewById(R.id.idTVregID);
            fullNameTV = itemView.findViewById(R.id.idTVfName);
            addressTV = itemView.findViewById(R.id.idTVAddress);
            accNUmberTV = itemView.findViewById(R.id.idTVaccNumber);
        }
    }


}
