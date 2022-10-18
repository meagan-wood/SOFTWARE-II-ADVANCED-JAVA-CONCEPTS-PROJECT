package model;

/** Division class. */
public class Division {
    private int divisionId;
    private String division;
    private int countryId;

    /**Division constructor.
     * @param divisionId Division ID as int
     * @param division Division name as string
     * @param countryId Country ID as int
     */
    public Division (int divisionId, String division, int countryId){
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;
    }

    /** Getter for the divisionId.
     * @return divisionId Returns divisionId
     */
    public int getDivisionId() {
        return divisionId;
    }

    /** Setter for divisionId.
     * @param divisionId Sets divisionId
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /** Getter for the division name.
     * @return division Returns division name
     */
    public String getDivision() {
        return division;
    }

    /** Setter for division name.
     * @param division Sets division name
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /** Getter for the countryId.
     * @return countryId Returns countryId
     */
    public int getCountryId() {
        return countryId;
    }

    /** Setter for countryId.
     * @param countryId Sets countryId
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    @Override
    public String toString(){
        return (division);
    }
}
