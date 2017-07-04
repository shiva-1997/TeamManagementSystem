package TeamManagementSystem;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;


public class Company 
{
	int cid;
	String name;
	int pswd;
	ArrayList<Integer> teamList=new ArrayList<Integer>();
	Connection con=null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	public boolean login;
	String URL="jdbc:mysql://localhost:3306/task_system";
	public Company(int id,int pswd) throws SQLException
	{
		if(login(id,pswd))
		{
		load(id);	
		}
	}
public boolean login(int id,int pswd) throws SQLException
{
	try
	{
		Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection(URL,"root","sathya97#");
		ps=con.prepareStatement("select * from company where cid=? and pswd=?");
		ps.setLong(1,id);
		ps.setLong(2,pswd);
		rs=(ResultSet) ps.executeQuery();
    }
	catch(Exception e)
	{
		System.out.println(e);
	}
	if(rs.next())
	{
		login=true;
		System.out.println("Login successfull");
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
public void load(int id)
{
	try
	{ 
		Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection(URL,"root","sathya97#");
		ps=con.prepareStatement("select * from company where cid=?");
		ps.setLong(1,id);
		rs=(ResultSet) ps.executeQuery();
		while(rs.next())
		{
		cid=Integer.parseInt(rs.getString(1));                                   
		name=rs.getString(2);
		pswd=Integer.parseInt(rs.getString(3));
		}
		System.out.println("company loaded\n Welcome "+name);
		ps.close();
		con.close();
	}
	catch(Exception e)
	{
		System.out.println(e);
	}	
	loadTeam(id);
}
public void loadTeam(int id)
{
	try
	{ 
		Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection(URL,"root","sathya97#");
		ps=con.prepareStatement("select * from team where cid=?");
		ps.setLong(1,id);
		rs=(ResultSet) ps.executeQuery();
		while(rs.next())
		{
		teamList.add(Integer.parseInt(rs.getString(2)));
		}
		ps.close();
		con.close();
	}
	catch(Exception e)
	{
		System.out.println(e);
	}	
	
}
public void addTeam(int tid)
{
	try
	{
		Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection(URL,"root","sathya97#");
		ps=con.prepareStatement("insert into team values (?,?)");
		ps.setLong(1,cid);
		ps.setLong(2,tid);
		ps.executeUpdate();
		System.out.println("Team added");
		teamList.add(tid);
		ps.close();
		con.close();
    }
	catch(Exception e)
	{
		System.out.println(e);
	}
	
}
public void getTeams()
{
	Iterator<Integer> iterator = teamList.iterator();
	while(iterator.hasNext()){
System.out.println("Team:"+iterator.next());	
	}
}
public void getCompanyDetails() {
	System.out.println(cid+" "+name+" "+pswd);	
	getTeams();
}
public void addMember(int mid,String mname,int tid)
{
@SuppressWarnings("unused")
Member addMember=new Member(mid,mname,tid);
}
@SuppressWarnings("unused")
public void addTeamLeader(int tid,int mid,int leadPswd,String mname) 
{
	TeamLeader addTeamLeader=new TeamLeader(tid,mid,leadPswd,mname);
}

}
