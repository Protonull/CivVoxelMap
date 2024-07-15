package uk.protonull.civvoxelmap.features.config;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public final class RadarConfigAlignment {
    public static final int X_PADDING = 5;

    public static void realignOptionWidget(
        final @NotNull AbstractWidget widget,
        final @NotNull Screen screen,
        final @Range(from = 0, to = Integer.MAX_VALUE) int index
    ) {
        widget.setX(calculateOptionX(widget, screen, index));
        widget.setY(calculateOptionY(widget, screen, index));
    }

    public static int calculateOptionX(
        final @NotNull AbstractWidget widget,
        final @NotNull Screen screen,
        final @Range(from = 0, to = Integer.MAX_VALUE) int index
    ) {
        return switch (index % 2) {
            case 0 -> (screen.width / 2) - X_PADDING - widget.getWidth();
            case 1 -> (screen.width / 2) + X_PADDING;
            default -> throw new IllegalArgumentException("wtf is index? [" + index + "]");
        };
    }

    public static int calculateOptionY(
        final @NotNull AbstractWidget widget,
        final @NotNull Screen screen,
        final @Range(from = 0, to = Integer.MAX_VALUE) int index
    ) {
        return (screen.height / 6) + (widget.getHeight() + 4) * (index >> 1);
    }
}
