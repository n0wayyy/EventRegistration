import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static GuestList importGuestList(File guestListFile, File waitingListFile) throws FileNotFoundException {
        Scanner fileScanner = new Scanner(guestListFile);
        int capacity = fileScanner.nextInt();
        fileScanner.nextLine();

        ArrayList<Guest> guestArrayList= new ArrayList<Guest>();
        ArrayList<Guest> guestQueueArrayList= new ArrayList<Guest>();

        while(fileScanner.hasNextLine()){
            String[] stringArray = fileScanner.nextLine().split(",");
            Guest guest = new Guest(stringArray[0], stringArray[1], stringArray[2], stringArray[3]);
            guestArrayList.add(guest);
        }

        fileScanner = new Scanner(waitingListFile);
        while(fileScanner.hasNextLine()){
            String[] stringArray = fileScanner.nextLine().split(",");
            if(stringArray[0].equals("test"))
                break;
            Guest guest = new Guest(stringArray[0], stringArray[1], stringArray[2], stringArray[3]);
            guestQueueArrayList.add(guest);
        }

        fileScanner.close();
        GuestList guestList = new GuestList(capacity, guestArrayList, guestQueueArrayList);
        return guestList;
    }

    public static void updateFile(GuestList guestList, File guestListFile, File waitListFile) throws IOException {
        int capacity = guestList.getMaxCapacity();
        ArrayList<Guest> guestArrayList = guestList.getGuestArrayList();
        ArrayList<Guest> guestQueueArrayList = guestList.getGuestQueueArrayList();

        String fileContent = "";
        fileContent += capacity + "\n";


        for(Guest guest : guestArrayList)
            fileContent += guest.getFirstName() + "," + guest.getLastName() + "," + guest.getEMail() + "," +
                                guest.getPhoneNumber() +"\n";

        FileWriter fileWriter = new FileWriter(guestListFile);
        fileWriter.write(fileContent);
        fileWriter.flush();


        fileContent = "";

        for(Guest guest : guestQueueArrayList)
            fileContent += guest.getFirstName() + "," + guest.getLastName() + "," + guest.getEMail() + "," +
                guest.getPhoneNumber() +"\n";

        fileWriter = new FileWriter(waitListFile);
        fileWriter.write(fileContent);
        fileWriter.flush();
        fileWriter.close();
        return;

    }


    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);

        File guestListFile = new File("src/GuestList.txt");
        File waitListFile = new File("src/WaitingList.txt");
        GuestList guestList = importGuestList(guestListFile, waitListFile);

        System.out.println("Type help for list of commands.");
        while(true){
            String command = scan.next();
            if(command.equals("quit"))
                return;

            switch (command.toLowerCase()){
                case "help":
                    guestList.help();
                    break;
                case "add":
                    guestList.addGuest();
                    updateFile(guestList,guestListFile,waitListFile);
                    break;
                case "check":
                    guestList.check();
                    break;
                case "remove":
                    guestList.remove();
                    updateFile(guestList,guestListFile,waitListFile);
                    break;
                case "update":
                    guestList.update();
                    updateFile(guestList,guestListFile,waitListFile);
                    break;
                case "guests":
                    guestList.displayGuestList();
                    break;
                case "waitlist":
                    guestList.displayWaitingList();
                    break;
                case "available":
                    guestList.numberOfAvailable();
                    break;
                case "guests_no":
                    guestList.numberOfGuests();
                    break;
                case "waitlist_no":
                    guestList.numberOfWaitingGuests();
                    break;
                case "subscribe_no":
                    guestList.numberOfTotalGuests();
                    break;
                case "search":
                    guestList.search();
                    break;
                case "quit":
                    scan.close();
                    return;
                default:
                    System.out.println("Command not valid. Type help for the list of commands.");
                    continue;
            }
        }
    }
}
