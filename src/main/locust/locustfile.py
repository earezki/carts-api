import random
import uuid

from locust import task, between, constant
from locust.contrib.fasthttp import FastHttpUser


def currency():
    currencies = ['eur', 'mad', 'usd']
    return random.choice(currencies)


def random_float(min: float, max: float):
    return "{:.2f}".format(random.uniform(min, max))


class CartsApi(FastHttpUser):
    wait_time = constant(0.5)

    default_cart_id = uuid.uuid4()
    cart_id = default_cart_id

    @task()
    def create_new_cart(self):
        response = self.client.post(
            "/v1/carts",
            json={
                "currency": currency()
            }
        )

        if response.status_code == 201:
            location = response.headers['location']
            uri_parts = location.split('/')
            self.cart_id = uri_parts[len(uri_parts) - 1]

    @task()
    def add_cart_item(self):
        cartItem = {
            "productId": f"p-{random.randrange(1, 1000)}",
            "price": random_float(1, 100),
            "quantity": random.randrange(1, 100),
            "mergeMode": random.choice(["REPLACE", "MERGE"])
        }

        with self.client.put(
                f"/v1/carts/{self.cart_id}",
                name="/v1/carts/{cartId}",
                catch_response=True,
                json=cartItem
        ) as response:
            if self.cart_id is self.default_cart_id and response.status_code == 404:
                response.success()

    @task()
    def get_cart_by_id(self):
        with self.client.get(
                f"/v1/carts/{self.cart_id}",
                name="/v1/carts/{cartId}",
                catch_response=True,
        ) as response:
            if self.cart_id is self.default_cart_id and response.status_code == 404:
                response.success()
