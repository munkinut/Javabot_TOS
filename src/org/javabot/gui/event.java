/*
 * event.java
 *
 * Copyright (C) 2001 by Torsten Born
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

package org.javabot.gui;

import java.util.logging.Logger;

class event extends java.awt.event.WindowAdapter {

    Logger log = Logger.getLogger(this.getClass().getName());

    public void windowClosing(java.awt.event.WindowEvent e) {
    
      System.exit(0);
    }
 
	 
}
