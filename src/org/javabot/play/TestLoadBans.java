/*
 * TestLoadBans.java - Tests loading of the bans file.
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

package org.javabot.play;

import org.javabot.security.Ban;
import org.javabot.security.Bans;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.logging.Logger;

public class TestLoadBans {

    Logger log = Logger.getLogger(this.getClass().getName());

    public static void main(String[] args) {
        Bans bans = loadBans();
        for (Ban ban:bans.getBans()) {
            System.out.println("Ban mask = " + ban.getHostmask());
        }
    }

    private static synchronized Bans loadBans() {
        Bans bans = null;
        try {
            // create JAXB context and initializing Marshaller
            JAXBContext jaxbContext = JAXBContext.newInstance(Bans.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            // specify the location and name of xml file to be read
            File XMLfile = new File("C:\\Users\\Warren\\IdeaProjects\\Javabot_TOS\\config\\bans.xml");

            // this will create Java object - country from the XML file
            bans = (Bans) jaxbUnmarshaller.unmarshal(XMLfile);

        } catch (JAXBException e) {
            // some exception occured
            e.printStackTrace();
        }
        return bans;
    }
}
