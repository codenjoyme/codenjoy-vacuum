package com.codenjoy.dojo.vacuum.model.level;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 - 2021 Codenjoy
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

import java.util.ArrayList;
import java.util.List;

public class Levels {

    private static final List<Level> levels = new ArrayList<>();

    // TODO сделать левелы изменяемыми на админке и убрать этот хардкод
    public static int SIZE = dustLevel1().size();

    static {
        levels.add(dustLevel1());
        levels.add(switchLevel2());
        levels.add(limiterLevel3());
        levels.add(roundaboutLevel4());
    }

    public static Level get(int number) {
        if (number < 1) {
            throw new IllegalArgumentException("Level number should " +
                    "be greater than 1 but received " + number);
        }
        if (number > levels.size()) {
            throw new IllegalArgumentException("Has only " + count() +
                    " levels but requested level " + number);
        }
        return levels.get(number - 1);
    }

    public static int count() {
        return levels.size();
    }

    private static Level dustLevel1() {
        return generate("##########" +
                        "#*S#***#*#" +
                        "#*##*#*#*#" +
                        "#****#*#*#" +
                        "######***#" +
                        "#********#" +
                        "#*#*###**#" +
                        "#*#*###**#" +
                        "#***###**#" +
                        "##########"
        );
    }

    public static Level generate(String map) {
        return new Level(map);
    }

    private static Level switchLevel2() {
        return generate("##########" +
                        "#***S←**##" +
                        "#*####**##" +
                        "#*#↓****←#" +
                        "#*#*##* *#" +
                        "#* *  * *#" +
                        "#* *  * *#" +
                        "#*#→**↑ *#" +
                        "#********#" +
                        "##########"
        );
    }

    private static Level limiterLevel3() {
        return generate("##########" +
                        "#****┌***#" +
                        "####**╡╥*#" +
                        "#S*╡══**╡#" +
                        "#**####**#" +
                        "#######*╥#" +
                        "#######**#" +
                        "#######**#" +
                        "#*******╡#" +
                        "##########"
        );
    }

    private static Level roundaboutLevel4() {
        return generate("##########" +
                        "#*#####**#" +
                        "#***##*┐##" +
                        "#*#*###**#" +
                        "#* └***┘*#" +
                        "#└*******#" +
                        "#S******┘#" +
                        "##########" +
                        "##########" +
                        "##########"
        );
    }
}
