package com.rowsen.jdcoupon;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {
    WebView web;
    MyWebViewClient client;
    WebSettings settings;
    CookieManager manager;
    Handler handler;
    SharedPreferences sp;
    GetPara para;
    Button b159;
    Button b135;
    Button b134;
    Button bGet;
    String cookie_159;
    String cookie_135;
    String cookie_134;
    String sid_159;
    String sid_135;
    String sid_134;
    String coupon_url = "https://api.m.jd.com/client.action?functionId=newBabelAwardCollection";
    String agent = "Mozilla/5.0 (Linux; Android 8.0; Pixel 2 Build/OPD3.170816.012) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Mobile Safari/537.36";
    //领券活动地址：
    // https://pro.m.jd.com/mall/active/3J6CSU6S6was4gqMYahCDA5SgTy/index.html?utm_source=androidapp&ShareTm=Ci1SmD4KOATAU%2BH%2BnHiSOOpFFMZcEs5SoLKsuiXoiCRn49JcbQ%2FkAOAHzQyuhygUyFvkn9ssu0F%2F1FVyQIqqQY1YksgmYyTcy1%2B12hZ5yn4VsscZPGBQ2XC7dlgzbaXCyX%2Bhxopr3U5EH1MvxpeU6bGdF3oMKuCbM599qS4yEb0%3D&ad_od=share&utm_medium=appshare&utm_campaign=t_335139774&utm_term=Wxfriends&from=singlemessage
    //90-2地址：
    //https://api.m.jd.com/client.action?functionId=newBabelAwardCollection
    //request body:
    //String body = "{\"activityId\":\"3C3ssKKfw7uQ7QQKF5AY6NoLysxF\",\"from\":\"H5node\",\"scene\":\"1\",\"args\":\"key=4775DEFAD35ABF68977F9D7D3784087495A9F8A0F085094462B439A61DF47DBCB0079FF9130C91D8D889A57221EE53F4_babel,roleId=6589C013794ABF250ADDA86DAFA59815_babel\",\"platform\":\"3\",\"orgType\":\"2\",\"openId\":\"-1\",\"pageClickKey\":\"Babel_Coupon\",\"eid\":\"MO6NI3IES26IIAJDLJ7OBOY2QYXFPMZOUOPOXR5DHAH3PQ37J4LTBMVBNC3UEK657UGDU5NWJCVWUDVHDX5PJZ2PL4\",\"fp\":\"4690768afabdd6396110379b6159ce37\",\"shshshfp\":\"c5a47a1e8855b5f8c5a9016c9cbe0208\",\"shshshfpa\":\"0fb34f83-cfce-4ae0-5979-f43050ce816e-1532822110\",\"shshshfpb\":\"19a11e131aac3429caf4a9b8cbf50aa19a826bb3714d6794e5b1349b89\",\"childActivityUrl\":\"https%3A%2F%2Fpro.m.jd.com%2Fmall%2Factive%2F3C3ssKKfw7uQ7QQKF5AY6NoLysxF%2Findex.html\",\"mitemAddrId\":\"\",\"geo\":{\"lng\":\"\",\"lat\":\"\"},\"addressId\":\"\",\"posLng\":\"\",\"posLat\":\"\",\"focus\":\"\",\"innerAnchor\":\"\"}";

    LinearLayout get_url;
    Button auto;
    Button manual;
    EditText dif_time;
    CheckBox coupon_40;
    CheckBox coupon_2;
    String body_40;
    String body_2;
    String body_manual;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        web = findViewById(R.id.web);
        b135 = findViewById(R.id.user2);
        b134 = findViewById(R.id.user3);
        b159 = findViewById(R.id.user1);
        bGet = findViewById(R.id.get);
        sp = getSharedPreferences("Cookie", MODE_PRIVATE);
        cookie_134 = sp.getString("cookie_134", null);
        cookie_135 = sp.getString("cookie_135", null);
        cookie_159 = sp.getString("cookie_159", null);
        if (cookie_134 != null) {
            b134.setText("134_Cookie(历史)");
            bGet.setEnabled(true);
        }
        if (cookie_135 != null) {
            b135.setText("135_Cookie(历史)");
            bGet.setEnabled(true);
        }
        if (cookie_159 != null) {
            b159.setText("159_Cookie(历史)");
            bGet.setEnabled(true);
        }
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        cookie_135 = (String) msg.obj;
                        //sid_135 = getSid(cookie_135);
                        if (sp.edit().putString("cookie_135", cookie_135).commit()) {
                            b135.setText("135_Cookie(新获取)");
                            bGet.setEnabled(true);
                        }
                        break;
                    case 1:
                        cookie_159 = (String) msg.obj;
                        //sid_159 = getSid(cookie_159);
                        if (sp.edit().putString("cookie_159", cookie_159).commit()) {
                            b159.setText("159_Cookie(新获取)");
                            bGet.setEnabled(true);
                        }
                        break;
                    case 2:
                        cookie_134 = (String) msg.obj;
                        //sid_134 = getSid(cookie_134);
                        if (sp.edit().putString("cookie_134", cookie_134).commit()) {
                            b134.setText("134_Cookie(新获取)");
                            bGet.setEnabled(true);
                        }
                        break;
                    case 3:
                        Toasty.warning(MainActivity.this, "没有获取到任何的用户Cookie！").show();
                        bGet.setEnabled(false);
                        // Toast.makeText(MainActivity.this, "没有获取到任何的用户Cookie！", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        String s = ((String) msg.obj).split("\\\",\\\"")[0];
                        s = s.split(":\\\"")[1];
                        if (s.contains("领取成功")) {
                            Toasty.success(MainActivity.this, "用户" + msg.arg1 + "：" + s).show();
                            switch (msg.arg1) {
                                case 159:
                                    b159.append("(已领)");
                                    break;
                                case 134:
                                    b134.append("(已领)");
                                    break;
                                case 135:
                                    b135.append("(已领)");
                                    break;
                            }
                        } else
                            Toasty.error(MainActivity.this, "用户" + msg.arg1 + "：" + s).show();
                        //Toast.makeText(MainActivity.this, msg.obj + "", Toast.LENGTH_SHORT).show();
                        break;
                    case 5://获取到90-40券时
                        coupon_40 = new CheckBox(MainActivity.this);
                        String[] temp = (msg.obj + "").split("\\|");
                        coupon_40.setText(temp[0]);
                        auto.setVisibility(GONE);
                        manual.setVisibility(GONE);
                        get_url.addView(coupon_40);
                        body_40 = temp[1];
                        break;
                    case 6://获取到90-2券时
                        coupon_2 = new CheckBox(MainActivity.this);
                        coupon_2.setText((msg.obj + "").split("\\|")[0]);
                        auto.setVisibility(GONE);
                        manual.setVisibility(GONE);
                        get_url.addView(coupon_2);
                        body_2 = (msg.obj + "").split("\\|")[1];
                        break;
                    case 7://获取服务器coupon异常时
                        Toasty.error(MainActivity.this, "服务器数据获取异常,请稍后尝试或使用手动!").show();
                        auto.setVisibility(View.VISIBLE);
                        auto.setEnabled(true);
                        manual.setVisibility(View.VISIBLE);
                        manual.setEnabled(true);
                        break;
                    case 8://自动获取时没有勾选任何优惠券
                        Toasty.error(MainActivity.this, "请至少勾选一张优惠券!").show();
                        break;
                    case 9:
                        dif_time.setText(msg.obj+"");
                        break;
                }
            }
        };
        settings = web.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        manager = CookieManager.getInstance();
        client = new MyWebViewClient(handler);
        web.setWebViewClient(client);
        web.loadUrl("https://plogin.m.jd.com/user/login.action?");
    }

    public void click(View view) {
        clearWebViewCache();
        client.cookie = null;
        client.sid = null;
        web.loadUrl("https://plogin.m.jd.com/user/login.action?");
    }

    public void clearWebViewCache() {
// 清除cookie即可彻底清除缓存
        CookieSyncManager.createInstance(this);
        CookieManager.getInstance().removeAllCookie();
    }

    //截取sid功能
    String getSid(String cookie) {
        String sid = null;
        String[] temp = cookie.split(";");
        for (String s : temp) {
            if (s.contains("sid")) {
                // if(s.split("=")[0].trim().equals("sid")) {
                sid = s.split("=")[1];
                //Log.e("sid", sid);
                break;
                //   }
            }
        }
        return sid;
    }

    //抢券按钮，先弹出一个时间选择
    public void get(final View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("设定抢券参数");
        View v = View.inflate(MainActivity.this, R.layout.alert_getpara, null);
        final RadioGroup time = v.findViewById(R.id.group_time);
        final RadioGroup interval = v.findViewById(R.id.group_interval);
        final RadioGroup num = v.findViewById(R.id.group_num);
        dif_time = v.findViewById(R.id.dif_time);
        final EditText url = v.findViewById(R.id.url);
        get_url = v.findViewById(R.id.get_url);
        auto = v.findViewById(R.id.auto);
        manual = v.findViewById(R.id.manual);
        auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auto.setEnabled(false);
                manual.setEnabled(false);
                //获取本地时间与JD时间差
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        Connection.Response response = null;
                        try {
                            response = Jsoup.connect("https://m.jd.com/").timeout(5000).execute();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Calendar calendar = Calendar.getInstance();
                        long local_time = calendar.getTimeInMillis();
                        Date dd = null; //将字符串改为date的格式
                        try {
                            dd = new SimpleDateFormat("EEE,dd MMM yyyy HH:mm:ss 'GMT'", Locale.ENGLISH).parse(response.headers().get("Date"));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        calendar.setTime(dd);
                        calendar.add(Calendar.HOUR_OF_DAY, 8);
                        String sent_time = response.headers().get("X-Android-Sent-Millis");
                        String received_time = response.headers().get("X-Android-Received-Millis");
                        /*Log.e("header", response.headers().toString());
                        Log.e("SERVER_TIME_RAW", dd.getTime() + "");
                        Log.e("SERVER_TIME", calendar.getTimeInMillis() + "");
                        Log.e("sent_millis", sent_time);
                        Log.e("received_millis", received_time);*/
                        String dif_time = calendar.getTimeInMillis() + Long.parseLong(received_time) - Long.parseLong(sent_time) - local_time + "";
                        Message msg = Message.obtain();
                        msg.what = 9;
                        msg.obj = dif_time;
                        handler.sendMessage(msg);
                    }
                }.start();
                //获取券地址
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        Document doc;
                        try {
                            doc = Jsoup.connect("http://free.idcfengye.com:10172").timeout(5000).get();
                            //Log.e("response", doc.body().toString());
                            Elements div_40 = doc.getElementsByClass("90-40");
                            if (div_40.size() > 0) {
                                Message msg_40 = Message.obtain();
                                msg_40.what = 5;
                                msg_40.obj = div_40.get(0).text();
                                // Log.e("90-40", msg_40.obj+"");
                                handler.sendMessage(msg_40);
                            }
                            Elements div_2 = doc.getElementsByClass("90-2");
                            if (div_2.size() > 0) {
                                Message msg_2 = Message.obtain();
                                msg_2.what = 6;
                                msg_2.obj = div_2.get(0).text();
                                // Log.e("90-2", msg_2.obj+"");
                                handler.sendMessage(msg_2);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            handler.sendEmptyMessage(7);
                        }
                    }
                }.start();
            }
        });
        manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auto.setVisibility(GONE);
                manual.setVisibility(GONE);
                url.setVisibility(View.VISIBLE);
            }
        });
        builder.setView(v);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int para_time = 0;
                int para_interval = 0;
                int para_num = 0;
                long para_dif = 0;
                String dif = dif_time.getText().toString().trim();
                if (!TextUtils.isEmpty(dif))
                    para_dif = Long.parseLong(dif);
                switch (time.getCheckedRadioButtonId()) {
                    case R.id.time_10:
                        para_time = 10;
                        break;
                    case R.id.time_12:
                        para_time = 12;
                        break;
                    case R.id.time_14:
                        para_time = 14;
                        break;
                    case R.id.time_18:
                        para_time = 18;
                        break;
                    case R.id.time_20:
                        para_time = 20;
                        break;
                }
                switch (interval.getCheckedRadioButtonId()) {
                    case R.id.time_300:
                        para_interval = 300;
                        break;
                    case R.id.time_500:
                        para_interval = 500;
                        break;
                    case R.id.time_800:
                        para_interval = 800;
                        break;
                    case R.id.time_1s:
                        para_interval = 1000;
                        break;
                }
                switch (num.getCheckedRadioButtonId()) {
                    case R.id.time_1:
                        para_num = 1;
                        break;
                    case R.id.time_2:
                        para_num = 2;
                        break;
                    case R.id.time_3:
                        para_num = 3;
                        break;
                }
                if (!TextUtils.isEmpty(url.getText().toString().trim())) {
                    body_manual = url.getText().toString().trim();
                    para = new GetPara(para_time, para_interval, para_num, para_dif, body_manual, null, null);
                } else if (coupon_40.isChecked() && !TextUtils.isEmpty(body_40) && !coupon_2.isChecked())
                    para = new GetPara(para_time, para_interval, para_num, para_dif, null, body_40, null);
                else if (coupon_40.isChecked() && !TextUtils.isEmpty(body_40) && coupon_2.isChecked() && !TextUtils.isEmpty(body_2))
                    para = new GetPara(para_time, para_interval, para_num, para_dif, null, body_40, body_2);
                else if (coupon_2.isChecked() && !TextUtils.isEmpty(body_2) && !coupon_40.isChecked())
                    para = new GetPara(para_time, para_interval, para_num, para_dif, null, null, body_2);
                else if (!coupon_2.isChecked() && !coupon_40.isChecked())
                    handler.sendEmptyMessage(8);

                Intent intent = new Intent(MainActivity.this, GetCouponService.class);
                intent.putExtra("para", para);
                intent.putExtra("cookie_159", cookie_159);
                intent.putExtra("cookie_135", cookie_135);
                intent.putExtra("cookie_134", cookie_134);
                Toasty.normal(MainActivity.this, "后台抢券服务正在开启ing！").show();
                startService(intent);
                view.setEnabled(false);
                //getCoupon(cookie_159, 159, para);
                //getCoupon(cookie_135, 135, para);
                // getCoupon(cookie_134, 134, para);
            }
        });
        builder.create().show();
    }
}
