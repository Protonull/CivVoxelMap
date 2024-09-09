package uk.protonull.civvoxelmap.mixins.settings;

import com.mamiyaotaru.voxelmap.gui.GuiAddWaypoint;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.gui.components.events.GuiEventListener;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uk.protonull.civvoxelmap.Helpers;

@Mixin(GuiAddWaypoint.class)
public abstract class WaypointPickerFixMixin {
    @Shadow(remap = false)
    private boolean choosingColor;
    @Shadow(remap = false)
    private boolean choosingIcon;

    @Inject(
        method = "keyPressed",
        at = @At("TAIL"),
        cancellable = true
    )
    protected void cvm$closePopupOnEsc(
        final int keyCode,
        final int scanCode,
        final int modifiers,
        final @NotNull CallbackInfoReturnable<Boolean> cir
    ) {
        final GuiAddWaypoint self = Helpers.hardCast(this);
        // .popupOpen() is a misnomer, it returns true when the screen is open, but the "popups" are closed.
        if (!self.popupOpen() && keyCode == InputConstants.KEY_ESCAPE) {
            this.choosingColor = false;
            this.choosingIcon = false;
            for (final GuiEventListener element : self.getButtonList()) {
                element.setFocused(false);
            }
            cir.setReturnValue(true);
        }
    }

    //@Override
    public boolean shouldCloseOnEsc() {
        // Allow players to ESC out of choosing a waypoint colour / icon
        return !this.choosingColor && !this.choosingIcon;
    }
}
