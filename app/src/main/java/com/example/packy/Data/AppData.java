package com.example.packy.Data;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.example.packy.Constants.MyConstants;
import com.example.packy.Database.RoomDB;
import com.example.packy.Models.Items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppData extends Application {

    RoomDB database;
    String category;
    Context context;

    public static final String LAST_VERSION = "LAST_VERSION";
    public static final int NEW_VERSION = 1;

    public AppData(RoomDB database) {
        this.database = database;
    }

    public AppData(RoomDB database, Context context) {
        this.database = database;
        this.context = context;
    }

    public List<Items> getBasicData() {
        category = "Basic Needs";
         List<Items> basicItem = new ArrayList<>();
         basicItem.clear();
         basicItem.add(new Items("Visa",category,false));
         basicItem.add(new Items("Passport",category,false));
         basicItem.add(new Items("Books",category,false));
         basicItem.add(new Items("House key",category,false));
         basicItem.add(new Items("Umbrella",category,false));
         basicItem.add(new Items("Notebook",category,false));
         basicItem.add(new Items("Spectacles case",category,false));
         basicItem.add(new Items("Cash",category,false));
         basicItem.add(new Items("Wallet",category,false));
         basicItem.add(new Items("Driving license",category,false));
         basicItem.add(new Items("Charger",category,false));
         basicItem.add(new Items("Laptop",category,false));
         return basicItem;
    }

    public List<Items> getPersonalCareData(){
        String []data = {"Tooth brush","Tooth paste","Mouthwash","Floss","Spectacles","Razor Blade","Shaving powder","Soap","Shampoo","Hair conditioner","Hair brush","Hair comb",
        "Hair dryer","Hair clip","Hair moulder","Lotion","Moisturizer","Sunscreen","Cleanser","Lip gloss","Perfume","Deodorant","Body splash","Wet wipes","Pads","Cotton","Ear buds",
        "Nail polish","Nail polish remover","Nail cutter","Tissue paper","Scissors"};
        return prepareItemsList(MyConstants.PERSONAL_CARE_CAMEL_CASE,data);

    }

    public List<Items> getClothingData(){
        String []data ={"Lingerie","Stockings","Liners","Pajamas","Hat","T-shirts","Skirts","Casual dress","Evening dress","Shirt",
        "Shorts","Cardigan","Vest","Jacket","Trousers","Jeans","Crop top","Suit","Coat","Gloves","Scarf","Swim suit",
        "Belt","Sandals","Sneakers","Casual shoes,Heels","Wedges","Boots","Sports wear","Rain coat"};
        return prepareItemsList(MyConstants.CLOTHING_CAMEL_CASE,data);
    }

    public List<Items> getBabyNeedsData() {
        String[] data = {"Blanket","Bath towel","Baby powder","Baby oil","Baby socks","Baby hat","Outfit","Baby oil",
        "Jumpsuit","Bibs","Milk bottles","Milk powder","Thermos","Baby food","Breast pump","Baby food spoon","Diapers",
        "Wet wipes","Storage container","Sweater","Stockings","Baby care cover","Baby shampoo","Potty","Nasal aspirator","Probiotic powder",
        "Toys","Playpen","Baby moisturizer"};
        return prepareItemsList(MyConstants.BABY_NEEDS_CAMEL_CASE, data);
    }

    public List<Items> getHealthData() {
        String []data ={"Drugs used","Vitamins and supplements","Hot water bag","Painkillers","Bandage","Tincture of Lodine",
        "Lens solution","Condom"};
        return prepareItemsList(MyConstants.HEALTH_CAMEL_CASE,data);
    }

    public List<Items> getTechnologyData() {
        String []data ={"Phone","Charger","Camera","Bluetooth speaker","Headphone","Laptop","Mouse",
        "Extension cable","Power bank","Flash drive"};
        return prepareItemsList(MyConstants.TECHNOLOGY_CAMEL_CASE,data);
    }

    public List<Items> getFoodData() {
        String []data ={"Water","Snacks","Juice","Packed lunch","Thermos","Baby food","Fruit"};
        return prepareItemsList(MyConstants.FOOD_CAMEL_CASE,data);
    }

    public List<Items> getBeachSuppliesData() {
        String []data ={"Sunscreen","Lotion","Swim suit","Sun glasses","Beach sandals","Floater",
        "Towel","Beach bag","Beach sheet"};
        return prepareItemsList(MyConstants.BEACH_SUPPLIES_CAMEL_CASE,data);
    }

    public List<Items> getCarSuppliesData() {
        String []data ={"Driving license","Car jack","Pump","Spare car key","Car cover","Car charger"};
        return prepareItemsList(MyConstants.CAR_SUPPLIES_CAMEL_CASE,data);
    }

    public List<Items> getNeedsData() {
        String []data ={"Backpack","Travel lock","Magazine","Important numbers","Laundry","Sewing kit","Sports equipment"};
        return prepareItemsList(MyConstants.NEEDS_CAMEL_CASE,data);
    }

    public List<Items> prepareItemsList(String category,String []data){
        List<String> list = Arrays.asList(data);
        List<Items> dataList = new ArrayList<>();
        dataList.clear();

        for(int i=0;i<list.size();i++){
            dataList.add(new Items(list.get(i),category,false));

        }
        return dataList;

    }

    public List<List<Items>> getAllData(){
        List<List<Items>> listOfAllItems = new ArrayList<>();
        listOfAllItems.clear();
        listOfAllItems.add(getBasicData());
        listOfAllItems.add(getClothingData());
        listOfAllItems.add(getPersonalCareData());
        listOfAllItems.add(getBabyNeedsData());
        listOfAllItems.add(getHealthData());
        listOfAllItems.add(getTechnologyData());
        listOfAllItems.add(getFoodData());
        listOfAllItems.add(getBeachSuppliesData());
        listOfAllItems.add(getCarSuppliesData());
        listOfAllItems.add(getNeedsData());
        return listOfAllItems;

    }

    public void persistAllData(){
        List<List<Items>> listOfAllItems = getAllData();
        for(List<Items> list: listOfAllItems){
            for(Items items:list){
                database.mainDao().saveItem(items);
            }
        }
        System.out.println("Data added");
    }

    public void persistDataByCategory(String category,Boolean onlyDelete) {
        try {
            List<Items> list = deleteAndGetListByCategory(category, onlyDelete);
            if (!onlyDelete){
                for(Items item : list) {
                    database.mainDao().saveItem(item);
                }

            }else {
                Toast.makeText(this, category + "Reset successful", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private List<Items> deleteAndGetListByCategory(String category, Boolean onlyDelete) {
        if(onlyDelete){
            database.mainDao().deleteAllByCategoryAndAddedBy(category, MyConstants.SYSTEM_SMALL);
        }else {
            database.mainDao().deleteAllByCategory(category);
        }

        switch (category){
            case MyConstants.BASIC_NEEDS_CAMEL_CASE:
                return getBasicData();

            case  MyConstants.CLOTHING_CAMEL_CASE:
                return getClothingData();

            case  MyConstants.PERSONAL_CARE_CAMEL_CASE:
                return getPersonalCareData();

            case  MyConstants.BABY_NEEDS_CAMEL_CASE:
                return getBabyNeedsData();

            case  MyConstants.HEALTH_CAMEL_CASE:
                return getHealthData();

            case  MyConstants.TECHNOLOGY_CAMEL_CASE:
                return getTechnologyData();

            case  MyConstants.FOOD_CAMEL_CASE:
                return getFoodData();

            case  MyConstants.BEACH_SUPPLIES_CAMEL_CASE:
                return getBeachSuppliesData();

            case  MyConstants.CAR_SUPPLIES_CAMEL_CASE:
                return getCarSuppliesData();

            case  MyConstants.NEEDS_CAMEL_CASE:
                return getNeedsData();

            default:
                return new ArrayList<>();


        }
    }

}

