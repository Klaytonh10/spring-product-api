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
            float price = resultSet.getFloat("UnitPrice");

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
                float price = resultSet.getFloat("UnitPrice");
                Product product = new Product(id, name, category, price);
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            System.err.println("Houston, we have a database problem: " + e);
            return null;
        }
    }

    public void updateProductById(int pid,Product p) {
        String sql = """
                update products
                set ProductName = ?, CategoryID = ?, UnitPrice = ?
                where ProductID = ?;
                """;
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setString(1, p.getProductName());
            preparedStatement.setInt(2, p.getCategoryId());
            preparedStatement.setFloat(3, p.getUnitPrice());
            preparedStatement.setInt(4, p.getProductID());
            int rowsUpdated = preparedStatement.executeUpdate();
            System.out.println("Row's updated: " + rowsUpdated);
            if(rowsUpdated!=1) {
                System.err.println("Too many rows updated");
                throw new RuntimeException("Too many rows updated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProductById(int pid) {
        String sql = """
                delete from products
                where ProductID = ?;
                """;
        try(
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, pid);
            int rowsDeleted = preparedStatement.executeUpdate();
            if(rowsDeleted!=1) {
                throw new RuntimeException("The rows deleted wasn't 1 as expected");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
