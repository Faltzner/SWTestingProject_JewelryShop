package collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import sql_and_db_util.DBConnect;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

public class DataCollection {
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet rs;

    private ObservableList<Employee> employeeList;
    private ObservableList<BuyOrder> buyOrderList;
    private ObservableList<WorkOrder> workList;
    private ObservableList<Product> productList;
    private ObservableList<Receipt> receiptList;

    public DataCollection() {
        connection = DBConnect.connect();
        employeeList = FXCollections.observableArrayList();
        buyOrderList = FXCollections.observableArrayList();
        workList = FXCollections.observableArrayList();
        productList = FXCollections.observableArrayList();
        receiptList = FXCollections.observableArrayList();
    }

    public void reconnect() {
        connection = DBConnect.connect();
    }

    public void readEmployeeList() {
        employeeList.clear();
        try {
            statement = connection.prepareStatement("SELECT * FROM `employee`");
            rs = statement.executeQuery();
            while (rs.next()) {
                employeeList.add(new Employee(rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getInt(5)));
            }
            readSkill();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void readSkill() {
        try {
            statement = connection.prepareStatement("SELECT * FROM `skill`");
            rs = statement.executeQuery();
            while (rs.next()) {
                employeeList.forEach(employee -> {
                    try {
                        if (rs.getString(1).equals(employee.getID())) {
                            employee.setNecklace(rs.getString(2));
                            employee.setEarrings(rs.getString(3));
                            employee.setRing(rs.getString(4));
                            employee.setBracelet(rs.getString(5));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void readBuyOrderList() {
        buyOrderList.clear();
        try {
            statement = connection.prepareStatement("SELECT * FROM `buyorder`");
            rs = statement.executeQuery();
            while (rs.next()) {
                buyOrderList.add(new BuyOrder(rs.getString(1), rs.getString(2),
                        rs.getInt(3), rs.getInt(4), rs.getDate(5),
                        rs.getDate(6), rs.getInt(7), rs.getInt(8)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void readWorkList(String str) {
        workList.clear();
        String query;
        String personClass;
        if (str.equals("admin")) {
            query = "SELECT * FROM `workorder`";
            personClass = "admin";
        } else {
            query = "SELECT * FROM `workorder` WHERE employeeID=\'" + str + "\'";
            personClass = "employee";
        }
        try {
            statement = connection.prepareStatement(query);
            rs = statement.executeQuery();
            ResultSet tempRS = rs;
            while (tempRS.next()) {
                Blob imageBlob = tempRS.getBlob(8);
                InputStream input = imageBlob.getBinaryStream();
                BufferedImage bufferedImage = ImageIO.read(input);
                String employeeName = getEmployeeName(tempRS.getString(2));
                String productName = getProductName(tempRS.getString(3));
                if (bufferedImage != null) {
                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                    workList.add(new WorkOrder(tempRS.getString(1), tempRS.getString(2),
                            tempRS.getString(1), tempRS.getString(4), tempRS.getDate(5),
                            tempRS.getDate(6), tempRS.getString(7), personClass, image,
                            employeeName, productName));
                } else {
                    workList.add(new WorkOrder(tempRS.getString(1), tempRS.getString(2),
                            tempRS.getString(1), tempRS.getString(4), tempRS.getDate(5),
                            tempRS.getDate(6), tempRS.getString(7), personClass, null,
                            employeeName, productName));
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void readProductList() {
        productList.clear();
        try {
            statement = connection.prepareStatement("SELECT * FROM `product`");
            rs = statement.executeQuery();
            while (rs.next()) {
                Blob imageBlob = rs.getBlob(6);
                InputStream input = imageBlob.getBinaryStream();
                BufferedImage bufferedImage = ImageIO.read(input);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);

                productList.add(new Product(rs.getString(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getInt(5), image));

                input.close();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void readReceiptList() {
        receiptList.clear();
        try {
            statement = connection.prepareStatement("SELECT * FROM `receipt`");
            rs = statement.executeQuery();
            while (rs.next()) {
                receiptList.add(new Receipt(rs.getString(1), rs.getInt(2),
                        rs.getDate(3), rs.getString(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createBuyOrder(String buyOrderID, String productID, int quantity, int price, Date localDate,
                                Date dueDate, int assignedNum, int status) {
        try {
            String query = "INSERT INTO `buyorder` (buyorderID, productID, Quantity, price, assignedDate, dueDate, assignednum, status)"
                    + " values (?, ?, ?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, buyOrderID);
            statement.setString(2, productID);
            statement.setInt(3, quantity);
            statement.setInt(4, price);
            statement.setDate(5, localDate);
            statement.setDate(6, dueDate);
            statement.setInt(7, assignedNum);
            statement.setInt(8, status);

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createWorkOrder(String workID, String from, String employeeID, String productID, Date localDate,
                                Date dueDate, String status) {
        try {
            String query = "INSERT INTO `workorder` (workID, employeeID, productID, buyorderID, assignedDate, dueDate"
                    + ", status, workpic) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, workID);
            statement.setString(2, employeeID);
            statement.setString(3, productID);
            statement.setString(4, from);
            statement.setDate(5, localDate);
            statement.setDate(6, dueDate);
            statement.setString(7, status);
            statement.setBlob(8, connection.createBlob());

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createReceipt(String receiptID, int quantity, int price, Date date, String buyOrderID) {
        int sumPrice =  quantity * price;
        try {
            String query = "INSERT INTO `receipt` (receiptID, totalprice, date, buyorderID) VALUES (?, ?, ?, ?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, receiptID);
            statement.setInt(2, sumPrice);
            statement.setDate(3, date);
            statement.setString(4, buyOrderID);

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addProduct(String id, String name, String description, String type, int rank, Image image, String fileType) {
        try {
            String query = "INSERT INTO `product` (productID, name, description, type, rank, productpic)"
                    + "VALUES (?, ?, ?, ?, ?, ?)";

            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
            ByteArrayOutputStream s = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, fileType, s);
            byte[] res = s.toByteArray();

            Blob blobImage = connection.createBlob();
            blobImage.setBytes(1, res);

            statement = connection.prepareStatement(query);
            statement.setString(1, id);
            statement.setString(2, name);
            statement.setString(3, description);
            statement.setString(4, type);
            statement.setInt(5, rank);
            statement.setBlob(6, blobImage);

            statement.execute();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void addEmployee(String id, String name, String phoneNum, String password) {
        try {
            String query = "INSERT INTO `employee` (employeeID, name, phone, password, NumOfWork)"
                    + "VALUES (?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, id);
            statement.setString(2, name);
            statement.setString(3, phoneNum);
            statement.setString(4, password);
            statement.setInt(5,0);

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addSkill(String id, int necklaceSkill, int earringsSkill, int braceletSkill, int ringSkill) {
        try {
            String query = "INSERT INTO `skill` (employeeID, necklace, earrings, ring, bracelet)"
                    + "VALUES (?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, id);
            statement.setInt(2, necklaceSkill);
            statement.setInt(3, earringsSkill);
            statement.setInt(4, ringSkill);
            statement.setInt(5, braceletSkill);

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBuyOrderAssigned(String orderID, String work) {
        try {
            if (work.equals("1")) {
                String query = "UPDATE `buyorder` SET assignednum = assignednum + 1 WHERE buyorderID = '" + orderID + "'";
                statement = connection.prepareStatement(query);
                statement.execute();
            } else if (work.equals("-1")) {
                String query = "UPDATE `buyorder` SET assignednum = assignednum - 1 WHERE buyorderID = '" + orderID + "'";
                statement = connection.prepareStatement(query);
                statement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBuyOrderStatus(String buyOrderID) {
        try {
            String query = "UPDATE `buyorder` SET status = status + 1 WHERE buyorderID = '" + buyOrderID + "'";
            statement = connection.prepareStatement(query);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateWorkOrder(String id, String status) {
        try {
            String query = "UPDATE `workorder` SET status = '" + status + "' WHERE workID = '" + id + "'";
            statement = connection.prepareStatement(query);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateWorkOrderPic(Image image, String fileType, String id) {
        try {
            String query = "UPDATE `workorder` SET workpic = ? WHERE workID = '" + id + "'";
            statement = connection.prepareStatement(query);

            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
            ByteArrayOutputStream s = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, fileType, s);
            byte[] res = s.toByteArray();

            Blob blobImage = connection.createBlob();
            blobImage.setBytes(1, res);

            statement.setBlob(1, blobImage);

            statement.execute();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void updateEmployee(String employeeID, String work) {
        try {
            if (work.equals("1")) {
                String query = "UPDATE `employee` SET NumOfWork = NumOfWork + 1 WHERE employeeID = '" + employeeID + "'";
                statement = connection.prepareStatement(query);
                statement.execute();
            } else if (work.equals("-1")) {
                String query = "UPDATE `employee` SET NumOfWork = NumOfWork - 1 WHERE employeeID = '" + employeeID + "'";
                statement = connection.prepareStatement(query);
                statement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteWorkOrder(String workID) {
        try {
            String query = "DELETE FROM `workorder` WHERE workID = '" + workID + "'";
            statement = connection.prepareStatement(query);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getEmployeeName(String employeeID) {
        try {
            String query = "SELECT `name` FROM `employee` WHERE employeeID = '" + employeeID + "'";
            statement = connection.prepareStatement(query);
            rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            } return "";
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getProductName(String productID) {
        try {
            String query = "SELECT `name` FROM `product` WHERE productID = '" + productID + "'";
            statement = connection.prepareStatement(query);
            rs = statement.executeQuery();
            if (rs.next()) {
                return capitalizeFirstLetter(rs.getString(1));
            } return "";
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }

    public ObservableList<Employee> getEmployeeList() {
        return employeeList;
    }

    public ObservableList<BuyOrder> getBuyOrderList() {
        return buyOrderList;
    }

    public ObservableList<WorkOrder> getWorkList() {
        return workList;
    }

    public ObservableList<Product> getProductList() {
        return productList;
    }

    public ObservableList<Receipt> getReceiptList() {
        return receiptList;
    }

    public void setEmployeeList(ObservableList<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public void setBuyOrderList(ObservableList<BuyOrder> buyOrderList) {
        this.buyOrderList = buyOrderList;
    }

    public void setWorkList(ObservableList<WorkOrder> workList) {
        this.workList = workList;
    }

    public void setProductList(ObservableList<Product> productList) {
        this.productList = productList;
    }

    public void setReceiptList(ObservableList<Receipt> receiptList) {
        this.receiptList = receiptList;
    }

    public Connection getConnection() {
        return connection;
    }

    public String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }

        String[] words = original.split(" ");
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].substring(0, 1).toUpperCase() + words[i].substring(1).toLowerCase();
        }
        return String.join(" ", words);
    }
}