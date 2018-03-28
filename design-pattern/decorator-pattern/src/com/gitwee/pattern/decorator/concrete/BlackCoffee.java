package com.gitwee.pattern.decorator.concrete;

public class BlackCoffee extends Coffee{

    public BlackCoffee(String description, int cost) {
        super.setCost(20);
        super.setDescription("Black Coffee");
    }

    @Override
    public String description() {
        return super.description();
    }

    @Override
    public int cost() {
        return super.cost();
    }
}
