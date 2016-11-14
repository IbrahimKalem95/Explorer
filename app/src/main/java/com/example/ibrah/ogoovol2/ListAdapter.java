package com.example.ibrah.ogoovol2;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ibrah on 5.11.2016.
 */

public class ListAdapter extends BaseAdapter{

    private List<MainActivity> list;
    LayoutInflater layoutInflater;
    Context context;
    ArrayList<String> items = new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;
    private String message;

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public ListAdapter(Context context, List<MainActivity> list)
    {
        this.context = context;
        // Layout Inflater tanımlanıyor...
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = list;
    }

    @Override
    public int getCount()
    {
        return list.size();
    }

    @Override
    public Object getItem(int position)
    {
        return list.get(position);

    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Ogeler Diğer aktivitilerden Cağırılıyor

        View satirView = layoutInflater.inflate(R.layout.satir_layout,null);


        MainActivity main = new MainActivity();
        main.GetData(satirView);

        return satirView;
    }

}
