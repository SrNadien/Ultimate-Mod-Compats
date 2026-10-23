package nadiendev.umc.creatures.registry;

import java.util.ArrayList;
import java.util.List;
import nadiendev.umc.UltimateModCompats;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

public final class CreaturesSounds {

    public record SoundSpec(String name, String vanillaEvent, float volume, float pitch) {
    }

    public static final List<SoundSpec> SPECS = new ArrayList<>();

    public static final DeferredHolder<SoundEvent, SoundEvent> WEREWOLF_AMBIENT = sound("entity.werewolf.ambient", "minecraft:entity.wolf.growl", 1.0F, 0.6F);
    public static final DeferredHolder<SoundEvent, SoundEvent> WEREWOLF_HURT = sound("entity.werewolf.hurt", "minecraft:entity.wolf.hurt", 1.0F, 0.6F);
    public static final DeferredHolder<SoundEvent, SoundEvent> WEREWOLF_DEATH = sound("entity.werewolf.death", "minecraft:entity.wolf.death", 1.0F, 0.5F);
    public static final DeferredHolder<SoundEvent, SoundEvent> WEREWOLF_HOWL = sound("entity.werewolf.howl", "minecraft:entity.wolf.howl", 1.4F, 0.6F);

    public static final DeferredHolder<SoundEvent, SoundEvent> VAMPIRE_AMBIENT = sound("entity.vampire.ambient", "minecraft:entity.evoker.ambient", 0.9F, 0.8F);
    public static final DeferredHolder<SoundEvent, SoundEvent> VAMPIRE_HURT = sound("entity.vampire.hurt", "minecraft:entity.evoker.hurt", 1.0F, 0.9F);
    public static final DeferredHolder<SoundEvent, SoundEvent> VAMPIRE_DEATH = sound("entity.vampire.death", "minecraft:entity.evoker.death", 1.0F, 0.8F);
    public static final DeferredHolder<SoundEvent, SoundEvent> VAMPIRE_TRANSFORM = sound("entity.vampire.transform", "minecraft:entity.bat.takeoff", 1.2F, 0.6F);
    public static final DeferredHolder<SoundEvent, SoundEvent> VAMPIRE_BITE = sound("entity.vampire.bite", "minecraft:entity.phantom.bite", 1.0F, 0.9F);

    public static final DeferredHolder<SoundEvent, SoundEvent> GHOST_AMBIENT = sound("entity.ghost.ambient", "minecraft:entity.vex.ambient", 0.9F, 0.5F);
    public static final DeferredHolder<SoundEvent, SoundEvent> GHOST_HURT = sound("entity.ghost.hurt", "minecraft:entity.vex.hurt", 1.0F, 0.6F);
    public static final DeferredHolder<SoundEvent, SoundEvent> GHOST_DEATH = sound("entity.ghost.death", "minecraft:entity.vex.death", 1.0F, 0.6F);

    public static final DeferredHolder<SoundEvent, SoundEvent> IMP_AMBIENT = sound("entity.imp.ambient", "minecraft:entity.blaze.ambient", 0.8F, 1.7F);
    public static final DeferredHolder<SoundEvent, SoundEvent> IMP_HURT = sound("entity.imp.hurt", "minecraft:entity.blaze.hurt", 1.0F, 1.6F);
    public static final DeferredHolder<SoundEvent, SoundEvent> IMP_DEATH = sound("entity.imp.death", "minecraft:entity.blaze.death", 1.0F, 1.6F);
    public static final DeferredHolder<SoundEvent, SoundEvent> IMP_FIREBALL = sound("entity.imp.fireball", "minecraft:entity.blaze.shoot", 1.0F, 1.4F);

    public static final DeferredHolder<SoundEvent, SoundEvent> ENT_AMBIENT = sound("entity.ent.ambient", "minecraft:entity.ravager.ambient", 1.0F, 0.45F);
    public static final DeferredHolder<SoundEvent, SoundEvent> ENT_HURT = sound("entity.ent.hurt", "minecraft:entity.iron_golem.hurt", 1.0F, 0.45F);
    public static final DeferredHolder<SoundEvent, SoundEvent> ENT_DEATH = sound("entity.ent.death", "minecraft:entity.iron_golem.death", 1.0F, 0.45F);
    public static final DeferredHolder<SoundEvent, SoundEvent> ENT_STEP = sound("entity.ent.step", "minecraft:block.wood.step", 1.0F, 0.6F);

    public static final DeferredHolder<SoundEvent, SoundEvent> SUMMON = sound("item.summon", "minecraft:entity.evoker.prepare_summon", 1.0F, 0.8F);
    public static final DeferredHolder<SoundEvent, SoundEvent> BANISH = sound("item.banish", "minecraft:entity.evoker.cast_spell", 1.0F, 0.7F);

    private CreaturesSounds() {
    }

    static void init() {
    }

    private static DeferredHolder<SoundEvent, SoundEvent> sound(String name, String vanillaEvent, float volume, float pitch) {
        SPECS.add(new SoundSpec(name, vanillaEvent, volume, pitch));
        return CreaturesRegistry.SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(UltimateModCompats.MODID, name)));
    }
}
