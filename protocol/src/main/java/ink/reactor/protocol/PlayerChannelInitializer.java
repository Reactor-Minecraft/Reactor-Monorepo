package ink.reactor.protocol;

import java.util.List;

import ink.reactor.protocol.decoder.NoCompressionDecoder;
import ink.reactor.protocol.encoder.NoCompressionEncoder;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

final class PlayerChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final List<PlayerConnectionImpl> playersConnection;

    PlayerChannelInitializer(List<PlayerConnectionImpl> playersConnection) {
        this.playersConnection = playersConnection;
    }

    @Override
    protected void initChannel(final SocketChannel channel) {
        final PlayerConnectionImpl connection = new PlayerConnectionImpl(channel);
        playersConnection.add(connection);

        final ChannelConfig config = channel.config();
        config.setOption(ChannelOption.TCP_NODELAY, true);

        if (ProtocolOptions.OPTIONS.isTcpFastOpen()) {
            config.setOption(ChannelOption.TCP_FASTOPEN, ProtocolOptions.OPTIONS.getTcpFastOpenConnections());
            config.setOption(ChannelOption.TCP_FASTOPEN_CONNECT,true);
        }

        config.setOption(ChannelOption.IP_TOS, 0x18);
        config.setAllocator(ByteBufAllocator.DEFAULT);

        channel.pipeline()
            .addLast("timeout", new ReadTimeoutHandler(20))
            .addLast("decoder", new NoCompressionDecoder())
            .addLast("manager", new NetworkManager(connection))
            .addLast("encoder", new NoCompressionEncoder());
    }
}