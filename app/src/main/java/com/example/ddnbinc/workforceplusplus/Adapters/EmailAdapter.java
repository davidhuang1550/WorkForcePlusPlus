package com.example.ddnbinc.workforceplusplus.Adapters;
import com.example.ddnbinc.workforceplusplus.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;

/**
 * Created by davidhuang on 2017-02-18.
 */

public class EmailAdapter extends BaseAdapter {
    private ArrayList<String> Emails;
    private Activity mActivity;

    public EmailAdapter(ArrayList<String> list,Activity activity){
        Emails=list;
        mActivity=activity;
    }

    @Override
    public int getCount() {
        return Emails.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View row;
        final LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.email_list_component,null);
        final String text = Emails.get(i);
        TextView textView = (TextView)row.findViewById(R.id.Email_id);
        textView.setText(text);

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"+text));
                String title = "Email Via";
                Intent chooser = Intent.createChooser(intent, title);
                mActivity.startActivity(chooser);

            }
        });



        return row;
    }
}
