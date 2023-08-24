package xyz.devpelux.terravibe.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import xyz.devpelux.terravibe.tags.TerravibeFluidTags;

import java.util.List;
import java.util.Optional;

/**
 * Ancient seed item.
 */
public class AncientSeedItem extends Item {
	protected final int minCleaningAmount;
	protected final int maxCleaningAmount;

	/**
	 * Initializes a new instance.
	 */
	public AncientSeedItem(Settings settings, int dirtyLevel) {
		super(settings);
		//Sets the cleaning amount basing on the dirty level.
		//More high the dirty level is, more low the cleaning amount is.
		//Every wash, will clean the item by a random cleaning amount between a minimum and a maximum.
		int avgCleaningAmount = 100 / MathHelper.clamp(dirtyLevel, 1, 100);
		this.minCleaningAmount = avgCleaningAmount - (avgCleaningAmount / 2);
		this.maxCleaningAmount = avgCleaningAmount + (avgCleaningAmount / 2);
	}

	/**
	 * Gets the dirty value from the stack, by default returns 100.
	 * If the item is not an ancient seed returns 0.
	 */
	public static int getDirtyValue(ItemStack stack) {
		if (stack.getItem() instanceof AncientSeedItem) {
			NbtCompound nbt = stack.getOrCreateNbt();
			if (nbt.contains("Dirty", NbtElement.INT_TYPE)) {
				return nbt.getInt("Dirty");
			}
			return 100;
		}
		return 0;
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

		//Shows the dirty value as a tooltip.
		int dirtyValue = getDirtyValue(stack);
		tooltip.add(Text.translatable("item.terravibe.ancient_seed.dirty").append(" " + dirtyValue + "%")
				.formatted(Formatting.DARK_GRAY));
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
				//Every wash, will clean the item by a random cleaning amount between a minimum and a maximum.
				ItemStack used = player.getStackInHand(hand);
				ItemStack result = ItemUsage.exchangeStack(used, player, getLessDirty(used, world.getRandom()));
				getWashSound().ifPresent(sound -> player.playSound(sound, 0.5f, 0.9f));
				return TypedActionResult.success(result, world.isClient());
			}
		}

		return TypedActionResult.pass(player.getStackInHand(hand));
	}

	/**
	 * Gets a less dirty stack.
	 */
	protected ItemStack getLessDirty(ItemStack stack, Random random) {
		//Tha random cleaning amount is generated in a max range between 1 and 150, considering all the cases.
		int newDirtyValue = getDirtyValue(stack) - random.nextBetween(minCleaningAmount, maxCleaningAmount);
		if (newDirtyValue > 0) {
			ItemStack result = new ItemStack(stack.getItem());
			result.getOrCreateNbt().putInt("Dirty", newDirtyValue);
			return result;
		}
		return stack.getRecipeRemainder();
	}
}
