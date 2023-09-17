package xyz.devpelux.terravibe.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import xyz.devpelux.terravibe.item.TerravibeItems;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * A particle spreader for dusts.
 */
public class SpreaderPotBlock extends Block {
	/**
	 * Level of the content.
	 */
	public static final IntProperty LEVEL;

	/**
	 * Current plug.
	 */
	public static final EnumProperty<Plug> PLUG;

	/**
	 * Spreading settings.
	 */
	protected final SpreadingSettings spreadingSettings;

	/**
	 * Initializes a new instance.
	 */
	public SpreaderPotBlock(Settings settings, SpreadingSettings spreadingSettings) {
		super(settings);
		this.spreadingSettings = Objects.requireNonNull(spreadingSettings);
		setDefaultState(getDefaultState().with(LEVEL, 0).with(PLUG, Plug.None));
	}

	/**
	 * Registers the properties of the block.
	 */
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(LEVEL, PLUG);
	}

	/**
	 * Executed when the block is used.
	 * Interacts with the block basing on the used item.
	 */
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		//Gets the stack in the main hand.
		ItemStack inHand = player.getStackInHand(Hand.MAIN_HAND);

		//Gets the plug and the corresponding item.
		Plug plug = state.get(PLUG);

		//Select the appropriate action basing on the current plug.
		switch (plug) {
			case Plug, DarkPlug -> {
				//The spreader is plugged.
				//Removes the plug if the player is interacting with empty stack.
				//Then returns the plug to the player if is not in creative.
				if (inHand.isEmpty()) {
					world.setBlockState(pos, state.with(PLUG, Plug.None));
					if (!player.getAbilities().creativeMode) {
						Item plugItem = plug == Plug.DarkPlug ? TerravibeItems.DARK_CORK_PLUG : TerravibeItems.CORK_PLUG;
						player.getInventory().offerOrDrop(new ItemStack(plugItem));
					}
				}
			}
			case None -> {
				//The spreader is not plugged.
				//Interacts with the item if is a supported item.
				//todo creare un registro di item <-> blocchi che associa ad un item contenibile nello spreader un blocco spreader.
				//todo esempio: birch_mold_dust <-> birch_mold_dust_spreader.
			}
		}

		return ActionResult.PASS;
	}

	/**
	 * Executed when the block is used with a supported containable item.
	 * Interacts with the block basing on the used item.
	 */
	protected ActionResult onUseItem(BlockState state, World world, BlockPos pos, ItemStack usedStack, Block associatedBlock) {
		//todo chiamare questo metodo da onUse inviando l'item contenibile e il blocco associato.
		//todo verificare se è davvero necessario un metodo separato.
		//Client: SUCCESS / Server: CONSUME
		return ActionResult.success(world.isClient());
	}

	/**
	 * Executed at the block breaking.
	 * Drops the content and the plug.
	 */
	@Override
	public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		super.onBreak(world, pos, state, player);

		//The drop is supported only in server world, when the player is not in creative mode.
		if (!world.isClient() && !player.getAbilities().creativeMode) {

			//Gets the item that can be contained in the spreader.
			Item content = spreadingSettings.containable.get().asItem();
			//Gets the quantity of the content.
			int contentQuantity = state.get(LEVEL);
			if (contentQuantity > 0) {
				//Drops the content.
				dropStack(world, pos, content.getDefaultStack().copyWithCount(contentQuantity));
			}

			//Drops the plug if the spreader is plugged.
			Plug plug = state.get(PLUG);
			if (plug != Plug.None) {
				Item plugItem = plug == Plug.DarkPlug ? TerravibeItems.DARK_CORK_PLUG : TerravibeItems.CORK_PLUG;
				dropStack(world, pos, new ItemStack(plugItem));
			}
		}
	}

	/**
	 * Gets a value indicating if the block reacts with the ticking system.
	 */
	@Override
	public boolean hasRandomTicks(BlockState state) {
		//Has random ticks if is not plugged and the level is greater than zero.
		return state.get(PLUG) == Plug.None && state.get(LEVEL) > 0;
	}

	/**
	 * Executed every tick randomly.
	 * Consumes the content.
	 */
	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		//Consumes the content.
		int level = state.get(LEVEL);
		//More content is faster to consume (more probably).
		int consumingTime = spreadingSettings.consumingTime * (2 - level + 1);
		if (random.nextInt(consumingTime) == 0) {
			//If the consuming is successful, decreases the current level by 1.
			int newLevel = Math.max(level - 1, 0);
			world.setBlockState(pos, state.with(LEVEL, newLevel));
		}
	}

	/**
	 * Executed every tick randomly to handle effects.
	 */
	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		//Particles are displayed if the spreader is not plugged.
		if (state.get(PLUG) == Plug.None) {
			//Gets the particle to spawn.
			DefaultParticleType particle = spreadingSettings.particle;
			//Gets the level of the content.
			int level = state.get(LEVEL);

			//Generates the particles floating away from the block.
			for (int i = 0; i < spreadingSettings.spreadPerTick; ++i) {
				int time = 2 - level;
				if (time == 0 || random.nextInt(time) == 0) {
					//Spawns a particle in a random position in the block.
					double x = pos.getX() + (0.4d + (random.nextDouble() * 0.4d));
					double y = pos.getY() + (0.4d + (random.nextDouble() * 0.4d));
					double z = pos.getZ() + (0.4d + (random.nextDouble() * 0.4d));
					world.addParticle(particle, x, y, z, 0d, 0.3d, 0d);
				}
			}

			//Generates the wandering particles at random positions.
			BlockPos.Mutable randomPos = new BlockPos.Mutable();
			for (int i = 0; i < level; ++i) {
				//Select a random spawn position between a radius of 10.
				int posX = pos.getX() + random.nextBetween(-spreadingSettings.spreadingRadius, spreadingSettings.spreadingRadius);
				int posY = pos.getY() + random.nextInt(spreadingSettings.spreadingHeight);
				int posZ = pos.getZ() + random.nextBetween(-spreadingSettings.spreadingRadius, spreadingSettings.spreadingRadius);
				randomPos.set(posX, posY, posZ);

				//If the position has air, then spawns a particle in a random position in the block.
				if (!world.getBlockState(randomPos).isFullCube(world, randomPos)) {
					double x = randomPos.getX() + random.nextDouble();
					double y = randomPos.getY() + random.nextDouble();
					double z = randomPos.getZ() + random.nextDouble();
					world.addParticle(particle, x, y, z, 0d, 0d, 0d);
				}
			}
		}
	}

	static {
		LEVEL = IntProperty.of("level", 0, 2);
		PLUG = EnumProperty.of("plug", Plug.class);
	}


	/**
	 * Represents the plug currently used.
	 */
	public enum Plug implements StringIdentifiable {
		/**
		 * Opened.
		 */
		None("none"),

		/**
		 * Closed with plug.
		 */
		Plug("plug"),

		/**
		 * Closed with dark plug.
		 */
		DarkPlug("dark_plug");

		/**
		 * Name representing the value.
		 */
		private final String name;

		/**
		 * Initializes a new value with the name specified.
		 */
		Plug(String name) {
			this.name = name;
		}

		/**
		 * Returns the string representation of this instance.
		 */
		@Override
		public String asString() {
			return this.name;
		}

		/**
		 * Returns the string representation of this instance.
		 */
		@Override
		public String toString() {
			return asString();
		}
	}


	/**
	 * Contains parameters for the spreader.
	 */
	public static final class SpreadingSettings {
		/**
		 * Item containable.
		 */
		private final Supplier<ItemConvertible> containable;

		/**
		 * Particle type to spread.
		 */
		private final DefaultParticleType particle;

		/**
		 * Consuming time.
		 */
		private final int consumingTime;

		/**
		 * Max height from the current position to spread the wandering particles.
		 */
		private final int spreadingHeight;

		/**
		 * Max radius from the current position to spread the wandering particles.
		 */
		private final int spreadingRadius;

		/**
		 * Number of particles to spread every tick.
		 */
		private final int spreadPerTick;

		/**
		 * Initializes a new instance.
		 */
		public SpreadingSettings(Supplier<ItemConvertible> containable, DefaultParticleType particle, int consumingTime, int spreadingHeight, int spreadingRadius, int spreadPerTick) {
			this.containable = containable;
			this.particle = particle;
			this.consumingTime = consumingTime;
			this.spreadingHeight = spreadingHeight;
			this.spreadingRadius = spreadingRadius;
			this.spreadPerTick = spreadPerTick;
		}
	}
}
