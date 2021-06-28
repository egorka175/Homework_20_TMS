package by.tms.homework_TMS_21_.Storage;
import by.tms.homework_TMS_21_.entity.Addresses;
import by.tms.homework_TMS_21_.entity.Telephones;
import by.tms.homework_TMS_21_.entity.User;
import java.sql.*;
import java.util.*;
public class DbUserStorage {
    /**
     * save;
     * updateNameById;
     * updatePasswordById;
     * deleteById;
     * getAll;
     * getAllByName;
     * getByUsername;
     * getAllByStreet;
     * exist;
     * existById.
     */
    //Проверка методов класса.
    public static void main(String[] args) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "postgres");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        DbUserStorage dataBaseAPI = new DbUserStorage(connection);
    }

    private Connection connection;
    public DbUserStorage(Connection connection) {
        this.connection = connection;
    }
    Map<Integer, Optional<User>> userList = new HashMap<>();

    //Метот , который сохраняет нового пользователя.
    public void save(User user) {
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement1 = connection.prepareStatement("insert into telephones values (default, ?) returning id");
            preparedStatement1.setString(1, user.getTelephoneNumber().getTelephoneNumber());
            ResultSet resultSet = preparedStatement1.executeQuery();
            resultSet.next();
            int newTelephoneId = resultSet.getInt(1);
            preparedStatement1.close();

            PreparedStatement preparedStatement2 = connection.prepareStatement("insert into addresses values (default, ?) returning id");
            preparedStatement2.setString(1, user.getAddress().getStreet());
            ResultSet resultSet2 = preparedStatement2.executeQuery();
            resultSet2.next();
            int newStreetId = resultSet2.getInt(1);
            preparedStatement2.close();

            PreparedStatement preparedStatement = connection.prepareStatement("insert into users values (default, ?, ?, ?, ?,?)");
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getUserName());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setInt(4, newTelephoneId);
            preparedStatement.setInt(5, newStreetId);
            preparedStatement.execute();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    //Метод , который изменяет имя по значению ID.
    public void updateNameById(int id, String name) {
        try {
            connection.setAutoCommit(false);
            String sql = "update users set name = ? where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
            preparedStatement.close();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //Метод , который изеняет пароль по ID.
    public void updatePasswordById(int id, String password) {
        try {
            connection.setAutoCommit(false);
            String sql = "update users set password = ? where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, password);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
            preparedStatement.close();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //Метод,который удаляет пользователя по ID.
    public void deleteById(int id) {
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("delete from users  where id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            preparedStatement.close();

            PreparedStatement preparedStatement2 = connection.prepareStatement("delete from addresses  where id = ?");
            preparedStatement2.setInt(1, id);
            preparedStatement2.execute();
            preparedStatement2.close();

            PreparedStatement preparedStatement3 = connection.prepareStatement("delete from telephones  where id = ?");
            preparedStatement3.setInt(1, id);
            preparedStatement3.execute();
            preparedStatement3.close();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }
    //Метод, который выводит всех пользователей.
    public Optional<User> getAll() {
        Optional<User> optional;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select u.id, u.name, u.username, u.password, a.id, a.street, t.id, t.\"telephoneNumber\"from users u join telephones t on  (t.id= u.telephone_id) join addresses a on (a.id = u.address_id)");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String username = resultSet.getString(3);
                String password = resultSet.getString(4);
                int addressId = resultSet.getInt(5);
                String street = resultSet.getString(6);
                int telephoneId = resultSet.getInt(7);
                String telephoneNumber = resultSet.getString(8);
                optional = Optional.of(new User(id, name, username, password, new Addresses(addressId, street), new Telephones(telephoneId, telephoneNumber)));
                userList.put(id, optional);
                return optional;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    //Метод,  который выводит пользователя по его имени.
    public Optional<User> getAllByName(String name2) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select u.id, u.name, u.username, u.password, a.id, a.street, t.id, t.\"telephoneNumber\"from users u join telephones t on  (t.id= u.telephone_id) join addresses a on (a.id = u.address_id) where name=?");
            preparedStatement.setString(1, name2);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String username = resultSet.getString(3);
                String password = resultSet.getString(4);
                int addressId = resultSet.getInt(5);
                String street = resultSet.getString(6);
                int telephoneId = resultSet.getInt(7);
                String telephoneNumber = resultSet.getString(8);
                return Optional.of(new User(id, name, username, password, new Addresses(addressId, street), new Telephones(telephoneId, telephoneNumber)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    //Метод, который выводит пользователя по имени пользователя.
    public Optional<User> getAllByUserName(String userName2) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select u.id, u.name, u.username, u.password, a.id, a.street, t.id, t.\"telephoneNumber\"from users u join telephones t on  (t.id= u.telephone_id) join addresses a on (a.id = u.address_id) where username=?");
            preparedStatement.setString(1, userName2);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String username = resultSet.getString(3);
                String password = resultSet.getString(4);
                int addressId = resultSet.getInt(5);
                String street = resultSet.getString(6);
                int telephoneId = resultSet.getInt(7);
                String telephoneNumber = resultSet.getString(8);
                return Optional.of(new User(id, name, username, password, new Addresses(addressId, street), new Telephones(telephoneId, telephoneNumber)));
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    //Метод, который выводит пользователя по улице в которой он живетю.
    public Optional<User> getAllByStreet(String street2) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select u.id, u.name, u.username, u.password, a.id, a.street, t.id, t.\"telephoneNumber\"from users u join telephones t on  (t.id= u.telephone_id) join addresses a on (a.id = u.address_id) where street=?");
            preparedStatement.setString(1, street2);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String username = resultSet.getString(3);
                String password = resultSet.getString(4);
                int addressId = resultSet.getInt(5);
                String street = resultSet.getString(6);
                int telephoneId = resultSet.getInt(7);
                String telephoneNumber = resultSet.getString(8);
                return Optional.of(new User(id, name, username, password, new Addresses(addressId, street), new Telephones(telephoneId, telephoneNumber)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    //Метод, который проверяет существование пользователя по его ID.
    public boolean existsById(int id) {
        boolean is = false;
        try {
            String sql = "select id from users where id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                is = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return is;
    }
     //Метод, который проверяет на пустоту БД.
    public boolean exists(User user) {
        return existsById(user.getId());
    }
}

