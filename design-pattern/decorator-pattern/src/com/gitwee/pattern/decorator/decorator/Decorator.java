package com.gitwee.pattern.decorator.decorator;

import com.gitwee.pattern.decorator.component.Drink;

public abstract class Decorator extends Drink {

    private Drink drink;

    public Decorator(Drink drink) {
        this.drink = drink;
    }

    @Override
    public String description() {
        return super.getDescription() + "&&" + drink.description();
    }

    @Override
    public int cost() {
        return super.getCost() + drink.cost();
    }
}
