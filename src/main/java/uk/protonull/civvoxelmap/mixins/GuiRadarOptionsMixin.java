package uk.protonull.civvoxelmap.mixins;

import com.mamiyaotaru.voxelmap.RadarSettingsManager;
import com.mamiyaotaru.voxelmap.gui.GuiRadarOptions;
import com.mamiyaotaru.voxelmap.gui.overridden.EnumOptionsMinimap;
import com.mamiyaotaru.voxelmap.gui.overridden.GuiOptionButtonMinimap;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.protonull.civvoxelmap.config.ExtraRadarSettings;
import uk.protonull.civvoxelmap.config.RadarConfigAlignment;
import uk.protonull.civvoxelmap.mixins.accessors.ScreenAccessor;

@Mixin(GuiRadarOptions.class)
public abstract class GuiRadarOptionsMixin implements RadarConfigAlignment.Accessor, ScreenAccessor {
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

    // ============================================================
    // Custom Options
    // ============================================================

    @Final
    @Shadow
    private RadarSettingsManager options;

    @Inject(
        method = "init",
        at = @At(
            value = "INVOKE",
            target = "Lcom/mamiyaotaru/voxelmap/gui/GuiRadarOptions;addRenderableWidget(Lnet/minecraft/client/gui/components/events/GuiEventListener;)Lnet/minecraft/client/gui/components/events/GuiEventListener;",
            ordinal = 2, // Put after "Mobs to Display..." button
            shift = At.Shift.BEFORE
        )
    )
    private void cvm_inject$addExtraOptions(
        final @NotNull CallbackInfo ci
    ) {
        cvm_invoker$addRenderableWidget(ExtraRadarSettings.createHideElevationButton(
            (GuiRadarOptions) (Object) this,
            (ExtraRadarSettings.Accessor) this.options,
            getNextOptionIndex()
        ));

        cvm_invoker$addRenderableWidget(ExtraRadarSettings.createHideSneakingButton(
            (GuiRadarOptions) (Object) this,
            (ExtraRadarSettings.Accessor) this.options,
            getNextOptionIndex()
        ));
    }

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
            case SHOWPLAYERHELMETS, SHOWMOBHELMETS -> button.setTooltip(Tooltip.create(Component.literal("Civ (illegal): Must be disabled as it reads entity data!")));
        }
        return button;
    }
}
