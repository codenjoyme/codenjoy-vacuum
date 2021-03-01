package com.codenjoy.dojo.vacuum.services;

/*-
 * #%L
 * expansion - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2016 - 2020 Codenjoy
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


import com.codenjoy.dojo.services.settings.Parameter;
import com.codenjoy.dojo.services.settings.Settings;
import com.codenjoy.dojo.services.settings.SettingsImpl;

public final class SettingsWrapper {

    public static SettingsWrapper data;

    private final Parameter<Integer> initialScore;
    private final Parameter<Integer> cleanReward;
    private final Parameter<Integer> wasteTimePenalty;

    private final Settings settings;

    public static SettingsWrapper setup(Settings settings) {
        return new SettingsWrapper(settings);
    }

    // for testing
    public static SettingsWrapper setup() {
        return setup(new SettingsImpl());
    }

    private SettingsWrapper(Settings settings) {
        data = this;
        this.settings = settings;

        initialScore = settings.addEditBox("Initial player score").type(Integer.class).def(0);
        cleanReward = settings.addEditBox("Cleaning one cell reward").type(Integer.class).def(100);
        wasteTimePenalty = settings.addEditBox("Waste of time penalty").type(Integer.class).def(1);
    }

    public int getInitialScore() {
        return initialScore.getValue();
    }

    public int getCleanReward() {
        return cleanReward.getValue();
    }

    public int getWasteTimePenalty() {
        return wasteTimePenalty.getValue();
    }
}
