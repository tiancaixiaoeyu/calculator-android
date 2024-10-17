package com.example.mycalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CalculationAdapter extends ArrayAdapter<Calculation> {
    public CalculationAdapter(Context context, List<Calculation> calculations) {
        super(context, 0, calculations);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Calculation calculation = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        }

        TextView text1 = convertView.findViewById(android.R.id.text1);
        TextView text2 = convertView.findViewById(android.R.id.text2);

        text1.setText(calculation.getExpression());
        text2.setText(calculation.getResult());

        return convertView;
    }
}
