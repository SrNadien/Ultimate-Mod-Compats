package nadiendev.umc.spirit.registry;

import java.util.function.Supplier;
import nadiendev.umc.spirit.infusion.SpiritInfusions;
import nadiendev.umc.spirit.world.SpiritVisit;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.attachment.AttachmentType;

public final class SpiritAttachments {

    public static final Supplier<AttachmentType<SpiritInfusions>> INFUSIONS = SpiritRegistry.ATTACHMENT_TYPES.register("infusions",
            () -> AttachmentType.builder(() -> SpiritInfusions.EMPTY)
                    .serialize(SpiritInfusions.CODEC)
                    .sync(SpiritInfusions.STREAM_CODEC)
                    .copyOnDeath()
                    .build());

    public static final Supplier<AttachmentType<SpiritVisit>> VISIT = SpiritRegistry.ATTACHMENT_TYPES.register("visit",
            () -> AttachmentType.builder(() -> new SpiritVisit(Level.OVERWORLD, BlockPos.ZERO, 0.0F, -1L, -1L))
                    .serialize(SpiritVisit.CODEC)
                    .sync(SpiritVisit.STREAM_CODEC)
                    .build());

    private SpiritAttachments() {
    }

    public static void load() {
    }
}
