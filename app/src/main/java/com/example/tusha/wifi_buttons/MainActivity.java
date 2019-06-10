package com.example.tusha.wifi_buttons;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import org.json.JSONObject;
import java.io.DataOutputStream;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import android.util.Log;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    String textrelay1 = "relay1";
    String textrelay2 = "relay2";
    String textrelay3 = "relay3";
    String textrelay4 = "relay4";

    public int b1=0;
    public int b2=0;
    public int b3=0;
    public int b4=0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.Buttonlight).setOnClickListener(this);
        findViewById(R.id.Buttonfan).setOnClickListener(this);
        findViewById(R.id.Buttontv).setOnClickListener(this);
        findViewById(R.id.Buttonac).setOnClickListener(this);
        findViewById(R.id.Buttonallon).setOnClickListener(this);
        findViewById(R.id.Buttonalloff).setOnClickListener(this);
    }



    public void sendPost(final String relayName, final int btnval) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    String urlAdress="https://io.adafruit.com/api/v2/jigisha/feeds/" + relayName + "/data";

                    Log.e ("EkURL",urlAdress);

                    URL url = new URL(urlAdress);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty( "X-AIO-Key", "2d7def4636914ff7a73cfe896cc5e611");

                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("value",btnval);
                    jsonParam.put("created_at",Calendar.getInstance().getTime().toString());
                    jsonParam.put("lat", "");
                    jsonParam.put("lon", "");
                    jsonParam.put("ele", "");
                    jsonParam.put("epoch", 0);




                    Log.e("JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    os.writeBytes(jsonParam.toString());

                    os.flush();
                    os.close();

                    Log.e("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.e("MSG" , conn.getResponseMessage());

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("Elk Err",e.getMessage().toString());
                }
            }
        });

        thread.start();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.Buttonlight:

                if (b1 == 0) {
                    b1 = 1;
                }else {
                    b1 = 0;

                }
                sendPost(textrelay1, b1);
            break;
            case R.id.Buttonfan:
                if (b2 == 0) {
                    b2 = 1;
                }else {
                    b2 = 0;

                }
                sendPost(textrelay2, b2);
                break;
            case R.id.Buttontv:
                if (b3 == 0) {
                    b3 = 1;
                }else {
                    b3 = 0;

                }
                sendPost(textrelay3, b3);
                break;
            case R.id.Buttonac:
                if (b4 == 0) {
                    b4 = 1;
                }else {
                    b4 = 0;

                }
                sendPost(textrelay4, b4);
                break;
            case R.id.Buttonallon:
                b1 =1;
                b2 =1;
                b3 =1;
                b4 =1;
                sendPost(textrelay1, b1);
                sendPost(textrelay2, b2);
                sendPost(textrelay3, b3);
                sendPost(textrelay4, b4);
                break;
            case R.id.Buttonalloff:
                b1 =0;
                b2 =0;
                b3 =0;
                b4 =0;
                sendPost(textrelay1, b1);
                sendPost(textrelay2, b2);
                sendPost(textrelay3, b3);
                sendPost(textrelay4, b4);
                break;

        }
    }
}
