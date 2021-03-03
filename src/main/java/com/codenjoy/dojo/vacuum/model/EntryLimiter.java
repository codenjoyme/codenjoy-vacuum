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

import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;
import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.codenjoy.dojo.services.PointImpl.pt;

public class EntryLimiter {
    private final int x;
    private final int y;
    private Set<Direction> permittedEntries;

    public EntryLimiter(int x, int y, Iterable<Direction> directions) {
        this.x = x;
        this.y = y;
        this.permittedEntries = Sets.newHashSet(directions);
    }

    public EntryLimiter(int x, int y, Direction... directions) {
        this(x, y, Sets.newHashSet(directions));
    }

    public EntryLimiter(EntryLimiter another) {
        this(another.x, another.y, another.permittedEntries);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point getPosition() {
        return pt(x, y);
    }

    public void permitFrom(Direction direction) {
        permittedEntries.add(direction);
    }

    public void forbidFrom(Direction direction) {
        permittedEntries.remove(direction);
    }

    public List<Direction> getPermittedEntries() {
        return new ArrayList<>(permittedEntries);
    }

    public void setPermittedEntries(List<Direction> entries) {
        this.permittedEntries = new HashSet<>(entries);
    }

    public boolean canEnterFrom(Point point) {
        return permittedEntries.stream()
                .map(d -> d.change(this.getPosition()))
                .anyMatch(p -> p.equals(point));
    }
}
