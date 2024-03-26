package uk.protonull.civvoxelmap.config;

import com.mamiyaotaru.voxelmap.gui.GuiRadarOptions;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public final class ExtraRadarSettings {
    public interface Accessor {
        boolean hideElevation();
        void hideElevation(boolean hideElevation);

        boolean hideSneaking();
        void hideSneaking(boolean hideSneaking);
    }

    private static @NotNull Component createButtonText(
        final @NotNull String prefix,
        final boolean enabled
    ) {
        return Component.literal(prefix + ": " + (enabled ? "ON" : "OFF"));
    }

    public static @NotNull Button createHideElevationButton(
        final @NotNull GuiRadarOptions screen,
        final @NotNull Accessor extra,
        final int index
    ) {
        final String PREFIX = "Hide elevation";
        final Button button = Button.builder(
                createButtonText(PREFIX, extra.hideElevation()),
                (self) -> {
                    final boolean newValue = !extra.hideElevation();
                    extra.hideElevation(newValue);
                    self.setMessage(createButtonText(PREFIX, newValue));
                }
            )
            .tooltip(Tooltip.create(Component.literal("Civ (required): Disable VoxelMap's brightness and opacity elevation indicator?")))
            .build();
        RadarConfigAlignment.realignOptionWidget(button, screen, index);
        return button;
    }

    public static @NotNull Button createHideSneakingButton(
        final @NotNull GuiRadarOptions screen,
        final @NotNull Accessor extra,
        final int index
    ) {
        final String PREFIX = "Hide sneaking";
        final Button button = Button.builder(
                createButtonText(PREFIX, extra.hideSneaking()),
                (self) -> {
                    final boolean newValue = !extra.hideSneaking();
                    extra.hideSneaking(newValue);
                    self.setMessage(createButtonText(PREFIX, newValue));
                }
            )
            .tooltip(Tooltip.create(Component.literal("Civ (Fair Play): Hide crouching players? (slight delay)")))
            .build();
        RadarConfigAlignment.realignOptionWidget(button, screen, index);
        return button;
    }

    public static @NotNull Iterator<Entity> filterOutSneakingPlayers(
        final @NotNull Iterator<Entity> iterator,
        final @NotNull Accessor extra
    ) {
        if (!extra.hideSneaking()) {
            return iterator;
        }
        final var entities = new ArrayList<Entity>();
        while (iterator.hasNext()) {
            final Entity entity = iterator.next();
            if (entity instanceof final Player player && player.isCrouching()) {
                continue;
            }
            entities.add(entity);
        }
        return entities.iterator();
    }
}
