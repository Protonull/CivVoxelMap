package uk.protonull.civvoxelmap.mixins;

import com.mamiyaotaru.voxelmap.gui.GuiRadarOptions;
import com.mamiyaotaru.voxelmap.gui.overridden.EnumOptionsMinimap;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import uk.protonull.civvoxelmap.config.RadarConfigAlignment;

@Mixin(GuiRadarOptions.class)
public abstract class GuiRadarOptionsMixin implements RadarConfigAlignment.Accessor {
    // ============================================================
    // Realignment
    // ============================================================

    @Unique
    private int cvm$optionIndex = 0;

    @Unique
    @Override
    public int getNextOptionIndex() {
        return this.cvm$optionIndex++;
    }

    @ModifyVariable(
        method = "init",
        at = @At("STORE")
    )
    private EnumOptionsMinimap[] cvm_modify_variable$getOptionCount(
        final EnumOptionsMinimap[] options
    ) {
        this.cvm$optionIndex = options.length;
        return options;
    }

    @ModifyArg(
        method = "init",
        at = @At(
            value = "INVOKE",
            target = "Lcom/mamiyaotaru/voxelmap/gui/GuiRadarOptions;addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;",
            ordinal = 1
        ),
        index = 0
    )
    private <T extends GuiEventListener & Renderable & NarratableEntry> T cvm_modify_arg$realignMobsToDisplayButton(
        final T widget
    ) {
        // Reposition the "Mobs to Display..." button so it doesn't look so out of place.
        RadarConfigAlignment.realignOptionWidget(
            (Button) widget,
            (GuiRadarOptions) (Object) this,
            ((RadarConfigAlignment.Accessor) this).getNextOptionIndex()
        );
        return widget;
    }
}
