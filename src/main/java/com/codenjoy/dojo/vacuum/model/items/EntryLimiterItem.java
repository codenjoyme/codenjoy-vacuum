package com.codenjoy.dojo.vacuum.model.items;

import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.vacuum.model.Element;
import com.codenjoy.dojo.vacuum.model.EntryLimiter;
import com.google.common.collect.Lists;

import java.util.ArrayList;

import static com.codenjoy.dojo.vacuum.model.Element.*;

public class EntryLimiterItem extends AbstractItem {
    private final EntryLimiter limiter;

    public static EntryLimiterItem create(int x, int y, Direction... directions) {
        if (directions.length == 1) {
            Direction direction = directions[0];
            switch (direction) {
                case UP:
                    return new EntryLimiterItem(x, y, LIMITER_UP, Direction.UP);
                case LEFT:
                    return new EntryLimiterItem(x, y, LIMITER_LEFT, Direction.LEFT);
                case RIGHT:
                    return new EntryLimiterItem(x, y, LIMITER_RIGHT, Direction.RIGHT);
                case DOWN:
                    return new EntryLimiterItem(x, y, LIMITER_DOWN, Direction.DOWN);
                default:
                    throw new IllegalArgumentException("Direction " + direction + " is not supported by direction limiters");
            }
        }
        if (directions.length == 2) {
            ArrayList<Direction> directionList = Lists.newArrayList(directions);
            if (directionList.contains(Direction.UP) && directionList.contains(Direction.DOWN)) {
                return new EntryLimiterItem(x, y, LIMITER_VERTICAL, directions);
            }
            if (directionList.contains(Direction.LEFT) && directionList.contains(Direction.RIGHT)) {
                return new EntryLimiterItem(x, y, LIMITER_HORIZONTAL, directions);
            }
            throw new IllegalArgumentException("Directions [" + directions[0] + ", " + directions[1] + "] are not supported by direction limiters");
        }
        throw new IllegalArgumentException("A direction limiter can not have more than 2 permitted directions");
    }

    private EntryLimiterItem(int x, int y, Element element, Direction... directions) {
        super(element, x, y);
        this.limiter = new EntryLimiter(x, y, directions);
    }

    public EntryLimiterItem(EntryLimiterItem another) {
        super(another);
        this.limiter = new EntryLimiter(another.limiter);
    }

    public boolean canEnterFrom(Point point) {
        return limiter.canEnterFrom(point);
    }
}
