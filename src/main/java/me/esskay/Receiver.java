package me.esskay;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;

@Slf4j
@RequiredArgsConstructor
public class Receiver implements Runnable {

    @NonNull @Getter
    private final InetAddress address;

    @NonNull @Getter
    private final Integer port;

    @Getter @Setter
    private MulticastSocket socket = null;

    @Override
    public void run() {

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (socket != null) {
                log.info("Closing receiver socket before shutdown");
                socket.close();
            }
        }));

        try {
            socket = new MulticastSocket(port);
            socket.joinGroup(address);
        } catch(final SocketException e){
            log.error("{} is not a multicast address", address, e);
            return;
        } catch (final IOException e) {
            log.error("Failed to create receiver socket", e);
            return;
        }

        final byte[] buffer = new byte[100];

        while (true) {
            final DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                socket.receive(packet);
                log.info("<< {}", new String(buffer));
            } catch (final IOException e) {
                log.error("Problem receiving data", e);
                socket.close();
                break;
            }
        }

    }
}
