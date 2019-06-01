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
import android.widget.EditText;
import android.widget.RadioGroup;

import es.dmoral.toasty.Toasty;

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
    String body = "{\"activityId\":\"3J6CSU6S6was4gqMYahCDA5SgTy\",\"from\":\"H5node\",\"scene\":\"1\",\"args\":\"key=D2812FF5595C93DEB07100B738649BD6A2F18F9AF4114E8F1E94BAD002A6E0D77AD8F35A7BAA041CD9C8942D17F97B24_babel,roleId=E7977DF45D147D178446B5686CD18744_babel\",\"platform\":\"3\",\"orgType\":\"2\",\"openId\":\"-1\",\"pageClickKey\":\"Babel_Coupon\",\"eid\":\"S65ETJ6BMMLH5P4CNVA4SDDBH5MAR53LCDPC2BVOOPZ7W2TMZ3EQ5OQZPZM7SD3SSQTVUEOZZTWZ47TCPKLLM3ZYDU\",\"fp\":\"ad693cbf9064b6fda865c1a817965657\",\"shshshfp\":\"923c5560c26f39d3a0afd4c56bcf88b6\",\"shshshfpa\":\"359743c3-1bd8-22a5-12af-2a7dfa5f9c9c-1533910101\",\"shshshfpb\":\"1232a27d447a44cc8a08c07566034d9013b36a680fe2d69c15b6d9c195\",\"childActivityUrl\":\"https%3A%2F%2Fpro.m.jd.com%2Fmall%2Factive%2F3J6CSU6S6was4gqMYahCDA5SgTy%2Findex.html%3Futm_source%3Dandroidapp%26ShareTm%3DCi1SmD4KOATAU%252BH%252BnHiSOOpFFMZcEs5SoLKsuiXoiCRn49JcbQ%252FkAOAHzQyuhygUyFvkn9ssu0F%252F1FVyQIqqQY1YksgmYyTcy1%252B12hZ5yn4VsscZPGBQ2XC7dlgzbaXCyX%252Bhxopr3U5EH1MvxpeU6bGdF3oMKuCbM599qS4yEb0%253D%26ad_od%3Dshare%26utm_medium%3Dappshare%26utm_campaign%3Dt_335139774%26utm_term%3DWxfriends%26from%3Dsinglemessage\",\"mitemAddrId\":\"\",\"geo\":{\"lng\":\"\",\"lat\":\"\"},\"addressId\":\"\",\"posLng\":\"\",\"posLat\":\"\",\"focus\":\"\",\"innerAnchor\":\"\"}";

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
                        sid_135 = getSid(cookie_135);
                        if (sp.edit().putString("cookie_135", cookie_135).commit()) {
                            b135.setText("135_Cookie(新获取)");
                            bGet.setEnabled(true);
                        }
                        break;
                    case 1:
                        cookie_159 = (String) msg.obj;
                        sid_159 = getSid(cookie_159);
                        if (sp.edit().putString("cookie_159", cookie_159).commit()) {
                            b159.setText("159_Cookie(新获取)");
                            bGet.setEnabled(true);
                        }
                        break;
                    case 2:
                        cookie_134 = (String) msg.obj;
                        sid_134 = getSid(cookie_134);
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
                Log.e("sid", sid);
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
        final EditText dif_time = v.findViewById(R.id.dif_time);
        final EditText url = v.findViewById(R.id.url);
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
                if (!TextUtils.isEmpty(url.getText().toString().trim()))
                    body = url.getText().toString().trim();
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
                para = new GetPara(para_time, para_interval, para_num, para_dif, body);
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
