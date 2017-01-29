package com.example.ddnbinc.workforceplusplus.Utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by davidhuang on 2017-01-27.
 * can be simplified greater
 */


public class StringFormater {
    private SimpleDateFormat formatter;

    public StringFormater(){

    }

    public String setDays(Long time){
        formatter = new SimpleDateFormat("EEEE");

        String date = formatter.format(new Date(time * 1000L));
        return date;
    }
    public String Process(Long s,Long e){
        Long Start_time = (s % 86400);
        Long End_time = (e % 86400);

        String start = combine(Start_time / 3600,(Start_time % 3600) / 60,Start_time);
        String end = combine(End_time / 3600,(End_time % 3600) / 60,End_time);

        formatter = new SimpleDateFormat("MMMM d, yyyy");
        String dateString_begin = formatter.format(new Date(s * 1000L))+" "+start+"-"+end;
        return dateString_begin;
    }
    public String combine(Long hour, Long minutes, Long time){
        String AMPM;
        if (time >= 43200) {
            AMPM = " PM";
            if(time>=46800)hour-=12;
        } else AMPM = " AM";
        return  hour.toString()+":"+minutes.toString()+AMPM;
    }



}
