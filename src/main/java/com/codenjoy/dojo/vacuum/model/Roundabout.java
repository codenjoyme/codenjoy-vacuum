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

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;


// TODO make it able to treat more than 2 directions
public class Roundabout extends PointImpl {

    private final EntryLimiter limiter;

    public Roundabout(Point pt, Direction... directions) {
        super(pt);
        this.limiter = new EntryLimiter(pt, Arrays.asList(directions));
    }

    public boolean canEnterFrom(Point pt) {
        return limiter.canEnterFrom(pt);
    }

    public Direction enterFrom(Point pt) {
        List<Direction> entries = limiter.permitted();
        if (entries.size() > 2) {
            throw new IllegalStateException("Roundabout can treat exactly 2 directions");
        }
        Direction exit;
        if (entries.get(0).change(this).equals(pt)) {
            exit = entries.get(1);
        } else if (entries.get(1).change(this).equals(pt)) {
            exit = entries.get(0);
        } else {
            throw new IllegalArgumentException("Entry from point " + pt + " is prohibited");
        }
        rotate();
        return exit;
    }

    private void rotate() {
        rotate(false);
    }

    private void rotate(boolean counter) {
        List<Direction> rotated = limiter.permitted().stream()
                .map(e -> counter ? e.counterClockwise() : e.clockwise())
                .collect(toList());
        limiter.permitted(rotated);
    }

    public List<Direction> directions() {
        return limiter.permitted();
    }
}
