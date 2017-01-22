package cofh.lib.world.feature;

import cofh.lib.util.numbers.ConstantProvider;
import cofh.lib.util.numbers.INumberProvider;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class FeatureGenUniform extends FeatureBase {

	final WorldGenerator worldGen;
	final INumberProvider count;
	final INumberProvider minY;
	final INumberProvider maxY;

	public FeatureGenUniform(String name, WorldGenerator worldGen, int count, int minY, int maxY, GenRestriction biomeRes, boolean regen, GenRestriction dimRes) {

		this(name, worldGen, new ConstantProvider(count), new ConstantProvider(minY), new ConstantProvider(maxY), biomeRes, regen, dimRes);
	}

	public FeatureGenUniform(String name, WorldGenerator worldGen, INumberProvider count, INumberProvider minY, INumberProvider maxY, GenRestriction biomeRes, boolean regen, GenRestriction dimRes) {

		super(name, biomeRes, regen, dimRes);
		this.worldGen = worldGen;
		this.count = count;
		this.minY = minY;
		this.maxY = maxY;
	}

	@Override
	public boolean generateFeature(Random random, int chunkX, int chunkZ, World world) {

		int blockX = chunkX * 16;
		int blockZ = chunkZ * 16;

		BlockPos pos = new BlockPos(blockX, 64, blockZ);

		final int count = this.count.intValue(world, random, pos);

		boolean generated = false;
		for (int i = 0; i < count; i++) {
			int x = blockX + random.nextInt(16);
			final int minY = this.minY.intValue(world, random, pos), maxY = this.maxY.intValue(world, random, pos);
			int y = minY + random.nextInt(maxY - minY);
			int z = blockZ + random.nextInt(16);
			if (!canGenerateInBiome(world, x, z, random)) {
				continue;
			}
			generated |= worldGen.generate(world, random, new BlockPos(x, y, z));
		}
		return generated;
	}

}
