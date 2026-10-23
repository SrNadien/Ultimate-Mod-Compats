package nadiendev.umc.compat.kubejs.witchery.item;

import dev.latvian.mods.kubejs.item.ItemBuilder;
import dev.latvian.mods.rhino.util.HideFromJS;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.favouriteless.enchanted.common.enchanted.poppet.PoppetColour;
import net.favouriteless.enchanted.common.enchanted.poppet.effects.ConsumerPlayerPoppetEffect;
import net.favouriteless.enchanted.common.items.poppets.PlayerPoppetItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class PlayerPoppetItemBuilder extends ItemBuilder {

    private static final int DEFAULT_REGENERATION_TICKS = 100;
    private static final int DEFAULT_REGENERATION_AMPLIFIER = 1;

    private transient PoppetColour colour = PoppetColour.MAGIC;
    private transient Predicate<DamageSource> predicate = source -> false;
    private final transient List<Supplier<MobEffectInstance>> effects = new ArrayList<>();
    private transient Consumer<Player> protectCallback = player -> { };

    public PlayerPoppetItemBuilder(ResourceLocation id) {
        super(id);
        texture("enchanted:item/poppet");
        this.maxStackSize = 1;
        this.maxDamage = 1;
    }

    public PlayerPoppetItemBuilder poppetColour(long primary, long secondary) {
        this.colour = new PoppetColour((int) primary, (int) secondary);
        return this;
    }

    public PlayerPoppetItemBuilder protectsAgainst(String... damageTypesOrTags) {
        List<WitcheryEffects.DamageMatcher> matchers = WitcheryEffects.damageMatchers(damageTypesOrTags);
        this.predicate = source -> matchers.stream().anyMatch(matcher -> matcher.matches(source));
        return this;
    }

    public PlayerPoppetItemBuilder protectsWhen(Predicate<DamageSource> predicate) {
        this.predicate = predicate;
        return this;
    }

    public PlayerPoppetItemBuilder effect(String effect, int ticks, int amplifier) {
        effects.add(WitcheryEffects.effect(effect, ticks, amplifier));
        return this;
    }

    public PlayerPoppetItemBuilder onProtect(Consumer<Player> callback) {
        this.protectCallback = callback;
        return this;
    }

    @Override
    @HideFromJS
    public Item createObject() {
        List<Supplier<MobEffectInstance>> applied = new ArrayList<>(effects);
        if (applied.isEmpty()) {
            applied.add(() -> new MobEffectInstance(MobEffects.REGENERATION, DEFAULT_REGENERATION_TICKS, DEFAULT_REGENERATION_AMPLIFIER));
        }
        return new PlayerPoppetItem(colour, new ConsumerPlayerPoppetEffect(predicate, protectCallback, applied), createItemProperties());
    }
}
