package com.codenjoy.dojo.vacuum.model;

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

import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.vacuum.model.items.*;

import java.util.List;
import java.util.Optional;

public class GameBoard {

    private final int size;
    private final Start start;
    private final List<Barrier> barriers;
    private final List<Dust> dust;
    private final List<DirectionSwitcher> switchers;
    private final List<DirectionLimiter> limiters;

    public GameBoard(
            int size,
            Start start,
            List<Barrier> barriers,
            List<Dust> dust,
            List<DirectionSwitcher> switchers,
            List<DirectionLimiter> limiters
    ) {
        this.size = size;
        this.start = start;
        this.barriers = barriers;
        this.dust = dust;
        this.switchers = switchers;
        this.limiters = limiters;
    }

    public int getSize() {
        return size;
    }

    public Start getStart() {
        return start;
    }

    public List<Barrier> getBarriers() {
        return barriers;
    }

    public List<Dust> getDust() {
        return dust;
    }

    public boolean isInBounds(int x, int y) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }

    public boolean isBarrier(int x, int y) {
        return barriers.stream().anyMatch(b -> b.getX() == x && b.getY() == y);
    }

    public boolean isAllClear() {
        return dust.isEmpty();
    }

    public Optional<DirectionSwitcher> getDirectionSwitcher(Point point) {
        return switchers.stream()
                .filter(s -> s.getX() == point.getX() && s.getY() == point.getY())
                .findFirst();
    }

    public List<DirectionSwitcher> getDirectionSwitchers() {
        return switchers;
    }

    public boolean isCleanPoint(Point point) {
        return !start.equals(point)
                && !barriers.contains(point)
                && !dust.contains(point)
                && !switchers.contains(point)
                && !limiters.contains(point);
    }

    public boolean isDust(Point point) {
        return dust.stream().anyMatch(d -> d.equals(point));
    }

    public void removeDust(Point point) {
        Dust dustCell = dust.stream()
                .filter(d -> d.equals(point))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no dust at point: " + point));
        dust.remove(dustCell);
    }

    public Optional<DirectionLimiter> getDirectionLimiter(Point point) {
        return limiters.stream()
                .filter(l -> l.equals(point))
                .findFirst();
    }

    public List<DirectionLimiter> getDirectionLimiters() {
        return limiters;
    }
}
