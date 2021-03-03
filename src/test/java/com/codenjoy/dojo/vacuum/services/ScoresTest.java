package com.codenjoy.dojo.vacuum.services;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2016 Codenjoy
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


import com.codenjoy.dojo.services.PlayerScores;
import org.junit.Before;
import org.junit.Test;

import static com.codenjoy.dojo.vacuum.services.GameSettings.Keys.CLEANING_ONE_CELL_REWARD;
import static com.codenjoy.dojo.vacuum.services.GameSettings.Keys.WASTE_OF_TIME_PENALTY;
import static org.junit.Assert.assertEquals;

public class ScoresTest {

    private PlayerScores scores;
    private GameSettings settings;

    public void allClear() {
        scores.event(Event.ALL_CLEAR);
    }

    public void dustCleaned() {
        scores.event(Event.DUST_CLEANED);
    }

    public void timeWasted() {
        scores.event(Event.TIME_WASTED);
    }


    @Before
    public void setup() {
        settings = new GameSettings();
        scores = new Scores(0, settings);
    }

    @Test
    public void shouldCollectScores() {
        scores = new Scores(140, settings);

        dustCleaned();
        dustCleaned();
        dustCleaned();
        dustCleaned();

        timeWasted();
        timeWasted();
        timeWasted();

        allClear();
        allClear();

        assertEquals(140
                + 4 * settings.integer(CLEANING_ONE_CELL_REWARD)
                - 3 * settings.integer(WASTE_OF_TIME_PENALTY),
                scores.getScore());
    }

    @Test
    public void shouldNotLessThanZero() {
        timeWasted();

        assertEquals(Scores.MIN_SCORE, scores.getScore());
    }

    @Test
    public void shouldClearScore() {
        dustCleaned();

        scores.clear();

        assertEquals(Scores.MIN_SCORE, scores.getScore());
    }


}
