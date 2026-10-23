package nadiendev.umc.spirit.coven;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;

public class CovenData extends SavedData {

    private static final String NAME = "umc_spirit_covens";

    private final Map<UUID, Coven> covens = new LinkedHashMap<>();
    private final Map<UUID, UUID> memberIndex = new LinkedHashMap<>();

    public static CovenData get(MinecraftServer server) {
        return server.overworld().getDataStorage().computeIfAbsent(new Factory<>(CovenData::new, CovenData::load, null), NAME);
    }

    private static CovenData load(CompoundTag tag, HolderLookup.Provider registries) {
        CovenData data = new CovenData();
        for (Tag entry : tag.getList("covens", Tag.TAG_COMPOUND)) {
            Coven coven = Coven.load((CompoundTag) entry);
            if (!coven.members().isEmpty()) {
                data.covens.put(coven.id(), coven);
            }
        }
        data.reindex();
        return data;
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        ListTag list = new ListTag();
        covens.values().forEach(coven -> list.add(coven.save()));
        tag.put("covens", list);
        return tag;
    }

    private void reindex() {
        memberIndex.clear();
        covens.values().forEach(coven -> coven.memberIds().forEach(member -> memberIndex.put(member, coven.id())));
    }

    public Collection<Coven> all() {
        return new ArrayList<>(covens.values());
    }

    public Optional<Coven> byId(UUID id) {
        return Optional.ofNullable(covens.get(id));
    }

    public Optional<Coven> ofPlayer(UUID player) {
        UUID id = memberIndex.get(player);
        return id == null ? Optional.empty() : byId(id);
    }

    public Optional<Coven> byName(String name) {
        return covens.values().stream().filter(coven -> coven.name().equalsIgnoreCase(name)).findFirst();
    }

    public Coven create(String name, UUID leader, String leaderName) {
        Coven coven = new Coven(UUID.randomUUID(), name, leader, leaderName);
        covens.put(coven.id(), coven);
        memberIndex.put(leader, coven.id());
        setDirty();
        return coven;
    }

    public void addMember(Coven coven, UUID player, String name) {
        coven.addMember(player, name);
        memberIndex.put(player, coven.id());
        setDirty();
    }

    public void removeMember(Coven coven, UUID player) {
        coven.removeMember(player);
        memberIndex.remove(player);
        if (coven.members().isEmpty()) {
            covens.remove(coven.id());
        }
        setDirty();
    }

    public void invite(Coven coven, UUID player) {
        coven.invite(player);
        setDirty();
    }

    public void renameMember(Coven coven, UUID player, String name) {
        if (!name.equals(coven.memberName(player))) {
            coven.renameMember(player, name);
            setDirty();
        }
    }

    public void disband(Coven coven) {
        coven.memberIds().forEach(memberIndex::remove);
        covens.remove(coven.id());
        setDirty();
    }
}
