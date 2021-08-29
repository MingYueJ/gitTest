import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class TestLoginPreparedStatement {
    /*
     * 用户登录的验证：在数据库中创建用户表user,并向表中输入一条记录
     * 1.在程序中输入用户名和密码，与数据库中的用户信息比较
     * 2.创建Properties集合
     * 3.创建输入流的对象
     * 4.把输入流中的数据加载到Properties集合中
     * 5.创建并注册驱动对象：Driver，
     *  通过getProperty()方法获取配置文件中的数据
     * 6.获取连接对象：Connection，
     *  通过getProperty()方法获取配置文件中的数据
     * 7.创建执行SQL语句的操作对象 ：PreparedStatement
     *   使用?占位预编译SQL语句
     * 8.给占位符赋值 占位符是有下标的，下标从1开始，setXXX(下标,值)
     * 9.调用PreparedStatement的方法，执行相应的SQL语句
     *   执行查询 ： ResultSet executeQuery()
     * 10.关闭资源 ：ResultSet->Statement->Connection
     * -----------------------------------------------
     * 使用PreparedStatement -> 预编译SQL对象
     * Statement和PreparedStatement的区别主要有以下三点：
     * 1.Statement是先进行SQL语句拼接，
     *   再进行SQL语句编译，存在SQL注入问题；
     * 2.PreparedStatement是预先编译SQL语句框架
     *   (带有占位符的SQL语句)，
     *  然后再给占位符赋值，能够防止SQL注入。
     * 3.Statement编译一次，执行一次，编译一次，执行一次。
     *  而PreparedStatement是编译一次框架，执行N次，
     *  所以PreparedStatement效率较高。
     *  综上所述，PreparedStatement使用较多。
     */
    private static String userName="";
    private static String userPWD="";

    public static void main(String[] args) {
        //1.在程序中输入用户名和密码，与数据库中的用户信息比较
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入用户名：");
        userName=sc.nextLine();
        System.out.println("请输入密码：");
        userPWD=sc.nextLine();
        boolean b=login(userName, userPWD);
        System.out.println(b?"登录成功":"登录失败");
    }

    public static boolean login(String userName,String userPWD){
        //2.创建Properties集合
        Properties pro=new Properties();
        InputStream in=null;
        try {
            //3.创建输入流的对象
            in=new FileInputStream("jdbc.properties");
            //4.把输入流中的数据加载到Properties集合中
            pro.load(in);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            try {
                if(in!=null) in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        Connection conn=null;
        PreparedStatement pst=null;
        ResultSet rs=null;
        try {
            //5.创建并注册驱动对象：Driver，
//通过getProperty()方法获取配置文件中的数据
            Class.forName(pro.getProperty("driver"));

//6.获取连接对象：Connection，
//通过getProperty()方法获取配置文件中的数据
            conn= DriverManager.getConnection(pro.getProperty("url"), pro);

//7.创建执行SQL语句的操作对象 ：PreparedStatement
            //使用?占位预编译SQL语句
            String sql="select userName from user where userName=? and userPWD=?";
            pst=conn.prepareStatement(sql);

//8.给占位符赋值 占位符是有下标的，
//下标从1开始，setXXX(下标,值)
            pst.setString(1,userName);
            pst.setString(2,userPWD);
            //System.out.println("sql="+sql);

//9.调用PreparedStatement的方法，执行相应的SQL语句
            //执行查询 ： ResultSet executeQuery()
            rs=pst.executeQuery();//此处没有参数，否则引发异常
            if(rs.next()){
                return true;
            }else{
                return false;
            }
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("登录过程中发生错误");
            e.printStackTrace();
        }finally {
            try {
                //9.关闭资源 ：ResultSet->Statement->Connection
                if(rs!=null) rs.close();
                if(pst!=null) pst.close();
                if(conn!=null) conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return false;
    }
}