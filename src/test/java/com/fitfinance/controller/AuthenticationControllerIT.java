package com.fitfinance.controller;

import com.fitfinance.config.FileUtils;
import com.fitfinance.config.IntegrationTestConfig;
import com.fitfinance.repository.UserRepository;
import com.fitfinance.service.JwtService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import lombok.extern.log4j.Log4j2;
import net.javacrumbs.jsonunit.assertj.JsonAssertions;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Log4j2
@Sql(value = "/sql/user/init_one_regular_user.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
class AuthenticationControllerIT extends IntegrationTestConfig {
    private static final String URL = "/api/v1/auth";
    private static final String MOCKED_ACCESS_TOKEN = "mocked-access-token";
    private static final String MOCKED_REFRESH_TOKEN = "mocked-refresh-token";
    public static final String REAL_ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJsdWNhc0BmaXRmaW5hbmNlLmNvbSIsImlhdCI6MTQxOTE1Njc0NTAsImV4cCI6MTczODUxNjMyN30.jimoGvt72c_Qj6n8NYEZOVtFmUE7WNCP5To9NgrKBjM";

    @LocalServerPort
    private int port;

    @Autowired
    private FileUtils fileUtils;
    @Autowired
    private UserRepository userRepository;
    @MockitoSpyBean
    private JwtService jwtService;

    @BeforeEach
    void setUrl() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    @Order(1)
    @DisplayName("POST v1/auth/authenticate returns AuthenticationResponse when successful")
    @Sql(value = "/sql/token/clean_tokens.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(value = "/sql/user/clean_users.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void authenticate_ReturnsAuthenticationResponse_WhenSuccessful() {
        var authenticationRequest = fileUtils.readResourceFile("/auth/post-request-authenticate-200.json");
        var expectedResponse = fileUtils.readResourceFile("/auth/post-response-authenticate-200.json");

        var regularUser = userRepository.findByEmail("lucas@fitfinance.com").orElse(null);

        BDDMockito.when(jwtService.generateToken(regularUser)).thenReturn(MOCKED_ACCESS_TOKEN);
        BDDMockito.when(jwtService.generateRefreshToken(regularUser)).thenReturn(MOCKED_REFRESH_TOKEN);


        var response = RestAssured.given()
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(authenticationRequest)
                .when()
                .post(URL + "/authenticate")
                .then()
                .statusCode(HttpStatus.OK.value())
                .log().all()
                .extract().response().body().asString();

        JsonAssertions.assertThatJson(response)
                .isEqualTo(expectedResponse);
    }

    @Test
    @Order(2)
    @DisplayName("POST v1/auth/authenticate returns 403 Forbidden when password is wrong")
    @Sql(value = "/sql/user/clean_users.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void authenticate_Returns403Forbidden_WhenPasswordIsWrong() {
        var authenticationRequest = fileUtils.readResourceFile("/auth/post-request-authenticate-403.json");

        RestAssured.given()
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(authenticationRequest)
                .when()
                .post(URL + "/authenticate")
                .then()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .log().all();
    }

    @Test
    @Order(3)
    @DisplayName("POST /api/v1/auth/refresh-token returns AuthenticationResponse when successful")
    @Sql(value = "/sql/token/init_one_access_token.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/token/clean_tokens.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(value = "/sql/user/clean_users.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void refreshToken_ReturnsAuthenticationResponse_WhenSuccessful() {
        var expectedResponse = fileUtils.readResourceFile("/auth/post-response-refresh-token-200.json");

        var regularUser = userRepository.findByEmail("lucas@fitfinance.com").orElse(null);

        BDDMockito.when(jwtService.generateToken(regularUser)).thenReturn(MOCKED_ACCESS_TOKEN);
        BDDMockito.when(jwtService.generateRefreshToken(regularUser)).thenReturn(MOCKED_REFRESH_TOKEN);

        var headers = new HttpHeaders();
        headers.setBearerAuth(REAL_ACCESS_TOKEN);

        var response = RestAssured.given()
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .headers(headers)
                .when()
                .post(URL + "/refresh-token")
                .then()
                .statusCode(HttpStatus.OK.value())
                .log().all()
                .extract().response().body().asString();

        JsonAssertions.assertThatJson(response)
                .isEqualTo(expectedResponse);
    }

    @Test
    @Order(4)
    @DisplayName("POST /api/v1/auth/refresh-token returns 403 Forbidden when request does not have token")
    @Sql(value = "/sql/token/init_one_access_token.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/sql/token/clean_tokens.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(value = "/sql/user/clean_users.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void refreshToken_Returns403Forbidden_WhenRequestDoesNotHaveToken() {
        RestAssured.given()
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .when()
                .post(URL + "/refresh-token")
                .then()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .log().all();
    }

    @Test
    @Order(5)
    @DisplayName("POST /api/v1/auth/logout returns 204 No Content when successful")
    @Sql(value = "/sql/token/init_one_access_token.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void logout_Returns204NoContent_WhenSuccessful() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(REAL_ACCESS_TOKEN);

        RestAssured.given()
                .contentType(ContentType.JSON).accept(ContentType.JSON)
                .headers(headers)
                .when()
                .post(URL + "/logout")
                .then()
                .statusCode(HttpStatus.OK.value())
                .log().all();

        Assertions.assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();
    }
}