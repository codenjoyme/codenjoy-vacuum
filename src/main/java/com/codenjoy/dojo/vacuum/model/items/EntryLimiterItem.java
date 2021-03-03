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
import com.codenjoy.dojo.vacuum.model.EntryLimiter;
import com.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.codenjoy.dojo.services.Direction.*;
import static com.codenjoy.dojo.vacuum.model.Elements.*;

public class EntryLimiterItem extends AbstractItem {
    private static final Map<Elements, List<Direction>> elementsToDirections = new HashMap<>();
    static {
        elementsToDirections.put(LIMITER_LEFT, Lists.newArrayList(LEFT));
        elementsToDirections.put(LIMITER_UP, Lists.newArrayList(UP));
        elementsToDirections.put(LIMITER_RIGHT, Lists.newArrayList(RIGHT));
        elementsToDirections.put(LIMITER_DOWN, Lists.newArrayList(DOWN));
        elementsToDirections.put(LIMITER_HORIZONTAL, Lists.newArrayList(LEFT, RIGHT));
        elementsToDirections.put(LIMITER_VERTICAL, Lists.newArrayList(UP, DOWN));
    }

    private final EntryLimiter limiter;

    private EntryLimiterItem(Elements element, int x, int y) {
        super(element, x, y);
        this.limiter = new EntryLimiter(x, y, elementsToDirections.get(element));
    }

    public EntryLimiterItem(Point point, Elements element) {
        this(element, point.getX(), point.getY());
    }

    public boolean canEnterFrom(Point point) {
        return limiter.canEnterFrom(point);
    }
}
