package uk.protonull.civvoxelmap.mixins.catching;

import com.mamiyaotaru.voxelmap.VoxelConstants;
import com.mamiyaotaru.voxelmap.fabricmod.FabricModVoxelMap;
import com.mamiyaotaru.voxelmap.util.CommandUtils;
import net.minecraft.client.GuiMessageTag;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FabricModVoxelMap.class)
public abstract class ChatWaypointSafelyParseMixin {
    @Redirect(
        method = "onChat",
        at = @At(
            value = "INVOKE",
            target = "Lcom/mamiyaotaru/voxelmap/util/CommandUtils;checkForWaypoints(Lnet/minecraft/network/chat/Component;Lnet/minecraft/client/GuiMessageTag;)Z"
        )
    )
    protected boolean cvm$onChat$safelyCatchError(
        final @NotNull Component chat,
        final @NotNull GuiMessageTag indicator
    ) {
        try {
            return CommandUtils.checkForWaypoints(chat, indicator);
        }
        catch (final Exception thrown) {
            VoxelConstants.getLogger().warn("Something went wrong while checking for waypoints on message: {}", chat.getString(), thrown);
            return true; // Keep the chat message
        }
    }
}
