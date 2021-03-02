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


import com.codenjoy.dojo.client.ClientBoard;
import com.codenjoy.dojo.client.Solver;
import com.codenjoy.dojo.services.AbstractGameType;
import com.codenjoy.dojo.services.EventListener;
import com.codenjoy.dojo.services.GameType;
import com.codenjoy.dojo.services.PlayerScores;
import com.codenjoy.dojo.services.multiplayer.GameField;
import com.codenjoy.dojo.services.multiplayer.GamePlayer;
import com.codenjoy.dojo.services.multiplayer.MultiplayerType;
import com.codenjoy.dojo.services.printer.CharElements;
import com.codenjoy.dojo.services.settings.Parameter;
import com.codenjoy.dojo.services.settings.SimpleParameter;
import com.codenjoy.dojo.vacuum.client.Board;
import com.codenjoy.dojo.vacuum.client.ai.AISolver;
import com.codenjoy.dojo.vacuum.model.Element;
import com.codenjoy.dojo.vacuum.model.Player;
import com.codenjoy.dojo.vacuum.model.VacuumGame;
import com.codenjoy.dojo.vacuum.model.level.Levels;

public class GameRunner extends AbstractGameType implements GameType {

    private VacuumGame game;

    public GameRunner() {
        setupSettings();
    }

    private void setupSettings() {
        SettingsWrapper.setup(settings);
    }

    @Override
    public PlayerScores getPlayerScores(Object score) {
        return new Scores((Integer) score, SettingsWrapper.data);
    }

    @Override
    public GameField createGame(int levelNumber) {
        return game = new VacuumGame(Levels.get(levelNumber));
    }

    @Override
    public Parameter<Integer> getBoardSize() {
        return SimpleParameter.v(game.getSize());
    }

    @Override
    public String name() {
        return "vacuum";
    }

    @Override
    public CharElements[] getPlots() {
        return Element.values();
    }

    @Override
    public Class<? extends Solver> getAI() {
        return AISolver.class;
    }

    @Override
    public Class<? extends ClientBoard> getBoard() {
        return Board.class;
    }

    @Override
    public MultiplayerType getMultiplayerType() {
        return MultiplayerType.SINGLE_LEVELS.apply(Levels.count());
    }

    @Override
    public GamePlayer createPlayer(EventListener listener, String playerId) {
        return new Player(listener);
    }
}