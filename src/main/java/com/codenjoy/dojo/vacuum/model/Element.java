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


import com.codenjoy.dojo.services.printer.CharElements;

/**
 * Тут указана легенда всех возможных объектов на поле и их состояний.
 * Важно помнить, что для каждой енумной константы надо создать спрайт в папке \src\main\webapp\resources\sprite.
 */
public enum Element implements CharElements {

    NONE(' '),                          // Чистая клетка локации. Проезд облагается штрафом.
                                            // Эффективный пылесос должен меньше гулять по чистым местам и больше убираться.

    VACUUM('O'),                        // Сам робот-пылесос.
    START('S'),                         // Стартовая точка робота-пылесоса на уровне.

    BARRIER('#'),                       // Барьер, через который нельзя проехать.
    DUST('*'),                          // Грязь/пыль, которую нужно очистить для того, чтобы пройти уровень.

    // Переключатели движения.
    // Заехать можно с любой стороны.
    // Автоматически поворачивают робота в сторону, в которую указывают.
    SWITCH_LEFT('←'),                   // Переключатель влево.
    SWITCH_RIGHT('→'),                  // Переключатель вправо.
    SWITCH_UP('↑'),                     // Переключатель вверх.
    SWITCH_DOWN('↓'),                   // Переключатель вниз.

    // Ограничители взъезда.
    // Заехать можно только с определенной стороны.
    // Выезжать с ограничителя можно в любом направлении.
    LIMITER_LEFT('╡'),                  // Заехать можно только слева.
    LIMITER_RIGHT('╞'),                 // Заехать можно только справа.
    LIMITER_UP('╨'),                    // Заехать можно только сверху.
    LIMITER_DOWN('╥'),                  // Заехать можно только снизу.
    LIMITER_VERTICAL('║'),              // Заехать можно как сверху так и снизу.
    LIMITER_HORIZONTAL('═'),            // Заехать можно как слева так и справа.

    // "Карусели".
    // Работают одновременно и как переключатели движения и как ограничители въезда.
    // Заехать можно только с определенной стороны, автоматически переключают направление движения робота.
    // После проезда по ним поворачиваются по часовой стрелке.
    ROUNDABOUT_LEFT_UP('┘'),            // Заехать можно слева и сверху, повернет робота наверх или налево соотв.
    ROUNDABOUT_UP_RIGHT('└'),           // Заехать можно сверху и справа, повернет робота направо или наверх соотв.
    ROUNDABOUT_RIGHT_DOWN('┌'),         // Заехать можно справа и снизу, повернет робота вниз или направо соотв.
    ROUNDABOUT_DOWN_LEFT('┐');          // Заехать можно снизу и слева, повернет робота налево или вниз соотв.

    final char ch;

    Element(char ch) {
        this.ch = ch;
    }

    @Override
    public char ch() {
        return ch;
    }

    @Override
    public String toString() {
        return String.valueOf(ch);
    }

    public static Element byCode(char ch) {
        for (Element el : Element.values()) {
            if (el.ch == ch) {
                return el;
            }
        }
        throw new IllegalArgumentException("No such element for " + ch);
    }

}
