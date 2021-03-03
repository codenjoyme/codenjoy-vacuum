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
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Objects;

import static com.codenjoy.dojo.vacuum.services.Event.*;
import static com.codenjoy.dojo.vacuum.services.Event.DUST_CLEANED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class VacuumTest extends AbstractGameTest {

    @Test
    public void shouldSpawnOnStartPoint() {
        // given
        givenFl("#####" +
                "#   #" +
                "# S #" +
                "#   #" +
                "#####");

        // when then
        assertE("#####" +
                "#   #" +
                "# O #" +
                "#   #" +
                "#####");
    }

    @Test
    public void shouldGoUntilBarrier() {
        // given
        givenFl("#####" +
                "# S #" +
                "#   #" +
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

        hero.right(); // should not affect
        game.tick();

        assertE("#####" +
                "# S #" +
                "#   #" +
                "# O #" +
                "#####");

        game.tick();

        assertE("#####" +
                "# S #" +
                "#   #" +
                "# O #" +
                "#####");

        // when
        hero.right();
        game.tick();

        // then
        assertE("#####" +
                "# S #" +
                "#   #" +
                "#  O#" +
                "#####");

        game.tick();

        assertE("#####" +
                "# S #" +
                "#   #" +
                "#  O#" +
                "#####");
    }

    @Test
    public void shouldBeAbleToChangeDirection_whenStopped() {
        // given
        givenFl("#####" +
                "# S #" +
                "#   #" +
                "#   #" +
                "#####");

        // when
        hero.right();
        game.tick();

        // then
        assertE("#####" +
                "# SO#" +
                "#   #" +
                "#   #" +
                "#####");

        hero.down();
        game.tick();

        assertE("#####" +
                "# S #" +
                "#  O#" +
                "#   #" +
                "#####");
    }

    @Test
    public void shouldCleanDust() {
        // given
        givenFl("#####" +
                "# S #" +
                "#***#" +
                "#***#" +
                "#####");

        // when
        hero.down();
        game.tick();

        // then
        assertE("#####" +
                "# S #" +
                "#*O*#" +
                "#***#" +
                "#####");

        game.tick();

        assertE("#####" +
                "# S #" +
                "#* *#" +
                "#*O*#" +
                "#####");

        verify(listener, times(2)).event(DUST_CLEANED);
    }

    @Test
    public void shouldBeenFined_whenWastingTime() {
        // given
        givenFl("#####" +
                "# S #" +
                "#* *#" +
                "#* *#" +
                "#####");

        // when
        hero.down();
        game.tick();
        game.tick();

        // then
        assertE("#####" +
                "# S #" +
                "#* *#" +
                "#*O*#" +
                "#####");

        verify(listener, times(2)).event(TIME_WASTED);
    }

    @Test
    public void shouldWin_whenAllClear() {
        // given
        givenFl("#####" +
                "#S**#" +
                "#* *#" +
                "#***#" +
                "#####");

        // when
        // clean all of the dust
        hero.right();
        game.tick();
        game.tick();
        game.tick();

        hero.down();
        game.tick();
        game.tick();

        hero.left();
        game.tick();
        game.tick();

        hero.up();
        game.tick();

        // then
        assertE("#####" +
                "#S  #" +
                "#O  #" +
                "#   #" +
                "#####");

        ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        verify(listener, times(8)).event(eventCaptor.capture());
        assertEquals(ALL_CLEAR, eventCaptor.getAllValues().get(7));
        assertTrue(containsOnly(eventCaptor.getAllValues().subList(0, 6), DUST_CLEANED));
    }

    @Test
    public void shouldNotBeFined_whenSteppingOnStartPoint() {
        // given
        givenFl("####" +
                "#S*#" +
                "#**#" +
                "####");

        // when
        hero.right();
        game.tick();

        hero.left();
        game.tick();

        // then
        assertE("####" +
                "#O #" +
                "#**#" +
                "####");

        verify(listener, never()).event(TIME_WASTED);
    }

    @Test
    public void shouldNotBeFined_whenDoNothingAtGameTick() {
        // given
        givenFl("#####" +
                "#S**#" +
                "#* *#" +
                "#***#" +
                "#####");

        // when
        game.tick();
        game.tick();
        game.tick();

        // then
        verify(listener, never()).event(TIME_WASTED);
    }

    private boolean containsOnly(List<?> list, Object value) {
        for (Object o : list) {
            if (!Objects.equals(o, value)) {
                return false;
            }
        }
        return true;
    }
}
