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
        // given
        givenFl("#####" +
                "#   #" +
                "# ┌S#" +
                "#   #" +
                "#####");

        // when
        hero.left();
        game.tick();
        game.tick();

        // then
        assertE("#####" +
                "#   #" +
                "# ┐S#" +
                "# O #" +
                "#####");

        // when
        hero.up();
        game.tick();
        game.tick();

        // then
        assertE("#####" +
                "#   #" +
                "#O┘S#" +
                "#   #" +
                "#####");

        // when
        hero.right();
        game.tick();
        game.tick();

        // then
        assertE("#####" +
                "# O #" +
                "# └S#" +
                "#   #" +
                "#####");

        // when
        hero.down();
        game.tick();
        game.tick();

        // then
        assertE("#####" +
                "#   #" +
                "# ┌O#" +
                "#   #" +
                "#####");
    }

    @Test
    public void shouldBeBelowRobot_whenStandingOnIt_case1() {
        // given
        givenFl("#####" +
                "#   #" +
                "# ┌S#" +
                "#   #" +
                "#####");

        // when
        hero.left();
        game.tick();

        // then
        assertE("#####" +
                "#   #" +
                "# OS#" +
                "#   #" +
                "#####");
    }

    @Test
    public void shouldBeBelowRobot_whenStandingOnIt_case2() {
        // given
        givenFl("#####" +
                "#   #" +
                "# ┐ #" +
                "# S #" +
                "#####");

        // when
        hero.up();
        game.tick();

        // then
        assertE("#####" +
                "#   #" +
                "# O #" +
                "# S #" +
                "#####");
    }

    @Test
    public void shouldBeBelowRobot_whenStandingOnIt_case3() {
        // given
        givenFl("#####" +
                "#   #" +
                "#S┘ #" +
                "#   #" +
                "#####");

        // when
        hero.right();
        game.tick();

        // then
        assertE("#####" +
                "#   #" +
                "#SO #" +
                "#   #" +
                "#####");
    }

    @Test
    public void shouldBeBelowRobot_whenStandingOnIt_case4() {
        // given
        givenFl("#####" +
                "# S #" +
                "# └ #" +
                "#   #" +
                "#####");
        // when
        hero.down();
        game.tick();

        // then
        assertE("#####" +
                "# S #" +
                "# O #" +
                "#   #" +
                "#####");
    }

    @Test
    public void shouldNotBeFined_whenSandingOnRoundabout_case1() {
        // given
        givenFl("#####" +
                "#   #" +
                "# ┌S#" +
                "#   #" +
                "#####");

        // when
        hero.left();
        game.tick();

        // then
        verify(listener, never()).event(Event.TIME_WASTED);
    }

    @Test
    public void shouldNotBeFined_whenSandingOnRoundabout_case2() {
        // given
        givenFl("#####" +
                "#   #" +
                "# ┐ #" +
                "# S #" +
                "#####");

        // when
        hero.up();
        game.tick();

        // then
        verify(listener, never()).event(Event.TIME_WASTED);
    }

    @Test
    public void shouldNotBeFined_whenSandingOnRoundabout_case3() {
        // given
        givenFl("#####" +
                "#   #" +
                "#S┘ #" +
                "#   #" +
                "#####");

        // when
        hero.right();
        game.tick();

        // then
        verify(listener, never()).event(Event.TIME_WASTED);
    }

    @Test
    public void shouldNotBeFined_whenSandingOnRoundabout_case4() {
        // given
        givenFl("#####" +
                "# S #" +
                "# └ #" +
                "#   #" +
                "#####");

        // when
        hero.down();
        game.tick();

        // then
        verify(listener, never()).event(Event.TIME_WASTED);
    }

    @Test
    public void shouldNotBeAbleToEnterFromProhibitedSide_case1() {
        // given
        givenFl("#####" +
                "#   #" +
                "#S└ #" +
                "#   #" +
                "#####");

        // when
        hero.right();
        game.tick();

        // then
        assertE("#####" +
                "#   #" +
                "#O└ #" +
                "#   #" +
                "#####");
    }

    @Test
    public void shouldNotBeAbleToEnterFromProhibitedSide_case2() {
        // given
        givenFl("#####" +
                "#   #" +
                "#S┌ #" +
                "#   #" +
                "#####");

        // when
        hero.right();
        game.tick();

        // then
        assertE("#####" +
                "#   #" +
                "#O┌ #" +
                "#   #" +
                "#####");
    }

    @Test
    public void shouldNotBeAbleToEnterFromProhibitedSide_case3() {
        // given
        givenFl("#####" +
                "# S #" +
                "# ┐ #" +
                "#   #" +
                "#####");

        // when
        hero.down();
        game.tick();

        // then
        assertE("#####" +
                "# O #" +
                "# ┐ #" +
                "#   #" +
                "#####");
    }

    @Test
    public void shouldNotBeAbleToEnterFromProhibitedSide_case4() {
        // given
        givenFl("#####" +
                "#   #" +
                "# ┘S#" +
                "#   #" +
                "#####");

        // when
        hero.left();
        game.tick();

        // then
        assertE("#####" +
                "#   #" +
                "# ┘O#" +
                "#   #" +
                "#####");
    }

    @Test
    public void shouldBeAbleToEnterFromPermittedSide_case1() {
        // given
        givenFl("#####" +
                "# S #" +
                "# └ #" +
                "#   #" +
                "#####");

        // when
        hero.down();
        game.tick();

        // then
        game.tick();
        assertE("#####" +
                "# S #" +
                "# ┌O#" +
                "#   #" +
                "#####");
    }

    @Test
    public void shouldBeAbleToEnterFromPermittedSide_case2() {
        // given
        givenFl("#####" +
                "#   #" +
                "# └S#" +
                "#   #" +
                "#####");

        // when
        hero.left();
        game.tick();
        game.tick();

        // then
        assertE("#####" +
                "# O #" +
                "# ┌S#" +
                "#   #" +
                "#####");
    }

    @Test
    public void shouldBeAbleToEnterFromPermittedSide_case3() {
        // given
        givenFl("#####" +
                "#   #" +
                "# ┌S#" +
                "#   #" +
                "#####");

        // when
        hero.left();
        game.tick();
        game.tick();

        // then
        assertE("#####" +
                "#   #" +
                "# ┐S#" +
                "# O #" +
                "#####");
    }

    @Test
    public void shouldBeAbleToEnterFromPermittedSide_case4() {
        // given
        givenFl("#####" +
                "#   #" +
                "# ┌ #" +
                "# S #" +
                "#####");

        // when
        hero.up();
        game.tick();
        game.tick();

        // then
        assertE("#####" +
                "#   #" +
                "# ┐O#" +
                "# S #" +
                "#####");
    }

    @Test
    public void shouldBeAbleToEnterFromPermittedSide_case5() {
        // given
        givenFl("#####" +
                "#   #" +
                "# ┐ #" +
                "# S #" +
                "#####");

        // when
        hero.up();
        game.tick();
        game.tick();

        // then
        assertE("#####" +
                "#   #" +
                "#O┘ #" +
                "# S #" +
                "#####");
    }

    @Test
    public void shouldBeAbleToEnterFromPermittedSide_case6() {
        // given
        givenFl("#####" +
                "#   #" +
                "#S┐ #" +
                "#   #" +
                "#####");

        // when
        hero.right();
        game.tick();
        game.tick();

        // then
        assertE("#####" +
                "#   #" +
                "#S┘ #" +
                "# O #" +
                "#####");
    }

    @Test
    public void shouldBeAbleToEnterFromPermittedSide_case7() {
        // given
        givenFl("#####" +
                "#   #" +
                "#S┘ #" +
                "#   #" +
                "#####");

        // when
        hero.right();
        game.tick();
        game.tick();

        // then
        assertE("#####" +
                "# O #" +
                "#S└ #" +
                "#   #" +
                "#####");
    }

    @Test
    public void shouldBeAbleToEnterFromPermittedSide_case8() {
        // given
        givenFl("#####" +
                "# S #" +
                "# ┘ #" +
                "#   #" +
                "#####");

        // when
        hero.down();
        game.tick();
        game.tick();

        // then
        assertE("#####" +
                "# S #" +
                "#O└ #" +
                "#   #" +
                "#####");
    }
}
