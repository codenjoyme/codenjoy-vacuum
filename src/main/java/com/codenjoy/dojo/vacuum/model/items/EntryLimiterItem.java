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
