package hcmute.edu.vn.foody.adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hcmute.edu.vn.foody.R;
import hcmute.edu.vn.foody.database.Database;
import hcmute.edu.vn.foody.model.Advertisement;
import hcmute.edu.vn.foody.model.Restaurant;

public class RestaurantAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Restaurant> resList;

    Database database;
    SQLiteDatabase mydatabase;

    public RestaurantAdapter(Context context, int layout, List<Restaurant> resList) {
        this.context = context;
        this.layout = layout;
        this.resList = resList;
    }

    @Override
    public int getCount() {
        return resList.size();
    }

    @Override
    public Object getItem(int position) {
        return resList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder{
        ImageView imgRes;
        TextView txtResname, txtResStarNB, txtResAddress;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            holder.imgRes = convertView.findViewById(R.id.img_Home_Res);
            holder.txtResname = convertView.findViewById(R.id.textview_Home_ResName);
            holder.txtResAddress = convertView.findViewById(R.id.textview_Home_ResAddress);
            holder.txtResStarNB = convertView.findViewById(R.id.textview_Home_ResStarNB);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Restaurant res = resList.get(position);

        try {
            database = new Database(context, "food.sqlite", null, 1);
            mydatabase = database.getReadableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        double rate = 0;
        Cursor c  = mydatabase.rawQuery("select * from RatingRes where ResID =? ",
                new String[]{String.valueOf(res.getResID())});
        if(c.moveToNext()==true){
            rate = c.getDouble(1);
        }

        byte[] bmp = res.getResImg();
        Bitmap img = BitmapFactory.decodeByteArray(bmp,0,bmp.length);
        holder.imgRes.setImageBitmap(img);
        holder.txtResname.setText(res.getResName());
        holder.txtResAddress.setText(res.getResAddress());
        holder.txtResStarNB.setText(String.valueOf(rate));

        return convertView;
    }
}
