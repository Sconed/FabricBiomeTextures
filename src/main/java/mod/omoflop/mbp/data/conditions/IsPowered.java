package mod.omoflop.mbp.data.conditions;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import mod.omoflop.mbp.data.BlockModelPredicate;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

public class IsPowered extends BlockModelPredicate {
   private final boolean CHECK_IS_EMITTING;
    public IsPowered(boolean emitting_arg) {
	    CHECK_IS_EMITTING =emitting_arg;
    }

    @Override
    public boolean meetsCondition(BlockView world, BlockPos pos, BlockState state, Identifier renderContext) {
	    ClientWorld realWorld = MinecraftClient.getInstance().world;
	    assert realWorld!= null;
	    return	realWorld.isReceivingRedstonePower(pos) 			  &&
	    (	    !CHECK_IS_EMITTING								  ||
	    (		realWorld.isEmittingRedstonePower(pos, Direction.UP	) ||
	    			realWorld.isEmittingRedstonePower(pos, Direction.DOWN	) ||
	    			realWorld.isEmittingRedstonePower(pos, Direction.NORTH	) ||
	    			realWorld.isEmittingRedstonePower(pos, Direction.SOUTH	) ||
	    			realWorld.isEmittingRedstonePower(pos, Direction.EAST	) ||
	    			realWorld.isEmittingRedstonePower(pos, Direction.WEST	)
	    )
	    );
    }

    public static IsPowered parse(JsonElement arg) {
        JsonObject obj = arg.getAsJsonObject();
	   
	   boolean checkEmitting=false;
	    if (obj.has("is_emitting")) checkEmitting = obj.get("is_emitting").getAsBoolean();
	   
	   return new IsPowered(checkEmitting);
    }
}
