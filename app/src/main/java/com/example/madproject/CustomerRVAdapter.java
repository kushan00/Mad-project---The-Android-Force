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

public class CustomerRVAdapter extends RecyclerView.Adapter<CustomerRVAdapter.ViewHolder> {

    private ArrayList<CustomerRVModel> customerRVModelArrayList;
    private Context context;
    int lastPos = -1;
    private CustomerClickInterface customerClickInterface;

    public CustomerRVAdapter(ArrayList<CustomerRVModel> customerRVModelArrayList, Context context, CustomerClickInterface customerClickInterface) {
        this.customerRVModelArrayList = customerRVModelArrayList;
        this.context = context;
        this.customerClickInterface = customerClickInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.customer_rv_item,parent,false);
           return  new ViewHolder(view);
        }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int position) {
        CustomerRVModel customerRVModel = customerRVModelArrayList.get(position);
        holder.customerIDTV.setText(customerRVModel.getCustomerID());
        holder.customerNameTV.setText(customerRVModel.getCustomerName());
        holder.customerAddressTV.setText(customerRVModel.getCustomerAddress());
        holder.customerNICTV.setText(customerRVModel.getCustomerNic());
        setAnimation(holder.itemView, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customerClickInterface.onCustomerClick(position);
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
        return customerRVModelArrayList.size();
    }

    public interface CustomerClickInterface{
        void onCustomerClick(int position);

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView customerNameTV, customerAddressTV,customerNICTV, customerIDTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            customerNameTV = itemView.findViewById(R.id.idTVCustomerName);
            customerAddressTV = itemView.findViewById(R.id.idTVCustomerAddress);
            customerNICTV = itemView.findViewById(R.id.idTVCusNic );
            customerIDTV = itemView.findViewById(R.id.idTVCustomerId);

        }
    }

}
