package org.example.steps.serenity;

import org.example.pages.DictionaryPage;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;

public class EndUserSteps {

    DictionaryPage dictionaryPage;

    @Step
    public void enter_good_data() {
        dictionaryPage.enter_login_data("bangabriel16@gmail.com", "bangabriel16");
        assertThat(dictionaryPage.avatar_is_present(), is(true));
    }

    @Step
    public void search_for(String term) {
        assertThat(dictionaryPage.click_search_button(), is(true));
        dictionaryPage.search_for(term);
        assertThat(dictionaryPage.linux_torvald_linux_present(), is(true));

    }

    @Step
    public void go_to_your_stars() {
        assertThat(dictionaryPage.go_to_your_stars(), is(true));
    }

    @Step
    public void unstar_repository() {
        assertThat(dictionaryPage.unstar_repository(), is(false));
    }
    @Step
    public void click_linux_torvalds_linux_link(){
        assertThat(dictionaryPage.click_linux_torvalds_linux_link(), is(true));
    }

    @Step
    public void star_repository() {
        assertThat(dictionaryPage.star_repository(), is(true));
    }

    @Step
    public void enter_data(String email, String password){
        dictionaryPage.enter_login_data(email, password);
        if(password .equals("bangabriel16")) {
            assertThat(dictionaryPage.avatar_is_present(), is(true));
        } else {
            assertThat(dictionaryPage.get_login_error_message(), containsString("Incorrect username or password."));
        }
    }
    @Step
    public void enter_bad_data() {
        dictionaryPage.enter_login_data("anemailthatdefenetlyshouldnotbevalid@hopeso.com", "password");
        assertThat(dictionaryPage.get_login_error_message(), containsString("Incorrect username or password."));
    }
    @Step
    public void clicks_sign_in_link() {
        dictionaryPage.click_sign_in_link();
    }




    @Step
    public void is_the_home_page() {
        dictionaryPage.open();

    }

}