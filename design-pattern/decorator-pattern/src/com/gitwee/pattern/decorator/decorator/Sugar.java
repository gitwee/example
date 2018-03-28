package com.gitwee.pattern.decorator.decorator;

import com.gitwee.pattern.decorator.component.Drink;

public class Sugar extends Decorator{

    public Sugar(Drink drink) {
        super(drink);
        super.setCost(50);
        super.setDescription("Sugar");
    }

}
