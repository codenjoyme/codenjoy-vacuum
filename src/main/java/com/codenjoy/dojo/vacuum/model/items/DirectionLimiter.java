package com.codenjoy.dojo.vacuum.model.items;

import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.PointImpl;
import com.codenjoy.dojo.vacuum.model.Element;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

import static com.codenjoy.dojo.vacuum.model.Element.*;

public class DirectionLimiter extends AbstractItem {
    private final List<Direction> permittedEntryDirections;

    public static DirectionLimiter create(int x, int y, Direction... directions) {
        if (directions.length == 1) {
            Direction direction = directions[0];
            switch (direction) {
                case UP:
                    return new DirectionLimiter(x, y, LIMITER_UP, Direction.UP);
                case LEFT:
                    return new DirectionLimiter(x, y, LIMITER_LEFT, Direction.LEFT);
                case RIGHT:
                    return new DirectionLimiter(x, y, LIMITER_RIGHT, Direction.RIGHT);
                case DOWN:
                    return new DirectionLimiter(x, y, LIMITER_DOWN, Direction.DOWN);
                default:
                    throw new IllegalArgumentException("Direction " + direction + " is not supported by direction limiters");
            }
        }
        if (directions.length == 2) {
            ArrayList<Direction> directionList = Lists.newArrayList(directions);
            if (directionList.contains(Direction.UP) && directionList.contains(Direction.DOWN)) {
                return new DirectionLimiter(x, y, LIMITER_VERTICAL, directions);
            }
            if (directionList.contains(Direction.LEFT) && directionList.contains(Direction.RIGHT)) {
                return new DirectionLimiter(x, y, LIMITER_HORIZONTAL, directions);
            }
            throw new IllegalArgumentException("Directions [" + directions[0] + ", " + directions[1] + "] are not supported by direction limiters");
        }
        throw new IllegalArgumentException("A direction limiter can not have more than 2 permitted directions");
    }

    private DirectionLimiter(int x, int y, Element element, Direction... directions) {
        super(element, x, y);
        this.permittedEntryDirections = Lists.newArrayList(directions);
    }

    public DirectionLimiter(DirectionLimiter another) {
        super(another);
        this.permittedEntryDirections = Lists.newArrayList(another.permittedEntryDirections);
    }

    public Point getPosition() {
        return new PointImpl(x, y);
    }

    public boolean canEnterFrom(Point point) {
        return permittedEntryDirections.stream()
                .map(d -> d.change(this.getPosition()))
                .anyMatch(p -> p.getX() == point.getX() && p.getY() == point.getY());
    }
}
