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

import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.vacuum.model.Element;
import com.codenjoy.dojo.vacuum.model.GameBoard;
import com.codenjoy.dojo.vacuum.model.items.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Level {

    private final int size;
    private final Start start;
    private final List<Barrier> barriers;
    private final List<Dust> dust;
    private final List<DirectionSwitcher> switchers;
    private final List<DirectionLimiter> limiters;

    private Level(
            int size,
            Start start,
            List<Barrier> barriers,
            List<Dust> dust,
            List<DirectionSwitcher> switchers,
            List<DirectionLimiter> limiters
    ) {
        this.size = size;
        this.start = start;
        this.barriers = Collections.unmodifiableList(barriers);
        this.dust = Collections.unmodifiableList(dust);
        this.switchers = Collections.unmodifiableList(switchers);
        this.limiters = Collections.unmodifiableList(limiters);
    }

    public GameBoard newBoard() {
        return new GameBoard(
                size,
                new Start(start),
                barriers.stream().map(Barrier::new).collect(Collectors.toList()),
                dust.stream().map(Dust::new).collect(Collectors.toList()),
                new ArrayList<>(switchers),
                new ArrayList<>(limiters)
        );
    }

    public static Level generate(String map) {
        double sqrt = Math.sqrt(map.length());
        if (sqrt - Math.floor(sqrt) != 0) {
            throw new IllegalArgumentException("Map should be square");
        }

        int size = (int) sqrt;

        Start start = null;
        var barriers = new ArrayList<Barrier>();
        var dust = new ArrayList<Dust>();
        var switchers = new ArrayList<DirectionSwitcher>();
        var limiters = new ArrayList<DirectionLimiter>();

        for (int i = 0; i < map.length(); i++) {
            char ch = map.charAt(i);
            int x = i % size;
            int y = size - 1 - i / size;
            switch (Element.byCode(ch)) {
                case START:
                    if (start != null) {
                        throw new IllegalArgumentException("Only one start point should be declared");
                    }
                    start = new Start(x, y);
                    break;
                case BARRIER:
                    barriers.add(new Barrier(x, y));
                    break;
                case DUST:
                    dust.add(new Dust(x, y));
                    break;
                case SWITCH_LEFT:
                    switchers.add(DirectionSwitcher.create(Direction.LEFT, x, y));
                    break;
                case SWITCH_RIGHT:
                    switchers.add(DirectionSwitcher.create(Direction.RIGHT, x, y));
                    break;
                case SWITCH_UP:
                    switchers.add(DirectionSwitcher.create(Direction.UP, x, y));
                    break;
                case SWITCH_DOWN:
                    switchers.add(DirectionSwitcher.create(Direction.DOWN, x, y));
                    break;
                case LIMITER_LEFT:
                    limiters.add(DirectionLimiter.create(x, y, Direction.LEFT));
                    break;
                case LIMITER_RIGHT:
                    limiters.add(DirectionLimiter.create(x, y, Direction.RIGHT));
                    break;
                case LIMITER_UP:
                    limiters.add(DirectionLimiter.create(x, y, Direction.UP));
                    break;
                case LIMITER_DOWN:
                    limiters.add(DirectionLimiter.create(x, y, Direction.DOWN));
                    break;
                case LIMITER_VERTICAL:
                    limiters.add(DirectionLimiter.create(x, y, Direction.UP, Direction.DOWN));
                    break;
                case LIMITER_HORIZONTAL:
                    limiters.add(DirectionLimiter.create(x, y, Direction.LEFT, Direction.RIGHT));
                    break;
                case NONE:
                    break;
                case VACUUM:
                    throw new IllegalArgumentException("Map should not contain hero declaration, only start point '" + Element.START.ch() + "'");
                default:
                    throw new IllegalArgumentException("Element with code: '" + ch + "' not supported");
            }
        }
        if (!isMapBorderedCorrectly(map)) {
            throw new IllegalArgumentException("Map should be surrounded by barriers '" + Element.BARRIER.ch() + "'");
        }
        if (start == null) {
            throw new IllegalArgumentException("Start point '" + Element.START.ch() + "' should be declared on map");
        }
        if (!isMapPassable(map)) {
            throw new IllegalArgumentException("There is no way to pass this level");
        }
        return new Level(size, start, barriers, dust, switchers, limiters);
    }

    private static boolean isMapBorderedCorrectly(String map) {
        int size = (int) Math.sqrt(map.length());
        return map.matches(createBorderedMapRegex(size));
    }

    private static String createBorderedMapRegex(int size) {
        return String.format("^#{%d}", size) +
                String.valueOf(String.format("#.{%d}#", size - 2)).repeat(Math.max(0, size - 2)) +
                String.format("#{%d}$", size);
    }

    private static boolean isMapPassable(String map) {
        // TODO: Implement this
        return true;
    }

}
