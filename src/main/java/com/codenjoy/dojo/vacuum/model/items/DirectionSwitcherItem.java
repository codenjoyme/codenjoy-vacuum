package com.codenjoy.dojo.vacuum.model.items;

import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.vacuum.model.DirectionSwitcher;
import com.codenjoy.dojo.vacuum.model.Element;

public class DirectionSwitcherItem extends AbstractItem {
    private final DirectionSwitcher switcher;

    public static DirectionSwitcherItem create(Direction direction, int x, int y) {
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
        return new DirectionSwitcherItem(element, direction, x, y);
    }

    private DirectionSwitcherItem(Element element, Direction direction, int x, int y) {
        super(element, x, y);
        this.switcher = new DirectionSwitcher(x, y, direction);
    }

    public DirectionSwitcherItem(DirectionSwitcherItem another) {
        super(another);
        this.switcher = new DirectionSwitcher(another.switcher);
    }

    public Direction getDirection() {
        return switcher.getDirection();
    }
}
