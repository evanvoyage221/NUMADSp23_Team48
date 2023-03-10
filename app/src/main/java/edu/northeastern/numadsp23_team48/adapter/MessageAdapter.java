package edu.northeastern.numadsp23_team48.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.northeastern.numadsp23_team48.R;
import edu.northeastern.numadsp23_team48.model.ChatMessage;
import edu.northeastern.numadsp23_team48.viewholder.UserViewHolder;

public class MessageAdapter extends RecyclerView.Adapter {

    Context context;
    List<ChatMessage> chats;
    int sendFlag = 1;
    int receiveFlag = 2;

    public MessageAdapter(Context context, List<ChatMessage> chats) {
        this.context = context;
        this.chats = chats;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == sendFlag) {
            View view = LayoutInflater.from(context).inflate(R.layout.sender_chat_layout, parent, false);
            return new SenderViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.receiver_chat_layout, parent, false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getClass() == SenderViewHolder.class) {
            SenderViewHolder senderViewHolder = (SenderViewHolder) holder;
            senderViewHolder.sUserName.setText(chats.get(position).getSender());
            String[] time= chats.get(position).getTimestamp().split(" ");
            senderViewHolder.senderDate.setText(time[0]);
            senderViewHolder.senderTime.setText(time[1].substring(0, 5));
//            senderViewHolder.senderDate.setText(chats.get(position).getTimestamp());
            long imageID = chats.get(position).getImageID();
            if (imageID == 2131165271) {
                senderViewHolder.displaySticker.setImageResource(R.drawable.cat_icon_1);
            } else if (imageID == 2131165308) {
                senderViewHolder.displaySticker.setImageResource(R.drawable.cat_icon_2);
            } else if (imageID == 2131165309) {
                senderViewHolder.displaySticker.setImageResource(R.drawable.cat_icon_3);
            } else if (imageID == 2131165325) {
                senderViewHolder.displaySticker.setImageResource(R.drawable.cat_icon_4);
            } else if (imageID == 2131165368) {
                senderViewHolder.displaySticker.setImageResource(R.drawable.cat_icon_5);
            } else if (imageID == 2131165369) {
                senderViewHolder.displaySticker.setImageResource(R.drawable.cat_icon_6);
            } else {
                senderViewHolder.displaySticker.setImageResource(R.drawable.cat_icon_7);
            }
        }

        if (holder.getClass() == ReceiverViewHolder.class) {
            ReceiverViewHolder receiverViewHolder = (ReceiverViewHolder) holder;
            receiverViewHolder.rUserName.setText(chats.get(position).getSender());
            String[] time= chats.get(position).getTimestamp().split(" ");
//            receiverViewHolder.receiverDate.setText(chats.get(position).getTimestamp());
            receiverViewHolder.receiverDate.setText(time[0]);
            receiverViewHolder.receiverTime.setText(time[1].substring(0, 5));

            long imageID = chats.get(position).getImageID();
            if (imageID == 2131165271) {
                receiverViewHolder.displaySticker.setImageResource(R.drawable.cat_icon_1);
            } else if (imageID == 2131165308) {
                receiverViewHolder.displaySticker.setImageResource(R.drawable.cat_icon_2);
            } else if (imageID == 2131165309) {
                receiverViewHolder.displaySticker.setImageResource(R.drawable.cat_icon_3);
            } else if (imageID == 2131165325) {
                receiverViewHolder.displaySticker.setImageResource(R.drawable.cat_icon_4);
            } else if (imageID == 2131165368) {
                receiverViewHolder.displaySticker.setImageResource(R.drawable.cat_icon_5);
            } else if (imageID == 2131165369) {
                receiverViewHolder.displaySticker.setImageResource(R.drawable.cat_icon_6);
            } else {
                receiverViewHolder.displaySticker.setImageResource(R.drawable.cat_icon_7);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        Intent intent = ((Activity) context).getIntent();
        ChatMessage message = chats.get(position);
        if (intent.getExtras().getString("currentUserName").equals(message.getSender())) {
            return sendFlag;
        } else {
            return receiveFlag;
        }
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    static class SenderViewHolder extends RecyclerView.ViewHolder {
        TextView sUserName;
        TextView senderDate;
        TextView senderTime;
        ImageView displaySticker;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            sUserName = itemView.findViewById(R.id.sender);
            displaySticker = itemView.findViewById(R.id.senderImage);
            senderDate = itemView.findViewById(R.id.sender_date);
            senderTime=itemView.findViewById(R.id.sender_time);
        }
    }

    static class ReceiverViewHolder extends RecyclerView.ViewHolder {
        TextView rUserName;
        TextView receiverDate;
        TextView receiverTime;
        ImageView displaySticker;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            rUserName = itemView.findViewById(R.id.receive);
            displaySticker = itemView.findViewById(R.id.receiverImage);
            receiverDate = itemView.findViewById(R.id.receiver_date);
            receiverTime=itemView.findViewById(R.id.receiver_time);
        }
    }
}