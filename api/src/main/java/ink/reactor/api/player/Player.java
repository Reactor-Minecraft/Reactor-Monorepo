package ink.reactor.api.player;

import java.util.UUID;

import ink.reactor.api.command.CommandSender;
import ink.reactor.api.player.connection.PlayerConnection;
import ink.reactor.api.player.data.Gamemode;
import ink.reactor.api.player.data.PlayerInventory;
import ink.reactor.api.player.data.PlayerSkin;
import ink.reactor.api.world.World;
import ink.reactor.chat.ChatMode;
import ink.reactor.chat.component.ChatComponent;

import ink.reactor.entity.Entity;

import ink.reactor.entity.data.MinecraftEntity;
import ink.reactor.entity.data.adapter.LivingMetadata;
import ink.reactor.entity.data.adapter.MinecraftEntityMetadata;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public abstract class Player implements CommandSender, MinecraftEntityMetadata, LivingMetadata {
    private final MinecraftEntity minecraftEntity = new MinecraftEntity();

    private final int id = Entity.ENTITY_ID_COUNTER.incrementAndGet();
    private final PlayerSkin skin = new PlayerSkin();

    private final PlayerConnection connection;
    private final String name;
    private final UUID uuid;

    private Gamemode gamemode = Gamemode.SURVIVAL;
    private long ping;

    private String clientInfo;

    private String locale;
    private byte viewDistance;
    private ChatMode chatMode;
    private boolean chatColors;
    private int mainHand;
    private boolean textFiltering;
    private boolean serverListings;

    private float experience;

    private World world;

    public abstract PlayerInventory getInventory();
    public abstract void setTabHeaderFooter(final ChatComponent[] header, final ChatComponent[] footer);

    public abstract void showTitle(final ChatComponent[] title);
    public abstract void showTitle(final ChatComponent[] title, final ChatComponent[] subtitle);
    public abstract void showTitle(final ChatComponent[] title, final ChatComponent[] subtitle, int fadeIn, int stay, int fadeOut);
    public abstract void clearTitles();

    public abstract void sendActionBar(final ChatComponent[] component);

    public abstract void setLevel(int level);
    public abstract void setExperience(float experience);
    public abstract int getLevel();
    public abstract float getRequiredExperience();

    @Override
    public final boolean equals(Object obj) {
        return obj == this || (obj instanceof Player player && player.uuid.equals(this.uuid));
    }

    @Override
    public final int hashCode() {
        return id;
    }
}
