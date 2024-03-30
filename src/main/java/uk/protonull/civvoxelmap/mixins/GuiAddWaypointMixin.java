package uk.protonull.civvoxelmap.mixins;

import com.mamiyaotaru.voxelmap.gui.GuiAddWaypoint;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GuiAddWaypoint.class)
public abstract class GuiAddWaypointMixin {
    @Shadow(remap = false)
    private boolean choosingColor;
    @Shadow(remap = false)
    private boolean choosingIcon;

    @Redirect(
        method = "keyPressed",
        at = @At(
            value = "INVOKE",
            target = "Lcom/mamiyaotaru/voxelmap/gui/GuiAddWaypoint;popupOpen()Z"
        )
    )
    private boolean cvm_redirect$isPopupOpen(
        final @NotNull GuiAddWaypoint instance
    ) {
        return this.choosingColor || this.choosingIcon;
    }

    //@Override
    public boolean shouldCloseOnEsc() {
        // Allow players to ESC out of choosing a waypoint colour / icon
        if (this.choosingColor || this.choosingIcon) {
            this.choosingColor = false;
            this.choosingIcon = false;
            return false;
        }
        return true;
    }
}
