package com.rowsen.jdcoupon;

import java.io.Serializable;

public class GetPara implements Serializable {
    //Calendar calendar;
    int interval;
    int num;
    long dif_time;
    int time;
    String body;

    GetPara() {

    }

    GetPara(int time, int interval, int num, long dif_time, String body) {
        this.time = time;
        this.interval = interval;
        this.num = num;
        this.dif_time = dif_time;
        this.body = body;
        /*calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        switch (time) {
            case 10:
                calendar.set(Calendar.HOUR_OF_DAY, 10);
                break;
            case 12:
                calendar.set(Calendar.HOUR_OF_DAY, 12);
                break;
            case 14:
                calendar.set(Calendar.HOUR_OF_DAY, 14);
                break;
            case 18:
                calendar.set(Calendar.HOUR_OF_DAY, 18);
                break;
            case 20:
                calendar.set(Calendar.HOUR_OF_DAY, 20);
                break;
        }*/
    }

    @Override
    public String toString() {
        String s = "time:" + time + "," + "interval:" + interval + "," + "num:" + num + "," + "dif_time:" + dif_time;
        return s;
    }
}
