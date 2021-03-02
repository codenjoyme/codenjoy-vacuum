package com.codenjoy.dojo.vacuum.model;

import org.junit.Test;

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
}
