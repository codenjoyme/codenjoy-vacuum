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
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.vacuum.model.GameBoard;
import com.codenjoy.dojo.vacuum.model.items.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.codenjoy.dojo.utils.LevelUtils.getObjects;
import static com.codenjoy.dojo.vacuum.model.Elements.*;

public class Level {

    private final int size;
    private final Start start;
    private final List<Barrier> barriers;
    private final List<Dust> dust;
    private final List<DirectionSwitcherItem> switchers;
    private final List<EntryLimiterItem> limiters;
    private final List<RoundaboutItem> roundabouts;

    private Level(
            int size,
            Start start,
            List<Barrier> barriers,
            List<Dust> dust,
            List<DirectionSwitcherItem> switchers,
            List<EntryLimiterItem> limiters,
            List<RoundaboutItem> roundabouts
    ) {
        this.size = size;
        this.start = start;
        this.barriers = Collections.unmodifiableList(barriers);
        this.dust = Collections.unmodifiableList(dust);
        this.switchers = Collections.unmodifiableList(switchers);
        this.limiters = Collections.unmodifiableList(limiters);
        this.roundabouts = Collections.unmodifiableList(roundabouts);
    }

    public GameBoard newBoard() {
        return new GameBoard(
                size,
                new Start(start),
                barriers.stream()
                        .map(Barrier::new)
                        .collect(Collectors.toList()),
                dust.stream()
                        .map(Dust::new)
                        .collect(Collectors.toList()),
                new ArrayList<>(switchers),
                new ArrayList<>(limiters),
                new ArrayList<>(roundabouts)
        );
    }

    public static Level generate(String map) {
        double sqrt = Math.sqrt(map.length());
        if (sqrt - Math.floor(sqrt) != 0) {
            throw new IllegalArgumentException("Map should be square");
        }
        int size = (int) sqrt;
        LengthToXY mapper = new LengthToXY(size);

        Start start = getObjects(mapper, map, Start::new, START).get(0);
        var barriers = getObjects(mapper, map, (Function<Point, Barrier>) Barrier::new, BARRIER);
        var dust = getObjects(mapper, map, (Function<Point, Dust>) Dust::new, DUST);

        var switchers = getObjects(mapper, map, DirectionSwitcherItem::new,
                SWITCH_LEFT, SWITCH_UP, SWITCH_RIGHT, SWITCH_DOWN);

        var limiters = getObjects(mapper, map, EntryLimiterItem::new,
                LIMITER_LEFT, LIMITER_UP, LIMITER_RIGHT, LIMITER_DOWN, LIMITER_HORIZONTAL, LIMITER_VERTICAL);

        var roundabouts = getObjects(mapper, map, RoundaboutItem::new,
                ROUNDABOUT_LEFT_UP, ROUNDABOUT_UP_RIGHT,  ROUNDABOUT_RIGHT_DOWN, ROUNDABOUT_DOWN_LEFT);

        return new Level(size, start, barriers, dust, switchers, limiters, roundabouts);
    }
}
