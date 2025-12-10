package com.pluralsight.demo.daos;

import com.pluralsight.demo.models.Product;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;

import java.util.ArrayList;

@Component //Makes a bean (creates a class that spring handles)
public class ProductDao {

    private BasicDataSource dataSource;

    @Autowired
    public ProductDao(@Value("${datasource.url}") String url,
                      @Value("${datasource.username}") String user,
                      @Value("${DB_PASSWORD}") String password) {
        BasicDataSource source = new BasicDataSource();
        source.setUrl(url);
        source.setUsername(user);
        source.setPassword(password);
        this.dataSource = source;
    }

    public Product createNewProduct(Product product) {

        String sql = """
                insert into products (ProductName, CategoryID, UnitPrice)
                values (?,?,?);
                """;

        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ) {
            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setInt(2, product.getCategoryId());
            preparedStatement.setDouble(3, product.getUnitPrice());

            int rowsInserted = preparedStatement.executeUpdate();

            if(rowsInserted != 1) {
                System.err.println("Something crazy is happening... number of rows inserted ain't 1 🤪");
            }

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int productId = resultSet.getInt(1);
            resultSet.close();

            return getProductById(productId);

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Product getProductById(int id) {
        String sql = """
                select *
                from Products
                where ProductID = ?;
                """;

        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            String name = resultSet.getString("ProductName");
            int category = resultSet.getInt("CategoryID");
            double price = resultSet.getDouble("UnitPrice");

            Product product = new Product(id, name, category, price);
            return product;

        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Product> getProducts() {

        String sql = "select * from products;";
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {

            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Product> products = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("ProductID");
                String name = resultSet.getString("ProductName");
                int category = resultSet.getInt("CategoryID");
                double price = resultSet.getDouble("UnitPrice");
                Product product = new Product(id, name, category, price);
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            System.err.println("Houston, we have a database problem: " + e);
            return null;
        }
    }

}
