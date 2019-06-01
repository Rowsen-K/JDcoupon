package com.rowsen.jdcoupon;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;

public class GetCouponService extends Service {
    String coupon_url = "https://api.m.jd.com/client.action?functionId=newBabelAwardCollection";
    String agent = "Mozilla/5.0 (Linux; Android 8.0; Pixel 2 Build/OPD3.170816.012) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Mobile Safari/537.36";
    //String body = "{\"activityId\":\"3J6CSU6S6was4gqMYahCDA5SgTy\",\"from\":\"H5node\",\"scene\":\"1\",\"args\":\"key=D2812FF5595C93DEB07100B738649BD6A2F18F9AF4114E8F1E94BAD002A6E0D77AD8F35A7BAA041CD9C8942D17F97B24_babel,roleId=E7977DF45D147D178446B5686CD18744_babel\",\"platform\":\"3\",\"orgType\":\"2\",\"openId\":\"-1\",\"pageClickKey\":\"Babel_Coupon\",\"eid\":\"S65ETJ6BMMLH5P4CNVA4SDDBH5MAR53LCDPC2BVOOPZ7W2TMZ3EQ5OQZPZM7SD3SSQTVUEOZZTWZ47TCPKLLM3ZYDU\",\"fp\":\"ad693cbf9064b6fda865c1a817965657\",\"shshshfp\":\"923c5560c26f39d3a0afd4c56bcf88b6\",\"shshshfpa\":\"359743c3-1bd8-22a5-12af-2a7dfa5f9c9c-1533910101\",\"shshshfpb\":\"1232a27d447a44cc8a08c07566034d9013b36a680fe2d69c15b6d9c195\",\"childActivityUrl\":\"https%3A%2F%2Fpro.m.jd.com%2Fmall%2Factive%2F3J6CSU6S6was4gqMYahCDA5SgTy%2Findex.html%3Futm_source%3Dandroidapp%26ShareTm%3DCi1SmD4KOATAU%252BH%252BnHiSOOpFFMZcEs5SoLKsuiXoiCRn49JcbQ%252FkAOAHzQyuhygUyFvkn9ssu0F%252F1FVyQIqqQY1YksgmYyTcy1%252B12hZ5yn4VsscZPGBQ2XC7dlgzbaXCyX%252Bhxopr3U5EH1MvxpeU6bGdF3oMKuCbM599qS4yEb0%253D%26ad_od%3Dshare%26utm_medium%3Dappshare%26utm_campaign%3Dt_335139774%26utm_term%3DWxfriends%26from%3Dsinglemessage\",\"mitemAddrId\":\"\",\"geo\":{\"lng\":\"\",\"lat\":\"\"},\"addressId\":\"\",\"posLng\":\"\",\"posLat\":\"\",\"focus\":\"\",\"innerAnchor\":\"\"}";
    NotificationManager manager;
    NotificationChannel channel;
    Notification note;
    Bitmap icon;

    public GetCouponService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
        channel = new NotificationChannel("Rowsen", "Rowsen_GetCoupon", NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("领券结果！");
        channel.enableVibration(true);
        channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        channel.setShowBadge(true);
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
        try {
            icon = BitmapFactory.decodeStream(getAssets().open("r.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("service", "开启");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        GetPara para = (GetPara) intent.getSerializableExtra("para");
        String cookie_159 = intent.getStringExtra("cookie_159");
        String cookie_135 = intent.getStringExtra("cookie_135");
        String cookie_134 = intent.getStringExtra("cookie_134");
        /*Log.e("para", para.toString());
        Log.e("cookie_159", cookie_159);
        Log.e("cookie_134", cookie_134);
        Log.e("cookie_135", cookie_135);*/
        getCoupon(cookie_134, 134, para,para.num);
        getCoupon(cookie_135, 135, para,para.num);
        getCoupon(cookie_159, 159, para,para.num);
        Toasty.success(getApplicationContext(), "后台抢券服务已开启！").show();
        Notification start = new Notification.Builder(getApplicationContext())
                .setContentTitle("抢券服务")
                .setContentText("服务正在运行ing")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.r)
                .setLargeIcon(icon)
                .setChannelId("Rowsen")
                .build();
        startForeground(100,start);
        return START_REDELIVER_INTENT;
    }

    //获取优惠券的具体请求代码
    void getCoupon(final String cookie, final int num, final GetPara para,final int n) {
        new Thread() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                long current_time = System.currentTimeMillis();
                long set_time = setTime(para.time).getTimeInMillis() - para.dif_time;
                while (current_time < set_time) {
                    SystemClock.sleep(50);
                    current_time = System.currentTimeMillis();
                }
                try {
                    Connection.Response response = Jsoup.connect(coupon_url)
                            .header("Content-Type", "application/x-www-form-urlencoded")
                            .header("Accept", "application/json")
                            .header("Cookie", cookie)
                            .userAgent(agent)
                            .method(Connection.Method.POST)
                            .data("body", para.body)
                            .data("client", "wh5")
                            .data("clientVersion", "1.0.0")
                            .data("sid", "")
                            .data("uuid", "")//1530348927005997475156
                            .data("area", "")
                            .execute();
                    Log.e("response", response.body());
                    String s = response.body();
                    s = s.split(":\\\"")[1];
                    if (s.contains("领取成功")) {
                        note = new Notification.Builder(getApplicationContext())
                                .setContentTitle(num + "：领取成功！")
                                .setContentText(num + "：领取成功！")
                                .setWhen(System.currentTimeMillis())
                                .setSmallIcon(R.drawable.r)
                                .setLargeIcon(icon)
                                .setChannelId("Rowsen")
                                .build();
                    } else {
                        note = new Notification.Builder(getApplicationContext())
                                .setContentTitle(num + s)
                                .setContentText(num + s)
                                .setWhen(System.currentTimeMillis())
                                .setSmallIcon(R.drawable.r)
                                .setLargeIcon(icon)
                                .setChannelId("Rowsen")
                                .build();
                    }
                    manager.notify(num, note);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (n > 1) {
                    SystemClock.sleep(para.interval - 10);
                    getCoupon(cookie, num, para,n-1);
                }
            }
        }.start();
    }

    //获取设定时间
    Calendar setTime(int time){
        Calendar calendar = Calendar.getInstance();
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
        }
        return calendar;
    }
}
