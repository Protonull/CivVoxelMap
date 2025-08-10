package uk.protonull.civvoxelmap.mixins.catching;

import com.mamiyaotaru.voxelmap.VoxelConstants;
import com.mamiyaotaru.voxelmap.util.CommandUtils;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(VoxelConstants.class)
public abstract class CatchCommandThrowsMixin {
    @Shadow(
        remap = false
    )
    @Final
    private static Logger LOGGER;

    @Redirect(
        method = "onSendChatMessage",
        at = @At(
            value = "INVOKE",
            target = "Lcom/mamiyaotaru/voxelmap/util/CommandUtils;waypointClicked(Ljava/lang/String;)V"
        ),
        remap = false
    )
	private static void civvoxelmap$wrapWaypointClickedCommand(
        final @NotNull String waypoint
    ) {
        try {
            CommandUtils.waypointClicked(waypoint);
        }
        catch (final Exception err) {
            LOGGER.warn("Waypoint-click command failed!", err);
        }
    }

    @Redirect(
        method = "onSendChatMessage",
        at = @At(
            value = "INVOKE",
            target = "Lcom/mamiyaotaru/voxelmap/util/CommandUtils;teleport(Ljava/lang/String;)V"
        ),
        remap = false
    )
    private static void civvoxelmap$wrapTeleportCommand(
        final @NotNull String destination
    ) {
        try {
            CommandUtils.teleport(destination);
        }
        catch (final Exception err) {
            LOGGER.warn("Teleport command failed!", err);
        }
    }
}
