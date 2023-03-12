package com.utility;

import java.util.List;

import com.model.Stud;

public class Utility {

	public static void displayRecords(List<Stud> students) {
		if(students!=null && !students.isEmpty()) {
			System.out.println("\n|\t ID \t|\t Name \t|\t mobile Number \t|");
			System.out.println("=====================================================================");
			for(Stud st : students)
			{
				System.out.println("\t"+st.getId()+"\t|\t"+st.getName()+"\t|\t"+st.getMobileNo());
				System.out.println("------------------------------------------------------------------------------");
			}
		}else {
			System.err.println("record not found");
		}
	}
	
}
