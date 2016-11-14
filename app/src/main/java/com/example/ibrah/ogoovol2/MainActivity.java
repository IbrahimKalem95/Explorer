package com.example.ibrah.ogoovol2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;



public class MainActivity extends AppCompatActivity
{
    Button btnAra;
    EditText editText;
    ListView listData;
    ArrayList<HashMap<String, String>> items;
    TextView txtDataTitle;
    ImageView dataImage;
    TextView txtDataDescription;
    TextView txtDataKatagoriy;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAra = (Button)findViewById(R.id.btnAra);
        editText = (EditText)findViewById(R.id.edtText);
        listData = (ListView)findViewById(R.id.listData);
        items = new ArrayList<>();

    }
    private URL URLOlustur(URL url) throws MalformedURLException
    {

    url = new URL("https://itunes.apple.com/search?term=" + editText.getText());

    return url;
    }

    public void GetData(View view)
    {
       new FetchData().execute();

        dataImage = (ImageView)view.findViewById(R.id.dataImage);
        txtDataTitle = (TextView)view.findViewById(R.id.txtDataTitle);
        txtDataKatagoriy = (TextView)view.findViewById(R.id.txtDataKategoriy);
        txtDataDescription = (TextView)view.findViewById(R.id.txtDataDescription);

    }
    private class FetchData extends AsyncTask<Void, Void, String>
    {
        URL url ;

        @Override
        protected String doInBackground(Void... params)
        {
                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;
                String forecastJsonStr = null;
            try {
                    urlConnection = (HttpURLConnection)  URLOlustur(url).openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    InputStream inputStream = urlConnection.getInputStream();

                    if (inputStream == null)
                    {
                        // Nothing to do.
                        return null;
                    }
                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null)
                    {

                        sb.append(line + "\n");

                    }
                    if (sb.length() == 0)
                    {
                         return null;
                    }
                    forecastJsonStr = sb.toString();
                    return forecastJsonStr;
                }

                catch (IOException e)
                {
                    Log.e("PlaceholderFragment", "Error ", e);

                    return null;
                } finally{
                    if (urlConnection != null)
                    {
                        urlConnection.disconnect();
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (final IOException e)
                        {
                            Log.e("PlaceholderFragment", "Error closing stream", e);
                        }
                    }
                }
        }
            @Override
            protected void onPostExecute(String s)
            {
                super.onPostExecute(s);
                try {
                    ParseAndDisplay(s);
                    editText.setText("");

                } catch (JSONException e)
                {
                    e.printStackTrace();
                }

            }
    }
    public void ParseAndDisplay(String s) throws JSONException
    {
         String collectionName = null;
         String kind = null;
        String image = null;
        HashMap<String, String> contact = null;
        JSONObject res;

        JSONObject mainObject = new JSONObject(s);
       JSONArray results = mainObject.getJSONArray("results");
        items.clear();
       for(int i = 0; i < results.length(); i++)
       {   SimpleAdapter adapter = null;
           res = results.getJSONObject(i);
           collectionName = res.getString("collectionName");
           kind = res.getString("kind");
           image = res.getString("artworkUrl30");

           contact = new HashMap<>();
           contact.put("collectionName",collectionName);
           contact.put("kind",kind);
           contact.put("artworkUrl30",image);

            items.add(contact);
            adapter = new SimpleAdapter(
                   MainActivity.this, items,
                   R.layout.satir_layout, new String[]{"collectionName",
                   "kind","artworkUrl30"}, new int[]{R.id.txtDataTitle,
                   R.id.txtDataKategoriy,R.id.dataImage });

           listData.setAdapter(adapter);

       }

        //noinspection ConstantConditions
        final String finalCollectionName = collectionName.trim();
        //noinspection ConstantConditions
        final String finalKind = kind.trim();

        listData.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("collectionName", finalCollectionName);
                bundle.putString("kind", finalKind);

                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(MainActivity.this,DetailActivity.class);
                startActivity(intent);
            }
        });


    }
}







