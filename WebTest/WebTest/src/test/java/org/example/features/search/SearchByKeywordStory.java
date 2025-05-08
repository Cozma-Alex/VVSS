package org.example.features.search;

import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Issue;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Pending;
import net.thucydides.core.annotations.Steps;

import net.thucydides.junit.annotations.UseTestDataFrom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import org.example.steps.serenity.EndUserSteps;

@RunWith(SerenityRunner.class)
public class SearchByKeywordStory {

    @Managed(uniqueSession = true)
    public WebDriver webdriver;

    @Steps
    public EndUserSteps anna;

    @Issue("#HUB FULL USE CASE")
    @Test
    public void full_use_case_test() {
        anna.is_the_home_page();
        anna.clicks_sign_in_link();
        anna.enter_good_data();
        anna.search_for("linux");
        anna.click_linux_torvalds_linux_link();
        anna.star_repository();
        anna.go_to_your_stars();
        anna.unstar_repository();

    }


}
