package uk.protonull.civvoxelmap.mixins.settings;

import com.llamalad7.mixinextras.sugar.Local;
import com.mamiyaotaru.voxelmap.gui.GuiMinimapOptions;
import com.mamiyaotaru.voxelmap.gui.overridden.GuiOptionButtonMinimap;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMinimapOptions.class)
public abstract class GuiMinimapOptionsMixin {
    // ============================================================
    // Warnings
    // ============================================================

    @Inject(
        method = "init",
        at = @At(
            value = "INVOKE",
            target = "Lcom/mamiyaotaru/voxelmap/gui/GuiMinimapOptions;addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;",
            ordinal = 0,
            shift = At.Shift.BEFORE
        )
    )
    private void cvm$init$addTooltipWarnings(
        final @NotNull CallbackInfo ci,
        final @NotNull @Local GuiOptionButtonMinimap button
    ) {
        switch (button.returnEnumOptions()) {
            case CAVEMODE -> button.setTooltip(Tooltip.create(Component.literal("Civ (illegal): Must be OFF!")));
        }
    }
}
