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

@Slf4j
@RequiredArgsConstructor
public class Sender implements Runnable {

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
                log.info("Closing sender socket before shutdown");
                socket.close();
            }
        }));

        try {
            socket = new MulticastSocket();
        } catch (final IOException e) {
            log.error("Failed to create sender socket", e);
            return;
        }

        while (true) {
            final String message = "Current time: " + System.currentTimeMillis();
            log.info(">> {}", message);
            final DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(), address, port);
            try {
                socket.send(packet);
                Thread.sleep(1000);
            } catch (final IOException | InterruptedException e) {
                log.error("Problem sending data - {}", message, e);
                socket.close();
                break;
            }
        }
    }
}
