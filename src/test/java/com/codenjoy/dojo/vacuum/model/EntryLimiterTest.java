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

import org.junit.Test;

import static com.codenjoy.dojo.vacuum.services.Event.TIME_WASTED;

public class EntryLimiterTest extends AbstractGameTest {

    @Test
    public void shouldBeAbleToEnterCell_whenTryingFromPermittedDirection() {
        // given
        givenFl("######" +
                "#S####" +
                "#║╞═╥#" +
                "#║##║#" +
                "#╨╡═╡#" +
                "######");

        // when
        hero.down();
        game.tick();
        game.tick();
        game.tick();

        // then
        assertE("######" +
                "#S####" +
                "#║╞═╥#" +
                "#║##║#" +
                "#O╡═╡#" +
                "######");

        // when
        hero.right();
        game.tick();
        game.tick();
        game.tick();

        // then
        assertE("######" +
                "#S####" +
                "#║╞═╥#" +
                "#║##║#" +
                "#╨╡═O#" +
                "######");

        // when
        hero.up();
        game.tick();
        game.tick();

        // then
        assertE("######" +
                "#S####" +
                "#║╞═O#" +
                "#║##║#" +
                "#╨╡═╡#" +
                "######");
        // when
        hero.left();
        game.tick();
        game.tick();

        // then
        assertE("######" +
                "#S####" +
                "#║O═╥#" +
                "#║##║#" +
                "#╨╡═╡#" +
                "######");
    }

    @Test
    public void shouldNotFinePlayer_whenSteppingOnLimiters() {
        // given when
        shouldBeAbleToEnterCell_whenTryingFromPermittedDirection();

        // then
        neverFired(TIME_WASTED);
    }

    @Test
    public void shouldNotBeAbleToEnterCell_whenRightLimiterDoesNotAllowIt_tryToEnterFromLeft() {
        // given
        givenFl("####" +
                "#S╞#" +
                "#  #" +
                "####");

        // when
        hero.right();
        game.tick();

        // then
        assertE("####" +
                "#O╞#" +
                "#  #" +
                "####");
    }

    @Test
    public void shouldNotBeAbleToEnterCell_whenRightLimiterDoesNotAllowIt_tryToEnterFromAbove() {
        // given
        givenFl("####" +
                "#S #" +
                "#╞ #" +
                "####");

        // when
        hero.down();
        game.tick();

        // then
        assertE("####" +
                "#O #" +
                "#╞ #" +
                "####");
    }

    @Test
    public void shouldNotBeAbleToEnterCell_whenRightLimiterDoesNotAllowIt_tryToEnterFromBelow() {
        // given
        givenFl("####" +
                "#╞ #" +
                "#S #" +
                "####");
        // when
        hero.up();
        game.tick();

        // then
        assertE("####" +
                "#╞ #" +
                "#O #" +
                "####");
    }

    @Test
    public void shouldNotBeAbleToEnterCell_whenLeftLimiterDoesNotAllowIt_tryToEnterFromRight() {
        // given
        givenFl("####" +
                "#╡S#" +
                "#  #" +
                "####");

        // when
        hero.left();
        game.tick();

        // then
        assertE("####" +
                "#╡O#" +
                "#  #" +
                "####");
    }

    @Test
    public void shouldNotBeAbleToEnterCell_whenLeftLimiterDoesNotAllowIt_tryToEnterFromAbove() {
        // given
        givenFl("####" +
                "#S #" +
                "#╡ #" +
                "####");

        // when
        hero.down();
        game.tick();

        // then
        assertE("####" +
                "#O #" +
                "#╡ #" +
                "####");
    }

    @Test
    public void shouldNotBeAbleToEnterCell_whenLeftLimiterDoesNotAllowIt_tryToEnterFromBelow() {
        // given
        givenFl("####" +
                "#╡ #" +
                "#S #" +
                "####");
        // when
        hero.up();
        game.tick();

        // then
        assertE("####" +
                "#╡ #" +
                "#O #" +
                "####");
    }

    @Test
    public void shouldNotBeAbleToEnterCell_whenUpLimiterDoesNotAllowIt_tryToEnterFromRight() {
        // given
        givenFl("####" +
                "#╨S#" +
                "#  #" +
                "####");

        // when
        hero.left();
        game.tick();

        // then
        assertE("####" +
                "#╨O#" +
                "#  #" +
                "####");
    }

    @Test
    public void shouldNotBeAbleToEnterCell_whenUpLimiterDoesNotAllowIt_tryToEnterFromLeft() {
        // given
        givenFl("####" +
                "#S╨#" +
                "#  #" +
                "####");

        // when
        hero.right();
        game.tick();

        // then
        assertE("####" +
                "#O╨#" +
                "#  #" +
                "####");
    }

    @Test
    public void shouldNotBeAbleToEnterCell_whenUpLimiterDoesNotAllowIt_tryToEnterFromBelow() {
        // given
        givenFl("####" +
                "#╨ #" +
                "#S #" +
                "####");

        // when
        hero.up();
        game.tick();

        // then
        assertE("####" +
                "#╨ #" +
                "#O #" +
                "####");
    }

    @Test
    public void shouldNotBeAbleToEnterCell_whenDownLimiterDoesNotAllowIt_tryToEnterFromRight() {
        // given
        givenFl("####" +
                "#╥S#" +
                "#  #" +
                "####");

        // when
        hero.left();
        game.tick();

        // then
        assertE("####" +
                "#╥O#" +
                "#  #" +
                "####");
    }

    @Test
    public void shouldNotBeAbleToEnterCell_whenDownLimiterDoesNotAllowIt_tryToEnterFromLeft() {
        // given
        givenFl("####" +
                "#S╥#" +
                "#  #" +
                "####");

        // when
        hero.right();
        game.tick();

        // then
        assertE("####" +
                "#O╥#" +
                "#  #" +
                "####");
    }

    @Test
    public void shouldNotBeAbleToEnterCell_whenDownLimiterDoesNotAllowIt_tryToEnterFromAbove() {
        // given
        givenFl("####" +
                "#S #" +
                "#╥ #" +
                "####");

        // when
        hero.down();
        game.tick();

        // then
        assertE("####" +
                "#O #" +
                "#╥ #" +
                "####");
    }

    @Test
    public void shouldNotBeAbleToEnterCell_whenHorizontalLimiterDoesNotAllowIt_tryToEnterFromAbove() {
        // given
        givenFl("####" +
                "#S #" +
                "#═ #" +
                "####");

        // when
        hero.down();
        game.tick();

        // then
        assertE("####" +
                "#O #" +
                "#═ #" +
                "####");
    }

    @Test
    public void shouldNotBeAbleToEnterCell_whenHorizontalLimiterDoesNotAllowIt_tryToEnterFromBelow() {
        // given
        givenFl("####" +
                "#═ #" +
                "#S #" +
                "####");

        // when
        hero.up();
        game.tick();

        // then
        assertE("####" +
                "#═ #" +
                "#O #" +
                "####");
    }

    @Test
    public void shouldNotBeAbleToEnterCell_whenVerticalLimiterDoesNotAllowIt_tryToEnterFromLeft() {
        // given
        givenFl("####" +
                "#S║#" +
                "#  #" +
                "####");

        // when
        hero.right();
        game.tick();

        // then
        assertE("####" +
                "#O║#" +
                "#  #" +
                "####");
    }

    @Test
    public void shouldNotBeAbleToEnterCell_whenVerticalLimiterDoesNotAllowIt_tryToEnterFromRight() {
        // given
        givenFl("####" +
                "#║S#" +
                "#  #" +
                "####");

        // when
        hero.left();
        game.tick();

        // then
        assertE("####" +
                "#║O#" +
                "#  #" +
                "####");
    }
}
