package hcmute.edu.vn.foody.adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.NoCopySpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hcmute.edu.vn.foody.HomeActivity;
import hcmute.edu.vn.foody.R;
import hcmute.edu.vn.foody.database.Database;
import hcmute.edu.vn.foody.model.Food;
import hcmute.edu.vn.foody.model.Rating;

public class ViewallAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Food> foodList;
    private List<Rating> rateList;

    SQLiteDatabase mydatabase;
    Database database;

    public ViewallAdapter(Context context, int layout, List<Food> foodList, List<Rating> rateList) {
        this.context = context;
        this.layout = layout;
        this.foodList = foodList;
        this.rateList =rateList;
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
        TextView txtName, txtPrice, txtRating;
        ImageView imgFood;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.txtName = (TextView) convertView.findViewById(R.id.textview_MonAn2_Food);
            holder.txtPrice = (TextView) convertView.findViewById(R.id.textview_Monan2_FoodPrice);
            holder.txtRating = (TextView) convertView.findViewById(R.id.textview_MonAn2_StarNB);
            holder.imgFood = (ImageView) convertView.findViewById(R.id.img_MonAn2_Food);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }


        Food food = foodList.get(position);
        Rating rate = rateList.get(position);


        holder.txtName.setText(food.getFoodName());
        String price = String.valueOf((int)food.getPrice());
        holder.txtPrice.setText(price + " VNĐ");
        holder.txtRating.setText(String.valueOf(rate.getNumStar()));

        byte[] bytearr = food.getFoodImg();
        Bitmap bmp = BitmapFactory.decodeByteArray(bytearr,0,bytearr.length);
        holder.imgFood.setImageBitmap(bmp);

        return convertView;
    }
}
