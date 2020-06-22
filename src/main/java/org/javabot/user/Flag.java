/*
 * Flag.java - represents a flag or option
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

package org.javabot.user;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.logging.Logger;

@XmlRootElement(namespace = "org.javabot.user.Users")
@XmlType(propOrder = { "name", "truth"})
public class Flag {

    Logger log = Logger.getLogger(this.getClass().getName());

    public static final String FRIEND = "FRIEND";
    public static final String OP = "OP";
    public static final String MASTER = "MASTER";
    public static final String OWNER = "OWNER";
    public static final String VOICE = "VOICE";

    private String name;
    private boolean truth;

    public Flag() {
        this.name = FRIEND;
        this.truth = true;
    }

    public Flag(String name, boolean truth) {
        this.name = name;
        this.truth = truth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTruth() {
        return truth;
    }

    public void setTruth(boolean truth) {
        this.truth = truth;
    }

}
