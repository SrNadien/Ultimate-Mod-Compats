package nadiendev.umc.compat.kubejs.witchery.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import dev.latvian.mods.kubejs.recipe.RecipeScriptContext;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentType;
import dev.latvian.mods.rhino.type.TypeInfo;
import java.util.Locale;

public record HexColourComponent(RecipeComponentType<?> type) implements RecipeComponent<Integer> {

    public static final Codec<Integer> HEX_CODEC = Codec.STRING.comapFlatMap(HexColourComponent::decode, HexColourComponent::encode);

    @Override
    public Codec<Integer> codec() {
        return HEX_CODEC;
    }

    @Override
    public TypeInfo typeInfo() {
        return TypeInfo.NUMBER.or(TypeInfo.STRING);
    }

    @Override
    public Integer wrap(RecipeScriptContext cx, Object from) {
        if (from instanceof Number number) {
            return number.intValue() & 0xFFFFFF;
        }
        return parse(String.valueOf(from));
    }

    public static int parse(String raw) {
        String text = raw.trim();
        if (text.startsWith("#")) {
            text = text.substring(1);
        } else if (text.startsWith("0x") || text.startsWith("0X")) {
            text = text.substring(2);
        }
        return Integer.parseUnsignedInt(text, 16) & 0xFFFFFF;
    }

    public static String encode(int colour) {
        return String.format(Locale.ROOT, "%06X", colour & 0xFFFFFF);
    }

    private static DataResult<Integer> decode(String raw) {
        try {
            return DataResult.success(parse(raw));
        } catch (NumberFormatException ex) {
            return DataResult.error(() -> "Color hexadecimal no valido: " + raw);
        }
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
