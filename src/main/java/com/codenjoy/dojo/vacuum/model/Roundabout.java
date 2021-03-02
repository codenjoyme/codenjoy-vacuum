package com.codenjoy.dojo.vacuum.model;

/*-
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
import com.codenjoy.dojo.services.PointImpl;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.stream.Collectors;


// TODO make it able to treat more than 2 directions
public class Roundabout {
    private final EntryLimiter limiter;
    private final int x;
    private final int y;

    public Roundabout(int x, int y, Iterable<Direction> directions) {
        this.x = x;
        this.y = y;
        this.limiter = new EntryLimiter(x, y, directions);
    }

    public Roundabout(int x, int y, Direction... directions) {
        this(x, y, Sets.newHashSet(directions));
    }

    public Roundabout(Roundabout another) {
        this(another.x, another.y, another.limiter.getPermittedEntries());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point getPosition() {
        return new PointImpl(x, y);
    }

    public boolean canEnterFrom(Point point) {
        return limiter.canEnterFrom(point);
    }

    public Direction enterFrom(Point point) {
        List<Direction> entries = limiter.getPermittedEntries();
        Point position = getPosition();
        if (entries.size() > 2) {
            throw new IllegalStateException("Roundabout can treat exactly 2 directions");
        }
        Direction exitDirection = null;
        if (entries.get(0).change(position).equals(point)) {
            exitDirection = entries.get(1);
        } else if (entries.get(1).change(position).equals(point)) {
            exitDirection = entries.get(0);
        } else {
            throw new IllegalArgumentException("Entry from point " + point + " is prohibited");
        }
        rotate();
        return exitDirection;
    }

    private void rotate() {
        rotate(false);
    }

    private void rotate(boolean counterClockwise) {
        List<Direction> rotatedEntries = limiter.getPermittedEntries().stream()
                .map(e -> counterClockwise ? e.counterClockwise() : e.clockwise())
                .collect(Collectors.toList());
        limiter.setPermittedEntries(rotatedEntries);
    }

    public List<Direction> getDirections() {
        return limiter.getPermittedEntries();
    }
}
