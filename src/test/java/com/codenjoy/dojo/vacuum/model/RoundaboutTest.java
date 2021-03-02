package com.codenjoy.dojo.vacuum.model;

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
