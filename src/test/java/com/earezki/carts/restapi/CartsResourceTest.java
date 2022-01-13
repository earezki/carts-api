package com.earezki.carts.restapi;

import com.earezki.carts.application.AddCartItemCommand;
import com.earezki.carts.application.CreateCartCommand;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class CartsResourceTest {

    @Test
    public void test_create_new_cart() {

        String location = given().body(new CreateCartCommand("EUR"))
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when().post("/carts")
                .then()
                .statusCode(201)
                .extract()
                .header("Location");

        addCartItem(location, new AddCartItemCommand("undefined","123", BigDecimal.ONE, 1, "merge"));

        Response cartData = getCart(location);
        assertEquals(
                location.substring(location.lastIndexOf("/") + 1),
                cartData.jsonPath().get("id")
        );
        assertEquals(
                "EUR",
                cartData.jsonPath().get("currency")
        );
        List<Map> cartItems = cartData.jsonPath().getList("cartItems");

        assertEquals(1, cartItems.size());
        assertEquals("123", cartItems.get(0).get("productId"));
        assertEquals(0, BigDecimal.ONE.compareTo(new BigDecimal(cartItems.get(0).get("totalPrice").toString())));
        assertEquals(1, cartItems.get(0).get("quantity"));

    }

    private void addCartItem(String location, AddCartItemCommand request) {
        given().body(request)
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when().put(location)
                .then()
                .statusCode(204);
    }

    private Response getCart(String location) {
        return given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .when().get(location)
                .then()
                .statusCode(200)
                .extract().response();
    }

}