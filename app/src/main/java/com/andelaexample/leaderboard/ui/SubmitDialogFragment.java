package com.andelaexample.leaderboard.ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.andelaexample.leaderboard.R;
import com.andelaexample.leaderboard.data.api.GadsApi;
import com.andelaexample.leaderboard.data.api.GadsService;
import com.andelaexample.leaderboard.data.interfaces.OnSubmitClicked;
import com.andelaexample.leaderboard.data.models.ProjectSubmission;
import com.andelaexample.leaderboard.databinding.LayoutDialogBinding;
import com.andelaexample.leaderboard.utils.ConstantsUtils;
import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmitDialogFragment extends DialogFragment implements OnSubmitClicked {

    private LayoutDialogBinding binding;

    private ProjectSubmission projectSubmission;
    private OnSubmissionSuccessListener onSubmissionSuccessListener;
    private String success;
    private String failure;

    public SubmitDialogFragment(ProjectSubmission projectSubmission, OnSubmissionSuccessListener onSubmissionSuccessListener) {
        this.projectSubmission = projectSubmission;
        this.onSubmissionSuccessListener = onSubmissionSuccessListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        success = "Submission Successful";
        failure = "Submission not Successful";
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.layout_dialog, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        binding.setOnSubmit(this);

        binding.ivDialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmissionSuccessListener.onSubmissionSuccess(false);
                dismiss();
            }
        });

        showConfirmation();


    }

    private void showLoading() {
        binding.confirmLayout.setVisibility(View.GONE);
        binding.loadingLayout.setVisibility(View.VISIBLE);
        binding.feedbackLayout.setVisibility(View.GONE);
    }

    private void showConfirmation() {
        binding.confirmLayout.setVisibility(View.VISIBLE);
        binding.loadingLayout.setVisibility(View.GONE);
        binding.feedbackLayout.setVisibility(View.GONE);
    }

    private void showFeedback() {
        binding.confirmLayout.setVisibility(View.GONE);
        binding.loadingLayout.setVisibility(View.GONE);
        binding.feedbackLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSubmitProject() {

        showLoading();

        GadsService service = GadsApi.createService(GadsService.class, ConstantsUtils.PROJECT_SUBMISSION_URL);

        Call<Void> call = service.submitForm(projectSubmission.getFirstName(), projectSubmission.getSecondName(), projectSubmission.getEmailAddress(), projectSubmission.getProjectLink());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                showFeedback();

                if (response.isSuccessful()) {

                    showFeedbackMessage(R.drawable.ic_baseline_check_circle_24, success);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            onSubmissionSuccessListener.onSubmissionSuccess(true);
                            dismiss();
                        }
                    }, 1500);

                    return;
                }

                showFeedbackMessage(R.drawable.ic_baseline_warning_24, failure);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onSubmissionSuccessListener.onSubmissionSuccess(false);
                        dismiss();
                    }
                }, 1500);

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

                showFeedbackMessage(R.drawable.ic_baseline_warning_24, failure);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onSubmissionSuccessListener.onSubmissionSuccess(false);
                        dismiss();
                    }
                }, 1500);

            }
        });
    }

    private void showFeedbackMessage(int p, String failure) {
        Glide.with(binding.feedbackLayout.getContext())
                .load(p)
                .into(binding.ivFeedbackImage);

        binding.tvDialogFeedback.setText(failure);
    }

    public interface OnSubmissionSuccessListener {
        void onSubmissionSuccess(boolean isSuccessful);
    }
}
