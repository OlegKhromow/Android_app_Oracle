package stu.cn.ua.lab1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import stu.cn.ua.lab1.databinding.ActivityQuestionBinding;
import stu.cn.ua.lab1.databinding.ActivitySettingsBinding;
import stu.cn.ua.lab1.model.UserInfo;

public class QuestionActivity extends AppCompatActivity {

    public static final String EXTRA_QUESTION = "QUESTION";
    private final String KEY_ANSWER = "ANSWER";
    private ActivityQuestionBinding binding;
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuestionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        userInfo = getIntent().getParcelableExtra(EXTRA_QUESTION);

        if (savedInstanceState != null)
            binding.answerTextView.setText(savedInstanceState.getCharSequence(KEY_ANSWER));

        binding.askButton.setOnClickListener(v -> {
            generateAnswer();
        });

        binding.toMenuButton.setOnClickListener(v -> {
            finish();
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence(KEY_ANSWER, binding.answerTextView.getText());
    }

    private void generateAnswer() {
        String answer = binding.editQuestion.getText().toString().trim();
        binding.answerTextView.setText("");
        if (answer.isEmpty()) {
            binding.editQuestion.setError(getString(R.string.enter_question_error));
            binding.editQuestion.setText("");
        }
        else {
            long time = System.currentTimeMillis() / 1000 / 60 / 60 / 24;
            String allData = answer + userInfo + time + "";
            int hash = allData.hashCode();
            String[] answers = getResources().getStringArray(R.array.answers_array);
            binding.answerTextView.setText(answers[Math.abs(hash) % answers.length]);
        }
    }
}