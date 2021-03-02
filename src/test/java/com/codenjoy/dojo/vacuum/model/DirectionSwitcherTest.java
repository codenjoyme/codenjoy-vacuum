package com.codenjoy.dojo.vacuum.model;

import com.codenjoy.dojo.vacuum.services.Event;
import org.junit.Test;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

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

    @Test
    public void shouldTurnAround_whenStepOnSwitcherInOppositeDirection() {
        givenFl("######" +
                "#S   #" +
                "#    #" +
                "#    #" +
                "#↑   #" +
                "######");

        hero.down();
        game.tick();
        game.tick();
        game.tick();
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
        givenFl("#####" +
                "#S↓ #" +
                "#↑← #" +
                "#   #" +
                "#####");

        hero.right();
        game.tick();
        game.tick();
        game.tick();
        game.tick();
        assertE("#####" +
                "#O↓ #" +
                "#↑← #" +
                "#   #" +
                "#####");

        verify(listener, never()).event(Event.TIME_WASTED);
    }
}
