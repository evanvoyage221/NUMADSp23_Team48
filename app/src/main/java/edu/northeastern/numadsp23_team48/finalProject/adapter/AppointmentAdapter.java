package edu.northeastern.numadsp23_team48.finalProject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.northeastern.numadsp23_team48.R;
import edu.northeastern.numadsp23_team48.finalProject.schema.AppointmentModel;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<AppointmentModel> mAppointmentList;

    public AppointmentAdapter(Context context, ArrayList<AppointmentModel> appointmentList) {
        mContext = context;
        mAppointmentList = appointmentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.appointment_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppointmentModel appointment = mAppointmentList.get(position);

        holder.tvName.setText(appointment.getName());
        holder.tvAddress.setText(appointment.getHospital());
        holder.tvContact.setText(appointment.getContact());
        holder.tvFee.setText(appointment.getFees());
        holder.tvDate.setText(appointment.getDate());
        holder.tvTime.setText(appointment.getTime());
    }

    @Override
    public int getItemCount() {
        return mAppointmentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvAddress, tvContact, tvFee, tvDate, tvTime;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvContact = itemView.findViewById(R.id.tvContact);
            tvFee = itemView.findViewById(R.id.tvFee);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }
}

