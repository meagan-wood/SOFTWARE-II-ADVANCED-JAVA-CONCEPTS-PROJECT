package model;

/** Country Class.
 */
public class Country {
    public int countryId;
    public String country;

    /**Country constructor.
     * @param country Country name as string
     * @param countryId Country ID as int
     */
    public Country(int countryId, String country){
        this.countryId = countryId;
        this.country = country;
    }

    /** Getter for the countryId.
     * @return countryId Returns country ID
     */
    public int getCountryId() {
        return countryId;
    }

    /** Setter for countryId.
     * @param countryId Sets country ID
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /** Getter for the country.
     * @return country Returns country Name
     */
    public String getCountry() {
        return country;
    }

    /** Setter for country.
     * @param country Sets country name
     */
    public void setCountry(String country) {
        this.country = country;
    }


    @Override
    public String toString(){
        return(country);
    }
}
