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
import java.io.*;
import java.util.*;

public class ScriptHandler {
    
    private Interpreter interpreter;
    private DataOutputStream outbound;

    /** Creates new ScriptHandler */
    public ScriptHandler(DataOutputStream outbound) {
        this.interpreter = new Interpreter();
        this.outbound = outbound;
    }
    
    public void handlePublicCmd(String channel, String nick, String hostmask, Vector cmd) {
        if (!cmd.isEmpty()) {
            String command = (String)cmd.get(0);
            Vector params = this.parseParams(cmd);
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
        String fs = File.separator;
        String currentPath = System.getProperty("user.dir");
        //String currentPath = "d:\\projects\\javabot\\compiled";
        StringBuffer path = new StringBuffer(currentPath);
        path.append(fs);
        path.append("org");
        path.append(fs);
        path.append("javabot");
        path.append(fs);
        path.append("scripts");
        path.append(fs);
        path.append(command);
        path.append(".bsh");
        return path.toString();
    }

    private Vector parseParams(Vector cmd) {
        Vector v = new Vector();
        if (cmd.size() > 1) {
            v.addAll(cmd.subList(1,cmd.size()));
        }
        return v;
    }
}
