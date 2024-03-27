package uk.protonull.civvoxelmap.gui.widgets;

import com.mamiyaotaru.voxelmap.RadarSettingsManager;
import java.util.Objects;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import uk.protonull.civvoxelmap.config.RadarOption;

public class RadarOptionButton<T> extends Button {
    protected final RadarSettingsManager manager;
    public final RadarOption<T> option;
    private final OnPress<T> onPress;

    public RadarOptionButton(
        final @NotNull RadarSettingsManager manager,
        final @NotNull RadarOption<T> option
    ) {
        this(manager, option, (button) -> {});
    }

    public RadarOptionButton(
        final @NotNull RadarSettingsManager manager,
        final @NotNull RadarOption<T> option,
        final @NotNull OnPress<T> onPress
    ) {
        super(
            0, 0, 150, 20,
            Component.empty(),
            null,
            option.getNarration()
        );
        setTooltip(option.getTooltip());
        this.manager = manager;
        this.option = option;
        this.onPress = Objects.requireNonNull(onPress);
        update();
    }

    @Override
    public final void onPress() {
        this.option.nextValue(this.manager);
        this.onPress.onPress(this);
        update();
    }

    public void update() {
        setMessage(this.option.getLabel(this.option.getValue(this.manager)));
        this.active = !this.option.requiresRadarEnabled() || this.manager.showRadar;
    }

    public interface OnPress<T> {
        void onPress(
            @NotNull RadarOptionButton<T> button
        );
    }
}
