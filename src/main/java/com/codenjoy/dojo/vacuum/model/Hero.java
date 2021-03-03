package com.codenjoy.dojo.vacuum.model;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 Codenjoy
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
import com.codenjoy.dojo.services.State;
import com.codenjoy.dojo.services.multiplayer.PlayerHero;
import com.codenjoy.dojo.vacuum.model.items.Roundabout;
import com.codenjoy.dojo.vacuum.services.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Hero extends PlayerHero<Field> implements State<Elements, Player> {
    private static final int RESTART_ACTION = 0;

    private Direction direction;
    private List<Event> events;
    private boolean reset = false;
    private boolean levelPassed = false;

    public Hero(Point xy) {
        super(xy);
    }

    @Override
    public void init(Field field) {
        this.field = field;
    }

    @Override
    public void down() {
        if (direction == null) {
            direction = Direction.DOWN;
        }
    }

    @Override
    public void up() {
        if (direction == null) {
            direction = Direction.UP;
        }
    }

    @Override
    public void left() {
        if (direction == null) {
            direction = Direction.LEFT;
        }
    }

    @Override
    public void right() {
        if (direction == null) {
            direction = Direction.RIGHT;
        }
    }

    @Override
    public void act(int... codes) {
        if (codes.length > 0 && codes[0] == RESTART_ACTION) {
            reset = true;
        }
    }

    @Override
    public void tick() {
        events = new ArrayList<>();

        if (direction != null) {
            goTo(direction.change(this.copy()));
        }

        if (field.isAllClear()) {
            events.add(Event.ALL_CLEAR);
            levelPassed = true;
        }
    }

    private void goTo(Point pt) {
        Boolean isNotEntryLimited = field.limiter(pt)
                .map(l -> l.canEnterFrom(this))
                .orElse(true);

        Optional<Roundabout> roundabout = field.roundabout(pt);

        isNotEntryLimited &= roundabout.map(r -> r.canEnterFrom(this))
                .orElse(true);

        if (field.isBarrier(pt) || !isNotEntryLimited) {
            direction = null;
            return;
        }

        roundabout.ifPresent(r -> this.direction = r.enterFrom(this));
        move(pt);

        field.switcher(pt)
                .ifPresent(s -> this.direction = s.direction());

        if (field.isCleanPoint(pt)) {
            events.add(Event.TIME_WASTED);
        }

        if (field.isDust(pt)) {
            field.removeDust(pt);
            events.add(Event.DUST_CLEANED);
        }

        Point next = direction.change(pt);
        if (field.isBarrier(next)) {
            direction = null;
        }

    }

    public List<Event> getEvents() {
        return events;
    }

    @Override
    public Elements state(Player player, Object... alsoAtPoint) {
        return Elements.VACUUM;
    }

    public boolean levelPassed() {
        return levelPassed;
    }

    public boolean reset() {
        return reset;
    }
}
