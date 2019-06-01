package com.rowsen.jdcoupon;

import java.io.Serializable;

public class GetPara implements Serializable {
    //Calendar calendar;
    int interval;
    int num;
    long dif_time;
    int time;
    String body_manual;
    String body_40;
    String body_2;

    GetPara() {

    }

    GetPara(int time, int interval, int num, long dif_time, String body_manual,String body_40,String body_2) {
        this.time = time;
        this.interval = interval;
        this.num = num;
        this.dif_time = dif_time;
        this.body_manual = body_manual;
        this.body_40 = body_40;
        this.body_2 = body_2;
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
        return "time:" + time + "," + "interval:" + interval + "," + "num:" + num + "," + "dif_time:" + dif_time;
    }
}
