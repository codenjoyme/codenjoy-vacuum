package com.codenjoy.dojo.vacuum.model.level;

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

import com.codenjoy.dojo.services.LengthToXY;
import com.codenjoy.dojo.vacuum.model.items.*;

import java.util.List;

import static com.codenjoy.dojo.utils.LevelUtils.getObjects;
import static com.codenjoy.dojo.vacuum.model.Elements.*;

public class Level {

    private String map;
    private LengthToXY xy;

    public Level(String map) {
        this.map = map;
        xy = new LengthToXY(size());
    }

    public int size() {
        return (int) Math.sqrt(map.length());
    }

    public List<Roundabout> roundabouts() {
        return getObjects(xy, map,
                Roundabout::new,
                ROUNDABOUT_LEFT_UP,
                ROUNDABOUT_UP_RIGHT,
                ROUNDABOUT_RIGHT_DOWN,
                ROUNDABOUT_DOWN_LEFT);
    }

    public List<EntryLimiter> limiters() {
        return getObjects(xy, map,
                EntryLimiter::new,
                LIMITER_LEFT,
                LIMITER_UP,
                LIMITER_RIGHT,
                LIMITER_DOWN,
                LIMITER_HORIZONTAL,
                LIMITER_VERTICAL);
    }

    public List<DirectionSwitcher> switchers() {
        return getObjects(xy, map,
                DirectionSwitcher::new,
                SWITCH_LEFT,
                SWITCH_UP,
                SWITCH_RIGHT,
                SWITCH_DOWN);
    }

    public List<Dust> dust() {
        return getObjects(xy, map,
                Dust::new,
                DUST);
    }

    public List<Barrier> barriers() {
        return getObjects(xy, map,
                Barrier::new,
                BARRIER);
    }

    public Start start() {
        return getObjects(xy, map,
                Start::new,
                START).get(0);
    }
}