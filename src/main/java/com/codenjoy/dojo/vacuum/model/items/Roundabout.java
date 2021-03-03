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

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Roundabout extends AbstractItem {

    public static final int MAX_DIRECTIONS = 2;

    private List<Direction> permitted;

    public boolean canEnterFrom(Point from) {
        return EntryLimiter.checkEnter(permitted, from, this);
    }

    public Direction enterFrom(Point pt) {
        if (permitted.size() > MAX_DIRECTIONS) {
            throw new IllegalStateException("Roundabout can treat exactly 2 directions");
        }
        Direction result = direction(pt);
        permitted = rotate();
        element = parse(permitted);
        return result;
    }

    public Direction direction(Point pt) {
        if (from().change(this).equals(pt)) {
            return to();
        }

        if (to().change(this).equals(pt)) {
            return from();
        }

        throw new IllegalArgumentException("Entry from point " + pt + " is prohibited");
    }

    public Direction to() {
        return permitted.get(1);
    }

    public Direction from() {
        return permitted.get(0);
    }

    private List<Direction> rotate() {
        return permitted.stream()
                .map(Direction::clockwise)
                .collect(toList());
    }

    private static Elements parse(List<Direction> directions) {
        if (directions.size() != MAX_DIRECTIONS) {
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

    public Roundabout(Point pt, Elements element) {
        super(pt, element);

        switch (element) {
            case ROUNDABOUT_LEFT_UP:
                permitted = Arrays.asList(Direction.LEFT, Direction.UP);
                break;
            case ROUNDABOUT_UP_RIGHT:
                permitted = Arrays.asList(Direction.UP, Direction.RIGHT);
                break;
            case ROUNDABOUT_RIGHT_DOWN:
                permitted = Arrays.asList(Direction.RIGHT, Direction.DOWN);
                break;
            case ROUNDABOUT_DOWN_LEFT:
                permitted = Arrays.asList(Direction.DOWN, Direction.LEFT);
                break;
            default:
                throw new IllegalArgumentException("Element " + element + " is not supported");
        }
    }
}
