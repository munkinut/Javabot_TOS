module SecurityManager {
    requires java.activation;
    requires gnu.regexp;
    requires PropertyManager;
    requires java.logging;
    requires Util;
    requires java.xml.bind;
    exports org.javabot.security;
}