package me.giannists.rest;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.jayway.restassured.RestAssured;
import me.giannists.helpers.JsonHelper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.jayway.restassured.RestAssured.when;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@Profile("test")
public class UserControllerIntegrationTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);

    @LocalServerPort
    int port;

    @Before
    public void init(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = this.port;

        stubFor(get(urlEqualTo("/users/2"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withBody(JsonHelper.getMockUserById().toString())));


        stubFor(get(urlEqualTo("/posts?userId=2"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "text/plain")
                        .withBody(JsonHelper.getMockPostsByUserId().toString())));
    }

    @Test
    public void getUserDataShouldReturnAggregatedData() {
        String responseJson = when()
                .get("/users/2/data")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().response().asString();

        assertEquals(JsonHelper.getMockMergedResponse().toString(), responseJson);
    }

}
