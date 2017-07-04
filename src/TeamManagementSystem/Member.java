package TeamManagementSystem;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Member {
	
	private int mid;
	private String mname;
	private int tid;
	public boolean login;
	Connection con=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	String URL="jdbc:mysql://localhost:3306/task_system";
	public Member(int mid,String mname,int tid)
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection(URL,"root","sathya97#");
			ps=con.prepareStatement("insert into member values (?,?,?)");
			ps.setLong(1,mid);
			ps.setString(2,mname);
			ps.setLong(3,tid);
			ps.executeUpdate();
			System.out.println("New Member Added");
			ps.close();
			con.close();
	    }
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	public Member(int id,String name) throws SQLException
	{
		if(login(id,name))
		{
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				con=DriverManager.getConnection(URL,"root","sathya97#");
				ps=con.prepareStatement("select * from member where mid=?");
				ps.setLong(1,id);
				rs=(ResultSet) ps.executeQuery();
				while(rs.next())
				{
				mid=Integer.parseInt(rs.getString(1));                                   
				mname=rs.getString(2);
				tid=Integer.parseInt(rs.getString(3));
				}
				ps.close();
				con.close();
			}
			catch(Exception e)
			{
				System.out.println(e);
			}	
		}
		
	}
	
		public boolean login(int id,String name) throws SQLException
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection(URL,"root","sathya97#");
			ps=con.prepareStatement("select * from member where mid=? and mname=?");
			ps.setLong(1,id);
			ps.setString(2,name);
			rs=(ResultSet) ps.executeQuery();
	    }
		catch(Exception e)
		{
			System.out.println(e);
		}
		if(rs.next())
		{
			login=true;
			ps.close();
			con.close();
			return true;
		}
		else
		{
			login=false;
			System.out.println("Login failed");
			ps.close();
			con.close();
			return false;
		}

	}
	
	public void getMemberDetails() {
		System.out.println(mid+" "+mname+" "+tid);	
	}
	public void taskList()
	{
		Task task=new Task();
	  task.getTasksForMember(mid);
	}
	public void updateTaskStatus(int taskid,String status) throws SQLException
	{
		Task task=new Task(""+taskid,mid);
		task.updateStatus(status);
	}
	public void taskDetails(int taskid) throws SQLException
	{
	Task task=new Task(""+taskid,mid);
	if(task.login)
	task.getTaskDetails();
	else
	{
		System.out.println("Select your task from:");
		task.getTasksForMember(mid);
	}
	}
	public String getCurrentStatus(int taskid) throws SQLException {
		Task task=new Task(""+taskid,mid);
	return	task.getTaskStatus();
	}
	public void viewTimeLine(int taskid) throws SQLException {
		Task task=new Task(""+taskid,mid);
		if(task.login)
			task.viewTimeLine();
			else
			{
				System.out.println("Select your task from:");
				task.getTasksForMember(mid);
			}
	}
	public void viewReport() throws IOException {
		@SuppressWarnings("unused")
		Report report=new Report(mid,"member","weekly");
	}
	public boolean check(int taskid) throws SQLException {
		Task task=new Task(""+taskid,mid);
		return task.checkTask();
	}

}