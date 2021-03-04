package com.codenjoy.dojo.vacuum.client;

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

import com.codenjoy.dojo.vacuum.model.Elements;
import org.junit.Before;
import org.junit.Test;

import static com.codenjoy.dojo.services.PointImpl.pt;
import static org.junit.Assert.assertEquals;

public class BoardTest {

    private Board board;

    public static Board board(String boardString) {
        return (Board)new Board().forString(boardString);
    }

    @Before
    public void before() {
        board = board(
                "##########" +
                "#SO******#" +
                "#*↓  ═ ←*#" +
                "#* ↓╞ ← *#" +
                "#*║ ┘└╥ *#" +
                "#* ╨┐┌ ║*#" +
                "#* → ╡↑ *#" +
                "#*→ ═  ↑*#" +
                "#********#" +
                "##########");
    }

    @Test
    public void shouldWorkToString() {
        assertEquals(
                /*9*/"##########\n" +
                /*8*/"#SO******#\n" +
                /*7*/"#*↓  ═ ←*#\n" +
                /*6*/"#* ↓╞ ← *#\n" +
                /*5*/"#*║ ┘└╥ *#\n" +
                /*4*/"#* ╨┐┌ ║*#\n" +
                /*3*/"#* → ╡↑ *#\n" +
                /*2*/"#*→ ═  ↑*#\n" +
                /*1*/"#********#\n" +
                /*0*/"##########\n" +
                /*    0123456789 */
                "\n" +
                "Vacuum cleaner at: [2,8]\n" +
                "Start point at: [1,8]\n" +
                "Barriers at: [[0,0], [0,1], [0,2], [0,3], [0,4], [0,5], [0,6], [0,7], [0,8], [0,9], [1,0], [1,9], [2,0], [2,9], [3,0], [3,9], [4,0], [4,9], [5,0], [5,9], [6,0], [6,9], [7,0], [7,9], [8,0], [8,9], [9,0], [9,1], [9,2], [9,3], [9,4], [9,5], [9,6], [9,7], [9,8], [9,9]]\n" +
                "Dust at: [[1,1], [1,2], [1,3], [1,4], [1,5], [1,6], [1,7], [2,1], [3,1], [3,8], [4,1], [4,8], [5,1], [5,8], [6,1], [6,8], [7,1], [7,8], [8,1], [8,2], [8,3], [8,4], [8,5], [8,6], [8,7], [8,8]]\n" +
                "None: [[2,3], [2,4], [2,6], [3,2], [3,5], [3,7], [4,3], [4,7], [5,2], [5,6], [6,2], [6,4], [6,7], [7,3], [7,5], [7,6]]\n" +
                "Roundabouts: [[4,4], [4,5], [5,4], [5,5]]\n" +
                "Limiters: [[2,5], [3,4], [4,2], [4,6], [5,3], [5,7], [6,5], [7,4]]\n" +
                "Switches: [[2,2], [2,7], [3,3], [3,6], [6,3], [6,6], [7,2], [7,7]]", board.toString());
    }

    @Test
    public void shouldWork_getAt() {
        assertEquals(Elements.NONE, board.getAt(2, 3));
        assertEquals(Elements.BARRIER, board.getAt(0, 8));
        assertEquals(Elements.START, board.getAt(1, 8));
    }

    @Test
    public void shouldWork_getAt_point() {
        assertEquals(Elements.NONE, board.getAt(pt(7, 3)));
        assertEquals(Elements.BARRIER, board.getAt(pt(0, 8)));
        assertEquals(Elements.START, board.getAt(pt(1, 8)));
    }

    @Test
    public void shouldWork_getNear() {
        assertEquals("[ , ↑,  , ↑, ║, *, *, *]", board.getNear(7, 3).toString());
        assertEquals("[#, #, *, S, #]", board.getNear(0, 8).toString());
        assertEquals("[┘, ╞,  , └, ═, ╥, ←,  ]", board.getNear(5, 6).toString());
    }

    @Test
    public void shouldWork_getNear_point() {
        assertEquals("[ , ↑,  , ↑, ║, *, *, *]", board.getNear(pt(7, 3)).toString());
        assertEquals("[#, #, *, S, #]", board.getNear(pt(0, 8)).toString());
        assertEquals("[┘, ╞,  , └, ═, ╥, ←,  ]", board.getNear(pt(5, 6)).toString());
    }

    @Test
    public void shouldWork_getAt_outOfBoard() {
        assertEquals(Elements.BARRIER, board.getAt(-1, 1));
        assertEquals(Elements.BARRIER, board.getAt(1, -1));
        assertEquals(Elements.BARRIER, board.getAt(100, 1));
        assertEquals(Elements.BARRIER, board.getAt(1, 100));

        assertEquals(Elements.BARRIER, board.getAt(pt(-1, 1)));
        assertEquals(Elements.BARRIER, board.getAt(pt(1, -1)));
        assertEquals(Elements.BARRIER, board.getAt(pt(100, 1)));
        assertEquals(Elements.BARRIER, board.getAt(pt(1, 100)));
    }

    @Test
    public void shouldWork_getBarriers() {
        assertEquals("[[0,0], [0,1], [0,2], [0,3], [0,4], [0,5], " +
                        "[0,6], [0,7], [0,8], [0,9], [1,0], [1,9], " +
                        "[2,0], [2,9], [3,0], [3,9], [4,0], [4,9], " +
                        "[5,0], [5,9], [6,0], [6,9], [7,0], [7,9], " +
                        "[8,0], [8,9], [9,0], [9,1], [9,2], [9,3], " +
                        "[9,4], [9,5], [9,6], [9,7], [9,8], [9,9]]",
                board.getBarriers().toString());
    }

    @Test
    public void shouldWork_getStartPoint() {
        assertEquals("[1,8]",
                board.getStartPoint().toString());
    }

    @Test
    public void shouldWork_getDust() {
        assertEquals("[[1,1], [1,2], [1,3], [1,4], [1,5], [1,6], " +
                        "[1,7], [2,1], [3,1], [3,8], [4,1], [4,8], " +
                        "[5,1], [5,8], [6,1], [6,8], [7,1], [7,8], " +
                        "[8,1], [8,2], [8,3], [8,4], [8,5], [8,6], " +
                        "[8,7], [8,8]]",
                board.getDust().toString());
    }
 
    @Test
    public void shouldWork_getNone() {
        assertEquals("[[2,3], [2,4], [2,6], [3,2], [3,5], [3,7], " +
                        "[4,3], [4,7], [5,2], [5,6], [6,2], [6,4], " +
                        "[6,7], [7,3], [7,5], [7,6]]",
                board.getNone().toString());
    }
 
    @Test
    public void shouldWork_getRoundabouts() {
        assertEquals("[[4,4], [4,5], [5,4], [5,5]]",
                board.getRoundabouts().toString());
    }
    
    @Test
    public void shouldWork_getLimiters() {
        assertEquals("[[2,5], [3,4], [4,2], [4,6], [5,3], [5,7], [6,5], [7,4]]",
                board.getLimiters().toString());
    }  
    
    @Test
    public void shouldWork_getSwitches() {
        assertEquals("[[2,2], [2,7], [3,3], [3,6], [6,3], " +
                        "[6,6], [7,2], [7,7]]",
                board.getSwitches().toString());
    }

    @Test
    public void shouldWork_isBarrierAt() {
        assertEquals(false, board.isBarrierAt(1, 1));
        assertEquals(false, board.isBarrierAt(5, 1));
    }

    @Test
    public void shouldWork_isBarrierAt_point() {
        assertEquals(false, board.isBarrierAt(pt(1, 1)));
        assertEquals(false, board.isBarrierAt(pt(5, 1)));
    }

    @Test
    public void shouldWork_countNear() {
        assertEquals(1, board.countNear(0, 0, Elements.DUST));
        assertEquals(3, board.countNear(2, 1, Elements.DUST));
        assertEquals(2, board.countNear(4, 1, Elements.DUST));

        assertEquals(3, board.countNear(1, 7, Elements.BARRIER));
        assertEquals(5, board.countNear(1, 1, Elements.BARRIER));
        assertEquals(0, board.countNear(7, 7, Elements.BARRIER));
        assertEquals(3, board.countNear(7, 1, Elements.BARRIER));
        assertEquals(3, board.countNear(1, 6, Elements.BARRIER));
    }

    @Test
    public void shouldWork_countNear_point() {
        assertEquals(1, board.countNear(pt(0, 0), Elements.DUST));
        assertEquals(3, board.countNear(pt(2, 1), Elements.DUST));
        assertEquals(2, board.countNear(pt(4, 1), Elements.DUST));

        assertEquals(3, board.countNear(pt(1, 7), Elements.BARRIER));
        assertEquals(5, board.countNear(pt(1, 1), Elements.BARRIER));
        assertEquals(0, board.countNear(pt(7, 7), Elements.BARRIER));
        assertEquals(3, board.countNear(pt(7, 1), Elements.BARRIER));
        assertEquals(3, board.countNear(pt(1, 6), Elements.BARRIER));
    }

    @Test
    public void shouldWork_isAt() {
        assertEquals(true, board.isAt(3, 1, Elements.DUST));
        assertEquals(true, board.isAt(2, 1, Elements.DUST));

        assertEquals(true, board.isAt(3, 1, Elements.VACUUM, Elements.DUST));
        assertEquals(true, board.isAt(2, 1, Elements.VACUUM, Elements.DUST));
    }

    @Test
    public void shouldWork_isAt_point() {
        assertEquals(true, board.isAt(pt(3, 1), Elements.DUST));
        assertEquals(true, board.isAt(pt(2, 1), Elements.DUST));

        assertEquals(true, board.isAt(pt(3, 1), Elements.VACUUM, Elements.DUST));
        assertEquals(true, board.isAt(pt(2, 1), Elements.VACUUM, Elements.DUST));
    }

    @Test
    public void shouldWork_isNear() {
        assertEquals(true, board.isNear(1, 1, Elements.BARRIER));
        assertEquals(false, board.isNear(5, 5, Elements.BARRIER));
    }

    @Test
    public void shouldWork_isNear_point() {
        assertEquals(true, board.isNear(pt(1, 1), Elements.BARRIER));
        assertEquals(false, board.isNear(pt(5, 5), Elements.BARRIER));
    }
}
