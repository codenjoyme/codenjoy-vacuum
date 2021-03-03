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
import com.codenjoy.dojo.services.multiplayer.GamePlayer;
import com.codenjoy.dojo.vacuum.services.Event;
import com.codenjoy.dojo.vacuum.services.GameSettings;

/**
 * Класс игрока. Тут кроме героя может подсчитываться очки.
 * Тут же ивенты передабтся лиснеру фреймворка.
 */
public class Player extends GamePlayer<Hero, Field> {

    Hero hero;
    private boolean levelPassed = false;
    private boolean restartRequested = false;

    public Player(EventListener listener, GameSettings settings) {
        super(listener, settings);
    }

    public Hero getHero() {
        return hero;
    }

    @Override
    public void newHero(Field field) {
        levelPassed = false;
        restartRequested = false;
        hero = new Hero(field.getStart());
        hero.init(field);
    }

    @Override
    public boolean isAlive() {
        return !levelPassed && !restartRequested;
    }

    @Override
    public boolean isWin() {
        return levelPassed;
    }

    @Override
    public void event(Object event) {
        super.event(event);
        switch ((Event) event) {
            case ALL_CLEAR:
                levelPassed = true;
                break;
            case RESTART:
                restartRequested = true;
                break;
        }
    }
}
