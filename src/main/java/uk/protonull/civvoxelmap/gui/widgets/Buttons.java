package uk.protonull.civvoxelmap.gui.widgets;

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
        final @NotNull String prefix,
        final boolean value
    ) {
        return Component.literal(prefix + ": " + (value ? "ON" : "OFF"));
    }

    public static <T extends Enum<T>> @NotNull Component enumMessage(
        final @NotNull String prefix,
        final T value
    ) {
        return Component.literal(prefix + ": " + value.name());
    }
}
