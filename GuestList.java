import java.util.ArrayList;
import java.util.Scanner;

public class GuestList extends ArrayList {
    private Scanner sc = new Scanner(System.in);

    private ArrayList<Guest> guestArrayList;
    private ArrayList<Guest> guestQueueArrayList;
    private int maxCapacity;


    public GuestList(int maxCapacity){
        this.maxCapacity = maxCapacity;
        this.guestArrayList = new ArrayList<Guest>();
        this.guestQueueArrayList = new ArrayList<Guest>();
    }

    public GuestList(int maxCapacity, ArrayList guestArrayList, ArrayList guestQueueArrayList){
        this.maxCapacity = maxCapacity;
        this.guestArrayList = guestArrayList;
        this.guestQueueArrayList = guestQueueArrayList;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public ArrayList<Guest> getGuestArrayList() {
        return guestArrayList;
    }

    public ArrayList<Guest> getGuestQueueArrayList() {
        return guestQueueArrayList;
    }

    /*
    The following method displays the list of all commands available for the application
    COMMAND: help
     */

    public void help(){
        System.out.println("Available commands:");
        System.out.println("help\t\t\t- Display command list.");
        System.out.println("add\t\t\t\t- Add a new guest.");
        System.out.println("check\t\t\t- Check if a person is registered to the event.");
        System.out.println("remove\t\t\t- Remove guest from list.");
        System.out.println("update\t\t\t- Update guest details.");
        System.out.println("guests\t\t\t- List of guests participating at the event.");
        System.out.println("waitlist\t\t- List of guests on the waiting list.");
        System.out.println("available\t\t- Number of available spots for the event.");
        System.out.println("guests_no\t\t- Number of participants at the event.");
        System.out.println("waitilist_no\t- Number of people on the waiting list.");
        System.out.println("subscribe_no\t- Number of total guests registered at the event.");
        System.out.println("search\t\t\t- Search for a person.");
        System.out.println("quit\t\t\t- Exit the application.");
    }


    /*
    USE:    The following method creates a new guest and adds the guest to the list if the guest is not already registered.
    COMMAND:add
    RETURNS:-1 if already registered
             0 if guest has been added in the event list
             #orderNumber if the guest has been placed in the waiting list
     */
    public int addGuest(){
        Guest newGuest = createGuest();
        if(contains(guestArrayList,newGuest) || contains(guestQueueArrayList,newGuest)) {
            System.out.println("ERROR: Guest already registered.");
            return -1;
        }

        if(guestArrayList.size() < maxCapacity) {
            guestArrayList.add(newGuest);
            System.out.println("Congratulations! You have successfully registered for the event!");
            return 0;
        }

        guestQueueArrayList.add(newGuest);
        System.out.println("You have been added to the waiting list. Your current queue number is: " + (guestQueueArrayList.size() - 1)
                + "You will be notified as soon as a spot becomes available.");
        return guestQueueArrayList.size() - 1;
    }


    /*
    USE:    The following method checks if a guest is already registered.
    COMMAND:check
    NOTE:   TAKES INPUT FROM KEYBOARD BASED ON SEARCH CRITERIA
    RETURNS:true if guest is registered.
            false if guest is not registered
     */

    public boolean check(){
        int option = getSelectionOption();
        boolean found  = checkRegistered(option);

        if(found){
            System.out.println("Guest is registered.");
            return true;
        }

        System.out.println("Guest is not registered.");
        return false;
    }

    /*
    USE:    The following method checks if the guest exists is registered based on the search criteria and removes the guest if found.
    COMMAND:remove
    RETURNS:true - if the guest has been removed
            false- if the guest has not been removed(in which case the guest wasn't found)

     */

    public boolean remove() {
        int option = getSelectionOption();
        Guest targetGuest = getGuestByCriteria(option);

        return findAndRemoveGuest(targetGuest);
    }

    /*
    USE:    The following method searches for a user depending on the search criteria.
            If the user is found, a new message will be displayed asking the user to select the field which will be updated.
            After the selected field is selected, the user will be asked to introduce the new details of the field.
            Field will be updated accordingly.
    COMMAND:update

     */

    public void update() {
        int searchOption = getSelectionOption();
        Guest targetGuest = getGuestByCriteria(searchOption);

        if (!contains(guestArrayList, targetGuest) && !contains(guestQueueArrayList, targetGuest)) {
            System.out.println("Guest not registered.");
            return;
        }

        if(contains(guestArrayList,targetGuest))
            targetGuest = findGuestInList(guestArrayList,targetGuest);
        else
            targetGuest = findGuestInList(guestQueueArrayList,targetGuest);

        int updateOption = getUpdateSelection();

        switch (updateOption){
            case 1:
                System.out.println("Please introduce the new First Name:");
                String newFirstName = sc.next();

                while(!verifyName(newFirstName)){
                    System.out.println("Please try again!");
                    System.out.println("Please introduce the new First Name:");
                    newFirstName = sc.next();
                }
                targetGuest.setFirstName(newFirstName);
                System.out.println("First Name changed successfully.");
                break;
            case 2:
                System.out.println("Please introduce the new Last Name:");
                String newLastName = sc.next();

                while(!verifyName(newLastName)){
                    System.out.println("Please try again!");
                    System.out.println("Please introduce the new Last Name:");
                    newLastName = sc.next();
                }
                targetGuest.setLastName(newLastName);
                System.out.println("Last Name changed successfully.");
                break;
            case 3:
                System.out.println("Please introduce the new eMail:");
                String newEMail = sc.next();

                while(!verifyEmail(newEMail)){
                    System.out.println("Please try again!");
                    System.out.println("Please introduce the new eMail:");
                    newEMail = sc.next();
                }
                targetGuest.setEMail(newEMail);
                System.out.println("eMail changed successfully.");
                break;
            case 4:
                System.out.println("Please introduce the new phone number:");
                String newPhoneNumber = sc.next();

                while(!verifyPhoneNumber(newPhoneNumber)){
                    System.out.println("Please try again!");
                    System.out.println("Please introduce the new phone number:");
                    newPhoneNumber = sc.next();
                }
                targetGuest.setPhoneNumber(newPhoneNumber);
                System.out.println("Last Name changed successfully.");
                break;
            default:
                System.out.println("Selected option not available.");
                return;
        }
        return;
    }

    /*
    USE:    The following method displays a list of all the guests who are participating to the event.
    COMMAND:guests
     */

    public void displayGuestList(){
        if(guestArrayList.size() == 0){
            System.out.println("There are no guests registered.");
            return;
        }
        for(Guest guest : guestArrayList){
            System.out.println("Guest number: " + (guestArrayList.indexOf(guest) + 1));
            displayGuestDetails(guest);
        }
    }

    /*
    USE:    The following method displays a list all the guests currently placed in the waiting list.
    COMMAND:waitlist
     */

    public void displayWaitingList(){
        if(guestQueueArrayList.size() == 0){
            System.out.println("There are no guests in the waiting list.");
            return;
        }
        for(Guest guest : guestQueueArrayList){
            System.out.println("Guest waiting number: " + (guestQueueArrayList.indexOf(guest) + 1));
            displayGuestDetails(guest);

        }
    }

    /*
    USE:    The following method displays the current number of available spots for the event.
    COMMAND:available
     */

    public void numberOfAvailable(){
        int availableNo = maxCapacity - guestArrayList.size();

        switch (availableNo){
            case 0:
                System.out.println("There are no available spots.");
                break;
            case 1:
                System.out.println("There is 1 available spot.");
                break;
            default:
                System.out.println("There are " + availableNo + " spots available.");
                break;
        }
        return;
    }

    /*
    USE:    The following method displays the number of guests participating at the event.
    COMMAND:guests_no
     */

    public void numberOfGuests(){
        int guestsNo = guestArrayList.size();

        switch (guestsNo){
            case 0:
                System.out.println("There are no guests participating at the event.");
                break;
            case 1:
                System.out.println("There is 1 guest participating at the event.");
                break;
            default:
                System.out.println("There are " + guestsNo + " guests participating at the event.");
        }
        return;
    }

    /*
    USE:    The following method displays the number of guests currently registered on the waiting list.
    COMMAND:waitlist_no
     */

    public void numberOfWaitingGuests(){
        int guestsNo = guestQueueArrayList.size();

        switch (guestsNo){
            case 0:
                System.out.println("There are no guests on the waiting list.");
                break;
            case 1:
                System.out.println("There is 1 guest registered on the waiting list.");
                break;
            default:
                System.out.println("There are " + guestsNo + " guests registered on the waiting list.");
        }
        return;
    }

    /*
    USE:    The following method displays the total number of guests registered for the event, disregarding whether
            they are in the guest list or waiting list
    COMMAND:subscribe_no
     */

    public void numberOfTotalGuests(){
        int guestsNo = guestArrayList.size();

        switch (guestsNo){
            case 0:
                System.out.println("There are no guests registered for the event.");
                break;
            case 1:
                System.out.println("There is 1 guest registered for the event.");
                break;
            default:
                System.out.println("There are " + guestsNo + " guests registered for the event.");
        }
        return;
    }

    /*
    USE:    The following method will ask the user to input a string. If any guest contains the given string,
            the guest information will be displayed along with the specific list in which the guest is registered
            and his position in the queue if the guest is found in the waiting list.
    COMMAND:search
     */
    public void search(){
        System.out.println("Please introduce the name of the guest");
        String searchInput = sc.next();
        searchInput.toLowerCase();
        boolean found = false;

        for(Guest guest : guestArrayList){
            if(guest.findString(searchInput)){
                System.out.println("Guest participating to event.");
                System.out.println("Guest details:");
                displayGuestDetails(guest);
                found = true;
            }
        }

        for(Guest guest : guestQueueArrayList){
            if(guest.findString(searchInput)){
                System.out.println("Guest currently in waiting list. Guest waiting number: " + guestQueueArrayList.indexOf(guest) + 1);
                System.out.println("Guest details:");
                displayGuestDetails(guest);
                found = true;
            }
        }

        if(found == false)
            System.out.println("No guests with matching details found.");
    }

    //This method asks the user for a new Guest information and returns a new Guest object with the information given.
    private Guest createGuest(){
        String firstName = "";
        String lastName = "";
        String EMail = "";
        String phoneNumber = "";

        boolean condition = false;

        System.out.println("Please introduce the following information:");
        while(!condition) {
            System.out.print("First name: ");
            firstName = sc.next();
            System.out.println();

            condition = verifyName(firstName);
            if(!condition)
                System.out.println("Please try again!");
        }

        condition = false;
        while(!condition) {
            System.out.print("Last name: ");
            lastName = sc.next();
            System.out.println();

            condition = verifyName(lastName);
            if(!condition)
                System.out.println("Please try again!");
        }

        condition = false;
        while (!condition) {
            System.out.print("EMail: ");
            EMail = sc.next();
            System.out.println();

            condition = verifyEmail(EMail);
            if(!condition)
                System.out.println("Please try again!");
        }

        condition = false;

        while(!condition) {
            System.out.print("Phone number: ");
            phoneNumber = sc.next();
            System.out.println();

            condition = verifyPhoneNumber(phoneNumber);
            if(!condition)
                System.out.println("Please try again!");
        }
        Guest newGuest = new Guest(firstName,lastName,EMail,phoneNumber);
        return newGuest;
    }

    //The following method receives a list of Guests and a Guest(with incomplete details). If the Guest is found in the
    //list of Guests(based on name/EMail/phoneNumber), the method will return true. Otherwise, the method will return false
    private boolean contains(ArrayList<Guest> arrayList, Guest guest){
        if(findByName(arrayList, guest.getFirstName(), guest.getLastName()) ||
                findByEMail(arrayList,guest.getEMail()) ||
                findByPhoneNumber(arrayList, guest.getPhoneNumber()))
            return true;

        return false;
    }

    //This method receives the option selected previously for the searching criteria and continues to ask the user
    //for further details regarding the search. If the inputs given by the user match any guest in any of the two lists,
    //the method will return true. Otherwise, the method will return false.

    private boolean checkRegistered(int option){

        switch (option){
            case 1:
                System.out.print("First name: ");
                String GuestFirstName = sc.next();
                System.out.print("Last name: ");
                String GuestLastName = sc.next();
                System.out.println();
                if(findByName(guestArrayList,GuestFirstName, GuestLastName) ||
                        findByName(guestQueueArrayList,GuestFirstName,GuestLastName))
                    return true;
                break;

            case 2:
                System.out.print("Email: ");
                String email = sc.next();
                System.out.println();
                if(findByEMail(guestArrayList,email) || findByEMail(guestQueueArrayList,email))
                    return true;
                break;

            case 3:
                System.out.print("Phone number: ");
                String phoneNo = sc.next();
                System.out.println();
                if(findByPhoneNumber(guestArrayList, phoneNo) || findByPhoneNumber(guestQueueArrayList,phoneNo))
                    return true;
                break;

            default:
                System.out.println("Selected option not available.");
                break;
        }

        return false;
    }

    //This method receives a list of Guests and two strings. The method will return true of false whether the strings
    //match the firstName and lastName properties of any Guest in the list of Guests.
    private static boolean findByName(ArrayList<Guest> arrayList, String firstName, String lastName){
        if(arrayList.isEmpty())
            return false;

        for(Guest guest : arrayList)
            if (guest.getFirstName().equals(firstName) &&
                    guest.getLastName().equals(lastName))
                return true;
        return false;
    }

    //This method receives a list of Guests and one string. The method will return true of false whether the string
    //matches the EMail property of any Guest in the list of Guests.
    private static boolean findByEMail(ArrayList<Guest> arrayList, String EMail){
        if(arrayList.isEmpty())
            return false;

        for(Guest guest : arrayList)
            if (guest.getEMail().equals(EMail)) return true;

        return false;
    }

    //This method receives a list of Guests and one string. The method will return true of false whether the string
    //matches the phoneNumber property of any Guest in the list of Guests.
    private static boolean findByPhoneNumber(ArrayList<Guest> arrayList, String phoneNumber){
        if(arrayList.isEmpty())
            return false;

        for(Guest guest : arrayList)
            if (guest.getPhoneNumber().equals(phoneNumber)) return true;

        return false;
    }

    //The following method is designed to return the a new Guest object based on the criteria selected by the user(option)

    private Guest getGuestByCriteria(int option){
        Guest targetGuest = new Guest();
        switch (option) {
            case 1:
                System.out.print("First name: ");
                String GuestFirstName = sc.next();
                System.out.print("Last name: ");
                String GuestLastName = sc.next();
                System.out.println();

                targetGuest = new Guest(GuestFirstName, GuestLastName, "", "");
                break;
            case 2:
                System.out.print("Email: ");
                String email = sc.next();
                System.out.println();

                targetGuest = new Guest("", "", email, "");
                break;
            case 3:
                System.out.print("Phone number: ");
                String phoneNo = sc.next();
                System.out.println();

                targetGuest = new Guest("", "", "", phoneNo);
                break;
            default:
                System.out.println("Selected option not available");
                break;
        }
        return targetGuest;
    }



    //The following method is given a Guest object and is designed to find it in the list of participants and remove it
    //if the guest is found and will return true. If not, an appropriate message will be displayed and will return false

    private boolean findAndRemoveGuest(Guest targetGuest){
        Guest searchResult = findGuestInList(guestArrayList,targetGuest);

        if(!targetGuest.equals(searchResult)){
            guestArrayList.remove(searchResult);
            if(!guestQueueArrayList.isEmpty()) {
                guestArrayList.add(guestQueueArrayList.get(0));
                guestQueueArrayList.remove(0);
            }

            System.out.println("Guest has been removed from participants.");
            return true;
        }

        searchResult = findGuestInList(guestQueueArrayList,targetGuest);
        if(!targetGuest.equals(searchResult)){
            guestQueueArrayList.remove(searchResult);
            System.out.println("Guest has been removed from the waiting list.");
            return true;
        }

        System.out.println("Guest not found.");
        return false;
    }

    //The following method receives a list of Guests and a Guest. If the guest properties are found in the list,
    //the method will return the guest inside the list. Otherwise, the method will return the initial guest received
    private Guest findGuestInList(ArrayList<Guest> arrayList, Guest targetGuest){
        for(Guest currentGuest : arrayList)
            if(targetGuest.getFirstName().equals(currentGuest.getFirstName()) &&
                    targetGuest.getLastName().equals(currentGuest.getLastName())   ||
                    targetGuest.getEMail().equals(currentGuest.getEMail())         ||
                    targetGuest.getPhoneNumber().equals(currentGuest.getPhoneNumber()))
                return currentGuest;

        return targetGuest;
    }

    //The following method will ask the user to select the searching criteria. If the input is invalid, the process will
    //repeat until a valid option will be selected or the quit command will be called, in which case, the application will
    //close.
    private int getSelectionOption(){
        displaySearchOptions();
        String option = sc.next();
        int failCount = 0;

        while (option == null || option.length() != 1 || (option.charAt(0) != '1' && option.charAt(0) != '2' && option.charAt(0) != '3')) {
            if(option.equals("quit"))
                System.exit(0);
            option = sc.next();
            failCount++;
            System.out.println("Selection Failed. Please introduce a valid option.");
            if(failCount % 3 == 0)
                displaySearchOptions();
        }

        return Character.getNumericValue(option.charAt(0));
    }

    //The following method calls displayUpdateOption which displays the options available for updating guest information.
    //After a valid input has been registered, the method will return the integer value of the option.
    //If the input is incorrect, the method will continue to ask the user for a valid input until such input is given
    //or until the user types quit. In which case, the application will close.
    private int getUpdateSelection(){
        displayUpdateOptions();
        String option = sc.next();
        int failCount = 0;

        while (option == null || option.length() != 1 || (option.charAt(0) != '1' && option.charAt(0) != '2'
                && option.charAt(0) != '3' && option.charAt(0) != '4')) {
            if(option.equals("quit"))
                System.exit(0);
            option = sc.next();
            failCount++;
            System.out.println("Selection Failed. Please introduce a valid option.");
            if(failCount % 3 == 0)
                displayUpdateOptions();
        }

        return Character.getNumericValue(option.charAt(0));
    }

    //The following method displays all the guest information.
    private void displayGuestDetails(Guest guest){
        System.out.println("First name: " + guest.getFirstName());
        System.out.println("Last name: " + guest.getLastName());
        System.out.println("Phone number: " + guest.getPhoneNumber());
        System.out.println("Email address: " + guest.getEMail());
        System.out.println();
    }

    //The following method displays the options available for searching guests in the list.
    private static void displaySearchOptions(){
        System.out.println("Please select the number corresponding to the criteria which should be used for searching:");
        System.out.println("\t - 1.By name");
        System.out.println("\t - 2.By email");
        System.out.println("\t - 3.By phone number");
    }

    //The following method displays the available options for updating guest information
    private void displayUpdateOptions(){
        System.out.println("Which field would you like to change? Please fill with the corresponding number:");
        System.out.println("\t1 - First Name");
        System.out.println("\t2 - Last Name");
        System.out.println("\t3 - Email address");
        System.out.println("\t4 - Phone Number");
        return;
    }

    private boolean verifyName(String name){
        if(!Character.isUpperCase(name.charAt(0))) {
            System.out.println("Name must start with upper case.");
            return false;
        }

        for(int i = 1; i < name.length(); i++)
            if(name.charAt(i) < 'a' || name.charAt(i) > 'z'){
                System.out.println("Name is invalid.");
                return false;
            }
        return true;
    }

    private boolean verifyEmail(String email){
        if(email.contains(",")) {
            System.out.println("Email can not contain ','!");
            return false;
        }
        if(!email.contains("@")) {
            System.out.println("Email must contain '@'!");
            return false;
        }
        if(email.substring(email.indexOf("@") + 1).contains("@")){
            System.out.println("Email can only contain one '@'!");
            return false;
        }

        char[] invalidCharacters = {'%', '!', '#', '$', '^', '&', '*', '(', ')', '{', '}', '[', ']', '|',
                                    ';', ':', '/', '?', ',', '<','>', '-', '_', '=', '+'};

        for(int i = 0; i < invalidCharacters.length; i++)
            if(email.contains(Character.toString(invalidCharacters[i]))){
                System.out.println("Character " + invalidCharacters[i] + " is not valid.");
                return false;
            }
        return true;
    }

    private boolean verifyPhoneNumber(String phoneNo){
        if(phoneNo.length() != 10){
            System.out.println("Phone number length is not valid.");
            return false;
        }

        for(int i = 0; i < phoneNo.length(); i++)
            if(!Character.isDigit(phoneNo.charAt(i))) {
                System.out.println("Phone number must be numeric");
                return false;
            }
        return true;
    }
}