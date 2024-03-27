package uk.protonull.civvoxelmap.config.screen;

import com.mamiyaotaru.voxelmap.RadarSettingsManager;
import com.mamiyaotaru.voxelmap.VoxelConstants;
import java.util.Objects;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import uk.protonull.civvoxelmap.config.RadarConfigAlignment;
import uk.protonull.civvoxelmap.config.RadarOption;
import uk.protonull.civvoxelmap.gui.widgets.Buttons;
import uk.protonull.civvoxelmap.gui.widgets.RadarOptionButton;

public final class RadarMobSettingsScreen extends Screen {
    private final Screen parentScreen;
    private final RadarSettingsManager options;
    private int optionIndex;

    public RadarMobSettingsScreen(
        final @NotNull Screen parentScreen,
        final @NotNull RadarSettingsManager options
    ) {
        super(Component.literal("Additional Mob-Radar Settings"));
        this.parentScreen = Objects.requireNonNull(parentScreen);
        this.options = Objects.requireNonNull(options);
    }

    private @NotNull Button addOptionButton(
        final @NotNull Button button
    ) {
        RadarConfigAlignment.realignOptionWidget(button, this, this.optionIndex++);
        addRenderableWidget(button);
        return button;
    }

    @Override
    protected void init() {
        this.optionIndex = 0;

        addOptionButton(new RadarOptionButton<>(this.options, RadarOption.SHOW_HOSTILES));
        addOptionButton(new RadarOptionButton<>(this.options, RadarOption.SHOW_NEUTRALS));
        addOptionButton(new RadarOptionButton<>(this.options, RadarOption.SHOW_MOB_NAMES));
        addOptionButton(new RadarOptionButton<>(this.options, RadarOption.SHOW_MOB_HELMETS));

        addRenderableWidget(
            Buttons.createButton(Component.translatable("gui.done"))
                .onPress((button) -> VoxelConstants.getMinecraft().setScreen(this.parentScreen))
                .bounds(this.width / 2 - 100, this.height / 6 + 168, 200, 20)
                .build()
        );
    }

    @Override
    public void render(
        final @NotNull GuiGraphics drawContext,
        final int mouseX,
        final int mouseY,
        final float delta
    ) {
        renderBackground(drawContext);
        drawContext.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFF);
        super.render(drawContext, mouseX, mouseY, delta);
    }
}
