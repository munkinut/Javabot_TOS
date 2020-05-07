/*
 * RandomGenerator.java - produces strings of random characters
 *
 * Copyright (C) 2001 by Warren Milburn
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */

package org.javabot.util;

import java.util.Random;
import java.util.Date;

public abstract class RandomGenerator {
    
    private final Random random;
    private final char[] select = {'A','B','C','D','E','F','G','H','J','K','L',
    'M','N','P','Q','R','S','T','U','V','W','X',
    'Y','Z',
    'a','b','c','d','e','f','g','h','i','j','k',
    'm','n','o','p','q','r','s','t','u','v','w','x',
    'y','z',
    '2','3','4','5','6','7','8','9'};
    
    public RandomGenerator() {
        random = new Random(new Date().getTime());
    }
    
    @SuppressWarnings("SameParameterValue")
    protected String generateCode(int chars) {
        char[] pass = new char[chars];
        int index;
        for (int i = 0; i < chars; i++) {
            index = random.nextInt(select.length);
            pass[i] = select[index];
        }
        return new String(pass);
    }
    
    public abstract String generate();
    
}