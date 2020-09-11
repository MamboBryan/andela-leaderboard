package com.andelaexample.leaderboard.data.api;

import com.andelaexample.leaderboard.data.models.Learner;
import com.andelaexample.leaderboard.data.models.SkillLearner;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface GadsService {

    /**
     * Gets all learners in a list
     */
    @Headers("Content-Type:application/json")
    @GET("api/hours")
    Call<List<Learner>> getLearners(
    );

    /**
     * Gets all skill iq learners
     */
    @Headers("Content-Type:application/json")
    @GET("api/skilliq")
    Call<List<SkillLearner>> getSkillIqLearners(
    );

}
