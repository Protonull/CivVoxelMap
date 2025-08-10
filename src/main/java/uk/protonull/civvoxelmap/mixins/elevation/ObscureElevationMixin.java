package uk.protonull.civvoxelmap.mixins.elevation;

import com.mamiyaotaru.voxelmap.VoxelMap;
import com.mamiyaotaru.voxelmap.util.Contact;
import com.mamiyaotaru.voxelmap.util.GameVariableAccessShim;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.protonull.civvoxelmap.features.config.ExtraRadarSettings;

/**
 * Mixins aren't really sophisticated enough to target the code within the spaghetti of VoxelMap that darkens/fades
 * "contacts" that are above or below the player, so the solution here is to set this "contacts" Y-level to be the same
 * as the player.
 */
@Mixin(Contact.class)
public abstract class ObscureElevationMixin {
    @Shadow(
        remap = false
    )
    public double y;

    @Inject(
        method = "updateLocation",
        at = @At("TAIL"),
        remap = false
    )
    protected void civvoxelmap$useSameElevationAsPlayer(
        final @NotNull CallbackInfo ci
    ) {
        if (((ExtraRadarSettings.Accessor) VoxelMap.radarOptions).hideElevation()) {
            this.y = GameVariableAccessShim.yCoord();
        }
    }
}
