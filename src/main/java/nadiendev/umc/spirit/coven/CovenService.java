package nadiendev.umc.spirit.coven;

import java.util.Optional;
import java.util.UUID;
import nadiendev.umc.spirit.config.SpiritConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public final class CovenService {

    private CovenService() {
    }

    private static boolean enabled(ServerPlayer player) {
        if (SpiritConfig.ENABLED.get() && SpiritConfig.COVENS_ENABLED.get()) {
            return true;
        }
        player.sendSystemMessage(Component.translatable("message.umc.coven.disabled"));
        return false;
    }

    private static CovenData data(ServerPlayer player) {
        return CovenData.get(player.server);
    }

    public static Optional<Coven> covenOf(ServerPlayer player) {
        return data(player).ofPlayer(player.getUUID());
    }

    public static boolean create(ServerPlayer player, String rawName) {
        if (!enabled(player)) {
            return false;
        }
        CovenData data = data(player);
        String name = rawName.trim();
        if (name.length() < 3 || name.length() > SpiritConfig.COVEN_NAME_MAX_LENGTH.get()) {
            player.sendSystemMessage(Component.translatable("message.umc.coven.bad_name", 3, SpiritConfig.COVEN_NAME_MAX_LENGTH.get()));
            return false;
        }
        if (data.ofPlayer(player.getUUID()).isPresent()) {
            player.sendSystemMessage(Component.translatable("message.umc.coven.already_member"));
            return false;
        }
        if (data.byName(name).isPresent()) {
            player.sendSystemMessage(Component.translatable("message.umc.coven.name_taken", name));
            return false;
        }
        Coven coven = data.create(name, player.getUUID(), player.getGameProfile().getName());
        player.sendSystemMessage(Component.translatable("message.umc.coven.created", coven.name()).withStyle(ChatFormatting.LIGHT_PURPLE));
        return true;
    }

    public static boolean invite(ServerPlayer leader, ServerPlayer target) {
        if (!enabled(leader)) {
            return false;
        }
        CovenData data = data(leader);
        Optional<Coven> own = data.ofPlayer(leader.getUUID());
        if (own.isEmpty()) {
            leader.sendSystemMessage(Component.translatable("message.umc.coven.not_member"));
            return false;
        }
        Coven coven = own.get();
        if (!coven.leader().equals(leader.getUUID())) {
            leader.sendSystemMessage(Component.translatable("message.umc.coven.not_leader"));
            return false;
        }
        if (coven.isMember(target.getUUID())) {
            leader.sendSystemMessage(Component.translatable("message.umc.coven.target_member", target.getDisplayName()));
            return false;
        }
        if (data.ofPlayer(target.getUUID()).isPresent()) {
            leader.sendSystemMessage(Component.translatable("message.umc.coven.target_busy", target.getDisplayName()));
            return false;
        }
        data.invite(coven, target.getUUID());
        leader.sendSystemMessage(Component.translatable("message.umc.coven.invited", target.getDisplayName()));
        target.sendSystemMessage(Component.translatable("message.umc.coven.invite_received", coven.name(), leader.getDisplayName()).withStyle(ChatFormatting.LIGHT_PURPLE));
        return true;
    }

    public static boolean join(ServerPlayer player, String rawName, boolean ignoreInvite) {
        if (!enabled(player)) {
            return false;
        }
        CovenData data = data(player);
        if (data.ofPlayer(player.getUUID()).isPresent()) {
            player.sendSystemMessage(Component.translatable("message.umc.coven.already_member"));
            return false;
        }
        Optional<Coven> found = data.byName(rawName.trim());
        if (found.isEmpty()) {
            player.sendSystemMessage(Component.translatable("message.umc.coven.unknown", rawName.trim()));
            return false;
        }
        return joinCoven(player, data, found.get(), ignoreInvite);
    }

    public static boolean joinCoven(ServerPlayer player, CovenData data, Coven coven, boolean ignoreInvite) {
        if (!ignoreInvite && !SpiritConfig.COVEN_OPEN_JOIN.get() && !coven.isInvited(player.getUUID())) {
            player.sendSystemMessage(Component.translatable("message.umc.coven.not_invited", coven.name()));
            return false;
        }
        if (coven.members().size() >= SpiritConfig.COVEN_MAX_MEMBERS.get()) {
            player.sendSystemMessage(Component.translatable("message.umc.coven.full", coven.name()));
            return false;
        }
        data.addMember(coven, player.getUUID(), player.getGameProfile().getName());
        broadcast(player.server, coven, Component.translatable("message.umc.coven.joined", player.getDisplayName(), coven.name()).withStyle(ChatFormatting.LIGHT_PURPLE));
        return true;
    }

    public static boolean leave(ServerPlayer player) {
        if (!enabled(player)) {
            return false;
        }
        CovenData data = data(player);
        Optional<Coven> own = data.ofPlayer(player.getUUID());
        if (own.isEmpty()) {
            player.sendSystemMessage(Component.translatable("message.umc.coven.not_member"));
            return false;
        }
        Coven coven = own.get();
        data.removeMember(coven, player.getUUID());
        player.sendSystemMessage(Component.translatable("message.umc.coven.left", coven.name()));
        if (!coven.members().isEmpty()) {
            broadcast(player.server, coven, Component.translatable("message.umc.coven.member_left", player.getDisplayName()));
        }
        return true;
    }

    public static boolean kick(ServerPlayer leader, ServerPlayer target) {
        if (!enabled(leader)) {
            return false;
        }
        CovenData data = data(leader);
        Optional<Coven> own = data.ofPlayer(leader.getUUID());
        if (own.isEmpty() || !own.get().leader().equals(leader.getUUID())) {
            leader.sendSystemMessage(Component.translatable("message.umc.coven.not_leader"));
            return false;
        }
        Coven coven = own.get();
        if (!coven.isMember(target.getUUID()) || target.getUUID().equals(leader.getUUID())) {
            leader.sendSystemMessage(Component.translatable("message.umc.coven.target_not_member", target.getDisplayName()));
            return false;
        }
        data.removeMember(coven, target.getUUID());
        target.sendSystemMessage(Component.translatable("message.umc.coven.kicked", coven.name()));
        broadcast(leader.server, coven, Component.translatable("message.umc.coven.member_kicked", target.getDisplayName()));
        return true;
    }

    public static boolean disband(ServerPlayer leader) {
        if (!enabled(leader)) {
            return false;
        }
        CovenData data = data(leader);
        Optional<Coven> own = data.ofPlayer(leader.getUUID());
        if (own.isEmpty() || !own.get().leader().equals(leader.getUUID())) {
            leader.sendSystemMessage(Component.translatable("message.umc.coven.not_leader"));
            return false;
        }
        Coven coven = own.get();
        broadcast(leader.server, coven, Component.translatable("message.umc.coven.disbanded", coven.name()));
        data.disband(coven);
        return true;
    }

    public static void info(ServerPlayer viewer, Coven coven) {
        viewer.sendSystemMessage(Component.translatable("message.umc.coven.info_title", coven.name(), coven.members().size(), SpiritConfig.COVEN_MAX_MEMBERS.get()).withStyle(ChatFormatting.LIGHT_PURPLE));
        MutableComponent list = Component.empty();
        boolean first = true;
        for (UUID member : coven.memberIds()) {
            if (!first) {
                list.append(Component.literal(", "));
            }
            first = false;
            MutableComponent name = Component.literal(coven.memberName(member));
            if (member.equals(coven.leader())) {
                name.withStyle(ChatFormatting.GOLD);
            }
            if (viewer.server.getPlayerList().getPlayer(member) == null) {
                name.withStyle(ChatFormatting.GRAY);
            }
            list.append(name);
        }
        viewer.sendSystemMessage(Component.translatable("message.umc.coven.info_members", list));
    }

    public static void broadcast(MinecraftServer server, Coven coven, Component message) {
        for (UUID member : coven.memberIds()) {
            ServerPlayer online = server.getPlayerList().getPlayer(member);
            if (online != null) {
                online.sendSystemMessage(message);
            }
        }
    }
}
