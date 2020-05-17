/*
 * Bans.java - represents a list of bans
 *
 * Copyright (C) 2020 by Warren Milburn
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

package org.javabot.security;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement
public class Bans {

    private ArrayList<Ban> bans;

    public Bans() {
        bans = new ArrayList<>();
    }

    public ArrayList<Ban> getBans() {
        return bans;
    }

    @XmlElement(name = "ban")
    public void setBans(ArrayList<Ban> bans) {
        this.bans = bans;
    }


}
