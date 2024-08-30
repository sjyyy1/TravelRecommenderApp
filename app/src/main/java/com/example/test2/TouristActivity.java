package com.example.test2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;


public class TouristActivity extends AppCompatActivity {

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tourist);
        LinearLayout resultContainer = findViewById(R.id.resultContainer);
        // 添加旅游人格信息
        Intent intent = getIntent();
        if (intent != null) {
            String travelPlan = intent.getStringExtra("travelPlan");
            // 将旅行计划按天分割
            String[] daysPlans = travelPlan.split("第");
            if (daysPlans.length > 0) {
                // 提取旅游人格信息（假设它位于第一个计划之前）
                String personalityInfo = daysPlans[0].trim();

                if (!personalityInfo.isEmpty()) {
                    // 创建布局
                    LinearLayout personalityLayout = new LinearLayout(this);
                    personalityLayout.setOrientation(LinearLayout.VERTICAL);
                    personalityLayout.setGravity(Gravity.CENTER);
                    personalityLayout.setPadding(32, 16, 32, 16);

                    // 创建“您的旅游人格为”TextView
                    TextView introTextView1 = new TextView(this);
                    introTextView1.setText("\n");
                    introTextView1.setTextSize(20);
                    introTextView1.setTextColor(getResources().getColor(R.color.primaryTextColor));
                    introTextView1.setGravity(Gravity.CENTER);
                    introTextView1.setPadding(0, 16, 0, 8);

                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    int screenWidth = displayMetrics.widthPixels;
                    // 创建旅游人格类型TextView
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            screenWidth,
                            screenWidth
                    );
                    // 从 assets 文件夹中加载字体文件
                    Context context = this;
                    Typeface customTypeface = ResourcesCompat.getFont(context, R.font.black);
                    TextView personalityContentTextView = new TextView(this);
                    personalityContentTextView.setTypeface(customTypeface);
                    personalityContentTextView.setTextSize(35);
                    personalityContentTextView.setTextColor(getResources().getColor(R.color.text));
                    personalityContentTextView.setGravity(Gravity.CENTER);
                    personalityContentTextView.setPadding(0, 8, 0, 0);
                    personalityContentTextView.setLayoutParams(params);
                    String textContent = "您的旅游人格为\n" + personalityInfo.substring(5); // 假设从 personalityInfo 中获取文本内容
                    personalityContentTextView.setText(textContent);
                    Drawable backgroundDrawable;
                    // 根据不同的文本内容选择不同的背景资源
                    if (textContent.contains("全面探索型")) {
                        backgroundDrawable = getResources().getDrawable(R.drawable.q); // 假设背景资源为 background_blue.xml
                    } else if (textContent.contains("休闲放松型")) {
                        backgroundDrawable = getResources().getDrawable(R.drawable.x); // 假设背景资源为 background_green.xml
                    } else if (textContent.contains("体验多元型")) {
                        backgroundDrawable = getResources().getDrawable(R.drawable.t); // 假设背景资源为 background_green.xml
                    } else {
                        backgroundDrawable = getResources().getDrawable(R.drawable.o); // 默认背景资源，假设为 background_red.xml
                    }
                    backgroundDrawable.setAlpha(128);
                    personalityContentTextView.setBackground(backgroundDrawable); // 设置背景资源

                    // 创建“您的旅游人格为”TextView
                    TextView introTextView2 = new TextView(this);
                    introTextView2.setText("\n");
                    introTextView2.setTextSize(20);
                    introTextView2.setTextColor(getResources().getColor(R.color.primaryTextColor));
                    introTextView2.setGravity(Gravity.CENTER);
                    introTextView2.setPadding(0, 16, 0, 8);

                    // 添加TextView到布局中
                    personalityLayout.addView(introTextView1);
                    personalityLayout.addView(personalityContentTextView);
                    personalityLayout.addView(introTextView2);

                    // 将布局添加到resultContainer中
                    resultContainer.addView(personalityLayout);
                }
                LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                layoutParam.setMargins(0, 100, 0, 0);
                Button button = new Button(this);
                button.setText("点击此按钮\n查收为您量身定制的旅行方案吧");
                button.setTextSize(22);
                button.setBackgroundColor(-1);
                button.setTextColor(getResources().getColor(R.color.primaryTextColor));
                button.setTypeface(null, Typeface.BOLD);
                button.setPadding(16, 16, 16, 16);
                button.setAlpha(0.8F);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                layoutParams.gravity = Gravity.CENTER; // 设置按钮居中显示
                button.setLayoutParams(layoutParams);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(TouristActivity.this, ResultActivity.class);
                        intent.putExtra("travelPlan", travelPlan);
                        startActivity(intent);
                    }
                });
                resultContainer.addView(button);
            }
        }
    }
}