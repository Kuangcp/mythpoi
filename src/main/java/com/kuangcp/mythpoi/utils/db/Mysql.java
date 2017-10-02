package com.kuangcp.mythpoi.utils.db;

/**
 * Created by https://github.com/kuangcp on 17-10-1  上午10:34
 */

import com.kuangcp.mythpoi.utils.config.Config;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 切记要导JAR驱动包，还有配置好URL
 * @author Myth on 2016年7月24日
 */
public class Mysql{
    private static int count = 0;
    private PreparedStatement ps = null;
    private Connection cn = null;
    private ResultSet rs = null;
    private String Driver;
    private StringBuilder URL=new StringBuilder();

    /**
     * 手动设置链接数据的属性
     * @param database 数据库
     * @param username 用户
     * @param port 端口号
     * @param password 密码
     */
    public Mysql(String database,String port,String username,String password){
        Config con = new Config("/mysql.properties");
        this.Driver = con.getString("Driver");
        this.URL.append("jdbc:mysql://localhost:").append(port).append("/").append(database).append("?user=")
                .append(username).append("&password=").append(password).append("&userUnicode=true&characterEncoding=UTF8");
//		this.URL="jdbc:mysql://localhost:3306/"+db+"?user="+user+"&password="+pass+"&userUnicode=true&characterEncoding=UTF8";
    }
    /**
     * 采用配置文件的默认配置
     */
    public Mysql(){
        Config con = new Config("/mysql.properties");
        this.Driver = con.getString("Driver");
        String database = con.getString("database");
        String username = con.getString("username");
        String password = con.getString("password");
        String port = con.getString("port");
        this.URL.append("jdbc:mysql://localhost:").append(port).append("/").append(database).append("?user=")
                .append(username).append("&password=").append(password).append("&userUnicode=true&characterEncoding=UTF8");
    }
    /**获取数据库连接*/
    public Connection getConnection(){
        try {
            Class.forName(Driver);
            cn = DriverManager.getConnection(URL.toString());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("获取连接，异常！");
        }
        return cn;
    }

    /**查询全部的操作 返回值是ResultSet 切记使用完后要finally关闭*/
    public ResultSet queryBySQL(String sql){
        count++;
        try {
            cn = getConnection();
            ps=cn.prepareStatement(sql);
            rs=ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
//		finally {
        //不能关闭
//			this.closeAll();
//		}
        System.out.println("这是第"+count+"次查询操作");
        return rs;
    }
    /**
     * SQL查询并返回List集合
     * @param sql SQL 语句
     * @return List String数组 一行是一个String[] 按查询的字段顺序
     */
    public List<String []> queryReturnList(String sql){
        int Cols;
        List <String []> data = new ArrayList<>();
        ResultSet rs = queryBySQL(sql);
        try {
            Cols = rs.getMetaData().getColumnCount();//获取总列数
            while(rs.next()){
                //为什么放在while外面就会出现最后一组元素覆盖整个数组？
                String [] row = new String [Cols];
                for (int i=0;i<Cols;i++){
                    row[i] = rs.getString(i+1);
                }
                data.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            this.closeAll();
        }
        return data;
    }
    /**把增删改 合在一起 返回值是 布尔值
     * 各种连接已经关闭了不用再次关闭了
     * SQL只能输一句，不能多句运行
     * @param sql 执行的SQL
     * @return boolean 是否执行成功
     */
    public boolean executeUpdateSQL(String sql){
        boolean flag = true;
        try{
            cn = getConnection();
            ps=cn.prepareStatement(sql);
            int i=ps.executeUpdate();
            System.out.print("    增删改查成功_"+i+"_行受影响-->");
            if(i!=1){
                flag=false;
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("增删改查失败");
            flag=false;
        }finally {
            this.closeAll();
        }
        return flag;
    }
    /**
     * 插入多条数据并采用了事务
     * @param sqls SQL的String数组
     * @return boolean 是否成功
     * */
    public boolean batchInsertWithAffair(String [] sqls){
        boolean success = true;
        try{
            Class.forName(Driver);
            cn = DriverManager.getConnection(URL.toString());
            cn.setAutoCommit(false);//取消自动提交
            int i=0;
            for (String sql:sqls){
                i++;
                ps=cn.prepareStatement(sql);
                ps.addBatch();
//				System.out.println("第"+i+"条记录插入成功");
            }
            ps.executeBatch();
            System.out.println("批量操作无异常");
            cn.commit();//无异常再提交
        }catch(Exception e){
            success = false;
            try {
                cn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            System.out.println("增删改查失败");
        }finally {
            try {
                cn.setAutoCommit(true);//改回来
            } catch (SQLException e) {
                e.printStackTrace();
            }
            this.closeAll();
        }
        return success;
    }
    /**关闭数据库资源*/
    public void closeAll(){
        //关闭资源 后打开先关闭
        try {
            if(rs!=null) rs.close();
            if(ps!=null) ps.close();
            if(cn!=null) cn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("资源关闭异常");
        }
        System.out.println("正常-关闭资源");
    }
}