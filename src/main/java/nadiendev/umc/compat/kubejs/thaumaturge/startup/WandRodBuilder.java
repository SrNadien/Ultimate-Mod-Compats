package nadiendev.umc.compat.kubejs.thaumaturge.startup;

import dev.latvian.mods.rhino.util.HideFromJS;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.function.BiConsumer;
import nadiendev.umc.compat.kubejs.thaumaturge.util.ThaumaturgeIds;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class WandRodBuilder {

    private final ResourceLocation id;
    private int capacity = 25;
    private int craftCost = 1;
    private ResourceLocation texture;
    private boolean glow;
    private boolean staff;
    private boolean runes;
    private ResourceLocation assemblyResearch;
    private BiConsumer<ItemStack, Player> onUpdate;

    WandRodBuilder(ResourceLocation id) {
        this.id = id;
        this.texture = ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "textures/models/wand_rod_" + id.getPath() + ".png");
    }

    public WandRodBuilder capacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public WandRodBuilder craftCost(int craftCost) {
        this.craftCost = craftCost;
        return this;
    }

    public WandRodBuilder texture(String texture) {
        this.texture = ThaumaturgeIds.withDefault(texture, ThaumaturgeIds.KUBEJS);
        return this;
    }

    public WandRodBuilder glow(boolean glow) {
        this.glow = glow;
        return this;
    }

    public WandRodBuilder staff(boolean staff) {
        this.staff = staff;
        return this;
    }

    public WandRodBuilder runes(boolean runes) {
        this.runes = runes;
        return this;
    }

    public WandRodBuilder assemblyResearch(String research) {
        this.assemblyResearch = ThaumaturgeIds.thaumaturge(research);
        return this;
    }

    public WandRodBuilder onUpdate(BiConsumer<ItemStack, Player> callback) {
        this.onUpdate = callback;
        return this;
    }

    @HideFromJS
    ResourceLocation id() {
        return id;
    }

    @HideFromJS
    Object build() {
        Class<?> type = ThaumaturgeReflect.type("wands.WandRod");
        Class<?> updateType = ThaumaturgeReflect.type("wands.IWandRodOnUpdate");
        Object update = onUpdate == null ? null : proxy(updateType);
        return ThaumaturgeReflect.newInstance(type,
                List.of(int.class, int.class, ResourceLocation.class, updateType, boolean.class, boolean.class, boolean.class, ResourceLocation.class),
                capacity, craftCost, texture, update, glow, staff, runes, assemblyResearch);
    }

    private Object proxy(Class<?> updateType) {
        InvocationHandler handler = (proxy, method, args) -> {
            if ("onUpdate".equals(method.getName()) && args != null && args.length == 2) {
                onUpdate.accept((ItemStack) args[0], (Player) args[1]);
                return null;
            }
            return switch (method.getName()) {
                case "hashCode" -> System.identityHashCode(proxy);
                case "equals" -> proxy == args[0];
                default -> "WandRodOnUpdate[" + id + "]";
            };
        };
        return Proxy.newProxyInstance(updateType.getClassLoader(), new Class<?>[] {updateType}, handler);
    }
}
