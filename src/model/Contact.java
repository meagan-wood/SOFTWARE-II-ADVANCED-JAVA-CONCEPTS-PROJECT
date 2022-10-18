package model;

/** Contact Class.
 */
public class Contact {

    private String contactName;
    private int contactId;

    /**Contact constructor.
     * @param contactName Contact name as string
     * @param contactId Contact ID as int
     */
    public Contact(String contactName, int contactId){
        this.contactName = contactName;
        this.contactId = contactId;
    }

    /** Getter for the contactId.
     * @return contactId Returns contact ID
     */
    public int getContactId() {
        return contactId;
    }

    /** Setter for contactId.
     * @param contactId Sets contact ID
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /** Getter for the contactName.
     * @return contactName Returns contact name
     */
    public String getContactName() {
        return contactName;
    }

    /** Setter for contactName.
     * @param contactName Sets contact name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    @Override
    public String toString(){
        return(Integer.toString(contactId) + " " + contactName);
    }
}
