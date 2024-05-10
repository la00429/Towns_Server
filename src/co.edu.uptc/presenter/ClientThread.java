package co.edu.uptc.presenter;

import co.edu.uptc.model.Department;
import co.edu.uptc.model.Inhabitant;
import co.edu.uptc.net.Connetion;
import co.edu.uptc.net.Request;
import co.edu.uptc.net.Responsive;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.Socket;

public class ClientThread extends Thread {
    private Connetion connetion;
    private Department department;


    public ClientThread(Socket socket, Department department) throws IOException {
        this.connetion = new Connetion(socket);
        this.department = department;

    }

    @Override
    public void run() {
        super.run();
        try {
            menuPrincipal();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void menuPrincipal() throws IOException {
        establishConnection();
        Request request;
        do {
            request = new Gson().fromJson(connetion.receive(), Request.class);
            switch (request.getOption()) {
                case "1":
                    addInhabitant(request.getNameTown(), request.getNameInhabitant(), request.getIdInhabitant());
                    break;
                case "2":
                    showInhabitants(request.getNameTown());
                    break;
                case "3":
                    calculateMostPopulous();
                    break;
                case "4":
                    calculateMinorPopulous();
                    break;
                case "6":
                    closeConnection();
                    break;
            }


        } while (!request.getOption().equals("0"));


    }

    private void establishConnection() throws IOException {
        connetion.connect();
        Gson gson = new Gson();
        connetion.send(gson.toJson(new Responsive("Connection established")));
        Responsive message = gson.fromJson(connetion.receive(), Responsive.class);
        System.err.println(message.getMessage());
    }


    private void addInhabitant(String town, String name, String id) throws IOException {
        boolean verification = department.addInhabitantInTown(town, new Inhabitant(id, name.toUpperCase().replace(" ", "")));
        if (verification) {
            connetion.send(new Gson().toJson(new Responsive(true)));
        } else {
            connetion.send(new Gson().toJson(new Responsive(false)));
            verificationTown(town);
        }
    }

    private void verificationTown(String town) throws IOException {
        if (department.searchTownship(town) == null) {
            connetion.send(new Gson().toJson(new Responsive(true)));
        } else {
            connetion.send(new Gson().toJson(new Responsive(false)));
        }
    }

    private void showInhabitants(String town) throws IOException {
        if (department.searchTownship(town) == null) {
            connetion.send(new Gson().toJson(new Responsive(true)));
        } else {
            connetion.send(new Gson().toJson(new Responsive(department.searchTownship(town).getName(), department.searchTownship(town).getInhabitants(), false)));
        }

    }

    private void calculateMostPopulous() throws IOException {
        connetion.send(new Gson().toJson(new Responsive(department.calculateMostPopulous())));
    }

    private void calculateMinorPopulous() throws IOException {
        connetion.send(new Gson().toJson(new Responsive(department.calculateMinorPopulous())));
    }

    private void closeConnection() throws IOException {
        connetion.send(new Gson().toJson(new Responsive("Close connection")));
        connetion.disconnect();
        throw new IOException("Connection closed");
    }
}

