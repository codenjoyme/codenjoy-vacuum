package com.codenjoy.dojo.vacuum.model.items;

import com.codenjoy.dojo.services.Point;

public interface Passable {
    boolean canEnterFrom(Point pt);
}
