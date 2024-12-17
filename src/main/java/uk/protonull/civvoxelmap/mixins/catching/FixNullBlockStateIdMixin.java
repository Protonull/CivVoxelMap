package uk.protonull.civvoxelmap.mixins.catching;

import com.mamiyaotaru.voxelmap.persistent.CompressibleMapData;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

/**
 * TODO: This should no longer be an issue after 1.21 update
 * @see <a href="https://github.com/fantahund/VoxelMap/commit/2982c416fd8ecf4c0331bc103e0c11bf65e511cc">2982c416fd8ecf4c0331bc103e0c11bf65e511cc</a>
 */
@Mixin(CompressibleMapData.class)
public abstract class FixNullBlockStateIdMixin {
    @ModifyVariable(
        method = "getIDFromState",
        at = @At("TAIL"),
        remap = false
    )
    protected @NotNull Integer civvoxelmap$zeroOutNullIds(
        final Integer id
    ) {
        return id == null ? 0 : id;
    }
}
