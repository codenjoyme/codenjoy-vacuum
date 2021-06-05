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
import com.codenjoy.dojo.games.vacuum.Element;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.codenjoy.dojo.services.Direction.*;
import static com.codenjoy.dojo.games.vacuum.Element.*;
import static java.util.stream.Collectors.toList;

public class Roundabout extends AbstractItem {

    public static final int MAX_DIRECTIONS = 2;

    private static final Map<Element, List<Direction>> elements =
            new HashMap<>(){{
                put(ROUNDABOUT_LEFT_UP, Arrays.asList(LEFT, UP));
                put(ROUNDABOUT_UP_RIGHT, Arrays.asList(UP, RIGHT));
                put(ROUNDABOUT_RIGHT_DOWN, Arrays.asList(RIGHT, DOWN));
                put(ROUNDABOUT_DOWN_LEFT, Arrays.asList(DOWN, LEFT));
            }};

    private List<Direction> permitted;

    public Roundabout(Point pt, Element element) {
        super(pt, element);
        permitted = elements.get(element);
        if (permitted == null) {
            throw new IllegalArgumentException("Element " + element + " is not supported");
        }
    }

    public boolean canEnterFrom(Point pt) {
        return EntryLimiter.checkEnter(permitted, pt, this);
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

    private static Element parse(List<Direction> directions) {
        if (directions.size() != MAX_DIRECTIONS) {
            throw new IllegalArgumentException("Roundabout should treat exactly 2 " +
                    "directions but " + directions.size() + " received");
        }

        return elements.entrySet().stream()
                .filter(entry -> entry.getValue().containsAll(directions))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Roundabout with direction ["
                        + directions.get(0) + ", " + directions.get(1) + "] is not supported"));
    }
}