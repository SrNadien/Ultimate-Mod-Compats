package nadiendev.umc.creatures.form;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;

public enum CreatureKind implements StringRepresentable {
    NONE("none"),
    WEREWOLF("werewolf"),
    VAMPIRE("vampire");

    public static final Codec<CreatureKind> CODEC = StringRepresentable.fromEnum(CreatureKind::values);
    public static final StreamCodec<ByteBuf, CreatureKind> STREAM_CODEC = ByteBufCodecs.idMapper(index -> values()[index], CreatureKind::ordinal);

    private final String id;

    CreatureKind(String id) {
        this.id = id;
    }

    @Override
    public String getSerializedName() {
        return id;
    }

    public static CreatureKind byName(String name) {
        for (CreatureKind kind : values()) {
            if (kind.id.equals(name)) {
                return kind;
            }
        }
        return NONE;
    }
}
