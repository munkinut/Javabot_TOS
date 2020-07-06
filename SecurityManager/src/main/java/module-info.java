module SecurityManager {
    requires PropertyManager;
    requires Util;
    requires java.logging;
    requires gnu.regexp;
    requires java.xml.bind;
    exports org.javabot.security;
}