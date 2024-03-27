package uk.protonull.civvoxelmap.gui.widgets;

import java.util.Objects;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public final class Buttons {
    public static final Button.OnPress DEFAULT_ON_PRESS = (button) -> {};

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
}
