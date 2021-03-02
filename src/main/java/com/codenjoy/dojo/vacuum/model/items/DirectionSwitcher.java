package com.codenjoy.dojo.vacuum.model.items;

import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.vacuum.model.Element;

public class DirectionSwitcher extends AbstractItem {
    private final Direction direction;

    public static DirectionSwitcher create(Direction direction, int x, int y) {
        Element element;
        switch (direction) {
            case LEFT:
                element = Element.SWITCH_LEFT;
                break;
            case RIGHT:
                element = Element.SWITCH_RIGHT;
                break;
            case UP:
                element = Element.SWITCH_UP;
                break;
            case DOWN:
                element = Element.SWITCH_DOWN;
                break;
            default:
                throw new IllegalArgumentException("Direction " + direction + " is not supported by direction switchers");
        }
        return new DirectionSwitcher(element, direction, x, y);
    }

    private DirectionSwitcher(Element element, Direction direction, int x, int y) {
        super(element, x, y);
        this.direction = direction;
    }

    public DirectionSwitcher(DirectionSwitcher another) {
        super(another);
        this.direction = another.direction;
    }

    public Direction getDirection() {
        return direction;
    }
}
