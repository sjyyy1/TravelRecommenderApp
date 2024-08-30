package com.example.test2;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Typeface;
import android.view.Gravity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ResultActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        String travelPlan = getIntent().getStringExtra("travelPlan");

        LinearLayout resultContainer = findViewById(R.id.resultContainer);

        // 添加显示图片按钮
        Button showImageButton = new Button(this);
        showImageButton.setText("点击查看区域图像");

        // 创建布局参数并设置为居中
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.gravity = Gravity.CENTER;

        // 应用布局参数
        showImageButton.setLayoutParams(params);

        // 设置按钮点击事件
        showImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageDialog();
            }
        });

        // 将按钮添加到 resultContainer 的顶部
        resultContainer.addView(showImageButton, 0);

        // 将旅行计划按天分割
        String[] daysPlans = travelPlan.split("第");

        int i = -1;
        for (String dayPlan : daysPlans) {
            if(++i==0) continue;
            //i=i+1;
            if (!dayPlan.trim().isEmpty()) {
                CardView cardView = new CardView(this);
                cardView.setCardElevation(8);
                cardView.setRadius(16);
                cardView.setContentPadding(16, 16, 16, 16);
                cardView.setUseCompatPadding(true);
                cardView.setCardBackgroundColor(getResources().getColor(R.color.cardBackgroundColor));

                LinearLayout cardLayout = new LinearLayout(this);
                cardLayout.setOrientation(LinearLayout.VERTICAL);

                int drawableResId = getDrawableResIdForIndex(i);
                // 定义固定的大小
                int fixedWidth = getResources().getDisplayMetrics().widthPixels - 10; // 手机屏宽度减5
                int fixedHeight = fixedWidth + 5; // 高度略长
                // 设置自带的边框图片和透明度
                Drawable borderDrawable = getScaledDrawable(drawableResId, fixedWidth, fixedHeight);

                //Drawable borderDrawable = getScaledDrawable(R.drawable.a2, fixedWidth, fixedHeight);
                cardView.setBackground(borderDrawable);


                // 添加分割线
                ImageView divider = new ImageView(this);
                divider.setBackgroundColor(getResources().getColor(R.color.dividerColor));
                LinearLayout.LayoutParams dividerParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        2 // 分割线的高度
                );
                dividerParams.setMargins(0, 16, 0, 16);

                TextView titleTextView = new TextView(this);
                titleTextView.setText("第" + dayPlan.substring(0, 1) + "天");
                titleTextView.setTextSize(20);
                titleTextView.setGravity(Gravity.CENTER);
                titleTextView.setTextColor(getResources().getColor(R.color.primaryTextColor));
                titleTextView.setPadding(0, 16, 0, 16);

                TextView contentTextView = new TextView(this);
                contentTextView.setText(dayPlan.substring(2));
                contentTextView.setTextSize(16);
                contentTextView.setTextColor(getResources().getColor(R.color.primaryTextColor));
                contentTextView.setLineSpacing(1.5f, 1.5f);

                cardLayout.addView(titleTextView);
                cardLayout.addView(contentTextView);
                cardView.addView(cardLayout);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                layoutParams.setMargins(32, 16, 32, 16);
                resultContainer.addView(cardView, layoutParams);
            }
        }
    }


    private void showImageDialog() {
        // 创建 AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 创建一个 ImageView 并设置图片资源
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.map); // 替换为你的图片资源 ID

        // 设置对话框的自定义视图
        builder.setView(imageView);

        // 设置对话框的关闭按钮
        builder.setNegativeButton("关闭图像", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // 创建并显示对话框
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // 辅助方法：根据索引获取图片资源 ID
    @SuppressLint("DiscouragedApi")
    private int getDrawableResIdForIndex(int index) {
        // 构造资源 ID，假设你的图片命名为1、2、3...
        String drawableName = "a" + index; // 将索引转换为字符串
        return getResources().getIdentifier(drawableName, "drawable", getPackageName());
    }

    private Drawable getScaledDrawable(int drawableResId, int targetWidth, int targetHeight) {
        // 1. 获取原始图片
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), drawableResId);
        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();

        // 2. 计算缩放比例
        float scaleWidth = ((float) targetWidth) / originalWidth;
        float scaleHeight = ((float) targetHeight) / originalHeight;
        float scale = Math.min(scaleWidth, scaleHeight);  // 按最小比例缩放以保持图片比例

        // 3. 计算缩放后的宽高
        int scaledWidth = Math.round(originalWidth * scale);
        int scaledHeight = Math.round(originalHeight * scale);

        // 4. 创建缩放后的图片
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);

        // 5. 创建 Drawable
        BitmapDrawable drawable = new BitmapDrawable(getResources(), scaledBitmap);
        drawable.invalidateSelf();  // 强制更新 Drawable 的显示

        return drawable;
    }
}