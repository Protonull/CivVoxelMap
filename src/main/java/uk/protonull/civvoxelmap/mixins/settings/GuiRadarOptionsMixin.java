package uk.protonull.civvoxelmap.mixins.settings;

import com.mamiyaotaru.voxelmap.RadarSettingsManager;
import com.mamiyaotaru.voxelmap.VoxelConstants;
import com.mamiyaotaru.voxelmap.gui.GuiMobs;
import com.mamiyaotaru.voxelmap.gui.GuiRadarOptions;
import java.util.Objects;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import uk.protonull.civvoxelmap.features.config.RadarConfigAlignment;
import uk.protonull.civvoxelmap.features.config.RadarOption;
import uk.protonull.civvoxelmap.features.config.screen.MoreRadarSettingsScreen;
import uk.protonull.civvoxelmap.features.config.widgets.RadarOptionButton;

@Mixin(GuiRadarOptions.class)
public abstract class GuiRadarOptionsMixin {
    @Final
    @Shadow
    private Screen parent;

    @Final
    @Shadow(remap = false)
    private RadarSettingsManager options;

    @Shadow
    protected final Component screenTitle = Component.translatable("options.minimap.radar.title");

    @Unique
    private int civvoxelmap$optionIndex;

    @Unique
    private @NotNull Button civvoxelmap$addOptionButton(
        final @NotNull Button button
    ) {
        final var screen = (GuiRadarOptions) (Object) this;
        RadarConfigAlignment.realignOptionWidget(button, screen, this.civvoxelmap$optionIndex++);
        screen.addRenderableWidget(button);
        return button;
    }

    @Unique
    private void civvoxelmap$addOptionButtonWithCog(
        final @NotNull Button button,
        final @NotNull Component tooltip,
        final @NotNull Button.OnPress onPress
    ) {
        civvoxelmap$addOptionButton(button);

        final var screen = (GuiRadarOptions) (Object) this;
        final Button cog = screen.addRenderableWidget(new Button(
            0,
            0,
            20,
            Button.DEFAULT_HEIGHT,
            Component.literal("⚙"),
            Objects.requireNonNull(onPress),
            Button.DEFAULT_NARRATION
        ));
        cog.setTooltip(Tooltip.create(Objects.requireNonNull(tooltip)));

        button.setWidth(button.getWidth() - cog.getWidth() - RadarConfigAlignment.X_PADDING);
        cog.setX(button.getX() + button.getWidth() + RadarConfigAlignment.X_PADDING);
        cog.setY(button.getY());
    }

    /**
     * @author Protonull
     * @reason This Mixin is basically just replacing the class entirely.
     */
    @Overwrite
    public void init() {
        final var screen = (GuiRadarOptions) (Object) this;
        screen.clearWidgets();
        screen.clearFocus();
        this.civvoxelmap$optionIndex = 0;

        civvoxelmap$addOptionButton(new RadarOptionButton<>(this.options, RadarOption.ENABLED, (button) -> {
            for (final GuiEventListener widget : screen.children()) {
                if (widget instanceof final RadarOptionButton<?> optionButton) {
                    optionButton.update();
                }
            }
        }));
        civvoxelmap$addOptionButton(new RadarOptionButton<>(this.options, RadarOption.MODE, (button) -> init()));

        switch (RadarOption.MODE.getValue(this.options)) {
            case SIMPLE -> {
                civvoxelmap$addOptionButton(new RadarOptionButton<>(this.options, RadarOption.SHOW_HOSTILES));
                civvoxelmap$addOptionButton(new RadarOptionButton<>(this.options, RadarOption.SHOW_NEUTRALS));
                civvoxelmap$addOptionButton(new RadarOptionButton<>(this.options, RadarOption.SHOW_PLAYERS));
                civvoxelmap$addOptionButton(new RadarOptionButton<>(this.options, RadarOption.SHOW_FACING));
            }
            case FULL -> {
                civvoxelmap$addOptionButtonWithCog(
                    new RadarOptionButton<>(this.options, RadarOption.SHOW_PLAYERS),
                    Component.translatable("civvoxelmap.settings.player.label"),
                    (cog) -> VoxelConstants.getMinecraft().setScreen(MoreRadarSettingsScreen.forPlayers(screen, this.options))
                );
                civvoxelmap$addOptionButtonWithCog(
                    // TODO: Fix this button not disabling when the radar is disabled, unlike the RadarOptionButton's
                    new Button(
                        0,
                        0,
                        Button.DEFAULT_WIDTH,
                        Button.DEFAULT_HEIGHT,
                        Component.translatable("options.minimap.radar.selectmobs"),
                        (self) -> VoxelConstants.getMinecraft().setScreen(new GuiMobs(screen, this.options)),
                        Button.DEFAULT_NARRATION
                    ),
                    Component.translatable("civvoxelmap.settings.mods.label"),
                    (cog) -> VoxelConstants.getMinecraft().setScreen(MoreRadarSettingsScreen.forMobs(screen, this.options))
                );
                civvoxelmap$addOptionButton(new RadarOptionButton<>(this.options, RadarOption.FILTERING));
                civvoxelmap$addOptionButton(new RadarOptionButton<>(this.options, RadarOption.OUTLINES));
            }
        }

        civvoxelmap$addOptionButton(new RadarOptionButton<>(this.options, RadarOption.HIDE_ELEVATION));
        civvoxelmap$addOptionButton(new RadarOptionButton<>(this.options, RadarOption.HIDE_SNEAKING));
        civvoxelmap$addOptionButton(new RadarOptionButton<>(this.options, RadarOption.HIDE_INVISIBLE));
        civvoxelmap$addOptionButton(new RadarOptionButton<>(this.options, RadarOption.BETTER_RADAR_SORTING));

        screen.addRenderableWidget(new Button(
            screen.width / 2 - 100,
            screen.height / 6 + 168,
            200,
            Button.DEFAULT_HEIGHT,
            Component.translatable("gui.done"),
            (button) -> VoxelConstants.getMinecraft().setScreen(this.parent),
            Button.DEFAULT_NARRATION
        ));
    }


    @Redirect(
        method = "render",
        at = @At(
            value = "INVOKE",
            target = "Lcom/mamiyaotaru/voxelmap/gui/GuiRadarOptions;renderBlurredBackground(Lnet/minecraft/client/gui/GuiGraphics;)V"
        )
    )
    protected void civvoxelmap$doNotRenderBlurredBackground(
        final @NotNull GuiRadarOptions instance,
        final @NotNull GuiGraphics guiGraphics
    ) {
        // DO NOTHING
    }

    @Redirect(
        method = "render",
        at = @At(
            value = "INVOKE",
            target = "Lcom/mamiyaotaru/voxelmap/gui/GuiRadarOptions;renderMenuBackground(Lnet/minecraft/client/gui/GuiGraphics;)V"
        )
    )
    protected void civvoxelmap$doNotRenderMenuBackground(
        final @NotNull GuiRadarOptions instance,
        final @NotNull GuiGraphics guiGraphics
    ) {
        // DO NOTHING
    }
}
