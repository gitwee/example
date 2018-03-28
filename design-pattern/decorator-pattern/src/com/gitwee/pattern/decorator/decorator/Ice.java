package com.gitwee.pattern.decorator.decorator;

import com.gitwee.pattern.decorator.component.Drink;

public class Ice extends Decorator{

    public Ice(Drink drink) {
        super(drink);
        super.setCost(3);
        super.setDescription("Ice");
    }

}
