package com.example.networktest;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView responseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sendRequest = (Button)findViewById(R.id.send_request);
        responseText = (TextView)findViewById(R.id.response_text);
        sendRequest.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.send_request){
            sendRequestWithHttpURLConnection();
            Toast.makeText(this,"点击了button！",Toast.LENGTH_SHORT).show();
        }
    }

    private void sendRequestWithHttpURLConnection(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e("sendRequesn","进入额");
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try{
                    URL url = new URL("https://www.so.com/s?q=apache%E6%9C%8D%E5%8A%A1%E5%99%A8%E4%B8%8B%E8%BD%BDwindows+Win+10&src=srp&fr=hao_360so_b&psid=daf75538449eea32683882816d175b0ahttps://www.so.com/s?q=apache%E6%9C%8D%E5%8A%A1%E5%99%A8%E4%B8%8B%E8%BD%BDwindows+Win+10&src=srp&fr=hao_360so_b&psid=daf75538449eea32683882816d175b0a");
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in));
                    final StringBuilder response = new StringBuilder();
                    String line;
                    Log.e("urlqqq",response.toString());
                    while((line = reader.readLine()) != null){
                        response.append(line);
                        System.out.print("111");
                        Log.e("url",response.toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this,"读取到的："+response,Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    showResponse(response.toString());
                }catch(Exception e){
                    e.printStackTrace();
                }finally{
                    if (reader != null){
                        try{
                            reader.close();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                    if(connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
               Toast.makeText(MainActivity.this,response,Toast.LENGTH_SHORT).show();
                responseText.setText(response);
            }
        });
    }
}
