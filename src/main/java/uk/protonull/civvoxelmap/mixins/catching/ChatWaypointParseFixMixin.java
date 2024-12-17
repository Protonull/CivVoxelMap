package uk.protonull.civvoxelmap.mixins.catching;

import com.mamiyaotaru.voxelmap.util.CommandUtils;
import com.mamiyaotaru.voxelmap.util.Waypoint;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import uk.protonull.civvoxelmap.features.waypoints.AttemptWaypointParse;

@Mixin(CommandUtils.class)
public abstract class ChatWaypointParseFixMixin {
    /**
     * Whenever a waypoint is detected in chat, VoxelMap will immediately attempt to parse it, including matching it
     * against any stored dimensions. VoxelMap does this to ensure the waypoint is valid before making it clickable.
     * But something goes wrong during this process where a waypoint is generated but its dimension is null, causing an
     * NPE later in the call stack. So this method replaces that checker by checking whether the waypoint has the
     * minimum necessary data, and that anything else is at least non-blank.
     */
    @Redirect(
        method = "getWaypointStrings",
        at = @At(
            value = "INVOKE",
            target = "Lcom/mamiyaotaru/voxelmap/util/CommandUtils;createWaypointFromChat(Ljava/lang/String;)Lcom/mamiyaotaru/voxelmap/util/Waypoint;"
        ),
        remap = false
    )
    private static @Nullable Waypoint civvoxelmap$checkWaypointFormat(
        final @NotNull String raw
    ) {
        return AttemptWaypointParse.attemptParse(raw);
    }
}
