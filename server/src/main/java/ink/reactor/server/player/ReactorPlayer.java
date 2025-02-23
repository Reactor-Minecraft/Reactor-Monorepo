package ink.reactor.server.player;

import java.util.UUID;

import ink.reactor.api.inventory.InventoryHolder;
import ink.reactor.api.player.ExperienceCalculator;
import ink.reactor.api.player.Player;
import ink.reactor.api.player.connection.PlayerConnection;
import ink.reactor.api.player.data.PlayerInventory;
import ink.reactor.chat.component.ChatComponent;
import ink.reactor.chat.component.RawComponent;
import ink.reactor.chat.util.ComponentCombiner;
import ink.reactor.protocol.outbound.play.*;
import ink.reactor.server.player.adapter.PlayerLiving;
import lombok.Getter;

public final class ReactorPlayer extends Player implements InventoryHolder {

    private final ReactorPlayerInventory reactorPlayerInventory = new ReactorPlayerInventory(this);

    @Getter
    private final PlayerLiving living = PlayerLiving.create(this);

    public ReactorPlayer(String name, UUID uuid, PlayerConnection connection) {
        super(connection, name, uuid);
    }

    @Override
    public void sendMessage(final String message) {
        sendMessage(new RawComponent(message));
    }

    @Override
    public void sendMessage(final String... messages) {
        final PacketOutSystemChat[] packets = new PacketOutSystemChat[messages.length];
        for (int i = 0; i < messages.length; i++) {
            packets[i] = new PacketOutSystemChat(new RawComponent(messages[i]));
        }
        getConnection().sendPackets(packets);
    }

    @Override
    public void sendMessage(final ChatComponent[] components) {
        getConnection().sendPackets(new PacketOutSystemChat(ComponentCombiner.toNBT(components)));
    }

    @Override
    public void sendMessage(final ChatComponent component) {
        getConnection().sendPacket(new PacketOutSystemChat(component));
    }

    public PlayerInventory getInventory() {
        return reactorPlayerInventory;
    }

    @Override
    public void setTabHeaderFooter(final ChatComponent[] header, final ChatComponent[] footer) {
        getConnection().sendPacket(new PacketOutSetTablist(header, footer));
    }

    @Override
    public void showTitle(final ChatComponent[] title) {
        getConnection().sendPacket(new PacketOutSetTitleText(title));
        getConnection().sendPacket(new PacketOutSetTitleAnimationTimes(10, 70, 20));
    }

    @Override
    public void showTitle(final ChatComponent[] title, final ChatComponent[] subtitle) {
        showTitle(title);
        getConnection().sendPacket(new PacketOutSetSubtitleText(subtitle));
    }

    @Override
    public void showTitle(final ChatComponent[] title, final ChatComponent[] subtitle, final int fadeIn, final int stay, final int fadeOut) {
        getConnection().sendPacket(new PacketOutSetTitleText(title));
        getConnection().sendPacket(new PacketOutSetSubtitleText(subtitle));
        getConnection().sendPacket(new PacketOutSetTitleAnimationTimes(fadeIn, stay, fadeOut));
    }

    @Override
    public void clearTitles() {
        getConnection().sendPacket(new PacketOutClearTitles(true));
    }

    @Override
    public void sendActionBar(ChatComponent[] component) {
        getConnection().sendPacket(new PacketOutSetActionBarText(component));
    }

    @Override
    public void setLevel(int level) {
        final int totalExperience = ExperienceCalculator.getRecollectedExperience(level);
        final float barPercentage = ExperienceCalculator.getExperienceBarPercentage(totalExperience);
        getConnection().sendPacket(new PacketOutSetExperience(barPercentage, level, totalExperience));
    }

    @Override
    public void setExperience(float experience) {
        final int level = ExperienceCalculator.getCurrentLevelFromExperience(experience);
        final float barPercentage = ExperienceCalculator.getExperienceBarPercentage(experience);
        getConnection().sendPacket(new PacketOutSetExperience(barPercentage, level, (int) experience));
    }

    @Override
    public int getLevel() {
        final float experience = getExperience();
        return ExperienceCalculator.getCurrentLevelFromExperience(experience);
    }

    @Override
    public float getRequiredExperience() {
        final int level = getLevel();
        return ExperienceCalculator.getRequiredExperienceToLevelUp(level);
    }
}
