package hcmute.edu.vn.foody.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import hcmute.edu.vn.foody.LoginActivity;
import hcmute.edu.vn.foody.R;
import hcmute.edu.vn.foody.model.Account;

public class AccountAdapter extends BaseAdapter {


    private LoginActivity context;
    private int layout;
    private List<Account> accountList;

    public AccountAdapter(LoginActivity context, int layout, List<Account> accountList) {
        this.context = context;
        this.layout = layout;
        this.accountList = accountList;
    }

    @Override
    public int getCount() {
        return accountList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    private class ViewHolder {
        EditText edtUsername, edtPassword;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.edtUsername = (EditText) convertView.findViewById(R.id.edtUserName);
            holder.edtPassword = (EditText) convertView.findViewById(R.id.edtPassword);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Account account = accountList.get(position);

        return convertView;
    }
}
