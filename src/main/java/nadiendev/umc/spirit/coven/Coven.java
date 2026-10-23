package nadiendev.umc.spirit.coven;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;

public final class Coven {

    private final UUID id;
    private String name;
    private UUID leader;
    private final Map<UUID, String> members = new LinkedHashMap<>();
    private final Set<UUID> invites = new LinkedHashSet<>();

    public Coven(UUID id, String name, UUID leader, String leaderName) {
        this.id = id;
        this.name = name;
        this.leader = leader;
        this.members.put(leader, leaderName);
    }

    public UUID id() {
        return id;
    }

    public String name() {
        return name;
    }

    public UUID leader() {
        return leader;
    }

    public Map<UUID, String> members() {
        return Collections.unmodifiableMap(members);
    }

    public Set<UUID> memberIds() {
        return Collections.unmodifiableSet(members.keySet());
    }

    public boolean isMember(UUID player) {
        return members.containsKey(player);
    }

    public boolean isInvited(UUID player) {
        return invites.contains(player);
    }

    public String memberName(UUID player) {
        return members.getOrDefault(player, player.toString());
    }

    void addMember(UUID player, String playerName) {
        members.put(player, playerName);
        invites.remove(player);
    }

    void removeMember(UUID player) {
        members.remove(player);
        if (player.equals(leader) && !members.isEmpty()) {
            leader = members.keySet().iterator().next();
        }
    }

    void renameMember(UUID player, String playerName) {
        if (members.containsKey(player)) {
            members.put(player, playerName);
        }
    }

    void invite(UUID player) {
        invites.add(player);
    }

    void setName(String name) {
        this.name = name;
    }

    CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        tag.put("id", NbtUtils.createUUID(id));
        tag.putString("name", name);
        tag.put("leader", NbtUtils.createUUID(leader));
        ListTag memberList = new ListTag();
        members.forEach((uuid, memberName) -> {
            CompoundTag entry = new CompoundTag();
            entry.put("uuid", NbtUtils.createUUID(uuid));
            entry.putString("name", memberName);
            memberList.add(entry);
        });
        tag.put("members", memberList);
        ListTag inviteList = new ListTag();
        invites.forEach(uuid -> inviteList.add(NbtUtils.createUUID(uuid)));
        tag.put("invites", inviteList);
        return tag;
    }

    static Coven load(CompoundTag tag) {
        UUID leader = NbtUtils.loadUUID(tag.get("leader"));
        Coven coven = new Coven(NbtUtils.loadUUID(tag.get("id")), tag.getString("name"), leader, "");
        coven.members.clear();
        for (Tag entry : tag.getList("members", Tag.TAG_COMPOUND)) {
            CompoundTag member = (CompoundTag) entry;
            coven.members.put(NbtUtils.loadUUID(member.get("uuid")), member.getString("name"));
        }
        for (Tag entry : tag.getList("invites", Tag.TAG_INT_ARRAY)) {
            coven.invites.add(NbtUtils.loadUUID(entry));
        }
        if (!coven.members.containsKey(coven.leader) && !coven.members.isEmpty()) {
            coven.leader = coven.members.keySet().iterator().next();
        }
        return coven;
    }
}
