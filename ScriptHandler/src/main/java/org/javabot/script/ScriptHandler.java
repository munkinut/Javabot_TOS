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

import bsh.EvalError;
import bsh.Interpreter;
import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import org.javabot.configuration.PropertyManager;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Logger;

public class ScriptHandler {

    final Logger log = Logger.getLogger(this.getClass().getName());

    // For Beanshell scripts
    private final Interpreter interpreter;
    private final DataOutputStream outbound;
    private final String scriptPath;
    //private String file;

    // For Groovy scripts
    GroovyScriptEngine gse;
    //Binding binding;

    /** Creates new ScriptHandler */
    public ScriptHandler(DataOutputStream outbound) {
        this.outbound = outbound;

        log.info("ScriptHandler() called");
        PropertyManager pm = PropertyManager.getInstance();
        scriptPath = pm.getScriptsLocation();
        log.info("scriptPath = " + scriptPath);

        // For Beanshell scripts
        this.interpreter = new Interpreter();

        // For Groovy scripts
        //File tmpDir = new File(scriptPath);
        //String[] roots = new String[]{tmpDir.getAbsolutePath()};
        //try {
        //     this.gse = new GroovyScriptEngine(roots);
        //} catch (IOException e) {
        //    log.warning("IOException thrown : " + e.getMessage());
        //}
        //binding = new Binding();

    }
    
    public void handlePublicCmd(String channel, String nick, String hostmask, ArrayList<String> cmd) {
        if (!cmd.isEmpty()) {
            ArrayList<String> params = this.parseParams(cmd);
            String command = cmd.get(0);
            if(isBeanshellScript(command)) {
                String script = this.pathToScript(command, "bsh");
                log.info(script);
                ScriptResource scriptResource = new ScriptResource(
                        outbound, channel, nick, hostmask, params);
                try {
                    interpreter.set("scriptResource", scriptResource);
                    interpreter.source(script);
                }
                catch (EvalError e) {
                    log.severe(e.getMessage());
                }
                catch (FileNotFoundException fnfe) {
                    log.warning("Could not find script : " + script);
                }
                catch (IOException ioe) {
                    log.warning("Could not read script : " + script);
                }

            }
            else if(isGroovyScript(command)) {
                String script = this.pathToScript(command, "groovy");
                String scriptName = command + ".groovy";
                log.info(script);
                log.info(scriptName);
                ScriptResource scriptResource = new ScriptResource(
                        outbound, channel, nick, hostmask, params);
                String[] roots = new String[]{scriptPath};
                try {
                    gse = new GroovyScriptEngine(roots);
                } catch (IOException e) {
                    log.warning("IOException thrown : " + e.getMessage());
                }
                Binding binding = new Binding();
                binding.setProperty("scriptResource", scriptResource);
                try {
                    gse.run(scriptName, binding);
                } catch (ResourceException e) {
                    log.warning("ResourceException thrown : " + e.getMessage());
                    e.printStackTrace();
                } catch (ScriptException e) {
                    log.warning("ScriptException thrown : " + e.getMessage());
                }
            }
            else {
                log.warning("Script was neither Beanshell nor Groovy.");
            }
        }
    }

    // TODO : write this so we can have bsh scripts being properly identified
    private boolean isBeanshellScript(String command) {
        // Search for command in the scripts location
        // If it ends with bsh, return true
        String extension = "bsh";
        File scriptDir = new File(scriptPath);
        File[] scriptFiles = scriptDir.listFiles();
        boolean success = false;
        for (File scriptFile : Objects.requireNonNull(scriptFiles)) {
            String filename = scriptFile.getName();
            log.info("Script filename = " + filename);
            if(filename.startsWith(command) && filename.endsWith(extension)) {
                log.info("filename starts with " + command + " and ends with " + extension);
                success = true;
                break;
            }
        }
        return success;
    }

    // TODO : write this so we can have groovy scripts being properly identified
    private boolean isGroovyScript(String command) {
        // Search for command in the scripts location
        // If it ends with groovy, return true
        String extension = "groovy";
        File scriptDir = new File(scriptPath);
        File[] scriptFiles = scriptDir.listFiles();
        boolean success = false;
        for (File scriptFile : Objects.requireNonNull(scriptFiles)) {
            String filename = scriptFile.getName();
            log.info("Script filename = " + filename);
            if(filename.startsWith(command) && filename.endsWith(extension)) {
                log.info("filename starts with " + command + " and ends with " + extension);
                success = true;
                break;
            }
        }
        return success;
    }

    private String pathToScript(String command, String extension) {
        String totalPath = scriptPath + command + "." + extension;
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
