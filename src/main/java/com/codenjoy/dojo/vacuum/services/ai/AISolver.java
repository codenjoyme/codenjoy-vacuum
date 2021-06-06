package com.codenjoy.dojo.vacuum.services.ai;

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


import com.codenjoy.dojo.client.Solver;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.algs.DeikstraFindWay;
import com.codenjoy.dojo.games.vacuum.Board;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AISolver implements Solver<Board> {

    private DeikstraFindWay way;
    private Dice dice;

    public AISolver(Dice dice) {
        this.dice = dice;
        this.way = new DeikstraFindWay();
    }

    public DeikstraFindWay.Possible possible(final Board board) {
        return new DeikstraFindWay.Possible() {
            @Override
            public boolean possible(Point pt) {
                if (board.isBarrierAt(pt)) return false;
                return true;
            }
        };
    }

    @Override
    public String get(final Board board) {
        List<Direction> result = getDirections(board);
        if (result.isEmpty()) return "";
        return result.get(0).toString() + getBombIfNeeded(board);
    }

    private String getBombIfNeeded(Board board) {
        Point me = board.getMe();
        if (me.getX() % 2 == 0 && me.getY() % 2 == 0) {
            return ", ACT";
        } else {
            return "";
        }
    }

    public List<Direction> getDirections(Board board) {
        int size = board.size();
        if (bombsNear(board)) {
            return Arrays.asList(Direction.random(dice));
        }

        Point from = board.getMe();
        DeikstraFindWay.Possible map = possible(board);
        return way.getShortestWay(size, from, Collections.singletonList(from), map);
    }

    // TODO fix Deikstra find way
    private boolean bombsNear(Board board) {
//        Point me = board.getMe();
//        Point atLeft = Direction.LEFT.change(me);
//        Point atRight = Direction.RIGHT.change(me);
//        Point atUp = Direction.UP.change(me);
//        Point atDown = Direction.DOWN.change(me);
//
//        return board.isAt(atLeft.getX(), atLeft.getY(), Element.DUST, Element.BARRIER, Element.OTHER_HERO) &&
//                board.isAt(atRight.getX(), atRight.getY(), Element.DUST, Element.BARRIER, Element.OTHER_HERO) &&
//                board.isAt(atUp.getX(), atUp.getY(), Element.DUST, Element.BARRIER, Element.OTHER_HERO) &&
//                board.isAt(atDown.getX(), atDown.getY(), Element.DUST, Element.BARRIER, Element.OTHER_HERO);
        return true;
    }
}
