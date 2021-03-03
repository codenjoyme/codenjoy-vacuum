package com.codenjoy.dojo.vacuum.model;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 Codenjoy
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


import com.codenjoy.dojo.services.EventListener;
import com.codenjoy.dojo.services.printer.PrinterFactory;
import com.codenjoy.dojo.services.printer.PrinterFactoryImpl;
import com.codenjoy.dojo.utils.TestUtils;
import com.codenjoy.dojo.vacuum.model.level.Level;
import com.codenjoy.dojo.vacuum.services.Event;
import com.codenjoy.dojo.vacuum.services.GameSettings;
import org.junit.Before;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public abstract class AbstractGameTest {

    protected VacuumGame game;
    protected Hero hero;
    protected EventListener listener;
    protected Player player;
    protected PrinterFactory printer;
    private GameSettings settings;

    @Before
    public void setup() {
        settings = new GameSettings();
        printer = new PrinterFactoryImpl();
    }

    protected void givenFl(String board) {
        Level level = Level.generate(board);
        game = new VacuumGame(level, settings);

        listener = mock(EventListener.class);
        player = new Player(listener, settings);
        game.newGame(player);
        player.newHero(game);
        hero = player.getHero();
    }

    protected void assertE(String expected) {
        assertEquals(TestUtils.injectN(expected),
                printer.getPrinter(game.reader(), player).print());
    }

    protected void neverFired(Event event) {
        verify(listener, never()).event(event);
    }

    public void fired(String expected) {
        ArgumentCaptor<Event> captor = ArgumentCaptor.forClass(Event.class);
        verify(listener, times(expected.split(",").length)).event(captor.capture());
        assertEquals(expected, captor.getAllValues().toString());
    }
}
