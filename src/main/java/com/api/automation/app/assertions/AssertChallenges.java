package com.api.automation.app.assertions;

import com.api.automation.app.models.Challenge;
import com.api.automation.app.models.ChallengesResponse;
import com.api.automation.core.assertions.BaseApiAssert;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;

import java.util.List;

public class AssertChallenges extends BaseApiAssert {

    private final List<Challenge> challenges;

    public AssertChallenges(Response response) {
        super(response);
        this.challenges = response.as(ChallengesResponse.class).getChallenges();
    }

    public void checkListNotEmpty() {
        Assertions.assertThat(challenges)
                .as("challenges list")
                .isNotEmpty();
    }

    public void checkError(){
        assertStatus(404);
    }


}
