package TeamManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import com.mysql.jdbc.ResultSet;

public class TimeLine {
	private int taskid;
	private Timestamp updated;
	private Timestamp s_poc;
	private Timestamp e_poc;
	private Timestamp s_inprogress;
	private Timestamp e_inprogress;
	private Timestamp s_underreview;
	private Timestamp e_underreview;
	private Timestamp s_testing;
	private Timestamp e_testing;
	private Timestamp s_released;
	private Timestamp e_released;
	Connection con=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	String URL="jdbc:mysql://localhost:3306/task_system";
	public TimeLine(int id)
	{

		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection(URL,"root","sathya97#");
			ps=con.prepareStatement("insert into timeline(taskid)"+ "values (?)");
			ps.setLong(1,id);
			ps.executeUpdate();
			System.out.println("timeline created");
			ps.close();
			con.close();
	    }
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
  public TimeLine(String id)
	{
		load(id);
	}
	public TimeLine() {
}

	public void load(String id)
	{
		try
		{ 
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection(URL,"root","sathya97#");
			ps=con.prepareStatement("select * from timeline where taskid=?");
			ps.setString(1,id);
			rs=(ResultSet) ps.executeQuery();
			while(rs.next())
			{
			taskid=Integer.parseInt(rs.getString(1));                                   
		    updated=rs.getTimestamp(2);
		    s_poc=rs.getTimestamp(3);
		    e_poc=rs.getTimestamp(4);
		    s_inprogress=rs.getTimestamp(5);
		    e_inprogress=rs.getTimestamp(6);
		    s_underreview=rs.getTimestamp(7);
		    e_underreview=rs.getTimestamp(8);
		    s_testing=rs.getTimestamp(9);
		    e_testing=rs.getTimestamp(10);
		    s_released=rs.getTimestamp(11);
		    e_released=rs.getTimestamp(12);
			}
			ps.close();
			con.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}	
	}
	public void getTimeLineDetails() {
		System.out.println("Task ID:"+taskid+"\nLast Updated:"+updated+"\nStart POC:"+s_poc+"\nEnd POC:"+e_poc+"\nStart InProgress:"+s_inprogress+"\nEnd InProgress:"+e_inprogress+"\nStart UnderReview:"+ s_underreview+"\nEnd UnderReview:"+ e_underreview+"\nStart Testing:"+s_testing+"\nEnd Testing:"+e_testing+"\nStart Released:"+s_released+"\nEnd Released:"+e_released);
	}

	public void updateStatus(String status) {
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection(URL,"root","sathya97#");
			if(status=="s_poc")
			    ps=con.prepareStatement("update timeline set s_poc=now() where taskid=?  and s_poc is NULL");
			else if(status=="e_poc")
			    ps=con.prepareStatement("update timeline set e_poc=now() where taskid=?  and e_poc is NULL");
			else if(status=="s_inprogress")
			    ps=con.prepareStatement("update timeline set s_inprogress=now() where taskid=? and s_inprogress is NULL");
			else if(status=="e_inprogress")
			    ps=con.prepareStatement("update timeline set e_inprogress=now() where taskid=? and e_inprogress is NULL");
			else if(status=="s_underreview")
				ps=con.prepareStatement("update timeline set s_underreview=now() where taskid=? and s_underreview is NULL");
			else if(status=="e_underreview")
				ps=con.prepareStatement("update timeline set e_underreview=now() where taskid=? and e_underreview is NULL");
			else if(status=="s_testing")
				ps=con.prepareStatement("update timeline set s_testing=now() where taskid=? and s_testing is NULL");
			else if(status=="e_testing")
				ps=con.prepareStatement("update timeline set e_testing=now() where taskid=? and e_testing is NULL");
			else if(status=="s_released")
				ps=con.prepareStatement("update timeline set s_released=now() where taskid=? and s_released is NULL");
			else if(status=="e_released")
				ps=con.prepareStatement("update timeline set e_released=now() where taskid=? and e_released is NULL");
			else
				System.out.println("Enter valid status");
			ps.setLong(1,taskid);
			ps.executeUpdate();
			System.out.println("Status Changed\nTimeline Updated");
			ps.close();
			con.close();
	    }
		catch(Exception e)
		{
			System.out.println(e);
		}		
	}

	public void viewTeamTimeLine(int taskid, int tid) {
		try
		{ 
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection(URL,"root","sathya97#");
			ps=con.prepareStatement("select * from timeline where taskid in(select taskid from task where taskid=? and mid in(select mid from member where tid=?))");
			ps.setInt(1,taskid);
			ps.setInt(2, tid);
			rs=(ResultSet) ps.executeQuery();
			while(rs.next())
			{
			taskid=Integer.parseInt(rs.getString(1));                                   
		    updated=rs.getTimestamp(2);
		    s_poc=rs.getTimestamp(3);
		    e_poc=rs.getTimestamp(4);
		    s_inprogress=rs.getTimestamp(5);
		    e_inprogress=rs.getTimestamp(6);
		    s_underreview=rs.getTimestamp(7);
		    e_underreview=rs.getTimestamp(8);
		    s_testing=rs.getTimestamp(9);
		    e_testing=rs.getTimestamp(10);
		    s_released=rs.getTimestamp(11);
		    e_released=rs.getTimestamp(12);
			}
			ps.close();
			con.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}	
		getTimeLineDetails();
	}
	public void viewTTimeLine(int tid) {
		try
		{ 
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection(URL,"root","sathya97#");
			ps=con.prepareStatement("select t.taskid,tk.curnt_status,m.mname,m.tid,tk.created_at,tk.finisht_at from timeline t INNER JOIN task tk on t.taskid= tk.taskid INNER JOIN member m on m.mid=tk.mid where m.tid=?");
			ps.setInt(1, tid);
			rs=(ResultSet) ps.executeQuery();
			while(rs.next())
			{
			System.out.println(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getTimestamp(5)+" "+rs.getTimestamp(6)+" ");
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
