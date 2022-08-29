package model;

public class Contact {

    private String contactName;
    private int contactId;

    public Contact(String contactName, int contactId){
        this.contactName = contactName;
        this.contactId = contactId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    @Override
    public String toString(){
        return(Integer.toString(contactId) + " " + contactName);
    }
}
