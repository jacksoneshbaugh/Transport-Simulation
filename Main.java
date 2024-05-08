import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Entry point for the Transport Simulation.
 * 
 * @author Jackson Eshbaugh
 * @version 05/04/2024
 */
public class Main {

    /**
     * Entry-point for the simulation. Takes a command line argument to determine wether to randomize the
     * config before starting the simulation.
     * 
     * @param args if {@code args[0] == "random"}, then the simulation's configuration file will be randomized
     * before starting.
     */
    public static void main(String[] args) {

        if(args.length > 0 && args[0].equals("random")) {
            int maxX = 10 + (int)(Math.random() * ((30 - 10) + 1));
            int maxY = 10 + (int)(Math.random() * ((30 - 10) + 1));
            int minShipments = 2 + (int)(Math.random() * ((6 - 2) + 1));
            int maxShipments = 8 + (int)(Math.random() * ((15 - 8) + 1));
            int hours = 5 + (int)(Math.random() * ((15 - 5) + 1));
            int numTrucks = 3 + (int)(Math.random() * ((10 - 3) + 1));
            int numWarehouses = 4 + (int)(Math.random() * ((12 - 4) + 1));

            try (BufferedWriter w = new BufferedWriter(new FileWriter("simulation.properties"))){
                w.write("hours=" + hours);
                w.newLine();
                w.write("numTrucks=" + numTrucks);
                w.newLine();
                w.write("numWarehouses=" + numWarehouses);
                w.newLine();
                w.write("minShipments=" + minShipments);
                w.newLine();
                w.write("maxShipments=" + maxShipments);
                w.newLine();
                w.write("maxX=" + maxX);
                w.newLine();
                w.write("maxY=" + maxY);
                w.newLine();
            } catch (IOException e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }

        File config = new File("simulation.properties");
        if(!config.isFile()) {
            try (BufferedWriter w = new BufferedWriter(new FileWriter(config))){
                config.createNewFile();

                w.write("hours=" + 8);
                w.newLine();
                w.write("numTrucks=" + 4);
                w.newLine();
                w.write("numWarehouses=" + 3);
                w.newLine();
                w.write("minShipments=" + 3);
                w.newLine();
                w.write("maxShipments=" + 10);
                w.newLine();
                w.write("maxX=" + 10);
                w.newLine();
                w.write("maxY=" + 10);
                w.newLine();
            }
            catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        
        int maxX = -1;
        int maxY = -1;
        int minShipments = -1;
        int maxShipments = -1;
        int hours = -1;
        int numTrucks = -1;
        int numWarehouses = -1;

        try {
            Scanner s = new Scanner(config);

            while(s.hasNextLine()) {
                Scanner line = new Scanner(s.nextLine()).useDelimiter("\\s*=\\s*");
                String param = line.next();
                String value = line.next();

                if(param.equals("maxX")) {
                    maxX = Integer.parseInt(value);
                } else if(param.equals("maxY")) {
                    maxY = Integer.parseInt(value);
                } else if(param.equals("minShipments")) {
                    minShipments = Integer.parseInt(value);
                } else if(param.equals("maxShipments")) {
                    maxShipments = Integer.parseInt(value);
                } else if(param.equals("hours")) {
                    hours = Integer.parseInt(value);
                } else if(param.equals("numTrucks")) {
                    numTrucks = Integer.parseInt(value);
                } else if(param.equals("numWarehouses")) {
                    numWarehouses = Integer.parseInt(value);
                }

                line.close();
            }

            if(maxX == -1 || maxY== -1 || minShipments == -1 || maxShipments == -1 || numTrucks == -1 || numWarehouses == -1 || hours == -1) {
                System.err.println("Malformed configuration file. Please fix the file, or use the \"random\" argument to generate a new file.");
                System.exit(-1);
            }

            s.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        
        References.LOG_FILE_NAME = "simulation-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + ".log";
        
        TransportSimulation sim = new TransportSimulation(maxX, maxY, numTrucks, numWarehouses, minShipments, maxShipments);
        sim.run(hours);
        
        boolean flag = false;
        
        while(!flag) {
            Scanner s = new Scanner(System.in);
            System.out.println("Would you like to run the simulation for more hours, or are you finished? (Enter a number of hours, or anything else to quit):");
            
            if(s.hasNextInt()) {
                sim.run(s.nextInt());
            } else {
                System.out.println("Goodbye!");
                flag = true;
            }
        }
    }

}