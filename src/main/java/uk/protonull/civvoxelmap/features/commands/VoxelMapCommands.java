package uk.protonull.civvoxelmap.features.commands;

import com.mamiyaotaru.voxelmap.util.CommandUtils;
import org.jetbrains.annotations.NotNull;

public final class VoxelMapCommands {
    public static boolean attemptRunCommand(
        final @NotNull String string
    ) {
        if (string.startsWith("newWaypoint")) {
            CommandUtils.waypointClicked(string);
            return true;
        }
        else if (string.startsWith("ztp")) {
            CommandUtils.teleport(string);
            return true;
        }
        return false;
    }
}
