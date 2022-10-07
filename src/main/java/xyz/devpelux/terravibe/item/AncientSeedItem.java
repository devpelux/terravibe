package xyz.devpelux.terravibe.item;

import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import xyz.devpelux.terravibe.tags.TerravibeFluidTags;

import java.util.List;
import java.util.Optional;

/**
 * Ancient seed item.
 */
public class AncientSeedItem extends ColoredItem {
	/**
	 * Initializes a new {@link AncientSeedItem}.
	 */
	public AncientSeedItem(Settings settings, ItemColorProvider provider) {
		super(settings, provider);
	}

	/**
	 * Gets the wash sound.
	 */
	public Optional<SoundEvent> getWashSound() {
		return Optional.of(SoundEvents.AMBIENT_UNDERWATER_EXIT);
	}

	/**
	 * Adds a tooltip that shows the current dirty value of the stack.
	 */
	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		super.appendTooltip(stack, world, tooltip, context);

		int dirty = stack.getOrCreateNbt().getInt("Dirty");
		tooltip.add(Text.literal("Dirty: " + dirty).formatted(Formatting.DARK_GRAY));
	}

	/**
	 * Executed when the item is used by the player.
	 */
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		//Executes a ray casting to get the pointed block or fluid.
		BlockHitResult hit = raycast(world, player, RaycastContext.FluidHandling.ANY);
		if (hit.getType() == HitResult.Type.BLOCK) {
			BlockPos pos = hit.getBlockPos();
			FluidState state = world.getFluidState(pos);
			if (state.isIn(TerravibeFluidTags.CLEANER)) {
				//If the hit result is a cleaner fluid, cleans the item.
				ItemStack stack = player.getStackInHand(hand);
				return TypedActionResult.success(clean(state, world, pos, stack, player, hand));
			}
		}

		return TypedActionResult.pass(player.getStackInHand(hand));
	}

	/**
	 * Executed when the item is used by the player on a cleaner fluid.
	 * Drops a less dirty item.
	 */
	public ItemStack clean(FluidState state, World world, BlockPos pos, ItemStack stack, PlayerEntity player, Hand hand) {
		if (!world.isClient()) {
			//Drops the result and consumes the stack.
			ItemStack result = getRemainder(stack);
			player.getInventory().offerOrDrop(result);

			//Consumes the stack only if the player is not in creative mode.
			if (!player.getAbilities().creativeMode) {
				stack.decrement(1);
			}

			getWashSound().ifPresent(sound -> player.playSound(sound, SoundCategory.BLOCKS, 0.5f, 0.9f));
		}

		return stack;
	}

	/**
	 * Gets the remainder for the stack.
	 */
	private ItemStack getRemainder(ItemStack stack) {
		int dirty = stack.getOrCreateNbt().getInt("Dirty");
		if (dirty > 1) {
			ItemStack result = new ItemStack(stack.getItem());
			result.getOrCreateNbt().putInt("Dirty", dirty - 1);
			return result;
		}
		return new ItemStack(stack.getItem().getRecipeRemainder());
	}


	/**
	 * Contains all the color providers for ancient seed items used by terravibe.
	 */
	public static class TerravibeColorProviders {
		private TerravibeColorProviders() {
		}

		public static int ancient_nightshade_fern_seeds(ItemStack stack, int tintIndex) {
			int dirty = stack.getOrCreateNbt().getInt("Dirty");
			return switch (dirty) {
				case 1 -> 0x814731;
				case 2 -> 0x736058;
				default -> -1;
			};
		}
	}
}
