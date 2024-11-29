package uk.protonull.civvoxelmap.features.config.widgets;

import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public final class Buttons {
    public static @NotNull Component boolMessage(
        final @NotNull Component prefix,
        final boolean value
    ) {
        return value
            ? Component.translatable("civmodern.gui.button.bool.on", prefix)
            : Component.translatable("civmodern.gui.button.bool.off", prefix);
    }

    public static <T extends Enum<T>> @NotNull Component enumMessage(
        final @NotNull Component prefix,
        final T value
    ) {
        return Component.translatable("civmodern.gui.button.enum", prefix, value.name());
    }
}
