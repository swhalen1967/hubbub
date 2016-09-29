package edu.acc.j2ee.hubbub.models;

import java.util.ArrayList;
import java.util.List;

public class HubbubModel<T> {
    private final List<String> errors = new ArrayList<>();
    private T model;
    
    public HubbubModel() {}
    
    public HubbubModel(T model) {
        this.model = model;
    }

    public List<String> getErrors() {
        return errors;
    }

    public HubbubModel<T> addError(String error) {
        errors.add(error);
        return this;
    }
    
    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }
    
    @Override
    public String toString() {
        return String.format("Model{model=%s, errors=%s}", model, errors);
    }
}
