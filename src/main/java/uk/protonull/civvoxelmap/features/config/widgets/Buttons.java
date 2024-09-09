package uk.protonull.civvoxelmap.features.config.widgets;

import java.util.Objects;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public final class Buttons {
    public interface OnPressStage {
        @NotNull Button.Builder onPress(
            @NotNull Button.OnPress handler
        );
    }

    public static @NotNull OnPressStage createButton(
        final @NotNull Component message
    ) {
        Objects.requireNonNull(message);
        return new OnPressStage() {
            @Override
            public @NotNull Button.Builder onPress(
                final @NotNull Button.OnPress handler
            ) {
                return Button.builder(message, handler);
            }
        };
    }

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
