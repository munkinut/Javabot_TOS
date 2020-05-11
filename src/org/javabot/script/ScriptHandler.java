/*
 * ScriptHandler.java - handles scripted commands for JavaBot
 *
 * Copyright (C) 2002 by Warren Milburn
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

package org.javabot.script;

import bsh.*;
import org.javabot.configuration.PropertyManager;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

public class ScriptHandler {

    final Logger log = Logger.getLogger(this.getClass().getName());

    private final Interpreter interpreter;
    private final DataOutputStream outbound;
    private final String scriptPath;

    /** Creates new ScriptHandler */
    public ScriptHandler(DataOutputStream outbound) {
        log.info("ScriptHandler() called");
        Properties properties = PropertyManager.getInstance().getProperties();
        scriptPath = properties.getProperty("Scripts_Location");
        log.info("scriptPath = " + scriptPath);

        this.interpreter = new Interpreter();
        this.outbound = outbound;
    }
    
    public void handlePublicCmd(String channel, String nick, String hostmask, ArrayList<String> cmd) {
        if (!cmd.isEmpty()) {
            String command = cmd.get(0);
            ArrayList<String> params = this.parseParams(cmd);
            String script = this.pathToScript(command);
            ScriptResource scriptResource = new ScriptResource(
                outbound, channel, nick, hostmask, params);
            try {
                interpreter.set("scriptResource", scriptResource);
                interpreter.source(script);
            }
            catch (EvalError e) {
                e.printStackTrace();
            }
            catch (FileNotFoundException fnfe) {
                System.err.println("Could not find script : " + script);
            }
            catch (IOException ioe) {
                System.err.println("Could not read script : " + script);
                ioe.printStackTrace();
            }
        }
    }
    
    private String pathToScript(String command) {
        String totalPath = scriptPath + command + ".bsh";
        log.info("Looking for script at " + totalPath);
        return totalPath;
    }

    private ArrayList<String> parseParams(ArrayList<String> cmd) {
        ArrayList<String> v = new ArrayList<>();
        if (cmd.size() > 1) {
            v.addAll(cmd.subList(1,cmd.size()));
        }
        return v;
    }
}
