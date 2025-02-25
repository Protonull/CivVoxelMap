package uk.protonull.civvoxelmap.mixins.waypoints;

import com.mamiyaotaru.voxelmap.VoxelConstants;
import net.minecraft.client.GuiMessageTag;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(VoxelConstants.class)
public abstract class PreventOfficialWaypointParsingMixin {
    /**
     * @author Protonull
     * @reason Completely redoing how chat waypoints are handled.
     * @see uk.protonull.civvoxelmap.mixins.waypoints.InjectClickableWaypointMixin
     */
    @Overwrite
    public static boolean onChat(Component chat, GuiMessageTag indicator) {
        return true; // DO NOTHING
    }
}
