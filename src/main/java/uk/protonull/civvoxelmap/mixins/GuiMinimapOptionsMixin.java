package uk.protonull.civvoxelmap.mixins;

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
        at = @At(
            value = "STORE",
            target = "Lcom/mamiyaotaru/voxelmap/gui/overridden/GuiOptionButtonMinimap;<init>(IILcom/mamiyaotaru/voxelmap/gui/overridden/EnumOptionsMinimap;Lnet/minecraft/network/chat/Component;Lnet/minecraft/client/gui/components/Button$OnPress;)V"
        )
    )
    private @NotNull GuiOptionButtonMinimap cvm_modify_variable$addTooltipWarnings(
        final @NotNull GuiOptionButtonMinimap button
    ) {
        switch (button.returnEnumOptions()) {
            case CAVEMODE -> button.setTooltip(Tooltip.create(Component.literal("Civ (illegal): Must be disabled! Cave Mode is permitted in the Nether and VoxelMap will use it regardless of this setting.")));
        }
        return button;
    }
}
