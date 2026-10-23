package nadiendev.umc.spirit.coven;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Optional;
import nadiendev.umc.UltimateModCompats;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = UltimateModCompats.MODID)
public final class CovenCommands {

    private CovenCommands() {
    }

    @SubscribeEvent
    public static void register(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(Commands.literal("umc")
                .then(Commands.literal("coven")
                        .then(Commands.literal("create")
                                .then(Commands.argument("name", StringArgumentType.greedyString())
                                        .executes(context -> run(context, player -> CovenService.create(player, StringArgumentType.getString(context, "name"))))))
                        .then(Commands.literal("invite")
                                .then(Commands.argument("player", EntityArgument.player())
                                        .executes(context -> run(context, player -> CovenService.invite(player, EntityArgument.getPlayer(context, "player"))))))
                        .then(Commands.literal("join")
                                .then(Commands.argument("name", StringArgumentType.greedyString())
                                        .executes(context -> run(context, player -> CovenService.join(player, StringArgumentType.getString(context, "name"), false)))))
                        .then(Commands.literal("leave")
                                .executes(context -> run(context, CovenService::leave)))
                        .then(Commands.literal("kick")
                                .then(Commands.argument("player", EntityArgument.player())
                                        .executes(context -> run(context, player -> CovenService.kick(player, EntityArgument.getPlayer(context, "player"))))))
                        .then(Commands.literal("disband")
                                .executes(context -> run(context, CovenService::disband)))
                        .then(Commands.literal("info")
                                .executes(context -> run(context, player -> info(player, null)))
                                .then(Commands.argument("name", StringArgumentType.greedyString())
                                        .executes(context -> run(context, player -> info(player, StringArgumentType.getString(context, "name"))))))));
    }

    private static boolean info(ServerPlayer player, String name) {
        CovenData data = CovenData.get(player.server);
        Optional<Coven> coven = name == null ? data.ofPlayer(player.getUUID()) : data.byName(name.trim());
        if (coven.isEmpty()) {
            player.sendSystemMessage(Component.translatable(name == null ? "message.umc.coven.not_member" : "message.umc.coven.unknown", name == null ? "" : name.trim()));
            return false;
        }
        CovenService.info(player, coven.get());
        return true;
    }

    private static int run(CommandContext<CommandSourceStack> context, PlayerAction action) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        return action.run(player) ? 1 : 0;
    }

    @FunctionalInterface
    private interface PlayerAction {

        boolean run(ServerPlayer player) throws CommandSyntaxException;
    }
}
