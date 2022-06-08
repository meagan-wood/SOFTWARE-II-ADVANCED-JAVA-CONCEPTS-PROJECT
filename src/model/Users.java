package model;

/** Users class. */
public class Users {
    private int userId;
    private String userName;
    private String password;

    /**User constructor.
     * @param userId ID as int
     * @param userName Name as string
     * @param password Password as string
     */
    public Users(int userId, String userName, String password){
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    /** Getter for the userId.
     * @return userId Returns userId
     */
    public int getUserId() {
        return userId;
    }

    /** Setter for userId.
     * @param userId Sets userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /** Getter for userName.
     * @return userName Returns userName
     */
    public String getUserName() {
        return userName;
    }

    /** Setter for userName.
     * @param userName Sets userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** Getter for password.
     * @return password Returns password
     */
    public String getPassword() {
        return password;
    }

    /** Setter for password.
     * @param password Sets password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
