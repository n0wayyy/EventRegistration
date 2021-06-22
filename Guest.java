public class Guest {

    private String firstName;
    private String lastName;
    private String EMail;
    private String phoneNumber;

    public Guest(){
        this.firstName = "";
        this.lastName = "";
        this.EMail = "";
        this.phoneNumber = "";
    }

    public Guest(String firstName, String lastName, String EMail, String phoneNumber){
        this.firstName = firstName;
        this.lastName = lastName;
        this.EMail = EMail;
        this.phoneNumber = phoneNumber;
    }


    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setEMail (String EMail){
        this.EMail = EMail;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }


    public String getLastName(){
        return lastName;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getEMail(){
        return EMail;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }


    public boolean equals(Guest matchGuest){
        if(this.getFirstName().equals(matchGuest.getFirstName())&&
           this.getLastName().equals(matchGuest.getLastName())  &&
           this.getEMail().equals(matchGuest.getEMail())        &&
           this.getPhoneNumber().equals(matchGuest.getPhoneNumber()))
            return true;
        return false;
    }

    public boolean findString(String searchString){
        if((this.getFirstName().toLowerCase().contains(searchString) ||
            this.getLastName().toLowerCase().contains(searchString)  ||
            this.getEMail().toLowerCase().contains(searchString))    ||
            this.getPhoneNumber().toLowerCase().contains(searchString))
            return true;
        return false;
    }


}
