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
import com.codenjoy.dojo.vacuum.model.DirectionSwitcher;
import com.codenjoy.dojo.vacuum.model.Elements;

public class DirectionSwitcherItem extends AbstractItem {
    private final DirectionSwitcher switcher;

    public static DirectionSwitcherItem create(Direction direction, int x, int y) {
        Elements element;
        switch (direction) {
            case LEFT:
                element = Elements.SWITCH_LEFT;
                break;
            case RIGHT:
                element = Elements.SWITCH_RIGHT;
                break;
            case UP:
                element = Elements.SWITCH_UP;
                break;
            case DOWN:
                element = Elements.SWITCH_DOWN;
                break;
            default:
                throw new IllegalArgumentException("Direction " + direction + " is not supported by direction switchers");
        }
        return new DirectionSwitcherItem(element, direction, x, y);
    }

    private DirectionSwitcherItem(Elements element, Direction direction, int x, int y) {
        super(element, x, y);
        this.switcher = new DirectionSwitcher(x, y, direction);
    }

    public DirectionSwitcherItem(DirectionSwitcherItem another) {
        super(another);
        this.switcher = new DirectionSwitcher(another.switcher);
    }

    public Direction getDirection() {
        return switcher.getDirection();
    }
}
