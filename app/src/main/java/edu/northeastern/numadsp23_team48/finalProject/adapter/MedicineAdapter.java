package edu.northeastern.numadsp23_team48.finalProject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.northeastern.numadsp23_team48.R;
import edu.northeastern.numadsp23_team48.finalProject.schema.MedicineModel;
import edu.northeastern.numadsp23_team48.finalProject.schema.OrderModel;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.medicineViewHolder> {
    private ArrayList<MedicineModel> medlist;

    public MedicineAdapter(ArrayList<MedicineModel> medlist) {
        this.medlist = medlist;
    }

    @NonNull
    @Override
    public medicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_item_layout,parent,false);
        return new MedicineAdapter.medicineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull medicineViewHolder holder, int position) {
        final MedicineModel medicine = medlist.get(position);
        holder.medName.setText(medicine.getName());
        holder.price.setText(String.valueOf(medicine.getPrice()));

    }

    @Override
    public int getItemCount() {
        return medlist.size();
    }


    public class medicineViewHolder extends RecyclerView.ViewHolder {

        private TextView medName,price;
        public medicineViewHolder(@NonNull View itemView) {
            super(itemView);
            medName = itemView.findViewById(R.id.medicine_name);
            price = itemView.findViewById(R.id.medicine_price);
        }
    }
}
