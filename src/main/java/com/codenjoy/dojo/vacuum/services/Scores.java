package com.codenjoy.dojo.vacuum.services;

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


import com.codenjoy.dojo.services.PlayerScores;

import java.util.concurrent.atomic.AtomicInteger;

import static com.codenjoy.dojo.vacuum.services.GameSettings.Keys.CLEANING_ONE_CELL_REWARD;
import static com.codenjoy.dojo.vacuum.services.GameSettings.Keys.WASTE_OF_TIME_PENALTY;

public class Scores implements PlayerScores {

    // TODO выделить так же во всех играх
    public static final int MIN_SCORE = 0;

    private final AtomicInteger score;
    private final GameSettings settings;

    public Scores(int startScore, GameSettings settings) {
        this.score = new AtomicInteger(startScore);
        this.settings = settings;
    }

    @Override
    public int clear() {
        score.set(0);
        return score.get();
    }

    @Override
    public Integer getScore() {
        return score.get();
    }

    @Override
    public void event(Object event) {
        if (!(event instanceof Event)) {
            throw new IllegalArgumentException("Given object should be an instance of Event class");
        }
        switch ((Event) event) {
            case ALL_CLEAR:
                break;
            case TIME_WASTED:
                score.addAndGet(settings.integer(WASTE_OF_TIME_PENALTY) * -1);
                break;
            case DUST_CLEANED:
                score.addAndGet(settings.integer(CLEANING_ONE_CELL_REWARD));
                break;
        }
        score.accumulateAndGet(MIN_SCORE, Math::max);
    }

    @Override
    public void update(Object score) {
        this.score.set(Integer.parseInt(score.toString()));
    }
}
