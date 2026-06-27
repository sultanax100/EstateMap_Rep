package com.example.estatemap;



import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import androidx.appcompat.app.AppCompatActivity;

public class Contract extends AppCompatActivity {

    // Fields for the Contract class
    private String idNumber;
    private String mobileNumber;
    private String nationality;
    private int age;
    private int area;
    private String contractRegistryNumber;
    private String contractType;
    private String imageURL;
    private String location;
    private String ownerName; // Maps to landlord
    private int price;
    private String rentStartDate;
    private String rentEndDate;
    private int rooms;
    private String type; // Property type

    // Default constructor for Firebase
    public Contract() {
        // Empty constructor required for Firebase
    }

    // Getters and Setters for all fields
    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public String getContractRegistryNumber() {
        return contractRegistryNumber;
    }

    public void setContractRegistryNumber(String contractRegistryNumber) {
        this.contractRegistryNumber = contractRegistryNumber;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getRentStartDate() {
        return rentStartDate;
    }

    public void setRentStartDate(String rentStartDate) {
        this.rentStartDate = rentStartDate;
    }

    public String getRentEndDate() {
        return rentEndDate;
    }

    public void setRentEndDate(String rentEndDate) {
        this.rentEndDate = rentEndDate;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // Activity methods and logic
    private CheckBox agreeCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract); // Ensure the layout file is correct

        // Initialize the checkbox
        agreeCheckbox = findViewById(R.id.agree_checkbox);

        // Set a listener to handle checkbox state changes
        agreeCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Open the LeaseAgreementActivity when the checkbox is checked
                    Intent intent = new Intent(Contract.this, LeaseAgreementActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
