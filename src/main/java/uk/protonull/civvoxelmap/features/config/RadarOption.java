package uk.protonull.civvoxelmap.features.config;

import com.mamiyaotaru.voxelmap.RadarSettingsManager;
import com.mamiyaotaru.voxelmap.gui.overridden.EnumOptionsMinimap;
import java.util.function.Supplier;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.protonull.civvoxelmap.features.config.widgets.Buttons;

public interface RadarOption<T> {
    @NotNull Component getLabel(
        @NotNull T value
    );

    default @Nullable Tooltip getTooltip() {
        return null;
    }

    default @NotNull Button.CreateNarration getNarration() {
        return Supplier::get;
    }

    default boolean requiresRadarEnabled() {
        return true;
    }

    @NotNull T getValue(
        @NotNull RadarSettingsManager manager
    );

    void setValue(
        @NotNull RadarSettingsManager manager,
        @NotNull T value
    );

    void nextValue(
        @NotNull RadarSettingsManager manager
    );

    // ============================================================
    // Default Options
    // ============================================================

    RadarOption<Boolean> ENABLED = new RadarOption<>() {
        @Override
        public @NotNull Component getLabel(
            final @NotNull Boolean value
        ) {
            return Buttons.boolMessage(Component.translatable(EnumOptionsMinimap.SHOWRADAR.getName()), value);
        }
        @Override
        public @NotNull Boolean getValue(
            final @NotNull RadarSettingsManager manager
        ) {
            return manager.showRadar;
        }
        @Override
        public void setValue(
            final @NotNull RadarSettingsManager manager,
            final @NotNull Boolean value
        ) {
            manager.showRadar = value;
        }
        @Override
        public void nextValue(
            final @NotNull RadarSettingsManager manager
        ) {
            setValue(manager, !getValue(manager));
        }
        @Override
        public boolean requiresRadarEnabled() {
            return false;
        }
    };

    enum RadarMode { FULL, SIMPLE }
    RadarOption<RadarMode> MODE = new RadarOption<>() {
        private static final int RADAR_MODE_SIMPLE_RAW = 1;
        private static final int RADAR_MODE_FULL_RAW = 2;

        @Override
        public @NotNull Component getLabel(
            final @NotNull RadarMode value
        ) {
            return Buttons.enumMessage(Component.translatable(EnumOptionsMinimap.RADARMODE.getName()), value);
        }
        @Override
        public @NotNull RadarMode getValue(
            final @NotNull RadarSettingsManager manager
        ) {
            return manager.radarMode == RADAR_MODE_SIMPLE_RAW ? RadarMode.SIMPLE : RadarMode.FULL;
        }
        @Override
        public void setValue(
            final @NotNull RadarSettingsManager manager,
            final @NotNull RadarMode value
        ) {
            manager.radarMode = switch (value) {
                case FULL -> RADAR_MODE_FULL_RAW;
                case SIMPLE -> RADAR_MODE_SIMPLE_RAW;
            };
        }
        @Override
        public void nextValue(
            final @NotNull RadarSettingsManager manager
        ) {
            setValue(manager, switch (getValue(manager)) {
                case FULL -> RadarMode.SIMPLE;
                case SIMPLE -> RadarMode.FULL;
            });
        }
    };

    RadarOption<Boolean> SHOW_HOSTILES = new RadarOption<>() {
        @Override
        public @NotNull Component getLabel(
            final @NotNull Boolean value
        ) {
            return Buttons.boolMessage(Component.translatable(EnumOptionsMinimap.SHOWHOSTILES.getName()), value);
        }
        @Override
        public @NotNull Boolean getValue(
            final @NotNull RadarSettingsManager manager
        ) {
            return manager.showHostiles;
        }
        @Override
        public void setValue(
            final @NotNull RadarSettingsManager manager,
            final @NotNull Boolean value
        ) {
            manager.showHostiles = value;
        }
        @Override
        public void nextValue(
            final @NotNull RadarSettingsManager manager
        ) {
            setValue(manager, !getValue(manager));
        }
    };

    RadarOption<Boolean> SHOW_NEUTRALS = new RadarOption<>() {
        @Override
        public @NotNull Component getLabel(
            final @NotNull Boolean value
        ) {
            return Buttons.boolMessage(Component.translatable(EnumOptionsMinimap.SHOWNEUTRALS.getName()), value);
        }
        @Override
        public @NotNull Boolean getValue(
            final @NotNull RadarSettingsManager manager
        ) {
            return manager.showNeutrals;
        }
        @Override
        public void setValue(
            final @NotNull RadarSettingsManager manager,
            final @NotNull Boolean value
        ) {
            manager.showNeutrals = value;
        }
        @Override
        public void nextValue(
            final @NotNull RadarSettingsManager manager
        ) {
            setValue(manager, !getValue(manager));
        }
    };

    RadarOption<Boolean> SHOW_PLAYERS = new RadarOption<>() {
        @Override
        public @NotNull Component getLabel(
            final @NotNull Boolean value
        ) {
            return Buttons.boolMessage(Component.translatable(EnumOptionsMinimap.SHOWPLAYERS.getName()), value);
        }
        @Override
        public @NotNull Boolean getValue(
            final @NotNull RadarSettingsManager manager
        ) {
            return manager.showPlayers;
        }
        @Override
        public void setValue(
            final @NotNull RadarSettingsManager manager,
            final @NotNull Boolean value
        ) {
            manager.showPlayers = value;
        }
        @Override
        public void nextValue(
            final @NotNull RadarSettingsManager manager
        ) {
            setValue(manager, !getValue(manager));
        }
    };

    RadarOption<Boolean> SHOW_PLAYER_NAMES = new RadarOption<>() {
        @Override
        public @NotNull Component getLabel(
            final @NotNull Boolean value
        ) {
            return Buttons.boolMessage(Component.translatable(EnumOptionsMinimap.SHOWPLAYERNAMES.getName()), value);
        }
        @Override
        public @NotNull Boolean getValue(
            final @NotNull RadarSettingsManager manager
        ) {
            return manager.showPlayerNames;
        }
        @Override
        public void setValue(
            final @NotNull RadarSettingsManager manager,
            final @NotNull Boolean value
        ) {
            manager.showPlayerNames = value;
        }
        @Override
        public void nextValue(
            final @NotNull RadarSettingsManager manager
        ) {
            setValue(manager, !getValue(manager));
        }
    };

    RadarOption<Boolean> SHOW_MOB_NAMES = new RadarOption<>() {
        @Override
        public @NotNull Component getLabel(
            final @NotNull Boolean value
        ) {
            return Buttons.boolMessage(Component.translatable(EnumOptionsMinimap.SHOWMOBNAMES.getName()), value);
        }
        @Override
        public @NotNull Boolean getValue(
            final @NotNull RadarSettingsManager manager
        ) {
            return manager.showMobNames;
        }
        @Override
        public void setValue(
            final @NotNull RadarSettingsManager manager,
            final @NotNull Boolean value
        ) {
            manager.showMobNames = value;
        }
        @Override
        public void nextValue(
            final @NotNull RadarSettingsManager manager
        ) {
            setValue(manager, !getValue(manager));
        }
    };

    RadarOption<Boolean> SHOW_PLAYER_HELMETS = new RadarOption<>() {
        @Override
        public @NotNull Component getLabel(
            final @NotNull Boolean value
        ) {
            return Buttons.boolMessage(Component.translatable(EnumOptionsMinimap.SHOWPLAYERHELMETS.getName()), value);
        }
        @Override
        public @NotNull Boolean getValue(
            final @NotNull RadarSettingsManager manager
        ) {
            return manager.showHelmetsPlayers;
        }
        @Override
        public void setValue(
            final @NotNull RadarSettingsManager manager,
            final @NotNull Boolean value
        ) {
            manager.showHelmetsPlayers = value;
        }
        @Override
        public void nextValue(
            final @NotNull RadarSettingsManager manager
        ) {
            setValue(manager, !getValue(manager));
        }
        @Override
        public Tooltip getTooltip() {
            return Tooltip.create(Component.translatable("civvoxelmap.feature.illegal.tooltip.off"));
        }
    };

    RadarOption<Boolean> SHOW_MOB_HELMETS = new RadarOption<>() {
        @Override
        public @NotNull Component getLabel(
            final @NotNull Boolean value
        ) {
            return Buttons.boolMessage(Component.translatable(EnumOptionsMinimap.SHOWMOBHELMETS.getName()), value);
        }
        @Override
        public @NotNull Boolean getValue(
            final @NotNull RadarSettingsManager manager
        ) {
            return manager.showHelmetsMobs;
        }
        @Override
        public void setValue(
            final @NotNull RadarSettingsManager manager,
            final @NotNull Boolean value
        ) {
            manager.showHelmetsMobs = value;
        }
        @Override
        public void nextValue(
            final @NotNull RadarSettingsManager manager
        ) {
            setValue(manager, !getValue(manager));
        }
        @Override
        public Tooltip getTooltip() {
            return Tooltip.create(Component.translatable("civvoxelmap.feature.illegal.tooltip.off"));
        }
    };

    RadarOption<Boolean> SHOW_FACING = new RadarOption<>() {
        @Override
        public @NotNull Component getLabel(
            final @NotNull Boolean value
        ) {
            return Buttons.boolMessage(Component.translatable(EnumOptionsMinimap.SHOWFACING.getName()), value);
        }
        @Override
        public @NotNull Boolean getValue(
            final @NotNull RadarSettingsManager manager
        ) {
            return manager.showFacing;
        }
        @Override
        public void setValue(
            final @NotNull RadarSettingsManager manager,
            final @NotNull Boolean value
        ) {
            manager.showFacing = value;
        }
        @Override
        public void nextValue(
            final @NotNull RadarSettingsManager manager
        ) {
            setValue(manager, !getValue(manager));
        }
    };

    RadarOption<Boolean> FILTERING = new RadarOption<>() {
        @Override
        public @NotNull Component getLabel(
            final @NotNull Boolean value
        ) {
            return Buttons.boolMessage(Component.translatable(EnumOptionsMinimap.RADARFILTERING.getName()), value);
        }
        @Override
        public @NotNull Boolean getValue(
            final @NotNull RadarSettingsManager manager
        ) {
            return manager.filtering;
        }
        @Override
        public void setValue(
            final @NotNull RadarSettingsManager manager,
            final @NotNull Boolean value
        ) {
            manager.filtering = value;
        }
        @Override
        public void nextValue(
            final @NotNull RadarSettingsManager manager
        ) {
            setValue(manager, !getValue(manager));
        }
    };

    RadarOption<Boolean> OUTLINES = new RadarOption<>() {
        @Override
        public @NotNull Component getLabel(
            final @NotNull Boolean value
        ) {
            return Buttons.boolMessage(Component.translatable(EnumOptionsMinimap.RADAROUTLINES.getName()), value);
        }
        @Override
        public @NotNull Boolean getValue(
            final @NotNull RadarSettingsManager manager
        ) {
            return manager.outlines;
        }
        @Override
        public void setValue(
            final @NotNull RadarSettingsManager manager,
            final @NotNull Boolean value
        ) {
            manager.outlines = value;
        }
        @Override
        public void nextValue(
            final @NotNull RadarSettingsManager manager
        ) {
            setValue(manager, !getValue(manager));
        }
    };

    // ============================================================
    // Custom Options
    // ============================================================

    RadarOption<Boolean> HIDE_ELEVATION = new RadarOption<>() {
        @Override
        public @NotNull Component getLabel(
            final @NotNull Boolean value
        ) {
            return Buttons.boolMessage(Component.translatable("civvoxelmap.settings.elevation.label"), value);
        }
        @Override
        public @NotNull Boolean getValue(
            final @NotNull RadarSettingsManager manager
        ) {
            return ((ExtraRadarSettings.Accessor) manager).hideElevation();
        }
        @Override
        public void setValue(
            final @NotNull RadarSettingsManager manager,
            final @NotNull Boolean value
        ) {
            ((ExtraRadarSettings.Accessor) manager).hideElevation(value);
        }
        @Override
        public void nextValue(
            final @NotNull RadarSettingsManager manager
        ) {
            setValue(manager, !getValue(manager));
        }
        @Override
        public Tooltip getTooltip() {
            return Tooltip.create(Component.translatable("civvoxelmap.feature.illegal.tooltip.on"));
        }
    };

    RadarOption<Boolean> HIDE_SNEAKING = new RadarOption<>() {
        @Override
        public @NotNull Component getLabel(
            final @NotNull Boolean value
        ) {
            return Buttons.boolMessage(Component.translatable("civvoxelmap.settings.sneaking.label"), value);
        }
        @Override
        public @NotNull Boolean getValue(
            final @NotNull RadarSettingsManager manager
        ) {
            return ((ExtraRadarSettings.Accessor) manager).hideSneaking();
        }
        @Override
        public void setValue(
            final @NotNull RadarSettingsManager manager,
            final @NotNull Boolean value
        ) {
            ((ExtraRadarSettings.Accessor) manager).hideSneaking(value);
        }
        @Override
        public void nextValue(
            final @NotNull RadarSettingsManager manager
        ) {
            setValue(manager, !getValue(manager));
        }
        @Override
        public Tooltip getTooltip() {
            return Tooltip.create(
                Component.empty()
                    .append(Component.translatable("civvoxelmap.settings.sneaking.tooltip"))
                    .append(Component.literal("\n \n"))
                    .append(Component.translatable("civvoxelmap.feature.illegal.tooltip.off"))
            );
        }
    };

    RadarOption<Boolean> HIDE_INVISIBLE = new RadarOption<>() {
        @Override
        public @NotNull Component getLabel(
            final @NotNull Boolean value
        ) {
            return Buttons.boolMessage(Component.translatable("civvoxelmap.settings.invisible.label"), value);
        }
        @Override
        public @NotNull Boolean getValue(
            final @NotNull RadarSettingsManager manager
        ) {
            return ((ExtraRadarSettings.Accessor) manager).hideInvisible();
        }
        @Override
        public void setValue(
            final @NotNull RadarSettingsManager manager,
            final @NotNull Boolean value
        ) {
            ((ExtraRadarSettings.Accessor) manager).hideInvisible(value);
        }
        @Override
        public void nextValue(
            final @NotNull RadarSettingsManager manager
        ) {
            setValue(manager, !getValue(manager));
        }
        @Override
        public Tooltip getTooltip() {
            return Tooltip.create(
                Component.empty()
                    .append(Component.translatable("civvoxelmap.settings.invisible.tooltip"))
                    .append(Component.literal("\n \n"))
                    .append(Component.translatable("civvoxelmap.feature.illegal.tooltip.off"))
            );
        }
    };

    RadarOption<Boolean> BETTER_RADAR_SORTING = new RadarOption<>() {
        @Override
        public @NotNull Component getLabel(
            final @NotNull Boolean value
        ) {
            return Buttons.boolMessage(Component.translatable("civvoxelmap.settings.sorting.label"), value);
        }
        @Override
        public @NotNull Boolean getValue(
            final @NotNull RadarSettingsManager manager
        ) {
            return ((ExtraRadarSettings.Accessor) manager).useBetterRadarSort();
        }
        @Override
        public void setValue(
            final @NotNull RadarSettingsManager manager,
            final @NotNull Boolean value
        ) {
            ((ExtraRadarSettings.Accessor) manager).useBetterRadarSort(value);
        }
        @Override
        public void nextValue(
            final @NotNull RadarSettingsManager manager
        ) {
            setValue(manager, !getValue(manager));
        }
        @Override
        public Tooltip getTooltip() {
            return Tooltip.create(Component.translatable("civvoxelmap.settings.sorting.tooltip"));
        }
    };
}
