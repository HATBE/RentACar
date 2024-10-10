package ch.hatbe.soeproject.service.user;

import ch.hatbe.soeproject.persistance.entities.Booking;
import ch.hatbe.soeproject.persistance.entities.User;

import java.util.ArrayList;
import java.util.List;

public interface UserService {
    List<User> getUsers();
}
