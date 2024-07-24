package uk.protonull.civvoxelmap.mixins.settings;

import com.mamiyaotaru.voxelmap.RadarSettingsManager;
import com.mamiyaotaru.voxelmap.VoxelConstants;
import com.mamiyaotaru.voxelmap.gui.GuiMobs;
import com.mamiyaotaru.voxelmap.gui.GuiRadarOptions;
import java.util.Objects;
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
import uk.protonull.civvoxelmap.Helpers;
import uk.protonull.civvoxelmap.features.config.RadarConfigAlignment;
import uk.protonull.civvoxelmap.features.config.RadarOption;
import uk.protonull.civvoxelmap.features.config.screen.MoreRadarSettingsScreen;
import uk.protonull.civvoxelmap.features.config.widgets.Buttons;
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
    private int cvm$optionIndex;

    @Unique
    private @NotNull Button cvm$addOptionButton(
        final @NotNull Button button
    ) {
        final GuiRadarOptions screen = Helpers.hardCast(this);
        RadarConfigAlignment.realignOptionWidget(button, screen, this.cvm$optionIndex++);
        screen.addRenderableWidget(button);
        return button;
    }

    @Unique
    private void cvm$addOptionButtonWithCog(
        final @NotNull Button button,
        final @NotNull Component tooltip,
        final @NotNull Button.OnPress onPress
    ) {
        cvm$addOptionButton(button);

        final GuiRadarOptions screen = Helpers.hardCast(this);
        final Button cog = screen.addRenderableWidget(
            Buttons.createButton(Component.literal("⚙"))
                .onPress(Objects.requireNonNull(onPress))
                .tooltip(Objects.requireNonNull(Tooltip.create(tooltip)))
                .size(20, 20)
                .build()
        );

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
        final GuiRadarOptions screen = Helpers.hardCast(this);
        screen.clearWidgets();
        screen.clearFocus();
        this.cvm$optionIndex = 0;

        cvm$addOptionButton(new RadarOptionButton<>(this.options, RadarOption.ENABLED, (button) -> {
            for (final GuiEventListener widget : screen.children()) {
                if (widget instanceof final RadarOptionButton<?> optionButton) {
                    optionButton.update();
                }
            }
        }));
        cvm$addOptionButton(new RadarOptionButton<>(this.options, RadarOption.MODE, (button) -> init()));

        switch (RadarOption.MODE.getValue(this.options)) {
            case SIMPLE -> {
                cvm$addOptionButton(new RadarOptionButton<>(this.options, RadarOption.SHOW_HOSTILES));
                cvm$addOptionButton(new RadarOptionButton<>(this.options, RadarOption.SHOW_NEUTRALS));
                cvm$addOptionButton(new RadarOptionButton<>(this.options, RadarOption.SHOW_PLAYERS));
                cvm$addOptionButton(new RadarOptionButton<>(this.options, RadarOption.SHOW_FACING));
            }
            case FULL -> {
                cvm$addOptionButtonWithCog(
                    new RadarOptionButton<>(this.options, RadarOption.SHOW_PLAYERS),
                    Component.literal("More player settings"),
                    (cog) -> VoxelConstants.getMinecraft().setScreen(MoreRadarSettingsScreen.forPlayers(screen, this.options))
                );
                cvm$addOptionButtonWithCog(
                    // TODO: Fix this button not disabling when the radar is disabled, unlike the RadarOptionButton's
                    Buttons.createButton(Component.translatable("options.minimap.radar.selectmobs"))
                        .onPress((self) -> VoxelConstants.getMinecraft().setScreen(new GuiMobs(screen, this.options)))
                        .build(),
                    Component.literal("More mob settings"),
                    (cog) -> VoxelConstants.getMinecraft().setScreen(MoreRadarSettingsScreen.forMobs(screen, this.options))
                );
                cvm$addOptionButton(new RadarOptionButton<>(this.options, RadarOption.FILTERING));
                cvm$addOptionButton(new RadarOptionButton<>(this.options, RadarOption.OUTLINES));
            }
        }

        cvm$addOptionButton(new RadarOptionButton<>(this.options, RadarOption.HIDE_ELEVATION));
        cvm$addOptionButton(new RadarOptionButton<>(this.options, RadarOption.HIDE_SNEAKING));
        cvm$addOptionButton(new RadarOptionButton<>(this.options, RadarOption.HIDE_INVISIBLE));
        cvm$addOptionButton(new RadarOptionButton<>(this.options, RadarOption.BETTER_RADAR_SORTING));

        screen.addRenderableWidget(
            Buttons.createButton(Component.translatable("gui.done"))
                .onPress((button) -> VoxelConstants.getMinecraft().setScreen(this.parent))
                .bounds(screen.width / 2 - 100, screen.height / 6 + 168, 200, 20)
                .build()
        );
    }
}
