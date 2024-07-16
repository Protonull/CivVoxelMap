package uk.protonull.civvoxelmap.features.waypoints;

import com.mamiyaotaru.voxelmap.util.Waypoint;
import net.minecraft.ResourceLocationException;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class AttemptWaypointParse {
    public static boolean isValidCoordinate(
        final @NotNull String coordinate
    ) {
        try {
            Integer.parseInt(coordinate);
            return true;
        }
        catch (final NumberFormatException ignored) {
            return false;
        }
    }

    public static boolean isValidResourceLocation(
        final @NotNull String location
    ) {
        try {
            new ResourceLocation(location);
            return true;
        }
        catch (final ResourceLocationException ignore) {
            return false;
        }
    }

    public static @Nullable Waypoint attemptParse(
        final @NotNull String raw
    ) {
        boolean hasX = false;
        boolean hasY = false;
        boolean hasZ = false;
        boolean hasDimension = false;
        for (final String pair : StringUtils.split(raw, ",")) {
            final String key, value; {
                final int splitterIndex = pair.indexOf(':');
                if (splitterIndex < 0) {
                    continue;
                }
                key = pair.substring(0, splitterIndex).toLowerCase().trim();
                value = pair.substring(splitterIndex + 1).trim();
            }
            if (key.isBlank() || value.isBlank()) {
                continue;
            }
            switch (key) {
                case "x" -> {
                    if (hasX || !AttemptWaypointParse.isValidCoordinate(value)) {
                        return null;
                    }
                    hasX = true;
                    continue;
                }
                case "y" -> {
                    if (hasY || !AttemptWaypointParse.isValidCoordinate(value)) {
                        return null;
                    }
                    hasY = true;
                    continue;
                }
                case "z" -> {
                    if (hasZ || !AttemptWaypointParse.isValidCoordinate(value)) {
                        return null;
                    }
                    hasZ = true;
                    continue;
                }
                case "dim", "dimension" -> {
                    if (hasDimension || !AttemptWaypointParse.isValidResourceLocation(value)) {
                        return null;
                    }
                    hasDimension = true;
                    continue;
                }
                case "dimensions" -> {
                    if (hasDimension) {
                        return null;
                    }
                    for (final String dimension : StringUtils.split(value, "#")) {
                        if (!AttemptWaypointParse.isValidResourceLocation(dimension)) {
                            return null;
                        }
                    }
                    hasDimension = true;
                    continue;
                }
                case "world" -> {
                    if (hasDimension) {
                        return null;
                    }
                    hasDimension = true;
                    continue;
                }
            }
        }
        if (hasX && hasY && hasZ && hasDimension) {
            return new Waypoint("", 0, 0, 0, false, 0f, 0f, 0f, "", null, null);
        }
        return null;
    }
}
