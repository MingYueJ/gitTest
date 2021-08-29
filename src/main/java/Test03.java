import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class Test03 {
    /*
     * 读取配置文件中数据，实现数据库的连接操作
     * IO+Properties
     * 1.创建配置文件  XXX.properties
     * 2.创建输入流的对象
     * 3.把输入流中的数据加载到Properties集合中
     * 4.实现更新指定员工编号的员工的姓名
     * 5.创建并注册驱动对象：Driver，通过getProperty()方法获取配置文件中的数据
     * 6.获取连接对象：Connection，通过getProperty()方法获取配置文件中的数据
     * 7.创建执行SQL语句的操作对象 ：Statement
     * 8.调用Statement的方法，执行相应的SQL语句
     *   执行增删改： int executeUpdate(String sql)
     * 9.关闭资源 ：Statement->Connection
     */
    public static void main(String[] args) {
        //1.创建Properties集合
        Properties pro=new Properties();
        InputStream in=null;
        try {
            //2.创建输入流的对象
            in=new FileInputStream("jdbc.properties");
            //3.把输入流中的数据加载到Properties集合中
            pro.load(in);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            try {
                in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //4.实现更新指定员工编号的员工的姓名
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入员工编号：");
        int empno=sc.nextInt();
        System.out.println("请输入员工的新名字：");
        String new_ename=sc.next();

        Connection conn=null;
        Statement stat=null;
        try {
            //5.创建并注册驱动对象：Driver，
//通过getProperty()方法获取配置文件中的数据
            Class.forName(pro.getProperty("driver"));

//6.获取连接对象：Connection，
//通过getProperty()方法获取配置文件中的数据
            conn= DriverManager.getConnection(pro.getProperty("url"),               pro.getProperty("user"), pro.getProperty("password"));

            //7.创建执行SQL语句的操作对象 ：Statement
            stat=conn.createStatement();

//8.调用Statement的方法，执行相应的SQL语句
            //执行增删改： int executeUpdate(String sql)
            String sql="update emp set ename='"+new_ename
                    +"' where empno="+empno;
            int count=stat.executeUpdate(sql);

            if(count>0){
                System.out.println("更新成功");
            }
        }catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("更新表中记录时发生错误");
            e.printStackTrace();
        }finally {
            try {
                //9.关闭资源 ：Statement->Connection
                if(stat!=null) {
                    stat.close();
                }
                if(conn!=null) {
                    conn.close();
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}