package xyz.devpelux.terravibe.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.client.color.block.BlockColorProvider;
import net.minecraft.item.ItemConvertible;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;
import xyz.devpelux.terravibe.item.TerravibeItems;

import java.util.Optional;

/**
 * An extinct fern that produces nightshade fern blueberries.
 */
public class NightshadeFernBlock extends TallFruitCropBlock implements BlockColorProvider {
	/**
	 * Settings of the block.
	 */
	public static final Settings SETTINGS;

	/**
	 * Voxel shapes of the lower block the crop.
	 */
	private static final VoxelShape[] LOWER_AGE_TO_SHAPE;

	/**
	 * Voxel shapes of the upper block the crop.
	 */
	private static final VoxelShape[] UPPER_AGE_TO_SHAPE;

	/**
	 * Initializes a new {@link TallFruitCropBlock}.
	 */
	public NightshadeFernBlock(Settings settings) {
		super(settings);
	}

	/**
	 * Initializes a new {@link NightshadeFernBlock} with default settings.
	 */
	public static NightshadeFernBlock of() {
		return new NightshadeFernBlock(SETTINGS);
	}

	/**
	 * Gets the age when the block must have an upper and lower block.
	 */
	@Override
	public int getAgeForUpper() {
		return 3;
	}

	/**
	 * Gets the age when the plant is fully grown, and is ready to make flowers, then fruits.
	 */
	@Override
	public int getPreFloweringAge() {
		return 5;
	}

	/**
	 * Gets the fruit item to pick from the plant.
	 */
	@Override
	public ItemConvertible getFruitItem() {
		return TerravibeItems.NIGHTSHADE_FERN_BLUEBERRIES;
	}

	/**
	 * Gets the seeds item of the block.
	 */
	@Override
	public ItemConvertible getSeedsItem() {
		return TerravibeItems.NIGHTSHADE_FERN_SEEDS;
	}

	/**
	 * Gets the pick sound.
	 */
	@Override
	public Optional<SoundEvent> getPickSound() {
		return Optional.of(SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES);
	}

	/**
	 * Gets the amount of the fruit item to pick from the plant.
	 */
	@Override
	protected int getFruitItemAmount(Random random, BlockState state) {
		return random.nextBetween(1, 2);
	}

	/**
	 * Gets a value indicating if the crop can be planted on top of the specified block.
	 */
	@Override
	protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
		return floor.isIn(BlockTags.DIRT) || floor.isOf(Blocks.FARMLAND);
	}

	/**
	 * Gets the outline shape of the lower block.
	 */
	@Override
	public VoxelShape getLowerOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		Vec3d vec3d = state.getModelOffset(world, pos);
		return LOWER_AGE_TO_SHAPE[getAge(state)].offset(vec3d.x, vec3d.y, vec3d.z);
	}

	/**
	 * Gets the outline shape of the upper block.
	 */
	@Override
	public VoxelShape getUpperOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		Vec3d vec3d = state.getModelOffset(world, pos);
		return UPPER_AGE_TO_SHAPE[getAge(state)].offset(vec3d.x, vec3d.y, vec3d.z);
	}

	/**
	 * Gets the colors of the block.
	 */
	@Override
	public int getColor(BlockState state, @Nullable BlockRenderView world, @Nullable BlockPos pos, int tintIndex) {
		return switch (tintIndex) {
			case 1 -> 0xaaffaa;
			case 2 -> getAge(state) == 7 ? 0x4d4067 : 0x569211;
			default -> -1;
		};
	}

	static {
		SETTINGS = FabricBlockSettings.of(Material.PLANT)
				.nonOpaque()
				.noCollision()
				.ticksRandomly()
				.breakInstantly()
				.sounds(BlockSoundGroup.CROP)
				.offsetType(OffsetType.XZ);
		LOWER_AGE_TO_SHAPE = new VoxelShape[]{
				Block.createCuboidShape(2.0, -1.0, 2.0, 14.0, 3.0, 14.0),
				Block.createCuboidShape(2.0, -1.0, 2.0, 14.0, 9.0, 14.0),
				Block.createCuboidShape(2.0, -1.0, 2.0, 14.0, 15.0, 14.0),
				Block.createCuboidShape(2.0, -1.0, 2.0, 14.0, 15.0, 14.0),
				Block.createCuboidShape(2.0, -1.0, 2.0, 14.0, 15.0, 14.0),
				Block.createCuboidShape(2.0, -1.0, 2.0, 14.0, 15.0, 14.0),
				Block.createCuboidShape(2.0, -1.0, 2.0, 14.0, 15.0, 14.0),
				Block.createCuboidShape(2.0, -1.0, 2.0, 14.0, 15.0, 14.0)
		};
		UPPER_AGE_TO_SHAPE = new VoxelShape[]{
				VoxelShapes.empty(),
				VoxelShapes.empty(),
				VoxelShapes.empty(),
				Block.createCuboidShape(2.0, -1.0, 2.0, 14.0, 5.0, 14.0),
				Block.createCuboidShape(2.0, -1.0, 2.0, 14.0, 11.0, 14.0),
				Block.createCuboidShape(2.0, -1.0, 2.0, 14.0, 15.0, 14.0),
				Block.createCuboidShape(2.0, -1.0, 2.0, 14.0, 15.0, 14.0),
				Block.createCuboidShape(2.0, -1.0, 2.0, 14.0, 15.0, 14.0)
		};
	}
}
