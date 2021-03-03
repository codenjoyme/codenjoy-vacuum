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
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.codenjoy.dojo.services.Direction.*;
import static com.codenjoy.dojo.vacuum.model.Elements.*;

public class EntryLimiter extends AbstractItem {

    private static final Map<Elements, List<Direction>> elements =
            new HashMap<>(){{
                put(LIMITER_LEFT, Arrays.asList(LEFT));
                put(LIMITER_UP, Arrays.asList(UP));
                put(LIMITER_RIGHT, Arrays.asList(RIGHT));
                put(LIMITER_DOWN, Arrays.asList(DOWN));
                put(LIMITER_HORIZONTAL, Arrays.asList(LEFT, RIGHT));
                put(LIMITER_VERTICAL, Arrays.asList(UP, DOWN));
            }};

    private List<Direction> permitted;

    public EntryLimiter(Point pt, Elements element) {
        super(pt, element);
        permitted = elements.get(element);
    }

    public boolean canEnterFrom(Point from) {
        return checkEnter(permitted, from, this);
    }

    public static boolean checkEnter(List<Direction> permitted, Point from, Point to) {
        return permitted.stream()
                .map(direction -> direction.change(to))
                .anyMatch(pt -> pt.equals(from));
    }
}
