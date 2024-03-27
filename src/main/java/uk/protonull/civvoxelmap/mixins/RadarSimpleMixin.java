package uk.protonull.civvoxelmap.mixins;

import com.mamiyaotaru.voxelmap.RadarSettingsManager;
import com.mamiyaotaru.voxelmap.RadarSimple;
import java.util.Iterator;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import uk.protonull.civvoxelmap.config.ExtraRadarSettings;

@Mixin(RadarSimple.class)
public class RadarSimpleMixin {
    @Final
    @Shadow(remap = false)
    public RadarSettingsManager options;

    @ModifyVariable(
        method = "calculateMobs",
        at = @At(
            value = "STORE",
            target = "Ljava/lang/Iterable;iterator()Ljava/util/Iterator;"
        ),
        remap = false
    )
    private @NotNull Iterator<Entity> cvm_modify_variable$filterOutCrouchingPlayers(
        final @NotNull Iterator<Entity> iterator
    ) {
        return ExtraRadarSettings.filterEntities(iterator, (ExtraRadarSettings.Accessor) this.options);
    }
}
