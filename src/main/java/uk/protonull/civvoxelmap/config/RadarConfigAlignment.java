package uk.protonull.civvoxelmap.config;

import com.mamiyaotaru.voxelmap.gui.GuiRadarOptions;
import net.minecraft.client.gui.components.AbstractWidget;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public final class RadarConfigAlignment {
    public interface Accessor {
        int getNextOptionIndex();
    }

    public static void realignOptionWidget(
        final @NotNull AbstractWidget widget,
        final @NotNull GuiRadarOptions screen,
        final int index
    ) {
        widget.setX(calculateOptionX(widget, screen, index));
        widget.setY(calculateOptionY(widget, screen, index));
    }

    public static int calculateOptionX(
        final @NotNull AbstractWidget widget,
        final @NotNull GuiRadarOptions screen,
        final @Range(from = 0, to = Integer.MAX_VALUE) int index
    ) {
        return switch (index % 2) {
            case 0 -> (screen.width / 2) - 5 - widget.getWidth();
            case 1 -> (screen.width / 2) + 5;
            default -> throw new IllegalArgumentException("wtf is index? [" + index + "]");
        };
    }

    public static int calculateOptionY(
        final @NotNull AbstractWidget widget,
        final @NotNull GuiRadarOptions screen,
        final @Range(from = 0, to = Integer.MAX_VALUE) int index
    ) {
        return (screen.getHeight() / 6) + (widget.getHeight() + 4) * (index >> 1);
    }
}
