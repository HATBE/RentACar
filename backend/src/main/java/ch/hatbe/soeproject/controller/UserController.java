package ch.hatbe.soeproject.controller;

import ch.hatbe.soeproject.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// TODO:
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = {"", "/"})
    public ResponseEntity<String> getUsers() {
        return ResponseEntity.ok("users");
    }

    @GetMapping("/{userid}")
    public ResponseEntity<String> getUser(@PathVariable int userid) {
        return ResponseEntity.ok("user: " + userid);
    }

    @PostMapping("/")
    public ResponseEntity<String> postUser() {
        return ResponseEntity.ok("post user");
    }

    @PatchMapping("/{userid}")
    public ResponseEntity<String> patchUser(@PathVariable int userid) {
        return ResponseEntity.ok("patch user" + userid);
    }

    @DeleteMapping("/{userid}")
    public ResponseEntity<String> deleteUser(@PathVariable int userid) {
        return ResponseEntity.ok("delete user" + userid);
    }
}
