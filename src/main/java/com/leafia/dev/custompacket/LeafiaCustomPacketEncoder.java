package com.leafia.dev.custompacket;

import com.leafia.dev.optimization.bitbyte.LeafiaBuf;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public interface LeafiaCustomPacketEncoder {
	void encode(LeafiaBuf buf);
	@Nullable
	Consumer<MessageContext> decode(LeafiaBuf buf);
}
