package com.example.ratan_000.imageupload;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by ratan_000 on 4/25/2015.
 */
public class ImageAdapter extends BaseAdapter{
    private Context mContext;
    private ArrayList<Bitmap> sample;
    String ipAddress = "http://52.24.17.228:3000/";

    public ImageAdapter(Context c,ArrayList<Bitmap> sample) {
        mContext = c;
        this.sample= sample;
    }

    public int getCount() {
        return sample.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    @Override
    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            Log.e("hi","************creating new view");
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            Log.e("hi","************applying convert view to image view");
            imageView = (ImageView) convertView;
        }

        Log.e("In ImageAdapter",sample.get(position)+" here");
        imageView.setImageBitmap(sample.get(position));
        return imageView;
    }

    // references to our images

}
