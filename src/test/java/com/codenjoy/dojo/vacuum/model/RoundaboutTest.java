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

public class RoundaboutTest extends AbstractGameTest {

    @Test
    public void shouldRotateRoundabout_whenGoingThroughIt() {
        givenFl("#####" +
                "#   #" +
                "# ┌S#" +
                "#   #" +
                "#####");

        hero.left();
        game.tick();
        game.tick();
        assertE("#####" +
                "#   #" +
                "# ┐S#" +
                "# O #" +
                "#####");

        hero.up();
        game.tick();
        game.tick();
        assertE("#####" +
                "#   #" +
                "#O┘S#" +
                "#   #" +
                "#####");

        hero.right();
        game.tick();
        game.tick();
        assertE("#####" +
                "# O #" +
                "# └S#" +
                "#   #" +
                "#####");

        hero.down();
        game.tick();
        game.tick();
        assertE("#####" +
                "#   #" +
                "# ┌O#" +
                "#   #" +
                "#####");
    }

    @Test
    public void shouldBeBelowRobot_whenStandingOnIt() {
        givenFl("#####" +
                "#   #" +
                "# ┌S#" +
                "#   #" +
                "#####");

        hero.left();
        game.tick();
        assertE("#####" +
                "#   #" +
                "# OS#" +
                "#   #" +
                "#####");

        givenFl("#####" +
                "#   #" +
                "# ┐ #" +
                "# S #" +
                "#####");

        hero.up();
        game.tick();
        assertE("#####" +
                "#   #" +
                "# O #" +
                "# S #" +
                "#####");

        givenFl("#####" +
                "#   #" +
                "#S┘ #" +
                "#   #" +
                "#####");

        hero.right();
        game.tick();
        assertE("#####" +
                "#   #" +
                "#SO #" +
                "#   #" +
                "#####");

        givenFl("#####" +
                "# S #" +
                "# └ #" +
                "#   #" +
                "#####");

        hero.down();
        game.tick();
        assertE("#####" +
                "# S #" +
                "# O #" +
                "#   #" +
                "#####");
    }

    @Test
    public void shouldNotBeFined_whenSandingOnRoundabout() {
        givenFl("#####" +
                "#   #" +
                "# ┌S#" +
                "#   #" +
                "#####");

        hero.left();
        game.tick();

        verify(listener, never()).event(Event.TIME_WASTED);
        givenFl("#####" +
                "#   #" +
                "# ┐ #" +
                "# S #" +
                "#####");

        hero.up();
        game.tick();

        verify(listener, never()).event(Event.TIME_WASTED);
        givenFl("#####" +
                "#   #" +
                "#S┘ #" +
                "#   #" +
                "#####");

        hero.right();
        game.tick();
        verify(listener, never()).event(Event.TIME_WASTED);

        givenFl("#####" +
                "# S #" +
                "# └ #" +
                "#   #" +
                "#####");

        hero.down();
        game.tick();
        verify(listener, never()).event(Event.TIME_WASTED);
    }

    @Test
    public void shouldNotBeAbleToEnterFromProhibitedSide() {
        givenFl("#####" +
                "#   #" +
                "#S└ #" +
                "#   #" +
                "#####");

        hero.right();
        game.tick();
        assertE("#####" +
                "#   #" +
                "#O└ #" +
                "#   #" +
                "#####");


        givenFl("#####" +
                "#   #" +
                "#S┌ #" +
                "#   #" +
                "#####");

        hero.right();
        game.tick();
        assertE("#####" +
                "#   #" +
                "#O┌ #" +
                "#   #" +
                "#####");

        givenFl("#####" +
                "# S #" +
                "# ┐ #" +
                "#   #" +
                "#####");

        hero.down();
        game.tick();
        assertE("#####" +
                "# O #" +
                "# ┐ #" +
                "#   #" +
                "#####");

        givenFl("#####" +
                "#   #" +
                "# ┘S#" +
                "#   #" +
                "#####");

        hero.left();
        game.tick();
        assertE("#####" +
                "#   #" +
                "# ┘O#" +
                "#   #" +
                "#####");
    }

    @Test
    public void shouldBeAbleToEnterFromPermittedSide() {
        // UP_RIGHT
        givenFl("#####" +
                "# S #" +
                "# └ #" +
                "#   #" +
                "#####");

        hero.down();
        game.tick();
        game.tick();
        assertE("#####" +
                "# S #" +
                "# ┌O#" +
                "#   #" +
                "#####");

        givenFl("#####" +
                "#   #" +
                "# └S#" +
                "#   #" +
                "#####");

        hero.left();
        game.tick();
        game.tick();
        assertE("#####" +
                "# O #" +
                "# ┌S#" +
                "#   #" +
                "#####");

        // RIGHT_DOWN
        givenFl("#####" +
                "#   #" +
                "# ┌S#" +
                "#   #" +
                "#####");

        hero.left();
        game.tick();
        game.tick();
        assertE("#####" +
                "#   #" +
                "# ┐S#" +
                "# O #" +
                "#####");

        givenFl("#####" +
                "#   #" +
                "# ┌ #" +
                "# S #" +
                "#####");

        hero.up();
        game.tick();
        game.tick();
        assertE("#####" +
                "#   #" +
                "# ┐O#" +
                "# S #" +
                "#####");

        // DOWN_LEFT
        givenFl("#####" +
                "#   #" +
                "# ┐ #" +
                "# S #" +
                "#####");

        hero.up();
        game.tick();
        game.tick();
        assertE("#####" +
                "#   #" +
                "#O┘ #" +
                "# S #" +
                "#####");

        givenFl("#####" +
                "#   #" +
                "#S┐ #" +
                "#   #" +
                "#####");

        hero.right();
        game.tick();
        game.tick();
        assertE("#####" +
                "#   #" +
                "#S┘ #" +
                "# O #" +
                "#####");

        // LEFT_UP
        givenFl("#####" +
                "#   #" +
                "#S┘ #" +
                "#   #" +
                "#####");

        hero.right();
        game.tick();
        game.tick();
        assertE("#####" +
                "# O #" +
                "#S└ #" +
                "#   #" +
                "#####");

        givenFl("#####" +
                "# S #" +
                "# ┘ #" +
                "#   #" +
                "#####");

        hero.down();
        game.tick();
        game.tick();
        assertE("#####" +
                "# S #" +
                "#O└ #" +
                "#   #" +
                "#####");
    }
}
