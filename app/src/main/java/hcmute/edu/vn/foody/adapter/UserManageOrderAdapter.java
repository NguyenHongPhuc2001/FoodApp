package hcmute.edu.vn.foody.adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hcmute.edu.vn.foody.R;
import hcmute.edu.vn.foody.database.Database;
import hcmute.edu.vn.foody.model.Food;
import hcmute.edu.vn.foody.model.OrderDetail;
import hcmute.edu.vn.foody.model.Orders;

public class UserManageOrderAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Orders> orderList;
    private List<OrderDetail> orderDetailList;
    private List<Food> foodList;
    private int Accid;

    public UserManageOrderAdapter(Context context, int layout, List<Orders> orderList, List<OrderDetail> orderDetailList, List<Food> foodList, int accid) {
        this.context = context;
        this.layout = layout;
        this.orderList = orderList;
        this.orderDetailList = orderDetailList;
        this.foodList = foodList;
        Accid = accid;
    }

    Database database;
    SQLiteDatabase mydatabase;


    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        ImageView imgFood;
        TextView txtFoodName, txtFoodPrice, txtFoodAmount, txtStatus, txtDate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.txtFoodName = (TextView) convertView.findViewById(R.id.textview_User_Foodname);
            holder.txtFoodPrice = (TextView) convertView.findViewById(R.id.textview_User_Price);
            holder.txtDate = (TextView) convertView.findViewById(R.id.textview_Order_Date);
            holder.txtStatus = (TextView) convertView.findViewById(R.id.textview_User_Status);
            holder.txtFoodAmount = (TextView) convertView.findViewById(R.id.textview_User_Soluong);
            holder.imgFood = (ImageView) convertView.findViewById(R.id.img_User_Monan);
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


        Orders orders = orderList.get(position);
        OrderDetail orderDetail = orderDetailList.get(position);
        Food food = foodList.get(position);

        if (orders.getCusID() == Accid) {
            if(orders.getStatus()==0) {
                holder.txtStatus.setText("Đang xử lý");
                holder.txtStatus.setTextColor(Color.parseColor("#ffd60a"));
                holder.txtFoodName.setText(food.getFoodName());
                holder.txtFoodPrice.setText(String.valueOf((int) food.getPrice()) + " VNĐ");
                holder.txtFoodAmount.setText("Số lượng: " + String.valueOf(orderDetail.getAmount()));
                holder.txtDate.setText(orders.getDateOrder());
                Bitmap bmp = BitmapFactory.decodeByteArray(food.getFoodImg(), 0, food.getFoodImg().length);
                holder.imgFood.setImageBitmap(bmp);
            }else{
                holder.txtStatus.setText("Đơn hàng đang đến");
                holder.txtStatus.setTextColor(Color.parseColor("#8ac926"));
                holder.txtFoodName.setText(food.getFoodName());
                holder.txtFoodPrice.setText(String.valueOf((int) food.getPrice()) + " VNĐ");
                holder.txtFoodAmount.setText("Số lượng: " + String.valueOf(orderDetail.getAmount()));
                holder.txtDate.setText(orders.getDateOrder());
                Bitmap bmp = BitmapFactory.decodeByteArray(food.getFoodImg(), 0, food.getFoodImg().length);
                holder.imgFood.setImageBitmap(bmp);
            }

        }

        return convertView;
    }
}
