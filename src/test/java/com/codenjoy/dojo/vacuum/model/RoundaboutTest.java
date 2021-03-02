package com.codenjoy.dojo.vacuum.model;

import org.junit.Test;

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
}
