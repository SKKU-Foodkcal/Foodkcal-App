package com.example.foodkcal_app;

public class PostResponse
{
    private String Fat;

    private String Carbs;

    private String Food_Name;

    private String Unit;

    private String Calories;

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