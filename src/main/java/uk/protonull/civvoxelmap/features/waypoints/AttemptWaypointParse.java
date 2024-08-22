package uk.protonull.civvoxelmap.features.waypoints;

import com.mamiyaotaru.voxelmap.util.Waypoint;
import net.minecraft.ResourceLocationException;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class AttemptWaypointParse {
    public static boolean isValidInteger(
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
            ResourceLocation.tryParse(location);
            return true;
        }
        catch (final ResourceLocationException ignore) {
            return false;
        }
    }

    public static boolean isValidFloat(
        final @NotNull String coordinate
    ) {
        try {
            Float.parseFloat(coordinate);
            return true;
        }
        catch (final NumberFormatException ignored) {
            return false;
        }
    }

    public static @Nullable Waypoint attemptParse(
        final @NotNull String raw
    ) {
        boolean hasName = false;
        boolean hasX = false;
        boolean hasY = false;
        boolean hasZ = false;
        boolean hasDimension = false;
        boolean hasEnabled = false;
        boolean hasRed = false;
        boolean hasGreen = false;
        boolean hasBlue = false;
        boolean hasIcon = false;
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
                case "name" -> {
                    if (hasName) {
                        return null;
                    }
                    hasName = true;
                    continue;
                }
                case "x" -> {
                    if (hasX || !isValidInteger(value)) {
                        return null;
                    }
                    hasX = true;
                    continue;
                }
                case "y" -> {
                    if (hasY || !isValidInteger(value)) {
                        return null;
                    }
                    hasY = true;
                    continue;
                }
                case "z" -> {
                    if (hasZ || !isValidInteger(value)) {
                        return null;
                    }
                    hasZ = true;
                    continue;
                }
                case "dim", "dimension" -> {
                    if (hasDimension || !isValidResourceLocation(value)) {
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
                        if (!isValidResourceLocation(dimension)) {
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
                case "enabled" -> {
                    if (hasEnabled) {
                        return null;
                    }
                    hasEnabled = true;
                    continue;
                }
                case "red" -> {
                    if (hasRed || !isValidFloat(value)) {
                        return null;
                    }
                    hasRed = true;
                    continue;
                }
                case "green" -> {
                    if (hasGreen || !isValidFloat(value)) {
                        return null;
                    }
                    hasGreen = true;
                    continue;
                }
                case "blue" -> {
                    if (hasBlue || !isValidFloat(value)) {
                        return null;
                    }
                    hasBlue = true;
                    continue;
                }
                case "color", "colour" -> {
                    if (hasRed || hasGreen || hasBlue || !isValidInteger(value)) {
                        return null;
                    }
                    hasRed = true;
                    hasGreen = true;
                    hasBlue = true;
                    continue;
                }
                case "suffix", "icon" -> {
                    if (hasIcon) {
                        return null;
                    }
                    hasIcon = true;
                    continue;
                }
            }
        }
        if (hasX && hasZ) {
            return new Waypoint("", 0, 0, 0, false, 0f, 0f, 0f, "", null, null);
        }
        return null;
    }
}
