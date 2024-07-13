package uk.protonull.civvoxelmap.mixins.settings;

import com.mamiyaotaru.voxelmap.MapSettingsManager;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MapSettingsManager.class)
public abstract class MapSettingsManagerMixin {
    @Shadow(
        remap = false
    )
    protected boolean showCaves;

    @Inject(
        method = "<init>",
        at = @At("TAIL"),
        remap = false
    )
    private void cvm$constructor$disableCaveModeByDefault(
        final @NotNull CallbackInfo ci
    ) {
        this.showCaves = false;
    }
}
