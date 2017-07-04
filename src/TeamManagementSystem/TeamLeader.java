package TeamManagementSystem;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.mysql.jdbc.ResultSet;

public class TeamLeader extends Member {
	int tid;
	int mid;
	int pswd;
	public boolean teamlogin;
	Connection con=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	String URL="jdbc:mysql://localhost:3306/task_system";
	public TeamLeader(int tid,int mid,int leadPswd,String mname)
	{
		super(mid,mname,tid);
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection(URL,"root","sathya97#");
			ps=con.prepareStatement("insert into teamleader values (?,?,?)");
			ps.setLong(1,tid);
			ps.setLong(2,mid);
			ps.setLong(3,leadPswd);
			ps.executeUpdate();
			System.out.println("Team Leader Assigned");
			ps.close();
			con.close();
	    }
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	public TeamLeader(int id, int pswd, String name) throws SQLException {
		super(id,name);
		if(login(id,pswd))
		{
		load(id);	
		}
	}
	public boolean login(int mid,int pswd) throws SQLException
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/task_system","root","sathya97#");
			ps=con.prepareStatement("select * from teamleader where mid=? and pswd=?");
			ps.setLong(1,mid);
			ps.setLong(2,pswd);
			rs=(ResultSet) ps.executeQuery();
	    }
		catch(Exception e)
		{
			System.out.println(e);
		}
		if(rs.next())
		{
			teamlogin=true;
			ps.close();
			con.close();
			return true;
		}
		else
		{
			teamlogin=false;
			System.out.println("Login failed");
			ps.close();
			con.close();
			return false;
		}

	}
	public void load(int id)
	{
		try
		{ 
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/task_system","root","sathya97#");
			ps=con.prepareStatement("select * from teamleader where mid=?");
			ps.setLong(1,id);
			rs=(ResultSet) ps.executeQuery();
			while(rs.next())
			{
			tid=Integer.parseInt(rs.getString(1));                                   
			mid=Integer.parseInt(rs.getString(2)); 
			pswd=Integer.parseInt(rs.getString(3));
			}
			ps.close();
			con.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}	
	}
	public void getTeamLeaderDetails() {
		System.out.println(tid+" "+mid+" "+pswd);	
	}
	
	public void createTask(int taskid,int memberid)
	{
      @SuppressWarnings("unused")
	Task task=new Task(taskid,memberid);
	}
	
	public void getMemberTaskList() {
		Task task=new Task();
	    task.getTaskList(tid);
	}

	public void teamTaskDetails(int taskid) {
		Task task=new Task();
		task.getTeamTaskDetails(taskid,tid);
	}

	public void viewTeamTimeLine(int taskid) {
		Task task=new Task();
		task.getTeamTimeLine(taskid,tid);
	}

	public void viewTeamReport(String type) throws IOException {
		@SuppressWarnings("unused")
		Report report=new Report(tid,"team",type);
	}
	public boolean verifyMember(int mid2) throws SQLException {
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection(URL,"root","sathya97#");
			ps=con.prepareStatement("select * from member where mid=? and tid=?");
			ps.setLong(1,mid2);
			ps.setLong(2,tid);
			rs=(ResultSet) ps.executeQuery();
	    }
		catch(Exception e)
		{
			System.out.println(e);
		}
		if(rs.next())
		{
			ps.close();
			con.close();
			return true;
		}
		else
		{
			ps.close();
			con.close();
			return false;
		}
		}

	public void memberList() {
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection(URL,"root","sathya97#");
			ps=con.prepareStatement("select mid from member where tid=?");
			ps.setLong(1,tid);
			rs=(ResultSet) ps.executeQuery();
			while(rs.next())
			{
			System.out.println("Member ID:"+rs.getString(1));                                   
			
			}
			ps.close();
			con.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}	
		
	}

	public void viewTTimeLine() {
TimeLine t=new TimeLine();
t.viewTTimeLine(tid);

		
	}


}
