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
import com.codenjoy.dojo.vacuum.model.DirectionSwitcher;
import com.codenjoy.dojo.vacuum.model.Elements;

import java.util.HashMap;
import java.util.Map;

import static com.codenjoy.dojo.services.Direction.*;
import static com.codenjoy.dojo.vacuum.model.Elements.*;

public class DirectionSwitcherItem extends AbstractItem {

    private static final Map<Elements, Direction> elements =
            new HashMap<>(){{
                put(SWITCH_LEFT, LEFT);
                put(SWITCH_UP, UP);
                put(SWITCH_RIGHT, RIGHT);
                put(SWITCH_DOWN, DOWN);
            }};

    private final DirectionSwitcher switcher;

    public DirectionSwitcherItem(Point pt, Elements element) {
        super(pt, element);
        this.switcher = new DirectionSwitcher(pt, elements.get(element));
    }

    public Direction direction() {
        return switcher.direction();
    }
}
