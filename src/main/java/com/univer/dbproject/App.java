package com.univer.dbproject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import org.postgresql.ds.PGSimpleDataSource;

public class App 
{
    public static Connection connection;
    public static void main( String[] args )
    {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL("jdbc:postgresql://localhost:5432/cooking");
        try (Connection connection = dataSource.getConnection("KEB", "");
             BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))){
            App.connection = connection;
            while (true){
                Command currentCommand = Command.valueOf(reader.readLine());
                switch (currentCommand){
                    case ADD_AUTHOR:
                        addAuthor(reader);
                        break;
                    case ADD_INGREDIENT:
                        addIngredient(reader);
                        break;
                    case ADD_DISHES_FAMILY:
                        addDishesFamily(reader);
                        break;
                    case ADD_RECIPE:
                        addRecipe(reader);
                        break;
                    case ADD_INGREDIENT_IN_RECIPE:
                        addRecipeIngredient(reader);
                        break;
                    case DELETE_AUTHOR:
                        deleteAuthor(reader);
                        break;
                    case DELETE_INGREDIENT:
                        deleteIngredient(reader);
                        break;
                    case DELETE_DISHES_FAMILY:
                        deleteDishesFamily(reader);
                        break;
                    case DELETE_RECIPE:
                        deleteRecipe(reader);
                        break;
                    case DELETE_INGREDIENT_FROM_RECIPE:
                        deleteIngredientFromRecipe(reader);
                        break;
                    case UPDATE_AUTHOR:
                        updateAuthor(reader);
                        break;
                    case UPDATE_INGREDIENT:
                        updateIngredient(reader);
                        break;
                    case UPDATE_DISHES_FAMILY:
                        updateDishesFamily(reader);
                        break;
                    case UPDATE_RECIPE:
                        updateRecipe(reader);
                        break;
                    case UPDATE_INGREDIENT_IN_RECIPE:
                        updateIngredientInRecipe(reader);
                        break;
                    case TAKE_AUTHOR_BY_KEY:
                        takeAuthorByKey(reader);
                        break;
                    case TAKE_INGREDIENT_BY_KEY:
                        takeIngredientByKey(reader);
                        break;
                    case TAKE_DISHES_FAMILY_BY_KEY:
                        takeDishesFamilyByKey(reader);
                        break;
                    case TAKE_RECIPE_BY_KEY:
                        takeRecipeByKey(reader);
                        break;
                    case TAKE_INGREDIENT_FROM_RECIPE_BY_KEY:
                        takeIngredientFromRecipeByKey(reader);
                        break;
                    case TAKE_ALL_AUTHOR:
                        takeAllAuthor();
                        break;
                    case TAKE_ALL_INGREDIENT:
                        takeAllIngredient();
                        break;
                    case TAKE_ALL_DISHES_FAMILY:
                        takeAllDishesFamily();
                        break;
                    case TAKE_ALL_RECIPE:
                        takeAllRecipe();
                        break;
                    case TAKE_ALL_INGREDIENT_FROM_RECIPE:
                        takeAllIngredientFromRecipe();
                        break;
                }
            }
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void takeAllIngredientFromRecipe() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select * from recipe_ingredient");
        printResults(statement.executeQuery());
    }


    private static void takeAllRecipe() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select * from recipe");
        printResults(statement.executeQuery());
    }

    private static void takeAllDishesFamily() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select * from dishes_family");
        printResults(statement.executeQuery());
    }

    private static void takeAllIngredient() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select * from ingredient");
        printResults(statement.executeQuery());
    }

    private static void takeAllAuthor() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select * from recipe_author");
        printResults(statement.executeQuery());
    }

    private static void takeIngredientFromRecipeByKey(BufferedReader reader) throws SQLException, IOException {
        System.out.println("Input the author's title:");
        String authorTitle = reader.readLine();
        System.out.println("Input the author name:");
        String author = reader.readLine();
        System.out.println("Input the ingredient name:");
        String ingredientName = reader.readLine();
        printResults(takeIngredientFromRecipeByKey(authorTitle, author, ingredientName));
    }

    private static ResultSet takeIngredientFromRecipeByKey(String authorTitle, String author, String ingredientName) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select * from recipe_ingredient where author_title = ? AND author = ? AND ingredient_name = ?");
        statement.setString(1, authorTitle);
        statement.setString(2, author);
        statement.setString(3, ingredientName);
        return statement.executeQuery();
    }

    private static void takeRecipeByKey(BufferedReader reader) throws IOException, SQLException {
        System.out.println("Input the author's title:");
        String authorTitle = reader.readLine();
        System.out.println("Input the author name");
        String author = reader.readLine();
        printResults(takeRecipeByKey(authorTitle, author));
    }

    private static ResultSet takeRecipeByKey(String authorTitle, String author) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select * from recipe where author_title = ? AND author = ?");
        statement.setString(1, authorTitle);
        statement.setString(2, author);
        return statement.executeQuery();
    }

    private static void takeDishesFamilyByKey(BufferedReader reader) throws IOException, SQLException {
        System.out.println("Input the dishes family name:");
        String name = reader.readLine();
        printResults(takeDishesFamilyByKey(name));
    }

    private static ResultSet takeDishesFamilyByKey(String name) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select * from dishes_family where name = ?");
        statement.setString(1, name);
        return statement.executeQuery();
    }

    private static void takeIngredientByKey(BufferedReader reader) throws IOException, SQLException {
        System.out.println("Input the ingredient name:");
        String ingredientName = reader.readLine();
        printResults(takeIngredientByKey(ingredientName));
    }

    private static ResultSet takeIngredientByKey(String ingredientName) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select * from ingredient where ingredient_name = ?");
        statement.setString(1, ingredientName);
        return statement.executeQuery();
    }

    private static void takeAuthorByKey(BufferedReader reader) throws IOException, SQLException {
        System.out.println("Input the author name:");
        String authorName = reader.readLine();
        printResults(takeAuthorByKey(authorName));
    }

    private static ResultSet takeAuthorByKey(String authorName) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select * from recipe_author where author_name = ?");
        statement.setString(1, authorName);
        return statement.executeQuery();
    }

    private static void updateIngredientInRecipe(BufferedReader reader) throws IOException, SQLException {
        System.out.println("Input the author's title:");
        String authorTitle = reader.readLine();
        System.out.println("Input the author name:");
        String author = reader.readLine();
        System.out.println("Input the ingredient name:");
        String ingredientName = reader.readLine();
        System.out.println("Input a new quantity:");
        String quantity = reader.readLine();
        updateIngredientInRecipe(authorTitle, author, ingredientName, quantity);
    }

    private static void updateIngredientInRecipe(String authorTitle, String author, String ingredientName, String quantity) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("update recipe_ingredient set quantity = ? WHERE author_title = ? AND author = ? AND ingredient_name = ?");
        statement.setString(1, quantity);
        statement.setString(2, authorTitle);
        statement.setString(3, author);
        statement.setString(4, ingredientName);
        statement.execute();
        System.out.println("Done");
    }

    private static void updateRecipe(BufferedReader reader) throws IOException, SQLException {
        System.out.println("Input the author's title:");
        String authorTitle = reader.readLine();
        System.out.println("Input the author name");
        String author = reader.readLine();
        System.out.println("Input the new dishes family title:");
        String dishesFamilyTitle = reader.readLine();
        System.out.println("Input the new tear of creation:");
        int yearOfCreation = Integer.parseInt(reader.readLine());
        System.out.println("Input the new cooking process text:");
        String cookingProcess = reader.readLine();
        updateRecipe(authorTitle, author, dishesFamilyTitle, yearOfCreation, cookingProcess);
    }

    private static void updateRecipe(String authorTitle, String author, String dishesFamilyTitle, int yearOfCreation, String cookingProcess) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("update recipe set dishes_family_title = ?, year_of_creation = ?, cooking_process = ? WHERE author_title = ? AND author = ?");
        statement.setString(1, dishesFamilyTitle);
        statement.setInt(2, yearOfCreation);
        statement.setString(3, cookingProcess);
        statement.setString(4, authorTitle);
        statement.setString(5, author);
        statement.execute();
        System.out.println("Done");
    }

    private static void updateDishesFamily(BufferedReader reader) throws IOException, SQLException {
        System.out.println("Input the dishes family name:");
        String name = reader.readLine();
        System.out.println("Input the new calorie content of the dish:");
        int caloric = Integer.parseInt(reader.readLine());
        System.out.println("Input the new country of appearance:");
        String country = reader.readLine();
        System.out.println("Input the new year of appearance:");
        int year = Integer.parseInt(reader.readLine());
        System.out.println("Input the new history of appearance:");
        String history = reader.readLine();
        updateDishesFamily(name, caloric, country, year, history);
    }

    private static void updateDishesFamily(String name, int caloric, String country, int year, String history) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("update dishes_family set caloric = ?, country = ?, year = ?, history = ? WHERE name = ?");
        statement.setInt(1, caloric);
        statement.setString(2, country);
        statement.setInt(3, year);
        statement.setString(4, history);
        statement.setString(5, name);
        statement.execute();
        System.out.println("Done");
    }

    private static void updateIngredient(BufferedReader reader) throws SQLException, IOException {
        System.out.println("Input the ingredient name:");
        String ingredientName = reader.readLine();
        System.out.println("Input the new ingredient description");
        String description = reader.readLine();
        updateIngredient(ingredientName, description);
    }

    private static void updateIngredient(String ingredientName, String description) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("update ingredient set description = ? WHERE ingredient_name = ?");
        statement.setString(1, description);
        statement.setString(2, ingredientName);
        statement.execute();
        System.out.println("Done");
    }

    private static void updateAuthor(BufferedReader reader) throws IOException, SQLException {
        System.out.println("Input the author name:");
        String authorName = reader.readLine();
        System.out.println("Input the new author's year of birth:");
        int yearOfBirth = Integer.parseInt(reader.readLine());
        System.out.println("Input the new author's brief biography:");
        String biography = reader.readLine();
        updateAuthor(authorName, yearOfBirth, biography);
    }

    private static void updateAuthor(String authorName, int yearOfBirth, String biography) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("update recipe_author set year_of_birth = ?, brief_biography = ? WHERE author_name = ?");
        statement.setInt(1, yearOfBirth);
        statement.setString(2, biography);
        statement.setString(3, authorName);
        statement.execute();
        System.out.println("Done");
    }

    private static void deleteIngredientFromRecipe(BufferedReader reader) throws SQLException, IOException {
        System.out.println("Input the author's title:");
        String authorTitle = reader.readLine();
        System.out.println("Input the author name:");
        String author = reader.readLine();
        System.out.println("Input the ingredient name:");
        String ingredientName = reader.readLine();
        deleteIngredientFromRecipe(authorTitle, author, ingredientName);
    }

    private static void deleteIngredientFromRecipe(String authorTitle, String author, String ingredientName) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("delete from recipe_ingredient where author_title = ? AND author = ? AND ingredient_name = ?");
        statement.setString(1, authorTitle);
        statement.setString(2, author);
        statement.setString(3, ingredientName);
        statement.execute();
        System.out.println("Done");
    }

    private static void deleteRecipe(BufferedReader reader) throws IOException, SQLException {
        System.out.println("Input the author's title:");
        String authorTitle = reader.readLine();
        System.out.println("Input the author name");
        String author = reader.readLine();
        deleteRecipe(authorTitle, author);
    }

    private static void deleteRecipe(String authorTitle, String author) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("delete from recipe where author_title = ? AND author = ?");
        statement.setString(1, authorTitle);
        statement.setString(2, author);
        statement.execute();
        System.out.println("Done");
    }

    private static void deleteDishesFamily(BufferedReader reader) throws SQLException, IOException {
        System.out.println("Input the dishes family name:");
        String name = reader.readLine();
        deleteDishesFamily(name);
    }

    private static void deleteDishesFamily(String name) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("delete from dishes_family where name = ?");
        statement.setString(1, name);
        statement.execute();
        System.out.println("Done");
    }

    private static void deleteIngredient(BufferedReader reader) throws SQLException, IOException {
        System.out.println("Input the ingredient name:");
        String ingredientName = reader.readLine();
        deleteIngredient(ingredientName);
    }

    private static void deleteIngredient(String ingredientName) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("delete from ingredient where ingredient_name = ?");
        statement.setString(1, ingredientName);
        statement.execute();
        System.out.println("Done");
    }

    private static void deleteAuthor(BufferedReader reader) throws IOException, SQLException {
        System.out.println("Input the author name:");
        String authorName = reader.readLine();
        deleteAuthor(authorName);
    }

    private static void deleteAuthor(String authorName) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("delete from recipe_author where author_name = ?");
        statement.setString(1, authorName);
        statement.execute();
        System.out.println("Done");
    }

    private static void addRecipeIngredient(BufferedReader reader) throws IOException, SQLException {
        System.out.println("Input the author's title:");
        String authorTitle = reader.readLine();
        System.out.println("Input the author name:");
        String author = reader.readLine();
        System.out.println("Input the ingredient name:");
        String ingredientName = reader.readLine();
        System.out.println("Input a quantity:");
        String quantity = reader.readLine();
        addRecipeIngredient(authorTitle, author, ingredientName, quantity);
    }

    private static void addRecipeIngredient(String authorTitle, String author, String ingredientName, String quantity) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("insert into recipe_ingredient values ( ?,?,?,? )");
        statement.setString(1, authorTitle);
        statement.setString(2, author);
        statement.setString(3, ingredientName);
        statement.setString(4, quantity);
        statement.execute();
        System.out.println("Done");
    }

    private static void addRecipe(BufferedReader reader) throws IOException, SQLException {
        System.out.println("Input the author's title:");
        String authorTitle = reader.readLine();
        System.out.println("Input the author name");
        String author = reader.readLine();
        System.out.println("Input the dishes family title:");
        String dishesFamilyTitle = reader.readLine();
        System.out.println("Input the tear of creation:");
        int yearOfCreation = Integer.parseInt(reader.readLine());
        System.out.println("Input the cooking process text:");
        String cookingProcess = reader.readLine();
        addRecipe(authorTitle, author, dishesFamilyTitle, yearOfCreation, cookingProcess);
    }

    private static void addRecipe(String authorTitle, String author, String dishesFamilyTitle, int yearOfCreation, String cookingProcess) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("insert into recipe values ( ?,?,?,?,? )");
        statement.setString(1, authorTitle);
        statement.setString(2, author);
        statement.setString(3, dishesFamilyTitle);
        statement.setInt(4, yearOfCreation);
        statement.setString(5, cookingProcess);
        statement.execute();
        System.out.println("Done");
    }

    private static void addDishesFamily(BufferedReader reader) throws IOException, SQLException {
        System.out.println("Input the dishes family name:");
        String name = reader.readLine();
        System.out.println("Input the calorie content of the dish:");
        int caloric = Integer.parseInt(reader.readLine());
        System.out.println("Input the country of appearance:");
        String country = reader.readLine();
        System.out.println("Input the year of appearance:");
        int year = Integer.parseInt(reader.readLine());
        System.out.println("Input the history of appearance:");
        String history = reader.readLine();
        addDishesFamily(name, caloric, country, year, history);
    }

    private static void addDishesFamily(String name, int caloric, String country, int year, String history) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("insert into dishes_family values ( ?,?,?,?,? )");
        statement.setString(1, name);
        statement.setInt(2, caloric);
        statement.setString(3, country);
        statement.setInt(4, year);
        statement.setString(5, history);
        statement.execute();
        System.out.println("Done");
    }

    private static void addIngredient(BufferedReader reader) throws IOException, SQLException {
        System.out.println("Input the ingredient name:");
        String ingredientName = reader.readLine();
        System.out.println("Input the ingredient description");
        String description = reader.readLine();
        addIngredient(ingredientName, description);
    }

    private static void addIngredient(String ingredientName, String description) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("insert into ingredient values ( ?,? )");
        statement.setString(1, ingredientName);
        statement.setString(2, description);
        statement.execute();
        System.out.println("Done");
    }

    public static void addAuthor(BufferedReader reader) throws IOException, SQLException {
        System.out.println("Input the author name:");
        String authorName = reader.readLine();
        System.out.println("Input the author's year of birth:");
        int yearOfBirth = Integer.parseInt(reader.readLine());
        System.out.println("Input the author's brief biography:");
        String biography = reader.readLine();
        addAuthor(authorName, yearOfBirth, biography);
    }
    public static void addAuthor(String authorName, int yearOfBirth, String biography) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("insert into recipe_author values ( ?,?,? )");
        statement.setString(1, authorName);
        statement.setInt(2, yearOfBirth);
        statement.setString(3, biography);
        statement.execute();
        System.out.println("Done");
    }

    public static void printResults(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            int numberOfColumn = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= numberOfColumn; i++) {
                System.out.println(resultSet.getMetaData().getColumnName(i) + ": " +resultSet.getString(i));
            }
            System.out.println();
        }
    }
}
