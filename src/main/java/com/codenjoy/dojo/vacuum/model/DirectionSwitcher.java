package com.codenjoy.dojo.vacuum.model;

import com.codenjoy.dojo.services.Direction;

public class DirectionSwitcher {
    private final int x;
    private final int y;
    private Direction direction;

    public DirectionSwitcher(int x, int y, Direction direction) {
        if (direction == null) {
            throw new IllegalArgumentException("Direction can not be null");
        }
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public DirectionSwitcher(DirectionSwitcher another) {
        this(another.x, another.y, another.direction);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }
}
