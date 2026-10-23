package nadiendev.umc.creatures.registry;

import com.mojang.serialization.Codec;
import java.util.function.Supplier;
import nadiendev.umc.creatures.form.CreatureCurse;
import net.neoforged.neoforge.attachment.AttachmentType;

public final class CreaturesAttachments {

    public static final Supplier<AttachmentType<CreatureCurse>> CURSE = CreaturesRegistry.ATTACHMENT_TYPES.register("creature_curse", () -> AttachmentType.builder(() -> CreatureCurse.NONE)
            .serialize(CreatureCurse.CODEC)
            .sync(CreatureCurse.STREAM_CODEC)
            .copyOnDeath()
            .build());

    public static final Supplier<AttachmentType<Boolean>> HUNTER = CreaturesRegistry.ATTACHMENT_TYPES.register("hunter", () -> AttachmentType.builder(() -> Boolean.FALSE)
            .serialize(Codec.BOOL)
            .copyOnDeath()
            .build());

    private CreaturesAttachments() {
    }

    static void init() {
    }
}
