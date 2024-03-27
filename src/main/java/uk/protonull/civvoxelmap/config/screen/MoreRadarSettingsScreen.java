package uk.protonull.civvoxelmap.config.screen;

import com.mamiyaotaru.voxelmap.RadarSettingsManager;
import com.mamiyaotaru.voxelmap.VoxelConstants;
import java.util.List;
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

public final class MoreRadarSettingsScreen extends Screen {
    private final Screen parentScreen;
    private final List<RadarOptionButton<?>> options;
    private int optionIndex;

    private MoreRadarSettingsScreen(
        final @NotNull Screen parentScreen,
        final @NotNull Component title,
        final @NotNull List<@NotNull RadarOptionButton<?>> options
    ) {
        super(title);
        this.parentScreen = Objects.requireNonNull(parentScreen);
        this.options = List.copyOf(options);
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
        this.options.forEach(this::addOptionButton);

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

    public static @NotNull MoreRadarSettingsScreen forPlayers(
        final @NotNull Screen parentScreen,
        final @NotNull RadarSettingsManager manager
    ) {
        return new MoreRadarSettingsScreen(
            parentScreen,
            Component.literal("Additional Player-Radar Settings"),
            List.of(
                new RadarOptionButton<>(manager, RadarOption.SHOW_PLAYER_NAMES),
                new RadarOptionButton<>(manager, RadarOption.SHOW_PLAYER_HELMETS)
            )
        );
    }

    public static @NotNull MoreRadarSettingsScreen forMobs(
        final @NotNull Screen parentScreen,
        final @NotNull RadarSettingsManager manager
    ) {
        return new MoreRadarSettingsScreen(
            parentScreen,
            Component.literal("Additional Mob-Radar Settings"),
            List.of(
                new RadarOptionButton<>(manager, RadarOption.SHOW_HOSTILES),
                new RadarOptionButton<>(manager, RadarOption.SHOW_NEUTRALS),
                new RadarOptionButton<>(manager, RadarOption.SHOW_MOB_NAMES),
                new RadarOptionButton<>(manager, RadarOption.SHOW_MOB_HELMETS)
            )
        );
    }
}
