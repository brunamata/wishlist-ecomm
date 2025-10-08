package com.ecommerce.wishlist;

import com.ecommerce.wishlist.adapter.dto.WishlistRequest;
import com.ecommerce.wishlist.config.MongoDBContainerInitializer;
import com.ecommerce.wishlist.domain.wishlist.entity.Item;
import com.ecommerce.wishlist.domain.wishlist.entity.WishlistEntity;
import com.ecommerce.wishlist.domain.wishlist.repository.WishlistRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@CucumberContextConfiguration
@ActiveProfiles("test")
public class WishlistStepDefinitions extends MongoDBContainerInitializer {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WishlistRepository wishlistRepository;

    private ResultActions latestResult;

    @BeforeStep
    public void clearRepository() {
        wishlistRepository.deleteAll();
    }

    @Given("the user {string} does not have a wishlist")
    public void the_user_does_not_have_a_wishlist(String userId) {
        // The process has done in the @Before method
    }

    @Given("the user {string} has a wishlist with {int} items")
    public void the_user_has_a_wishlist_with_items(String userId, int itemCount) {
        WishlistEntity wishlist = new WishlistEntity();
        wishlist.setUserId(userId);

        for (int i = 1; i <= itemCount; i++) {
            wishlist.getItems().add(new Item("tenis" + i));
        }

        wishlistRepository.save(wishlist);
    }

    @Given("the user {string} has a wishlist containing the items {string} and {string}")
    public void the_user_has_a_wishlist_containing_the_items(String userId, String item1, String item2) {
        WishlistEntity wishlist = new WishlistEntity();
        wishlist.setUserId(userId);
        wishlist.getItems().add(new Item(item1));
        wishlist.getItems().add(new Item(item2));
        wishlistRepository.save(wishlist);
    }

    @When("the user {string} adds the item {string} to their wishlist")
    public void the_user_adds_the_item_to_their_wishlist(String userId, String itemId) throws Exception {
        WishlistRequest request = new WishlistRequest(userId, new Item(itemId));
        latestResult = mockMvc
                .perform(post("/api/wishlist/{userId}/items", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
    }

    @When("the user {string} removes the item {string} from their wishlist")
    public void the_user_removes_the_item_from_their_wishlist(String userId, String itemId) throws Exception {
            latestResult = mockMvc
                    .perform(delete("/api/wishlist/{userId}/items/{itemId}", userId, itemId));
    }

    @When("the user {string} requests their wishlist")
    public void the_user_requests_their_wishlist(String userId) throws Exception {
            latestResult = mockMvc
                    .perform(get("/api/wishlist/{userId}", userId));
    }

    @When("the user {string} checks for the item {string} in their wishlist")
    public void the_user_checks_for_the_item_in_their_wishlist(String userId, String itemId) throws Exception {
            latestResult = mockMvc
                    .perform(get("/api/wishlist/{userId}/items/{itemId}", userId, itemId));
    }

    @Then("the operation should succeed with a status code of {int}")
    public void the_operation_should_succeed_with_a_status_code_of(int statusCode) throws Exception {
        latestResult.andExpect(status().is(statusCode));
    }

    @Then("the operation should fail with a status code of {int}")
    public void the_operation_should_fail_with_a_status_code_of(int statusCode) throws Exception {
        latestResult.andExpect(status().is(statusCode));
    }

    @Then("the removal operation should succeed with a status code of {int}")
    public void the_removal_operation_should_succeed_with_a_status_code_of(int statusCode) throws Exception {
        latestResult.andExpect(status().is(statusCode));
    }

    @And("the wishlist for user {string} should contain the item {string}")
    public void the_wishlist_for_user_should_contain_the_item(String userId, String itemId) throws Exception {
        mockMvc.perform(get("/api/wishlist/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items", hasItem(itemId)));
        }

    @And("the wishlist for user {string} should contain the item {string} and {string}")
    public void the_wishlist_for_user_should_contain_the_item_and(String userId, String item1, String item2) throws Exception {
        mockMvc.perform(get("/api/wishlist/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items",  hasItems(item1, item2)));
    }

    @And("the wishlist for user {string} should no longer contain the item {string}")
    public void the_wishlist_for_user_should_no_longer_contain_the_item(String userId, String itemId) {
        try {
            mockMvc.perform(get("/api/wishlist/{userId}", userId))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.items", not(hasItem(itemId))));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Then("the response should contain the items {string} and {string}")
    public void the_response_should_contain_the_items(String item1, String item2) throws Exception {
        latestResult.andExpect(jsonPath("$.items", hasItems(item1, item2)));
    }


    @Then("the system should confirm the item exists with a status code of {int}")
    public void the_system_should_confirm_the_item_exists_with_a_status_code_of(int statusCode) throws Exception {
        latestResult.andExpect(jsonPath("$.exists", hasItem(true))).andExpect(status().is(statusCode));
    }

    @Then("the system should report that the item was not found with a status code of {int}")
    public void the_system_should_report_that_the_item_was_not_found_with_a_status_code_of(int statusCode) throws Exception {
        latestResult.andExpect(jsonPath("$.exists", hasItem(false))).andExpect(status().is(statusCode));
    }
}
