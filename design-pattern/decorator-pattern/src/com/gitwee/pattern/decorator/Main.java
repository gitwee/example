package com.gitwee.pattern.decorator;

import com.gitwee.pattern.decorator.component.Drink;
import com.gitwee.pattern.decorator.concrete.WhiteCoffe;
import com.gitwee.pattern.decorator.decorator.Ice;
import com.gitwee.pattern.decorator.decorator.Milk;
import com.gitwee.pattern.decorator.decorator.Sugar;

public class Main {


    public static void main(String[] args) {
        Drink drink = new Sugar(new Milk(new WhiteCoffe()));
        System.out.println(drink.description() + ":" + drink.cost());

        drink = new Ice(drink);

        System.out.println(drink.description() + ":" + drink.cost());
    }

}
