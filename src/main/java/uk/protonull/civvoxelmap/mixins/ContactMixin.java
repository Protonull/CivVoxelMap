package uk.protonull.civvoxelmap.mixins;

import com.mamiyaotaru.voxelmap.VoxelMap;
import com.mamiyaotaru.voxelmap.util.Contact;
import com.mamiyaotaru.voxelmap.util.GameVariableAccessShim;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.protonull.civvoxelmap.config.ExtraRadarSettings;

@Mixin(Contact.class)
public abstract class ContactMixin {
    @Inject(
        method = "updateLocation",
        at = @At("TAIL"),
        remap = false
    )
    public void cvm_inject$obscureElevation(
        final @NotNull CallbackInfo ci
    ) {
        if (((ExtraRadarSettings.Accessor) VoxelMap.radarOptions).hideElevation()) {
            ((Contact) (Object) this).y = GameVariableAccessShim.yCoord();
        }
    }
}
