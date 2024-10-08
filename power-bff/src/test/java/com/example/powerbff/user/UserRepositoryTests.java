package com.example.powerbff.user;

import com.example.powerbff.entity.User;
import com.example.powerbff.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldSaveAndDeleteUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setPassword("pass");

        userRepository.save(user);
        Optional<User> foundUser = userRepository.findByUsername("testuser");
        assertThat(foundUser).isPresent();

        userRepository.deleteById(foundUser.get().getId());
        foundUser = userRepository.findByUsername("testuser");
        assertThat(foundUser).isNotPresent();
    }

    @Test
    public void shouldCreateTwoUsersWithDifferentIds() {
        User user = new User();
        user.setUsername("oneUser");
        userRepository.save(user);

        user = new User();
        user.setUsername("anotherUser");
        userRepository.save(user);

        Optional<User> oneUser = userRepository.findByUsername("oneUser");
        assertThat(oneUser).isPresent();

        Optional<User> anotherUser = userRepository.findByUsername("anotherUser");
        assertThat(anotherUser).isPresent();

        assertThat(oneUser.get().getId()).isNotEqualTo(anotherUser.get().getId());

        userRepository.deleteById(oneUser.get().getId());
        userRepository.deleteById(anotherUser.get().getId());
    }

    @Test
    public void shouldNotCreateTwoUsersWithSameUsername() {
        User user = new User();
        user.setUsername("sameUsername");
        user.setName("user1");
        userRepository.save(user);

        user = new User();
        user.setUsername("sameUsername");
        user.setName("user2");
        try {
            userRepository.save(user);
        } catch (Exception ignored) {}

        Optional<User> user1 = userRepository.findByName("user1");
        assertThat(user1).isPresent();

        Optional<User> user2 = userRepository.findByName("user2");
        assertThat(user2).isNotPresent();

        userRepository.deleteById(user1.get().getId());
    }

    @Test
    public void shouldNotUpdateCreatedAt() {
        User user = new User();
        user.setUsername("user");
        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByUsername("user");
        assertThat(foundUser).isPresent();

        user.setCreatedAt(LocalDateTime.of(2002, 7, 22, 0, 0, 0));
        user.setName("modified");
        userRepository.save(user);

        foundUser = userRepository.findByUsername("user");
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getCreatedAt()).isNotEqualTo(LocalDateTime.of(2002, 7, 22, 0, 0, 0));
        assertThat(foundUser.get().getName()).isEqualTo("modified");

        userRepository.deleteById(foundUser.get().getId());
    }

    @Test
    public void shouldNotCreateUserWithNonAutoGeneratedId() {
        User user = new User();
        user.setId(999);
        user.setUsername("personal-id");
        userRepository.save(user);

        assertThat(userRepository.findById(999L)).isNotPresent();
        Optional<User> foundUser = userRepository.findByUsername("personal-id");
        assertThat(foundUser).isPresent();

        userRepository.deleteById(foundUser.get().getId());
    }

    @Test
    public void shouldNotUpdateUserWithNewId() {
        User user = new User();
        user.setUsername("user");
        user.setName("name1");
        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByName("name1");
        assertThat(foundUser).isPresent();

        user = foundUser.get();
        user.setId(999);
        user.setName("name2");
        try {
            userRepository.save(user);
        } catch (Exception ignored) {}

        foundUser = userRepository.findByName("name2");
        assertThat(foundUser).isNotPresent();

        foundUser = userRepository.findByName("name1");
        assertThat(foundUser).isPresent();

        userRepository.deleteById(foundUser.get().getId());
    }

    @Test
    public void shouldUpdateOtherFieldsThanIdAndCreatedAt() {
        User user = new User();
        user.setUsername("user");
        user.setName("name1");
        user.setPassword("pass1");
        userRepository.save(user);

        Optional<User> foundUser = userRepository.findByUsername("user");
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getName()).isEqualTo("name1");
        assertThat(foundUser.get().getPassword()).isEqualTo("pass1");

        user = foundUser.get();
        user.setName("name2");
        user.setPassword("pass2");
        userRepository.save(user);

        foundUser = userRepository.findByUsername("user");
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getName()).isEqualTo("name2");
        assertThat(foundUser.get().getPassword()).isEqualTo("pass2");

        userRepository.deleteById(foundUser.get().getId());
    }

}
