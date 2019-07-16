package com.bytedance.android.lesson.restapi.restapi;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telecom.Call;
import android.view.View;
import android.widget.TextView;

import com.bytedance.android.lesson.restapi.restapi.bean.Joke;
import com.bytedance.android.lesson.restapi.restapi.bean.OSList;
import com.bytedance.android.lesson.restapi.restapi.utils.NetworkUtils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    public static String RAW =
            "{\"os\":[{\"name\":\"Pie\",\"code\":28}," +
                    "{\"name\":\"Oreo\",\"code\":27}]}";
    public TextView mTv;
    private View mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTv = findViewById(R.id.tv);
        mBtn = findViewById(R.id.btn);
//        mTv.setText(parseFirstNameWithJSON()); // json test
//        mTv.setText(parseFirstNameWithGson()); // json test
        initListeners();
    }

    private void initListeners() {
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                requestData(v);
            }
        });
    }

    private static String parseFirstNameWithGson() {
        OSList list = new Gson()
                .fromJson(RAW, OSList.class);
        return list.getOs()[0].getName();
    }

    private static String parseFirstNameWithJSON() {
        String result = null;
        try {
            JSONObject root = new JSONObject(RAW);
            JSONArray os = root.optJSONArray("os");
            result = os.optJSONObject(0).
                    optString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void requestData(View view) {
        // HttpURLConnection

//        String s = NetworkUtils.getResponseWithHttpURLConnection("https://api.icndb.com/jokes/random");
//        mTv.setText(s);

//        new NetwworkAsyncTask(mTv).execute("https://api.icndb.com/jokes/random");

        // Retrofit
//        Joke j = NetworkUtils.getResponseWithRetrofit();
//        mTv.setText(j.getValue().getJoke());
    }
    class NetwworkAsyncTask extends AsyncTask<String , Void , String>{

        private final WeakReference<TextView> mTextViewWeakReference;

        NetwworkAsyncTask(TextView textView) {
            mTextViewWeakReference = new WeakReference<>(textView);
        }



        @Override
        protected String doInBackground(String... strings) {
            String s = NetworkUtils.getResponseWithHttpURLConnection(strings[0]);
            return s;
        }


        @Override
        protected void onPostExecute(String s) {
            TextView textView = mTextViewWeakReference.get();
            if(textView != null){
                textView.setText(s);
            }
            mTv.setText(s);
        }
    }
}