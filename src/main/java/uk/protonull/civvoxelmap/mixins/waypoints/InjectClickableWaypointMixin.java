package uk.protonull.civvoxelmap.mixins.waypoints;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mamiyaotaru.voxelmap.util.CommandUtils;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import net.minecraft.ChatFormatting;
import net.minecraft.client.GuiMessageTag;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import uk.protonull.civvoxelmap.CivVoxelMapUtils;

@Mixin(ChatComponent.class)
public abstract class InjectClickableWaypointMixin {
    @ModifyVariable(
        method = "addMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;Lnet/minecraft/client/GuiMessageTag;)V",
        at = @At("HEAD"),
        argsOnly = true
    )
    protected @NotNull Component civvoxelmap$betterChatWaypointClickify(
        final @NotNull Component original,
        final @Local(argsOnly = true) LocalRef<MessageSignature> signatureRef,
        final @Local(argsOnly = true) LocalRef<GuiMessageTag> tagRef
    ) {
        final String plain; final List<MutableComponent> splitByCharacter = new ArrayList<>(); {
            final StringBuilder plainBuilder = new StringBuilder();
            for (final Component part : original.toFlatList()) {
                final String partPlain = CivVoxelMapUtils.getPlainContents(part);
                plainBuilder.append(partPlain);
                for (final String character : partPlain.split("")) {
                    splitByCharacter.add(Component.literal(character).withStyle(part.getStyle()));
                }
            }
            plain = plainBuilder.toString();
        }

        boolean matches = false;
        final Matcher matcher = CommandUtils.pattern.matcher(plain);
        while (matcher.find()) {
            matches = true;
            for (int i = matcher.start(); i < matcher.end(); i++) {
                final MutableComponent character = splitByCharacter.get(i);

                // The text within the [] brackets
                final String innerWaypoint = StringUtils.substring(matcher.group(), 1, -1);

                character.withStyle(
                    Style.EMPTY
                        .withColor(ChatFormatting.AQUA)
                        .withHoverEvent(new HoverEvent.ShowText(
                            CivVoxelMapUtils.ofChildren(
                                Component.translatable("minimap.waypointshare.tooltip1"),
                                CommonComponents.NEW_LINE,
                                Component.translatable("minimap.waypointshare.tooltip2")
                            )
                        ))
                        .withClickEvent(new ClickEvent.RunCommand(
                            "/newWaypoint " + innerWaypoint
                        ))
                );
            }
        }

        if (!matches) {
            return original;
        }

        signatureRef.set(null);
        tagRef.set(new GuiMessageTag(Color.MAGENTA.getRGB(), null, null, "ModifiedbyVoxelMap"));

        final MutableComponent result = Component.empty();
        result.getSiblings().addAll(splitByCharacter);
        return result;
    }
}
