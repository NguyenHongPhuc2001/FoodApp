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
import java.util.Scanner;

import hcmute.edu.vn.foody.R;
import hcmute.edu.vn.foody.database.Database;
import hcmute.edu.vn.foody.model.Food;
import hcmute.edu.vn.foody.model.FoodRes;

public class RestaurantFoodAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Food> foodList;

    Database database;
    SQLiteDatabase mydatabase;

    public RestaurantFoodAdapter(Context context, int layout, List<Food> foodList) {
        this.context = context;
        this.layout = layout;
        this.foodList = foodList;
    }

    @Override
    public int getCount() {
        return foodList.size();
    }

    @Override
    public Object getItem(int position) {
        return foodList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder{
        ImageView imgFood;
        TextView txtFoodname, txtFoodpirce, txtFoodrate;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.txtFoodname = (TextView) convertView.findViewById(R.id.textview_MonAn2_Food);
            holder.txtFoodrate = (TextView) convertView.findViewById(R.id.textview_MonAn2_StarNB);
            holder.txtFoodpirce = (TextView) convertView.findViewById(R.id.textview_Monan2_FoodPrice) ;
            holder.imgFood = (ImageView) convertView.findViewById(R.id.img_MonAn2_Food);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        try {
            database = new Database(context, "food.sqlite", null, 1);
            mydatabase = database.getReadableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Food food = foodList.get(position);
        int foodid = food.getFoodID();

        double rate = 0;

        Cursor c= mydatabase.rawQuery("select * from Rating where FoodID = ?",
                new String[]{String.valueOf(foodid)});
        if(c.moveToFirst()==true){
            rate = c.getDouble(1);
        }


        holder.txtFoodname.setText(food.getFoodName());
        holder.txtFoodrate.setText(String.valueOf(rate));
        holder.txtFoodpirce.setText(String.valueOf((int)food.getPrice())+ " VNĐ");
        byte[] img = food.getFoodImg();
        Bitmap bmp = BitmapFactory.decodeByteArray(img,0, img.length);
        holder.imgFood.setImageBitmap(bmp);

        return convertView;
    }
}
