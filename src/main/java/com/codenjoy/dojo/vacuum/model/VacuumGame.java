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


import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.printer.BoardReader;
import com.codenjoy.dojo.vacuum.model.items.*;
import com.codenjoy.dojo.vacuum.model.level.Level;
import com.codenjoy.dojo.vacuum.services.GameSettings;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

public class VacuumGame implements Field {

    private final List<Player> players = new LinkedList<>();
    private final Level level;
    private int size;
    private Start start;
    private List<Barrier> barriers;
    private List<Dust> dust;
    private List<DirectionSwitcherItem> switchers;
    private List<EntryLimiterItem> limiters;
    private List<RoundaboutItem> roundabouts;

    private GameSettings settings;

    public VacuumGame(Level level, GameSettings settings) {
        this.settings = settings;
        this.level = level;
        reset();
    }

    public void reset() {
        size = level.size();
        start = level.start();
        barriers = level.barriers();
        dust = level.dust();
        switchers = level.switchers();
        limiters = level.limiters();
        roundabouts = level.roundabouts();
    }

    @Override
    public void tick() {
        players.forEach(player -> {
            var hero = player.getHero();
            hero.tick();
            hero.getEvents().forEach(player::event);
        });
    }

    public boolean isInBounds(int x, int y) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }

    public boolean isBarrier(int x, int y) {
        return barriers.stream()
                .anyMatch(b -> b.getX() == x && b.getY() == y);
    }

    @Override
    public boolean isAllClear() {
        return dust.isEmpty();
    }

    @Override
    public Optional<DirectionSwitcherItem> getDirectionSwitcher(Point point) {
        return switchers.stream()
                .filter(s -> s.getX() == point.getX() && s.getY() == point.getY())
                .findFirst();
    }

    @Override
    public boolean isCleanPoint(Point point) {
        return !start.equals(point)
                && !barriers.contains(point)
                && !dust.contains(point)
                && !switchers.contains(point)
                && !limiters.contains(point)
                && !roundabouts.contains(point);
    }

    @Override
    public boolean isDust(Point point) {
        return dust.stream()
                .anyMatch(d -> d.equals(point));
    }

    @Override
    public void removeDust(Point point) {
        Dust dustCell = dust.stream()
                .filter(d -> d.equals(point))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("There is no dust at point: " + point));
        dust.remove(dustCell);
    }

    @Override
    public Optional<EntryLimiterItem> getDirectionLimiter(Point point) {
        return limiters.stream()
                .filter(l -> l.equals(point))
                .findFirst();
    }

    @Override
    public Point getStart() {
        return start;
    }

    @Override
    public boolean isBarrier(Point destination) {
        int x = destination.getX();
        int y = destination.getY();
        return isInBounds(x, y) && isBarrier(x, y);
    }

    public int getSize() {
        return size;
    }

    @Override
    public Optional<RoundaboutItem> getRoundabout(Point destination) {
        return roundabouts.stream()
                .filter(r -> r.equals(destination)).findFirst();
    }

    public List<Hero> getHeroes() {
        return players.stream()
                .map(Player::getHero)
                .collect(toList());
    }

    @Override
    public void newGame(Player player) {
        if (!players.contains(player)) {
            players.add(player);
        }
        reset();
        player.newHero(this);
    }

    @Override
    public void remove(Player player) {
        players.remove(player);
    }

    @Override
    public BoardReader reader() {
        return new BoardReader() {

            @Override
            public int size() {
                return VacuumGame.this.getSize();
            }

            @Override
            public Iterable<? extends Point> elements() {
                return new LinkedList<>() {{
                    addAll(getHeroes());
                    addAll(VacuumGame.this.barriers);
                    addAll(VacuumGame.this.dust);
                    addAll(VacuumGame.this.switchers);
                    addAll(VacuumGame.this.limiters);
                    addAll(VacuumGame.this.roundabouts);
                    add(VacuumGame.this.start);
                }};
            }
        };
    }

    @Override
    public GameSettings settings() {
        return settings;
    }
}
