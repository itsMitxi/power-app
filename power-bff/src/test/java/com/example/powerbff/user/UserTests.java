package com.example.powerbff.user;

import com.example.powerbff.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserTests {

    @Test
    public void shouldCreateUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setEmail("testuser@example.com");
        user.setName("testname");
        user.setImgUrl("testimgurl");

        assertThat(user.getUsername()).isEqualTo("testuser");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.getEmail()).isEqualTo("testuser@example.com");
        assertThat(user.getName()).isEqualTo("testname");
        assertThat(user.getImgUrl()).isEqualTo("testimgurl");
    }

    @Test
    public void shouldAutoAssignId() {
        User user = new User();

        assertThat(user.getId()).isNotEqualTo(null);
    }

}
