package com.andelaexample.leaderboard.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.andelaexample.leaderboard.R;
import com.andelaexample.leaderboard.adapter.LearnerAdapter;
import com.andelaexample.leaderboard.adapter.SkillerAdapter;
import com.andelaexample.leaderboard.data.api.GadsApi;
import com.andelaexample.leaderboard.data.api.GadsService;
import com.andelaexample.leaderboard.data.interfaces.OnRetryListener;
import com.andelaexample.leaderboard.data.models.Learner;
import com.andelaexample.leaderboard.data.models.SkillLearner;
import com.andelaexample.leaderboard.databinding.FragmentTemplateBinding;
import com.andelaexample.leaderboard.utils.ConstantsUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TemplateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TemplateFragment extends Fragment implements OnRetryListener {

    private static final String LEADER_CODE = "leader_code";

    private int leaderCode;
    private FragmentTemplateBinding binding;
    private GadsService service;

    private boolean isNetworkError = false;
    private boolean isFailedRequest = false;

    public TemplateFragment() {
        // Required empty public constructor
    }

    public static TemplateFragment newInstance(int leaderCode) {
        TemplateFragment fragment = new TemplateFragment();
        Bundle args = new Bundle();
        args.putInt(LEADER_CODE, leaderCode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            leaderCode = getArguments().getInt(LEADER_CODE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_template, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.includeLoading.setVisibility(View.VISIBLE);

        showLoading();

        binding.includeFeedback.setOnRetryListener(this);
    }

    private void showLoading() {
        binding.setIsLoadingVisible(true);
        binding.setIsDataVisible(false);
        binding.setIsFeedBackVisible(false);
    }

    private void showFeedback() {
        binding.setIsLoadingVisible(false);
        binding.setIsDataVisible(false);
        binding.setIsFeedBackVisible(true);
    }

    private void showData() {
        binding.setIsLoadingVisible(false);
        binding.setIsDataVisible(true);
        binding.setIsFeedBackVisible(false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        createRetrofitInstance();

        if (leaderCode == ConstantsUtils.CODE_LEARNER) {
            getLearnerList();
        } else if (leaderCode == ConstantsUtils.CODE_SKILL_LEADER) {
            getSkillIqList();
        }

    }

    private void getSkillIqList() {

        Call<List<SkillLearner>> call = service.getSkillIqLearners();

        call.enqueue(new Callback<List<SkillLearner>>() {
            @Override
            public void onResponse(Call<List<SkillLearner>> call, Response<List<SkillLearner>> response) {

                if (response.isSuccessful() && response.body() != null) {

                    showData();
                    initSkillLeaderAdapter(response.body());
                    return;
                }

                isFailedRequest = true;
                showFeedback();

            }

            @Override
            public void onFailure(Call<List<SkillLearner>> call, Throwable t) {

                showFeedback();
                isFailedRequest = true;

            }
        });

    }

    private void initLearnerRecycler(List<Learner> learnerList) {

        LearnerAdapter adapter = new LearnerAdapter(learnerList);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        binding.includeRecycler.rvMain.setLayoutManager(layoutManager);
        binding.includeRecycler.rvMain.setAdapter(adapter);

    }

    private void getLearnerList() {

        Call<List<Learner>> call = service.getLearners();
        call.enqueue(new Callback<List<Learner>>() {
            @Override
            public void onResponse(Call<List<Learner>> call, Response<List<Learner>> response) {
                if (response.isSuccessful() && response.body() != null) {

                    showData();
                    initLearnerRecycler(response.body());
                    return;
                }

                isFailedRequest = true;
                showFeedback();
            }

            @Override
            public void onFailure(Call<List<Learner>> call, Throwable t) {
                isFailedRequest = true;
                showFeedback();
            }
        });

    }

    private void initSkillLeaderAdapter(List<SkillLearner> skillLearners) {
        SkillerAdapter adapter = new SkillerAdapter(skillLearners);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        binding.includeRecycler.rvMain.setLayoutManager(layoutManager);
        binding.includeRecycler.rvMain.setAdapter(adapter);
    }

    private void createRetrofitInstance() {
        //create retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantsUtils.GADS_BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = GadsApi.createService(GadsService.class, ConstantsUtils.GADS_BASE_API_URL);

    }

    @Override
    public void onRetryClicked() {
        if (leaderCode == ConstantsUtils.CODE_LEARNER) {

            getLearnerList();
            showLoading();

        } else if (leaderCode == ConstantsUtils.CODE_SKILL_LEADER) {

            getSkillIqList();
            showLoading();

        } else {
            Toast.makeText(getContext(), "Cannot get the tab", Toast.LENGTH_SHORT).show();
        }
    }
}