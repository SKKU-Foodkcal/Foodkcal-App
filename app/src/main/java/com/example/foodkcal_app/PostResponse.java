package com.example.foodkcal_app;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostResponse
{
    @SerializedName("Fat")
    @Expose
    private String Fat;

    @SerializedName("Carbs")
    @Expose
    private String Carbs;

    @SerializedName("Food_Name")
    @Expose
    private String Food_Name;

    @SerializedName("Unit")
    @Expose
    private String Unit;

    @SerializedName("Calories")
    @Expose
    private String Calories;

    @SerializedName("Protein")
    @Expose
    private String Protein;

    public String getFat ()
    {
        return Fat;
    }

    public void setFat (String Fat)
    {
        this.Fat = Fat;
    }

    public String getCarbs ()
    {
        return Carbs;
    }

    public void setCarbs (String Carbs)
    {
        this.Carbs = Carbs;
    }

    public String getFood_Name ()
    {
        return Food_Name;
    }

    public void setFood_Name (String Food_Name)
    {
        this.Food_Name = Food_Name;
    }

    public String getUnit ()
    {
        return Unit;
    }

    public void setUnit (String Unit)
    {
        this.Unit = Unit;
    }

    public String getCalories ()
    {
        return Calories;
    }

    public void setCalories (String Calories)
    {
        this.Calories = Calories;
    }

    public String getProtein ()
    {
        return Protein;
    }

    public void setProtein (String Protein)
    {
        this.Protein = Protein;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Fat = "+Fat+", Carbs = "+Carbs+", Food_Name = "+Food_Name+", Unit = "+Unit+", Calories = "+Calories+", Protein = "+Protein+"]";
    }
}