import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class TestjdbcInsert {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //1.加载注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        //2.获取连接对象ur1、 user、password
        //url:jdbc : mysql://主机名称:mysql服务端口号/数据库名称
        String url = "jdbc:mysql://localhost:3306/test?&useSSL=false&serverTimezone=UTC";
        //String url = "jdbc:mysq1://loca1host:3306/test";
        String user = "root";
        String password = "123456";
        Connection conn = DriverManager.getConnection(url, user, password);//3.连接对象创建执行sQL语句的操作对象
        Statement state = conn.createStatement();//4.操作对象执行sql
        //sql:语句
        String sql = "select ename,sal from lemp";//查看操作返回结果集
        ResultSet resu = state.executeQuery(sql);//5.遍历查看结果集
        while (resu.next()) {
            System.out.print(resu.getString("ename"));
            System.out.print("  ");
            //System.out.print(resu.getString("sal"));
            System.out.println(resu.getDouble(2));
        }

        int i = state.executeUpdate("insert into lemp(ename, sal) values('柯二十二', 2222)");
        if (i > 0) {
            System.out.println("插入成功");
        }//插入操作

        int j = state.executeUpdate("update lemp set ename='柯22' where sal=2222");
        if (j > 0) {
            System.out.println("修改成功");
        }//修改操作

        int k = state.executeUpdate(" delete from lemp where sal=2222");
        if (k > 0) {
            System.out.println("删除成功");
        }//删除操作


//6.关闭资源
        resu.close();
        state.close();
        conn.close();
    }
}