import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Test02 {
    /*
     * JDBC编程五步：使用java.sql包中的接口
     * 1.创建并注册驱动对象：Driver
     * 2.获取连接对象：Connection
     * 3.创建执行SQL语句的操作对象 ：Statement
     * 4.调用Statement的方法，执行相应的SQL语句
     * 	  执行查询 ： ResultSet executeQuery(String sql)
     * 	  执行增删改： int executeUpdate(String sql)
     * 5.关闭资源 ：Statement->Connection
     * ----------------------------
     * 向员工表中添加一个新员工
     * 录入员工的：员工编号，员工姓名，员工职位，员工工资，员工部门编号
     */
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入员工编号：");
        int empno=sc.nextInt();
        System.out.println("请输入员工姓名：");
        String ename=sc.next();
        System.out.println("请输入员工职位：");
        String job=sc.next();
        System.out.println("请输入员工工资：");
        double sal=sc.nextDouble();
        System.out.println("请输入员工部门编号：");
        int deptno=sc.nextInt();

        Connection conn=null;
        Statement stat=null;
        try {
            //1.创建并注册驱动对象：Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2.获取连接对象：Connection
            String url = "jdbc:mysql://localhost:3306/test?&useSSL=false&serverTimezone=UTC";
            String user="root";
            String password="123456";
            conn= DriverManager.getConnection(url, user, password);
            //3.创建执行SQL语句的操作对象 ：Statement
            stat=conn.createStatement();
            //4.调用Statement的方法，执行相应的SQL语句
            //执行增删改： int executeUpdate(String sql)
            String sql="insert into emp(empno,ename,job,sal,deptno) "
                    +"values("+empno+",'"+ename+"','"
                    +job+"',"+sal+","+deptno+")";
            int count=stat.executeUpdate(sql);

            if(count>0){
                System.out.println("添加成功");
            }
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("添加记录发生错误");
            e.printStackTrace();
        }finally {
            //5.关闭资源 ：Statement->Connection
            try {
                if(stat!=null) {
                    stat.close();
                }
                if(conn!=null){
                    conn.close();
                }
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}