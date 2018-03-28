package com.gitwee.pattern.decorator.decorator;

import com.gitwee.pattern.decorator.component.Drink;

public class Milk extends Decorator{

    public Milk(Drink drink) {
        super(drink);
        super.setCost(10);
        super.setDescription("Milk");
    }

}
