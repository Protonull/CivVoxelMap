package uk.protonull.civvoxelmap.mixins.radar;

import com.mamiyaotaru.voxelmap.util.Contact;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import uk.protonull.civvoxelmap.CivVoxelMapUtils;

@Mixin(Contact.class)
public abstract class ContactMixin {
	@Shadow
	public abstract void setName(
		Component name
	);

	@Redirect(
		method = "<init>",
		at = @At(
			value = "FIELD",
			target = "Lcom/mamiyaotaru/voxelmap/util/Contact;name:Lnet/minecraft/network/chat/Component;",
			opcode = Opcodes.PUTFIELD
		),
		remap = false
	)
	protected void civvoxelmap$betterSetContactName(
		final @NotNull Contact instance,
		final Component name
	) {
		setName(switch (instance.entity) {
			case final Player player -> CivVoxelMapUtils.getPlainComponent(player.getName());
			case final Entity entity -> CivVoxelMapUtils.getPlainComponent(entity.getCustomName());
			case null -> null;
		});
	}
}
