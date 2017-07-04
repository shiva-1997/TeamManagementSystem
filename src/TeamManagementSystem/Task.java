package TeamManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import com.mysql.jdbc.ResultSet;

public class Task {
private int taskid;
private int mid;
private String current_status;
TimeLine timeLine;
Timestamp  created_at;
Timestamp  finisht_at;
public boolean login;
Connection con=null;
PreparedStatement ps=null;
ResultSet rs=null;
String URL="jdbc:mysql://localhost:3306/task_system";
public Task(int id,int mid)
{
	try
	{
		Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection(URL,"root","sathya97#");
		ps=con.prepareStatement("insert into task(taskid,mid)"+ "values (?,?)");
		ps.setLong(1,id);
		ps.setLong(2,mid);
		ps.executeUpdate();
		System.out.println("task created");
		ps.close();
	    
		@SuppressWarnings("unused")
		TimeLine create=new TimeLine(id);
    }
	catch(Exception e)
	{
		System.out.println(e);
	}
}
public Task(String id,int memberid) throws SQLException
{
	if(check(id,memberid))
	{
      load(id);
      timeLine=new TimeLine(id);
      }
}
public Task()
{
	
}
public boolean check(String id,int memberid) throws SQLException
{
	try
	{
		Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection(URL,"root","sathya97#");
		ps=con.prepareStatement("select * from task where taskid=? and mid=?");
		ps.setString(1,id);
		ps.setLong(2,memberid);
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
		ps.close();
		con.close();
		return false;
	}

}
public void load(String id)
{
	try
	{ 
		Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection(URL,"root","sathya97#");
		ps=con.prepareStatement("select * from task where taskid=?");
		ps.setString(1,id);
		rs=(ResultSet) ps.executeQuery();
		while(rs.next())
		{
		taskid=Integer.parseInt(rs.getString(1));                                   
		mid=Integer.parseInt(rs.getString(2)); 
		current_status=rs.getString(3);
		created_at=rs.getTimestamp(4);
		if(rs.getTimestamp(5)!=null)
		finisht_at=rs.getTimestamp(5);
		}
		ps.close();
		con.close();
	}
	catch(Exception e)
	{
		System.out.println(e);
	}	
}
public void getTaskDetails() {
		System.out.println("Task ID:"+taskid+"\nMemberID:"+mid+"\nCurrent Status:"+current_status+"\nCreated At:"+created_at+"\nFinisht At:"+finisht_at);
}
public void getTasksForMember(int id)
{
	try
	{ 
		Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection(URL,"root","sathya97#");
		ps=con.prepareStatement("select taskid from task where mid=?");
		ps.setLong(1,id);
		rs=(ResultSet) ps.executeQuery();
		while(rs.next())
		{
			System.out.println("Task:"+rs.getString(1));
		}
		ps.close();
		con.close();
	}
	catch(Exception e)
	{
		System.out.println(e);
	}	
}

public void updateStatus(String status) {
	current_status=status;
	try
	{
		Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection(URL,"root","sathya97#");
		ps=con.prepareStatement("update task set curnt_status=? where taskid=? and mid=?");
		ps.setString(1, status);
		ps.setInt(2, taskid);
		ps.setInt(3,mid);
		ps.executeUpdate();
		ps.close();
		con.close();
    }
	catch(Exception e)
	{
		System.out.println(e);
	}
	if(status=="e_released")
	{
		try
	{
		Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection(URL,"root","sathya97#");
		ps=con.prepareStatement("update task set finisht_at=now() where taskid=?");
		ps.setInt(1, taskid);
		ps.executeUpdate();
		System.out.println("task finisht");
		ps.close();
		con.close();
    }
	catch(Exception e)
	{
		System.out.println(e);
	}
	}
	timeLine.updateStatus(status);
}
public String getTaskStatus() {
	return current_status;
}
public void viewTimeLine() {
timeLine.getTimeLineDetails();
	
}
public void getTaskList(int tid) {
	try
	{ 
		Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection(URL,"root","sathya97#");
		ps=con.prepareStatement("select taskid,mid from task where mid in (select mid from member where tid=?)");
		ps.setInt(1,tid);
		rs=(ResultSet) ps.executeQuery();
		while(rs.next())
		{
			System.out.println("Task ID:"+Integer.parseInt(rs.getString(1))+"  Member ID:"+Integer.parseInt(rs.getString(2)));  
				}
		ps.close();
		con.close();
	}
	catch(Exception e)
	{
		System.out.println(e);
	}	
}
public void getTeamTaskDetails(int id,int teamid) {

	try
	{ 
		Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection(URL,"root","sathya97#");
		ps=con.prepareStatement("select * from task where taskid=? and mid in(select mid from member where tid=?)");
		ps.setLong(1,id);
		ps.setInt(2, teamid);
		rs=(ResultSet) ps.executeQuery();
		while(rs.next())
		{
			System.out.println("Task ID:"+rs.getString(1));
			System.out.println("Member ID:"+rs.getString(2));
			System.out.println("Current Status:"+rs.getString(3));
			System.out.println("Created At:"+rs.getTimestamp(4));
			System.out.println("Finisht At:"+rs.getTimestamp(5));
		}
		ps.close();
		con.close();
	}
	catch(Exception e)
	{
		System.out.println(e);
	}	
}
public void getTeamTimeLine(int taskid, int tid) {
	TimeLine time=new TimeLine();
	time.viewTeamTimeLine(taskid,tid);
}
public String getTaskReport(int id,String type,String reportFor) {
	StringBuffer report=new StringBuffer();
	try
	{ 
		Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection(URL,"root","sathya97#");
		if(type=="weekly"&&reportFor=="member")
		ps=con.prepareStatement("select taskid,curnt_status,created_at,finisht_at from task where mid=? AND (created_at >= DATE_SUB(CURDATE(),INTERVAL 7 DAY) AND created_at < CURDATE())");
		else if(type=="weekly"&&reportFor=="team")
        ps=con.prepareStatement(" select taskid,mid,curnt_status,created_at,finisht_at from task where mid in (select mid from member where tid=?) AND (created_at >= DATE_SUB(CURDATE(),INTERVAL 7 DAY) AND created_at < CURDATE()) ORDER BY mid,taskid");
		else if(type=="monthly"&&reportFor=="team")
	    ps=con.prepareStatement("select taskid,mid,curnt_status,created_at,finisht_at from task where mid in (select mid from member where tid=?) AND (created_at >= DATE_SUB(CURDATE(),INTERVAL DAYOFMONTH(CURDATE())-1 DAY) AND created_at < CURDATE()) ORDER BY mid,taskid");
		ps.setLong(1,id);
		rs=(ResultSet) ps.executeQuery();
		while(rs.next())
		{
			if(reportFor=="member")
			report.append("\nTask ID:"+rs.getString(1)+"\tTask Status:"+rs.getString(2)+"\tCreated At:"+rs.getTimestamp(3)+"\tFinisht At:"+rs.getString(4));
			else if(reportFor=="team")
				report.append("\nTask ID:"+rs.getString(1)+"\tMember ID:"+rs.getString(2)+"\tTask Status:"+rs.getString(3)+"\tCreated At:"+rs.getTimestamp(4)+"\tFinisht At:"+rs.getString(5));
		}
		ps.close();
		con.close();
	}
	catch(Exception e)
	{
		System.out.println(e);
		e.printStackTrace();
	}
	return report.toString();	
}
public boolean checkTask() {
	return login;
}

}
