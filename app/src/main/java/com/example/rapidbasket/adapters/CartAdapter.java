package com.example.rapidbasket.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rapidbasket.R;
import com.example.rapidbasket.models.CartModel;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHoder> {

    Context context;
    List<CartModel> list;

    int totalBill;

    public CartAdapter(Context context, List<CartModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHoder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        holder.date.setText(list.get(position).getCurrentDate());
        holder.time.setText(list.get(position).getCurrentTime());
        holder.price.setText(list.get(position).getProductPrice()+"₹");
        holder.name.setText(list.get(position).getProductName());
        holder.total_price.setText(String.valueOf(list.get(position).getTotal_price()));
        holder.total_quantity.setText(list.get(position).getTotal_quantity());

//        total bill pass to cart activity
        totalBill=totalBill+list.get(position).getTotal_price();
        Intent intent=new Intent("TotalBill");
        intent.putExtra("total_bill",totalBill);

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        TextView name,price,date,time, total_quantity, total_price;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.product_name);
            price=itemView.findViewById(R.id.product_price);
            date=itemView.findViewById(R.id.current_date);
            time=itemView.findViewById(R.id.current_time);
            total_quantity =itemView.findViewById(R.id.total_quantity);
            total_price =itemView.findViewById(R.id.total_price);
        }
    }
}
