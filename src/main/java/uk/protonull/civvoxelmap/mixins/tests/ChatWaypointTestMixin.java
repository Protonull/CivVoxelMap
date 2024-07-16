package uk.protonull.civvoxelmap.mixins.tests;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundLoginPacket;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
    value = ClientPacketListener.class,
    priority = 100
)
public class ChatWaypointTestMixin {
    @Inject(
        method = "handleLogin",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/network/protocol/PacketUtils;ensureRunningOnSameThread(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketListener;Lnet/minecraft/util/thread/BlockableEventLoop;)V",
            shift = At.Shift.AFTER
        )
    )
    public void cvm$printChatWaypointImmediatelyUponLogin(
        final @NotNull ClientboundLoginPacket packet,
        final @NotNull CallbackInfo ci
    ) {
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            Minecraft.getInstance().gui.getChat().addMessage(Component.literal("[x:1234, y:123, z:-1234, dim:minecraft:overworld]"));
        }
    }
}
