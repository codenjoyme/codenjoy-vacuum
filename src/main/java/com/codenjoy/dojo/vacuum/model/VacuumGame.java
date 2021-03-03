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
import com.codenjoy.dojo.vacuum.model.items.EntryLimiterItem;
import com.codenjoy.dojo.vacuum.model.items.DirectionSwitcherItem;
import com.codenjoy.dojo.vacuum.model.items.RoundaboutItem;
import com.codenjoy.dojo.vacuum.model.level.Level;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * О! Это самое сердце игры - борда, на которой все происходит.
 * Если какой-то из жителей борды вдруг захочет узнать что-то у нее, то лучше ему дать интефейс {@see Field}
 * Борда реализует интерфейс {@see Tickable} чтобы быть уведомленной о каждом тике игры. Обрати внимание на {Sample#tick()}
 */
public class VacuumGame implements Field {

    private final List<Player> players = new LinkedList<>();
    private Level level;
    private GameBoard board;

    public VacuumGame(Level level) {
        this.level = level;
        this.board = level.newBoard();
    }

    @Override
    public void tick() {
        players.forEach(player -> {
            var hero = player.getHero();
            hero.tick();
            hero.getEvents().forEach(player::event);
        });
    }

    @Override
    public boolean isBarrier(Point destination) {
        int x = destination.getX();
        int y = destination.getY();
        return board.isInBounds(x, y) && board.isBarrier(x, y);
    }

    @Override
    public Point getStart() {
        return board.getStart();
    }

    @Override
    public boolean isAllClear() {
        return board.isAllClear();
    }

    @Override
    public int getSize() {
        return board.getSize();
    }

    @Override
    public Optional<DirectionSwitcherItem> getDirectionSwitcher(Point point) {
        return board.getDirectionSwitcher(point);
    }

    @Override
    public boolean isCleanPoint(Point point) {
        return board.isCleanPoint(point);
    }

    @Override
    public boolean isDust(Point point) {
        return board.isDust(point);
    }

    @Override
    public void removeDust(Point point) {
        board.removeDust(point);
    }

    @Override
    public Optional<EntryLimiterItem> getDirectionLimiter(Point point) {
        return board.getDirectionLimiter(point);
    }

    @Override
    public Optional<RoundaboutItem> getRoundabout(Point destination) {
        return board.getRoundabouts().stream()
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
        board = level.newBoard();
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
                return board.getSize();
            }

            @Override
            public Iterable<? extends Point> elements() {
                return new LinkedList<>() {{
                    addAll(getHeroes());
                    addAll(board.getBarriers());
                    addAll(board.getDust());
                    addAll(board.getDirectionSwitchers());
                    addAll(board.getDirectionLimiters());
                    addAll(board.getRoundabouts());
                    add(board.getStart());
                }};
            }
        };
    }
}
