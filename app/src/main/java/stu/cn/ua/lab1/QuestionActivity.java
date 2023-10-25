package stu.cn.ua.lab1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import stu.cn.ua.lab1.databinding.ActivityQuestionBinding;
import stu.cn.ua.lab1.databinding.ActivitySettingsBinding;
import stu.cn.ua.lab1.model.UserInfo;

public class QuestionActivity extends AppCompatActivity {

    public static final String EXTRA_QUESTION = "QUESTION";
    private ActivityQuestionBinding binding;
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        userInfo = getIntent().getParcelableExtra(EXTRA_QUESTION);

        binding.askButton.setOnClickListener(v -> {
            generateAnswer();
        });

        binding.toMenuButton.setOnClickListener(v -> {
            finish();
        });
    }

    private void generateAnswer() {
        String answer = binding.editQuestion.getText().toString().trim();
        binding.answerTextView.setText("");
        if (answer.isEmpty())
            binding.editQuestion.setError(getString(R.string.enter_question_error));
        else {
            long time = System.currentTimeMillis() / 1000 / 60 / 60 / 24;
            String allData = answer + userInfo + time + "";
            int hash = allData.hashCode();
            
        }
    }
}