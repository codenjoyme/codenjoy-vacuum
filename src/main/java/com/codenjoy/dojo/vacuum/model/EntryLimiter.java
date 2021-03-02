package com.codenjoy.dojo.vacuum.model;

import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.PointImpl;
import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        return new PointImpl(x, y);
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
