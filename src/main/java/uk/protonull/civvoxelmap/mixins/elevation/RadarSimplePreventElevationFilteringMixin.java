package uk.protonull.civvoxelmap.mixins.elevation;

import com.mamiyaotaru.voxelmap.RadarSimple;
import com.mamiyaotaru.voxelmap.util.GameVariableAccessShim;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RadarSimple.class)
public abstract class RadarSimplePreventElevationFilteringMixin {
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
