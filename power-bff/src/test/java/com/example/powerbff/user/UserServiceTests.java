package com.example.powerbff.user;

import com.example.powerbff.entity.User;
import com.example.powerbff.exception.user.*;
import com.example.powerbff.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @Test
    public void shouldCreateAndDeleteUser() {
        User user = new User();
        user.setUsername("user");
        try {
            user = userService.createUser(user);
        } catch (Exception ignored) {}

        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("user");

        Optional<User> userFound = userService.getUserByUsername(user.getUsername());
        assertThat(userFound).isPresent();
        assertThat(userFound.get().getUsername()).isEqualTo(user.getUsername());

        userService.deleteUser(userFound.get().getId());
        Optional<User> userDeleted = userService.getUserByUsername(user.getUsername());
        assertThat(userDeleted).isNotPresent();
    }

    @Test
    public void shouldThrowUsernameExceptionWithDuplicatedUsername() {
        User user = new User();
        user.setUsername("same-username");
        try {
            user = userService.createUser(user);
        } catch (Exception ignored) {}

        Optional<User> userFound = userService.getUserByUsername(user.getUsername());
        assertThat(userFound).isPresent();

        User sameUser = user;
        assertThrows(UsernameAlreadyExistsException.class, () -> userService.createUser(sameUser));

        userService.deleteUser(userFound.get().getId());
    }

    @Test
    public void shouldThrowEmailExceptionWithDuplicatedEmail() {
        User user = new User();
        user.setUsername("user");
        user.setEmail("same-email");
        try {
            user = userService.createUser(user);
        } catch (Exception ignored) {}

        Optional<User> userFound = userService.getUserByUsername(user.getUsername());
        assertThat(userFound).isPresent();

        User sameEmailUser = user;
        sameEmailUser.setUsername("user2");
        assertThrows(EmailAlreadyExistsException.class, () -> userService.createUser(sameEmailUser));

        userService.deleteUser(userFound.get().getId());
    }

    @Test
    public void shouldFindUserById() {
        User user = new User();
        user.setUsername("find-by-id");
        try {
            userService.createUser(user);
        } catch (Exception ignored) {}

        Optional<User> userFound = userService.getUserById(user.getId());
        assertThat(userFound).isPresent();
        assertThat(userFound.get().getUsername()).isEqualTo(user.getUsername());

        userService.deleteUser(userFound.get().getId());
    }

    @Test
    public void shouldFindAllUsers() {
        User user1 = new User();
        user1.setUsername("user1");
        User user2 = new User();
        user2.setUsername("user2");
        User user3 = new User();
        user3.setUsername("user3");
        try {
            userService.createUser(user1);
            userService.createUser(user2);
            userService.createUser(user3);
        } catch (Exception ignored) {}

        List<User> users = userService.getAllUsers();
        assertThat(users).isNotEmpty();
        assertThat(users.size()).isEqualTo(3);
        assertThat(users.stream().map(User::getUsername)).containsExactlyInAnyOrder("user1", "user2", "user3");

        userService.deleteUser(user1.getId());
        userService.deleteUser(user2.getId());
        userService.deleteUser(user3.getId());
    }

    @Test
    public void shouldThrowExceptionWithBlankUsername() {
        User user = new User();
        user.setUsername("");
        assertThrows(BlankUsernameException.class, () -> userService.createUser(user));
    }

    @Test
    public void shouldUpdateUser() {
        User user = new User();
        user.setUsername("user-update");
        try {
            user = userService.createUser(user);
        } catch (Exception ignored) {}

        Optional<User> userFound = userService.getUserById(user.getId());
        assertThat(userFound).isPresent();

        user = new User();
        user.setPassword("newpass");
        user.setEmail("newemail");
        user.setName("newname");
        user.setImgUrl("newimgurl");
        try {
            user = userService.updateUser(userFound.get().getId(), user);
        } catch (Exception ignored) {}

        Optional<User> userUpdated = userService.getUserById(user.getId());
        assertThat(userUpdated).isPresent();
        assertThat(userUpdated.get().getPassword()).isEqualTo("newpass");
        assertThat(userUpdated.get().getEmail()).isEqualTo("newemail");
        assertThat(userUpdated.get().getName()).isEqualTo("newname");
        assertThat(userUpdated.get().getImgUrl()).isEqualTo("newimgurl");

        userService.deleteUser(userUpdated.get().getId());
    }

    @Test
    public void shouldThrowExceptionUpdatingUserWithDuplicatedUsername() {
        User user = new User();
        user.setUsername("username");
        User user2 = new User();
        user2.setUsername("user2");
        try {
            userService.createUser(user);
            userService.createUser(user2);
        } catch (Exception ignored) {}

        Optional<User> userFound = userService.getUserByUsername(user.getUsername());
        assertThat(userFound).isPresent();
        Optional<User> user2Found = userService.getUserByUsername(user2.getUsername());
        assertThat(user2Found).isPresent();

        User finalUser = new User();
        finalUser.setUsername("user2");
        assertThrows(UsernameAlreadyExistsException.class, () -> userService.updateUser(userFound.get().getId(), finalUser));

        userService.deleteUser(userFound.get().getId());
        userService.deleteUser(user2Found.get().getId());
    }

    @Test
    public void shouldUpdateUsernameIfItsNotDuplicated() {
        User user = new User();
        user.setUsername("username");
        try {
            userService.createUser(user);
        } catch (Exception ignored) {}

        Optional<User> userFound = userService.getUserByUsername(user.getUsername());
        assertThat(userFound).isPresent();

        User finalUser = new User();
        finalUser.setUsername("newusername");
        try {
            user = userService.updateUser(userFound.get().getId(), finalUser);
        } catch (Exception ignored) {}

        Optional<User> userUpdated = userService.getUserById(user.getId());
        assertThat(userUpdated).isPresent();
        assertThat(userUpdated.get().getUsername()).isEqualTo("newusername");

        userService.deleteUser(userFound.get().getId());
    }

    @Test
    public void shouldThrowExceptionWhenUpdatingUserNotFound() {
        User user = new User();
        user.setUsername("username");
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(999L, user));
    }

}
