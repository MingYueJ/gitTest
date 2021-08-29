import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class TestjdbcSelect {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Properties pro = new Properties();
        FileInputStream in1 = null;
        try {
//2.创建输入流的对象
            //in1 = new FileInputStream("D:\\JAVA\\jdbc0630\\src\\main\\resources\\jdbc.properties");//3.把输入流中的数据加载到Properties集合中
            in1 = new FileInputStream("jdbc.properties");
            pro.load(in1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
//3.关闭
                in1.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Class.forName("com. mysql.cj.jdbc.Driver");//5.
        Connection conn = DriverManager.getConnection(pro.getProperty("url"), pro.getProperty("user"), pro.getProperty("password"));// 6.
        Statement state = conn.createStatement();//7.
        int i = state.executeUpdate("delete from lemp where empno=21");
        if (i > 0) {
            System.out.println("更新成功");
            state.close();
            conn.close();
        }
    }
}