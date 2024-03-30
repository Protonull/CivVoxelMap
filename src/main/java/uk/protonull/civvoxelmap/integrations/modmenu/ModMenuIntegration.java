package uk.protonull.civvoxelmap.integrations.modmenu;

import com.mamiyaotaru.voxelmap.gui.GuiMinimapOptions;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public final class ModMenuIntegration implements ModMenuApi {
    @Override
    public @NotNull ConfigScreenFactory<?> getModConfigScreenFactory() {
        return GuiMinimapOptions::new;
    }

    @Override
    public @NotNull Map<String, ConfigScreenFactory<?>> getProvidedConfigScreenFactories() {
        return Map.of("voxelmap", GuiMinimapOptions::new);
    }
}
