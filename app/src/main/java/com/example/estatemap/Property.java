package com.example.estatemap;
import com.google.firebase.firestore.PropertyName;
public class Property {
    private String name,type,Location,pic;


    @PropertyName("picture")
    public String getPic() {
        return pic;
    }

    @PropertyName("Area")
    public double getArea() {
        return area;
    }

    @PropertyName("age")
    public double getAge() {
        return age;
    }

    @PropertyName("rooms")
    public int getRooms() {
        return rooms;
    }

    @PropertyName("location")
    public String getLocation() {
        return Location;
    }

    private double price,area,age;
    private int rooms;


    public Property() {
        // Default constructor required for calls to DataSnapshot.getValue(Property.class)
    }

    public Property(String name,String Location,String pic, double price,double area,double age,int rooms, String type) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.age=age;
        this.area=area;
        this.Location=Location;
        this.rooms=rooms;
        this.pic=pic;

    }


    public String getName() {
        return name;
    }

    @PropertyName("price")
    public double getPrice() {
        return price;
    }

    @PropertyName("type")
    public String getType() {
        return type;
    }


}