package edu.acc.j2ee.hubbub.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

public class HubbubUserDao {
    public static final Pattern USER_PATTERN = Pattern.compile("^\\w{6,12}$");
    public static final Pattern PASS_PATTERN = Pattern.compile("^[\\w\\.-]{8,16}$");
    public static final Pattern JOIN_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");

    private final List<HubbubUser> REPOSITORY = new ArrayList<>();
    
    public HubbubRegisterResult register(HubbubRegisterBean bean) {
        HubbubRegisterResult result = new HubbubRegisterResult();
        if (bean.getUser() == null || bean.getPass1() == null || bean.getPass2() == null
                || bean.getUser().length() < 1 || bean.getPass1().length() < 1 ||
                bean.getPass2().length() < 1) {
            result.addError("One or more fields are empty");
            return result;
        }
        if (!bean.getPass1().equals(bean.getPass2()))
            result.addError("Passwords do not match");
        if (!USER_PATTERN.matcher(bean.getUser()).matches())
            result.addError("User name field is empty or invalid");
        if (!PASS_PATTERN.matcher(bean.getPass1()).matches())
            result.addError("First password field is invalid");
        if (!PASS_PATTERN.matcher(bean.getPass2()).matches())
            result.addError("Second password field is invalid");
        if (result.getErrors().isEmpty()) {
            HubbubUser user = new HubbubUser(
                bean.getUser(), bean.getPass1(), LocalDate.now().toString());
            if (this.addUser(user))
                result.setUser(user);
            else
                result.addError("That user name is unavailable.");
        }
        return result;
    }
    
    public HubbubUser authenticate(String userName, String password) {
        if (!USER_PATTERN.matcher(userName).matches() ||
                !PASS_PATTERN.matcher(password).matches())
            return null;
        for (HubbubUser u : REPOSITORY) {
            if (u.getUserName().equals(userName) && u.getPassword().equals(password))
                return u;
        }
        return null;
    }

    public synchronized boolean addUser(HubbubUser user) {
        for (HubbubUser u : REPOSITORY) {
            if (u.getUserName().equals(user.getUserName())) {
                return false;
            }
        }
        return REPOSITORY.add(user);
    }

    public List<HubbubUser> getAllUsers() {
        return new ArrayList<>(REPOSITORY);
    }

    public List<HubbubUser> getUsersByJoinDateAscending() {
        return getUsersBy((h1,h2) -> (h1.getJoinDate().compareTo(h2.getJoinDate())));
    }

    public List<HubbubUser> getUsersByJoinDateDescending() {
        return getUsersBy((h1,h2) -> (h2.getJoinDate().compareTo(h1.getJoinDate())));
    }
    
    public List<HubbubUser> getUsersByUserNameAscending() {
        return getUsersBy((h1,h2) -> h1.getUserName().compareTo(h2.getUserName()));
    }
    
    private List<HubbubUser> getUsersBy(Comparator<HubbubUser> comp) {
        List<HubbubUser> clone = getAllUsers();
        Collections.sort(clone, comp);
        return clone;
    }
}
