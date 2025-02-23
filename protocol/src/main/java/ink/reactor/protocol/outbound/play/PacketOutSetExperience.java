package ink.reactor.protocol.outbound.play;

import ink.reactor.api.player.connection.PacketOutbound;
import ink.reactor.protocol.outbound.OutProtocol;
import ink.reactor.util.buffer.DataSize;
import ink.reactor.util.buffer.writer.ExpectedSizeBuffer;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PacketOutSetExperience implements PacketOutbound {

    private final float experienceBar;
    private final int level;
    private final int totalExperience;

    @Override
    public byte[] write() {
        final ExpectedSizeBuffer buffer = new ExpectedSizeBuffer(DataSize.FLOAT + DataSize.varInt(level) + DataSize.varInt(totalExperience));
        buffer.writeFloat(experienceBar);
        buffer.writeVarInt(level);
        buffer.writeVarInt(totalExperience);
        return buffer.compress();
    }

    @Override
    public int getId() {
        return OutProtocol.PLAY_SET_EXPERIENCE;
    }
}
