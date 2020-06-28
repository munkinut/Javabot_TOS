/*
 * Ban.java - Represents a ban
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

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(namespace = "net.munki.jaxb.Bans")
@XmlType(propOrder = {"hostmask"})
public class Ban {

    private String hostmask;

    public Ban() {
    }

    public Ban(String hostmask) {
        this.hostmask = hostmask;
    }

    public String getHostmask() {
        return hostmask;
    }

    public void setHostmask(String hostmask) {
        this.hostmask = hostmask;
    }

}
