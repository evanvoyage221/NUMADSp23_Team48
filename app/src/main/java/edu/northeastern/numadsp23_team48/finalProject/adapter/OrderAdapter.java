package edu.northeastern.numadsp23_team48.finalProject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.northeastern.numadsp23_team48.R;
import edu.northeastern.numadsp23_team48.finalProject.schema.OrderModel;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.viewHolder> {
    //private Context context;
    private ArrayList<OrderModel> orderlist;

    public OrderAdapter(ArrayList<OrderModel> orderlist) {
        //this.context = context;
        this.orderlist = orderlist;
    }

    @NonNull
    @Override
    public OrderAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_detail,parent,false);
        return new OrderAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.viewHolder holder, int position) {
            final OrderModel order = orderlist.get(position);
            holder.orderDate.setText(order.getOrderDate());
            holder.itemType.setText(order.getItemName());
            holder.price.setText(order.getTotalPrice());
    }

    @Override
    public int getItemCount() {
        return orderlist.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView price,orderDate,itemType;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            orderDate = itemView.findViewById(R.id.order_date);
            itemType = itemView.findViewById(R.id.order_item);
            price = itemView.findViewById(R.id.order_price);
        }
    }
}
