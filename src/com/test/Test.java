package com.test;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.dao.StudentDaoImpl;
import com.model.Stud;
import com.utility.Utility;


public class Test 
{
	public static void main(String[] args) 
	{
		Scanner sc=new Scanner(System.in);
		int option=0;
		int rs=0;
		Stud student=null;
		List<Stud> list=new ArrayList<Stud>();
		System.out.println("***************wecome***********************\n");
		while(true)
		{
			System.out.println("1]Add");
			System.out.println("2]update");
			System.out.println("3]delete");
			System.out.println("4]Search");
			System.out.println("5]View All Record");
			System.out.println("6]update by choise ");
			System.out.println("7]Exit");
			System.out.println("\n");
			
			System.out.println("Select your choice :");
			
			option=sc.nextInt();
			
		switch (option)
		{
			case 1:
					
					student=new Stud();
					/*System.out.println("Enter ID :");
					s.setId(sc.nextInt());*/	//it will not insert user given id ,it will insert table auto_incremented ID.  
					
					System.out.println("Enter name :");
					student.setName(sc.next());
					
					System.out.println("Enter Mobile Number :");
					student.setMobileNo(sc.next());
					StudentDaoImpl.add(student);
					
					break;
	
			case 2:
					// all fields are mandatory because not-null property used on fields
					
					System.out.println("You should know the ID First :");
					System.out.println("Enter ID :");
					student=StudentDaoImpl.loadStudent(sc.nextInt());
					list.add(student);
					Utility.displayRecords(list);
					list.clear();
					
					System.out.println("Enter name :");
					student.setName(sc.next());
					
//					System.out.println("Enter Mobile Number :");
//					student.setMobileNo(sc.next());
					
					//#step1
					//it will persist object in db if transaction started properly
					StudentDaoImpl.pushDirtyObj();
					
					//#step2
					// here it will throw exception "illegally attempted to associate a proxy with two open Sessions"
					// because in update method we are openeing new session and try to persist entity which is used 
					// in previous session and it is not close yet.
					// if we close previous session then it will work fine 
					/*StudentDaoImpl.session.close();
					StudentDaoImpl.update(student);*/
					
					//#step3
					// if we dont't want close the previous session object then used session merge method 
					/*StudentDaoImpl.session.merge(student);
					StudentDaoImpl.session.getTransaction().commit();*/
					
					//#step4
//					StudentDaoImpl.updateTest1();
					
					break;
				
			case 3:

					System.out.println("You should know the ID First :");
					System.out.println("Enter ID :");
					student=StudentDaoImpl.loadStudent(sc.nextInt());
					list.add(student);
					Utility.displayRecords(list);
					list.clear();
//					if previous session open then it will throw exception "illegally attempted to associate a proxy with two open Sessions"
					StudentDaoImpl.session.close();
					StudentDaoImpl.delete(student);
					System.out.println("record deleted ..");
					break;
			
			case 4:
					System.out.println("Enter ID :");
					student=StudentDaoImpl.loadStudent(sc.nextInt());
					list.add(student);
					Utility.displayRecords(list);
					list.clear();
					break;
					
		
			case 5:
					list=StudentDaoImpl.getAllRecord();
					Utility.displayRecords(list);
					list.clear();
					break;
			
			case 6:
					System.out.println("You should know the ID First :");
					System.out.println("Enter ID :");
					//if we used load here then it will throw  exception "illegally attempted to associate a proxy with two open Sessions"
					student=StudentDaoImpl.getStudent(sc.nextInt());
					list.add(student);
					Utility.displayRecords(list);
					list.clear();
					while(true)
					{
						
						System.out.println("1]Name ");
						System.out.println("2]MobileNumber");
						System.out.println("3]Update Both Name and Mobile Number ");
						
						System.out.println("select your Choice :");
						int choice=sc.nextInt();
						
						if(choice==1)
						{
							System.out.println("Enter Name :");
							student.setName(sc.next());
							StudentDaoImpl.saveOrUpdate(student);
							
						}else if(choice==2)
						{
							System.out.println("Enter Mobile Number :");
							student.setMobileNo(sc.next());
							StudentDaoImpl.saveOrUpdate(student);

						}else if(choice==3)
						{
							System.out.println("Enter Name :");
							student.setName(sc.next());
							
							System.out.println("Enter Mobile Number :");
							student.setMobileNo(sc.next());
							
							StudentDaoImpl.saveOrUpdate(student);
						}else{
							System.err.println("invalid choise");
							break;
						}
						
					}
					
			case 7 :
					System.exit(0);
					break;
				
			default:
					System.err.println("Invalid Choice ...try again");
					break;
			}	
		}
		
	}



}
