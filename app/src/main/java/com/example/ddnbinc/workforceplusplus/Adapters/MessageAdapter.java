package com.example.ddnbinc.workforceplusplus.Adapters;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ddnbinc.workforceplusplus.Classes.Message.ImageMessage;
import com.example.ddnbinc.workforceplusplus.Classes.Message.Message;
import com.example.ddnbinc.workforceplusplus.Classes.Message.TextMessage;
import com.example.ddnbinc.workforceplusplus.Classes.Users.Employee;
import com.example.ddnbinc.workforceplusplus.Decorator.OtherOngoingDecorator;
import com.example.ddnbinc.workforceplusplus.Decorator.OtherStartDecorator;
import com.example.ddnbinc.workforceplusplus.Decorator.TextDecorator;
import com.example.ddnbinc.workforceplusplus.Decorator.UserOngoingDecorator;
import com.example.ddnbinc.workforceplusplus.Decorator.UserStartDecorator;
import com.example.ddnbinc.workforceplusplus.R;
import com.example.ddnbinc.workforceplusplus.Utilities.StringFormater;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by davidhuang on 2017-03-11.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>  {

    private ArrayList<Message> messages;
    private Employee employee;
    private Activity mActivity;
    private boolean type;

    private static final int USER_MESSAGE = 0;
    private static final int OTHERS_MESSAGE = 1;
    private static final int USER_MESSAGE_ONGOING = 2;
    private static final int OTHERS_MESSAGE_ONGOING = 3;

    private int msg_type;

    private boolean isemployee;


    public MessageAdapter(ArrayList<Message> messages,Employee employee, Activity activity){
        this.messages = messages;
        this.employee = employee;
        this.mActivity = activity;
    }

    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        msg_type = viewType;
        switch (viewType){
            case USER_MESSAGE:
                return new ViewHolder(LayoutInflater.from(mActivity).inflate(R.layout.message_block_user, parent, false));
            case OTHERS_MESSAGE:
                return new ViewHolder(LayoutInflater.from(mActivity).inflate(R.layout.message_block_other, parent, false));
            case USER_MESSAGE_ONGOING:
                return new ViewHolder(LayoutInflater.from(mActivity).inflate(R.layout.message_block_user_ongoing, parent, false));
            case OTHERS_MESSAGE_ONGOING:
                return new ViewHolder(LayoutInflater.from(mActivity).inflate(R.layout.message_block_other_ongoing, parent, false));
        }

        return null;
    }

    @Override
    public void onBindViewHolder(final MessageAdapter.ViewHolder holder, int position) {
        if(type){
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReferenceFromUrl("gs://workforceplusplus.appspot.com/Chat/"
                    +((ImageMessage)messages.get(position)).getImageKey());
            storageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    try {
                        Picasso.with(mActivity).load(task.getResult()).fit().into(holder.imageView);
                    }catch(Exception e){
                        e.printStackTrace();
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                   // Toast.makeText(mActivity, "Failed to download image check Internet Connection", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            if(!isemployee) holder.title.setText(((TextMessage)messages.get(position)).getSenderID());
            else holder.tableLayout.removeView(holder.sender);
            holder.message.setText((((TextMessage) messages.get(position)).getMessage())+ "  "+
                    StringFormater.getmInstance().time(messages.get(position).getTimestamp()));
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        String sender = messages.get(position).getSenderID();
        type = messages.get(position).getType();
        isemployee=false;
        if(position!=0){
            if(sender.equals(messages.get(position-1).getSenderID())){
                if(sender.equals(employee.getEmployeeId())){
                    isemployee=true;
                    return USER_MESSAGE_ONGOING;
                }else{
                    return OTHERS_MESSAGE_ONGOING;
                }
            }else{
                return checkUser(sender);
            }
        }else{
            return checkUser(sender);
        }
    }

    public int checkUser(String sender){
        if(sender.equals(employee.getEmployeeId())){
            isemployee=true;
            return USER_MESSAGE;
        }else{
            return OTHERS_MESSAGE;
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public TextView createTextView(){
        TextView textView = new TextView(mActivity);
        return textView;
    }

    public void settablebackground(TableLayout tableLayout){
        TextDecorator textDecorator = null;
        switch(msg_type) {
            case USER_MESSAGE:
                textDecorator = new UserStartDecorator();
                break;
            case OTHERS_MESSAGE:
                textDecorator = new OtherStartDecorator();
                break;
            case USER_MESSAGE_ONGOING:
                textDecorator = new UserOngoingDecorator();
                break;
            case OTHERS_MESSAGE_ONGOING:
                textDecorator = new OtherOngoingDecorator();
                break;
        }

        if(textDecorator!=null)textDecorator.draw(tableLayout);

    }
    public ImageView createImageView(){
        ImageView imageView = new ImageView(mActivity);


        return imageView;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;
        TextView message;
        TextView timestamp;
        TableLayout tableLayout;
        TableRow sender;

        public ViewHolder(View itemView) {
            super(itemView);
            tableLayout = (TableLayout)itemView.findViewById(R.id.Table);
            sender = (TableRow) tableLayout.findViewById(R.id.tableRow1);
            TableRow message = (TableRow) tableLayout.findViewById(R.id.tableRow2);

            settablebackground(tableLayout);
            if(type){
                imageView = createImageView();
                ((LinearLayout)itemView).addView(imageView);
            }else{
                title = createTextView();
                sender.addView(title);
                this.message = createTextView();

                message.addView(this.message);
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT,1.0f);
                this.message.setLayoutParams(layoutParams);
                this.message.setPadding(0,0,10,0);

                this.timestamp = createTextView();
                this.timestamp.setTextColor((mActivity).getResources().getColor(R.color.darkgray));
                message.addView(this.timestamp);
            }
        }
    }



}

