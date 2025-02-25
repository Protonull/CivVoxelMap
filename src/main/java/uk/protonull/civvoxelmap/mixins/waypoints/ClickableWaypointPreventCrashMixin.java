package uk.protonull.civvoxelmap.mixins.waypoints;

import com.mamiyaotaru.voxelmap.VoxelConstants;
import com.mamiyaotaru.voxelmap.util.CommandUtils;
import com.mamiyaotaru.voxelmap.util.Waypoint;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CommandUtils.class)
public abstract class ClickableWaypointPreventCrashMixin {
    @Shadow(
        remap = false
    )
    private static @Nullable Waypoint createWaypointFromChat(
        final @NotNull String details
    ) {
        throw new org.apache.commons.lang3.NotImplementedException();
    }

    @Redirect(
        method = "waypointClicked",
        at = @At(
            value = "INVOKE",
            target = "Lcom/mamiyaotaru/voxelmap/util/CommandUtils;createWaypointFromChat(Ljava/lang/String;)Lcom/mamiyaotaru/voxelmap/util/Waypoint;"
        ),
        remap = false
    )
    private static Waypoint civvoxelmap$waypointClicked$safelyCatchError(
        final @NotNull String raw
    ) {
        try {
            return createWaypointFromChat(raw);
        }
        catch (final Exception thrown) {
            VoxelConstants.getLogger().warn("Something went wrong while clicking chat waypoint: {}", raw, thrown);
            return null;
        }
    }
}
