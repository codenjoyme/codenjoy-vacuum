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

import com.codenjoy.dojo.vacuum.services.Event;
import org.junit.Test;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class DirectionSwitcherTest extends AbstractGameTest {

    @Test
    public void vacuumShouldChangeDirection_whenSteppingOnDirectionSwitcher() {
        // given
        givenFl("######" +
                "#S ↓ #" +
                "# →  #" +
                "# ↑← #" +
                "#    #" +
                "######");

        // when
        hero.right();
        game.tick();
        game.tick();
        game.tick();
        game.tick();

        // then
        assertE("######" +
                "#S ↓ #" +
                "# →  #" +
                "# ↑O #" +
                "#    #" +
                "######");

        game.tick();

        assertE("######" +
                "#S ↓ #" +
                "# →  #" +
                "# O← #" +
                "#    #" +
                "######");

        game.tick();
        game.tick();

        assertE("######" +
                "#S ↓ #" +
                "# →O #" +
                "# ↑← #" +
                "#    #" +
                "######");

        game.tick();

        assertE("######" +
                "#S ↓ #" +
                "# → O#" +
                "# ↑← #" +
                "#    #" +
                "######");
    }

    @Test
    public void shouldTurnAround_whenStepOnSwitcherInOppositeDirection() {
        // given
        givenFl("######" +
                "#S   #" +
                "#    #" +
                "#    #" +
                "#↑   #" +
                "######");

        // when
        hero.down();
        game.tick();
        game.tick();
        game.tick();

        // then
        assertE("######" +
                "#S   #" +
                "#    #" +
                "#    #" +
                "#O   #" +
                "######");

        game.tick();
        game.tick();
        game.tick();

        assertE("######" +
                "#O   #" +
                "#    #" +
                "#    #" +
                "#↑   #" +
                "######");
    }

    @Test
    public void shouldNotBeFined_whenStepOnSwitcher() {
        // given
        givenFl("#####" +
                "#S↓ #" +
                "#↑← #" +
                "#   #" +
                "#####");

        // when
        hero.right();
        game.tick();
        game.tick();
        game.tick();
        game.tick();

        // then
        assertE("#####" +
                "#O↓ #" +
                "#↑← #" +
                "#   #" +
                "#####");

        verify(listener, never()).event(Event.TIME_WASTED);
    }
}
