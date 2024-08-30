package com.example.test2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<TravelQuestion> questions;
    private TravelPlanCalculator calculator;
    private int currentQuestionIndex = 0;

    private TextView questionText;
    private LinearLayout answersGroup;
    private Button nextButton;
    private Button previousButton;
    private TextView seekBarValue;
    private SeekBar seekBar;
    private LinearLayout seekBarContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionText = findViewById(R.id.questionText);
        answersGroup = findViewById(R.id.answersGroup);
        nextButton = findViewById(R.id.nextButton);
        previousButton = findViewById(R.id.previousButton);
        seekBarValue = findViewById(R.id.seekBarValue);
        seekBar = findViewById(R.id.seekBar);
        seekBarContainer = findViewById(R.id.seekBarContainer);

        questions = createQuestions();
        calculator = new TravelPlanCalculator(questions,this);

        displayQuestion(currentQuestionIndex);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentQuestionIndex == 0) {
                    int selectedDays = seekBar.getProgress() + 3; // 3 to 10 days
                    calculator.answerQuestion(currentQuestionIndex, selectedDays);
                } else if (currentQuestionIndex == 6) { // 第 7 个问题为多选
                    List<Integer> selectedAnswers = new ArrayList<>();
                    for (int i = 0; i < answersGroup.getChildCount(); i++) {
                        LinearLayout optionLayout = (LinearLayout) answersGroup.getChildAt(i);
                        CheckBox checkBox = optionLayout.findViewById(i + 1000); // 使用动态设置的 ID
                        if (checkBox.isChecked()) {
                            selectedAnswers.add(i);
                        }
                    }

                    if (selectedAnswers.isEmpty() && calculator.getAnswer(currentQuestionIndex) == null) {
                        Toast.makeText(MainActivity.this, "请至少选择一个选项", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (!selectedAnswers.isEmpty()) {
                        calculator.answerQuestion(currentQuestionIndex, selectedAnswers);
                    }
                } else {
                    RadioGroup radioGroup = (RadioGroup) answersGroup.getChildAt(0);
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    if (selectedId == -1 && calculator.getAnswer(currentQuestionIndex) == null) {
                        Toast.makeText(MainActivity.this, "请选择一个选项", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (selectedId != -1) {
                        RadioButton selectedButton = findViewById(selectedId);
                        int answerIndex = radioGroup.indexOfChild(selectedButton);
                        calculator.answerQuestion(currentQuestionIndex, answerIndex);
                    }
                }

                if (currentQuestionIndex < questions.size() - 1) {
                    currentQuestionIndex++;
                    displayQuestion(currentQuestionIndex);
                } else {
                    String travelPlan = calculator.getTravelPlan();
                    Intent intent = new Intent(MainActivity.this, TouristActivity.class);
                    intent.putExtra("travelPlan", travelPlan);
                    startActivity(intent);
                }
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentQuestionIndex > 0) {
                    currentQuestionIndex--;
                    displayQuestion(currentQuestionIndex);
                }
            }
        });
    }

    private List<TravelQuestion> createQuestions() {
        List<TravelQuestion> questions = new ArrayList<>();
        questions.add(new TravelQuestion("您的旅游天数：", Arrays.asList("3", "4", "5", "6", "7", "8", "9", "10")));
        questions.add(new TravelQuestion("您的旅游习惯是：", Arrays.asList("沉浸式旅游（注重旅行深度和质量）", "特种兵式旅游（注重旅行数量和广度）")));
        questions.add(new TravelQuestion("来北京后您主要想品尝：", Arrays.asList("老北京风味美食", "现代/多元风味")));
        questions.add(new TravelQuestion("您每顿饭的人均预算大概是多少：", Arrays.asList("50元以下", "50~200元", "200元以上")));
        questions.add(new TravelQuestion("住宿方面您更倾向于选择：", Arrays.asList("酒店", "民宿")));
        questions.add(new TravelQuestion("您能接受的住宿价格（元/晚）：", Arrays.asList("300元及以下", "300-800元", "800元及以上")));
        questions.add(new TravelQuestion("您游玩时更倾向于选择？（多选）", Arrays.asList("历史文化与学术探索类景区", "文化艺术与创意体验类景区", "自然生态与休闲放松类景区", "商业文化与风情体验类景区", "娱乐科普与教育体验类景区")));
        questions.add(new TravelQuestion("在购物方面，您的偏好倾向于：", Arrays.asList("有购物需求", "无购物需求")));
        return questions;
    }

    private int getImageResourceForOption(int questionIndex, int optionIndex) {
        // 根据 questionIndex 和 optionIndex 返回对应的图片资源 ID
        // 请根据实际情况返回正确的资源 ID
        if (questionIndex == 6) {
            switch (optionIndex) {
                case 0:
                    return R.drawable.img1;
                case 1:
                    return R.drawable.img2;
                case 2:
                    return R.drawable.img3;
                case 3:
                    return R.drawable.img4;
                case 4:
                    return R.drawable.img5;
                default:
                    return R.drawable.default_image; // 默认图片
            }
        }
        return R.drawable.default_image;
    }

    private void displayQuestion(int questionIndex) {
        TravelQuestion question = questions.get(questionIndex);
        questionText.setText(question.getQuestion());
        answersGroup.removeAllViews();

        if (questionIndex == 0) {
            seekBarContainer.setVisibility(View.VISIBLE);
            seekBarValue.setVisibility(View.VISIBLE);
            seekBar.setVisibility(View.VISIBLE);

            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    int days = progress + 3; // Adjusting for the range 3-10
                    seekBarValue.setText("选择天数: " + days);

                    // 存储选择的天数到 SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("TravelPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("selectedDays", days);
                    editor.apply();
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {}
            });
        } else {
            seekBarContainer.setVisibility(View.GONE);
            seekBarValue.setVisibility(View.GONE);
            seekBar.setVisibility(View.GONE);

            if (questionIndex == 6) {
                List<Integer> selectedAnswers = (List<Integer>) calculator.getAnswer(questionIndex);
                final List<Integer> currentSelectedAnswers = selectedAnswers != null ? selectedAnswers : new ArrayList<>();
                for (int i = 0; i < question.getAnswers().size(); i++) {
                    // 动态创建包含 CheckBox、TextView 和 ImageView 的布局
                    LinearLayout optionLayout = new LinearLayout(this);
                    optionLayout.setOrientation(LinearLayout.VERTICAL);
                    optionLayout.setPadding(16, 16, 16, 16);
                    optionLayout.setBackgroundResource(R.drawable.rounded_gray_background); // 设置浅灰色背景并调整透明度
                    LinearLayout.LayoutParams optionLayoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    optionLayoutParams.setMargins(0, 8, 0, 8); // 设置上下边距
                    optionLayout.setLayoutParams(optionLayoutParams);

                    LinearLayout horizontalLayout = new LinearLayout(this);
                    horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
                    horizontalLayout.setGravity(Gravity.START); // 水平居左

                    CheckBox checkBox = new CheckBox(this);
                    checkBox.setId(i + 1000); // 使用动态设置的 ID
                    if (currentSelectedAnswers.contains(i)) {
                        checkBox.setChecked(true);
                    }
                    horizontalLayout.addView(checkBox);

                    TextView textView = new TextView(this);
                    textView.setText(question.getAnswers().get(i));
                    horizontalLayout.addView(textView);

                    optionLayout.addView(horizontalLayout);

                    ImageView imageView = new ImageView(this);
                    LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 400);
                    imageParams.gravity = Gravity.START; // 水平居左
                    imageParams.setMargins(0, 16, 0, 16); // 设置上下边距
                    imageView.setLayoutParams(imageParams);
                    imageView.setImageResource(getImageResourceForOption(questionIndex, i));
                    optionLayout.addView(imageView);

                    final int index = i;
                    checkBox.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (checkBox.isChecked()) {
                                if (!currentSelectedAnswers.contains(index)) {
                                    currentSelectedAnswers.add(index);
                                }
                            } else {
                                currentSelectedAnswers.remove(Integer.valueOf(index));
                            }
                            calculator.answerQuestion(questionIndex, currentSelectedAnswers);
                        }
                    });

                    answersGroup.addView(optionLayout);
                }
            } else {
                RadioGroup radioGroup = new RadioGroup(this);
                radioGroup.setOrientation(RadioGroup.VERTICAL);
                Integer selectedAnswer = (Integer) calculator.getAnswer(questionIndex);
                for (int i = 0; i < question.getAnswers().size(); i++) {
                    final RadioButton radioButton = new RadioButton(this);
                    radioButton.setText(question.getAnswers().get(i));
                    radioButton.setId(View.generateViewId());

                    // 创建包含 RadioButton 的浅灰色背景布局
                    LinearLayout optionLayout = new LinearLayout(this);
                    optionLayout.setOrientation(LinearLayout.VERTICAL);
                    optionLayout.setPadding(16, 16, 16, 16);
                    optionLayout.setBackgroundResource(R.drawable.rounded_gray_background); // 设置浅灰色背景并调整透明度
                    LinearLayout.LayoutParams optionLayoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    optionLayoutParams.setMargins(0, 8, 0, 8); // 设置上下边距
                    optionLayout.setLayoutParams(optionLayoutParams);
                    optionLayout.addView(radioButton);

                    if (selectedAnswer != null && selectedAnswer == i) {
                        radioButton.setChecked(true);
                    }
                    final int index = i;
                    radioButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            radioGroup.clearCheck();
                            radioButton.setChecked(true);
                            calculator.answerQuestion(questionIndex, index);
                        }
                    });
                    radioGroup.addView(optionLayout);
                }
                answersGroup.addView(radioGroup);
            }
        }
    }
}
