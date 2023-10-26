package stu.cn.ua.lab1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;

import java.util.Calendar;

import stu.cn.ua.lab1.databinding.ActivityMainBinding;
import stu.cn.ua.lab1.model.UserInfo;

/**
 * Main activity with menu
 *
 * @author  Oleg Khromov
 */

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private final int RQ_SETTINGS = 1;
    private final String KEY_SETTINGS = "SETTINGS";

    private ActivityMainBinding binding;
    private UserInfo user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        if(savedInstanceState != null)
            user = savedInstanceState.getParcelable(KEY_SETTINGS);
        Log.d(TAG, "Settings: " + user);

        binding.askButton.setOnClickListener(v -> {
            if(user == null)
                showAlertDialog();
            else {
                Intent intent = new Intent(this, QuestionActivity.class);
                intent.putExtra(QuestionActivity.EXTRA_QUESTION, user);
                startActivity(intent);
            }
        });

        binding.settingsButton.setOnClickListener(v -> {
            openSettings();
        });

        binding.exitButton.setOnClickListener(v -> {
            finish();
        });
    }

    /**
     * Start SettingsActivity for enter/edit information about user
     */
    private void openSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra(SettingsActivity.EXTRA_SETTINGS, user);
        startActivityForResult(intent, RQ_SETTINGS);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_SETTINGS, user);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RQ_SETTINGS && resultCode == RESULT_OK){
            user = data.getParcelableExtra(SettingsActivity.EXTRA_SETTINGS);
            Log.d(TAG, "New settings: " + user);
        }
    }

    /**
     * Show alert dialog with an offer to enter user's data
     */
    private void showAlertDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.alert_dialog_title)
                .setMessage(R.string.alert_dialog_message)
                .setPositiveButton(R.string.alert_dialog_yes, (dialog, which) -> {
                    openSettings();
                })
                .setNegativeButton(R.string.alert_dialog_no, (dialog, which) -> {
                    dialog.cancel();
                })
                .setCancelable(false)
                .create();
        alertDialog.show();
    }
}