package co.edu.uptc.presenter;

import co.edu.uptc.model.Department;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Presenter {
    private final int PUERTO = 1234;
    private ServerSocket serverSocket;
    private Department department;

    public Presenter() throws IOException {
        this.serverSocket = new ServerSocket(PUERTO);
        this.department = new Department();
    }

    public void start() throws IOException {
        while (true) {
            Socket socket = serverSocket.accept();
            ClientThread clientThread = new ClientThread(socket, department);
            clientThread.start();
        }
    }
}
