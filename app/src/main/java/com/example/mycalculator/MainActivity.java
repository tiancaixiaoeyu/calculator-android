package com.example.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;


public class MainActivity extends AppCompatActivity {
    private Button button12; // 声明按钮变量
    private Button button15; // 声明按钮变量
    private TextView textViewDisplay;
    private static final String TAG = "MainActivity";
    private List<Calculation> calculations; // 存储历史记录
    private CalculationAdapter adapter; // 适配器
    private ListView listViewHistory; // 历史记录 ListView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        calculations = new ArrayList<>(); // 初始化列表
        listViewHistory = findViewById(R.id.listViewHistory); // 获取 ListView
        adapter = new CalculationAdapter(this, calculations); // 初始化适配器
        listViewHistory.setAdapter(adapter); // 设置适配器
        // 获取显示屏的引用
        textViewDisplay = findViewById(R.id.textViewDisplay);

        button15 = findViewById(R.id.button15);
        button15.setOnClickListener(v -> {
//            // 处理等号按钮的点击事件
//            Toast.makeText(this, "等号按钮被点击了！", Toast.LENGTH_SHORT).show();
//            // 这里可以添加计算逻辑
            cancel();
        });
        button12 = findViewById(R.id.button12);
        button12.setOnClickListener(v -> {
//            // 处理等号按钮的点击事件
//            Toast.makeText(this, "等号按钮被点击了！", Toast.LENGTH_SHORT).show();
//
            Log.d(TAG, "onclick =");
          evaluate(textViewDisplay.getText().toString());

        });
        // 为所有按钮设置点击事件
        int[] buttonIds = new int[] {
                R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5,
                R.id.button6, R.id.button7, R.id.button8, R.id.button9, R.id.button10, R.id.button11
                , R.id.button13, R.id.button14, R.id.button16, R.id.button17, R.id.button18, R.id.button19
        };

        for (int buttonId : buttonIds) {
            final Button button = findViewById(buttonId);
            button.setOnClickListener(v -> updateDisplay(button.getText().toString()));
        }
    }


    private void updateDisplay(String text) {
        // 更新显示屏的文本
        //Log.d("Calculator", "Appending text: " + text);
        textViewDisplay.append(ToDBC(text)); // 使用append来追加文本
    }
    private void cancel(){
        String currentText = textViewDisplay.getText().toString();
        Log.d(TAG, "Current text: " + currentText);
        Log.d(TAG, "cancel: ");
        if (!currentText.isEmpty()) {
            String newText = currentText.substring(0, currentText.length() - 1);  // 删除最后一个字符  
            textViewDisplay.setText(newText);
        }
    }

    public void evaluate(String expression) {
        try {
            String currentText = textViewDisplay.getText().toString();
            Log.d(TAG, "calculate "+currentText);
            // 使用 exp4j 解析和计算表达式
            Expression exp = new ExpressionBuilder(expression).build();
            double result = exp.evaluate(); // 计算结果
            textViewDisplay.setText(String.valueOf(result));

            // 保存历史记录
            // 添加到历史记录中，倒序插入

            calculations.add(0,new Calculation(expression, String.valueOf(result)));
            adapter.notifyDataSetChanged(); // 更新适配器
        } catch (Exception e) {
            System.out.println("表达式无效: " + e.getMessage());

        }
    }
    public static String ToDBC(String str) {

        char[] c = str.toCharArray();

        for (int i = 0; i < c.length; i++) {

            if (c[i] == 12288) {

                c[i] = (char) 32;

                continue;

            }

            if (c[i] > 65280 && c[i] < 65375)

                c[i] = (char) (c[i] - 65248);

        }

        return new String(c);

    }
}
