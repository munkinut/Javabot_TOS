package org.javabot.play;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class TestResetJavabotProps {

    static Logger log = Logger.getLogger("TestResetJavabotProps");

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
