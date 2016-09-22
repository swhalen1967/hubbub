package edu.acc.j2ee.hubbub.models;

import java.util.ArrayList;
import java.util.List;

public class HubbubRegisterResult {
    private List<String> errors = new ArrayList<>();
    private HubbubUser user;
    
    public boolean addError(String message) {
        return errors.add(message);
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public boolean success() {
        return errors.isEmpty();
    }

    public HubbubUser getUser() {
        return user;
    }

    public void setUser(HubbubUser user) {
        this.user = user;
    }
    
    @Override
    public String toString() {
        return String.format("HubbubRegisterResult[errors:%d, user:%s]",
                errors.size(), user);
    }
    
}
