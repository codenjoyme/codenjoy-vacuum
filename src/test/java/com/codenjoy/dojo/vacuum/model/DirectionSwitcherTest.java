package com.codenjoy.dojo.vacuum.model;

import org.junit.Test;

public class DirectionSwitcherTest extends AbstractGameTest {

    @Test
    public void vacuumShouldChangeDirection_whenSteppingOnDirectionSwitcher() {
        givenFl("######" +
                "#S ↓ #" +
                "# →  #" +
                "# ↑← #" +
                "#    #" +
                "######");

        hero.right();
        game.tick();
        game.tick();
        game.tick();
        game.tick();
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
}
