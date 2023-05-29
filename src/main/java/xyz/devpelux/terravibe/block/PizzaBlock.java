package xyz.devpelux.terravibe.block;

import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import xyz.devpelux.terravibe.item.TerravibeItems;

import java.util.ArrayList;

/**
 * Famous italian food with various ingredients.
 */
public abstract class PizzaBlock extends HorizontalFacingBlock {
	/**
	 * Max number of slices.
	 */
	public static final int MAX_SLICES = 4;

	/**
	 * Slices available.
	 */
	public static final IntProperty SLICES;

	/**
	 * Outline shapes of the block.
	 */
	private static final VoxelShape[][] OUTLINE_SHAPES;

	/**
	 * Initializes a new {@link PizzaBlock}.
	 */
	public PizzaBlock(Settings settings) {
		super(settings);
		setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(SLICES, 4));
	}

	/**
	 * Generates a new block of pizza four cheese.
	 */
	public static PizzaBlock fourCheese(Settings settings) {
		return new PizzaBlock(settings) {
			/**
			 * Gets the item of the slice.
			 */
			@Override
			public ItemConvertible getSliceItem() {
				return TerravibeItems.PIZZA_SLICE_FOUR_CHEESE;
			}
		};
	}

	/**
	 * Generates a new block of pizza margherita.
	 */
	public static PizzaBlock margherita(Settings settings) {
		return new PizzaBlock(settings) {
			/**
			 * Gets the item of the slice.
			 */
			@Override
			public ItemConvertible getSliceItem() {
				return TerravibeItems.PIZZA_SLICE_MARGHERITA;
			}
		};
	}

	/**
	 * Registers the properties of the block.
	 */
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(FACING, SLICES);
	}

	/**
	 * Gets the item of the slice.
	 */
	public abstract ItemConvertible getSliceItem();

	/**
	 * Gets the placement state of the block.
	 */
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		//When the player places the block, gets the opposite direction.
		return getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing());
	}

	/**
	 * Executed when the block is used.
	 * Eats a slice.
	 */
	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (player.getStackInHand(hand).isEmpty()) {
			if (!world.isClient()) {
				//Removes a slice.
				int currentSlices = state.get(SLICES);
				if (currentSlices > 1) {
					world.setBlockState(pos, state.with(SLICES, currentSlices - 1));
				} else {
					//If there are no slices left, removes the block.
					world.removeBlock(pos, false);
					world.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);
				}

				//Gives the slice to the player hand.
				player.setStackInHand(hand, new ItemStack(getSliceItem()));
			}

			//Client: SUCCESS / Server: CONSUME
			return ActionResult.success(world.isClient());
		}

		return ActionResult.PASS;
	}

	/**
	 * Gets a value indicating if the block can be placed at the current position.
	 */
	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		return world.getBlockState(pos.down()).isSolid();
	}

	/**
	 * Executed when a neighbor block is updated.
	 * Breaks the block if the bottom is not a solid block.
	 */
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState,
	                                            WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		return direction == Direction.DOWN && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState()
				: super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	/**
	 * Gets a value indicating if the block gives an output to comparators.
	 */
	@Override
	public boolean hasComparatorOutput(BlockState state) {
		return true;
	}

	/**
	 * Gets the comparator output value.
	 */
	@Override
	public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
		return state.get(SLICES) * 2;
	}

	/**
	 * Gets a value indicating if is possible to pass through the block.
	 */
	@Override
	public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
		return false;
	}

	/**
	 * Gets the outline shape of the block.
	 */
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return OUTLINE_SHAPES[state.get(FACING).getHorizontal()][state.get(SLICES)];
	}

	/**
	 * Generates a voxel shape for the block with the specified angles.
	 */
	private static VoxelShape generateVoxelShape(boolean northEast, boolean southEast, boolean southWest, boolean northWest) {
		ArrayList<VoxelShape> shapeList = new ArrayList<>();

		if (northEast) shapeList.add(Block.createCuboidShape(8, 0, 1, 15, 1, 8));
		if (southEast) shapeList.add(Block.createCuboidShape(8, 0, 8, 15, 1, 15));
		if (southWest) shapeList.add(Block.createCuboidShape(1, 0, 8, 8, 1, 15));
		if (northWest) shapeList.add(Block.createCuboidShape(1, 0, 1, 8, 1, 8));

		if (shapeList.size() == 0) return VoxelShapes.empty();
		return shapeList.stream().reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
	}

	static {
		SLICES = IntProperty.of("slices", 1, MAX_SLICES);

		//facing | slices
		//facing: south, west, north, east
		OUTLINE_SHAPES = new VoxelShape[][]{
				{
						generateVoxelShape(false, false, false, false),
						generateVoxelShape(true, false, false, false),
						generateVoxelShape(true, true, false, false),
						generateVoxelShape(true, true, true, false),
						generateVoxelShape(true, true, true, true),
				},
				{
						generateVoxelShape(false, false, false, false),
						generateVoxelShape(false, true, false, false),
						generateVoxelShape(false, true, true, false),
						generateVoxelShape(false, true, true, true),
						generateVoxelShape(true, true, true, true),
				},
				{
						generateVoxelShape(false, false, false, false),
						generateVoxelShape(false, false, true, false),
						generateVoxelShape(false, false, true, true),
						generateVoxelShape(true, false, true, true),
						generateVoxelShape(true, true, true, true)
				},
				{
						generateVoxelShape(false, false, false, false),
						generateVoxelShape(false, false, false, true),
						generateVoxelShape(true, false, false, true),
						generateVoxelShape(true, true, false, true),
						generateVoxelShape(true, true, true, true),
				}
		};
	}
}
