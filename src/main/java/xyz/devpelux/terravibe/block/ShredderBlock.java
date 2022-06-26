package xyz.devpelux.terravibe.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.devpelux.terravibe.blockentity.ShredderBlockEntity;
import xyz.devpelux.terravibe.core.ModInfo;

import java.util.stream.Stream;

/** Shreds a bunch of items to obtain something. */
public class ShredderBlock extends BlockWithEntity {
	/** Identifier of the block. */
	public static final Identifier ID =  new Identifier(ModInfo.MOD_ID, "shredder");

	/** Facing property defining the orientation of the block. */
	public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

	/** Voxel shape of the block. */
	private static VoxelShape VOXEL_SHAPE = null;

	/** Initializes a new {@link ShredderBlock}. */
	public ShredderBlock(Settings settings) {
		super(settings);
		setDefaultState(getStateManager().getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH));
	}

	/** Gets the block settings. */
	public static @NotNull FabricBlockSettings getSettings() {
		return FabricBlockSettings.of(Material.METAL, MapColor.ORANGE)
				.requiresTool()
				.strength(3.0F, 6.0F)
				.sounds(BlockSoundGroup.COPPER);
	}

	/**
	 * Executed when the block is used.<br>
	 * Displays the UI of the block.
	 */
	@SuppressWarnings("deprecation")
	@Override
	public ActionResult onUse(BlockState state, @NotNull World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (!world.isClient) {
			//Creates the screen handler for the UI.
			NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);

			if (screenHandlerFactory != null) {
				//Displays the UI.
				player.openHandledScreen(screenHandlerFactory);
			}
		}
		return ActionResult.SUCCESS;
	}

	/** Creates the block entity for the block. */
	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new ShredderBlockEntity(pos, state);
	}

	/** Gets the render type of the block. */
	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	/** Registers the properties of the block. */
	@Override
	protected void appendProperties(StateManager.@NotNull Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(Properties.HORIZONTAL_FACING);
	}

	/** Gets the correct orientation of the block basing on the way the player places it. */
	@Override
	public BlockState getPlacementState(@NotNull ItemPlacementContext ctx) {
		//When the player places the block, gets the opposite direction.
		return this.getDefaultState().with(Properties.HORIZONTAL_FACING, ctx.getPlayerFacing().getOpposite());
	}

	/** Rotates the block in the specified direction. */
	@SuppressWarnings("deprecation")
	@Override
	public BlockState rotate(@NotNull BlockState state, @NotNull BlockRotation rotation) {
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}

	/** Mirrors the block in the specified direction. */
	@SuppressWarnings("deprecation")
	@Override
	public BlockState mirror(@NotNull BlockState state, @NotNull BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(FACING)));
	}

	/** Gets the outline shape of the block. */
	@SuppressWarnings("deprecation")
	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return getVoxelShape();
	}

	/** Gets the ray-cast shape of the block. */
	@SuppressWarnings("deprecation")
	@Override
	public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
		return getVoxelShape();
	}

	/** Gets the voxel shape of the block. */
	public static @NotNull VoxelShape getVoxelShape() {
		if (VOXEL_SHAPE == null) {
			VOXEL_SHAPE = Stream.of(
							Block.createCuboidShape(2, 14, 2, 12, 16, 4),
							Block.createCuboidShape(2, 14, 4, 4, 16, 14),
							Block.createCuboidShape(4, 14, 12, 14, 16, 14),
							Block.createCuboidShape(12, 14, 2, 14, 16, 12),
							Block.createCuboidShape(3, 12, 3, 11, 14, 5),
							Block.createCuboidShape(3, 12, 5, 5, 14, 13),
							Block.createCuboidShape(5, 12, 11, 13, 14, 13),
							Block.createCuboidShape(11, 12, 3, 13, 14, 11),
							Block.createCuboidShape(4, 0, 4, 12, 12, 12))
					.reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
		}
		return VOXEL_SHAPE;
	}
}
