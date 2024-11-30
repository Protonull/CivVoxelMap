package uk.protonull.civvoxelmap.mixins.settings;

import com.mamiyaotaru.voxelmap.gui.GuiMinimapOptions;
import com.mamiyaotaru.voxelmap.gui.overridden.GuiOptionButtonMinimap;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(GuiMinimapOptions.class)
public abstract class GuiMinimapOptionsMixin {
    // ============================================================
    // Warnings
    // ============================================================

    @ModifyVariable(
        method = "init",
        at = @At("STORE")
    )
    protected @NotNull GuiOptionButtonMinimap civvoxelmap$addTooltipWarnings(
        final @NotNull GuiOptionButtonMinimap button
    ) {
        switch (button.returnEnumOptions()) {
            case CAVEMODE -> button.setTooltip(Tooltip.create(Component.translatable("civvoxelmap.feature.illegal.tooltip.off")));
        }
        return button;
    }
}
