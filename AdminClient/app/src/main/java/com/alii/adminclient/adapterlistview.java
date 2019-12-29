package com.alii.adminclient;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class adapterlistview extends BaseAdapter {

    private Context context;
    private ArrayList<Mobile> mobiles;

    public adapterlistview(Context context, ArrayList<Mobile> mobiles) {
        this.context = context;
        this.mobiles = mobiles;
    }

    @Override
    public int getCount() {
        return mobiles.size();
    }

    @Override
    public Object getItem(int position) {
        return mobiles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = (View) inflater.inflate(
                    R.layout.row_f_test, null);
        }

        TextView name = (TextView) convertView.findViewById(R.id.shoot_name);
        TextView summary = (TextView) convertView.findViewById(R.id.shoot_number);
        TextView one =convertView.findViewById(R.id.shoot_code);
        TextView price = convertView.findViewById(R.id.shoot_price);
        TextView unite = convertView.findViewById(R.id.shoot_unite);
        TextView totall = convertView.findViewById(R.id.shoot_toal);
        TextView tstsl = convertView.findViewById(R.id.shoot_tslsl);


        name.setText(mobiles.get(position).getNames()[position]);
        if(name.length() > 8){
            if (name.getText().toString().length() > 15){
                name.setTextSize(6);
            }
            else if (name.getText().toString().length() > 20){
                name.setTextSize( 4);
            } else if (name.getText().toString().length() > 10){
                name.setTextSize( 7);
            }
        }
        summary.setText(mobiles.get(position).getCounts()[position]);

        one.setText(mobiles.get(position).getOnes()[position]);
        if(one.length() > 8){
            if (one.getText().toString().length() > 15){
                one.setTextSize(6);
            }
            else if (one.getText().toString().length() > 20){
                one.setTextSize( 4);
            } else if (one.getText().toString().length() > 10){
                one.setTextSize( 7);
            }
        }
        price.setText("$" + mobiles.get(position).getPrices()[position]);
        if(price.length() > 8){
            if (price.getText().toString().length() > 15){
                price.setTextSize(6);
            }
            else if (price.getText().toString().length() > 20){
                price.setTextSize( 4);
            } else if (price.getText().toString().length() > 10){
                price.setTextSize( 7);
            }
        }
        unite.setText(mobiles.get(position).getUnits()[position]);
        totall.setText("$" + mobiles.get(position).getPtotal()[position]);
        if(totall.length() > 8){
            if (totall.getText().toString().length() > 15){
                totall.setTextSize(6);
            }
            else if (totall.getText().toString().length() > 20){
                totall.setTextSize( 4);
            } else if (totall.getText().toString().length() > 10){
                totall.setTextSize( 7);
            }
        }
        tstsl.setText(String.valueOf(position +1));



        return convertView;
    }
}
