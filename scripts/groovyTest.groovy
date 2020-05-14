
channel = scriptResource.getChannel();
nick = scriptResource.getNick();
hostmask = scriptResource.getHostmask();
params = scriptResource.getParams();

scriptResource.msgChannel("Hello everyone!");
scriptResource.msgChannel(channel);
scriptResource.msgChannel(nick);
scriptResource.msgChannel(hostmask);

for(param in params) {
    scriptResource.msgChannel(param);
}

