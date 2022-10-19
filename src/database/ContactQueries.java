package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Contact;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Contact queries class. */
public class ContactQueries {

    /** All contacts query. Database query returns all contact information.
     * Catches exceptions, prints stacktrace
     * @return ccontactNamesList observable list of contacts
     * @throws SQLException SQLException
     */
    public static ObservableList<Contact> contacts() throws SQLException {

        ObservableList<Contact> contactNamesList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM contacts";
            PreparedStatement ps = database.JDBC.connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()){
                String contactName = resultSet.getString("Contact_Name");
                Integer contactId = resultSet.getInt("Contact_ID");
                Contact newContact = new Contact(contactName, contactId);
                contactNamesList.add(newContact);
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return contactNamesList;
    }

    /** Contact query by contactId. Database query returns contact information for given contact ID.
     * Catches exceptions, prints stacktrace
     * @return  null
     * @throws SQLException SQLException
     */
    public static Contact contactsId(int contactID) throws SQLException {
        try{
            String sql = "SELECT * FROM contacts WHERE contact_ID=?";
            PreparedStatement ps = database.JDBC.connection.prepareStatement(sql);
            ps.setInt(1, contactID);
            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()){
                Integer contactId = resultSet.getInt("Contact_ID");
                String contactName = resultSet.getString("Contact_Name");
                Contact newContact = new Contact(contactName, contactId);
                return newContact;
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }
}
