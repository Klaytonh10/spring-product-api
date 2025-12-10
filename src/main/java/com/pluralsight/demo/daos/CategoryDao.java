package com.pluralsight.demo.daos;

import com.pluralsight.demo.models.Category;
import com.pluralsight.demo.models.Product;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Component
public class CategoryDao {

    private BasicDataSource dataSource;

    @Autowired
    public CategoryDao(@Value("${datasource.url}") String url,
                      @Value("${datasource.username}") String user,
                      @Value("${DB_PASSWORD}") String password) {
        BasicDataSource source = new BasicDataSource();
        source.setUrl(url);
        source.setUsername(user);
        source.setPassword(password);
        this.dataSource = source;
    }

    public Category insert(Category category) {

        return null;
    }

    public ArrayList<Category> getCategories() {

        String sql = "select * from categories;";

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {

            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Category> categories = new ArrayList<>();

            while (resultSet.next()) {
                Category category = new Category();
                int id = resultSet.getInt("CategoryID");
                String name = resultSet.getString("CategoryName");
                category.setCategoryId(id);
                category.setCategoryName(name);
                categories.add(category);
            }
            return categories;
        } catch (SQLException e) {
            System.err.println("Houston, we have a database problem: " + e);
            return null;
        }
    }

    public Category getCategoryById(int id) {

        String sql = """
                select *
                from categories
                where CategoryID = ?;
                """;

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            String categoryName = resultSet.getString("CategoryName");

            Category category = new Category(id, categoryName);
            return category;

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
