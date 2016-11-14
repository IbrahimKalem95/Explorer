package com.example.ibrah.ogoovol2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView txtDetailTitle;
    ImageView detailImage;
    TextView txtDetailDescription;
    TextView txtDetailKategori;
    String collectionName = null;
    String kind = null ;
    private Bundle extras = null;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailImage = (ImageView)findViewById(R.id.detailImage);
        txtDetailTitle = (TextView)findViewById(R.id.txtDetailTitle);
        txtDetailKategori = (TextView)findViewById(R.id.txtDetailKategori);
        txtDetailDescription = (TextView)findViewById(R.id.txtDetailDescription);

       extras = getIntent().getExtras();
        collectionName = extras.getString("collectionName");
        kind = extras.getString("kind");


        txtDetailTitle.setText(collectionName);
        txtDetailKategori.setText(kind);


    }
}
