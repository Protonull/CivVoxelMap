package uk.protonull.civvoxelmap.config;

import com.mamiyaotaru.voxelmap.gui.GuiRadarOptions;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public final class ExtraRadarSettings {
    public interface Accessor {
        boolean hideElevation();
        void hideElevation(boolean hideElevation);
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
            .tooltip(Tooltip.create(Component.literal("Civ: Disable VoxelMap's brightness and opacity elevation indicator?")))
            .build();
        RadarConfigAlignment.realignOptionWidget(button, screen, index);
        return button;
    }
}
