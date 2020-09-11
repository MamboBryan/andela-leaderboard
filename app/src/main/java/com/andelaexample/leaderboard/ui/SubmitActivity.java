package com.andelaexample.leaderboard.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.andelaexample.leaderboard.R;
import com.andelaexample.leaderboard.data.interfaces.OnSubmitClicked;
import com.andelaexample.leaderboard.data.models.ProjectSubmission;
import com.andelaexample.leaderboard.databinding.ActivitySubmitBinding;

public class SubmitActivity extends AppCompatActivity implements OnSubmitClicked, SubmitDialogFragment.OnSubmissionSuccessListener {

    private ActivitySubmitBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_submit);

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.setOnSubmitListener(this);
    }

    @Override
    public void onSubmitProject() {

        boolean isValidCredentials = true;

        if (TextUtils.isEmpty(binding.edtFirstName.getText().toString().trim())) {
            binding.textInputLayoutFirstName.setError("Invalid First Name");
            isValidCredentials = false;
        } else {
            binding.textInputLayoutFirstName.setError(null);
        }


        if (TextUtils.isEmpty(binding.edtSecondName.getText().toString().trim())) {
            binding.textInputLayoutSecondName.setError("Invalid Second Name");
            isValidCredentials = false;

        } else {
            binding.textInputLayoutSecondName.setError(null);
        }

        if (TextUtils.isEmpty(binding.edtEmail.getText().toString().trim()) ||
                !Patterns.EMAIL_ADDRESS.matcher(binding.edtEmail.getText()).matches()) {
            binding.textInputLayoutEmail.setError("Invalid Email address");
            isValidCredentials = false;
        } else {
            binding.textInputLayoutEmail.setError(null);
        }

        if (TextUtils.isEmpty(binding.edtLink.getText().toString().trim()) ||
                !Patterns.WEB_URL.matcher(binding.edtLink.getText()).matches()) {
            binding.textInputLayoutLink.setError("Invalid Link");
            isValidCredentials = false;

        } else {
            binding.textInputLayoutLink.setError(null);
        }

        if (isValidCredentials) {
            ProjectSubmission projectSubmission = new ProjectSubmission();

            projectSubmission.setFirstName(binding.edtFirstName.getText().toString());
            projectSubmission.setSecondName(binding.edtSecondName.getText().toString());
            projectSubmission.setEmailAddress(binding.edtEmail.getText().toString());
            projectSubmission.setProjectLink(binding.edtLink.getText().toString());

            DialogFragment dialogFragment = new SubmitDialogFragment(projectSubmission, this);
            dialogFragment.show(getSupportFragmentManager(), "Submit Dialog");
        }

    }

    @Override
    public void onSubmissionSuccess(boolean isSuccessful) {

        if (isSuccessful) {
            onBackPressed();
        }

    }
}