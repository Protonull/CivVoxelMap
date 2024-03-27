package uk.protonull.civvoxelmap.mixins;

import com.mamiyaotaru.voxelmap.RadarSettingsManager;
import com.mamiyaotaru.voxelmap.VoxelConstants;
import com.mamiyaotaru.voxelmap.gui.GuiMobs;
import com.mamiyaotaru.voxelmap.gui.GuiRadarOptions;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import uk.protonull.civvoxelmap.MagicValues;
import uk.protonull.civvoxelmap.config.RadarConfigAlignment;
import uk.protonull.civvoxelmap.config.RadarOption;
import uk.protonull.civvoxelmap.gui.widgets.Buttons;
import uk.protonull.civvoxelmap.gui.widgets.RadarOptionButton;
import uk.protonull.civvoxelmap.mixins.accessors.ScreenAccessor;

@Mixin(GuiRadarOptions.class)
public abstract class GuiRadarOptionsMixin implements ScreenAccessor {
    @Final
    @Shadow
    private Screen parent;

    @Final
    @Shadow(remap = false)
    private RadarSettingsManager options;

    @Shadow
    protected final Component screenTitle = Component.translatable("options.minimap.radar.title");

    @Unique
    private int optionIndex;

    @Unique
    private @NotNull Button addOptionButton(
        final @NotNull Button button
    ) {
        RadarConfigAlignment.realignOptionWidget(button, (GuiRadarOptions) (Object) this, this.optionIndex++);
        cvm_invoker$addRenderableWidget(button);
        return button;
    }

    @SuppressWarnings("OverwriteAuthorRequired")
    @Overwrite
    public void init() {
        final var screen = (GuiRadarOptions) (Object) this;
        cvm_invoker$clearWidgets();
        cvm_invoker$clearFocus();
        this.optionIndex = 0;

        addOptionButton(new RadarOptionButton<>(this.options, RadarOption.ENABLED, (button) -> {
            for (final GuiEventListener widget : screen.children()) {
                if (widget instanceof final RadarOptionButton<?> optionButton) {
                    optionButton.update();
                }
            }
        }));
        addOptionButton(new RadarOptionButton<>(this.options, RadarOption.MODE, (button) -> init()));

        switch (this.options.radarMode) {
            case MagicValues.RADAR_MODE_SIMPLE -> initSimple();
            case MagicValues.RADAR_MODE_FULL -> initFull();
        }

        cvm_invoker$addRenderableWidget(
            Buttons.createButton(Component.translatable("gui.done"))
                .onPress((button) -> VoxelConstants.getMinecraft().setScreen(this.parent))
                .bounds(screen.width / 2 - 100, screen.height / 6 + 168, 200, 20)
                .build()
        );
    }

    @Unique
    private void initSimple() {
        addOptionButton(new RadarOptionButton<>(this.options, RadarOption.SHOW_HOSTILES));
        addOptionButton(new RadarOptionButton<>(this.options, RadarOption.SHOW_NEUTRALS));
        addOptionButton(new RadarOptionButton<>(this.options, RadarOption.SHOW_PLAYERS));
        addOptionButton(new RadarOptionButton<>(this.options, RadarOption.SHOW_FACING));
        addOptionButton(new RadarOptionButton<>(this.options, RadarOption.HIDE_ELEVATION));
        addOptionButton(new RadarOptionButton<>(this.options, RadarOption.HIDE_SNEAKING));
    }

    @Unique
    private void initFull() {
        final var screen = (GuiRadarOptions) (Object) this;
        addOptionButton(new RadarOptionButton<>(this.options, RadarOption.SHOW_HOSTILES));
        addOptionButton(new RadarOptionButton<>(this.options, RadarOption.SHOW_NEUTRALS));
        addOptionButton(new RadarOptionButton<>(this.options, RadarOption.SHOW_PLAYERS));
        addOptionButton(new RadarOptionButton<>(this.options, RadarOption.SHOW_PLAYER_NAMES));
        addOptionButton(new RadarOptionButton<>(this.options, RadarOption.SHOW_MOB_NAMES));
        addOptionButton(new RadarOptionButton<>(this.options, RadarOption.SHOW_PLAYER_HELMETS));
        addOptionButton(new RadarOptionButton<>(this.options, RadarOption.SHOW_MOB_HELMETS));
        addOptionButton(new RadarOptionButton<>(this.options, RadarOption.FILTERING));
        addOptionButton(new RadarOptionButton<>(this.options, RadarOption.OUTLINES));
        addOptionButton(
            // TODO: Fix this button not disabling when the radar is disabled, unlike the RadarOptionButton's
            Buttons.createButton(Component.translatable("options.minimap.radar.selectmobs"))
                .onPress((self) -> VoxelConstants.getMinecraft().setScreen(new GuiMobs(screen, this.options)))
                .build()
        );
        addOptionButton(new RadarOptionButton<>(this.options, RadarOption.HIDE_ELEVATION));
        addOptionButton(new RadarOptionButton<>(this.options, RadarOption.HIDE_SNEAKING));
    }
}
