package trs.db;

import trs.entity.Vehicle;
import trs.entity.user.User;
import trs.entity.user.UserRole;
import trs.exception.DatabaseException;

import java.util.HashMap;
import java.util.Map;

public class UserDatabase {

    private static UserDatabase instance;
    private Map<String, User> userBase;

    private UserDatabase() {
        this.userBase = new HashMap<>();
        userBase.put("admin",new User("admin", "admin", UserRole.ADMIN));
        userBase.put("firstuser", new User("firstuser", "firstuser", UserRole.USER));
    }

    public static UserDatabase getInstance(){

        if (instance == null){
            instance = new UserDatabase();
        }
        return instance;
    }

    public void registerUser(User user) throws DatabaseException{
        if (userBase.containsKey(user.getLogin())) throw new DatabaseException("Користувач вже зареєстрований!");
        userBase.put(user.getLogin(), user);
    }

    public User getUser(String login){
        return userBase.get(login);
    }

    public Map<String, User> getBase() { return userBase; }

    public void setBase(Map<String, User> userBase){ this.userBase = userBase; }

}
