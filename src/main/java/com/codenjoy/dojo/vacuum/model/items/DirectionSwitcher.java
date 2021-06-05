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

import java.util.HashMap;
import java.util.Map;

import static com.codenjoy.dojo.services.Direction.*;
import static com.codenjoy.dojo.games.vacuum.Element.*;

public class DirectionSwitcher extends AbstractItem {

    private static final Map<Element, Direction> elements =
            new HashMap<>(){{
                put(SWITCH_LEFT, LEFT);
                put(SWITCH_UP, UP);
                put(SWITCH_RIGHT, RIGHT);
                put(SWITCH_DOWN, DOWN);
            }};

    private Direction direction;

    public DirectionSwitcher(Point pt, Element element) {
        super(pt, element);
        direction = elements.get(element);
    }

    public Direction direction() {
        return direction;
    }
}
