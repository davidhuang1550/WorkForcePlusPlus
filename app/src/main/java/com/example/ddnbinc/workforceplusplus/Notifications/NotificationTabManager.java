package com.example.ddnbinc.workforceplusplus.Notifications;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import com.example.ddnbinc.workforceplusplus.Adapters.NotificationRecycleAdapter;
import com.example.ddnbinc.workforceplusplus.Classes.Notification;
import com.example.ddnbinc.workforceplusplus.Classes.Users.Employee;
import com.example.ddnbinc.workforceplusplus.DataBaseConnection.DataBaseConnectionPresenter;
import com.example.ddnbinc.workforceplusplus.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
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
                        for(DataSnapshot snapshot :dataSnapshot.getChildren()){
                            Notification notifi = snapshot.getValue(Notification.class);
                            notifications.add(notifi);
                        }
                        if(first_iteration==false){
                            setView();
                            first_iteration=true;
                        }
                        else{
                            Snackbar.make(((MainActivity)mActivity).getCurrentFocus(), "New Notifications has been added" +
                                    ", Please Refresh.", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }
    public void setView(){
        swipeRefreshLayout.setRefreshing(false);
        adapter = new NotificationRecycleAdapter(notifications);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
               // recyclerView.getAdapter().notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
              //  adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if(direction == ItemTouchHelper.RIGHT){
                    Toast.makeText(mActivity, "Swiped right", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(mActivity, "Swiped left", Toast.LENGTH_SHORT).show();
                }
                notifications.remove(viewHolder.getAdapterPosition());
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());

            }
        }).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
    }

}
