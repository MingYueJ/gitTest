import java.sql.*;

public class Test01 {
    /*
     * JDBC编程六步：使用java.sql包中的接口
     * 1.创建并注册驱动对象：Driver
     * 2.获取连接对象：Connection
     * 3.创建执行SQL语句的操作对象 ：Statement
     * 4.调用Statement的方法，执行相应的SQL语句
     * 	 执行查询 ： ResultSet executeQuery(String sql)
     * 	 执行增删改： int executeUpdate(String sql)
     * 5.获取结果集(ResultSet)中的数据
     * 6.关闭资源 ：ResultSet->Statement->Connection
     * ----------------------------
     * 查询员工的信息(员工编号，员工姓名，员工职位，员工工资)
     */
    public static void main(String[] args) {
        Connection conn=null;
        Statement stat=null;
        ResultSet rs=null;
        try {
            //1.注册驱动对象
            //创建驱动对象
            Driver driver=new com.mysql.cj.jdbc.Driver();
            //注册驱动对象
            DriverManager.registerDriver(driver);
            //以上1的创建+注册=Class.forName(com.mysql.jdbc.Driver)
            //2.获取连接对象
          //MySQL的连接URL编写方式：
//jdbc:mysql://主机名称:mysql服务端口号/数据库名称?参数=值&参数=值

            String url="jdbc:mysql://localhost:3306/test?&useSSL=false&serverTimezone=UTC";
            String user="root";
            String password="123";
            conn= DriverManager.getConnection(url, user, password);

            //3.创建执行SQL语句的操作对象 ：Statement
            stat=conn.createStatement();

            //4.调用Statement的方法，执行相应的SQL语句
            //注意在SQL语句字符串中不能在结尾处写分号;
            rs=stat.executeQuery("select empno,ename,job,sal from lemp");

            //5.获取结果集(ResultSet)中的数据
            //一行一行的向下读取，第一次读取之前，光标(指针)在第一行上面
            //boolean  rs.next()
            System.out.println("员工编号\t员工姓名\t员工职位\t\t员工工资");
            while(rs.next()){//先读取每一行的值
                //再读取每一行中每一列的值
                //方法一：getXXX(int columnIndex)  按列的索引读取，从1开始
                //方法二：getXXX(String columnName) 按列的名称读取
			 /*
				System.out.print(rs.getInt(1)+"\t");
				System.out.print(rs.getString(2)+"\t");
				System.out.print(rs.getString(3)+"\t\t");
				System.out.print(rs.getDouble(4)+"\n");
			 */
                System.out.print(rs.getInt("empno")+"\t");
                System.out.print(rs.getString("ename")+"\t");
                System.out.print(rs.getString("job")+"\t\t  ");
                System.out.print(rs.getDouble("sal")+"\n");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            //6.关闭资源
            try {
                if(rs!=null) {
                    rs.close();
                }
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