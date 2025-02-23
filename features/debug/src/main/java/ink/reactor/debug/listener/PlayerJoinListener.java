package ink.reactor.debug.listener;

import java.util.List;
import java.util.Map;

import ink.reactor.api.player.Player;
import ink.reactor.api.player.event.PlayerJoinEvent;
import ink.reactor.api.plugin.listener.Listener;
import ink.reactor.api.scheduler.TickDuration;
import ink.reactor.chat.ChatColor;
import ink.reactor.chat.component.FullComponent;
import ink.reactor.chat.format.ChatLegacy;
import ink.reactor.chat.interactivity.ClickEvent;
import ink.reactor.chat.interactivity.HoverEvent;
import ink.reactor.entity.effect.MobEffect;
import ink.reactor.item.ItemStack;
import ink.reactor.item.Material;
import ink.reactor.item.component.ItemComponent;
import ink.reactor.item.data.potion.PotionEffectType;

public class PlayerJoinListener {

    @Listener
    public void onJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        player.sendMessageLegacy("""

            &bReactor &7- Debug plugin 

            &fHi, thanks for using reactor
            &fYou can see the progress in:
        """);

        final FullComponent fullComponent = new FullComponent("    Go to github page", ChatColor.YELLOW);
        fullComponent.setClickEvent(ClickEvent.openUrl("https://github.com/Reactor-Minecraft/Reactor"));
        fullComponent.setHoverEvent(HoverEvent.showText("a"));

        player.addEffect(new MobEffect(PotionEffectType.STRENGTH, 0, TickDuration.ofSeconds(5).duration()));

        player.sendMessage(fullComponent);

        player.setTabHeaderFooter(
            ChatLegacy.format("&aExample &fHeader"),
            ChatLegacy.format("&dExample &eFooter")
        );

        player.showTitle(
                ChatLegacy.format("&aExample Title"),
                ChatLegacy.format("&7Example Subtitle")
        );

        event.getPlayer().sendActionBar(ChatLegacy.format("&dAction bar text example"));

        final ItemStack itemStack = new ItemStack(Material.DIAMOND_HELMET);
        itemStack.getComponents().put(ItemComponent.LORE, List.of(
            512,
            "test",
            Map.of("Key", "value")
        ));

        itemStack.getComponents().put(ItemComponent.CUSTOM_NAME, ChatLegacy.format("&aCustom &e&lItem"));
        event.getPlayer().getInventory().setItem(36, itemStack);

        event.getPlayer().setExperience(3500F);
    }
}
