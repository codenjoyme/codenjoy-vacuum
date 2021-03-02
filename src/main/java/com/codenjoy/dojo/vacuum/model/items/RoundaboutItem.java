package com.codenjoy.dojo.vacuum.model.items;

import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.vacuum.model.Element;
import com.codenjoy.dojo.vacuum.model.Roundabout;
import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.List;

public class RoundaboutItem extends AbstractItem {
    private final Roundabout roundabout;

    public static RoundaboutItem create(int x, int y, Direction... directions) {
        ArrayList<Direction> directionsList = new ArrayList<>(Sets.newHashSet(directions));
        Element roundaboutType = elementByDirections(directionsList);
        return new RoundaboutItem(roundaboutType, x, y);
    }

    private static Element elementByDirections(List<Direction> directions) {
        if (directions.size() != 2) {
            throw new IllegalArgumentException("Roundabout should treat exactly 2 directions but " + directions.size() + " received");
        }

        if (directions.contains(Direction.LEFT) && directions.contains(Direction.UP)) {
            return Element.ROUNDABOUT_LEFT_UP;
        }
        if (directions.contains(Direction.UP) && directions.contains(Direction.RIGHT)) {
            return Element.ROUNDABOUT_UP_RIGHT;
        }
        if (directions.contains(Direction.RIGHT) && directions.contains(Direction.DOWN)) {
            return Element.ROUNDABOUT_RIGHT_DOWN;
        }
        if (directions.contains(Direction.DOWN) && directions.contains(Direction.LEFT)) {
            return Element.ROUNDABOUT_DOWN_LEFT;
        }

        throw new IllegalArgumentException("Roundabout with direction [" + directions.get(0) + ", " + directions.get(1) + "] is not supported");
    }

    private RoundaboutItem(Element element, int x, int y) {
        super(element, x, y);
        switch (element) {
            case ROUNDABOUT_LEFT_UP:
                this.roundabout = new Roundabout(x, y, Direction.LEFT, Direction.UP);
                break;
            case ROUNDABOUT_UP_RIGHT:
                this.roundabout = new Roundabout(x, y, Direction.UP, Direction.RIGHT);
                break;
            case ROUNDABOUT_RIGHT_DOWN:
                this.roundabout = new Roundabout(x, y, Direction.RIGHT, Direction.DOWN);
                break;
            case ROUNDABOUT_DOWN_LEFT:
                this.roundabout = new Roundabout(x, y, Direction.DOWN, Direction.LEFT);
                break;
            default:
                throw new IllegalArgumentException("Element " + element + " is not supported");
        }
    }

    public boolean canEnterFrom(Point point) {
        return roundabout.canEnterFrom(point);
    }

    public Direction enterFrom(Point point) {
        Direction exitDirection = roundabout.enterFrom(point);
        this.setElement(elementByDirections(roundabout.getDirections()));
        return exitDirection;
    }
}
