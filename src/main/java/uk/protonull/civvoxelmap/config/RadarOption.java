package uk.protonull.civvoxelmap.config;

import com.mamiyaotaru.voxelmap.RadarSettingsManager;
import com.mamiyaotaru.voxelmap.gui.overridden.EnumOptionsMinimap;
import java.util.function.Supplier;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.protonull.civvoxelmap.MagicValues;
import uk.protonull.civvoxelmap.gui.widgets.Buttons;

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
            return Buttons.boolMessage(I18n.get(EnumOptionsMinimap.SHOWRADAR.getName()), value);
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
        @Override
        public @NotNull Component getLabel(
            final @NotNull RadarMode value
        ) {
            return Buttons.enumMessage(I18n.get(EnumOptionsMinimap.RADARMODE.getName()), value);
        }
        @Override
        public @NotNull RadarMode getValue(
            final @NotNull RadarSettingsManager manager
        ) {
            return manager.radarMode == MagicValues.RADAR_MODE_SIMPLE ? RadarMode.SIMPLE : RadarMode.FULL;
        }
        @Override
        public void setValue(
            final @NotNull RadarSettingsManager manager,
            final @NotNull RadarMode value
        ) {
            manager.radarMode = switch (value) {
                case FULL -> MagicValues.RADAR_MODE_FULL;
                case SIMPLE -> MagicValues.RADAR_MODE_SIMPLE;
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
            return Buttons.boolMessage(I18n.get(EnumOptionsMinimap.SHOWHOSTILES.getName()), value);
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
            return Buttons.boolMessage(I18n.get(EnumOptionsMinimap.SHOWNEUTRALS.getName()), value);
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
            return Buttons.boolMessage(I18n.get(EnumOptionsMinimap.SHOWPLAYERS.getName()), value);
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
            return Buttons.boolMessage(I18n.get(EnumOptionsMinimap.SHOWPLAYERNAMES.getName()), value);
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
            return Buttons.boolMessage(I18n.get(EnumOptionsMinimap.SHOWMOBNAMES.getName()), value);
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
            return Buttons.boolMessage(I18n.get(EnumOptionsMinimap.SHOWPLAYERHELMETS.getName()), value);
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
            return Tooltip.create(Component.literal("Civ (illegal): Must be OFF!"));
        }
    };

    RadarOption<Boolean> SHOW_MOB_HELMETS = new RadarOption<>() {
        @Override
        public @NotNull Component getLabel(
            final @NotNull Boolean value
        ) {
            return Buttons.boolMessage(I18n.get(EnumOptionsMinimap.SHOWMOBHELMETS.getName()), value);
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
            return Tooltip.create(Component.literal("Civ (illegal): Must be OFF!"));
        }
    };

    RadarOption<Boolean> SHOW_FACING = new RadarOption<>() {
        @Override
        public @NotNull Component getLabel(
            final @NotNull Boolean value
        ) {
            return Buttons.boolMessage(I18n.get(EnumOptionsMinimap.SHOWFACING.getName()), value);
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
            return Buttons.boolMessage(I18n.get(EnumOptionsMinimap.RADARFILTERING.getName()), value);
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
            return Buttons.boolMessage(I18n.get(EnumOptionsMinimap.RADAROUTLINES.getName()), value);
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
            return Buttons.boolMessage("Hide elevation", value);
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
            return Tooltip.create(Component.literal("Civ (illegal): Must be ON!"));
        }
    };

    RadarOption<Boolean> HIDE_SNEAKING = new RadarOption<>() {
        @Override
        public @NotNull Component getLabel(
            final @NotNull Boolean value
        ) {
            return Buttons.boolMessage("Hide sneaking", value);
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
            return Tooltip.create(Component.literal("Civ (Fair Play): Hide crouching entities? (slight delay)"));
        }
    };

    RadarOption<Boolean> HIDE_INVISIBLE = new RadarOption<>() {
        @Override
        public @NotNull Component getLabel(
            final @NotNull Boolean value
        ) {
            return Buttons.boolMessage("Hide invisible", value);
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
            return Tooltip.create(Component.literal("Civ (Fair Play): Hide invisible entities? (slight delay)"));
        }
    };

    RadarOption<Boolean> BETTER_RADAR_SORTING = new RadarOption<>() {
        @Override
        public @NotNull Component getLabel(
            final @NotNull Boolean value
        ) {
            return Buttons.boolMessage("Better sorting", value);
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
            return Tooltip.create(Component.literal("Civ (Improvement): Sort radar entities in the order of: players > hostiles > others? (slight delay)"));
        }
    };
}
