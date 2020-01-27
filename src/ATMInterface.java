import java.util.Date;

public class ATMInterface {
    
}
/*AcoountHolder class*/
class AccountHolder {
    
    private int nrc_number;
    private String first_name;
    private String middle_name;
    private String last_name;
    private String mobile_number;
    private String email;
    private String nationality;
    private Address address;
    private Date dob;
    private String occupation = "None";
    private Account account;

    
    /*construct an account holder without a middle name and occupation*/
    public AccountHolder(int nrc_number, String first_name, String last_name,
            String mobile_number, String email, String nationality,
            Address address, Date dob) {
        
        nrc_number = getNRCNumber();
        
        first_name = getFirstName();
        
        last_name = getLastName();
        
        mobile_number = getMobileNumber();
        
        email = getEmail();
        
        nationality = getNationality();
        
        address = getAddress();
        
        dob = getDOB();
        
        
        
    }
    
    /**construct an account holder without an occupation*/
    public AccountHolder(int nrc_number, String first_name, String middle_name,
            String last_name, String mobile_number, String email,
            String nationality, Address address, Date dob, Account account) {
        
        nrc_number = getNRCNumber();
        
        first_name = getFirstName();
        
        middle_name = getMiddleName();
        
        last_name = getLastName();
        
        mobile_number = getMobileNumber();
        
        email = getEmail();
        
        nationality = getNationality();
        
        address = getAddress();
        
        dob = getDOB();
        
        account = getAccount();
        
    }
    
    /*construct account holder with all necessary details*/
    public AccountHolder(int nrc_number, String first_name, String middle_name,
            String last_name, String mobile_number, String email, 
            String nationality, Address address, Date dob, String occupation) {
        
        nrc_number = getNRCNumber();
        
        first_name = getFirstName();
        
        middle_name = getMiddleName();
        
        last_name = getLastName();
        
        nationality = getNationality();
        
        address = getAddress();
        
        dob = getDOB();
        
        occupation = getOccupation();
    }
    
    //get account holder's nrc number
    public int getNRCNumber() {
        
        return nrc_number;
    }
    
    public void setNRCNumber(int nrc_number) {
        
        this.nrc_number = nrc_number;
    }
    
    //get account holder's first name
    public String getFirstName() {
        
        return first_name;
        
    }
    
    //set account holder's first name
    public void setFirstName(String first_name) {
        
        this.first_name = first_name;
    }
    
    //get the account holder's middle name
    public String getMiddleName() {
        
        return middle_name;
    }
    
    //set the account holder's middle name
    public void setMiddleName(String middle_name) {
        
        this.middle_name = middle_name;
    }
    
    //get the account holder's last name
    public String getLastName() {
        
        return last_name;
    }
    
    //set the account holder's last name
    public void setLastName(String last_name) {
        
        this.last_name = last_name;
    }
    
    //get the account holder's mobile number
    public String getMobileNumber() {
        
        return mobile_number;
    }
    
    //set the account holder's mobile number
    public void setMobileNumber(String mobile_number) {
        
        this.mobile_number = mobile_number;
        
    }
    //get the account holder's email address
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        
        this.email = email;
    }
    //get the account holder's nationality
    public String getNationality() {
        
        return nationality;
    }
    
    //set the account holder's nationality
    public void setNationality(String nationality) {
        
        this.nationality = nationality;
    }
    
    //get the account holder's address
    public Address getAddress() {
        
        return address;
        
    }
    
    //set the account holder's address
    public void setAddress(Address address) {
        
        this.address = address;
        
    }
    //get the account holder's date of birth
    public Date getDOB() {
        
        return dob;
    }
    
    //set the account holder's date of birth
    public void setDOB(Date dob) {
        
        this.dob = dob;
    }
    
    //get the account holder's occupation
    public String getOccupation() {
        
        return occupation;
    }    
    
    //set the account holder's occupation
    public void setOccupation(String occupation) {
        
        this.occupation = occupation;
    }
    
    //get the account holder's account
    public Account getAccount() {
        
        return account;
    }
    
    //set the account holder's account
    public void setAccount(Account account) {
        
        this.account = account;
    }
}

class Address {
    
    private String street_address;
    private String city;
    private String country;
    private String postal_or_zip_code;
    
    //construc a default Address
    public Address() {
        
    }
    
    //construct an address with street address, city, country and postal or zip code
    public Address(String street_address, String city, String country, String postal_or_zip_code){
        
        street_address = getStreetAddress();
        
        city = getCity();
        
        country = getCountry();
        
        postal_or_zip_code = getPostalOrZipCode();
        
    }
    public String getStreetAddress() {
        
        return street_address;
        
    }

    public void setStreetAddress(String street_address) {
        
        this.street_address = street_address;
        
    }

    public String getCity() {
        
        return city;
    }

    public void setCity(String city) {
        
        this.city = city;
        
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        
        this.country = country;
        
    }

    public String getPostalOrZipCode() {
        
        return postal_or_zip_code;
    }

    public void setPostalOrZipCode(String postal_or_zip_code) {
        
        this.postal_or_zip_code = postal_or_zip_code;
        
    }
}
