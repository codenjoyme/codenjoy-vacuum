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

public class EntryLimiterTest extends AbstractGameTest {

    @Test
    public void shouldBeAbleToEnterCell_whenTryingFromPermittedDirection() {
        givenFl("######" +
                "#S####" +
                "#║╞═╥#" +
                "#║##║#" +
                "#╨╡═╡#" +
                "######");

        hero.down();
        game.tick();
        game.tick();
        game.tick();
        assertE("######" +
                "#S####" +
                "#║╞═╥#" +
                "#║##║#" +
                "#O╡═╡#" +
                "######");

        hero.right();
        game.tick();
        game.tick();
        game.tick();
        assertE("######" +
                "#S####" +
                "#║╞═╥#" +
                "#║##║#" +
                "#╨╡═O#" +
                "######");

        hero.up();
        game.tick();
        game.tick();
        assertE("######" +
                "#S####" +
                "#║╞═O#" +
                "#║##║#" +
                "#╨╡═╡#" +
                "######");

        hero.left();
        game.tick();
        game.tick();
        assertE("######" +
                "#S####" +
                "#║O═╥#" +
                "#║##║#" +
                "#╨╡═╡#" +
                "######");
    }

    @Test
    public void shouldNotFinePlayer_whenSteppingOnLimiters() {
        givenFl("######" +
                "#S####" +
                "#║╞═╥#" +
                "#║##║#" +
                "#╨╡═╡#" +
                "######");

        hero.down();
        game.tick();
        game.tick();
        game.tick();

        hero.right();
        game.tick();
        game.tick();
        game.tick();

        hero.up();
        game.tick();
        game.tick();

        hero.left();
        game.tick();
        game.tick();

        verify(listener, never()).event(Event.TIME_WASTED);
    }

    @Test
    public void shouldNotBeAbleToEnterCell_whenRightLimiterDoesNotAllowIt() {
        // Try to enter from left
        givenFl("####" +
                "#S╞#" +
                "#  #" +
                "####");

        hero.right();
        game.tick();
        assertE("####" +
                "#O╞#" +
                "#  #" +
                "####");

        // Try to enter from above
        givenFl("####" +
                "#S #" +
                "#╞ #" +
                "####");

        hero.down();
        game.tick();
        assertE("####" +
                "#O #" +
                "#╞ #" +
                "####");

        // Try to enter from below
        givenFl("####" +
                "#╞ #" +
                "#S #" +
                "####");

        hero.up();
        game.tick();
        assertE("####" +
                "#╞ #" +
                "#O #" +
                "####");
    }

    @Test
    public void shouldNotBeAbleToEnterCell_whenLeftLimiterDoesNotAllowIt() {
        // Try to enter from right
        givenFl("####" +
                "#╡S#" +
                "#  #" +
                "####");

        hero.left();
        game.tick();
        assertE("####" +
                "#╡O#" +
                "#  #" +
                "####");

        // Try to enter from above
        givenFl("####" +
                "#S #" +
                "#╡ #" +
                "####");

        hero.down();
        game.tick();
        assertE("####" +
                "#O #" +
                "#╡ #" +
                "####");

        // Try to enter from below
        givenFl("####" +
                "#╡ #" +
                "#S #" +
                "####");

        hero.up();
        game.tick();
        assertE("####" +
                "#╡ #" +
                "#O #" +
                "####");
    }

    @Test
    public void shouldNotBeAbleToEnterCell_whenUpLimiterDoesNotAllowIt() {
        // Try to enter from right
        givenFl("####" +
                "#╨S#" +
                "#  #" +
                "####");

        hero.left();
        game.tick();
        assertE("####" +
                "#╨O#" +
                "#  #" +
                "####");

        // Try to enter from left
        givenFl("####" +
                "#S╨#" +
                "#  #" +
                "####");

        hero.right();
        game.tick();
        assertE("####" +
                "#O╨#" +
                "#  #" +
                "####");

        // Try to enter from below
        givenFl("####" +
                "#╨ #" +
                "#S #" +
                "####");

        hero.up();
        game.tick();
        assertE("####" +
                "#╨ #" +
                "#O #" +
                "####");
    }

    @Test
    public void shouldNotBeAbleToEnterCell_whenDownLimiterDoesNotAllowIt() {
        // Try to enter from right
        givenFl("####" +
                "#╥S#" +
                "#  #" +
                "####");

        hero.left();
        game.tick();
        assertE("####" +
                "#╥O#" +
                "#  #" +
                "####");

        // Try to enter from left
        givenFl("####" +
                "#S╥#" +
                "#  #" +
                "####");

        hero.right();
        game.tick();
        assertE("####" +
                "#O╥#" +
                "#  #" +
                "####");

        // Try to enter from above
        givenFl("####" +
                "#S #" +
                "#╥ #" +
                "####");

        hero.down();
        game.tick();
        assertE("####" +
                "#O #" +
                "#╥ #" +
                "####");
    }

    @Test
    public void shouldNotBeAbleToEnterCell_whenHorizontalLimiterDoesNotAllowIt() {
        // Try to enter from above
        givenFl("####" +
                "#S #" +
                "#═ #" +
                "####");

        hero.down();
        game.tick();
        assertE("####" +
                "#O #" +
                "#═ #" +
                "####");

        // Try to enter from below
        givenFl("####" +
                "#═ #" +
                "#S #" +
                "####");

        hero.up();
        game.tick();
        assertE("####" +
                "#═ #" +
                "#O #" +
                "####");
    }

    @Test
    public void shouldNotBeAbleToEnterCell_whenVerticalLimiterDoesNotAllowIt() {
        // Try to enter from left
        givenFl("####" +
                "#S║#" +
                "#  #" +
                "####");

        hero.right();
        game.tick();
        assertE("####" +
                "#O║#" +
                "#  #" +
                "####");

        // Try to enter from right
        givenFl("####" +
                "#║S#" +
                "#  #" +
                "####");

        hero.left();
        game.tick();
        assertE("####" +
                "#║O#" +
                "#  #" +
                "####");
    }
}
