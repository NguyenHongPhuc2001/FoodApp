package hcmute.edu.vn.foody.adapter;

import android.content.ContentValues;
import android.content.Context;
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

import java.util.List;

import hcmute.edu.vn.foody.R;
import hcmute.edu.vn.foody.database.Database;
import hcmute.edu.vn.foody.model.Cart;
import hcmute.edu.vn.foody.model.Food;
import hcmute.edu.vn.foody.model.OrderDetail;

public class ViewCartAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Food> foodList;
    private List<OrderDetail> orderDetailList;
    private List<Cart> cartList;
    SQLiteDatabase mydatabase;
    Database database;

    public ViewCartAdapter(Context context, int layout, List<Food> foodList, List<OrderDetail> orderDetailList, List<Cart> cartList) {
        this.context = context;
        this.layout = layout;
        this.foodList = foodList;
        this.orderDetailList =orderDetailList;
        this.cartList = cartList;
    }


    @Override
    public int getCount() {
        return orderDetailList.size();
    }

    @Override
    public Object getItem(int position) {
        return foodList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return foodList.get(position).getFoodID();
    }

    public  int getCartId(int position){
        return cartList.get(position).getCartID();
    }
    public class ViewHolder{
        TextView txtName, txtPrice, txtAmount, totalPrice;
        Button btnAdd, btnMinus;
        ImageView imgCartMonAnFood;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        try {
            database = new Database(context, "food.sqlite", null, 1);
            mydatabase = database.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.txtName = (TextView) convertView.findViewById(R.id.cart_MonAn2_Food);
            holder.txtPrice = (TextView) convertView.findViewById(R.id.textview_cart_FoodPrice);
            holder.txtAmount = (TextView) convertView.findViewById(R.id.textview_cart_Soluong);
            holder.imgCartMonAnFood = (ImageView) convertView.findViewById(R.id.img_cartMonAn_Food);
            holder.totalPrice = (TextView) convertView.findViewById(R.id.textview_cart_FoodPrice2);
            holder.btnMinus = (Button) convertView.findViewById(R.id.button_cart_Minus);
            holder.btnAdd = (Button) convertView.findViewById(R.id.button_cart_Add);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }


        Food food = foodList.get(position);
        OrderDetail orderDetail = orderDetailList.get(position);


        holder.txtName.setText(food.getFoodName());
        String price = String.valueOf((int)food.getPrice());
        holder.txtPrice.setText(price + " VNĐ");
        holder.txtAmount.setText(String.valueOf(orderDetail.getAmount()));
        String totalPrice = String.valueOf((int)orderDetail.getTotalAmount());
        holder.totalPrice.setText(totalPrice + " VNĐ");

        byte[] bytearr = food.getFoodImg();
        Bitmap bmp = BitmapFactory.decodeByteArray(bytearr,0,bytearr.length);
        holder.imgCartMonAnFood.setImageBitmap(bmp);
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(holder.txtAmount.getText().toString());
                quantity++;
                holder.txtAmount.setText(String.valueOf(quantity));
                ContentValues cv = new ContentValues();
                int cartID = getCartId(position);
                int foodID = (int) getItemId(position);
                cv.put("CartID", cartID);
                cv.put("Quantity", quantity);
                cv.put("FoodID", foodID);
                mydatabase.update("Cart",cv, "CartID=?", new String[]{String.valueOf(cartID)});
//                mydatabase.execSQL("update Cart set Quantity = ? where CartID = ?", new String[]{String.valueOf(quantity),String.valueOf(cartID)});
            }
        });
        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(holder.txtAmount.getText().toString());
                quantity--;
                if (quantity <= 0){
                    int cartID = getCartId(position);
                    mydatabase.delete("Cart","CartID=?",new String[]{String.valueOf(cartID)});
                }else{
                    holder.txtAmount.setText(String.valueOf(quantity));
                    ContentValues cv = new ContentValues();
                    int cartID = getCartId(position);
                    int foodID = (int) getItemId(position);
                    cv.put("CartID", cartID);
                    cv.put("Quantity", quantity);
                    cv.put("FoodID", foodID);
                    mydatabase.update("Cart",cv, "CartID=?", new String[]{String.valueOf(cartID)});
                }

            }
        });
        return convertView;
    }
}
