package info.tehnut.xtones.network;

import info.tehnut.xtones.Xtones;
import info.tehnut.xtones.config.XtonesConfig;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class XtonesNetwork {
    private static final SimpleNetworkWrapper WRAPPER = new SimpleNetworkWrapper(Xtones.ID);
    private static final Logger LOGGER = LogManager.getLogger();

    private static final int XTONE_CYCLE = 0;
    private static final int CONFIG_SYNC = 1;

    private XtonesNetwork() {
    }

    public static void init() {
        WRAPPER.registerMessage(XtoneCycleHandler.INSTANCE, XtoneCycleMessage.class, XTONE_CYCLE, Side.SERVER);
        WRAPPER.registerMessage(ConfigSyncHandler.INSTANCE, ConfigSyncMessage.class, CONFIG_SYNC, Side.CLIENT);
    }

    @SideOnly(Side.CLIENT)
    public static void cycleXtone(final EntityPlayerSP player, final EnumHand hand, final MouseEvent event) {
        WRAPPER.sendToServer(new XtoneCycleMessage(player, hand, event.getDwheel()));
    }

    public static void syncConfig(final EntityPlayerMP player) {
        WRAPPER.sendTo(new ConfigSyncMessage(XtonesConfig.hasXtoneCycling()), player);
    }
}
