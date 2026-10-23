package nadiendev.umc.creatures.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.util.Collection;
import java.util.List;
import nadiendev.umc.UltimateModCompats;
import nadiendev.umc.creatures.config.CreaturesConfig;
import nadiendev.umc.creatures.form.CreatureForms;
import nadiendev.umc.creatures.form.CreatureKind;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = UltimateModCompats.MODID)
public final class CreaturesCommands {

    private static final List<String> FORMS = List.of("vampire", "werewolf", "hunter", "human");

    private CreaturesCommands() {
    }

    @SubscribeEvent
    public static void register(RegisterCommandsEvent event) {
        LiteralArgumentBuilder<CommandSourceStack> form = Commands.literal("form").requires(source -> source.hasPermission(2));
        for (String name : FORMS) {
            form.then(Commands.literal(name)
                    .executes(context -> apply(context.getSource(), List.of(context.getSource().getPlayerOrException()), name))
                    .then(Commands.argument("targets", EntityArgument.players())
                            .executes(context -> apply(context.getSource(), EntityArgument.getPlayers(context, "targets"), name))));
        }
        event.getDispatcher().register(Commands.literal("umc").then(form));
    }

    private static int apply(CommandSourceStack source, Collection<ServerPlayer> targets, String form) {
        int changed = 0;
        for (ServerPlayer player : targets) {
            if (set(player, form)) {
                changed++;
                source.sendSuccess(() -> Component.translatable("commands.umc.form.success", player.getDisplayName(), Component.translatable("form.umc." + form)), true);
            } else {
                source.sendFailure(Component.translatable("commands.umc.form.disabled", Component.translatable("form.umc." + form)));
            }
        }
        return changed;
    }

    private static boolean set(ServerPlayer player, String form) {
        CreatureForms.cure(player);
        CreatureForms.setHunter(player, false);
        switch (form) {
            case "human" -> {
                return true;
            }
            case "hunter" -> {
                if (!CreaturesConfig.active() || !CreaturesConfig.HUNTER_ENABLED.get()) {
                    return false;
                }
                CreatureForms.setHunter(player, true);
                player.displayClientMessage(Component.translatable("message.umc.hunter.become"), false);
                return true;
            }
            default -> {
                CreatureKind kind = CreatureKind.byName(form);
                if (!CreatureForms.isKindEnabled(kind)) {
                    return false;
                }
                CreatureForms.set(player, CreatureForms.get(player).withKind(kind));
                CreatureForms.transform(player, false);
                return true;
            }
        }
    }
}
