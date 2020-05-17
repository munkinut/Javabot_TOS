/*
 * TestResetJavabotProperties.java - Tests properties resetting.
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

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class TestResetJavabotProps {

    static final Logger log = Logger.getLogger("TestResetJavabotProps");

    public static void main(String[] args) {
        log.info("resetFromConfigBackup() called");

        String srcDir = "C:\\Users\\Warren\\IdeaProjects\\Javabot_TOS\\config\\";
        String srcFile = srcDir + "javabot.properties";
        log.info(srcFile);
        String destFile = srcDir + "javabot.properties.bak";
        log.info(destFile);
        File src = new File(srcFile);
        File dest = new File(destFile);
        String backupsLocation = "C:\\Users\\Warren\\IdeaProjects\\Javabot_TOS\\config_backups\\";
        File source = new File(backupsLocation + "javabot.properties");

        try {
            FileUtils.copyFile(src, dest);
            FileUtils.copyFileToDirectory(source, new File(srcDir));
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }

}
