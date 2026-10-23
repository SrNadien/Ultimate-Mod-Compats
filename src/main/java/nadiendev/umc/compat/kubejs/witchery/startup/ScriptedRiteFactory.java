package nadiendev.umc.compat.kubejs.witchery.startup;

import com.google.gson.JsonElement;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import net.favouriteless.enchanted.api.circle_magic.RiteFactory;
import net.favouriteless.enchanted.common.enchanted.circle_magic.rites.Rite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public record ScriptedRiteFactory(RiteDefinition definition, Map<String, JsonElement> params) implements RiteFactory {

    private static final String TYPE_KEY = "type";

    public static MapCodec<ScriptedRiteFactory> codec(RiteDefinition definition) {
        return new MapCodec<>() {
            @Override
            public <T> Stream<T> keys(DynamicOps<T> ops) {
                return Stream.empty();
            }

            @Override
            public <T> DataResult<ScriptedRiteFactory> decode(DynamicOps<T> ops, MapLike<T> input) {
                Map<String, JsonElement> params = new LinkedHashMap<>();
                input.entries().forEach(pair -> ops.getStringValue(pair.getFirst()).result().ifPresent(key -> {
                    if (!TYPE_KEY.equals(key)) {
                        params.put(key, new Dynamic<>(ops, pair.getSecond()).convert(JsonOps.INSTANCE).getValue());
                    }
                }));
                return DataResult.success(new ScriptedRiteFactory(definition, params));
            }

            @Override
            public <T> RecordBuilder<T> encode(ScriptedRiteFactory input, DynamicOps<T> ops, RecordBuilder<T> prefix) {
                input.params().forEach((key, value) -> prefix.add(key, JsonOps.INSTANCE.convertTo(ops, value)));
                return prefix;
            }
        };
    }

    @Override
    public Rite create(Rite.BaseRiteParams baseParams, Rite.RiteParams riteParams) {
        return new ScriptedRite(baseParams, riteParams, this);
    }

    @Override
    public ResourceLocation id() {
        return definition.id();
    }

    @Override
    public @Nullable List<ItemStack> getOutputs() {
        return definition.outputStacks();
    }
}
