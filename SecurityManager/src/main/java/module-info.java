module SecurityManager {
    requires PropertyManager;
    requires Util;
    requires java.logging;
    requires gnu.regexp;
    requires java.xml.bind;
    requires transitive java.activation;
    exports org.javabot.security;
}