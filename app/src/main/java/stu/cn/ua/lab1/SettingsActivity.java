package stu.cn.ua.lab1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import stu.cn.ua.lab1.databinding.ActivitySettingsBinding;
import stu.cn.ua.lab1.model.UserInfo;

public class SettingsActivity extends AppCompatActivity {

    public static final String EXTRA_SETTINGS = "SETTINGS";
    public static final String KEY_USERINFO = "USERINFO";
    private static final String TAG = SettingsActivity.class.getSimpleName();

    private ActivitySettingsBinding binding;

    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        if (savedInstanceState == null){
            userInfo = getIntent().getParcelableExtra(EXTRA_SETTINGS);
            if (userInfo == null){
                userInfo = UserInfo.EmptyUserInfo();
            }
        }else {
            userInfo = savedInstanceState.getParcelable(KEY_USERINFO);
        }
        initEnterFields();
        Log.d(EXTRA_SETTINGS, userInfo.toString());

        binding.birthDateEntry.setOnClickListener(v -> {
            setBirthDateFromDataPicker();
        });

        binding.sexRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            userInfo.setSex(radioButton.getText().toString());
            binding.femaleRadioButton.setError(null);
        });

        binding.saveButton.setOnClickListener(v -> {
            saveSettings();
        });
    }

    private void saveSettings() {
        boolean isOk = true;
        try {
            userInfo.setName(binding.nameEdit.getText().toString());
        } catch (IllegalArgumentException e) {
            binding.nameEdit.setText("");
            binding.nameEdit.setError(getString(R.string.enter_name_error));
            isOk = false;
        }
        try {
            userInfo.setSurname(binding.surnameEdit.getText().toString());
        } catch (IllegalArgumentException e) {
            binding.surnameEdit.setText("");
            binding.surnameEdit.setError(getString(R.string.enter_surname_error));
            isOk = false;
        }
        if(binding.sexRadioGroup.getCheckedRadioButtonId() == -1) {
            binding.femaleRadioButton.setError(getString(R.string.choose_sex_error));
            isOk = false;
        }
        if (isOk){
            Log.d(TAG, "New settings: " + userInfo);
            Intent intent = new Intent();
            intent.putExtra(EXTRA_SETTINGS, userInfo);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private void initEnterFields() {
        binding.nameEdit.setText(userInfo.getName());
        binding.surnameEdit.setText(userInfo.getSurname());
        binding.birthDateEntry.setText(userInfo.getBirthDateString());
        if(userInfo.getSex().length() !=0 )
            if (Objects.equals(userInfo.getSex(), getString(R.string.male))) {
                binding.maleRadioButton.setChecked(true);
            } else {
                binding.femaleRadioButton.setChecked(true);
            }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_USERINFO, userInfo);
    }

    private void setBirthDateFromDataPicker() {
        Calendar date = userInfo.getBirthDate();
        DatePickerDialog pickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    date.set(year, month, dayOfMonth);
                    binding.birthDateEntry.setText(userInfo.getBirthDateString());
                },
                date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
        pickerDialog.show();
    }

}
