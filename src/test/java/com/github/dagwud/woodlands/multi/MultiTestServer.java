package com.github.dagwud.woodlands.multi;

import com.github.dagwud.woodlands.SimulatorSender;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.domain.EEvent;
import com.github.dagwud.woodlands.game.domain.ELocation;
import com.github.dagwud.woodlands.game.messaging.MessagingFactory;
import com.github.dagwud.woodlands.web.TelegramServlet;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MultiTestServer {
    private static final int portNumber = 40_000;

    private static final Map<Integer, ServeUserThread> threads = new HashMap<>();

    private static int currentUserId = -1;


    public static void main(String[] args) throws IOException {
        TelegramServlet telegramServlet = new TelegramServlet();

        Settings.DEVELOPER_MODE = true;

        MessagingFactory.create(new SocketSender(threads));
        // various things don't happen without a persisted state to get at without erroring
        // may behave weird if your persistence works, then comment it out
        ELocation.scheduleRooms();
        EEvent.subscribeToStandardEvents();

        ServerSocket serverSocket = new ServerSocket(portNumber);
        System.out.println("Up and waiting for connections on " + portNumber);
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Accepted connection.");
            ServeUserThread serveUserThread = new ServeUserThread(clientSocket, currentUserId, telegramServlet);
            threads.put(currentUserId, serveUserThread);
            new Thread(serveUserThread).start();
            currentUserId--;
        }
    }
}
