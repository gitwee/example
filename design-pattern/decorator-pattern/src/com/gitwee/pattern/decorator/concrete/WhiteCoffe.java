package com.gitwee.pattern.decorator.concrete;

public class WhiteCoffe extends Coffee{

    public WhiteCoffe() {
        super.setCost(30);
        super.setDescription("White Coffee");
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
