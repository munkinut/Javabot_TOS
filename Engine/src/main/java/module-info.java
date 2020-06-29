module Engine {
    exports org.javabot.engine;
    requires PropertyManager;
    requires SecurityManager;
    requires java.logging;
    requires Util;
    requires JBotNetClient;
    requires BotNet;
    requires Message;
    requires ScriptHandler;
    requires UserManager;

}