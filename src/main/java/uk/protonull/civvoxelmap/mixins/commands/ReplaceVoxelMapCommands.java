package uk.protonull.civvoxelmap.mixins.commands;

import net.minecraft.client.multiplayer.ClientPacketListener;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import uk.protonull.civvoxelmap.features.commands.VoxelMapCommands;

@Mixin(ClientPacketListener.class)
public abstract class ReplaceVoxelMapCommands {
    @Inject(
        method = "sendCommand",
        at = @At("HEAD"),
        cancellable = true
    )
    protected void cvm$sendCommand$interceptSignedCommand(
        final @NotNull String command,
        final @NotNull CallbackInfo ci
    ) {
        if (VoxelMapCommands.attemptRunCommand(command)) {
            ci.cancel();
        }
    }

    @Inject(
        method = "sendUnsignedCommand",
        at = @At("HEAD"),
        cancellable = true
    )
    protected void cvm$sendCommand$interceptUnsignedCommand(
        final @NotNull String command,
        final @NotNull CallbackInfoReturnable<Boolean> cir
    ) {
        if (VoxelMapCommands.attemptRunCommand(command)) {
            cir.setReturnValue(true);
        }
    }
}
