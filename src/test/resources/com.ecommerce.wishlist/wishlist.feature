Feature: user Wishlist Management

  In order to save items I want to buy later,
  As a store user,
  I want to be able to add, remove, and view items in my wishlist.

  @add_item
  Scenario: Successfully add a item to a wishlist
    Given the user "user123" does not have a wishlist
    When the user "user123" adds the item "tenis1" to their wishlist
    Then the operation should succeed with a status code of 201
    And the wishlist for user "user123" should contain the item "tenis1"

  @add_item
  Scenario: Successfully add a item to a wishlist
    Given the user "user123" has a wishlist with 1 items
    When the user "user123" adds the item "tenis2" to their wishlist
    Then the operation should succeed with a status code of 201
    And the wishlist for user "user123" should contain the item "tenis1" and "tenis2"

  @add_item
  Scenario: Attempt to add a item to a full wishlist
    Given the user "user456" has a wishlist with 20 items
    When the user "user456" adds the item "tenis21" to their wishlist
    Then the operation should fail with a status code of 400

  @remove_item
  Scenario: Remove a item that exists in the wishlist
    Given the user "user123" has a wishlist containing the items "tenis1" and "tenis2"
    When the user "user123" removes the item "tenis1" from their wishlist
    Then the removal operation should succeed with a status code of 204
    And the wishlist for user "user123" should no longer contain the item "tenis1"

  @remove_item
  Scenario: Try to remove a item from a non-registered wishlist
    Given the user "user999" does not have a wishlist
    When the user "user999" removes the item "tenis999" from their wishlist
    Then the removal operation should fail with a status code of 404

  @list_items
  Scenario: View all items in an existing wishlist
    Given the user "user789" has a wishlist containing the items "tenis4" and "tenis5"
    When the user "user789" requests their wishlist
    Then the response should contain the items "tenis4" and "tenis5" with a status code of 200

  @list_items
  Scenario: View list from empty wishlist
    Given the user "user789" does not have a wishlist registered
    When the user "user789" requests their wishlist
    Then the response should return an empty list with a status code of 200

  @check_item
  Scenario: Check if a specific item exists in the wishlist
    Given the user "user789" has a wishlist containing the items "tenis4" and "tenis5"
    When the user "user789" checks for the item "tenis4" in their wishlist
    Then the system should confirm the item exists with a status code of 200

  @check_item
  Scenario: Check if a specific item does not exist in the wishlist
    Given the user "user789" has a wishlist containing the items "tenis4" and "tenis5"
    When the user "user789" checks for the item "tenis3" in their wishlist
    Then the system should return the information that item was not found with a status code of 200