package ch.hatbe.soeproject.service.user;

import ch.hatbe.soeproject.persistance.Database;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {
    private final Database database;

    public UserServiceImpl(Database database) {
        this.database = database;
    }

    public ArrayList<String> getUsers() {
        return database.getUsers();
    }
}
