package uk.protonull.civvoxelmap.mixins.elevation;

import com.mamiyaotaru.voxelmap.Radar;
import com.mamiyaotaru.voxelmap.util.GameVariableAccessShim;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Radar.class)
public abstract class RadarPreventElevationFilteringMixin {
    @Redirect(
        method = "calculateMobs",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/phys/Vec3;y()D"
        )
    )
    protected double civvoxelmap$useSameElevationAsPlayer(
        final @NotNull Vec3 instance
    ) {
        return GameVariableAccessShim.yCoord();
    }
}
