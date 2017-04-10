package com.example.ddnbinc.workforceplusplus.Notifications;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import com.example.ddnbinc.workforceplusplus.Adapters.NotificationRecycleAdapter;
import com.example.ddnbinc.workforceplusplus.Classes.Notifications.MessageNotification;
import com.example.ddnbinc.workforceplusplus.Classes.Notifications.Notification;
import com.example.ddnbinc.workforceplusplus.Classes.Notifications.PendingNotification;
import com.example.ddnbinc.workforceplusplus.Classes.Notifications.ResponseNotification;
import com.example.ddnbinc.workforceplusplus.Classes.Notifications.UrgentNotification;
import com.example.ddnbinc.workforceplusplus.Classes.Users.Employee;
import com.example.ddnbinc.workforceplusplus.DataBaseConnection.DataBaseConnectionPresenter;
import com.example.ddnbinc.workforceplusplus.Error.ErrorView;
import com.example.ddnbinc.workforceplusplus.MainActivity;
import com.example.ddnbinc.workforceplusplus.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by davidhuang on 2017-03-05.
 */

public class NotificationTabManager {
    private Activity mActivity;
    private DataBaseConnectionPresenter dataBaseConnectionPresenter;
    private RecyclerView recyclerView;
    private ArrayList<Notification>notifications;
    private NotificationRecycleAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Employee employee;
    private boolean first_iteration;

    public NotificationTabManager(Activity activity, RecyclerView rview, SwipeRefreshLayout swipe){
        mActivity=activity;
        dataBaseConnectionPresenter= ((MainActivity)mActivity).getDataBaseConnectionPresenter();
        recyclerView=rview;
        notifications= new ArrayList<>();
        swipeRefreshLayout=swipe;
        first_iteration=false;
    }
    public void setNotifications(){
        employee = ((MainActivity)mActivity).getEmployee();
        swipeRefreshLayout.setRefreshing(true);
        dataBaseConnectionPresenter.getDbReference().child("Users").child(employee.getEmployeeId()).child("Notifications").
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Notification notifi = null;
                                if (snapshot.hasChild("shift")) {
                                    notifi = snapshot.getValue(PendingNotification.class);
                                } else if (snapshot.hasChild("message")) {
                                    notifi = snapshot.getValue(MessageNotification.class);
                                } else if (snapshot.hasChild("response")) {
                                    notifi = snapshot.getValue(ResponseNotification.class);
                                } else {
                                    notifi = snapshot.getValue(UrgentNotification.class);
                                }
                                notifi.setId(snapshot.getKey());
                                notifications.add(notifi);
                            }
                            if (first_iteration == false) {
                                setView();
                                first_iteration = true;
                            } else {
                                Snackbar.make(((MainActivity) mActivity).getCurrentFocus(), "New Notifications has been added" +
                                        ", Please Refresh.", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        }catch (DatabaseException e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }
    public void setView(){
        swipeRefreshLayout.setRefreshing(false);


        if(notifications.size() == 0){
            mActivity.getFragmentManager().beginTransaction().replace(R.id.content_frame,new ErrorView()).commit();
        }
        else{
            adapter = new NotificationRecycleAdapter(notifications,mActivity);
        }
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if(direction == ItemTouchHelper.RIGHT){
                //    Toast.makeText(mActivity, "Swiped right", Toast.LENGTH_SHORT).show();
                }else{
                  //  Toast.makeText(mActivity, "Swiped left", Toast.LENGTH_SHORT).show();
                }
                notifications.remove(viewHolder.getAdapterPosition());
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());

            }
        }).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
    }

}
