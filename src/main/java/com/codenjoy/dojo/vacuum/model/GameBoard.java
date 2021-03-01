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

import com.codenjoy.dojo.vacuum.model.items.Barrier;
import com.codenjoy.dojo.vacuum.model.items.Dust;
import com.codenjoy.dojo.vacuum.model.items.Start;

import java.util.List;

public class GameBoard {

    private final int size;
    private final Start start;
    private final List<Barrier> barriers;
    private final List<Dust> dust;

    public GameBoard(int size, Start start, List<Barrier> barriers, List<Dust> dust) {
        this.size = size;
        this.start = start;
        this.barriers = barriers;
        this.dust = dust;
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

    public boolean isFreeToGo(int x, int y) {
        return barriers.stream().anyMatch(b -> b.getX() == x && b.getY() == y);
    }

    public boolean tryClean(int x, int y) {
        Dust dustCell = dust.stream()
                .filter(d -> d.getX() == x && d.getY() == y)
                .findFirst()
                .orElse(null);
        if (dustCell == null) {
            return false;
        }
        return dust.remove(dustCell);
    }

    public boolean isAllClear() {
        return dust.isEmpty();
    }
}
