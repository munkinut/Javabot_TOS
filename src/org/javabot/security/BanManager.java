/*
 * BanManager.java - provides banlist management for JavaBot
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

package org.javabot.security;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Logger;

import gnu.regexp.*;
import org.javabot.configuration.PropertyManager;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class BanManager {

    final Logger log = Logger.getLogger(this.getClass().getName());

    private final String banfile;
    private final Bans bans;

    public BanManager() {
        //String fs = java.io.File.separator;
        PropertyManager pm = PropertyManager.getInstance();
        banfile = pm.getBansLocation();
        log.info("banfile = " + banfile);
        bans = this.loadBans();
    }
    
    public boolean addBan(String banmask) {
        bans.getBans().add(new Ban(banmask));
        this.saveBans();
        return true;
    }
    
    public boolean delBan(String banmask) {
        //bans.getBans().add(new Ban(banmask));
        //this.saveBans();
        return true;
    }
    
    public boolean matches(String hostmask) {
        boolean matches = false;
        String banmask;
        RE exp;
        for (Object ban : bans.getBans()) {
            banmask = ((Ban)ban).getHostmask();
            try {
                exp = new RE(this.regThis(banmask));
                if (exp.isMatch(hostmask)) {
                    matches = true;
                    break;
                }
            } catch (REException ignored) {
            }
        }
        return matches;
    }

    private String regThis(String hostmask) {
        try {
            RE nickReg = new RE("\\*");
            hostmask = nickReg.substituteAll(hostmask, "\\S*");
        }
        catch (REException ignored) {
        }
        return hostmask;
    }

    public Bans getBans() {
        return this.bans;
    }
    
    public void saveBans() {
        this.storeBans();
    }

    private synchronized Bans loadBans() {
        Bans bans = null;
        try {
            // create JAXB context and initializing Marshaller
            JAXBContext jaxbContext = JAXBContext.newInstance(Bans.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            // specify the location and name of xml file to be read
            File XMLfile = new File(banfile);

            // this will create Java object - Users from the XML file
            bans = (Bans) jaxbUnmarshaller.unmarshal(XMLfile);

            ArrayList<Ban> banList = bans.getBans();
            for(Ban ban:banList) {
                log.info("User: " + ban.getHostmask());
            }

        } catch (JAXBException e) {
            // some exception occured
            log.warning("Could not unmarshal xml file : " + e.getMessage());
        }
        return bans;
    }

    private synchronized void storeBans() {

        try {

            // create JAXB context and initializing Marshal
            JAXBContext jaxbContext = JAXBContext.newInstance(Bans.class);
            javax.xml.bind.Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // for getting nice formatted output
            jaxbMarshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            //specify the location and name of xml file to be created
            File XMLfile = new File(banfile);

            // Writing to XML file
            jaxbMarshaller.marshal(bans, XMLfile);
            // Writing to console
            jaxbMarshaller.marshal(bans, System.out);

        } catch (JAXBException e) {
            // some exception occured
            log.warning("Could not marshal xml file : " + e.getMessage());
        }


    }

}
