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
    private boolean isAfterOperation = false;

    private boolean isNumber(String text) {
        return text.matches("[0-9]+");
    }

    private boolean isOperator(String text) {
        return text.matches("[+\\-*/'sqr']");
    }

    private Button button12; // 声明按钮变量
    private Button button15; // 声明按钮变量
    private Button button19; // 声明按钮变量
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

            cancel();
        });
        button12 = findViewById(R.id.button12);
        button12.setOnClickListener(v -> {

//
            Log.d(TAG, "onclick =");
            evaluate(textViewDisplay.getText().toString());

        });
        button19 = findViewById(R.id.button19);
        button19.setOnClickListener(v -> {
            deleteAll();
        });
        // 为所有按钮设置点击事件
        int[] buttonIds = new int[]{
                R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5,
                R.id.button6, R.id.button7, R.id.button8, R.id.button9, R.id.button10, R.id.button11
                , R.id.button13, R.id.button14, R.id.button16, R.id.button17, R.id.button18, R.id.button31, R.id.button32,
        };

        for (int buttonId : buttonIds) {
            final Button button = findViewById(buttonId);
            button.setOnClickListener(v -> updateDisplay(button.getText().toString()));
        }
    }
    public static Object returnIntegerIfPossible(double value) {
        // 检查浮点数是否为整数（即没有小数部分）
        if (value == (int) value) {
            // 如果是整数，则返回其整数部分（通过类型转换将其转换为int类型）
            return (int) value;
        } else {
            // 如果不是整数，则返回一个提示信息
           return value;
        }}


    private void updateDisplay(String text) {
        if (isAfterOperation && isNumber(text)) {
            // 如果是数字，并且是在运算之后，清空显示屏并显示这个数字
            textViewDisplay.setText(text);
            isAfterOperation = false; // 重置标志位
        } else
            if ( isAfterOperation&& isOperator(text)) {
            // 如果是运算符，直接追加到显示屏
            String currentText = textViewDisplay.getText().toString();
            if (!currentText.isEmpty() && !currentText.endsWith(" ")) {
                textViewDisplay.append(text);
//
            }
                isAfterOperation = false;

            } else {
            textViewDisplay.append(text);

        }
    }

    private void cancel() {
        String currentText = textViewDisplay.getText().toString();
        Log.d(TAG, "Current text: " + currentText);
        Log.d(TAG, "cancel: ");
        if (!currentText.isEmpty()) {
            String newText = currentText.substring(0, currentText.length() - 1);  // 删除最后一个字符  
            textViewDisplay.setText(newText);
        }
    }

    private void deleteAll() {
        textViewDisplay.setText("");
    }

    public void evaluate(String expression) {
        try {
            String currentText = textViewDisplay.getText().toString();
            Log.d(TAG, "calculate " + currentText);
            // 使用 exp4j 解析和计算表达式
            try {
                Expression exp = new ExpressionBuilder(expression).build();


                double result = exp.evaluate(); // 计算结果
                textViewDisplay.setText(String.valueOf(returnIntegerIfPossible(result)));
                calculations.add(0, new Calculation(expression,String.valueOf( returnIntegerIfPossible(result))));
            } catch (ArithmeticException e) {

                textViewDisplay.setText("ERROR");
                calculations.add(0, new Calculation(expression, "ERROR"));
            }


            isAfterOperation = true; // 设置标志位为true
            // 保存历史记录
            // 添加到历史记录中，倒序插入


            adapter.notifyDataSetChanged(); // 更新适配器
        } catch (Exception e) {
            System.out.println("表达式无效: " + e.getMessage());

        }
    }


}
