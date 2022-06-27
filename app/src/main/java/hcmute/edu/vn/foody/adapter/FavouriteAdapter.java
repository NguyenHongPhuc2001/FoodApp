package hcmute.edu.vn.foody.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import hcmute.edu.vn.foody.FavouriteActivity;
import hcmute.edu.vn.foody.R;
import hcmute.edu.vn.foody.database.Database;
import hcmute.edu.vn.foody.model.Favourite;
import hcmute.edu.vn.foody.model.Food;

public class FavouriteAdapter extends BaseAdapter {
    private FavouriteActivity context;
    private int layout;
    private List<Favourite> lsFavourite;
    private List<Food> lsFood;


    SQLiteDatabase mydatabase;
    Database database;

    public FavouriteAdapter(FavouriteActivity context, int layout, List<Favourite> lsFavourite, List<Food> lsFood) {
        this.context = context;
        this.layout = layout;
        this.lsFavourite = lsFavourite;
        this.lsFood = lsFood;
    }


    @Override
    public int getCount() {
        return lsFavourite.size();
    }

    @Override
    public Object getItem(int position) {
        return lsFood.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder {
        ImageView imgFood;
        TextView txtFoodName, txtFoodPrice, txtRate;
        Button btnDelete, btnAddtocart;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.txtFoodName = (TextView) convertView.findViewById(R.id.textview_favourite_tenmon);
            holder.txtFoodPrice = (TextView) convertView.findViewById(R.id.textview_favourite_giamon);
            holder.imgFood = (ImageView) convertView.findViewById(R.id.img_favourite_single_food);
            holder.txtRate = (TextView) convertView.findViewById(R.id.textview_Favourite_StarNB);
            holder.btnDelete = (Button) convertView.findViewById(R.id.button_Favourite_Delete);
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

        Favourite fa = lsFavourite.get(position);
        Food food = lsFood.get(position);
        double rate = 0;
//        String foodname = "";
//        double foodprice = 0;
//        double rate = 0;
//        byte[] img = null;
//

        Cursor c_rate = mydatabase.rawQuery("select * from Rating where FoodID = ?", new String[]{String.valueOf(food.getFoodID())});
        if (c_rate.moveToFirst() == true) {
            rate = c_rate.getDouble(1);
        }

        byte[] img = food.getFoodImg();
        Bitmap bmp = BitmapFactory.decodeByteArray(img, 0, img.length);

        holder.txtFoodName.setText(food.getFoodName());
        holder.txtFoodPrice.setText(String.valueOf((int)food.getPrice()) + " VNĐ");
        holder.txtRate.setText(String.valueOf(rate));
        holder.imgFood.setImageBitmap(bmp);


        //bắt sự kiện xóa
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = fa.getFavouriteID();
                if (id != 0) {
                context.DialogXoaFood(id);
                } else {
                    Toast.makeText(context, "Không thể xóa món ăn !", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return convertView;
    }
}
