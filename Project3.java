import java.io.*;
import java.util.*;
public class Project3 {
	static String[] appliances=new String[300];
	static String[] customer=new String[300];
	static String[] order=new String[200];
	static int index=0;
	static int c_index=0;
	static int order_index=0;
	static Scanner in= new Scanner(System.in);
	static BufferedReader ob=new BufferedReader(new InputStreamReader(System.in));
	public static void main(String args[]) throws Exception {//menu
		System.out.println("\n|-------------Welcome to the Home Appliances Store-------------|");
		f_array_appliances(appliances,"D:\\Appliances.txt");
		f_array_customer(customer,"D:\\Customer.txt");
		f_array_order(order,"D:\\Order.txt");
		index=p_incr();
		c_index=c_incr();
		order_index=o_incr();
		while(true) {
			try {
				System.out.print("1. Appliances\t 2. Customer\t 3. Order \t4. Exit\nEnter your choice:");
				int choice=in.nextInt();      
				switch (choice) {
					case 1:
						Appliances();   //appliances module
						writefile(appliances,"D:\\Appliances.txt"); //write data from array to file
						break;
					case 2:
						Customer();  //customer module
						//write data from array to file
						break;
					case 3:
						Order();  //Order module
						writefile_order(); //write data from array to file
						break;
					case 4:
						System.out.println("Exiting");
						System.exit(choice);
					default:
						System.err.println("Enter digits from 1 to 4.");
				}
			}//Exception handling 
			catch(InputMismatchException e) {
				System.err.println("Please enter digit numbers only!");
			}
			catch(Exception e) {
				System.err.println(e+ " has been caught!");
			}
			finally {
				in.nextLine();
			}
		}
	}
	//Module 1---------------------------||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
	public static void Appliances() {
		while(true) {
			try {
				System.out.print("Enter Your Choice \n1. Add Product Record\t 2. View Product Record\t 3. Search Product Record\n 4. Update Product Record\t5. Delete Product Record\t6. Back\nEnter number:  ");
				int choice=in.nextInt();   //user enter his choice
				if(choice==1) {
					add_product_record();
					index=increment();
				}
				else if(choice==2) {
					view_product_record();}
				else if(choice==3)
					search_product_record();
				else if(choice==4)
					update_product_record();
				else if(choice==5)
					delete_product_record();
				else if(choice==6) {
					System.out.println("Back");
					break;
				}
				else
					System.out.println("Enter number from 1 - 6. ");	
			}
			catch(InputMismatchException e) {
				System.err.println("Please enter digit numbers only!");
				in.nextLine();
			}
			catch(Exception e) {
				System.err.println(e+ " has been caught!");
				in.nextLine();
			}
		}
	}static int check_index(String[] list) {
		int ch=0;
		for(int i=0;i<list.length;i++) {
			if(list[i]!=null)
				ch=i;
		}
		return ch;
	}
	public static void add_product_record() throws Exception {  //add data into array
		System.out.print("Enter ID of Product: ");
		int id=in.nextInt();
		if(id_check("D:\\Appliances.txt",Integer.toString(id)))  //check whether id exists in file or not
			System.out.println("Id exists in file!.");
		else {
			if(check_inarr(appliances,id))   //check whether id exists in array or not
				System.out.println("ID exists in array!");
			else {
				System.out.print("Enter name of Product: ");
				String name=ob.readLine();
				if(name.isBlank())   //check if string contain white spaces or empty
					System.out.println("Field cannot be empty!");
				else {
					if(!name_check(name))   //check if name is in digits or characters
						System.out.println("Name must be string not digits.");
					else{
					System.out.print("Enter Quantity of Product: ");
					int quantity=in.nextInt();
					System.out.print("Enter Price of Product: ");
					int price=in.nextInt();
					appliances[index]=Integer.toString(id);
					appliances[index+1]=name.replaceAll(" ","-");
					appliances[index+2]=Integer.toString(quantity);
					appliances[index+3]=Integer.toString(price);
					}
				}
			}
		}
	}
	static int increment() {  //increment the static variable
		return index+=4;
	}
	public static void view_product_record() throws Exception {
		System.out.println("Press 1. Sort by ID 2. Sort by name 3. Sort by quantity  4. Sort by price ");
		int choice=in.nextInt();
		if(choice==1) {
			sort(appliances,0);
			p_view();}
		else if(choice==2) {
			sort_name(appliances,1);p_view();
			}
		else if(choice==3) {
			sort(appliances,2);p_view();
			}
		else if(choice==4) {
			sort(appliances,3);p_view();
			}
		else
			p_view();
	}
	public static void p_view() throws Exception {  //view the record of appliances
		if(length(appliances)<1)
			System.err.println("No Records available!");
		else {
			System.out.printf("%-20s%-20s%-20s%-20s\n","Product ID","Product Name","Quantity","Price");
			for(int i=0;i<appliances.length;i+=4) {
				if(appliances[i]!=null)
					System.out.printf("%-20s%-20s%-20s%-20s\n",appliances[i],appliances[i+1],appliances[i+2],appliances[i+3]);
			}
		}
	}	
	public static void search_product_record() throws Exception {
		System.out.println("Enter ID to search record: ");
 		int id=in.nextInt();
		int i=binary_search(appliances,id)*4;
 		if(i<0)
 			System.out.println("Id not found!");
 		else {
 			System.out.printf("%-20s%-20s%-20s%-20s\n","Product ID","Product Name","Quantity","Price");
 			System.out.printf("%-20s%-20s%-20s%-20s\n",appliances[i],appliances[i+1],appliances[i+2],appliances[i+3]);
 			}
 		}
	public static void update_product_record() throws Exception {
		boolean check=false;
		System.out.println("Enter ID to update record: ");
 		String id=in.next();		
 		for(int i=0;i<appliances.length;i+=4) {
 			if(appliances[i]!=null) {
 				if(appliances[i].equals(id)) {
 					check=true;
 					System.out.println("Which field you want to update: \n 1. Name\t2.Quantity\t3.Price\t4. back\nEnter choice: ");
 					int choice=in.nextInt();
 					if(choice==1) {
	 					System.out.println("Update name of the product: ");
	 					String name=ob.readLine();
	 					if(name.isBlank())System.out.println("Field cannot be empty!");
	 					else 
	 					appliances[i+1]=name.replaceAll(" ","-");}
 					else if(choice==2) {
	 					System.out.println("Update the quantity: ");
	 					int quantity=in.nextInt();
	 					appliances[i+2]=Integer.toString(quantity);
	 					}
 					else if(choice==3) {
	 					System.out.println("Update price: ");
	 					int price=in.nextInt();
	 					appliances[i+3]=Integer.toString(price);
	 					}
 					else if(choice==4)
 						break;
 					else
 						System.out.println("Enter number from 1 to 4.");
 					}
 				}
 		}
    	if(!check)
        	System.out.println("ID not found!");
    }
	public static void delete_product_record() throws Exception {
		boolean check=false;
		System.out.println("Enter ID to delete record: ");
 		String id=ob.readLine();		
 		for(int i = 0; i < appliances.length; i+=4){
        	if(appliances[i]!=null) {
	            if(appliances[i].equalsIgnoreCase(id)) {
	            	check=true;
	            	appliances[i]=null;appliances[i+1]=null;appliances[i+2]=null;appliances[i+3]=null;
	                break;
	            }
        	}  
 		}if(!check)System.out.println("ID not found!");
	}
	//Customer Module --------------------------------------------------------------|||||||||||||||||||||||||||||||||||||||||||||||||||||||
	public static void Customer() throws Exception {
		while(true)  {
			try{
				System.out.println("Enter Your Choice \n1. Add Customer Record\t 2. View Customer Record\t 3. Search Customer Record\n 4. Update Customer Record\t5. Delete CustomerRecord\t6. Back\nEnter number:  ");
				int choice=in.nextInt();
				if(choice==1) {
					add_customer_record(c_index);
					c_index=c_increment();
					writefile(customer,"D:\\Customer.txt"); 
				}
				else if(choice==2) {
					view_customer_record();
				}
				else if(choice==3)
					search_customer_record();
				else if(choice==4)
					update_customer_record();
				else if(choice==5)
					delete_customer_record();
				else if(choice==6) {
					System.out.println("Back");
					break;
				}
				else
					System.err.println("Enter number from 1 - 6. ");
			}
			catch(InputMismatchException e) {
				System.err.println("Please enter digit numbers only!");
				in.nextLine();
			}
			catch(Exception e) {
				System.err.println(e+ " has been caught!");
				in.nextLine();
			}
		}
	}
	public static void add_customer_record(int c_index) throws Exception {
		System.out.print("Enter ID: ");
		int id=in.nextInt();
		if(id_check("D:\\Customer.txt",Integer.toString(id)))  //check if id exists in file or not
			System.out.println("ID exists in file!");
		else {
			if(check_inarr(customer,id))   //check if id exists in array or not
				System.out.println("ID exists!");
			else {
			System.out.print("Enter name: ");
			String name=ob.readLine();
			if(name.isBlank())   //check if name is null or blank or not
				System.out.println("Field cannot be empty!");
			else {
				if(!name_check(name))    //check if name is integer or string
					System.out.println("Name must be string not digits.");
				else{
				System.out.print("Enter age: ");
				int age=in.nextInt();
				System.out.print("Enter Location: ");
				String location=ob.readLine();
				customer[c_index]=Integer.toString(id);
				customer[c_index+1]=name.replaceAll(" ","-");
				customer[c_index+2]=Integer.toString(age);
				customer[c_index+3]=location.replaceAll(" ","-");}
				}
			}
		}
	}
	static int c_increment() {
		return c_index+=4;
	}
	public static void view_customer_record() throws Exception {
		System.out.println("Press 1. Sort by ID 2. Sort by name 3. Sort by age  4. Sort by Location: ");
		int choice=in.nextInt();
		if(choice==1) {
			sort(customer,0);
			c_view();}
		else if(choice==2) {
			sort_name(customer,1);c_view();
			}
		else if(choice==3) {
			sort(customer,2);c_view();
			}
		else if(choice==4) {
			sort_name(customer,3);c_view();
			}
		else
			c_view();
	}
	public static void c_view() {
		if(length(customer)<1)
			System.err.println("No Records available!");
		else {
			System.out.printf("%-20s%-20s%-20s%-20s\n","Customer ID","Customer Name","Age","Location");
			for(int i=0;i<customer.length;i+=4) {
				if(customer[i]!=null)
					System.out.printf("%-20s%-20s%-20s%-20s\n",customer[i],customer[i+1],customer[i+2],customer[i+3]);
			}
		}
	}
	public static void search_customer_record() throws Exception {
		System.out.println("Enter ID to search record: ");
 		int id=in.nextInt();
		int i=binary_search(customer,id)*4;
 		if(i<0)
 			System.out.println("Id not found!");
 		else {
 			System.out.printf("%-20s%-20s%-20s%-20s\n","Customer ID","Customer Name","Age","Location");
 			System.out.printf("%-20s%-20s%-20s%-20s\n",customer[i],customer[i+1],customer[i+2],customer[i+3]);
 			}
	}
	public static void update_customer_record() throws Exception {
		boolean check=false;
		System.out.println("Enter ID to update record: ");
 		String id=in.next();		
 		for(int i=0;i<customer.length;i+=4) {
 			if(customer[i]!=null) {
 				if(customer[i].equals(id)) {
 					check=true;
 					System.out.println("Which field you want to update: \n 1. Name\t2. Age\t3. Location\t4. back\nEnter choice: ");
 					int choice=in.nextInt();
 					if(choice==1) {
	 					System.out.println("Update name of the customer: ");
	 					String name=ob.readLine();
	 					if(name.isBlank())System.out.println("Field cannot be empty!");
	 					else 
	 					customer[i+1]=name.replaceAll(" ","-");}
 					else if(choice==2) {
	 					System.out.println("Update the age: ");
	 					int age=in.nextInt();
	 					customer[i+2]=Integer.toString(age);
	 					}
 					else if(choice==3) {
	 					System.out.println("Update the location: ");
	 					String location=ob.readLine();
	 					customer[i+3]=location.replaceAll(" ","-");
	 					}
 					else if(choice==4)
 						break;
 					else
 						System.out.println("Enter number from 1 to 4.");
 					}
 				}
 		}
    	if(!check)
        	System.out.println("ID not found!");
    }
	public static void delete_customer_record() throws Exception {
		boolean check=false;
		System.out.println("Enter ID to delete record: ");
 		String id=ob.readLine();		
 		for(int i = 0; i < customer.length; i+=4){
        	if(customer[i]!=null) {
	            if(customer[i].equalsIgnoreCase(id)) {
	            	check=true;
	            	customer[i]=null;customer[i+1]=null;customer[i+2]=null;customer[i+3]=null;
	                break;
	            }
        	}  
 		}if(!check)System.out.println("ID not found!");
	}
	//Module 3-------------------------||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
	public static void Order() {
		while(true) {
			try {
				System.out.println("1. Add Order\t 2. View Order\t 3. Exit\nEnter choice: ");
				int choice=in.nextInt();
				if(choice==1) {
					add_order();
					order_index=order_increment();
				}
				else if(choice==2) {
					view_order();
				}
				else if(choice==3) {
					System.out.println("Back");break;
				}
				else
					System.out.println("Enter number from 1 - 3.");
					
			}
			catch(InputMismatchException e) {
				System.err.println("Please enter digit numbers only!");
				in.nextLine();
			}
			catch(Exception e) {
				System.err.println(e+ " has been caught!");
				in.nextLine();
			}
		}
	}
	public static void add_order() throws Exception {   //add order method user enter his order
		int total_price,quantity;
		boolean c_check=false;
		boolean p_check=false;
		System.out.println("Enter Customer ID: ");
		int cust_id=in.nextInt();
		for(int i=0;i<customer.length;i+=4) {
			if(customer[i]!=null && customer[i].equals(Integer.toString(cust_id))) {  //check if user entered id is equal to in array
				c_check=true;
				String cust_name=customer[i+1];
				System.out.println("Enter product ID: ");
				int prd_id=in.nextInt();
				for(int k=0;k<appliances.length;k+=4) {
					if(appliances[k]!=null && appliances[k].contentEquals(Integer.toString(prd_id))) {  //check if id exists in array or not
						p_check=true;
						String prd_name=appliances[k+1];
						String avail_quan=appliances[k+2];
						String price=appliances[k+3];
						System.out.println("Enter quantity u want: ");
						quantity=in.nextInt();
						appliances[k+2]=Integer.toString(Integer.parseInt(avail_quan)-quantity);  //append new quantity into appliances module
						if(quantity<=Integer.valueOf(avail_quan)) {   //check is quantity is available or not
							total_price=quantity*Integer.valueOf(price); //calculate total price
							order[order_index]=Integer.toString(cust_id);
							order[order_index+1]=cust_name.replaceAll(" ","-");
							order[order_index+2]=prd_name.replaceAll(" ","-");
							order[order_index+3]=Integer.toString(quantity);
							order[order_index+4]=Integer.toString(total_price);
						}
						else
							System.out.println("Available quantity is "+avail_quan);
					}
				}
			}
		}
		if(!p_check)
			System.err.println("No Product found!");
		else if(!c_check)
			System.err.println("ID not found!");
		
	}
	static int order_increment() {
		order_index+=5;
		return order_index;
	}
	public static void view_order() {
		if(length(order)<1)
			System.err.println("Cart is empty!");
		else {
			System.out.printf("%-20s%-20s%-20s%-20s%-20s\n","Customer ID","Customer Name","Product Name","Quantity","Total Price");
			for(int i=0;i<order.length;i+=5) {
				if(order[i]!=null)
					System.out.printf("%-20s%-20s%-20s%-20s%-20s\n",order[i],order[i+1],order[i+2],order[i+3],order[i+4]);
			}
		}
	}
	public static boolean check_inarr(String[] list,int id) {
		for(int i=0;i<list.length;i+=4) {
			if(list[i]!=null) {
				if(list[i].equals(Integer.toString(id)))
					return true;
			}
		}
		return false;
	}
	public static int length(String[] list) {
		int count=0;
		for(int i=0;i<list.length;i++) {
			if(list[i]!=null)
				count++;
		}
		return count;
	}
	public static void writefile(String[] list,String path) throws Exception {
		File f=new File(path);
		f.createNewFile();
		BufferedWriter bf=new BufferedWriter(new FileWriter(f));
		if(f.exists()) {
			for(int i=0;i<list.length;i+=4) {
				if(list[i]==null)
					continue;
				else { 
						if(!id_check(path,list[i])) {
							bf.write(list[i]+" ");bf.write(list[i+1]+" ");bf.write(list[i+2]+" ");bf.write(list[i+3]);
							bf.newLine();
						}
						
					}
				
				}
			}
		else {
			System.out.println("File not exists");
		}
		bf.close();		
	}
	public static void writefile_order() throws Exception {
		File f=new File("D:\\Order.txt");
		BufferedWriter bf=new BufferedWriter(new FileWriter(f));
		if(f.exists()) {
			for(int i=0;i<order.length;i+=5) {
				if(order[i]==null)
					continue;
				else { 
						if(!id_check("D:\\Order.txt",order[i])) {
							bf.write(order[i]+" ");bf.write(order[i+1]+" ");bf.write(order[i+2]+" ");bf.write(order[i+3]+" ");
							bf.write(order[i+4]);
							bf.newLine();
						}
						
						else continue;
					}
				
				}
			}
		else
			System.out.println("File not exist!");
		bf.close();		
	}
	public static boolean id_check(String filepath,String id) throws Exception {
		BufferedReader reader=new BufferedReader(new FileReader(filepath));
		boolean isFind=false;
		String s=reader.readLine();
		String[] words;
		while(s!=null)  
	      {
	         words=s.split(" ");
	         if (words[0].equals(id))
	        	 isFind=true;
	          s=reader.readLine();
	      }
		reader.close();
		return isFind;
	}
	 public static void sort(String[] list,int index) throws Exception{   //method of sorted array with string that contain number
		for(int i=0;i<list.length-1;i+=4) {
			if(list[i]!=null) {   //check id null or not
				for(int j=i+4;j<list.length;j+=4) {
					if(list[j]!=null) {   //check id null or not
						if(Integer.parseInt(list[j+index])<Integer.parseInt(list[i+index]))  {  //compare two integers
							String temp = list[i];list[i]=list[j];list[j] = temp;
							String temp1=list[i+1];list[i+1]=list[j+1];list[j+1] = temp1;
							String temp2=list[i+2];list[i+2]=list[j+2];list[j+2] = temp2;
							String temp3=list[i+3];list[i+3]=list[j+3];list[j+3] = temp3;
						}
					}
				}
			}
		}
	 }
	 public static void sort_name(String[] list,int index) throws Exception{   //method of sorted array with string that contain number
		for(int i=0;i<list.length-1;i+=4) {
			if(list[i]!=null) {   //check id null or not
				for(int j=i+4;j<list.length;j+=4) {
					if(list[j]!=null) {   //check id null or not
						if(list[j+index].compareToIgnoreCase(list[i+index])<0)  {  //compare two integers
							String temp = list[i];list[i]=list[j];list[j] = temp;
							String temp1=list[i+1];list[i+1]=list[j+1];list[j+1] = temp1;
							String temp2=list[i+2];list[i+2]=list[j+2];list[j+2] = temp2;
							String temp3=list[i+3];list[i+3]=list[j+3];list[j+3] = temp3;
						}
					}
				}
			}
		}
	 }
	 public static boolean name_check(String s) {   //method of check name
		 for(int i=0;i<s.length();i++) {
			 if((s.charAt(i)>='A' && s.charAt(i)<='Z') || (s.charAt(i)>='a' && s.charAt(i)<='z')|| s.charAt(i)==(char)32)
				 return true;
		 }
		 return false;
	 }
	 public static void f_array_appliances(String[] list,String path) throws Exception {
		 Scanner sc = new Scanner(new File(path));
		 int i=index;
	      while (sc.hasNext()) {
	        String str = sc.nextLine();
	        Scanner s=new Scanner(str);
	        s.useDelimiter("\\s");
	        list[i]=s.next();
	        list[i+1]=s.next();
	        list[i+2]=s.next();
	        list[i+3]=s.next();
	        i=p_incr();
	      }
	      sc.close();
	 }
	 static int p_incr() {
		 return index+=4;
	 }
	 public static void f_array_customer(String[] list,String path) throws Exception {
		 Scanner sc = new Scanner(new File(path));
		 int i=c_index;
	      while (sc.hasNext()) {
	        String str = sc.nextLine();
	        Scanner s=new Scanner(str);
	        s.useDelimiter("\\s");
	        list[i]=s.next();
	        list[i+1]=s.next();
	        list[i+2]=s.next();
	        list[i+3]=s.next();
	        i=c_incr();
	      }
	      sc.close();
	 }
	 static int c_incr() {
		 return c_index+=4;
	 }
	 public static void f_array_order(String[] list,String path) throws Exception {
		 Scanner sc = new Scanner(new File(path));
		 int i=order_index;
	      while (sc.hasNext()) {
	        String str = sc.nextLine();
	        Scanner s=new Scanner(str);
	        s.useDelimiter("\\s");
	        list[i]=s.next();
	        list[i+1]=s.next();
	        list[i+2]=s.next();
	        list[i+3]=s.next();
	        list[i+4]=s.next();
	        i=o_incr();
	      }
	      sc.close();
	 }
	 static int o_incr() {
		 return order_index+=5;
	 }
	 public static int binary_search(String[] list,int id) throws Exception{
		sort(list,0);
	 	int[] arr=array(list);
		int low=0;
		int high=arr.length-1;
		int min;
		while(low<=high) {
			min=(low+high)/2;
			if(id==arr[min]) {
				return min;
			}
			else if(id<arr[min])
				high=min-1;
			else if(id>arr[min])
				low=min+1;
		}
		return -1;
	}
	 public static int[] array(String[] list) {
		 int[] arr=new int[count(list)/4];	
			for(int i=0,j=0;j<list.length;i++,j+=4) {
				if(list[j]!=null)
					arr[i]=Integer.parseInt(list[j]);
			}
			return arr;
	 }
		public static int count(String[] list) {
			int count=0;
			for(int i=0;i<list.length;i++) {
				if(list[i]!=null)
					count++;
			}
			return count;
		}
}
