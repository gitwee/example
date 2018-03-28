package com.gitwee.pattern.decorator.concrete;

import com.gitwee.pattern.decorator.component.Drink;

public abstract class Coffee extends Drink{

    @Override
    public String description() {
        return super.getDescription();
    }

    @Override
    public int cost() {
        return super.getCost();
    }
}

