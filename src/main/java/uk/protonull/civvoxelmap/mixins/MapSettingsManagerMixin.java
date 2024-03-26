package uk.protonull.civvoxelmap.mixins;

import com.mamiyaotaru.voxelmap.MapSettingsManager;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MapSettingsManager.class)
public class MapSettingsManagerMixin {
    @Shadow
    protected boolean showCaves;

    @Inject(
        method = "<init>",
        at = @At("TAIL")
    )
    private void cvm_inject$disableCaveModeByDefault(
        final @NotNull CallbackInfo ci
    ) {
        this.showCaves = false;
    }
}
