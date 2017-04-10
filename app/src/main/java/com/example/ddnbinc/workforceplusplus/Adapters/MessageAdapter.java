package com.example.ddnbinc.workforceplusplus.Adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ddnbinc.workforceplusplus.Classes.Message.ImageMessage;
import com.example.ddnbinc.workforceplusplus.Classes.Message.Message;
import com.example.ddnbinc.workforceplusplus.Classes.Message.TextMessage;
import com.example.ddnbinc.workforceplusplus.Classes.Users.Employee;
import com.example.ddnbinc.workforceplusplus.DataBaseConnection.DataBaseConnectionPresenter;
import com.example.ddnbinc.workforceplusplus.Decorator.OtherOngoingDecorator;
import com.example.ddnbinc.workforceplusplus.Decorator.OtherStartDecorator;
import com.example.ddnbinc.workforceplusplus.Decorator.TextDecorator;
import com.example.ddnbinc.workforceplusplus.Decorator.UserOngoingDecorator;
import com.example.ddnbinc.workforceplusplus.Decorator.UserStartDecorator;
import com.example.ddnbinc.workforceplusplus.MainActivity;
import com.example.ddnbinc.workforceplusplus.R;
import com.example.ddnbinc.workforceplusplus.Utilities.StringFormater;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.IOException;
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

    public void setImage(ImageView view, Uri Path)throws IOException{
        view.setImageBitmap(MediaStore.Images.Media.getBitmap(mActivity.getContentResolver(),Path));

    }

    private void downloadImage(final String imageKey, final ImageView imageView){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://workforceplusplus.appspot.com/chatroom/"
                + imageKey);
        storageRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                try {

                    Target target = new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            String path = saveToInternalStorage(bitmap);
                            if (path != null) {
                                try {
                                    setImage(imageView, Uri.parse(path));
                                }catch (IOException e){
                                    // probably wont throw an error
                                }
                                employee.pushImage(imageKey, path);
                                DataBaseConnectionPresenter dataBaseConnectionPresenter = ((MainActivity) mActivity).getDataBaseConnectionPresenter();
                                dataBaseConnectionPresenter.getDbReference().child("Users").child(employee.getEmployeeId()).child("storedImages")
                                        .setValue(employee.getStoredImages());
                            }else{
                                //error with saving image into gallery
                            }
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {
                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {
                        }
                    };

                    Picasso.with(mActivity).load(task.getResult().toString()).into(target);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(mActivity, "Failed to download image check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBindViewHolder(MessageAdapter.ViewHolder holder, int position) {
        if(holder.timestamp != null) {
            if (type) {
                final String imageKey = ((ImageMessage) messages.get(position)).getImageKey();
                Uri imagePath = employee.getFindImagePath(imageKey);
                // if(holder.imageView==null) holder.imageView = createImageView();
                if (imagePath != null) {
                    try {
                        setImage(holder.imageView, imagePath);
                    } catch (IOException e) {
                        downloadImage(imageKey, holder.imageView);

                    }
                    // if imagePath returuns Null then delete it from the db and download the image again.
                    // consider using a local db to store the image reference instead of using the cloud
                } else {
                    downloadImage(imageKey, holder.imageView);
                }
            } else {
                if (!isemployee)
                    holder.title.setText(((TextMessage) messages.get(position)).getSenderID());
                else holder.tableLayout.removeView(holder.sender);

                holder.message.setText((((TextMessage) messages.get(position)).getMessage()) + "  " +
                        StringFormater.getmInstance().time(messages.get(position).getTimestamp()));

            }
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
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT,1.0f);
        imageView.setLayoutParams(layoutParams);
        imageView.setPadding(0,0,10,0);

        imageView.setMinimumHeight(700);
        imageView.setMinimumWidth(700);

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
            final TableRow message = (TableRow) tableLayout.findViewById(R.id.tableRow2);

            settablebackground(tableLayout);
            if(type){

                imageView = createImageView();

                message.addView(imageView);

            }else{
                title = createTextView();
                sender.addView(title);
                this.message = createTextView();

                message.addView(this.message);
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams
                        (TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT,1.0f);

                this.message.setLayoutParams(layoutParams);
                this.message.setPadding(0,0,10,0);

                this.timestamp = createTextView();
                this.timestamp.setTextColor((mActivity).getResources().getColor(R.color.darkgray));
                message.addView(this.timestamp);
            }
        }
    }


    private String saveToInternalStorage(Bitmap bitmapImage){

        return MediaStore.Images.Media.insertImage(mActivity.getContentResolver(), bitmapImage, "WorkForce++" , null);
    }
}

