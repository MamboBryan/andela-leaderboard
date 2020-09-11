package com.andelaexample.leaderboard.data.api;

import com.andelaexample.leaderboard.data.models.Learner;
import com.andelaexample.leaderboard.data.models.SkillLearner;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

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

    @POST("1FAIpQLSf9d1TcNU6zc6KR8bSEM41Z1g1zl35cwZr2xyjIhaMAz8WChQ/formResponse")
    @FormUrlEncoded
    Call<Void> submitForm(
            @Field("entry.1877115667") String firstName,
            @Field("entry.2006916086") String lastName,
            @Field("entry.1824927963") String emailAddress,
            @Field("entry.284483984") String projectLink
    );

}
