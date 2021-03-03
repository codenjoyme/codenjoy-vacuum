package com.codenjoy.dojo.vacuum.model.items;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 - 2021 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.vacuum.model.Elements;
import com.codenjoy.dojo.vacuum.model.Roundabout;

import java.util.List;

public class RoundaboutItem extends AbstractItem {
    private final Roundabout roundabout;

    private static Elements elementByDirections(List<Direction> directions) {
        if (directions.size() != 2) {
            throw new IllegalArgumentException("Roundabout should treat exactly 2 directions but " + directions.size() + " received");
        }

        if (directions.contains(Direction.LEFT) && directions.contains(Direction.UP)) {
            return Elements.ROUNDABOUT_LEFT_UP;
        }
        if (directions.contains(Direction.UP) && directions.contains(Direction.RIGHT)) {
            return Elements.ROUNDABOUT_UP_RIGHT;
        }
        if (directions.contains(Direction.RIGHT) && directions.contains(Direction.DOWN)) {
            return Elements.ROUNDABOUT_RIGHT_DOWN;
        }
        if (directions.contains(Direction.DOWN) && directions.contains(Direction.LEFT)) {
            return Elements.ROUNDABOUT_DOWN_LEFT;
        }

        throw new IllegalArgumentException("Roundabout with direction [" + directions.get(0) + ", " + directions.get(1) + "] is not supported");
    }

    private RoundaboutItem(Elements element, int x, int y) {
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

    public RoundaboutItem(Point point, Elements element) {
        this(element, point.getX(), point.getY());
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
