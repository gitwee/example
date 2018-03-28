package com.gitwee.pattern.decorator.component;

public abstract class Drink {

    private String description;

    private int cost;

    public abstract String description();
    public abstract int cost();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
