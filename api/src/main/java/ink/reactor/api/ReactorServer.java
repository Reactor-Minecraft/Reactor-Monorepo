package ink.reactor.api;

import ink.reactor.api.config.server.ServerConfig;
import ink.reactor.api.player.Player;
import ink.reactor.api.plugin.PluginManager;
import ink.reactor.api.scheduler.ServerScheduler;
import ink.reactor.api.world.WorldManager;
import ink.reactor.command.CommandSender;
import ink.reactor.command.storage.CommandStorage;

import java.util.Collection;
import java.util.UUID;

public interface ReactorServer {
    ServerConfig getConfig();
    PluginManager getPluginManager();
    WorldManager getWorldManager();
    ServerScheduler getScheduler();
    CommandStorage getCommandStorage();

    String getVersionName();
    int getVersionProtocol();

    Collection<Player> getPlayers();
    Player getPlayer(final UUID uuid);

    CommandSender getConsole();

    void stop();
}