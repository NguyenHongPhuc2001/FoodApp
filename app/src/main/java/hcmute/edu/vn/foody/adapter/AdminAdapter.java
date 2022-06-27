package hcmute.edu.vn.foody.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import hcmute.edu.vn.foody.AdminActivity;
import hcmute.edu.vn.foody.R;
import hcmute.edu.vn.foody.model.Customer;
import hcmute.edu.vn.foody.model.Food;
import hcmute.edu.vn.foody.model.OrderDetail;
import hcmute.edu.vn.foody.model.Orders;

public class AdminAdapter extends BaseAdapter {
    private AdminActivity context;
    private int layout;
    private List<Orders> lsOrder;
    private List<Customer> lsCustomer;

    public AdminAdapter(AdminActivity context, int layout, List<Orders> lsOrder, List<Customer> lsCustomer) {
        this.context = context;
        this.layout = layout;
        this.lsOrder = lsOrder;
        this.lsCustomer = lsCustomer;
    }


    @Override
    public int getCount() {
        return lsOrder.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    public class ViewHolder {
        ImageView imgCus;
        TextView txtCusname, txtPrice, txtDate, txtStatus, txtOrderID;
        Button btnAccept, btnDenies;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.txtCusname = (TextView) convertView.findViewById(R.id.textview_Admin_TenCus);
            holder.imgCus = (ImageView) convertView.findViewById(R.id.img_Admin_CusImg);
            holder.txtPrice = (TextView) convertView.findViewById(R.id.textview_Admin_TotalPrice);
            holder.txtDate = (TextView) convertView.findViewById(R.id.textview_Admin_Date);
            holder.txtStatus = (TextView)convertView.findViewById(R.id.textview_Admin_Status);
            holder.txtOrderID = (TextView)convertView.findViewById(R.id.textview_Admin_OrderID);
            holder.btnAccept = (Button)convertView.findViewById(R.id.btn_Admin_Accept);
            holder.btnDenies = (Button)convertView.findViewById(R.id.btn_Admin_Deny);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Orders order = lsOrder.get(position);
        Customer customer  = lsCustomer.get(position);


        if(order.getStatus()==0){
            byte[] img = customer.getCusImg();
            Bitmap bmp = BitmapFactory.decodeByteArray(img,0,img.length);
            holder.txtOrderID.setText(String.valueOf(order.getOrderID()));
            holder.imgCus.setImageBitmap(bmp);
            holder.txtCusname.setText(customer.getCusName());
            holder.txtPrice.setText(String.valueOf((int)order.getTotalOrder()) + " VNĐ");
            holder.txtDate.setText(String.valueOf(order.getDateOrder()));
            holder.txtStatus.setText(" Chưa duyệt ");
            holder.txtStatus.setTextColor(Color.parseColor("#5c6b73"));
        }else{
            byte[] img = customer.getCusImg();
            Bitmap bmp = BitmapFactory.decodeByteArray(img,0,img.length);
            holder.txtOrderID.setText(String.valueOf(order.getOrderID()));
            holder.imgCus.setImageBitmap(bmp);
            holder.txtCusname.setText(customer.getCusName());
            holder.txtPrice.setText(String.valueOf((int)order.getTotalOrder()) + " VNĐ");
            holder.txtDate.setText(String.valueOf(order.getDateOrder()));
            holder.txtStatus.setText(" Đã duyệt ");
            holder.txtStatus.setTextColor(Color.parseColor("#8ac926"));
        }


        //bắt sự kiện accept;
        holder.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = order.getOrderID();
                if (id != 0) {
                    context.DialogAccept(id);
                } else {
                    Toast.makeText(context, "Error !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return convertView;
    }
}
