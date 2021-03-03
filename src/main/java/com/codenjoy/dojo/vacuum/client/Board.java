package com.codenjoy.dojo.vacuum.client;

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


import com.codenjoy.dojo.client.AbstractBoard;
import com.codenjoy.dojo.vacuum.model.Elements;
import com.codenjoy.dojo.services.Point;

import java.util.List;

import static com.codenjoy.dojo.vacuum.model.Elements.*;

/**
 * Класс, обрабатывающий строковое представление доски.
 * Содержит ряд унаследованных методов {@see AbstractBoard},
 * но ты можешь добавить сюда любые свои методы на их основе.
 */
public class Board extends AbstractBoard<Elements> {

    @Override
    protected int inversionY(int y) {
        return size() - 1 - y;
    }

    @Override
    public Elements valueOf(char ch) {
        return Elements.byCode(ch);
    }

    public Elements getAt(int x, int y) {
        if (isOutOfField(x, y)) {
            return BARRIER;
        }
        return super.getAt(x, y);
    }

    public boolean isBarrierAt(int x, int y) {
        return isAt(x, y, BARRIER);
    }

    public boolean isBarrierAt(Point point) {
        return isAt(point, BARRIER);
    }

    @Override
    public String toString() {
        return String.format("%s\n" +
                        "Vacuum cleaner at: %s\n" +
                        "Start point at: %s\n" +
                        "Barriers at: %s\n" +
                        "Dust at: %s\n" +
                        "None: %s\n" +
                        "Roundabouts: %s\n" +
                        "Limiters: %s\n" +
                        "Switches: %s",
                boardAsString(),
                getMe(),
                getStartPoint(),
                getBarriers(),
                getDust(),
                getNone(),
                getRoundabouts(),
                getLimiters(),
                getSwitches());
    }

    public Point getMe() {
        return get(Elements.VACUUM).get(0);
    }

    public Point getStartPoint() {
        List<Point> points = get(Elements.START);
        if (!points.isEmpty()) {
            return points.get(0);
        }
        return getMe();
    }

    public List<Point> getBarriers() {
        return get(BARRIER);
    }

    public List<Point> getDust() {
        return get(DUST);
    }

    public List<Point> getNone() {
        return get(NONE);
    }

    public List<Point> getRoundabouts() {
        return get(ROUNDABOUT_LEFT_UP,
                ROUNDABOUT_UP_RIGHT,
                ROUNDABOUT_RIGHT_DOWN,
                ROUNDABOUT_DOWN_LEFT);
    }

    public List<Point> getLimiters() {
        return get(LIMITER_LEFT,
                LIMITER_RIGHT,
                LIMITER_UP,
                LIMITER_DOWN,
                LIMITER_VERTICAL,
                LIMITER_HORIZONTAL);
    }

    public List<Point> getSwitches() {
        return get(SWITCH_LEFT,
                SWITCH_RIGHT,
                SWITCH_DOWN,
                SWITCH_UP);
    }

}
