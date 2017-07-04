package TeamManagementSystem;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class TaskExecutor {
	public static void main(String args[]) throws SQLException, IOException
	{
		int choice,taskid;
		String currentStatus,mname;
		String[] status=new String[10];

		do
		{
		@SuppressWarnings("resource")
		Scanner in=new Scanner(System.in);
		System.out.println("Enter the choice \n1.Company\n2.TeamLeader\n3.TeamMember");
		choice=in.nextInt();
		
		switch(choice)
		{
		case 1:
			int id,pswd,compChoice,tid,mid;
			System.out.println("Enter company id and password");
			id=in.nextInt();
			pswd=in.nextInt();
			Company company=new Company(id,pswd);
			if(company.login==true)
			{
			do
			{

			System.out.println("Enter the choice\n1.View Teams\n2.Add Team\n3.Add Member\n4.Add TeamLeade\n5.CompanyDetails\n6.Back");
			compChoice=in.nextInt();
			switch(compChoice)
			{
			case 1:
				company.getTeams();
				break;
			case 2:
				System.out.println("Enter the Team ID");
				tid=in.nextInt();
				company.addTeam(tid);
				break;
			case 3:
				System.out.println("Enter the Member ID,Name,Team ID");
				mid=in.nextInt();
				mname=in.nextLine();
				in.nextLine();
				tid=in.nextInt();
				company.addMember(mid, mname, tid);
				break;
			case 4:
				System.out.println("Enter the Team ID,Member ID,Password,Member Name");
				tid=in.nextInt();
				mid=in.nextInt();
				pswd=in.nextInt();
				in.nextLine();
				mname=in.nextLine();
				company.addTeamLeader(tid, mid, pswd,mname);
				break;
			case 5:
				company.getCompanyDetails();
				break;
			default:
				System.out.println("Enter the correct choice");
				break;
			}
			System.out.println("Enter 6 to continue");
			compChoice=in.nextInt();
			}while(compChoice==6);
			}
			break;
	   case 2:
		   int teamLeaderChoice,select;
			status[0]="s_poc";status[1]="e_poc";
			status[2]="s_inprogress";status[3]="e_inprogress";
			status[4]="s_underreview";status[5]="e_underreview";
			status[6]="s_testing";status[7]="e_testing";
			status[8]="s_released";status[9]="e_released";
		   System.out.println("Enter Member id and password and name");
		   mid=in.nextInt();
		   pswd=in.nextInt();
		   in.nextLine();
		   mname=in.nextLine();
		   TeamLeader teamLeader=new TeamLeader(mid,pswd,mname);
		   if(teamLeader.teamlogin&&teamLeader.login==true)
		   {
		   do
		   {
			   System.out.println("Enter the choice\n1.Task List\n2.View Task Details\n3.Update Status\n4.View Timeline\n5.Generate reports\n6.Generate Team Reports\n7.Create Task\n8.MemberList\n9.teammm");
			   teamLeaderChoice=in.nextInt();
			   
			switch(teamLeaderChoice)
			   {
			   case 1:
				   System.out.println("Enter\n1.Team Task Details\n2.Personal Task Details");
				   in.nextLine();
				   select=in.nextInt();
				   if(select==1)
				   {
					   teamLeader.getMemberTaskList();
				   }
				   else if(select==2)
				   {
				   teamLeader.taskList();
				   }
					 break;
				   case 2:
					   System.out.println("Enter the task ID to be viewed");
					   taskid=in.nextInt();
					   teamLeader.teamTaskDetails(taskid);
					   break;
				   case 3:
					   System.out.println("Enter the task ID to be updated");
						  taskid=in.nextInt();
						 currentStatus= teamLeader.getCurrentStatus(taskid);
						 int taskChoice;
						 if(!(teamLeader.check(taskid)))
						 {
							 System.out.println("Select your task");
							 break;
						 }
						 else
						 {
						 if(currentStatus==null)
						 {
							 System.out.println("Current Status:"+currentStatus);
							 System.out.println("Do you want the change the status to:s_poc\nEnter\n1.yes\n2.No");
							 in.nextLine();
							 taskChoice=in.nextInt();
							 if(taskChoice==1)
								 teamLeader.updateTaskStatus(taskid,"s_poc");
						 }
						 else
						 {
						 for(int i=0;i<status.length;i++)
							{
								if(i==status.length-1)
								{
									System.out.println("task finisht");
								}
								else if(status[i].contains(currentStatus))
								{
									System.out.println("Current Status:"+currentStatus);
									System.out.println("do you want to change the status to:"+status[i+1]+"\nEnter\n1.yes\n2.No");
									taskChoice=in.nextInt();
									if(taskChoice==1)
										teamLeader.updateTaskStatus(taskid,status[i+1]);
									break;
								}
							}
						 }
						 }
						 break;
				   case 4:
						 System.out.println("Enter the taskid to view TimeLine:");
						 taskid=in.nextInt();
						 teamLeader.viewTeamTimeLine(taskid);
						 break;
				   case 5:
				   teamLeader.viewReport();
				   break;
				   case 6:
					   System.out.println("Enter\n1.Weekly Report\n2.Monthly Report");
					   choice=in.nextInt();
					   if(choice==1)
					   teamLeader.viewTeamReport("weekly");
					   else if(choice==2)
						   teamLeader.viewTeamReport("monthly");
					   break;
				   case 7:
					   System.out.println("Enter the Task ID and Member ID");
					   taskid=in.nextInt();
					   mid=in.nextInt();
					   if(teamLeader.verifyMember(mid))
					   teamLeader.createTask(taskid, mid);
					   else
					   {
						   System.out.println("Select member from your team");
						   teamLeader.memberList();
					   }
					   break;
				   case 8:
					   teamLeader.memberList();
					   break;
				   case 9:
					   teamLeader.viewTTimeLine();
				   default:
						System.out.println("Enter the correct choice");
						break;
			   }
			System.out.println("Enter 9 to continue");
			choice=in.nextInt();
		   }while(choice==9);
		   }
		  break;
	   case 3:
		   int memberChoice;
			status[0]="s_poc";status[1]="e_poc";
			status[2]="s_inprogress";status[3]="e_inprogress";
			status[4]="s_underreview";status[5]="e_underreview";
			status[6]="s_testing";status[7]="e_testing";
			status[8]="s_released";status[9]="e_released";
		   System.out.println("Enter Member id,Name");
		   mid=in.nextInt();
		   in.nextLine();
		   mname=in.nextLine();
		   Member member=new Member(mid,mname);
		   if(member.login==true)
		   {
		   do
		   {
			   System.out.println("Enter the choice\n1.Task List\n2.View Task Details\n3.Update Status\n4.View Timeline\n5.Generate reports");
			   memberChoice=in.nextInt();
			   switch(memberChoice)
			   {
			   case 1:
				 member.taskList();
				 break;
			   case 2:
				   System.out.println("Enter the task ID to be viewed");
				   taskid=in.nextInt();
				   member.taskDetails(taskid);
				   break;
			   case 3:
				  System.out.println("Enter the task ID to be updated");
				  taskid=in.nextInt();
				 currentStatus= member.getCurrentStatus(taskid);
				 int taskChoice;
				 if(!(member.check(taskid)))
				 {
					 System.out.println("Select your task");
					 break;
				 }
				 else
				 {
				 if(currentStatus==null)
				 {
					 System.out.println("Current Status:"+currentStatus);
					 System.out.println("Do you want the change the status to:s_poc\nEnter\n1.yes\n2.No");
					 in.nextLine();
					 taskChoice=in.nextInt();
					 if(taskChoice==1)
					 member.updateTaskStatus(taskid,"s_poc");
				 }
				 else
				 {
				 for(int i=0;i<status.length;i++)
					{
						if(i==status.length-1)
						{
							System.out.println("task finisht");
						}
						else if(status[i].contains(currentStatus))
						{
							System.out.println("Current Status:"+currentStatus);
							System.out.println("do you want to change the status to:"+status[i+1]+"\nEnter\n1.yes\n2.No");
							taskChoice=in.nextInt();
							if(taskChoice==1)
							member.updateTaskStatus(taskid,status[i+1]);
							break;
						}
					}
				 }
				 }
				 break;
			   case 4:
					 System.out.println("Enter the taskid to view TimeLine:");
					 taskid=in.nextInt();
					 member.viewTimeLine(taskid);
					 break;
			   case 5:
				   member.viewReport();
				   break;
			   default:
					System.out.println("Enter the correct choice");
					break;
			   }
			   System.out.println("Enter 6 to continue");
			   memberChoice=in.nextInt();
		   }while(memberChoice==6);
		   }
		   break;
	   
		   }
		System.out.println("Enter 4 to continue");
		choice=in.nextInt();
		}while(choice==4);

	}

}
