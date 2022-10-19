package model;

import database.ContactQueries;
import database.UserQueries;

import java.sql.SQLException;
import java.time.LocalDateTime;

/** Contact Class.
 */
public class Appointment {
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int customerId;
    private int userId;
    private int contactId;

    /**Appointment constructor.
     * @param appointmentId Appointment Id as int
     * @param title Title as string
     * @param description Description as string
     * @param location Location as string
     * @param type Type as string
     * @param startDateTime Start date and time as LocalDateTime
     * @param endDateTime End date and time as LocalDateTime
     * @param customerId Customer ID as int
     * @param userId User ID as int
     * @param contactId Contact ID as int
     */
    public Appointment(int appointmentId, String title, String description, String location, String type, LocalDateTime startDateTime, LocalDateTime endDateTime,
                       int customerId, int userId, int contactId){
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /** Getter for the appointmentId.
     * @return appointmentId Returns appointment ID
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /** Setter for appointmentId.
     * @param appointmentId Sets appointment ID
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /** Getter for the title.
     * @return title Returns title
     */
    public String getTitle() {
        return title;
    }

    /** Setter for title.
     * @param title Sets title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /** Getter for the description.
     * @return description Returns description
     */
    public String getDescription() {
        return description;
    }

    /** Setter for description.
     * @param description Sets description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /** Getter for the location.
     * @return location Returns location
     */
    public String getLocation() {
        return location;
    }

    /** Setter for location.
     * @param location Sets location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /** Getter for the type.
     * @return type Returns type
     */
    public String getType() {
        return type;
    }

    /** Setter for type.
     * @param type Sets type
     */
    public void setType(String type) {
        this.type = type;
    }

    /** Getter for the startDateTime.
     * @return startDateTime Returns start date and time
     */
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    /** Setter for startDateTime.
     * @param startDateTime Sets start date and times as localDateTime
     */
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    /** Getter for the endDateTime.
     * @return endDateTime Returns end date and time
     */
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    /** Setter for endDateTime.
     * @param endDateTime Sets end date and time as localDateTime
     */
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    /** Getter for the customerId.
     * @return customerId Returns customer ID
     */
    public int getCustomerId() {
        return customerId;
    }

    /** Setter for customerId.
     * @param customerId Sets customer ID
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /** Getter for the userId.
     * @return UserQueries.usersById(userId) Returns user information given user id
     */
    public Users getUserId() throws SQLException {
        return UserQueries.usersById(userId);
        //return userId;
    }

    /** Setter for userId.
     * @param userId Sets user ID
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId(){
        return contactId;
    }

    /** Getter for the contactId.
     * @return ContactQueries.contactsId(contactId) Returns contact information given contact id
     */
    public Contact getContactId() throws SQLException {
        return ContactQueries.contactsId(contactId);
        //return contactId;
    }

    /** Setter for contactId.
     * @param contactId Sets user ID
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }


}
