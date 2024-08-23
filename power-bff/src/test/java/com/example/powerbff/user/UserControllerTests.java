package com.example.powerbff.user;

import com.example.powerbff.constants.ApiEndpoints;
import com.example.powerbff.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserControllerTests {

    private final TestRestTemplate restTemplate = new TestRestTemplate();
    private String baseUrl;

    @BeforeEach
    public void setUp() {
        baseUrl = UriComponentsBuilder.fromHttpUrl(ApiEndpoints.BASE_URL)
                .path(ApiEndpoints.USERS_BASE)
                .toUriString();
    }

    @Test
    public void shouldCreateAndDelteUser() {
        User user = new User();
        user.setUsername("user");
        System.out.println(baseUrl);
        restTemplate.postForEntity(baseUrl, user, User.class);

        ResponseEntity<User> response = restTemplate.getForEntity(baseUrl + ApiEndpoints.USER_BY_USERNAME + user.getUsername(), User.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        long id = Objects.requireNonNull(response.getBody()).getId();
        restTemplate.delete(baseUrl + ApiEndpoints.USER_BY_ID + id, User.class);

        response = restTemplate.getForEntity(baseUrl + ApiEndpoints.USER_BY_USERNAME + user.getUsername(), User.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void shouldCreateUserWithAutoGenerateId() {
        User user = new User();
        user.setUsername("user");
        user.setId(999L);
        restTemplate.postForEntity(baseUrl, user, User.class);

        ResponseEntity<User> response = restTemplate.getForEntity(baseUrl + ApiEndpoints.USER_BY_USERNAME + user.getUsername(), User.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        long id = Objects.requireNonNull(response.getBody()).getId();
        assertThat(id).isNotEqualTo(999L);

        restTemplate.delete(baseUrl + ApiEndpoints.USER_BY_ID + id, User.class);
    }

    @Test
    public void shouldGetAllUsers() {
        User user1 = new User();
        user1.setUsername("user1");
        User user2 = new User();
        user2.setUsername("user2");
        User user3 = new User();
        user3.setUsername("user3");
        restTemplate.postForEntity(baseUrl, user1, User.class);
        restTemplate.postForEntity(baseUrl, user2, User.class);
        restTemplate.postForEntity(baseUrl, user3, User.class);

        ParameterizedTypeReference<List<User>> responseType = new ParameterizedTypeReference<>() {};
        ResponseEntity<List<User>> response = restTemplate.exchange(baseUrl, HttpMethod.GET, null, responseType);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<User> users = response.getBody();
        assertThat(users).isNotEmpty();
        assertThat(users.size()).isEqualTo(3);

        users.forEach(user -> restTemplate.delete(baseUrl + ApiEndpoints.USER_BY_ID + user.getId(), User.class));
    }

    @Test
    public void shouldGetUserById() {
        User user = new User();
        user.setUsername("user");
        ResponseEntity<User> response = restTemplate.postForEntity(baseUrl, user, User.class);

        long id = Objects.requireNonNull(response.getBody()).getId();
        response = restTemplate.getForEntity(baseUrl + ApiEndpoints.USER_BY_ID + id, User.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        String username = Objects.requireNonNull(response.getBody()).getUsername();
        assertThat(username).isEqualTo(user.getUsername());

        restTemplate.delete(baseUrl + ApiEndpoints.USER_BY_ID + id, User.class);
    }

    @Test
    public void shouldGetUserByUsername() {
        User user = new User();
        user.setUsername("user-to-found");
        restTemplate.postForEntity(baseUrl, user, User.class);

        ResponseEntity<User> response = restTemplate.getForEntity(baseUrl + ApiEndpoints.USER_BY_USERNAME + user.getUsername(), User.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        long id = Objects.requireNonNull(response.getBody()).getId();
        String username = Objects.requireNonNull(response.getBody()).getUsername();
        assertThat(username).isEqualTo(user.getUsername());

        restTemplate.delete(baseUrl + ApiEndpoints.USER_BY_ID + id, User.class);
    }

    @Test
    public void shouldUpdateUser() {
        User user = new User();
        user.setUsername("user");
        restTemplate.postForEntity(baseUrl, user, User.class);

        ResponseEntity<User> response = restTemplate.getForEntity(baseUrl + ApiEndpoints.USER_BY_USERNAME + user.getUsername(), User.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        User updatedUser = response.getBody();
        updatedUser.setUsername("updated-user");
        updatedUser.setPassword("updated-password");
        updatedUser.setEmail("updated-email");
        updatedUser.setName("updated-name");
        updatedUser.setImgUrl("updated-img-url");
        restTemplate.put(baseUrl + ApiEndpoints.USER_BY_ID + updatedUser.getId(), updatedUser);

        response = restTemplate.getForEntity(baseUrl + ApiEndpoints.USER_BY_USERNAME + updatedUser.getUsername(), User.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getPassword()).isEqualTo("updated-password");

        restTemplate.delete(baseUrl + ApiEndpoints.USER_BY_ID + updatedUser.getId(), User.class);
    }

    @Test
    public void shouldNotUpdateUserIfUsernameDuplicated() {
        User user1 = new User();
        user1.setUsername("same-username");
        User user2 = new User();
        user2.setUsername("user");
        restTemplate.postForEntity(baseUrl, user1, User.class);
        restTemplate.postForEntity(baseUrl, user2, User.class);

        ResponseEntity<User> response = restTemplate.getForEntity(baseUrl + ApiEndpoints.USER_BY_USERNAME + user1.getUsername(), User.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        long user1Id = Objects.requireNonNull(response.getBody()).getId();
        response = restTemplate.getForEntity(baseUrl + ApiEndpoints.USER_BY_USERNAME + user2.getUsername(), User.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

        User updatedUser = response.getBody();
        updatedUser.setUsername("same-username");
        response = restTemplate.exchange(baseUrl + ApiEndpoints.USER_BY_ID + updatedUser.getId(), HttpMethod.PUT, new HttpEntity<>(updatedUser), User.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        restTemplate.delete(baseUrl + ApiEndpoints.USER_BY_ID + user1Id, User.class);
        restTemplate.delete(baseUrl + ApiEndpoints.USER_BY_ID + updatedUser.getId(), User.class);
    }

    @Test
    public void shouldNotUpdateId() {
        User user = new User();
        user.setUsername("user");
        restTemplate.postForEntity(baseUrl, user, User.class);

        ResponseEntity<User> response = restTemplate.getForEntity(baseUrl + ApiEndpoints.USER_BY_USERNAME + user.getUsername(), User.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        long userId = Objects.requireNonNull(response.getBody()).getId();
        long updatedUserId = userId + 1;

        user = response.getBody();
        user.setId(updatedUserId);
        restTemplate.put(baseUrl + ApiEndpoints.USER_BY_ID + userId, user);

        response = restTemplate.getForEntity(baseUrl + ApiEndpoints.USER_BY_ID + updatedUserId, User.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        response = restTemplate.getForEntity(baseUrl + ApiEndpoints.USER_BY_ID + userId, User.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        restTemplate.delete(baseUrl + ApiEndpoints.USER_BY_ID + userId, User.class);
    }

}
